Feature: Resume validation

  Background: User authorized as admin
    Given User is authorized with token: "admin"

  Scenario: Resume validation for partially completed resume
    When "GET" request is sent to endpoint "/resume/1/validate" with no body
    Then Response status code should be 200
    And Response body should be:
    """
    {
       "isValid" : false,
       "progress" : 0.5,
       "validationResults" : [
          {
             "validationStatus" : "INVALID",
             "domain" : "SHORTCUT",
             "errors" : [
                "Summary length must be between 30 and 100"
             ]
          },
          {
             "validationStatus" : "VALID",
             "domain" : "SKILLS",
             "errors" : [

             ]
          },
          {
             "validationStatus" : "INVALID",
             "domain" : "EXPERIENCES",
             "errors" : [
                "Detail must be at least 10 characters"
             ]
          },
          {
             "validationStatus" : "VALID",
             "domain" : "SIDE_PROJECTS",
             "errors" : [

             ]
          },
          {
             "validationStatus" : "INVALID",
             "domain" : "HOBBIES",
             "errors" : [
                "Hobby name must be capitalized"
             ]
          },
          {
             "validationStatus" : "VALID",
             "domain" : "LANGUAGES",
             "errors" : [

             ]
          }
       ]
    }
    """