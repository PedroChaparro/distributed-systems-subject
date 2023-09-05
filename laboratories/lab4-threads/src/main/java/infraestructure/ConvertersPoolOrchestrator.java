package infraestructure;

import domain.DocumentConverter;
import domain.Logger;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConvertersPoolOrchestrator {
    private Queue<String> pendingFiles;
    private Logger logger;

    public ConvertersPoolOrchestrator() {
        this.pendingFiles = new ConcurrentLinkedQueue<>();
        this.logger = StdoutLogger.getInstance();
    }

    public void enqueueFile(String file) {
        pendingFiles.add(file);
        synchronized (pendingFiles) {
            pendingFiles.notify();
        }

        logger.logInfo("ORCHESTRATOR", "A new file was added to the queue ⌚", true);
    }

    public void registerWorker(int id){
        // Create a new thread to listen for new files
        Thread worker = new Thread(() -> {
            // Create a new instance of a converter (worker)
            DocumentConverter converterImplementation = new JOfficeDocumentConverter();

            // Listening loop
            while (true) {
                logger.logInfo(
                        "THREAD " + id,
                        "The thread is waiting for a new file \uD83D\uDCA4",
                        true
                );

                synchronized (pendingFiles) {
                    while (pendingFiles.isEmpty()) {
                        try {
                            pendingFiles.wait();
                        } catch (InterruptedException e) {
                            logger.logError(
                                    "THREAD " + id,
                                    "The thread was interrupted while waiting the Queue \uD83D\uDCA5",
                                    true
                            );
                            e.printStackTrace();
                        }
                    }
                }

                // Get the url
                String filePath = pendingFiles.poll();
                logger.logInfo(
                        "THREAD " + id,
                        "The thread received a new file \uD83D\uDC77",
                        true
                );
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
                        logger.logError(
                                "THREAD " + id,
                                "The file extension is not supported \uD83D\uDEAB",
                                false
                        );
                        break;
                }

                // Print the result
                if(resultingFilePath == null) continue;
                logger.logSuccess(
                        "THREAD " + id,
                        "The following file was converted successfully: " + resultingFilePath,
                        false
                );
            }
        });

        // Run the thread
        worker.start();
    }
}
