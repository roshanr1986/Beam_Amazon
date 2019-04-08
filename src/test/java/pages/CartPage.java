package pages;

import commonLibrary.implementation.CommonElements;
import commonLibrary.implementation.selectBoxControls;
import cucumber.api.Scenario;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
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

    @FindBy(how=How.XPATH, using = "//span[@id='sc-subtotal-amount-activecart']//span")
    private WebElement subTotalInCart;

    @FindBy(how = How.XPATH, using = "//*[@id='sc-active-cart']/div/h1")
    private WebElement shoppingCartEmptyMessage;

    @FindBy(how = How.XPATH, using = "//span[@class='a-size-medium sc-product-title a-text-bold']//ancestor::ul//following::div[@class='a-row sc-action-links']//span[@class='a-size-small sc-action-delete']//span")
    private List<WebElement> deleteButtonListInCart;

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

    public void setupCartBeforeAndAfterTest() throws Exception {
        clearCart();
    }

    public void clearCart() throws Exception {
        //check if cart is empty
        if(!getItemCountInTheCart().equalsIgnoreCase("0")){
            System.out.println("CART IS NOT EMPTY. STARTING TO DELETE EXISTING ITEMS");
            System.out.println("NO OF ITEMS STILL IN THE CART : "+getItemCountInTheCart());
            // else navigate inside cart
            navigateToCartPage();
            //get all items in the page and remove them all
            deleteAllItemsInCart();
            // verify cart item count is 0
            while (!getItemCountInTheCart().equalsIgnoreCase("0")){
                System.out.println("Cart is not empty yet. Continuing to delete existing items");
                deleteAllItemsInCart();
            }
            System.out.println("Cart is set to empty successfully ");

        } else {
            System.out.println("CART IS ALREADY EMPTY");
        }

    }

    public void deleteAllItemsInCart() throws Exception {
        for(int i=0;i<deleteButtonListInCart.size();i++){
            comElemet.click(deleteButtonListInCart.get(i));
            System.out.println("Deleted product "+i+1);
            Thread.sleep(5000);
        }
    }

    public int getProductCountInCart() throws InterruptedException {
        int count=0;
        try {
            Thread.sleep(5000);
            count=itemsInTheCart.size();
        } catch (NoSuchElementException e ) {
            System.out.println("No items in the list");
        }
        System.out.println("ITEMS COUNT INSIDE CART: "+count);
        return count;
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

    public String getItemCountInTheCart() throws Exception {
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
            String totalText=null;

            try {
                totalText = comElemet.getText(priceOfEachItemInTheCart.get(i)).trim();
            } catch (Exception e) {
                System.out.println("Error occurred when reading item value text from element");
                throw e;
            }

            if (totalText.contains("$")) {
                try {
                    price=DecimalFormat.getNumberInstance().parse(totalText.replace("$","").trim()).doubleValue();
                    System.out.println("Removed $ sign from the price text");
                } catch (ParseException e) {
                    System.out.println("Error occurred when converting sub total to double");
                    throw e;
                } catch (Exception ex){
                    throw ex;
                }
            } else if(totalText.contains("USD")) {
                try {
                    price=DecimalFormat.getNumberInstance().parse(totalText.replace("USD","").trim()).doubleValue();
                    System.out.println("Removed 'USD' from the price text");
                } catch (ParseException e) {
                    System.out.println("Error occurred when converting sub total to double");
                    throw e;
                } catch (Exception ex){
                    throw ex;
                }
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
        String totalText=null;

            try {
                totalText=comElemet.getText(subTotalInCart).trim();
            } catch (NoSuchElementException ne){
                System.out.println("Subtotal value is not found in the page, Checking if the cart is empty");

                //getting the cart empty message from page header
                String cartEmptyMessage;
                try {
                    cartEmptyMessage = comElemet.getText(shoppingCartEmptyMessage);
                } catch (NoSuchElementException e) {
                    System.out.println("Element for shoppingCartEmptyMessage was not found in the page ");
                    throw e;
                }

                // checking if empty message is valid before setting the subtotal to 0
                if(cartEmptyMessage.contains("Your Shopping Cart is empty")){
                    System.out.println("SHOPPING CART IS EMPTY AND SUBTOTAL VALUE IS 0");
                    totalText="0";
                } else {
                    System.out.println("Element of 'Subtotal value' in the cart is not found in the page, but cart is not empty. Actual message in the cart - "+cartEmptyMessage);
                    throw ne;
                }

            }catch (ParseException e) {
                System.out.println("Error occurred when reading sub total text value");
                throw e;
            } catch (Exception ex){
                throw ex;
            }


            if (totalText.contains("$")) {
                try {
                    subTotal=DecimalFormat.getNumberInstance().parse(totalText.replace("$","").trim()).doubleValue();
                } catch (ParseException e) {
                    System.out.println("Error occurred when converting sub total to double");
                    throw e;
                } catch (Exception ex){
                    throw ex;
                }
            } else if(totalText.contains("USD")) {
                try {
                    subTotal=DecimalFormat.getNumberInstance().parse(totalText.replace("USD","").trim()).doubleValue();
                } catch (ParseException e) {
                    System.out.println("Error occurred when converting sub total to double");
                    throw e;
                } catch (Exception ex){
                    throw ex;
                }
            }
            return subTotal;
        }

}
