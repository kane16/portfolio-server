Feature: Skill Operations


  Scenario: Admin adds a new skill to portfolio
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
    When "POST" request is sent to endpoint "/skills" with body:
    """
    {
      "name": "Kotlin",
      "description": "JVM Language",
      "level": 4,
      "domains": []
    }
    """
    Then Response status code should be 201
    And Response body should be:
    """
    true
    """
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
          },
          {
            "name": "Kotlin",
            "description": "JVM Language",
            "level": 4
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