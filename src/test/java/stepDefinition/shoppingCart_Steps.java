package stepDefinition;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;

import cucumber.api.Scenario;
//import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.NoSuchElementException;
import cucumber.api.java.en.Then;
import org.testng.Assert;
import pages.CartPage;
import pages.HomePage;
import pages.ProductPage;
import util.setup;

import java.util.*;

public class shoppingCart_Steps {
    public WebDriver driver;
    public Scenario _scenario;
    private setup driverSetup;
    private HomePage homePage;
    private ProductPage productPage;
    private CartPage cartPage;
    public List<String> products;
    public String productSearchName;

    public shoppingCart_Steps() {
        this.driver = Hooks.driver;
        this._scenario = Hooks._scenario;
        driverSetup = new setup(driver);
        homePage=new HomePage(driver,_scenario);
        productPage=new ProductPage(driver,_scenario);
        products=new ArrayList<String>();
        cartPage = new CartPage(driver,_scenario);
    }


    @Given("^I navigate to \"([^\"]*)\"$")
    public void iLoginTo(String url)  {
        System.out.println("URL is "+url.trim());
        try {
            driverSetup.setURL(url);
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
                System.out.println(expectedProduct.get("productDescription")+" is found in the search results");
                _scenario.write(expectedProduct.get("productDescription")+" is found in the search results");
            } catch (AssertionError e) {
                System.out.println("Expected Product Name - "+expectedProduct.get("productDescription")+" is not found in the search results page "+"\n");
                _scenario.write("Expected Product Name - "+expectedProduct.get("productDescription")+" is not found in the search results page "+"\n");
                Assert.fail("Expected Product Name - "+expectedProduct.get("productDescription")+" is not found in the search results page "+"\n");
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
    public void iAddFollowingProductsToTheCart(DataTable productsToBeAdded) throws Throwable {
        //get the total number of products to be added
        int productCountToBeAdded = productsToBeAdded.asMaps(String.class,String.class).size();
        System.out.println("Total number of products to be added to cart : "+productCountToBeAdded);
        //looping through the list and adding them to the cart
        for(Map <String,String> productList : productsToBeAdded.asMaps(String.class,String.class)){

            //clicking on the product
            try {
                productPage.selectProductByDescription(productList.get("productDescription"));
                System.out.println("========== PRODUCT SELECTED : "+productList.get("productDescription"));
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
                System.out.println("========== CLICKED ADD TO CART FOR : "+productList.get("productDescription"));
            } catch (Exception e) {
                System.out.println("Unable to click on 'Add to cart' button due to an exception - "+e.getMessage());
                Assert.fail("Unable to click on 'Add to cart' button due to an exception - "+e.getMessage());
            }

            try {
                productPage.closeAddToCartOverlay();
                System.out.println("========== OVERLAY CLOSED FOR : "+productList.get("productDescription"));
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
            System.out.println("============  TOTAL PRICE OF ALL ITEMS : "+totalPrice);
        } catch (Exception e) {
            System.out.println("TEST FAILED !. Unable to read the total price of each element from the cart due to an exception - "+e.getMessage());
            _scenario.write("TEST FAILED !. Unable to read the total price of each element from the cart due to an exception - "+e.getMessage());
            Assert.fail("TEST FAILED !. Unable to read the total price of each element from the cart due to an exception - "+e.getMessage());
        }

        // Read the subtotal value
        try {
            subTotal = cartPage.getSubTotalInTheCart();
            System.out.println("===========  SUBTOTAL VALUE IN THE CART : "+subTotal);
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

    @Then("^I select the product \"([^\"]*)\" from search results$")
    public void iSelectTheProductFromSearchResults(String productName) throws Throwable {
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
    }

    @Then("^I verify if page title contains the text \"([^\"]*)\"$")
    public void iVerifyIfPageTitleContainsTheText(String expectedTitle) throws Throwable {
        String actualTitle = null;
        try {
            actualTitle=productPage.getProductPageTitle();
            System.out.println("Actual page title : "+actualTitle);
        } catch (Exception e) {
            System.out.println("Unable to get the title of the page due to an exception - "+e.getMessage());
            _scenario.write("Unable to get the title of the page due to an exception - "+e.getMessage());
            Assert.fail("TEST FAILED !. Unable to get the title of the page due to an exception - "+e.getMessage());
        }

        try {
            Assert.assertTrue(actualTitle.toLowerCase().contains(expectedTitle.toLowerCase()));
            System.out.println("actual title contains the expeceted product name");
        } catch (AssertionError e) {
            System.out.println("TEST FAILED !. Actual page title does not contain the expected product name. Actual title - "+actualTitle+" Expected title - "+expectedTitle);
            _scenario.write("TEST FAILED !. Actual page title does not contain the expected product name. Actual title - "+actualTitle+" Expected title - "+expectedTitle);
            Assert.fail("TEST FAILED !. Actual page title does not contain the expected product name. Actual title - "+actualTitle+" Expected title - "+expectedTitle);
        }
    }

    @Given("^I select search category as \"([^\"]*)\" and click on search$")
    public void iSelectSearchCategoryAsAndClickOnSearch(String searchCategory) throws Throwable {
        try {
            homePage.selectSearchCategory(searchCategory.trim());
            System.out.println("===== SEARCH CATEGORY IS SET TO "+searchCategory);
        } catch (Exception e) {
            System.out.println("TEST FAILED !. Unable to select search category "+searchCategory+" from the select box due to an exception - "+e.getMessage());
            _scenario.write("TEST FAILED !. Unable to select search category "+searchCategory+" from the select box due to an exception - "+e.getMessage());
            Assert.fail("TEST FAILED !. Unable to select search category "+searchCategory+" from the select box due to an exception - "+e.getMessage());
        }

        try {
            Thread.sleep(5000);
            homePage.clickSearchIcon();
            System.out.println("===== CLICKED ON SEARCH ICON");
        } catch (Exception e) {
            System.out.println("TEST FAILED !. Unable to click on search icon due to an exception - "+e.getMessage());
            _scenario.write("TEST FAILED !. Unable to click on search icon due to an exception - "+e.getMessage());
            Assert.fail("TEST FAILED !. Unable to click on search icon due to an exception - "+e.getMessage());
        }
    }

    @Then("^I navigate to Amazon Chart section$")
    public void iNavigateToAmazonChartSection() throws Throwable {
        try {
            homePage.navigateToAmazonChartsLink();
            System.out.println("===== NAVIGATED TO 'AMAZON CHARTS' SECTION ");
        } catch (Exception e) {
            System.out.println("TEST FAILED !. Unable to navigate to 'Amazon Chart' section due to an exception - "+e.getMessage());
            _scenario.write("TEST FAILED !. Unable to navigate to 'Amazon Chart' section due to an exception - "+e.getMessage());
            Assert.fail("TEST FAILED !. Unable to navigate to 'Amazon Chart' section due to an exception - "+e.getMessage());
        }
    }

    @Then("^I view most read books section$")
    public void iViewMostReadBooksSection() throws Throwable {
        try {
            homePage.selectMostReadTab();
        } catch (Exception e) {
            System.out.println("TEST FAILED !. Unable to select 'Most Read' tab due to an exception - "+e.getMessage());
            _scenario.write("TEST FAILED !. Unable to select 'Most Read' tab due to an exception - "+e.getMessage());
            Assert.fail("TEST FAILED !. Unable to select 'Most Read' tab due to an exception - "+e.getMessage());
        }
    }

    @Then("^I select fiction category$")
    public void iSelectFictionCategory() throws Throwable {
        try {
            homePage.selectFictionTab();
        } catch (Exception e) {
            System.out.println("TEST FAILED !. Unable to select 'Fiction' tab due to an exception - "+e.getMessage());
            _scenario.write("TEST FAILED !. Unable to select 'Fiction' tab due to an exception - "+e.getMessage());
            Assert.fail("TEST FAILED !. Unable to select 'Fiction' tab due to an exception - "+e.getMessage());
        }
    }

    @Then("^I validate if \"([^\"]*)\" title is ranked as \"([^\"]*)\"$")
    public void iValidateIfTitleIsRankedAs(String ExpectedTitleName, String rank) throws Throwable {
        String actualTitle=null;
        try {
            actualTitle = homePage.getTheTitleforGivenPosition(rank);
            System.out.println("ACTUAL TITLE IS : "+actualTitle);
        } catch (Exception e) {
            System.out.println("TEST FAILED !. Unable to get the title for given rank due to an exception - "+e.getMessage());
            _scenario.write("TEST FAILED !. Unable to get the title for given rank due to an exception - "+e.getMessage());
            Assert.fail("TEST FAILED !. Unable to get the title for given rank due to an exception - "+e.getMessage());
        }

        //Assertion
        try {
            Assert.assertEquals(actualTitle.trim().toLowerCase(),ExpectedTitleName.trim().toLowerCase());
            System.out.println("TITLES MATCHED");
        } catch (AssertionError e) {
            System.out.println("TEST FAILED !. Expected and Actual Titles mismatched - "+e.getMessage());
            _scenario.write("TEST FAILED !. Expected and Actual Titles mismatched - "+e.getMessage());
            Assert.fail("TEST FAILED !. Expected and Actual Titles mismatched - "+e.getMessage());
        }
    }


    @Then("^I select Nonfiction category$")
    public void iSelectNonfictionCategory() throws Throwable {
        try {
            homePage.selectNonFictionTab();
        } catch (Exception e) {
            System.out.println("TEST FAILED !. Unable to select 'Nonfiction' tab due to an exception - "+e.getMessage());
            _scenario.write("TEST FAILED !. Unable to select 'Nonfiction' tab due to an exception - "+e.getMessage());
            Assert.fail("TEST FAILED !. Unable to select 'Nonfiction' tab due to an exception - "+e.getMessage());
        }
    }

    @Then("^I view most sold books section$")
    public void iViewMostSoldBooksSection() throws Throwable {
        try {
            homePage.selectMostSoldTab();
        } catch (Exception e) {
            System.out.println("TEST FAILED !. Unable to select 'Most Sold' tab due to an exception - "+e.getMessage());
            _scenario.write("TEST FAILED !. Unable to select 'Most Sold' tab due to an exception - "+e.getMessage());
            Assert.fail("TEST FAILED !. Unable to select 'Most Sold' tab due to an exception - "+e.getMessage());
        }
    }
}
