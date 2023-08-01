import domain.Site;
import infraestructure.ConvertersPoolOrchestrator;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.List;

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
        for(int i = 0; i < 8; i++) {
            orchestrator.registerWorker(i + 1);
        }

        // Add some work
        List<Site> defaultTargets = List.of(
                new Site("https://www.ruby-lang.org/en/", "Ruby"),
                new Site("https://www.rust-lang.org/", "Rust"),
                new Site("https://go.dev/", "Go"),
                new Site("https://www.python.org/", "Python"),
                new Site("https://www.typescriptlang.org/", "TypeScript"),
                new Site("https://www.haskell.org/", "Haskell"),
                new Site("https://www.scala-lang.org/", "Scala"),
                new Site("https://kotlinlang.org/", "Kotlin")
        );

        // Download each target 4 times
        for(int i = 0; i < 4; i++) {
            for(Site s : defaultTargets) {
                Site numberedSite = new Site(s.getUrl(), s.getName() + " " + (i + 1));
                orchestrator.enqueueSite(numberedSite);
            }
        }
    }
}
