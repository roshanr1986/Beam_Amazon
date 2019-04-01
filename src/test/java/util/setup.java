package util;

import commonLibrary.implementation.driver;
import org.openqa.selenium.WebDriver;

public class setup {
    private WebDriver driver;
    private commonLibrary.implementation.driver driverSetup;

    public setup(WebDriver driver) {
        this.driver = driver;
        driverSetup=new driver(driver);

    }

    public void setURL(String url) throws Exception {
        driverSetup.openBrowserAndGetURL(url);
    }

}
