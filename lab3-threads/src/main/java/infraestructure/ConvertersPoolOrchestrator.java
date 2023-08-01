package infraestructure;

import domain.Result;
import domain.Site;
import domain.WebToPdfConverter;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

public class ConvertersPoolOrchestrator {
    private String outputDirectoryPath;
    private Queue<Site> pendingSites;


    public ConvertersPoolOrchestrator(String outputDirectoryPath) {
        this.outputDirectoryPath = outputDirectoryPath;
        this.pendingSites = new ConcurrentLinkedQueue<>();
    }

    public void enqueueSite(Site s) {
        pendingSites.add(s);
        synchronized (pendingSites) {
            pendingSites.notify();
        }

        System.out.println(String.format("[ORCHESTRATOR] A new site was added to the queue ⌚"));
    }

    public void registerWorker(int id) {
        // Create a new thread to listen for new urls
        Thread worker = new Thread(() -> {
            // Create a new instance of a converter (worker)
            WebToPdfConverter converterImplementation = new WebToPdfPlaywrightConverter(outputDirectoryPath);

            // Listening loop
            while (true) {
                System.out.println(String.format("[THREAD %d] The thread is waiting for a new url \uD83D\uDCA4", id));

                synchronized (pendingSites) {
                    // Block until a new url is received to avoid unnecessary iterations
                    while (pendingSites.isEmpty()) {
                        try {
                            pendingSites.wait();
                        } catch (InterruptedException e) {
                            System.out.println(String.format("[THREAD %d] The thread was interrupted \uD83D\uDCA5", id));
                            e.printStackTrace();
                        }
                    }
                }

                // Get the url
                System.out.println(String.format("[THREAD %d] The thread received a new url \uD83D\uDC77", id));
                Site site = pendingSites.poll();
                if (site == null) {
                    System.out.println(String.format("[THREAD %d] The thread received a null site \uD83D\uDCA5", id));
                    continue;
                }

                Result result = new Result(
                        site.getName(),
                        converterImplementation.convert(site.getUrl(), site.getName().toLowerCase().replaceAll("\\s+", "-"))
                );

                System.out.println(String.format("[THREAD %d] The thread finished the conversion \uD83D\uDC4C", id));
                System.out.println(result);
            }
        });

        // Run the thread
        worker.start();
    }
}
