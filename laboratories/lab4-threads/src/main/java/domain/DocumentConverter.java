package domain;

public interface DocumentConverter {
    String convertWordToPdf(String sourcePath);
    String convertPowerPointToPdf(String sourcePath);
    String convertExcelToPdf(String sourcePath);
    String convertImageToPdf(String sourcePath);
}
