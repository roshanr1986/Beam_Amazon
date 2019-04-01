package core;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import util.DriverFactory;

public class PageObject {
	
	public boolean isPageLoaded(WebElement web_Element){
		return web_Element.isDisplayed();
	}
	
	public void clickElement(WebElement web_Element){
		web_Element.click();
	}
	
	public void setText(WebElement web_Element, String text){
		web_Element.sendKeys(text);
	}
	
	public void waitForElement(WebElement element) throws Exception {
        try {
            waitElementToBeClickable(element);
        } catch (WebDriverException e) {
            e.printStackTrace();
        }
    }
	public WebElement waitElementToBeClickable(WebElement element) {
        WebDriverWait wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), 150);
		WebElement elementFound = (WebElement) wait.until(ExpectedConditions.elementToBeClickable(element));
        return elementFound;
    }

}
