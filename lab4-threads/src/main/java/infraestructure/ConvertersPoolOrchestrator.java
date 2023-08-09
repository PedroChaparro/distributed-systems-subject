package infraestructure;

import domain.DocumentConverter;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConvertersPoolOrchestrator {
    private Queue<String> pendingFiles;

    public ConvertersPoolOrchestrator() {
        this.pendingFiles = new ConcurrentLinkedQueue<>();
    }

    public void enqueueFile(String file) {
        pendingFiles.add(file);
        synchronized (pendingFiles) {
            pendingFiles.notify();
        }

        System.out.println(String.format("[ORCHESTRATOR] A new file was added to the queue ⌚"));
    }

    public void registerWorker(int id){
        // Create a new thread to listen for new files
        Thread worker = new Thread(() -> {
            // Create a new instance of a converter (worker)
            DocumentConverter converterImplementation = new JOfficeDocumentConverter();

            // Listening loop
            while (true) {
                System.out.println(String.format("[THREAD %d] The thread is waiting for a new file \uD83D\uDCA4", id));

                synchronized (pendingFiles) {
                    while (pendingFiles.isEmpty()) {
                        try {
                            pendingFiles.wait();
                        } catch (InterruptedException e) {
                            System.out.println(String.format("[THREAD %d] The thread was interrupted while waiting the Queue \uD83D\uDCA5", id));
                            e.printStackTrace();
                        }
                    }
                }

                // Get the url
                String filePath = pendingFiles.poll();
                System.out.println(String.format("[THREAD %d] The thread received a new file \uD83D\uDC77", id));
                if (filePath == null) continue;

                // Get the file extension
                String extension = filePath.substring(filePath.lastIndexOf(".") + 1);
                String resultingFilePath;

                // Convert the file
                switch (extension) {
                    case "docx":
                        resultingFilePath = converterImplementation.convertWordToPdf(filePath);
                        break;
                    case "xlsx":
                        resultingFilePath = converterImplementation.convertExcelToPdf(filePath);
                        break;
                    case "pptx":
                        resultingFilePath = converterImplementation.convertPowerPointToPdf(filePath);
                        break;
                    case "png":
                        resultingFilePath = converterImplementation.convertImageToPdf(filePath);
                        break;
                    default:
                        resultingFilePath = null;
                        System.out.println(String.format("[THREAD %d] The file extension is not supported \uD83D\uDEAB", id));
                        break;
                }

                // Print the result
                if(resultingFilePath == null) continue;
                System.out.println(String.format("[THREAD %d] The thread finished the conversion \uD83D\uDC4C", id));
                System.out.println(resultingFilePath);
            }
        });

        // Run the thread
        worker.start();
    }
}
