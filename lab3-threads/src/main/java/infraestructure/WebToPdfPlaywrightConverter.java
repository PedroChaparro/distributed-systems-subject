package infraestructure;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import domain.WebToPdfConverter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class WebToPdfPlaywrightConverter implements WebToPdfConverter {
    private BrowserType.LaunchOptions browserOptions;
    private String outputDirectoryPath;

    public WebToPdfPlaywrightConverter(String outputDirectoryPath) {
        this.browserOptions = new BrowserType.LaunchOptions().setHeadless(false);
        this.outputDirectoryPath = outputDirectoryPath;
    }

    @Override
    public String convert(String url, String name) {
        try(Playwright playwright = Playwright.create()) {
            // Launch a new browser instance
            Browser browser = playwright.chromium().launch(browserOptions);
            Page page = browser.newPage();

            // Go to the requested page
            page.navigate(url);

            // Download the page as a pdf file
            byte[] pageByes = page.pdf();
            File pdf = new File(this.outputDirectoryPath + name + ".pdf");
            OutputStream os = new FileOutputStream(pdf);
            os.write(pageByes);
            os.close();

            // Return the path of the saved file
            return pdf.getPath();
        }catch (Exception e){
            System.out.println("[CONVERTER] Something went wrong");
            System.out.println(e);
            return null;
        }
    }
}
