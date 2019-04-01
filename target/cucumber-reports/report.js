$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("AmazonShoppingCart.feature");
formatter.feature({
  "line": 1,
  "name": "Amazon shopping cart functionality",
  "description": "",
  "id": "amazon-shopping-cart-functionality",
  "keyword": "Feature"
});
formatter.before({
  "duration": 12248941300,
  "status": "passed"
});
formatter.scenario({
  "line": 58,
  "name": "Add list of products to the cart and verify the changes",
  "description": "",
  "id": "amazon-shopping-cart-functionality;add-list-of-products-to-the-cart-and-verify-the-changes",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 56,
      "name": "@Scenario4"
    },
    {
      "line": 57,
      "name": "@all"
    }
  ]
});
formatter.step({
  "line": 59,
  "name": "I navigate to \"https://www.amazon.com/\"",
  "keyword": "Given "
});
formatter.step({
  "line": 60,
  "name": "I search for the product \"iphone 8\"",
  "keyword": "Given "
});
formatter.step({
  "line": 61,
  "name": "I add following products to the cart",
  "rows": [
    {
      "cells": [
        "productDescription",
        "quantity"
      ],
      "line": 62
    },
    {
      "cells": [
        "Apple iPhone 8 (64GB) - Silver - [works exclusively with Simple Mobile]",
        "1"
      ],
      "line": 63
    },
    {
      "cells": [
        "Apple iPhone 8, GSM Unlocked, 64GB - Space Gray (Refurbished)",
        "1"
      ],
      "line": 64
    }
  ],
  "keyword": "Then "
});
formatter.step({
  "line": 65,
  "name": "I navigate to cart",
  "keyword": "Then "
});
formatter.step({
  "line": 66,
  "name": "I verify if number of products in the cart is 2",
  "keyword": "Then "
});
formatter.step({
  "line": 67,
  "name": "I verify if total price in the cart is changed",
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "https://www.amazon.com/",
      "offset": 15
    }
  ],
  "location": "shoppingCart_Steps.iLoginTo(String)"
});
formatter.write("navigated to URL : https://www.amazon.com/");
formatter.result({
  "duration": 4604370800,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "iphone 8",
      "offset": 26
    }
  ],
  "location": "shoppingCart_Steps.iSearchForTheProduct(String)"
});
formatter.result({
  "duration": 3568260000,
  "status": "passed"
});
formatter.match({
  "location": "shoppingCart_Steps.iAddFollowingProductsToTheCartWithQuantity(DataTable)"
});
formatter.result({
  "duration": 348818556700,
  "error_message": "java.lang.AssertionError: Close icon in the overlay was not found. Unable to close the overlay and proceed ahead\r\n\tat org.testng.Assert.fail(Assert.java:96)\r\n\tat stepDefinition.shoppingCart_Steps.iAddFollowingProductsToTheCartWithQuantity(shoppingCart_Steps.java:292)\r\n\tat ✽.Then I add following products to the cart(AmazonShoppingCart.feature:61)\r\n",
  "status": "failed"
});
formatter.match({
  "location": "shoppingCart_Steps.iNavigateToCart()"
});
formatter.result({
  "status": "skipped"
});
formatter.match({
  "arguments": [
    {
      "val": "2",
      "offset": 46
    }
  ],
  "location": "shoppingCart_Steps.iVerifyIfNumberOfProductsInTheCartIs(int)"
});
formatter.result({
  "status": "skipped"
});
formatter.match({
  "location": "shoppingCart_Steps.iVerifyIfTotalPriceInTheCartIsChanged()"
});
formatter.result({
  "status": "skipped"
});
formatter.after({
  "duration": 44988700,
  "status": "passed"
});
});