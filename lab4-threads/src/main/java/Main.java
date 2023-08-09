import domain.DocumentConverter;
import infraestructure.ConvertersPoolOrchestrator;
import infraestructure.LibreOfficeDocumentConverter;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Configure the environment variables
        Dotenv dotenv = Dotenv.configure().load();
        String outputDirectoryPath = dotenv.get("OUTPUT_DIRECTORY");
        String libreOfficePath = dotenv.get("SOFFICE_PATH");

        if (outputDirectoryPath == null) {
            System.out.println("[MAIN] The OUTPUT_DIRECTORY environment variable is not set");
            System.exit(1);
        }

        if (libreOfficePath == null) {
            System.out.println("[MAIN] The SOFFICE_PATH environment variable is not set");
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
