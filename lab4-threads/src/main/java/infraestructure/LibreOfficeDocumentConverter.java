package infraestructure;

import domain.DocumentConverter;

// import java.io.BufferedReader;
// import java.io.InputStreamReader;

public class LibreOfficeDocumentConverter implements DocumentConverter {
    private String sharedCommand;
    private String outputDirectoryPath;

    public LibreOfficeDocumentConverter(String libreOfficePath, String outputDirectory) {
        this.sharedCommand = String.format(
                "%s --headless --convert-to pdf:writer_pdf_Export --outdir \"%s\"",
                libreOfficePath,
                outputDirectory
        );

        this.outputDirectoryPath = outputDirectory;
    }

    private String convert(String sourcePath) {
        try {
            // Builder to create the process
            ProcessBuilder processBuilder = new ProcessBuilder();
            String os = System.getProperty("os.name").toLowerCase();

            // Use the shell according to the OS (Tested on linux)
            if (os.contains("win")) {
                processBuilder.command("cmd.exe", "/c", String.format("%s \"%s\"", this.sharedCommand, sourcePath));
            } else {
                processBuilder.command("sh", "-c", String.format("%s \"%s\"", this.sharedCommand, sourcePath));
            }

            // Execute the process
            Process process = processBuilder.start();

            // Read the output of the process
            /*
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );
            String outputLine;

            while ((outputLine = reader.readLine()) != null) {
                output.append(outputLine).append("\n");
            }
            */

            // Wait for the process to finish
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.out.println("[CONVERTER] An error occurred when converting the document");
                // System.out.println(output);
                return null;
            }

            // Return the path of the saved file
            String fileName = sourcePath.substring(sourcePath.lastIndexOf("/") + 1);
            fileName = fileName.replaceAll("\\.[^.]*$", ".pdf");
            return this.outputDirectoryPath + fileName;
        } catch (Exception e) {
            System.out.println("[CONVERTER] An error occurred when converting the document");
            System.out.println(e);
            return null;
        }
    }

    @Override
    public String convertWordToPdf(String sourcePath) {
        return this.convert(sourcePath);
    }

    @Override
    public String convertPowerPointToPdf(String sourcePath) {
        return this.convert(sourcePath);
    }

    @Override
    public String convertExcelToPdf(String sourcePath) {
        return this.convert(sourcePath);
    }

    @Override
    public String convertImageToPdf(String sourcePath) {
        return this.convert(sourcePath);
    }
}
