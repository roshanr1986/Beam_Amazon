Feature: Amazon shopping cart functionality
  @Scenario1
  @all
  Scenario: Search for a product and verify the list count
    Given I navigate to "https://www.amazon.com/"
    Given I search for the product "iphone 8"
    Then I read the list of product descriptions on page 1
    #Then I validate if search results count is 19
    Then I validate if following products are found in the page
      |productDescription|
      |Apple iPhone 8 (64GB) - Silver [Locked to Simple Mobile Prepaid]|
      |Apple iPhone 8, GSM Unlocked, 64GB - Gold (Renewed)|

  @Scenario2
  @all
  Scenario: Add product to cart and verify product is added to the cart successfully
    Given I navigate to "https://www.amazon.com/"
    Given I setup cart before test
    Given I search for the product "iphone 8"
    Then I add product with name "Apple iPhone 8, GSM Unlocked, 64GB - Gold (Renewed)" to cart with quantity "1"
    Then I navigate to cart
    Then I verify if number of products in the cart is 1
    Then I verify if shopping cart has the product "Apple iPhone 8, GSM Unlocked, 64GB - Gold (Renewed)" added successfully
    Then I clear cart after test

  @Scenario3
  @all
  Scenario: Add product to cart and verify product is added to the cart successfully
    Given I navigate to "https://www.amazon.com/"
    Given I setup cart before test
    Given I search for the product "iphone 8"
    Then I add product with name "Apple iPhone 8, GSM Unlocked, 64GB - Gold (Renewed)" to cart with quantity "1"
    Then I navigate to cart
    Then I verify if number of products in the cart is 1
    Then I delete the product with name "Apple iPhone 8, GSM Unlocked, 64GB - Gold (Renewed)" from the cart
    Then I validate if total item count of cart is "0"
    Then I clear cart after test

  @Scenario4
  @all
  Scenario: Add list of products to the cart and verify the changes
    Given I navigate to "https://www.amazon.com/"
    Given I setup cart before test
    Given I search for the product "iphone 8"
    Then I add following products to the cart
      |productDescription|quantity|
      |Apple iPhone 8 (64GB) - Silver [Locked to Simple Mobile Prepaid]   |1       |
      |Apple iPhone 8, GSM Unlocked, 64GB - Gold (Renewed)                |1       |
    Then I navigate to cart
    Then I verify if number of products in the cart is 2
    Then I verify if total price in the cart is changed
    Then I clear cart after test
  
  @Scenario5
  @all
  Scenario: Remove list of products from cart and verify the changes
    Given I navigate to "https://www.amazon.com/"
    Then I setup cart before test
    Given I search for the product "iphone 8"
    Then I add following products to the cart
      |productDescription|quantity|
      |Apple iPhone 8 (64GB) - Silver [Locked to Simple Mobile Prepaid]      |1       |
      |Apple iPhone 8, GSM Unlocked, 64GB - Gold (Renewed)                   |1       |
    Then I navigate to cart
    Then I verify if number of products in the cart is 2
    Then I verify if total price in the cart is changed
    Then I delete the product with name "Apple iPhone 8, GSM Unlocked, 64GB - Gold (Renewed)" from the cart
    Then I verify if number of products in the cart is 1
    Then I verify if total price in the cart is changed
    Then I delete the product with name "Apple iPhone 8 (64GB) - Silver [Locked to Simple Mobile Prepaid]" from the cart
    Then I verify if number of products in the cart is 0
    Then I verify if total price in the cart is changed
    Then I clear cart after test


  @Scenario6
  @all
  Scenario: Validate page title when user selects a product
    Given I navigate to "https://www.amazon.com/"
    Given I search for the product "iphone 8"
    Then I select the product "Apple iPhone 8, GSM Unlocked, 64GB - Gold (Renewed)" from search results
    Then I verify if page title contains the text "Apple iPhone 8, GSM Unlocked, 64GB - Gold (Renewed)"

  @Scenario7
  @all
  Scenario: Verify if a title is among the most read books under fiction category for the most recent week
    Given I navigate to "https://www.amazon.com/"
    Given I setup cart before test
    Given I select search category as "Books" and click on search
    Then I navigate to Amazon Chart section
    Then I view most read books section
    Then I select fiction category
    Then I validate if "Harry Potter and the Order of the Phoenix" title is ranked as "2"
    Then I clear cart after test

  @Scenario8
  @all
  Scenario: Verify if a title is among the most read books under Non fiction category for the most recent week
    Given I navigate to "https://www.amazon.com/"
    Given I select search category as "Books" and click on search
    Then I navigate to Amazon Chart section
    Then I view most read books section
    Then I select Nonfiction category
    Then I validate if "12 Rules for Life" title is ranked as "6"

  @Scenario9
  @all
  Scenario: Verify if a title is among the most sold books under Non fiction category for the most recent week
    Given I navigate to "https://www.amazon.com/"
    Given I select search category as "Books" and click on search
    Then I navigate to Amazon Chart section
    Then I view most sold books section
    Then I select fiction category
    Then I validate if "Thick" title is ranked as "3"

  @Scenario10
  @all
  Scenario: Verify if a title is among the most sold books under Non fiction category for the most recent week
    Given I navigate to "https://www.amazon.com/"
    Given I select search category as "Books" and click on search
    Then I navigate to Amazon Chart section
    Then I view most sold books section
    Then I select Nonfiction category
    Then I validate if "Girl, Stop Apologizing" title is ranked as "4"

#  @Scenario2
#  @all
#  Scenario: Sample scenario 2
#    Given setp1
#    Then Step2

