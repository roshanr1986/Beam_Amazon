package commonLibrary.implementation;


import commonLibrary.contracts.ICheckbox;
import cucumber.api.Scenario;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public class checkBoxControls implements ICheckbox {

    private Scenario _scenario;

    public checkBoxControls(Scenario _scenario ) {
        this._scenario=_scenario;
    }

    public void changeCheckboxStatus(WebElement element, boolean status) throws Exception {
        try {
            if ((element.isSelected() && !status) || (!element.isSelected() && status)){
                element.click();
            }
        } catch (WebDriverException e) {
            throw e;
        }

    }

    public Boolean getCheckboxStatus(WebElement element) {
        Boolean status=null;
        try {
            if(element.isSelected()){
                status=true;
            } else if (!element.isSelected()){
                status=false;
            }
        } catch (NullPointerException ne) {
            System.out.println("The checkbox status is Null (Description - "+ne+")");
            throw ne;

        } catch (NoSuchElementException en) {
            System.out.println("The Element is not found (Description - "+en+")");
            throw en;

        } catch (WebDriverException we) {
            System.out.println("Webdriver Exception occurred (Description - "+we+")");
            throw we;
        }
        return status;
    }
}
