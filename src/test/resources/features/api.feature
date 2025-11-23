Feature: View CRUD endpoints of a demo PHP API (MAMP)

  @CRUD1
  Scenario: Get product details by ID
    When Send GET request to read product with id 1
    And Product name should be "Bamboo Thermal Ski Coat"
    And Product price should be 99
    Then Status code should be 200

  @CRUD2
  Scenario: Get all products
    When Send GET request to read all products
    Then Status code should be 200

  @CRUD3
  Scenario: View categories
    When Send GET request to read categories
    Then Status code should be 200

  @CRUD4
  Scenario: Update product
    Given Product payload is prepared
      | id | name                           | description                                                                                     | price | category_id |
      | 17 | Magnesium 250 mg (200 tablets) | Magnesium is critical to many bodily processes, and supports nerve, muscle, and heart function. | 22    | 2           |
    When Send PUT request to update product
    And Message should be "Product updated"
    Then Status code should be 200

  @CRUD5
  Scenario: Create product
    Given Product payload is prepared
      | name         | description                        | price | category_id |
      | Water Bottle | Blue water bottle. Holds 64 ounces | 14    | 3           |
    When Send POST request to create product
    And Message should be "Product was created."
    Then Status code should be 201

  @CRUD6
  Scenario: Delete last product
    When Send DELETE request to delete product
    And Message should be "Product was deleted."
    Then Status code should be 200

  @CRUD7
  Scenario: Get product by id returns expected headers
    When Send GET request to read product with id 1
    Then Response should contain expected headers for product read API