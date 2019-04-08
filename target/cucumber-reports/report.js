$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("AmazonShoppingCart.feature");
formatter.feature({
  "line": 1,
  "name": "Amazon shopping cart functionality",
  "description": "",
  "id": "amazon-shopping-cart-functionality",
  "keyword": "Feature"
});
formatter.before({
  "duration": 10202242300,
  "status": "passed"
});
formatter.scenario({
  "line": 4,
  "name": "Search for a product and verify the list count",
  "description": "",
  "id": "amazon-shopping-cart-functionality;search-for-a-product-and-verify-the-list-count",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 2,
      "name": "@Scenario1"
    },
    {
      "line": 3,
      "name": "@all"
    }
  ]
});
formatter.step({
  "line": 5,
  "name": "I navigate to \"https://www.amazon.com/\"",
  "keyword": "Given "
});
formatter.step({
  "line": 6,
  "name": "I search for the product \"iphone 8\"",
  "keyword": "Given "
});
formatter.step({
  "line": 7,
  "name": "I read the list of product descriptions on page 1",
  "keyword": "Then "
});
formatter.step({
  "comments": [
    {
      "line": 8,
      "value": "#Then I validate if search results count is 19"
    }
  ],
  "line": 9,
  "name": "I validate if following products are found in the page",
  "rows": [
    {
      "cells": [
        "productDescription"
      ],
      "line": 10
    },
    {
      "cells": [
        "Apple iPhone 8 (64GB) - Silver [Locked to Simple Mobile Prepaid]"
      ],
      "line": 11
    },
    {
      "cells": [
        "Apple iPhone 8, GSM Unlocked, 64GB - Gold (Renewed)"
      ],
      "line": 12
    }
  ],
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
  "duration": 5179678700,
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
  "duration": 3297461100,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "1",
      "offset": 48
    }
  ],
  "location": "shoppingCart_Steps.iReadTheListOfProductDescriptionsOnPage(int)"
});
formatter.result({
  "duration": 4348709300,
  "status": "passed"
});
formatter.match({
  "location": "shoppingCart_Steps.iValidateIfFollowingProductsAreFoundInThePage(DataTable)"
});
formatter.write("Apple iPhone 8 (64GB) - Silver [Locked to Simple Mobile Prepaid] is found in the search results");
formatter.write("Apple iPhone 8, GSM Unlocked, 64GB - Gold (Renewed) is found in the search results");
formatter.result({
  "duration": 8973600,
  "status": "passed"
});
formatter.after({
  "duration": 15797100,
  "status": "passed"
});
});