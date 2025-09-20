Feature: Skill Operations

  Background:
    Given User is authorized with token: "admin"

  Scenario: Skill Domain creation successful
    When "POST" request is sent to endpoint "/skills/domains" with body:
    """
    Databases
    """
    Then Response status code should be 201

  Scenario: Skill Domain creation failed when already exists for user
    When "POST" request is sent to endpoint "/skills/domains" with body:
    """
    JVM
    """
    Then Response status code should be 201
    When "POST" request is sent to endpoint "/skills/domains" with body:
    """
    JVM
    """
    Then Response status code should be 400
    And Response body should be:
    """
    {
      "error": "Skill Domain with name JVM already exists",
      "status": 400
    }
    """