Feature: CV View functionality

  Scenario: Default print
    When "GET" request is sent to endpoint "/pdf" with no body
    Then Response status code should be 200
    And Response body should be:
    """
    html 1
    """

  Scenario: Default print doesn't exist
    Given All resumes are deleted from database
    When "GET" request is sent to endpoint "/pdf" with no body
    Then Response status code should be 404
    And Response body should be:
    """
    {
    "error": "CV not found",
    "status": 404
    }
    """
    Then Restore DB resumes

  Scenario: Candidate Default print
    Given User is authorized with token: "candidate"
    When "GET" request is sent to endpoint "/pdf" with no body
    Then Response status code should be 200
    And Response body should be:
    """
    html 3
    """

  Scenario: Candidate cannot view others prints
    Given User is authorized with token: "candidate"
    When "GET" request is sent to endpoint "/pdf/1" with no body
    Then Response status code should be 404
    And Response body should be:
    """
    {
    "error": "CV not found",
    "status": 404
    }
    """

  Scenario: Get default print as admin
    Given User is authorized with token: "admin"
    When "GET" request is sent to endpoint "/pdf" with no body
    Then Response status code should be 200
    And Response body should be:
    """
    html 1
    """

  Scenario: Get candidate print
    Given User is authorized with token: "admin"
    When "GET" request is sent to endpoint "/pdf/3" with no body
    Then Response status code should be 200
    And Response body should be:
    """
    html 3
    """