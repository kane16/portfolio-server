Feature: Portfolio management

  Scenario: User pulls default portfolio
    When "GET" request is sent to endpoint "/cv" with no body
    Then Response status code should be 200
    And Response body should be:
    """
    {
       "id" : 3,
       "title" : "Lead Java Developer",
       "description" : "Experienced Backend Developer and Technical Lead with proven expertise in building scalable distributed systems and leading development teams.",
       "skills" : {
          "name" : "Skills",
          "values" : [
             "Kotlin",
             "Spring Boot",
             "MongoDB"
          ]
       },
       "experience" : {
          "name" : "Experience",
          "values" : [
             "Backend Developer",
             "Technical Lead"
          ]
       },
       "business" : {
          "name" : "Business",
          "values" : [
             "E-commerce Platform",
             "IT Consulting"
          ]
       },
       "languages" : {
          "name" : "Languages",
          "values" : [
             "English",
             "Spanish",
             "French"
          ]
       }
    }
    """
    
  Scenario: User pulls specific portfolio
    When "GET" request is sent to endpoint "/cv/2" with no body
    Then Response status code should be 200
    And Response body should be:
    """
    {
       "id" : 2,
       "title" : "Lead Java Developer",
       "description" : "Full Stack Developer with strong focus on modern web technologies and startup environment experience. Passionate about creating efficient and user-friendly applications.",
       "skills" : {
          "name" : "Skills",
          "values" : [
             "Java",
             "JavaScript",
             "React"
          ]
       },
       "experience" : {
          "name" : "Experience",
          "values" : [
             "Full Stack Developer"
          ]
       },
       "business" : {
          "name" : "Business",
          "values" : [
             "Tech Startup"
          ]
       },
       "languages" : {
          "name" : "Languages",
          "values" : [
             "English",
             "Spanish",
             "French"
          ]
       }
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
    