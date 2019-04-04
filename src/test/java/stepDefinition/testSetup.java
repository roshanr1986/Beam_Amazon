package stepDefinition;

import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.CartPage;

public class testSetup  {

    private WebDriver driver;
    public Scenario _scenario;
    public CartPage cartPage;


    public testSetup() {
        this.driver = Hooks.driver;
        this._scenario=Hooks._scenario;
        cartPage = new CartPage(driver,_scenario);
    }

    @Given("^I setup cart before test$")
    public void iSetupCartBeforeTest() throws Throwable {

        try {
            System.out.println("=================== STARTING TEST SETUP =====================");
            cartPage.setupCartBeforeAndAfterTest();
            System.out.println("============= TEST SETUP COMPLETED SUCCESSFULLY =============");
        } catch (Exception e) {
            System.out.println("UNABLE TO COMPLETE TEST SETUP DUE TO AN EXCEPTION"+"\n"+"Following Exception found -- "+"\n"+e.getMessage());
           _scenario.write("UNABLE TO COMPLETE TEST SETUP DUE TO AN EXCEPTION"+"\n"+"Following Exception found -- "+"\n"+e.getMessage());
            Assert.fail("UNABLE TO COMPLETE TEST SETUP DUE TO AN EXCEPTION"+"\n"+"Following Exception found -- "+"\n"+e.getMessage());
        }
    }

    @Then("^I clear cart after test$")
    public void iClearCartAfterTest() throws Throwable {
        try {
            System.out.println("=================== STARTING TEST TEARDOWN =====================");
            cartPage.setupCartBeforeAndAfterTest();
            System.out.println("============= TEST TEARDOWN COMPLETED SUCCESSFULLY =============");
        } catch (Exception e) {
            System.out.println("UNABLE TO COMPLETE TEST TEARDOWN DUE TO AN EXCEPTION"+"\n"+"Following Exception found -- "+"\n"+e.getMessage());
            _scenario.write("UNABLE TO COMPLETE TEST TEARDOWN DUE TO AN EXCEPTION"+"\n"+"Following Exception found -- "+"\n"+e.getMessage());
            Assert.fail("UNABLE TO COMPLETE TEST TEARDOWN DUE TO AN EXCEPTION"+"\n"+"Following Exception found -- "+"\n"+e.getMessage());
        }
    }
}
