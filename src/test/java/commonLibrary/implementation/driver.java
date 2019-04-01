package commonLibrary.implementation;

import commonLibrary.contracts.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class driver implements Driver {
    private WebDriver driver;

    public driver(WebDriver driver) {
        this.driver = driver;
    }


    @Override
    public void openBrowserAndGetURL(String url) throws Exception {
        driver.get(url);
    }

    @Override
    public String getTitle() throws Exception {
        return null;
    }

    @Override
    public String getCurrentURL() throws Exception {
        return null;
    }

    @Override
    public String getPageSource() throws Exception {
        return null;
    }

    @Override
    public void navigateToURL(String url) throws Exception {

    }

    @Override
    public void navigateToForward() throws Exception {

    }

    @Override
    public void navigateToBackward() throws Exception {

    }

    @Override
    public void refresh() throws Exception {

    }

    @Override
    public void closeBrowser() throws Exception {

    }

    @Override
    public void closeAllBrowsers() throws Exception {

    }

    @Override
    public void waitForElementPresent(WebDriver driver, WebElement element) throws Exception {

    }
}
