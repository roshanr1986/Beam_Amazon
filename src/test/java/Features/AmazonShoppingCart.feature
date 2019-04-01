Feature: Amazon shopping cart functionality
#  @Scenario1
#  @all
#  Scenario: Search for a product and verify the list count
#    Given I navigate to "https://www.amazon.com/"
#    Given I search for the product "iphone 8"
#    Then I read the list of product descriptions on page 1
#    Then I validate if search results count is 19

#    Then I validate if following products are found in the page
#      |productDescription|
#      |Apple iPhone 8 (64GB) - Silver - [works exclusively with Simple Mobile]|
#      |Apple iPhone 8, GSM Unlocked, 64GB - Gold (Refurbished)|
#      |Apple iPhone 8, GSM Unlocked, 64GB - Space Gray (Refurbished)|
#      |Apple iPhone 8, Fully Unlocked, 64GB (Refurbished)|
#      |Apple iPhone 8 64GB GSM Unlocked Smartphone, RED (Refurbished)|
#      |Apple iPhone 7, GSM Unlocked, 32GB - Rose Gold (Refurbished)|
#      |Apple iPhone 8, Fully Unlocked, 256GB - Gold (Refurbished)|
#      |Apple iPhone 8, Fully Unlocked, 256GB - Space Gray (Refurbished)|
#      |Screen Replacement White for iPhone 8 Plus 3D Touch LCD Screen Digitizer Replacement Frame Display Assembly Set with Repair Tool Kit|
#      |Yootech Aluminum Fast Wireless Charger,10W Qi-Certified Wireless Charging Stand,7.5W Compatible with iPhone Xs MAX/XR/XS/X/8/8 Plus,10W Fast Charging Galaxy S10/S10 Plus/S10E/S9(No AC Adapter)|
#      |Apple iPhone 8 Plus, GSM Unlocked, 64GB - Space Gray (Refurbished)|
#      |Apple iPhone 8, AT&T, 64GB - Space Gray (Refurbished)|
#      |Apple iPhone 6S, GSM Unlocked, 16GB - Rose Gold (Refurbished)|
#      |Apple iPhone 7 , GSM Unlocked, 128GB - Gold (Refurbished)|
#      |Apple iPhone 8 Plus, 64 GB, Fully Unlocked, Space Gray (Refurbished)|
#      |Apple iPhone 6, GSM Unlocked, 64 GB - Gold (Refurbished)|
#      |Apple iPhone 8 Plus 64GB Red (special edition Product RED) A1897 - Factory Unlocked - GSM ONLY, NO CDMA (Refurbished)|
#      |Apple iPhone 8, GSM Unlocked, 64GB (Refurbished)|
#      |in-Ear Headphones, Fellee Wired Stereo HiFi Earphones Stereo Dual Dynamic Drivers Earbuds with Mic and 3.5mm Jack, Noise Isolating Sports Headsets (Transparent Golden)|
#      |Earbuds - Wired Earbuds with Microphone Mic and Volume Control by Deivvox - in Ear Earphones Noise Cancelling Isolating Earphone - Bass Ear Buds in Ear Headphones Compatible iPhone iPod Samsung 3.5mm|
#      |Ownest Compatible with iPhone 7 Case,iPhone 8 Case,iPhone 6 Case with Armor Dual with Heavy Duty Protection and Finger Ring Kickstand Fit Magnetic Car Mount for iPhone 7,iPhone 8-(Mint)|

#  @Scenario2
#  @all
#  Scenario: Add product to cart and verify product is added to the cart successfully
#    Given I navigate to "https://www.amazon.com/"
#    Given I search for the product "iphone 8"
#    Then I add product with name "Apple iPhone 8 (64GB) - Silver - [works exclusively with Simple Mobile]" to cart with quantity "1"
#    Then I navigate to cart
#    Then I verify if number of products in the cart is 1
#    #Then I verify if shopping cart has the product "Apple iPhone 8, GSM Unlocked, 64GB - Gold (Refurbished)" added successfully
#    # Apple iPhone 8 (64GB) - Silver - [works exclusively with Simple Mobile]
#    # Apple iPhone 8, GSM Unlocked, 64GB - Gold (Refurbished)
#
#  @Scenario3
#  @all
#  Scenario: Add product to cart and verify product is added to the cart successfully
#    Given I navigate to "https://www.amazon.com/"
#    Given I search for the product "iphone 8"
#    Then I add product with name "Apple iPhone 8 (64GB) - Silver - [works exclusively with Simple Mobile]" to cart with quantity "1"
#    Then I navigate to cart
#    Then I verify if number of products in the cart is 1
#    Then I delete the product with name "Apple iPhone 8 (64GB) - Silver - [works exclusively with Simple Mobile]" from the cart
#    Then I validate if total item count of cart is "0"

  @Scenario4
  @all
  Scenario: Add list of products to the cart and verify the changes
    Given I navigate to "https://www.amazon.com/"
    Given I search for the product "iphone 8"
    Then I add following products to the cart
      |productDescription|quantity|
      |Apple iPhone 8 (64GB) - Silver - [works exclusively with Simple Mobile]      |1       |
      |Apple iPhone 8, GSM Unlocked, 64GB - Space Gray (Refurbished)                |1       |
    Then I navigate to cart
    Then I verify if number of products in the cart is 2
    Then I verify if total price in the cart is changed

#  @Scenario2
#  @all
#  Scenario: Sample scenario 2
#    Given setp1
#    Then Step2

