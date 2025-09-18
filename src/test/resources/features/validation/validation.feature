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
       "progress" : 50,
       "validationResults" : [
          {
             "validationStatus" : "INVALID",
             "domain" : {
                "title" : "Shortcut",
                "weight" : 10,
                "endpoint" : ""
             },
             "errors" : [
                "Summary length must be between 30 and 100"
             ]
          },
          {
             "validationStatus" : "VALID",
             "domain" : {
                "title" : "Skills",
                "weight" : 15,
                "endpoint" : "skills"
             },
             "errors" : [

             ]
          },
          {
             "validationStatus" : "INVALID",
             "domain" : {
                "title" : "Experience",
                "weight" : 30,
                "endpoint" : "experience"
             },
             "errors" : [
                "Detail must be at least 10 characters"
             ]
          },
          {
             "validationStatus" : "VALID",
             "domain" : {
                "title" : "Side projects",
                "weight" : 20,
                "endpoint" : "side-projects"
             },
             "errors" : [

             ]
          },
          {
             "validationStatus" : "INVALID",
             "domain" : {
                "title" : "Hobbies",
                "weight" : 10,
                "endpoint" : "hobbies"
             },
             "errors" : [
                "Hobby name must be capitalized"
             ]
          },
          {
             "validationStatus" : "VALID",
             "domain" : {
                "title" : "Languages",
                "weight" : 15,
                "endpoint" : "languages"
             },
             "errors" : [

             ]
          }
       ]
    }
    """