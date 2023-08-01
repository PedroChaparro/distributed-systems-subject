import application.ConverterUseCases;
import domain.WebToPdfConverter;
import infraestructure.WebToPdfPlaywrightConverter;
import io.github.cdimascio.dotenv.Dotenv;

public class Main {
    public static void main(String[] args) {
        // Configure the environment variables
        Dotenv dotenv = Dotenv.configure().load();
        String outputDirectoryPath = dotenv.get("FILES_DIRECTORY");
        if(outputDirectoryPath == null) {
            System.out.println("[ERROR] The FILES_DIRECTORY environment variable is not set");
            System.exit(1);
        }

        // Create and use the service
        WebToPdfConverter converterImplementation = new WebToPdfPlaywrightConverter(outputDirectoryPath);
        ConverterUseCases service = new ConverterUseCases(converterImplementation);
        String pdfPath = service.Convert("https://playwright.dev/java/docs/intro", "playwright");
    }
}
