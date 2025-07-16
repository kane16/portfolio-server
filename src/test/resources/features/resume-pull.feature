Feature: Resume data read from server

  Scenario: Guest pulls default portfolio
    When "GET" request is sent to endpoint "/portfolio" with no body
    Then Response status code should be 200
    And Response body should be:
    """
    {
       "id" : 1,
       "title" : "Lead Java Developer",
       "description" : "Senior Java Developer with extensive experience in banking software development. Specialized in building robust, secure, and scalable applications.",
       "skills" : {
          "name" : "Skills",
          "values" : [
             {
                "name" : "Java",
                "description" : "JVM"
             },
             {
                "name" : "Kotlin",
                "description" : "JVM"
             },
             {
                "name" : "Scala",
                "description" : "JVM"
             }
          ]
       },
       "experience" : {
          "name" : "Experience",
          "values" : [
             {
                "name" : "Senior Developer",
                "description" : "Development of core banking applications, implementing secure transaction processing systems and leading integration projects with external financial services"
             }
          ]
       },
       "business" : {
          "name" : "Business",
          "values" : [
             {
                "name" : "Bank",
                "description" : "Development of core banking applications, implementing secure transaction processing systems and leading integration projects with external financial services"
             }
          ]
       },
       "languages" : {
          "name" : "Languages",
          "values" : [
             {
                "name" : "English",
                "description" : "C1"
             },
             {
                "name" : "Spanish",
                "description" : "B2"
             },
             {
                "name" : "French",
                "description" : "A2"
             }
          ]
       }
    }
    """

  Scenario: Admin pulls specific portfolio
    Given User is authorized with token: "admin"
    When "GET" request is sent to endpoint "/portfolio/2" with no body
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
             {
                "name" : "Java",
                "description" : "JVM"
             },
             {
                "name" : "JavaScript",
                "description" : "Web Development"
             },
             {
                "name" : "React",
                "description" : "Frontend Framework"
             }
          ]
       },
       "experience" : {
          "name" : "Experience",
          "values" : [
             {
                "name" : "Full Stack Developer",
                "description" : "Designed and implemented full-stack solutions using React and Spring Boot, including real-time data processing features and responsive UI components"
             }
          ]
       },
       "business" : {
          "name" : "Business",
          "values" : [
             {
                "name" : "Tech Startup",
                "description" : "Designed and implemented full-stack solutions using React and Spring Boot, including real-time data processing features and responsive UI components"
             }
          ]
       },
       "languages" : {
          "name" : "Languages",
          "values" : [
             {
                "name" : "English",
                "description" : "C1"
             },
             {
                "name" : "Spanish",
                "description" : "B2"
             },
             {
                "name" : "French",
                "description" : "A2"
             }
          ]
       }
    }
    """

  Scenario: Guest tries to pull specific cv
    When "GET" request is sent to endpoint "/portfolio/2" with no body
    Then Response status code should be 401
    And Response body should be:
    """
    {
      "error": "Anonymous access is restricted to this endpoint",
      "status": 401
    }
    """

  Scenario: Admin user pulls non-existent portfolio
    Given User is authorized with token: "admin"
    When "GET" request is sent to endpoint "/portfolio/999" with no body
    Then Response status code should be 404
    And Response body should be:
    """
    {
      "error": "CV not found",
      "status": 404
    }
    """
    