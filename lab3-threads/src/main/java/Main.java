import domain.Site;
import infraestructure.ConvertersPoolOrchestrator;
import io.github.cdimascio.dotenv.Dotenv;

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
        orchestrator.enqueueSite(new Site("https://www.ruby-lang.org/en/", "ruby"));
        orchestrator.enqueueSite(new Site("https://www.rust-lang.org/", "rust"));
        orchestrator.enqueueSite(new Site("https://go.dev/", "go"));
        orchestrator.enqueueSite(new Site("https://www.python.org/", "python"));
        orchestrator.enqueueSite(new Site("https://www.typescriptlang.org/", "typescript"));
        orchestrator.enqueueSite(new Site("https://www.haskell.org/", "haskell"));
        orchestrator.enqueueSite(new Site("https://www.scala-lang.org/", "scala"));
        orchestrator.enqueueSite(new Site("https://www.php.net/", "php"));
        orchestrator.enqueueSite(new Site("https://kotlinlang.org/", "kotlin"));
    }
}
