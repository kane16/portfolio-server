Feature: Resume validation

  Background: User authorized as admin
    Given User is authorized with token: "candidate_empty"
    When "POST" request is sent to endpoint "/resume/edit/init" with body:
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
    When "POST" request is sent to endpoint "/resume/4/validate" with no body
    Then Response status code should be 200
    And Response body should be:
    """
    {
       "isValid" : false,
       "progress" : 40,
       "validationResults" : [
          {
             "validationStatus" : "VALID",
             "domain" : {
                "title" : "Shortcut",
                "weight" : 10,
                "endpoint" : ""
             },
             "errors" : [

             ]
          },
          {
             "validationStatus" : "INVALID",
             "domain" : {
                "title" : "Skills",
                "weight" : 15,
                "endpoint" : "skills"
             },
             "errors" : [
                "List must not be empty"
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
                "List must not be empty"
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
             "validationStatus" : "VALID",
             "domain" : {
                "title" : "Hobbies",
                "weight" : 10,
                "endpoint" : "hobbies"
             },
             "errors" : [

             ]
          },
          {
             "validationStatus" : "INVALID",
             "domain" : {
                "title" : "Languages",
                "weight" : 15,
                "endpoint" : "languages"
             },
             "errors" : [
                "At least two languages are required"
             ]
          }
       ]
    }
    """

  Scenario: Resume validation for partially completed resume
    When "POST" request is sent to endpoint "/resume/4/validate/experience/business" with body:
    """
    Bank
    """
    Then Response body should be:
    """
    {
       "isValid" : true,
       "domain" : "business",
       "errors" : [

       ]
    }
    """