package pages;

import commonLibrary.implementation.CommonElements;
import commonLibrary.implementation.textBoxControls;
import cucumber.api.Scenario;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import core.PageObject;
import util.DriverFactory;

public class HomePage extends PageObject{
	
	public ProductPage productPage;
	public AddToCartPage addToCartPage;
	public WebDriver driver;
	public Scenario _scenario;
	public CommonElements comElement;
	public textBoxControls textBox;
	
	@FindBy(how=How.ID, using="nav-logo")
	private WebElement amazonLogo;
	
	@FindBy(how=How.TAG_NAME, using ="title")
	public WebElement amazonLogoText;
	
	@FindBy(how=How.ID, using="nav-link-accountList")
	private WebElement amazonLoginLink;
	
	@FindBy(how=How.ID, using="ap_email")
	private WebElement loginEmail_textBox;
	
	@FindBy(how=How.ID, using="signInSubmit")
	private WebElement loginEmailSignIn_button;
	
	@FindBy(how=How.XPATH, using="//*[@id=\"auth-error-message-box\"]/div/div/ul/li")
	private WebElement loginError_text;
	
	@FindBy(how=How.ID, using="ap_password")
	private WebElement loginEmailPassword_textBox;
	
	@FindBy(how=How.ID, using="signInSubmit")
	private WebElement signInSubmit_button;
	
	@FindBy(how=How.ID, using="twotabsearchtextbox")
	private WebElement tabSearchTextBox_textbox;
	
	@FindBy(how=How.XPATH, using="//input[@type='submit']")
	private WebElement searchBox_button;
	
	@FindBy(how=How.XPATH, using="//img[@class='s-access-image cfMarker']")
	private WebElement firstElementOnPresent;
	
	@FindBy(how=How.ID, using="add-to-cart-button")
	private WebElement addToCart_button;
	
	@FindBy(how=How.ID, using="nav-cart")
	private WebElement clickCartButton;

	//Constructor class
	public HomePage(WebDriver driver, Scenario _scenario) {
		this.driver=driver;
		this._scenario=_scenario;
		PageFactory.initElements(driver, this);
		comElement = new CommonElements(driver,_scenario);
		textBox = new textBoxControls(_scenario);
	}


	public boolean checkHomePageLoaded(){
		return isPageLoaded(amazonLogo);
	}

	public String getAmazonPageTitle() {
		return driver.getTitle();
	}

	public void loginIntoAmazonInvalidUsername(String username, String password) {
		waitElementToBeClickable(amazonLoginLink);
		clickElement(amazonLoginLink);
		waitElementToBeClickable(loginEmail_textBox);
		setText(loginEmail_textBox, username);
		setText(loginEmailPassword_textBox,password);
		clickElement(loginEmailSignIn_button);
	}
	
	public void loginIntoAmazon(String username, String password) {
		waitElementToBeClickable(amazonLoginLink);
		clickElement(amazonLoginLink);
		waitElementToBeClickable(loginEmail_textBox);
		setText(loginEmail_textBox, username);
		clickElement(loginEmailSignIn_button);
		waitElementToBeClickable(signInSubmit_button);
		setText(loginEmailPassword_textBox, password);
		clickElement(signInSubmit_button);		
	}
	
	public String getLoginErrorMessage() {
		waitElementToBeClickable(loginError_text);
		return loginError_text.getText();
	}
	
	public void searchItem(String itemName) throws Exception {
		comElement.waitElementToBeClickable(tabSearchTextBox_textbox);
		//waitElementToBeClickable(tabSearchTextBox_textbox);
		textBox.clearTextBox(tabSearchTextBox_textbox);
		textBox.setText(tabSearchTextBox_textbox,itemName);
		//setText(tabSearchTextBox_textbox, itemName);
		comElement.click(searchBox_button);
		//clickElement(searchBox_button);
	}
	
	public void clickFirstItemInVisible() {
		clickElement(firstElementOnPresent);
		try {
			waitForElement(addToCart_button);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//productPage = PageFactory.initElements(DriverFactory.getInstance().getDriver(), ProductPage.class);

	}
	
	public void clickOnTheCart() {
		clickElement(clickCartButton);
		//addToCartPage = PageFactory.initElements(DriverFactory.getInstance().getDriver(), AddToCartPage.class);
		//return addToCartPage;
	}
	
}



