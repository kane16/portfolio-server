Feature: Author Registration

  Scenario: Register author for user succeeds when admin provides valid user id
    Given User is authorized with token: "admin"
    When "POST" request is sent to endpoint "/authors/register/users/200" with body:
    """
    {
      "firstname": "Jane",
      "lastname": "Doe",
      "email": "jane.doe@example.com"
    }
    """
    Then Response status code should be 200
    And Response body should be:
    """
    {
      "firstname": "Jane",
      "lastname": "Doe",
      "username": "jane",
      "email": "jane@example.com",
      "roles": ["ROLE_USER"]
    }
    """

  Scenario: Register author for user fails when username already exists
    Given User is authorized with token: "admin"
    When "POST" request is sent to endpoint "/authors/register/users/200" with body:
    """
    {
      "firstname": "Jane",
      "lastname": "Doe",
      "email": "jane.doe@example.com"
    }
    """
    And "POST" request is sent to endpoint "/authors/register/users/200" with body:
    """
    {
      "firstname": "Jane",
      "lastname": "Doe",
      "email": "jane.doe@example.com"
    }
    """
    Then Response status code should be 400
    And Response body should be:
    """
    {
      "message": "Author already exists",
      "status": 400
    }
    """

  Scenario: Register author for user fails when user does not exist
    Given User is authorized with token: "admin"
    When "POST" request is sent to endpoint "/authors/register/users/999" with body:
    """
    {
      "firstname": "Ghost",
      "lastname": "User",
      "email": "ghost@example.com"
    }
    """
    Then Response status code should be 404
    And Response body should be:
    """
    {
      "message": "User with id 999 not found",
      "status": 404
    }
    """

  Scenario: Register author for user fails when caller is not admin
    Given User is authorized with token: "user"
    When "POST" request is sent to endpoint "/authors/register/users/200" with body:
    """
    {
      "firstname": "Jane",
      "lastname": "Doe",
      "email": "jane.doe@example.com"
    }
    """
    Then Response status code should be 403
    And Response body should be:
    """
    {
      "message": "Access denied. Required role: ROLE_ADMIN",
      "status": 403
    }
    """

  Scenario: Register author for user fails when firstname is blank
    Given User is authorized with token: "admin"
    When "POST" request is sent to endpoint "/authors/register/users/200" with body:
    """
    {
      "firstname": "",
      "lastname": "Doe",
      "email": "jane.doe@example.com"
    }
    """
    Then Response status code should be 400
    And Response body should be:
    """
    {
      "message": "Firstname must not be empty",
      "status": 400
    }
    """

  Scenario: Edit author for user succeeds when admin updates existing author
    Given User is authorized with token: "admin"
    When "POST" request is sent to endpoint "/authors/register/users/200" with body:
    """
    {
      "firstname": "Jane",
      "lastname": "Doe",
      "email": "jane.doe@example.com"
    }
    """
    And "PUT" request is sent to endpoint "/authors/edit/users/200" with body:
    """
    {
      "firstname": "Janet",
      "lastname": "Smith",
      "email": "janet.smith@example.com"
    }
    """
    Then Response status code should be 200
    And Response body should be:
    """
    {
      "firstname": "Janet",
      "lastname": "Smith",
      "username": "jane",
      "email": "jane@example.com",
      "roles": ["ROLE_USER"]
    }
    """

  Scenario: Edit author for user fails when author does not exist
    Given User is authorized with token: "admin"
    When "PUT" request is sent to endpoint "/authors/edit/users/200" with body:
    """
    {
      "firstname": "Janet",
      "lastname": "Smith",
      "email": "janet.smith@example.com"
    }
    """
    Then Response status code should be 404
    And Response body should be:
    """
    {
      "message": "Author not found",
      "status": 404
    }
    """

  Scenario: Edit author for user fails when caller is not admin
    Given User is authorized with token: "user"
    When "PUT" request is sent to endpoint "/authors/edit/users/200" with body:
    """
    {
      "firstname": "Janet",
      "lastname": "Smith",
      "email": "janet.smith@example.com"
    }
    """
    Then Response status code should be 403
    And Response body should be:
    """
    {
      "message": "Access denied. Required role: ROLE_ADMIN",
      "status": 403
    }
    """

  Scenario: Get all authors succeeds when admin requests author list
    Given All resumes are deleted from database
    And User is authorized with token: "admin"
    When "POST" request is sent to endpoint "/authors/register/users/200" with body:
    """
    {
      "firstname": "Jane",
      "lastname": "Doe",
      "email": "jane.doe@example.com"
    }
    """
    And "GET" request is sent to endpoint "/authors" with no body
    Then Response status code should be 200
    And Response body should be:
    """
    [
      {
        "firstname": "Jane",
        "lastname": "Doe",
        "username": "jane",
        "email": "jane@example.com",
        "roles": ["ROLE_USER"]
      }
    ]
    """

  Scenario: Get all authors returns empty list when there are no authors
    Given All resumes are deleted from database
    And User is authorized with token: "admin"
    When "GET" request is sent to endpoint "/authors" with no body
    Then Response status code should be 200
    And Response body should be:
    """
    []
    """

  Scenario: Get all authors fails when caller is not admin
    Given User is authorized with token: "user"
    When "GET" request is sent to endpoint "/authors" with no body
    Then Response status code should be 403
    And Response body should be:
    """
    {
      "message": "Access denied. Required role: ROLE_ADMIN",
      "status": 403
    }
    """
