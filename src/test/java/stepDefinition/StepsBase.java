package stepDefinition;

import java.util.HashMap;
import java.util.Map;

import cucumber.api.Scenario;
//import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.testng.Assert;
import pages.AddToCartPage;
import pages.HomePage;
import pages.ProductPage;
import propertyReader.PropertyReader;
import util.DriverFactory;
import util.TestAssert;

public class StepsBase {
	public WebDriver driver;
	public Scenario _scenario;
	private HomePage homePage;
	private ProductPage productPage;
	private AddToCartPage addToCartPage;
	public PropertyReader propertyReader;
	public Map <String,String> productDetails;

	public StepsBase() {
		this.driver = Hooks.driver;
		this._scenario = Hooks._scenario;
		homePage=new HomePage(driver,_scenario);
		productPage=new ProductPage(driver,_scenario);

		propertyReader = new PropertyReader();
		productDetails=new HashMap<String,String>();
	}


//	@Before
//    public void before() throws Exception {
//		DriverFactory.getInstance().initializeDriver();
//		homePage = PageFactory.initElements(DriverFactory.getInstance().getDriver(), HomePage.class);
//	}
	
	@Given("^Web user navigate to the URL$")
	public void navigateURL() throws Throwable {
		String webUrl = propertyReader.readPropertyFile().get("url");
		//DriverFactory.getInstance().getDriver().get(webUrl);
		DriverFactory.getInstance().getURL(webUrl);
	}
	
	@When("^Web user check for the page header$")
	public void checkPageHeaderIsLoaded() throws Throwable {
	    if (homePage.checkHomePageLoaded()) {
	    	Assert.assertTrue(homePage.checkHomePageLoaded());
		}
	}

	@Then("^Web user validate the page header is \"(.*)\"$")
	public void validatePageHeader(String pageHeader) throws Throwable {
		//TestAssert.assertContains("validate the page header", pageHeader, homePage.getAmazonLogoString());
		System.out.println("Actual Title : "+homePage.getAmazonPageTitle());
		System.out.println("Expected Title : "+pageHeader);

		Assert.assertTrue(homePage.getAmazonPageTitle().contains(pageHeader));

	}
	
	@When("^Web user type incorrect user name \"(.*)\" and password \"(.*)\"$")
	public void failedLoginWithIncorrectUsername(String username, String password) throws Throwable {
		homePage.loginIntoAmazon(username, password);
	}
	
	@Then("^The error message should be \"(.*)\"$")
	public void validateError(String errorMessage) throws Throwable {
		String actualErrorMessage = homePage.getLoginErrorMessage();
		TestAssert.assertEquals("Validate Error message", errorMessage, actualErrorMessage);
	}
	
	@When("^Web user type valid user name \"(.*)\" and password \"(.*)\"$")
	public void failedLoginWithIncorrectPassword(String username, String password) throws Throwable {
		homePage.loginIntoAmazon(username, password);
	}
	
	@Then("^Web user validate the home page header \"(.*)\"$")
	public void validateHomePageHeaderAfterLogin(String pageHeader) throws Throwable {
		TestAssert.assertContains("validate the page header after the successfull", pageHeader, homePage.getAmazonPageTitle());
	}
	
	@When("^Web user search for \"(.*)\"$")
	public void searchItemName(String itemName){
		try {
			homePage.searchItem(itemName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@When("^Web user click on the first product visible$")
	public void clickOnFirstItem()throws Throwable {
		//productPage = homePage.clickFirstItemInVisible();
		homePage.clickFirstItemInVisible();
	}
	
	@Then("^product page should be visible$")
	public void validateTheProductPage() {
		boolean pageLoadedValue = productPage.checkProductPageLoaded();
		TestAssert.assertTrue(pageLoadedValue);
	}
	
	@When("^product price is captured as \"(.*)\"$")
	public void captureProdPrice(String key)throws Throwable {
		String price = productPage.getProductPrice();
		productDetails.put(key, price);
	}
	
	@When("^product name is captured as \"(.*)\"$")
	public void captureProductName(String key)throws Throwable {
		String productName = productPage.getProductName();
		productDetails.put(key, productName);
	}
	
	@When("^Web user add \"(.*)\" qualitity to the basket$")
	public void addProductToTheCart(String noOfItems)throws Throwable {
		//addToCartPage = productPage.addItemToTheCart(noOfItems);
		productPage.addItemToTheCart(noOfItems);
	}
	
	@Then("^Message should be \"(.*)\"$")
	public void validateTheProductPageValidateMessage(String message) throws Exception {
		String actualMessage = productPage.readAddToCartMessage();
		TestAssert.assertContains("Validate Add To Cart message", message, actualMessage);
	}
	
	@Then("^\"(.*)\" should be in the cart$")
	public void validateItemCount(String count) throws Exception {
		String itemCount = addToCartPage.getItemCountInTheCart();
		TestAssert.assertContains("Validate item count in the cart", count, itemCount);
	}
	
	@Then("^the product name should be \"(.*)\"$")
	public void validateProductName(String name) throws Exception {
		
	}
	
	@Then("^the product price should be \"(.*)\"$")
	public void validateProductPrice(String productPriceValue) throws Exception {
		String itemPrize = addToCartPage.getItemPrice();
		TestAssert.assertContains("Validate item price in the cart", productDetails.get(productPriceValue), itemPrize);
	}
	
	@When("^Web user logout from the system$")
	public void logoutFromAmazon() throws InterruptedException {
		//homePage = addToCartPage.logoutFromAddToCartPage();
		addToCartPage.logoutFromAddToCartPage();
	}
	
	@When("^Web user click on the cart$")
	public void clickTheCart() throws Throwable {
		//addToCartPage = homePage.clickOnTheCart();
		homePage.clickOnTheCart();
	}
	
	@Then("^the product price should be in the cart \"(.*)\"$")
	public void validateItemPriceInTheCart(String productPriceValue) throws Exception{
		String cartPrice = addToCartPage.getCartItemPrice();
		TestAssert.assertContains("Validate item price in the cart", productDetails.get(productPriceValue), cartPrice);
	}
	
	
	
//	@After
//	public void after() {
//		DriverFactory.getInstance().closeDriver();
//
//	}
	
	
}
