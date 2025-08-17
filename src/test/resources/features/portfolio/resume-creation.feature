Feature: Resume creation

  Scenario: For given user no history exists, user should be able to initiate resume creation
    Given User is authorized with token: "candidate_empty"
    When "GET" request is sent to endpoint "/portfolio/history" with no body
    Then Response status code should be 404
    And Response body should be:
    """
    {
      "error": "Resume History not found",
      "status": 404
    }
    """
    When "POST" request is sent to endpoint "/portfolio/edit/init" with body:
    """
    {
      "title": "My Professional Resume",
      "summary": "Experienced software developer with strong background in web technologies",
      "image": {
        "name": "My image",
        "src": "123.jpg"
      }
    }
    """
    Then Response status code should be 201
    And Response body should be:
    """
    true
    """
    When "GET" request is sent to endpoint "/portfolio/history" with no body
    Then Response status code should be 200
    And Response body should be:
    """
    {
       "defaultPortfolio" : {
          "id" : 4,
          "title" : "My Professional Resume",
          "summary" : "Experienced software developer with strong background in web technologies",
          "version" : 1,
          "state" : "PUBLISHED"
       },
       "history" : [
          {
             "id" : 4,
             "title" : "My Professional Resume",
             "summary" : "Experienced software developer with strong background in web technologies",
             "version" : 1,
             "state" : "PUBLISHED"
          }
       ]
    }
    """
    Given All resumes are deleted from database
    Then Restore DB resumes

  Scenario: Unauthorized user tries to initiate resume creation
    When "POST" request is sent to endpoint "/portfolio/edit/init" with body:
    """
    {
      "title": "My Professional Resume",
      "summary": "Experienced software developer with strong background in web technologies",
      "image": {
        "name": "My image",
        "src": "123.jpg"
      }
    }
    """
    Then Response status code should be 401
    And Response body should be:
    """
    {
      "error": "Anonymous access is restricted to this endpoint",
      "status": 401
    }
    """

  Scenario: User with insufficient role tries to initiate resume creation
    Given User is authorized with token: "user"
    When "POST" request is sent to endpoint "/portfolio/edit/init" with body:
    """
    {
      "title": "My Professional Resume",
      "summary": "Experienced software developer with strong background in web technologies",
      "image": {
        "name": "My image",
        "src": "123.jpg"
      }
    }
    """
    Then Response status code should be 403
    And Response body should be:
    """
    {
      "error": "Access denied. Required role: ROLE_CANDIDATE",
      "status": 403
    }
    """

  Scenario: Resume creation fails due to title too short
    Given User is authorized with token: "candidate_empty"
    When "POST" request is sent to endpoint "/portfolio/edit/init" with body:
    """
    {
      "title": "Too",
      "summary": "Experienced software developer with strong background in web technologies",
      "image": {
        "name": "My image",
        "src": "123.jpg"
      }
    }
    """
    Then Response status code should be 400
    And Response body should be:
    """
    {
      "error": "Title length must be between 5 and 30",
      "status": 400
    }
    """

  Scenario: Resume creation fails due to title too long
    Given User is authorized with token: "candidate_empty"
    When "POST" request is sent to endpoint "/portfolio/edit/init" with body:
    """
    {
      "title": "This title is way too long for validation and exceeds maximum",
      "summary": "Experienced software developer with strong background in web technologies",
      "image": {
        "name": "My image",
        "src": "123.jpg"
      }
    }
    """
    Then Response status code should be 400
    And Response body should be:
    """
    {
      "error": "Title length must be between 5 and 30",
      "status": 400
    }
    """

  Scenario: Resume creation fails due to summary too short
    Given User is authorized with token: "candidate_empty"
    When "POST" request is sent to endpoint "/portfolio/edit/init" with body:
    """
    {
      "title": "My Professional Resume",
      "summary": "Too short",
      "image": {
        "name": "My image",
        "src": "123.jpg"
      }
    }
    """
    Then Response status code should be 400
    And Response body should be:
    """
    {
      "error": "Summary length must be between 30 and 100",
      "status": 400
    }
    """

  Scenario: Resume creation fails due to summary too long
    Given User is authorized with token: "candidate_empty"
    When "POST" request is sent to endpoint "/portfolio/edit/init" with body:
    """
    {
      "title": "My Professional Resume",
      "summary": "This summary is way too long for validation purposes and exceeds the maximum allowed length significantly making it invalid",
      "image": {
        "name": "My image",
        "src": "123.jpg"
      }
    }
    """
    Then Response status code should be 400
    And Response body should be:
    """
    {
      "error": "Summary length must be between 30 and 100",
      "status": 400
    }
    """

  Scenario: Resume creation fails due to missing title field
    Given User is authorized with token: "candidate_empty"
    When "POST" request is sent to endpoint "/portfolio/edit/init" with body:
    """
    {
      "summary": "Experienced software developer with strong background in web technologies",
      "image": {
        "name": "My image",
        "src": "123.jpg"
      }
    }
    """
    Then Response status code should be 400
    And Response body should be:
    """
    {
      "error": "Request invalid",
      "status": 400
    }
    """

  Scenario: Resume creation fails due to missing summary field
    Given User is authorized with token: "candidate_empty"
    When "POST" request is sent to endpoint "/portfolio/edit/init" with body:
    """
    {
      "title": "My Professional Resume",
      "image": {
        "name": "My image",
        "src": "123.jpg"
      }
    }
    """
    Then Response status code should be 400
    And Response body should be:
    """
    {
      "error": "Request invalid",
      "status": 400
    }
    """

  Scenario: Resume creation fails due to missing image field
    Given User is authorized with token: "candidate_empty"
    When "POST" request is sent to endpoint "/portfolio/edit/init" with body:
    """
    {
      "title": "My Professional Resume",
      "summary": "Experienced software developer with strong background in web technologies"
    }
    """
    Then Response status code should be 400
    And Response body should be:
    """
    {
      "error": "Request invalid",
      "status": 400
    }
    """

  Scenario: Resume creation fails due to empty request body
    Given User is authorized with token: "candidate_empty"
    When "POST" request is sent to endpoint "/portfolio/edit/init" with body:
    """
    """
    Then Response status code should be 400
    And Response body should be:
    """
    {
      "error": "Request invalid",
      "status": 400
    }
    """

  Scenario: Resume creation fails due to malformed JSON
    Given User is authorized with token: "candidate_empty"
    When "POST" request is sent to endpoint "/portfolio/edit/init" with body:
    """
    {
      "title": "My Professional Resume",
      "summary": "Experienced software developer with strong background in web technologies",
      "image": {
        "name": "My image",
        "src": "123.jpg"
      }
    """
    Then Response status code should be 400
    And Response body should be:
    """
    {
      "error": "Request invalid",
      "status": 400
    }
    """

  Scenario: Candidate tries to create resume when already exists
    Given User is authorized with token: "candidate"
    When "POST" request is sent to endpoint "/portfolio/edit/init" with body:
    """
    {
      "title": "My Professional Resume",
      "summary": "Experienced software developer with strong background in web technologies",
      "image": {
        "name": "My image",
        "src": "123.jpg"
      }
    }
    """
    Then Response status code should be 201
    And Response body should be:
    """
    true
    """
    When "GET" request is sent to endpoint "/portfolio/history" with no body
    Then Response status code should be 200
    And Response body should be:
    """
    {
       "defaultPortfolio" : {
          "id" : 3,
          "title" : "Lead Java Developer",
          "summary" : "Experienced Backend Developer and Technical Lead with proven expertise in building scalable distributed systems and leading development teams.",
          "version" : 1,
          "state" : "PUBLISHED"
       },
       "history" : [
          {
             "id" : 3,
             "title" : "Lead Java Developer",
             "summary" : "Experienced Backend Developer and Technical Lead with proven expertise in building scalable distributed systems and leading development teams.",
             "version" : 1,
             "state" : "PUBLISHED"
          },
          {
             "id" : 4,
             "title" : "My Professional Resume",
             "summary" : "Experienced software developer with strong background in web technologies",
             "version" : 2,
             "state" : "DRAFT"
          }
       ]
    }
    """
    Given All resumes are deleted from database
    Then Restore DB resumes

  Scenario: Resume creation with minimum valid values
    Given User is authorized with token: "candidate_empty"
    When "POST" request is sent to endpoint "/portfolio/edit/init" with body:
    """
    {
      "title": "Valid",
      "summary": "This is a valid summary with exactly thirty characters minimum",
      "image": {
        "name": "Test",
        "src": "test.jpg"
      }
    }
    """
    Then Response status code should be 201
    And Response body should be:
    """
    true
    """
    Given All resumes are deleted from database
    Then Restore DB resumes

  Scenario: Resume creation with maximum valid values
    Given User is authorized with token: "candidate_empty"
    When "POST" request is sent to endpoint "/portfolio/edit/init" with body:
    """
    {
      "title": "Maximum length title allowed",
      "summary": "This summary has exactly one hundred characters which is the maximum allowed length for test",
      "image": {
        "name": "Maximum name",
        "src": "maximum.jpg"
      }
    }
    """
    Then Response status code should be 201
    And Response body should be:
    """
    true
    """
    Given All resumes are deleted from database
    Then Restore DB resumes

  Scenario: Unpublish after init and verify success
    Given User is authorized with token: "candidate_empty"
    When "POST" request is sent to endpoint "/portfolio/edit/init" with body:
    """
    {
      "title": "My Professional Resume",
      "summary": "Experienced software developer with strong background in web technologies",
      "image": {
        "name": "My image",
        "src": "123.jpg"
      }
    }
    """
    Then Response status code should be 201
    And Response body should be:
    """
    true
    """
    When "PUT" request is sent to endpoint "/portfolio/edit/1/unpublish" with no body
    Then Response status code should be 200
    And Response body should be:
    """
    true
    """
    When "GET" request is sent to endpoint "/portfolio/history" with no body
    Then Response status code should be 200
    And Response body should be:
    """
    {
       "defaultPortfolio" : null,
       "history" : [
          {
             "id" : 4,
             "title" : "My Professional Resume",
             "summary" : "Experienced software developer with strong background in web technologies",
             "version" : 1,
             "state" : "DRAFT"
          }
       ]
    }
    """
    Given All resumes are deleted from database
    Then Restore DB resumes

  Scenario: Unpublish fails when resume with given version does not exist
    Given User is authorized with token: "candidate"
    When "PUT" request is sent to endpoint "/portfolio/edit/999/unpublish" with no body
    Then Response status code should be 404
    And Response body should be:
    """
    {
      "error": "CV not found",
      "status": 404
    }
    """
    Given All resumes are deleted from database
    Then Restore DB resumes

  Scenario: Unpublish fails when version exists but is not in PUBLISHED state
    Given User is authorized with token: "candidate"
    When "GET" request is sent to endpoint "/portfolio/history" with no body
    Then Response status code should be 200
    And Response body should be:
    """
    {
       "defaultPortfolio" : {
          "id" : 3,
          "title" : "Lead Java Developer",
          "summary" : "Experienced Backend Developer and Technical Lead with proven expertise in building scalable distributed systems and leading development teams.",
          "version" : 1,
          "state" : "PUBLISHED"
       },
       "history" : [
          {
             "id" : 3,
             "title" : "Lead Java Developer",
             "summary" : "Experienced Backend Developer and Technical Lead with proven expertise in building scalable distributed systems and leading development teams.",
             "version" : 1,
             "state" : "PUBLISHED"
          }
       ]
    }
    """
    When "PUT" request is sent to endpoint "/portfolio/edit/1/unpublish" with no body
    Then Response status code should be 200
    When "PUT" request is sent to endpoint "/portfolio/edit/1/unpublish" with no body
    Then Response status code should be 400
    And Response body should be:
    """
    {
      "error": "Provided version 1 does not match PUBLISHED version",
      "status": 400
    }
    """
    Given All resumes are deleted from database
    Then Restore DB resumes