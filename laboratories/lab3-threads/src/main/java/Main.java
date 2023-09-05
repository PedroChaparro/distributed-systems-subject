import domain.Site;
import infraestructure.ConvertersPoolOrchestrator;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Configure the environment variables
        Dotenv dotenv = Dotenv.configure().load();
        String outputDirectoryPath = dotenv.get("FILES_DIRECTORY");
        if(outputDirectoryPath == null) {
            System.out.println("[MAIN] The FILES_DIRECTORY environment variable is not set");
            System.exit(1);
        }

        // Create an instance of the orchestrator
        ConvertersPoolOrchestrator orchestrator = new ConvertersPoolOrchestrator(outputDirectoryPath);

        // Register 4 workers
        for(int i = 0; i < 4; i++) {
            orchestrator.registerWorker(i + 1);
        }

        // Receive the sites to convert
        List<Site> targets = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("[LISTENING] Enter the URL of the site to be converted to PDF: ");
            String url = sc.nextLine();

            System.out.println("[LISTENING] Enter the name of the site to be converted to PDF: ");
            String name = sc.nextLine();

            Site s = new Site(url, name);
            targets.add(s);

            System.out.println("[LISTENING] Do you want to add another site (Y/N): ");
            String ans = sc.nextLine();
            if(ans.trim().toUpperCase() != "Y") break;
        }

        // Convert
        for(Site s: targets){
            orchestrator.enqueueSite(s);
        }
    }
}
