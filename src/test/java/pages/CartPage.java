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

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CartPage {

    public WebDriver driver;
    public Scenario _scenario;
    private CommonElements comElemet;
    private selectBoxControls selectBox;

    @FindBy(how= How.XPATH, using = "//div[@class='sc-list-item-content']")
    private List<WebElement> itemsInTheCart;

    @FindBy(how=How.ID, using = "nav-cart")
    private WebElement cartIcon;

    @FindBy(how = How.CSS, using = "#sc-active-cart > div > div > h2")
    private WebElement cartPageTitle;

    @FindBy(how=How.ID, using ="nav-cart-count")
    private WebElement itemCountInCartIcon;

    @FindBy(how = How.XPATH, using ="//div[@class='sc-list-item-content']//span[@class='a-size-medium a-color-price sc-price sc-white-space-nowrap sc-product-price sc-price-sign a-text-bold']")
    private List<WebElement> priceOfEachItemInTheCart;

    @FindBy(how=How.XPATH, using = "//span[@class='a-size-medium a-color-price sc-price sc-white-space-nowrap  sc-price-sign']")
    private WebElement subTotalInCart;

    public CartPage(WebDriver driver, Scenario _scenario) {
        this.driver=driver;
        this._scenario=_scenario;
        comElemet = new CommonElements(driver,_scenario);
        selectBox = new selectBoxControls(driver,_scenario);
        PageFactory.initElements(driver, this);
    }

    public void navigateToCartPage() throws Exception {
        //check if user is already in cart page
        if (comElemet.isElementVisible(cartPageTitle)) {
            System.out.println("User already in the cart page");
        } else {
            System.out.println("Navigating to cart page");
            comElemet.click(cartIcon);
        }

    }

    public int getProductCountInCart()  {
        return itemsInTheCart.size();
    }

    public void deleteGivenProductFromTheCart(String productDesc) throws Exception{
        WebElement deleteElement;
        try {
             deleteElement = driver.findElement(By.xpath("//span[@class='a-size-medium sc-product-title a-text-bold' and contains(text(),'"+productDesc+"')]//ancestor::ul//following::div[@class='a-row sc-action-links']//span[@class='a-size-small sc-action-delete']//span"));
        } catch (Exception e) {
            throw e;
        }

        comElemet.click(deleteElement);

    }

    public String getItemCountInTheCart() throws InterruptedException, Exception {
        Thread.sleep(3000);
        return comElemet.getText(itemCountInCartIcon).trim();
    }

    public double getTotalPriceOfAllItemsInTheCart() throws Exception {
        List<Double> pricesText = new ArrayList<Double>();
        double totalPrice=0;

        //looping through
        for(int i=0;i<priceOfEachItemInTheCart.size();i++){
            //converting each price into double
            double price=0;
            try {
                price = DecimalFormat.getNumberInstance().parse(comElemet.getText(priceOfEachItemInTheCart.get(i)).replace("$","").trim()).doubleValue();
            } catch (ParseException e) {
                System.out.println("Error occurred when converting price to double");
                throw e;
            }

            //store each value in list
            try {
                pricesText.add(i,price);
            } catch (Exception e) {
                System.out.println("Unable to store price in the list");
                throw e;
            }

            //calculate total price
            totalPrice=totalPrice+pricesText.get(i);
        }

        return totalPrice;
        }

        public double getSubTotalInTheCart() throws Exception {
        double subTotal=0;

            try {
                subTotal=DecimalFormat.getNumberInstance().parse(comElemet.getText(subTotalInCart).replace("$","").trim()).doubleValue();
            } catch (ParseException e) {
                System.out.println("Error occurred when converting sub total to double");
                throw e;
            } catch (Exception ex){
                throw ex;
            }
            return subTotal;
        }

}
