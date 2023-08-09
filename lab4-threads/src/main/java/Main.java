import domain.DocumentConverter;
import infraestructure.LibreOfficeDocumentConverter;
import io.github.cdimascio.dotenv.Dotenv;

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

        DocumentConverter converter =  new LibreOfficeDocumentConverter(libreOfficePath, outputDirectoryPath);
        String path = converter.convertWordToPdf(
                "/home/pacq/Downloads/lab-convert/input/word.docx"
        );
        System.out.println(path);
    }
}
