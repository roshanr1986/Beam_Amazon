package util;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import propertyReader.PropertyReader;

public class DriverFactory {
	
    private WebDriver driver;
    private static DriverFactory myObj = null;
    private String browser = "firefox";
    private String version = "firefox";
    private String URL = "";
    PropertyReader propertyReader = new PropertyReader();

    private DriverFactory() {
    	
    }

    public WebDriver getDriver() {	    	
        return this.driver;
    }

    private void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public static DriverFactory getInstance() {
        if(myObj == null) {
            myObj = new DriverFactory();
            return myObj;
        } else {
            return myObj;
        }
    }

    public void initializeDriver() throws Exception {
    	try {
            System.setProperty("webdriver.gecko.driver","drivers/geckodriver.exe");
            //drivers/geckodriver.exe
			//FirefoxProfile prof = new FirefoxProfile(new File(propertyReader.readPropertyFile().get("url")));
    		driver = new FirefoxDriver();
    		//driver.get(propertyReader.readPropertyFile().get("url"));
			driver.manage().timeouts().implicitlyWait(200, TimeUnit.SECONDS);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public void closeDriver(){
    	 getInstance().getDriver().close();
    }

    public void getURL(String url) {
        getInstance().getDriver().get(url);
    }

}
