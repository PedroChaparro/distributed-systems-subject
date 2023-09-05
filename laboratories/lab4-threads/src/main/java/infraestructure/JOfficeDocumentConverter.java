package infraestructure;

import com.qoppa.office.*;
import domain.DocumentConverter;
import domain.Logger;
import io.github.cdimascio.dotenv.Dotenv;

public class JOfficeDocumentConverter implements DocumentConverter {
    private String outputDirectoryPath;
    private Dotenv dotenv;
    private Logger logger;

    public JOfficeDocumentConverter() {
        this.dotenv = Dotenv.load();
        this.logger = StdoutLogger.getInstance();
        this.outputDirectoryPath = dotenv.get("OUTPUT_DIRECTORY");
    }

    private String handleException(Exception e, String documentName){
        logger.logError(
                "CONVERTER",
                "An error occurred when converting the document " + documentName,
                true
        );
        System.out.println(e);

        return null;
    }

    @Override
    public String convertWordToPdf(String sourcePath) {
        try {
            String documentName = sourcePath.substring(sourcePath.lastIndexOf("/") + 1);
            WordConvertOptions options = new WordConvertOptions();
            options.setFallbackFontPath(this.dotenv.get("FALLBACK_FONT_PATH"));

            WordDocument word = new WordDocument(sourcePath, options);
            String outputFilePath = this.outputDirectoryPath + documentName.replaceAll(
                    "\\.[^.]*$",
                    ".pdf"
            );

            word.saveAsPDF(outputFilePath);
            return outputFilePath;
        }catch(Exception e){
            return handleException(e, sourcePath.substring(sourcePath.lastIndexOf("/") + 1));
        }
    }

    @Override
    public String convertPowerPointToPdf(String sourcePath) {
        try {
            String documentName = sourcePath.substring(sourcePath.lastIndexOf("/") + 1);
            PowerPointConvertOptions options = new PowerPointConvertOptions();
            options.setFallbackFontPath(this.dotenv.get("FALLBACK_FONT_PATH"));

            PowerPointDocument ppt = new PowerPointDocument(sourcePath, options);
            String outputFilePath = this.outputDirectoryPath + documentName.replaceAll(
                    "\\.[^.]*$",
                    ".pdf"
            );

            ppt.saveAsPDF(outputFilePath);
            return outputFilePath;
        }catch(Exception e){
            return handleException(e, sourcePath.substring(sourcePath.lastIndexOf("/") + 1));
        }
    }

    @Override
    public String convertExcelToPdf(String sourcePath) {
        try {
            String documentName = sourcePath.substring(sourcePath.lastIndexOf("/") + 1);
            ExcelConvertOptions options = new ExcelConvertOptions();
            options.setFallbackFontPath(this.dotenv.get("FALLBACK_FONT_PATH"));

            ExcelDocument excel = new ExcelDocument(sourcePath, options);
            String outputFilePath = this.outputDirectoryPath + documentName.replaceAll(
                    "\\.[^.]*$",
                    ".pdf"
            );

            excel.saveAsPDF(outputFilePath);
            return outputFilePath;
        }catch(Exception e){
            return handleException(e, sourcePath.substring(sourcePath.lastIndexOf("/") + 1));
        }
    }

    @Override
    public String convertImageToPdf(String sourcePath) {
        throw new UnsupportedOperationException("Images convertion is not supported by the JOffice library");
    }
}
