@validation
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
       "progress" : 35,
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
                "weight" : 10,
                "endpoint" : "skills"
             },
             "errors" : [
                "List must not be empty"
             ]
          },
          {
             "validationStatus" : "INVALID",
             "domain" : {
                "title" : "Education",
                "weight" : 20,
                "endpoint" : "education"
             },
             "errors" : [
                "Education list must not be empty"
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
                "weight" : 5,
                "endpoint" : "hobbies"
             },
             "errors" : [

             ]
          },
          {
             "validationStatus" : "INVALID",
             "domain" : {
                "title" : "Languages",
                "weight" : 5,
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

  Scenario: Skill experience validation fails when level is not provided
    When "POST" request is sent to endpoint "/skills/domains" with body:
    """
    JVM
    """
    When "POST" request is sent to endpoint "/resume/edit/4/skills" with body:
    """
    {
      "name": "Kotlin",
      "description": "JVM Language",
      "level": 3,
      "domains": ["JVM"]
    }
    """
    Then Response status code should be 201
    When "POST" request is sent to endpoint "/resume/4/validate/experience/skills" with body:
    """
    [
      {
        "name": "Kotlin",
        "detail": "Solid production experience",
        "domains": ["JVM"]
      }
    ]
    """
    Then Response body should be:
    """
    {
       "isValid" : false,
       "domain" : "skillExperience",
       "errors" : [
          "Experience Skill Level must be between 1 and 5"
       ]
    }
    """
