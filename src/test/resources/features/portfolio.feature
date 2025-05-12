Feature: Portfolio management

  Scenario: User pulls default portfolio
    When "GET" request is sent to endpoint "/cv" with no body
    Then Response status code should be 200
    And Response body should be:
    """
    {
      "id" : 3,
      "shortDescription" : "Backend specialist with leadership experience",
      "dataMatrix" : [ {
        "name" : "Skills",
        "values" : [ "Kotlin", "Spring Boot", "MongoDB" ]
      }, {
        "name" : "Experience",
        "values" : [ "E-commerce Platform", "IT Consulting" ]
      } ]
    }
    """
    
  Scenario: User pulls specific portfolio
    When "GET" request is sent to endpoint "/cv/2" with no body
    Then Response status code should be 200
    And Response body should be:
    """
    {
      "id" : 2,
      "shortDescription" : "Full Stack Developer with startup experience",
      "dataMatrix" : [ {
        "name" : "Skills",
        "values" : [ "Java", "JavaScript", "React" ]
      }, {
        "name" : "Experience",
        "values" : [ "Tech Startup" ]
      } ]
    }
    """

  Scenario: User pulls non-existent portfolio
    When "GET" request is sent to endpoint "/cv/999" with no body
    Then Response status code should be 404
    And Response body should be:
    """
    {
      "error": "CV not found",
      "status": 404
    }
    """
    