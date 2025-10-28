Feature: CV View functionality

  Background: User is authorized with token: "admin"
    Given User is authorized with token: "admin"

  Scenario: Get default print as admin
    When "GET" request is sent to endpoint "/pdf" with no body
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
             "id" : null,
             "name" : "English",
             "level" : "C1"
          },
          {
             "id" : null,
             "name" : "Spanish",
             "level" : "B2"
          },
          {
             "id" : null,
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

  Scenario: Get candidate print
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
             "level" : 5
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
             "id" : null,
             "name" : "English",
             "level" : "C1"
          },
          {
             "id" : null,
             "name" : "Spanish",
             "level" : "B2"
          },
          {
             "id" : null,
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