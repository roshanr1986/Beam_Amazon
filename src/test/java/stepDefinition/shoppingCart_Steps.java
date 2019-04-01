package stepDefinition;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;

import cucumber.api.Scenario;
//import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.PageFactory;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.testng.Assert;
import pages.AddToCartPage;
import pages.CartPage;
import pages.HomePage;
import pages.ProductPage;
import propertyReader.PropertyReader;
import util.DriverFactory;
import util.TestAssert;
import util.setup;

import java.util.*;

public class shoppingCart_Steps {
    public WebDriver driver;
    public Scenario _scenario;
    private setup testSetup;
    private HomePage homePage;
    private ProductPage productPage;
    private CartPage cartPage;
    public PropertyReader propertyReader;
    public Map<String,String> productDetails;
    public List<String> products;
    public String productSearchName;

    public shoppingCart_Steps() {
        this.driver = Hooks.driver;
        this._scenario = Hooks._scenario;
        testSetup = new setup(driver);
        homePage=new HomePage(driver,_scenario);
        productPage=new ProductPage(driver,_scenario);
        propertyReader = new PropertyReader();
        productDetails=new HashMap<String,String>();
        products=new ArrayList<String>();
        cartPage = new CartPage(driver,_scenario);
    }


    @Given("^I navigate to \"([^\"]*)\"$")
    public void iLoginTo(String url)  {
        System.out.println("URL is "+url.trim());
        try {
            testSetup.setURL(url);
            _scenario.write("navigated to URL : "+url);
        } catch (Exception e) {
            System.out.println("Unable to Navigate to "+url+" due to following exception - "+e.getMessage());
            Assert.fail("Test failed");
        }
    }

    @Given("^I search for the product \"([^\"]*)\"$")
    public void iSearchForTheProduct(String productName) {
        productSearchName=productName;
        try {
            homePage.searchItem(productName);
        } catch (Exception e) {
            System.out.println("TEST FAILED. Unable to search for the product - "+productName+" due to an exception - "+e.getMessage());
            _scenario.write("TEST FAILED. Unable to search for the product - "+productName+" due to an exception - "+e.getMessage());
            Assert.fail("TEST FAILED. Unable to search for the product - "+productName+" due to an exception - "+e.getMessage());
        }
    }

    @Then("^I read the list of product descriptions on page (\\d+)$")
    public void iReadTheListOfProductDescriptionsOnPage(int pageNumber)  {
        // getting the list of products appear on search results
        try {
            products = productPage.getProductList();
            System.out.println("Number of products = "+products.size());
        } catch (Exception e) {
            System.out.println("Unable to get product list due to following exception - "+e.getMessage());
            Assert.fail("Test failed");
        }

        //printing out the list of product descriptions
        System.out.println("============== LIST OF PRODUCT DESCRIPTIONS IN PAGE "+pageNumber+" ==============");
        for(int i=0;i<products.size();i++){
            System.out.println((i+1)+") "+products.get(i));
        }
        System.out.println("=================================================================================");
    }



    @Given("^setp1$")
    public void setp() throws Throwable {
        System.out.println("Step 1");
    }

    @Then("^Step2$")
    public void step() throws Throwable {
        System.out.println("Step 2");
    }

    @Then("^I validate if search results count is (\\d+)$")
    public void iValidateIfSearchResultsCountIs(int expectedResultCount) throws Throwable {
        try {
            Assert.assertEquals(products.size(),expectedResultCount,"Test Passed. Expected count - "+expectedResultCount+" and actual result count - "+products.size());
            System.out.println("Test Passed. Expected count - "+expectedResultCount+" and actual result count - "+products.size());
        } catch (AssertionError e) {
            System.out.println("Expected results count mismatched. Expected count - "+expectedResultCount+" but actual result count - "+products.size());
            _scenario.write("Expected results count mismatched. Expected count - "+expectedResultCount+" but actual result count - "+products.size());
            Assert.fail();
        }
    }


    @Then("^I validate if following products are found in the page$")
    public void iValidateIfFollowingProductsAreFoundInThePage(DataTable expectedProductList) throws Throwable {
        for(Map <String,String> expectedProduct : expectedProductList.asMaps(String.class,String.class)){
            try {
                Assert.assertTrue(products.contains(expectedProduct.get("productDescription")),"PRODUCT FOUND = "+expectedProduct.get("productDescription"));
            } catch (AssertionError e) {
                //System.out.println("PRODUCT NOT FOUND = "+expectedProduct.get("productDescription"));
                System.out.println("Expected Product Name - "+expectedProduct.get("productDescription")+" is not found in the search results page ");
               // _scenario.write("Expected Product Name - "+expectedProduct.get("productDescription")+" is not found in the search results page "+e.getMessage());
                Assert.fail("Expected Product Name - "+expectedProduct.get("productDescription")+" is not found in the search results page ");
            }
        }
    }


    @Then("^I add product with name \"([^\"]*)\" to cart with quantity \"([^\"]*)\"$")
    public void iAddProductWithNameToCart(String productName, String quantity) throws Throwable {
        //clicking on the product
        try {
            productPage.selectProductByDescription(productName);
            System.out.println("Product selected");
        } catch (NoSuchElementException ne){
            System.out.println("TEST FAILED INTENTIONALLY !. Unable to find a matching product with given Name - "+productName);
            Assert.fail("TEST FAILED INTENTIONALLY !. Unable to find a matching product with given Name - "+productName);

        } catch (Exception e) {
            System.out.println("Unable to select the product with given name - "+productName+" due to an exception - "+e.getMessage());
            Assert.fail("Unable to select the product with given name - "+productName+" due to an exception - "+e.getMessage());
        }

        // set quantity
        try {
            productPage.setProductQuantity(quantity);
            System.out.println("Product quantity is set to "+quantity);
        } catch (Exception e) {
            System.out.println("Unable to set product quantity to "+quantity+" due to an exception - "+e.getMessage());
        }

        //click on add to cart button
        try {
            productPage.clickOnAddToCart();
        } catch (Exception e) {
            System.out.println("Unable to click on 'Add to cart' button due to an exception - "+e.getMessage());
            Assert.fail("Unable to click on 'Add to cart' button due to an exception - "+e.getMessage());
        }

        try {
            productPage.clickOnViewCart();
        } catch (NoSuchElementException en){
            System.out.println("'View cart' button is not visible for this product");

            //close the overlay by clicking on the x icon
            try {
                productPage.closeAddToCartOverlay();
            } catch (NoSuchElementException ne){
                System.out.println("Close icon in the overlay was not found. Unable to close the overlay and proceed ahead");
                Assert.fail("Close icon in the overlay was not found. Unable to close the overlay and proceed ahead");
            }catch (Exception e) {
                System.out.println("Unable to close the overlay due to an exception - "+e.getMessage());
                Assert.fail("Unable to close the overlay due to an exception - "+e.getMessage());
            }

        }catch (Exception e) {
            System.out.println("Unable to click on 'View to cart' button due to an exception - "+e.getMessage());
            Assert.fail("Unable to click on 'Add to cart' button due to an exception - "+e.getMessage());
        }

    }

    @Then("^I verify if shopping cart has the product \"([^\"]*)\" added successfully$")
    public void iVerifyIfShoppingCartHasTheProductAddedSuccessfully(String arg0) throws Throwable {

    }

    @Then("^I navigate to cart$")
    public void iNavigateToCart() throws Throwable {
        cartPage.navigateToCartPage();
    }

    @Then("^I verify if number of products in the cart is (\\d+)$")
    public void iVerifyIfNumberOfProductsInTheCartIs(int expectedItemCountInCart) throws Throwable {

        //get actual products in the cart
        int actualProductCountInCart=0;
        try {
            actualProductCountInCart = cartPage.getProductCountInCart();
        } catch (Exception e) {
            System.out.println("Unable to get existing product count in the cart due to an exception - "+e.getMessage());
            _scenario.write("Unable to get existing product count in the cart due to an exception - "+e.getMessage());
            Assert.fail("Unable to get existing product count in the cart due to an exception - "+e.getMessage());
        }

        // assertion
        try {
            Assert.assertEquals(actualProductCountInCart, expectedItemCountInCart);
        } catch (AssertionError e) {
            System.out.println("TEST FAILED ! Product count mismatched - "+e.getMessage());
            _scenario.write("TEST FAILED ! Product count mismatched - "+e.getMessage());
            Assert.fail("TEST FAILED ! Product count mismatched - "+e.getMessage());
        }
    }

    @Then("^I delete the product with name \"([^\"]*)\" from the cart$")
    public void iDeleteTheProductWithNameFromTheCart(String productDesc) throws Throwable {
        try {
            cartPage.deleteGivenProductFromTheCart(productDesc);
        } catch (Exception e) {
            System.out.println("TEST FAILED ! Unable to delete the product - "+productDesc+" due to an exception - "+e.getMessage());
            _scenario.write("TEST FAILED ! Unable to delete the product - "+productDesc+" due to an exception - "+e.getMessage());
            Assert.fail("TEST FAILED ! Unable to delete the product - "+productDesc+" due to an exception - "+e.getMessage());
        }
    }

    @Then("^I validate if total item count of cart is \"([^\"]*)\"$")
    public void iValidateIfTotalItemCountOfCartIs(String expectedCount) throws Throwable {
        String actualcount = null;
        try {
            actualcount=cartPage.getItemCountInTheCart();
        } catch (Exception e) {
            System.out.println("TEST FAILED ! Unable to get the total item count from the cart icon due to an exception - "+e.getMessage());
            _scenario.write("TEST FAILED ! Unable to get the total item count from the cart icon due to an exception - "+e.getMessage());
            Assert.fail("TEST FAILED ! Unable to get the total item count from the cart icon due to an exception - "+e.getMessage());
        }

        // assertion
        try {
            Assert.assertEquals(actualcount.trim().toLowerCase(),expectedCount.trim().toLowerCase());
        } catch (AssertionError e) {
            System.out.println("TEST FAILED ! actual and expected item count in the shopping cart mismatched - "+e.getMessage());
            _scenario.write("TEST FAILED ! actual and expected item count in the shopping cart mismatched - "+e.getMessage());
            Assert.fail("TEST FAILED ! actual and expected item count in the shopping cart mismatched - "+e.getMessage());
        }
    }

    @Then("^I add following products to the cart$")
    public void iAddFollowingProductsToTheCartWithQuantity(DataTable productsToBeAdded) throws Throwable {
        //get the total number of products to be added
        int productCountToBeAdded = productsToBeAdded.asMaps(String.class,String.class).size();
        System.out.println("Total number of products to be added to cart : "+productCountToBeAdded);
        //looping through the list and adding them to the cart
        for(Map <String,String> productList : productsToBeAdded.asMaps(String.class,String.class)){

            //clicking on the product
            try {
                productPage.selectProductByDescription(productList.get("productDescription"));
                System.out.println("Product selected");
            } catch (NoSuchElementException ne){
                System.out.println("TEST FAILED INTENTIONALLY !. Unable to find a matching product with given Name - "+productList.get("productDescription"));
                Assert.fail("TEST FAILED INTENTIONALLY !. Unable to find a matching product with given Name - "+productList.get("productDescription"));

            } catch (Exception e) {
                System.out.println("Unable to select the product with given name - "+productList.get("productDescription")+" due to an exception - "+e.getMessage());
                Assert.fail("Unable to select the product with given name - "+productList.get("productDescription")+" due to an exception - "+e.getMessage());
            }

            // set quantity
            try {
                productPage.setProductQuantity(productList.get("quantity"));
                System.out.println("Product quantity is set to "+productList.get("quantity"));
            } catch (Exception e) {
                System.out.println("Unable to set product quantity to "+productList.get("quantity")+" due to an exception - "+e.getMessage());
            }

            //click on add to cart button
            try {
                productPage.clickOnAddToCart();
            } catch (Exception e) {
                System.out.println("Unable to click on 'Add to cart' button due to an exception - "+e.getMessage());
                Assert.fail("Unable to click on 'Add to cart' button due to an exception - "+e.getMessage());
            }

            try {
                productPage.closeAddToCartOverlay();
                //productPage.clickOnViewCart();
            }  catch (NoSuchElementException ne){
                    System.out.println("Close icon in the overlay was not found. Unable to close the overlay and proceed ahead. Original Exception - "+ne.getMessage());
                    Assert.fail("Close icon in the overlay was not found. Unable to close the overlay and proceed ahead. Original Exception - "+ne.getMessage());
                }catch (Exception e) {
                    System.out.println("Unable to close the overlay due to an exception - "+e.getMessage());
                    Assert.fail("Unable to close the overlay due to an exception - "+e.getMessage());
                }
            //calling the search product method to re search for the product
            iSearchForTheProduct(productSearchName);
        }
    }

    @Then("^I verify if total price in the cart is changed$")
    public void iVerifyIfTotalPriceInTheCartIsChanged() throws Throwable {
        double totalPrice=0;
        double subTotal=0;
        try {
            totalPrice=cartPage.getTotalPriceOfAllItemsInTheCart();
            System.out.println("Total price of all items : "+totalPrice);
        } catch (Exception e) {
            System.out.println("TEST FAILED !. Unable to read the total price of each element from the cart due to an exception - "+e.getMessage());
            _scenario.write("TEST FAILED !. Unable to read the total price of each element from the cart due to an exception - "+e.getMessage());
            Assert.fail("TEST FAILED !. Unable to read the total price of each element from the cart due to an exception - "+e.getMessage());
        }

        // Read the subtotal value
        try {
            subTotal = cartPage.getSubTotalInTheCart();
            System.out.println("Subtotal value in the cart is : "+subTotal);
        } catch (Exception e) {
            System.out.println("TEST FAILED !. Unable to read the subtotal price from the cart due to an exception - "+e.getMessage());
            _scenario.write("TEST FAILED !. Unable to read the subtotal price from the cart due to an exception - "+e.getMessage());
            Assert.fail("TEST FAILED !. Unable to read the subtotal price from the cart due to an exception - "+e.getMessage());
        }

        // Assertion
        try {
            Assert.assertEquals(totalPrice,subTotal);
        } catch (AssertionError e) {
            System.out.println("TEST FAILED !. total price of products and subtotal in the cart mismatched - "+e.getMessage());
            _scenario.write("TEST FAILED !. total price of products and subtotal in the cart mismatched - "+e.getMessage());
            Assert.fail("TEST FAILED !. total price of products and subtotal in the cart mismatched - "+e.getMessage());
        }

    }
}
