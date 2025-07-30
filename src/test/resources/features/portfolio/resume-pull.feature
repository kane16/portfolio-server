Feature: Resume data read from server

  Scenario: Guest pulls default portfolio
    When "POST" request is sent to endpoint "/portfolio" with no body
    Then Response status code should be 200
    And Response body should be:
    """
    {
       "id" : 1,
       "fullname" : "Łukasz Gumiński",
       "imageSource" : "/images/lg.jpg",
       "title" : "Lead Java Developer",
       "summary" : "Senior Java Developer with extensive experience in banking software development. Specialized in building robust, secure, and scalable applications.",
       "skills" : [
          {
             "name" : "Java",
             "description" : "JVM",
             "level" : 5
          },
          {
             "name" : "Kotlin",
             "description" : "JVM",
             "level" : 4
          },
          {
             "name" : "Scala",
             "description" : "JVM",
             "level" : 3
          }
       ],
       "languages" : [
          {
             "name" : "English",
             "level" : "C1"
          },
          {
             "name" : "Spanish",
             "level" : "B2"
          },
          {
             "name" : "French",
             "level" : "A2"
          }
       ],
       "sideProjects" : [

       ],
       "workHistory" : [
          {
             "position" : "Senior Developer",
             "business" : "Bank",
             "summary" : "Lead developer for core banking systems",
             "description" : "Development of core banking applications, implementing secure transaction processing systems and leading integration projects with external financial services",
             "timespan" : {
                "start" : "2023.01",
                "end" : "2025.05"
             },
             "skills" : [
                {
                   "name" : "Java",
                   "description" : "JVM",
                   "level" : 5
                },
                {
                   "name" : "Kotlin",
                   "description" : "JVM",
                   "level" : 4
                }
             ]
          }
       ],
       "hobbies" : [
          "Music Production",
          "Open Source Contributing",
          "Yoga"
       ],
       "resumeId" : 1
    }
    """

  Scenario: Admin pulls specific portfolio
    Given User is authorized with token: "admin"
    When "POST" request is sent to endpoint "/portfolio/2" with no body
    Then Response status code should be 200
    And Response body should be:
    """
    {
       "id" : 2,
       "fullname" : "Łukasz Gumiński",
       "imageSource" : "/images/lg.jpg",
       "title" : "Lead Java Developer",
       "summary" : "Full Stack Developer with strong focus on modern web technologies and startup environment experience. Passionate about creating efficient and user-friendly applications.",
       "skills" : [
          {
             "name" : "Java",
             "description" : "JVM",
             "level" : 4
          },
          {
             "name" : "JavaScript",
             "description" : "Web Development",
             "level" : 5
          },
          {
             "name" : "React",
             "description" : "Frontend Framework",
             "level" : 5
          }
       ],
       "languages" : [
          {
             "name" : "English",
             "level" : "C1"
          },
          {
             "name" : "Spanish",
             "level" : "B2"
          },
          {
             "name" : "French",
             "level" : "A2"
          }
       ],
       "sideProjects" : [

       ],
       "workHistory" : [
          {
             "position" : "Full Stack Developer",
             "business" : "Tech Startup",
             "summary" : "Full-stack development of modern web applications",
             "description" : "Designed and implemented full-stack solutions using React and Spring Boot, including real-time data processing features and responsive UI components",
             "timespan" : {
                "start" : "2024.01",
                "end" : "2025.06"
             },
             "skills" : [
                {
                   "name" : "JavaScript",
                   "description" : "Web Development",
                   "level" : 5
                },
                {
                   "name" : "React",
                   "description" : "Frontend Framework",
                   "level" : 5
                }
             ]
          }
       ],
       "hobbies" : [
          "Music Production",
          "Open Source Contributing",
          "Yoga"
       ],
       "resumeId" : 2
    }
    """

  Scenario: Guest tries to pull specific cv
    When "POST" request is sent to endpoint "/portfolio/2" with no body
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
    When "POST" request is sent to endpoint "/portfolio/999" with no body
    Then Response status code should be 404
    And Response body should be:
    """
    {
      "error": "CV not found",
      "status": 404
    }
    """

  Scenario: Candidate pulls portfolio history
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

  Scenario: Unauthorized user tries to access portfolio history
    When "GET" request is sent to endpoint "/portfolio/history" with no body
    Then Response status code should be 401
    And Response body should be:
      """
      {
        "error": "Anonymous access is restricted to this endpoint",
        "status": 401
      }
      """

  Scenario: User tries to access portfolio history
    Given User is authorized with token: "user"
    When "GET" request is sent to endpoint "/portfolio/history" with no body
    Then Response status code should be 403
    And Response body should be:
      """
      {
        "error": "Access denied. Required role: ROLE_CANDIDATE",
        "status": 403
      }
      """

  Scenario: Candidate with no portfolio history
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

    