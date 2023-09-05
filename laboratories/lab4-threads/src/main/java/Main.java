import domain.Logger;
import infraestructure.ConvertersPoolOrchestrator;
import infraestructure.StdoutLogger;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Logger logger = StdoutLogger.getInstance();

        // Configure the environment variables
        Dotenv dotenv = Dotenv.configure().load();
        String outputDirectoryPath = dotenv.get("OUTPUT_DIRECTORY");
        String libreOfficePath = dotenv.get("SOFFICE_PATH");
        String fallbackFontPath = dotenv.get("FALLBACK_FONT_PATH");

        if (outputDirectoryPath == null) {
            logger.logError("MAIN", "The OUTPUT_DIRECTORY environment variable is not set", false);
            System.exit(1);
        }

        if (libreOfficePath == null) {
            logger.logError("MAIN", "The SOFFICE_PATH environment variable is not set", false);
            System.exit(1);
        }

        if (fallbackFontPath == null) {
            logger.logError("MAIN", "The FALLBACK_FONT_PATH environment variable is not set", false);
            System.exit(1);
        }

        // Receive the files to convert
        List<String> files = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        while (true) {
            String input = sc.nextLine();
            if(input.trim().toUpperCase().equals("EXIT")) break;
            files.add(input);
        }

        // Create an instance of the orchestrator
        ConvertersPoolOrchestrator orchestrator = new ConvertersPoolOrchestrator();

        // Register 4 workers
        for (int i = 0; i < 4; i++) {
            orchestrator.registerWorker(i + 1);
        }

        // Convert
        for (String filePath : files) {
            orchestrator.enqueueFile(filePath);
        }
    }
}
