package infraestructure;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import domain.WebToPdfConverter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.concurrent.CompletableFuture;

public class WebToPdfPlaywrightConverter implements WebToPdfConverter {
    private BrowserType.LaunchOptions options;
    private String outputDirectoryPath;

    public WebToPdfPlaywrightConverter(String outputDirectoryPath) {
        this.outputDirectoryPath = outputDirectoryPath;
        this.options = new BrowserType.LaunchOptions().setHeadless(true);
    }

    @Override
    public String convert(String url, String name) {
        try (Playwright playwright = Playwright.create()){
            // Open a browser
            Browser browser = playwright.chromium().launch(this.options);

            // Go to the requested page
            Page page = browser.newPage();
            page.navigate(url);

            // Download the page as a pdf file
            byte[] pageByes = page.pdf();
            File pdf = new File(this.outputDirectoryPath + name + ".pdf");
            OutputStream os = new FileOutputStream(pdf);
            os.write(pageByes);
            os.close();

            // Close the page
            page.close();

            // Return the path of the saved file
            return pdf.getPath();
        }catch (Exception e){
            System.out.println("[CONVERTER] An error occurred when converting the url");
            System.out.println(e);
            return null;
        }
    }
}
