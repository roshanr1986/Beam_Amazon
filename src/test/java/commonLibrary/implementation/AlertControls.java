package commonLibrary.implementation;

import commonLibrary.contracts.IAlertHandling;
import org.openqa.selenium.WebDriver;

public class  AlertControls implements IAlertHandling {

        private WebDriver driver;

        public AlertControls(WebDriver driver){
            this.driver=driver;
        }

        public void acceptAlert() throws Exception {
            driver.switchTo().alert().accept();
        }

        public void rejectAlert() throws Exception {
            driver.switchTo().alert().dismiss();
        }

        public String getMessageOfAlert() throws Exception {
            return driver.switchTo().alert().getText();
        }

        public boolean isAlertPresent(int timeoutInSeconds) throws Exception {
            return false;
        }

}
