Feature: CV View functionality

  Scenario: Default print
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

  Scenario: Default print with text search
    When "POST" request is sent to endpoint "/pdf" with body:
    """
    {
      "text": "JAVA"
    }
    """
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

  Scenario: Default print doesn't exist
    Given All resumes are deleted from database
    When "GET" request is sent to endpoint "/pdf" with no body
    Then Response status code should be 404
    And Response body should be:
    """
    {
    "error": "CV not found",
    "status": 404
    }
    """
    Then Restore DB resumes