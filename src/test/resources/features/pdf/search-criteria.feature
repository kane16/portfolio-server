Feature: CV View with search

  Background: User is authorized with token: "admin"
    Given User is authorized with token: "admin"

  Scenario: Search in default portfolio by no specified search criteria
    When "GET" request is sent to endpoint "/pdf/3" with no body
    Then Response status code should be 200
    And Response body should be:
    """
    {
       "id" : 3,
       "fullname" : "Łukasz Gumiński",
       "imageSource" : "",
       "title" : "Lead Java Developer",
       "summary" : "Experienced Backend Developer and Technical Lead with proven expertise in building scalable distributed systems and leading development teams.",
       "skills" : [
          {
             "name" : "Kotlin",
             "description" : "JVM",
             "level" : 4
          },
          {
             "name" : "Spring Boot",
             "description" : "Backend Framework",
             "level" : 5
          },
          {
             "name" : "MongoDB",
             "description" : "Database",
             "level" : 4
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
             "position" : "Backend Developer",
             "business" : "E-commerce Platform",
             "summary" : "Backend development for high-load e-commerce platform",
             "description" : "Developed and maintained high-load microservices architecture, implementing efficient caching strategies and optimizing database performance",
             "timespan" : {
                "start" : "2022.06",
                "end" : "2024.01"
             },
             "skills" : [
                {
                   "name" : "Kotlin",
                   "description" : "JVM",
                   "level" : 5
                },
                {
                   "name" : "MongoDB",
                   "description" : "Database",
                   "level" : 4
                }
             ]
          },
          {
             "position" : "Technical Lead",
             "business" : "IT Consulting",
             "summary" : "Technical leadership and architecture design",
             "description" : "Led multiple development teams, architected solution designs, and implemented best practices for code quality and development processes",
             "timespan" : {
                "start" : "2024.01",
                "end" : "2025.07"
             },
             "skills" : [
                {
                   "name" : "Kotlin",
                   "description" : "JVM",
                   "level" : 5
                },
                {
                   "name" : "Spring Boot",
                   "description" : "Backend Framework",
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
       "resumeId" : 3
    }
    """

  Scenario: Search in default portfolio by skills
    When "GET" request is sent to endpoint "/pdf/3?skills=MongoDB" with no body
    Then Response status code should be 200
    And Response body should be:
    """
    {
       "id" : 3,
       "fullname" : "Łukasz Gumiński",
       "imageSource" : "",
       "title" : "Lead Java Developer",
       "summary" : "Experienced Backend Developer and Technical Lead with proven expertise in building scalable distributed systems and leading development teams.",
       "skills" : [
          {
             "name" : "MongoDB",
             "description" : "Database",
             "level" : 4
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
             "position" : "Backend Developer",
             "business" : "E-commerce Platform",
             "summary" : "Backend development for high-load e-commerce platform",
             "description" : "Developed and maintained high-load microservices architecture, implementing efficient caching strategies and optimizing database performance",
             "timespan" : {
                "start" : "2022.06",
                "end" : "2024.01"
             },
             "skills" : [
                {
                   "name" : "Kotlin",
                   "description" : "JVM",
                   "level" : 5
                },
                {
                   "name" : "MongoDB",
                   "description" : "Database",
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
       "resumeId" : 3
    }
    """

  Scenario: Search in default portfolio by business
    When "GET" request is sent to endpoint "/pdf/3?business=IT+Consulting" with no body
    Then Response status code should be 200
    And Response body should be:
    """
    {
       "id" : 3,
       "fullname" : "Łukasz Gumiński",
       "imageSource" : "",
       "title" : "Lead Java Developer",
       "summary" : "Experienced Backend Developer and Technical Lead with proven expertise in building scalable distributed systems and leading development teams.",
       "skills" : [
          {
             "name" : "Kotlin",
             "description" : "JVM",
             "level" : 4
          },
          {
             "name" : "Spring Boot",
             "description" : "Backend Framework",
             "level" : 5
          },
          {
             "name" : "MongoDB",
             "description" : "Database",
             "level" : 4
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
             "position" : "Technical Lead",
             "business" : "IT Consulting",
             "summary" : "Technical leadership and architecture design",
             "description" : "Led multiple development teams, architected solution designs, and implemented best practices for code quality and development processes",
             "timespan" : {
                "start" : "2024.01",
                "end" : "2025.07"
             },
             "skills" : [
                {
                   "name" : "Kotlin",
                   "description" : "JVM",
                   "level" : 5
                },
                {
                   "name" : "Spring Boot",
                   "description" : "Backend Framework",
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
       "resumeId" : 3
    }
    """

  Scenario: Search in default portfolio by tech domain
    When "GET" request is sent to endpoint "/pdf/3?domains=Database" with no body
    Then Response status code should be 200
    And Response body should be:
    """
    {
       "id" : 3,
       "fullname" : "Łukasz Gumiński",
       "imageSource" : "",
       "title" : "Lead Java Developer",
       "summary" : "Experienced Backend Developer and Technical Lead with proven expertise in building scalable distributed systems and leading development teams.",
       "skills" : [
          {
             "name" : "MongoDB",
             "description" : "Database",
             "level" : 4
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
             "position" : "Backend Developer",
             "business" : "E-commerce Platform",
             "summary" : "Backend development for high-load e-commerce platform",
             "description" : "Developed and maintained high-load microservices architecture, implementing efficient caching strategies and optimizing database performance",
             "timespan" : {
                "start" : "2022.06",
                "end" : "2024.01"
             },
             "skills" : [
                {
                   "name" : "Kotlin",
                   "description" : "JVM",
                   "level" : 5
                },
                {
                   "name" : "MongoDB",
                   "description" : "Database",
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
       "resumeId" : 3
    }
    """

  Scenario: Search in default portfolio by complex filter
      When "GET" request is sent to endpoint "/pdf/3?domains=Database&business=IT+Consulting" with no body
      Then Response status code should be 200
      And Response body should be:
      """
      {
         "id" : 3,
         "fullname" : "Łukasz Gumiński",
         "imageSource" : "",
         "title" : "Lead Java Developer",
         "summary" : "Experienced Backend Developer and Technical Lead with proven expertise in building scalable distributed systems and leading development teams.",
         "skills" : [
            {
               "name" : "MongoDB",
               "description" : "Database",
               "level" : 4
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

         ],
         "hobbies" : [
            "Music Production",
            "Open Source Contributing",
            "Yoga"
         ],
         "resumeId" : 3
      }
      """