package pages;

import commonLibrary.implementation.CommonElements;
import commonLibrary.implementation.selectBoxControls;
import commonLibrary.implementation.textBoxControls;
import cucumber.api.Scenario;
import gherkin.lexer.Th;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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
	public selectBoxControls selectBox;
	
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

	@FindBy(how = How.ID, using = "searchDropdownBox")
	private WebElement selectSearchCategory;

	@FindBy(how = How.XPATH, using = "//span[@class='nav-a-content' and contains(text(), 'Amazon Charts')]")
	private WebElement amazonChartsLink;

	@FindBy(how = How.XPATH, using = "//div[@class='kc-tabs-label kc-active-tab' and contains(text(), 'MOST READ')]")
	private WebElement mostReadCategoryTabSelected;

	@FindBy(how = How.XPATH, using = "//div[@class='kc-tabs-label' and contains(text(), 'MOST READ')]")
	private WebElement mostReadCategoryTabNotSelected;

	@FindBy(how = How.XPATH, using = "//div[@class='kc-tabs-label kc-active-tab' and contains(text(), 'MOST SOLD')]")
	private WebElement mostSoldCategoryTabSelected;

	@FindBy(how = How.XPATH, using = "//div[@class='kc-tabs-label' and contains(text(), 'MOST SOLD')]")
	private WebElement mostSoldCategoryTabNotSelected;

	@FindBy(how = How.XPATH, using = "//div[@class='kc-fiction kc-sublist-part-left kc-sublist-part-active']")
	private WebElement fictionTabSelected;

	@FindBy(how = How.XPATH, using = "//a[@class='kc-fiction kc-sublist-part-left']")
	private WebElement fictionTabNotSelected;

	@FindBy(how = How.XPATH, using = "//div[@class='kc-nonfiction kc-sublist-part-right kc-sublist-part-active']")
	private WebElement nonFictionTabSelected;

	@FindBy(how = How.XPATH, using = "//a[@class='kc-nonfiction kc-sublist-part-right']")
	private WebElement nonFictionTabNotSelected;


	//div[@class='kc-tabs-label kc-active-tab' and contains(text(), 'MOST READ')]

	//Constructor class
	public HomePage(WebDriver driver, Scenario _scenario) {
		this.driver=driver;
		this._scenario=_scenario;
		PageFactory.initElements(driver, this);
		comElement = new CommonElements(driver,_scenario);
		textBox = new textBoxControls(_scenario);
		selectBox = new selectBoxControls(driver,_scenario);
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
		clickSearchIcon();
		//clickElement(searchBox_button);
	}

	public void clickSearchIcon() throws Exception {
		comElement.click(searchBox_button);
	}

	public void selectSearchCategory(String categoryText) throws Exception {
		selectBox.selectByVisibleText(selectSearchCategory,categoryText);
	}

	public void navigateToAmazonChartsLink() throws Exception {
		comElement.click(amazonChartsLink);
	}

	public boolean isMostReadTabSelected() throws Exception {
		return comElement.isElementVisible(mostReadCategoryTabSelected);
	}

	public void selectMostReadTab() throws Exception {
		if(mostReadCategoryTabSelected.isDisplayed()){
			System.out.println("Most read tab is already selected");
		} else {
			comElement.click(mostReadCategoryTabNotSelected);
			System.out.println("Clicked on Most read tab");
		}
	}

	public void selectMostSoldTab() throws Exception{
		if(mostSoldCategoryTabNotSelected.isDisplayed()){
			comElement.click(mostSoldCategoryTabNotSelected);
			System.out.println("Clicked on Most Sold tab");
		} else {
			System.out.println("Most Sold tab is already selected");
		}
	}

	public void selectFictionTab() throws Exception {
		if(fictionTabSelected.isDisplayed()){
			System.out.println("'Fiction' tab is already selected");
		} else {
			comElement.click(fictionTabNotSelected);
			System.out.println("Clicked on 'Fiction' tab");
		}
	}

	public void selectNonFictionTab() throws Exception {
		if((nonFictionTabNotSelected.isDisplayed())){
			comElement.click(nonFictionTabNotSelected);
			System.out.println("Clicked on 'NonFiction' tab");
		} else {
			System.out.println("'NonFiction' tab is already selected");
		}
	}


	public String getTheTitleforGivenPosition(String position) throws Exception {
		WebElement element = null;
		if(position.equalsIgnoreCase("1") || position.equalsIgnoreCase("2") || position.equalsIgnoreCase("3")) {
			try {
				Thread.sleep(5000);
				element = driver.findElement(By.xpath("//div[@class='kc-rank-card tablet-hide kc-four-columns' and @data-rank='" + position + "']//div[@class='kc-rank-card-metadata']//div[@class='kc-rank-card-title']"));
			} catch (NoSuchElementException e) {
				System.out.println("Unable to find the element for the position - " + position);
				throw e;
			}
		}else {
			try {
				Thread.sleep(5000);
				element = driver.findElement(By.xpath("//div[@id='rank"+position+"']//div[@class='kc-rank-card-metadata']//div[@class='kc-rank-card-title']"));
			} catch (NoSuchElementException e) {
				System.out.println("Unable to find the element for the position - " + position);
				throw e;
			}

		}

		return comElement.getText(element);
	}
	
	public void clickFirstItemInVisible() {
		clickElement(firstElementOnPresent);
		try {
			waitForElement(addToCart_button);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void clickOnTheCart() {
		clickElement(clickCartButton);
		//addToCartPage = PageFactory.initElements(DriverFactory.getInstance().getDriver(), AddToCartPage.class);
		//return addToCartPage;
	}

	
}



