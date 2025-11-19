
let db = db.getSiblingDB("portfolio");

db.createUser({user: "root", pwd: "pass", roles: [{role: "readWrite", db: "portfolio"}]});
db.createCollection('Author');
db.Author.insertMany([
    {
      "_id": 1,
      "username": "admin",
      "email": "lukasz.guminski629@gmail.com",
      "roles": [
        "ROLE_USER",
        "ROLE_ADMIN",
        "ROLE_CANDIDATE",
        "ROLE_EDITOR"
      ],
      "skills": [],
      "domains": [
        {
          "name": "JVM"
        },
        {
          "name": "Databases"
        },
        {
          "name": "AI Tools"
        },
        {
          "name": "JS Framework"
        },
        {
          "name": "Architecture"
        },
        {
          "name": "CI/CD"
        }
      ],
      "businesses": [],
      "_class": "pl.delukesoft.portfolioserver.domain.user.Author"
    }
  ]
);
db.createCollection('Resume');
db.Resume.insertMany([
    {
      "_id": 1,
      "shortcut": {
        "user": {
          "username": "admin",
          "email": "lukasz.guminski629@gmail.com",
          "roles": [
            "ROLE_USER",
            "ROLE_EDITOR",
            "ROLE_ADMIN",
            "ROLE_CANDIDATE"
          ]
        },
        "title": "Lead Software Engineer",
        "summary": "Since the beginning of my journey in the JVM ecosystem, I have worked in the banking, logistics, tourism, and medical industries. I am a great enthusiast of concise and elegant solutions.\n\nLately, I have been writing most of my code in Kotlin and Scala, exploring faster frameworks like Micronaut and Quarkus\u2014and I'm pretty happy with the results.\n\nIn side projects, I work on the frontend using React and Angular, with a strong appreciation for Redux.",
        "image": {
          "name": "me",
          "src": "/resources/images/me.jpg"
        }
      },
      "skills": [
        {
          "name": "Java",
          "level": 5,
          "domains": [
            {
              "name": "JVM"
            }
          ]
        },
        {
          "name": "Kotlin",
          "level": 4,
          "domains": [
            {
              "name": "JVM"
            }
          ]
        },
        {
          "name": "Scala",
          "level": 3,
          "domains": [
            {
              "name": "JVM"
            }
          ]
        },
        {
          "name": "Oracle",
          "level": 3,
          "domains": [
            {
              "name": "Databases"
            }
          ]
        },
        {
          "name": "MongoDB",
          "level": 3,
          "domains": [
            {
              "name": "Databases"
            }
          ]
        },
        {
          "name": "Github Copilot",
          "level": 4,
          "domains": [
            {
              "name": "AI Tools"
            }
          ]
        },
        {
          "name": "React",
          "level": 3,
          "domains": [
            {
              "name": "JS Framework"
            }
          ]
        },
        {
          "name": "Angular",
          "level": 3,
          "domains": [
            {
              "name": "JS Framework"
            }
          ]
        },
        {
          "name": "Microservices",
          "level": 4,
          "domains": [
            {
              "name": "Architecture"
            }
          ]
        },
        {
          "name": "Gitlab",
          "level": 4,
          "domains": [
            {
              "name": "CI/CD"
            }
          ]
        },
        {
          "name": "UML",
          "level": 5,
          "domains": [
            {
              "name": "Architecture"
            }
          ]
        },
        {
          "name": "Github",
          "level": 3,
          "domains": [
            {
              "name": "CI/CD"
            }
          ]
        }
      ],
      "experience": [
        {
          "_id": 1,
          "business": {
            "name": "Capgemini"
          },
          "position": "Quality Assurance Tester",
          "summary": "Quality assurance for big application to manage shipments in framework similar to Selenium.",
          "description": "Logistics application to manage shipments",
          "timeframe": {
            "start": new Date("2017-05-31T22:00:00.000+00:00"),
            "end": new Date("2018-05-30T22:00:00.000+00:00")
          },
          "skills": [
            {
              "skill": {
                "name": "Java",
                "level": 5,
                "domains": [
                  {
                    "name": "JVM"
                  }
                ]
              },
              "level": 5,
              "detail": "Testing using framework similar to selenium"
            }
          ]
        },
        {
          "_id": 2,
          "business": {
            "name": "Asseco"
          },
          "position": "Junior Java Developer",
          "summary": "Hospital management application for organization of procedure rooms occupations and prescriptions.",
          "description": "Hospital management application",
          "timeframe": {
            "start": new Date("2018-05-31T22:00:00.000+00:00"),
            "end": new Date("2019-01-30T23:00:00.000+00:00")
          },
          "skills": [
            {
              "skill": {
                "name": "Java",
                "level": 5,
                "domains": [
                  {
                    "name": "JVM"
                  }
                ]
              },
              "level": 5,
              "detail": "Backend functionalities created in Java 7"
            },
            {
              "skill": {
                "name": "Angular",
                "level": 3,
                "domains": [
                  {
                    "name": "JS Framework"
                  }
                ]
              },
              "level": 3,
              "detail": "Frontend views creation in Angular 2+"
            }
          ]
        },
        {
          "_id": 3,
          "business": {
            "name": "Travelplanet"
          },
          "position": "Mid Java Developer",
          "summary": "I worked on developing a search engine that aggregated data from various providers.",
          "description": "Tour industry - offers and website maintenence",
          "timeframe": {
            "start": new Date("2019-01-31T23:00:00.000+00:00"),
            "end": new Date("2021-01-30T23:00:00.000+00:00")
          },
          "skills": [
            {
              "skill": {
                "name": "Java",
                "level": 5,
                "domains": [
                  {
                    "name": "JVM"
                  }
                ]
              },
              "level": 5,
              "detail": "Java 8, JVM internals"
            },
            {
              "skill": {
                "name": "Scala",
                "level": 3,
                "domains": [
                  {
                    "name": "JVM"
                  }
                ]
              },
              "level": 3,
              "detail": "Designing new features, Akka Framework extensively used"
            },
            {
              "skill": {
                "name": "MongoDB",
                "level": 3,
                "domains": [
                  {
                    "name": "Databases"
                  }
                ]
              },
              "level": 3,
              "detail": "Additional database used"
            }
          ]
        },
        {
          "_id": 4,
          "business": {
            "name": "Epam"
          },
          "position": "Senior Java Developer",
          "summary": "Event-driven system responsible for  propagation and processing of various events for system users",
          "description": "Banking applications",
          "timeframe": {
            "start": new Date("2021-01-31T23:00:00.000+00:00")
          },
          "skills": [
            {
              "skill": {
                "name": "Java",
                "level": 5,
                "domains": [
                  {
                    "name": "JVM"
                  }
                ]
              },
              "level": 5,
              "detail": "Java 8, migrations"
            },
            {
              "skill": {
                "name": "Oracle",
                "level": 3,
                "domains": [
                  {
                    "name": "Databases"
                  }
                ]
              },
              "level": 3,
              "detail": "Main database"
            },
            {
              "skill": {
                "name": "Gitlab",
                "level": 4,
                "domains": [
                  {
                    "name": "CI/CD"
                  }
                ]
              },
              "level": 4,
              "detail": "CI/CD pipelines, Repository"
            }
          ]
        }
      ],
      "sideProjects": [],
      "education": [
        {
          "_id": 1,
          "title": "Bachelor of Science",
          "institution": {
            "name": "Politechnika Wroc\u0142awska",
            "city": "Wroc\u0142aw",
            "country": "Polska"
          },
          "timeframe": {
            "start": new Date("2013-09-30T22:00:00.000+00:00"),
            "end": new Date("2017-06-29T22:00:00.000+00:00")
          },
          "fieldOfStudy": "Power Engineering",
          "grade": 4.5,
          "type": "DEGREE",
          "description": "BSc in Electrical Power Engineering, specializing in renewable energy systems.",
          "externalLinks": [
            "https://pwr.edu.pl/"
          ]
        },
        {
          "_id": 4,
          "title": "Bachelor of Science",
          "institution": {
            "name": "Politechnika Wroc\u0142awska",
            "city": "Wroc\u0142aw",
            "country": "Polska"
          },
          "timeframe": {
            "start": new Date("2015-09-30T22:00:00.000+00:00"),
            "end": new Date("2019-01-29T23:00:00.000+00:00")
          },
          "fieldOfStudy": "Computer Science",
          "grade": 4.5,
          "type": "DEGREE",
          "description": "Focus on algorithms, databases, software design and programming languages paradigms.",
          "externalLinks": [
            "https://pwr.edu.pl/"
          ]
        }
      ],
      "hobbies": [
        {
          "name": "Skiing"
        },
        {
          "name": "Books"
        },
        {
          "name": "Games"
        }
      ],
      "languages": [
        {
          "_id": 1,
          "name": "English",
          "level": 5
        },
        {
          "_id": 2,
          "name": "German",
          "level": 1
        },
        {
          "_id": 3,
          "name": "Polish",
          "level": 7
        }
      ],
      "createdOn": new Date("2025-11-01T14:32:00.864+00:00"),
      "lastModified": new Date("2025-11-01T16:27:05.217+00:00"),
      "_class": "pl.delukesoft.portfolioserver.domain.resume.Resume"
    }
  ]
);
db.createCollection('FieldConstraint');
db.FieldConstraint.insertMany([
    {
      "_id": 1,
      "path": "resume.education.title",
      "constraints": {
        "minLength": 5,
        "maxLength": 30,
        "nullable": false
      },
      "_class": "pl.delukesoft.portfolioserver.domain.constraint.FieldConstraint"
    },
    {
      "_id": 2,
      "path": "resume.shortcut.title",
      "constraints": {
        "minLength": 5,
        "maxLength": 30,
        "nullable": false
      },
      "_class": "pl.delukesoft.portfolioserver.domain.constraint.FieldConstraint"
    },
    {
      "_id": 3,
      "path": "resume.language.name",
      "constraints": {
        "minLength": 3,
        "maxLength": 50,
        "nullable": false
      },
      "_class": "pl.delukesoft.portfolioserver.domain.constraint.FieldConstraint"
    },
    {
      "_id": 4,
      "path": "resume.skill.description",
      "constraints": {
        "minLength": 3,
        "maxLength": 100,
        "nullable": true
      },
      "_class": "pl.delukesoft.portfolioserver.domain.constraint.FieldConstraint"
    },
    {
      "_id": 5,
      "path": "resume.skill.name",
      "constraints": {
        "minLength": 1,
        "maxLength": 50,
        "nullable": false
      },
      "_class": "pl.delukesoft.portfolioserver.domain.constraint.FieldConstraint"
    },
    {
      "_id": 6,
      "path": "resume.skill.domain.name",
      "constraints": {
        "minLength": 1,
        "maxLength": 50,
        "nullable": false
      },
      "_class": "pl.delukesoft.portfolioserver.domain.constraint.FieldConstraint"
    },
    {
      "_id": 7,
      "path": "resume.experience.business.name",
      "constraints": {
        "minLength": 3,
        "maxLength": 50,
        "nullable": false
      },
      "_class": "pl.delukesoft.portfolioserver.domain.constraint.FieldConstraint"
    },
    {
      "_id": 8,
      "path": "resume.shortcut.summary",
      "constraints": {
        "minLength": 30,
        "maxLength": 1000,
        "nullable": false
      },
      "_class": "pl.delukesoft.portfolioserver.domain.constraint.FieldConstraint"
    },
    {
      "_id": 9,
      "path": "resume.experience.skill.detail",
      "constraints": {
        "minLength": 3,
        "maxLength": 500,
        "nullable": false
      },
      "_class": "pl.delukesoft.portfolioserver.domain.constraint.FieldConstraint"
    },
    {
      "_id": 10,
      "path": "resume.hobby.name",
      "constraints": {
        "minLength": 3,
        "maxLength": 50,
        "nullable": false
      },
      "_class": "pl.delukesoft.portfolioserver.domain.constraint.FieldConstraint"
    },
    {
      "_id": 11,
      "path": "resume.experience.summary",
      "constraints": {
        "minLength": 5,
        "maxLength": 1000,
        "nullable": false
      },
      "_class": "pl.delukesoft.portfolioserver.domain.constraint.FieldConstraint"
    },
    {
      "_id": 12,
      "path": "resume.education.fieldOfStudy",
      "constraints": {
        "minLength": 3,
        "maxLength": 50,
        "nullable": false
      },
      "_class": "pl.delukesoft.portfolioserver.domain.constraint.FieldConstraint"
    },
    {
      "_id": 13,
      "path": "resume.experience.position",
      "constraints": {
        "minLength": 3,
        "maxLength": 50,
        "nullable": false
      },
      "_class": "pl.delukesoft.portfolioserver.domain.constraint.FieldConstraint"
    },
    {
      "_id": 14,
      "path": "resume.experience.description",
      "constraints": {
        "minLength": 0,
        "maxLength": 2147483647,
        "nullable": true
      },
      "_class": "pl.delukesoft.portfolioserver.domain.constraint.FieldConstraint"
    }
  ]
);
db.createCollection('ResumeVersion');
db.ResumeVersion.insertMany([
    {
      "_id": 1,
      "resume": {
        "$ref": "Resume",
        "$id": 1
      },
      "version": 1,
      "state": "PUBLISHED",
      "_class": "pl.delukesoft.portfolioserver.domain.resumehistory.ResumeVersion"
    }
  ]
);
db.createCollection('Sequence');
db.Sequence.insertMany([
    {
      "_id": 1,
      "collectionName": "author",
      "sequenceNumber": 2,
      "_class": "pl.delukesoft.portfolioserver.domain.sequence.Sequence"
    },
    {
      "_id": 2,
      "collectionName": "resume",
      "sequenceNumber": 2,
      "_class": "pl.delukesoft.portfolioserver.domain.sequence.Sequence"
    },
    {
      "_id": 3,
      "collectionName": "resume_history",
      "sequenceNumber": 2,
      "_class": "pl.delukesoft.portfolioserver.domain.sequence.Sequence"
    },
    {
      "_id": 4,
      "collectionName": "resume_version",
      "sequenceNumber": 2,
      "_class": "pl.delukesoft.portfolioserver.domain.sequence.Sequence"
    },
    {
      "_id": 5,
      "collectionName": "education",
      "sequenceNumber": 5,
      "_class": "pl.delukesoft.portfolioserver.domain.sequence.Sequence"
    },
    {
      "_id": 6,
      "collectionName": "experience",
      "sequenceNumber": 5,
      "_class": "pl.delukesoft.portfolioserver.domain.sequence.Sequence"
    },
    {
      "_id": 7,
      "collectionName": "language",
      "sequenceNumber": 4,
      "_class": "pl.delukesoft.portfolioserver.domain.sequence.Sequence"
    }
  ]
);
db.createCollection('ResumeHistory');
db.ResumeHistory.insertMany([
    {
      "_id": 1,
      "versions": [
        {
          "$ref": "ResumeVersion",
          "$id": 1
        }
      ],
      "user": {
        "username": "admin",
        "email": "lukasz.guminski629@gmail.com",
        "roles": [
          "ROLE_USER",
          "ROLE_EDITOR",
          "ROLE_ADMIN",
          "ROLE_CANDIDATE"
        ]
      },
      "_class": "pl.delukesoft.portfolioserver.domain.resumehistory.ResumeHistory",
      "defaultResume": {
        "_id": 1,
        "resume": {
          "$ref": "Resume",
          "$id": 1
        },
        "version": 1,
        "state": "PUBLISHED"
      }
    }
  ]
);
