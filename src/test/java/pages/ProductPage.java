package pages;

import commonLibrary.implementation.CommonElements;
import commonLibrary.implementation.selectBoxControls;
import cucumber.api.Scenario;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import core.PageObject;
import util.DriverFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class ProductPage extends PageObject{

	public WebDriver driver;
	public Scenario _scenario;
	private AddToCartPage addToCartPage;
	private CommonElements comElemet;
	private selectBoxControls selectBox;

	@FindBy(how=How.ID, using="add-to-cart-button")
	private WebElement addToCart_button;
	
	@FindBy(how=How.ID, using="priceblock_ourprice")
	private WebElement productPrice;
	
	@FindBy(how=How.ID, using="productTitle")
	private WebElement productTitle;
	
	@FindBy(how=How.ID, using="quantity")
	private WebElement productQuantity;
	
	@FindBy(how=How.ID, using="huc-v2-order-row-confirm-text")
	private WebElement addToCartMessage;

	@FindBy(how = How.XPATH, using = "//div[@class='s-result-list sg-row']//div[@class='s-include-content-margin s-border-bottom']//span[@class='a-size-medium a-color-base a-text-normal']")
	private List<WebElement> productList;

	@FindBy(how = How.XPATH,using = "//div[@class='s-result-list sg-row']//div[@class='sg-row']//div[@class='sg-col-inner']//h5[@class='a-color-base s-line-clamp-4']//a//span")
    private List<WebElement> productListGridView;

	@FindBy(how=How.NAME, using = "quantity")
	private WebElement quantitySelectDropdown;

	private By quantitySelect =  By.name("quantity");

	@FindBy(how=How.ID, using = "add-to-cart-button")
	private WebElement addToCartBtn;

	@FindBy(how=How.XPATH, using = "//a[text()='View Cart']//parent::span")
	private WebElement viewCartBtn;

	@FindBy(how=How.XPATH, using = "//div[@class='sc-list-item-content']")
    private List<WebElement> itemsInTheCart;

	@FindBy(how = How.CSS, using = "#uss-sheet-view > div.uss-o-close-icon.uss-o-close-icon-medium")
    private WebElement cloceIconInAddToCartOverlay;

	@FindBy(how = How.XPATH, using = "//button[@class=' a-button-close a-declarative' and @data-action='a-popover-close']")
    private WebElement declarativeCloseIconInAddToCartOverlay;

	@FindBy(how=How.ID, using = "hlb-view-cart-announce")
    private WebElement cartButtonWhenNoOverlayIsDisplayed;


	//Constructor class
	public ProductPage(WebDriver driver, Scenario _scenario) {
		this.driver=driver;
		this._scenario=_scenario;
		comElemet = new CommonElements(driver,_scenario);
		selectBox = new selectBoxControls(driver,_scenario);
		PageFactory.initElements(driver, this);
	}


	public boolean checkProductPageLoaded(){
		return isPageLoaded(addToCart_button);
	}

	public String getProductPrice() {
		return productPrice.getText();
	}

	public String getProductName() {
		return productTitle.getText();
	}
	
	public void addItemToTheCart(String productCount) {
		Select prodcutSelectCnt = new Select(productQuantity);
		prodcutSelectCnt.selectByVisibleText(productCount);
		clickElement(addToCart_button);
		//addToCartPage = PageFactory.initElements(DriverFactory.getInstance().getDriver(), AddToCartPage.class);
		//return addToCartPage;
	}
	
	public String readAddToCartMessage() throws Exception {
		waitForElement(addToCartMessage);
		return addToCartMessage.getText();
	}

	public List<String> getProductList() throws Exception {
		List<String> products = new ArrayList<String>();
		Iterator<WebElement> itr = productList.iterator();
		while (itr.hasNext()){
			WebElement we = itr.next();
			products.add(comElemet.getText(we).trim());
		}
		return products;
	}

	public void selectProductByDescription(String desc) throws Exception {
        WebElement productElement = null;
        try {
            productElement = driver.findElement(By.xpath("//span[@class='a-size-medium a-color-base a-text-normal' and text()='"+desc.trim()+"']//parent::a"));
        } catch (NoSuchElementException e) {
            throw e;
        }
        comElemet.click(productElement);
	}

	public void setProductQuantity(String qty) throws Exception {

        if (isQuantitySelectBoxPresent()) {
            String defaultVal=selectBox.getDefaultText(quantitySelectDropdown);
            if(!defaultVal.equalsIgnoreCase(qty)){
                selectBox.selectByVisibleText(quantitySelectDropdown,qty);
                System.out.println("Quantity is set to "+qty);
            } else {
                System.out.println("Quantity is already set to "+qty);
            }
        } else {
            System.out.println("Selection of Quantity is not applicable for this product");
        }
    }
	
	public void clickOnAddToCart() throws Exception {
	    Thread.sleep(3000);
		comElemet.click(addToCartBtn);
	}

	public void clickOnViewCart() throws Exception {
	    Thread.sleep(3000);
		comElemet.click(viewCartBtn);
	}

	public boolean isQuantitySelectBoxPresent() throws Exception {
        //return comElemet.isElementVisible(quantitySelectDropdown);
       return driver.findElements(quantitySelect).size() > 0;

    }

	public int getProductCountInCart()  {
	    return itemsInTheCart.size();
    }

    public void closeAddToCartOverlay() throws Exception {
	    //checking if no overlay is present
        if(comElemet.isElementVisible(cartButtonWhenNoOverlayIsDisplayed)){
            System.out.println("No overlay is present ");
        } else {
            //check if declarative close button is present and close it if present
            if(comElemet.isElementVisible(declarativeCloseIconInAddToCartOverlay)){
                comElemet.click(declarativeCloseIconInAddToCartOverlay);
                System.out.println("clicked on declarative close button in add to cart overlay");
            }else {
                //comElemet.click(cloceIconInAddToCartOverlay);
                System.out.println("refreshing the page to get rid of add to cart overlays");
                //refreshing the page to get rid of add to cart overlays
                driver.navigate().refresh();

            }
        }

    }
	
}
