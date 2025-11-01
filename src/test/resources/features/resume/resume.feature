Feature: Resume creation

  Scenario: For given user no history exists, user should be able to initiate resume creation
    Given User is authorized with token: "candidate_empty"
    When "GET" request is sent to endpoint "/resume/history" with no body
    Then Response status code should be 404
    And Response body should be:
    """
    {
      "error": "Resume History not found",
      "status": 404
    }
    """
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
    And Response body should be:
    """
    true
    """
    When "GET" request is sent to endpoint "/resume/history" with no body
    Then Response status code should be 200
    And Response body should be:
    """
    {
       "defaultPortfolio" : null,
       "history" : [
          {
             "id" : 4,
             "title" : "My Professional Resume",
             "summary" : "Experienced software developer with strong background in web technologies",
             "version" : 1,
             "state" : "DRAFT"
          }
       ]
    }
    """

  Scenario: Unauthorized user tries to initiate resume creation
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
    Then Response status code should be 401
    And Response body should be:
    """
    {
      "error": "Anonymous access is restricted to this endpoint",
      "status": 401
    }
    """

  Scenario: User with insufficient role tries to initiate resume creation
    Given User is authorized with token: "user"
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
    Then Response status code should be 403
    And Response body should be:
    """
    {
      "error": "Access denied. Required role: ROLE_CANDIDATE",
      "status": 403
    }
    """

  Scenario: Resume creation fails due to title too short
    Given User is authorized with token: "candidate_empty"
    When "POST" request is sent to endpoint "/resume/edit/init" with body:
    """
    {
      "title": "Too",
      "summary": "Experienced software developer with strong background in web technologies",
      "image": {
        "name": "My image",
        "src": "123.jpg"
      }
    }
    """
    Then Response status code should be 400
    And Response body should be:
    """
    {
      "error": "Title length must be between 5 and 30",
      "status": 400
    }
    """

  Scenario: Resume creation fails due to title too long
    Given User is authorized with token: "candidate_empty"
    When "POST" request is sent to endpoint "/resume/edit/init" with body:
    """
    {
      "title": "This title is way too long for validation and exceeds maximum",
      "summary": "Experienced software developer with strong background in web technologies",
      "image": {
        "name": "My image",
        "src": "123.jpg"
      }
    }
    """
    Then Response status code should be 400
    And Response body should be:
    """
    {
      "error": "Title length must be between 5 and 30",
      "status": 400
    }
    """

  Scenario: Resume creation fails due to summary too short
    Given User is authorized with token: "candidate_empty"
    When "POST" request is sent to endpoint "/resume/edit/init" with body:
    """
    {
      "title": "My Professional Resume",
      "summary": "Too short",
      "image": {
        "name": "My image",
        "src": "123.jpg"
      }
    }
    """
    Then Response status code should be 400
    And Response body should be:
    """
    {
      "error": "Summary length must be between 30 and 1000",
      "status": 400
    }
    """

  Scenario: Resume creation fails due to missing title field
    Given User is authorized with token: "candidate_empty"
    When "POST" request is sent to endpoint "/resume/edit/init" with body:
    """
    {
      "summary": "Experienced software developer with strong background in web technologies",
      "image": {
        "name": "My image",
        "src": "123.jpg"
      }
    }
    """
    Then Response status code should be 400
    And Response body should be:
    """
    {
      "error": "Request invalid",
      "status": 400
    }
    """

  Scenario: Resume creation fails due to missing summary field
    Given User is authorized with token: "candidate_empty"
    When "POST" request is sent to endpoint "/resume/edit/init" with body:
    """
    {
      "title": "My Professional Resume",
      "image": {
        "name": "My image",
        "src": "123.jpg"
      }
    }
    """
    Then Response status code should be 400
    And Response body should be:
    """
    {
      "error": "Request invalid",
      "status": 400
    }
    """

  Scenario: Resume creation fails due to missing image field
    Given User is authorized with token: "candidate_empty"
    When "POST" request is sent to endpoint "/resume/edit/init" with body:
    """
    {
      "title": "My Professional Resume",
      "summary": "Experienced software developer with strong background in web technologies"
    }
    """
    Then Response status code should be 400
    And Response body should be:
    """
    {
      "error": "Request invalid",
      "status": 400
    }
    """

  Scenario: Resume creation fails due to empty request body
    Given User is authorized with token: "candidate_empty"
    When "POST" request is sent to endpoint "/resume/edit/init" with body:
    """
    """
    Then Response status code should be 400
    And Response body should be:
    """
    {
      "error": "Request invalid",
      "status": 400
    }
    """

  Scenario: Resume creation fails due to malformed JSON
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
    """
    Then Response status code should be 400
    And Response body should be:
    """
    {
      "error": "Request invalid",
      "status": 400
    }
    """

  Scenario: Candidate tries to create resume when already exists
    Given User is authorized with token: "candidate"
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
    When "GET" request is sent to endpoint "/resume/history" with no body
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
          },
          {
             "id" : 4,
             "title" : "My Professional Resume",
             "summary" : "Experienced software developer with strong background in web technologies",
             "version" : 2,
             "state" : "DRAFT"
          }
       ]
    }
    """

  Scenario: Resume creation with minimum valid values
    Given User is authorized with token: "candidate_empty"
    When "POST" request is sent to endpoint "/resume/edit/init" with body:
    """
    {
      "title": "Valid",
      "summary": "This is a valid summary with exactly thirty characters minimum",
      "image": {
        "name": "Test",
        "src": "test.jpg"
      }
    }
    """
    Then Response status code should be 201

  Scenario: Resume creation with maximum valid values
    Given User is authorized with token: "candidate_empty"
    When "POST" request is sent to endpoint "/resume/edit/init" with body:
    """
    {
      "title": "Maximum length title allowed",
      "summary": "This summary has exactly one hundred characters which is the maximum allowed length for test",
      "image": {
        "name": "Maximum name",
        "src": "maximum.jpg"
      }
    }
    """
    Then Response status code should be 201

  Scenario: Unpublish after init and publish success
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
    When "GET" request is sent to endpoint "/resume/history" with no body
    And Response body should be:
    """
    {
       "defaultPortfolio" : null,
       "history" : [
          {
             "id" : 4,
             "title" : "My Professional Resume",
             "summary" : "Experienced software developer with strong background in web technologies",
             "version" : 1,
             "state" : "DRAFT"
          }
       ]
    }
    """
    When "PUT" request is sent to endpoint "/resume/edit/1/publish" with no body
    Then Response status code should be 200
    When "GET" request is sent to endpoint "/resume/history" with no body
    And Response body should be:
    """
    {
       "defaultPortfolio" : {
           "id" : 4,
           "title" : "My Professional Resume",
           "summary" : "Experienced software developer with strong background in web technologies",
           "version" : 1,
           "state" : "PUBLISHED"
       },
       "history" : [
          {
             "id" : 4,
             "title" : "My Professional Resume",
             "summary" : "Experienced software developer with strong background in web technologies",
             "version" : 1,
             "state" : "PUBLISHED"
          }
       ]
    }
    """
    When "PUT" request is sent to endpoint "/resume/edit/unpublish" with no body
    Then Response status code should be 200
    When "GET" request is sent to endpoint "/resume/history" with no body
    Then Response status code should be 200
    And Response body should be:
    """
    {
       "defaultPortfolio" : null,
       "history" : [
          {
             "id" : 4,
             "title" : "My Professional Resume",
             "summary" : "Experienced software developer with strong background in web technologies",
             "version" : 1,
             "state" : "DRAFT"
          }
       ]
    }
    """

  Scenario: Unpublish fails when version exists but is not in PUBLISHED state
    Given User is authorized with token: "candidate"
    When "GET" request is sent to endpoint "/resume/history" with no body
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
    When "PUT" request is sent to endpoint "/resume/edit/unpublish" with no body
    Then Response status code should be 200
    When "PUT" request is sent to endpoint "/resume/edit/unpublish" with no body
    Then Response status code should be 400
    And Response body should be:
    """
    {
      "error": "No version has been published yet",
      "status": 400
    }
    """

  Scenario: After Successful Resume creation candidate can read his resume by id
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
    When "GET" request is sent to endpoint "/resume/history" with no body
    Then Response status code should be 200
    And Response body should be:
    """
    {
       "defaultPortfolio" : null,
       "history" : [
          {
             "id" : 4,
             "title" : "My Professional Resume",
             "summary" : "Experienced software developer with strong background in web technologies",
             "version" : 1,
             "state" : "DRAFT"
          }
       ]
    }
    """
    When "POST" request is sent to endpoint "/cv/4" with no body
    Then Response status code should be 200

  Scenario: Update resume fails when resume with given id does not exist
    Given User is authorized with token: "candidate"
    When "PUT" request is sent to endpoint "/resume/edit/999" with body:
    """
    {
      "title": "Updated Resume Title",
      "summary": "Updated summary text with valid length for testing update",
      "image": {
        "name": "Updated",
        "src": "updated.jpg"
      }
    }
    """
    Then Response status code should be 404
    And Response body should be:
    """
    {
      "error": "CV not found",
      "status": 404
    }
    """

  Scenario: Successful resume shortcut update
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
    When "PUT" request is sent to endpoint "/resume/edit/4" with body:
    """
    {
      "title": "Updated Resume Title",
      "summary": "Updated summary text with valid length for testing update",
      "image": {
        "name": "Updated",
        "src": "updated.jpg"
      }
    }
    """
    Then Response status code should be 200
    When "PUT" request is sent to endpoint "/resume/edit/1/publish" with no body
    Then Response status code should be 200
    When "GET" request is sent to endpoint "/resume/history" with no body
    Then Response status code should be 200
    And Response body should be:
    """
    {
       "defaultPortfolio" : {
          "id" : 4,
          "title" : "Updated Resume Title",
          "summary" : "Updated summary text with valid length for testing update",
          "version" : 1,
          "state" : "PUBLISHED"
       },
       "history" : [
          {
             "id" : 4,
             "title" : "Updated Resume Title",
             "summary" : "Updated summary text with valid length for testing update",
             "version" : 1,
             "state" : "PUBLISHED"
          }
       ]
    }
    """

  Scenario: Admin adds a new skill to resume
    Given User is authorized with token: "admin"
    When "POST" request is sent to endpoint "/cv/1" with no body
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
              "id": null,
              "name" : "English",
              "level" : "C1"
           },
           {
              "id": null,
              "name" : "Spanish",
              "level" : "B2"
           },
           {
              "id": null,
              "name" : "French",
              "level" : "A2"
           }
        ],
        "education" : [

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
    When "PUT" request is sent to endpoint "/resume/edit/unpublish" with no body
    Then Response status code should be 200
    When "POST" request is sent to endpoint "/skills/domains" with body:
    """
    JVM
    """
    Then Response status code should be 201
    When "POST" request is sent to endpoint "/resume/edit/1/skills" with body:
    """
    {
      "name": "Groovy",
      "description": "JVM Language",
      "level": 2,
      "domains": ["JVM"]
    }
    """
    Then Response status code should be 201
    And Response body should be:
      """
      true
      """
    When "PUT" request is sent to endpoint "/resume/edit/1/publish" with no body
    Then Response status code should be 200
    When "POST" request is sent to endpoint "/cv/1" with no body
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
           },
           {
             "name": "Groovy",
             "description": "JVM Language",
             "level": 2
           }
        ],
        "languages" : [
           {
              "id": null,
              "name" : "English",
              "level" : "C1"
           },
           {
              "id": null,
              "name" : "Spanish",
              "level" : "B2"
           },
           {
              "id": null,
              "name" : "French",
              "level" : "A2"
           }
        ],
        "education" : [

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

  Scenario: Candidate pulls resume history
    Given User is authorized with token: "candidate"
    When "GET" request is sent to endpoint "/resume/history" with no body
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

  Scenario: Unauthorized user tries to access resume history
    When "GET" request is sent to endpoint "/resume/history" with no body
    Then Response status code should be 401
    And Response body should be:
      """
      {
        "error": "Anonymous access is restricted to this endpoint",
        "status": 401
      }
      """

  Scenario: User tries to access resume history
    Given User is authorized with token: "user"
    When "GET" request is sent to endpoint "/resume/history" with no body
    Then Response status code should be 403
    And Response body should be:
      """
      {
        "error": "Access denied. Required role: ROLE_CANDIDATE",
        "status": 403
      }
      """

  Scenario: Candidate with no resume history
    Given User is authorized with token: "candidate_empty"
    When "GET" request is sent to endpoint "/resume/history" with no body
    Then Response status code should be 404
    And Response body should be:
      """
      {
        "error": "Resume History not found",
        "status": 404
      }
      """

  Scenario: Admin removing skill from resume successful
    Given User is authorized with token: "admin"
    When "POST" request is sent to endpoint "/cv/1" with no body
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
            "id": null,
            "name" : "English",
            "level" : "C1"
         },
         {
            "id": null,
            "name" : "Spanish",
            "level" : "B2"
         },
         {
            "id": null,
            "name" : "French",
            "level" : "A2"
         }
      ],
      "education" : [

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
    When "PUT" request is sent to endpoint "/resume/edit/unpublish" with no body
    Then Response status code should be 200
    When "DELETE" request is sent to endpoint "/resume/edit/1/skills/Java" with no body
    Then Response status code should be 200
    When "PUT" request is sent to endpoint "/resume/edit/1/publish" with no body
    Then Response status code should be 200
    When "POST" request is sent to endpoint "/cv/1" with no body
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
            "id": null,
            "name" : "English",
            "level" : "C1"
         },
         {
            "id": null,
            "name" : "Spanish",
            "level" : "B2"
         },
         {
            "id": null,
            "name" : "French",
            "level" : "A2"
         }
      ],
      "education" : [

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

  Scenario: Remove not existing skill
    Given User is authorized with token: "admin"
    When "DELETE" request is sent to endpoint "/resume/edit/1/skills/BBB" with no body
    Then Response status code should be 404
    And Response body should be:
      """
      {
        "error" : "Skill with name BBB not found",
        "status" : 404
      }
      """

  Scenario: Edit existing skill should be successful
    Given User is authorized with token: "admin"
    When "PUT" request is sent to endpoint "/resume/edit/unpublish" with no body
    Then Response status code should be 200
    When "POST" request is sent to endpoint "/skills/domains" with body:
    """
    JVM
    """
    Then Response status code should be 201
    When "PUT" request is sent to endpoint "/resume/edit/1/skills/Java" with body:
      """
      {
         "name" : "Jaka",
         "description" : "Java programming language",
         "level" : 3,
         "domains": ["JVM"]
      }
      """
    Then Response status code should be 200
    When "PUT" request is sent to endpoint "/resume/edit/1/publish" with no body
    Then Response status code should be 200
    When "POST" request is sent to endpoint "/cv/1" with no body
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
              "name" : "Jaka",
              "description" : "Java programming language",
              "level" : 3
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
              "id": null,
              "name" : "English",
              "level" : "C1"
           },
           {
              "id": null,
              "name" : "Spanish",
              "level" : "B2"
           },
           {
              "id": null,
              "name" : "French",
              "level" : "A2"
           }
        ],
        "education" : [

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

  Scenario: Edit existing skill should fail if skill does not exist
    Given User is authorized with token: "admin"
    When "PUT" request is sent to endpoint "/resume/edit/1/skills/mmm" with body:
    """
    {
       "name" : "Jaka",
       "description" : "Java programming language",
       "level" : 3,
       "domains": []
    }
    """
    Then Response body should be:
    """
    {
      "error" : "Skill with name mmm not found",
      "status" : 404
    }
    """