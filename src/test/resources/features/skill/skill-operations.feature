Feature: Skill Operations

  Background:
    Given User is authorized with token: "admin"

  Scenario: Successfully create a skill
    When "POST" request is sent to endpoint "/skills" with body:
    """
    {
      "name": "React",
      "description": "JS framework",
      "level": 3,
      "domains": []
    }
    """
    Then Response status code should be 201

  Scenario: Skill creation failed when already exists for user
    When "POST" request is sent to endpoint "/skills" with body:
    """
    {
      "name": "Kotlin",
      "description": "JVM language",
      "level": 4,
      "domains": []
    }
    """
    Then Response status code should be 400
    And Response body should be:
    """
    {
      "error": "Skill with name Kotlin already exists",
      "status": 400
    }
    """

  Scenario: Skill Domain creation successful
    When "POST" request is sent to endpoint "/skills/domains" with body:
    """
    Databases
    """
    Then Response status code should be 201

  Scenario: Skill Domain creation failed when already exists for user
    When "POST" request is sent to endpoint "/skills/domains" with body:
    """
    Databases
    """
    Then Response status code should be 201
    When "POST" request is sent to endpoint "/skills/domains" with body:
    """
    Databases
    """
    Then Response status code should be 400
    And Response body should be:
    """
    {
      "error": "Skill Domain with name Databases already exists",
      "status": 400
    }
    """