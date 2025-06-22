Feature: Resume data read from server

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
             {
                "name" : "Kotlin",
                "description" : "JVM"
             },
             {
                "name" : "Spring Boot",
                "description" : "Backend Framework"
             },
             {
                "name" : "MongoDB",
                "description" : "Database"
             }
          ]
       },
       "experience" : {
          "name" : "Experience",
          "values" : [
             {
                "name" : "Backend Developer",
                "description" : "Developed and maintained high-load microservices architecture, implementing efficient caching strategies and optimizing database performance"
             },
             {
                "name" : "Technical Lead",
                "description" : "Led multiple development teams, architected solution designs, and implemented best practices for code quality and development processes"
             }
          ]
       },
       "business" : {
          "name" : "Business",
          "values" : [
             {
                "name" : "E-commerce Platform",
                "description" : "Developed and maintained high-load microservices architecture, implementing efficient caching strategies and optimizing database performance"
             },
             {
                "name" : "IT Consulting",
                "description" : "Led multiple development teams, architected solution designs, and implemented best practices for code quality and development processes"
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
    