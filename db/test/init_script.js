
let db = db.getSiblingDB("portfolio");

db.createUser({user: "root", pwd: "pass", roles: [{role: "readWrite", db: "portfolio"}]});
db.createCollection('Skill');
db.Skill.insertMany([
    {
      "_id": 1,
      "name": "Java",
      "level": 5,
      "description": "JVM",
      "username": "admin",
      "domains": [
        {
          "$ref": "TechDomain",
          "$id": 1
        },
        {
          "$ref": "TechDomain",
          "$id": 2
        }
      ],
      "_class": "pl.delukesoft.portfolioserver.domain.resume.skill.Skill"
    },
    {
      "_id": 2,
      "name": "Kotlin",
      "level": 4,
      "description": "JVM",
      "username": "admin",
      "domains": [
        {
          "$ref": "TechDomain",
          "$id": 1
        },
        {
          "$ref": "TechDomain",
          "$id": 2
        }
      ],
      "_class": "pl.delukesoft.portfolioserver.domain.resume.skill.Skill"
    },
    {
      "_id": 3,
      "name": "Scala",
      "level": 3,
      "description": "JVM",
      "username": "admin",
      "domains": [
        {
          "$ref": "TechDomain",
          "$id": 1
        },
        {
          "$ref": "TechDomain",
          "$id": 2
        }
      ],
      "_class": "pl.delukesoft.portfolioserver.domain.resume.skill.Skill"
    },
    {
      "_id": 4,
      "name": "Java",
      "level": 4,
      "description": "JVM",
      "username": "user",
      "domains": [
        {
          "$ref": "TechDomain",
          "$id": 1
        },
        {
          "$ref": "TechDomain",
          "$id": 2
        }
      ],
      "_class": "pl.delukesoft.portfolioserver.domain.resume.skill.Skill"
    },
    {
      "_id": 5,
      "name": "JavaScript",
      "level": 5,
      "description": "Web Development",
      "username": "user",
      "domains": [
        {
          "$ref": "TechDomain",
          "$id": 3
        }
      ],
      "_class": "pl.delukesoft.portfolioserver.domain.resume.skill.Skill"
    },
    {
      "_id": 6,
      "name": "React",
      "level": 5,
      "description": "Frontend Framework",
      "username": "user",
      "domains": [
        {
          "$ref": "TechDomain",
          "$id": 3
        },
        {
          "$ref": "TechDomain",
          "$id": 4
        }
      ],
      "_class": "pl.delukesoft.portfolioserver.domain.resume.skill.Skill"
    },
    {
      "_id": 7,
      "name": "Kotlin",
      "level": 5,
      "description": "JVM",
      "username": "candidate",
      "domains": [
        {
          "$ref": "TechDomain",
          "$id": 1
        },
        {
          "$ref": "TechDomain",
          "$id": 2
        }
      ],
      "_class": "pl.delukesoft.portfolioserver.domain.resume.skill.Skill"
    },
    {
      "_id": 8,
      "name": "Spring Boot",
      "level": 5,
      "description": "Backend Framework",
      "username": "candidate",
      "domains": [
        {
          "$ref": "TechDomain",
          "$id": 1
        },
        {
          "$ref": "TechDomain",
          "$id": 2
        },
        {
          "$ref": "TechDomain",
          "$id": 4
        }
      ],
      "_class": "pl.delukesoft.portfolioserver.domain.resume.skill.Skill"
    },
    {
      "_id": 9,
      "name": "MongoDB",
      "level": 4,
      "description": "Database",
      "username": "candidate",
      "domains": [
        {
          "$ref": "TechDomain",
          "$id": 5
        }
      ],
      "_class": "pl.delukesoft.portfolioserver.domain.resume.skill.Skill"
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
          "email": "john.doe@example.com",
          "roles": [
            "ROLE_USER",
            "ROLE_CANDIDATE",
            "ROLE_ADMIN"
          ]
        },
        "title": "Lead Java Developer",
        "summary": "Senior Java Developer with extensive experience in banking software development. Specialized in building robust, secure, and scalable applications.",
        "image": {
          "name": "lg.jpg",
          "src": "/images/lg.jpg"
        }
      },
      "skills": [
        {
          "_id": 1,
          "name": "Java",
          "level": 5,
          "description": "JVM",
          "username": "admin",
          "domains": [
            {
              "$ref": "TechDomain",
              "$id": 1
            },
            {
              "$ref": "TechDomain",
              "$id": 2
            }
          ]
        },
        {
          "_id": 2,
          "name": "Kotlin",
          "level": 4,
          "description": "JVM",
          "username": "admin",
          "domains": [
            {
              "$ref": "TechDomain",
              "$id": 1
            },
            {
              "$ref": "TechDomain",
              "$id": 2
            }
          ]
        },
        {
          "_id": 3,
          "name": "Scala",
          "level": 3,
          "description": "JVM",
          "username": "admin",
          "domains": [
            {
              "$ref": "TechDomain",
              "$id": 1
            },
            {
              "$ref": "TechDomain",
              "$id": 2
            }
          ]
        }
      ],
      "experience": [
        {
          "business": {
            "_id": 1,
            "name": "Bank",
            "username": "admin"
          },
          "position": "Senior Developer",
          "summary": "Lead developer for core banking systems",
          "description": "Development of core banking applications, implementing secure transaction processing systems and leading integration projects with external financial services",
          "timeframe": {
            "start": new Date("2022-12-31T23:00:00.000+00:00"),
            "end": new Date("2025-05-07T22:00:00.000+00:00")
          },
          "skills": [
            {
              "skill": {
                "$ref": "Skill",
                "$id": 1
              },
              "level": 5,
              "detail": "JVM"
            },
            {
              "skill": {
                "$ref": "Skill",
                "$id": 2
              },
              "level": 4,
              "detail": "JVM"
            }
          ]
        }
      ],
      "sideProjects": [],
      "hobbies": [
        {
          "_id": 1,
          "name": "Music Production",
          "username": "admin"
        },
        {
          "_id": 2,
          "name": "Open Source Contributing",
          "username": "admin"
        },
        {
          "_id": 3,
          "name": "Yoga",
          "username": "admin"
        }
      ],
      "languages": [
        {
          "_id": 1,
          "name": "English",
          "level": 5,
          "username": "admin"
        },
        {
          "_id": 2,
          "name": "Spanish",
          "level": 4,
          "username": "admin"
        },
        {
          "_id": 3,
          "name": "French",
          "level": 2,
          "username": "admin"
        }
      ],
      "createdOn": new Date("2025-05-08T15:04:57.824+00:00"),
      "lastModified": new Date("2025-05-08T15:04:57.824+00:00"),
      "_class": "pl.delukesoft.portfolioserver.domain.resume.Resume"
    },
    {
      "_id": 2,
      "shortcut": {
        "user": {
          "username": "user",
          "email": "jane.smith@example.com",
          "roles": [
            "ROLE_USER"
          ]
        },
        "title": "Lead Java Developer",
        "summary": "Full Stack Developer with strong focus on modern web technologies and startup environment experience. Passionate about creating efficient and user-friendly applications.",
        "image": {
          "name": "lg.jpg",
          "src": "/images/lg.jpg"
        }
      },
      "skills": [
        {
          "_id": 4,
          "name": "Java",
          "level": 4,
          "description": "JVM",
          "username": "user",
          "domains": [
            {
              "$ref": "TechDomain",
              "$id": 1
            },
            {
              "$ref": "TechDomain",
              "$id": 2
            }
          ]
        },
        {
          "_id": 5,
          "name": "JavaScript",
          "level": 5,
          "description": "Web Development",
          "username": "user",
          "domains": [
            {
              "$ref": "TechDomain",
              "$id": 3
            }
          ]
        },
        {
          "_id": 6,
          "name": "React",
          "level": 5,
          "description": "Frontend Framework",
          "username": "user",
          "domains": [
            {
              "$ref": "TechDomain",
              "$id": 3
            },
            {
              "$ref": "TechDomain",
              "$id": 4
            }
          ]
        }
      ],
      "experience": [
        {
          "business": {
            "_id": 2,
            "name": "Tech Startup",
            "username": "user"
          },
          "position": "Full Stack Developer",
          "summary": "Full-stack development of modern web applications",
          "description": "Designed and implemented full-stack solutions using React and Spring Boot, including real-time data processing features and responsive UI components",
          "timeframe": {
            "start": new Date("2023-12-31T23:00:00.000+00:00"),
            "end": new Date("2025-06-09T22:00:00.000+00:00")
          },
          "skills": [
            {
              "skill": {
                "$ref": "Skill",
                "$id": 5
              },
              "level": 5,
              "detail": "Web Development"
            },
            {
              "skill": {
                "$ref": "Skill",
                "$id": 6
              },
              "level": 5,
              "detail": "Frontend Framework"
            }
          ]
        }
      ],
      "sideProjects": [],
      "hobbies": [
        {
          "_id": 4,
          "name": "Music Production",
          "username": "user"
        },
        {
          "_id": 5,
          "name": "Open Source Contributing",
          "username": "user"
        },
        {
          "_id": 6,
          "name": "Yoga",
          "username": "user"
        }
      ],
      "languages": [
        {
          "_id": 4,
          "name": "English",
          "level": 5,
          "username": "user"
        },
        {
          "_id": 5,
          "name": "Spanish",
          "level": 4,
          "username": "user"
        },
        {
          "_id": 6,
          "name": "French",
          "level": 2,
          "username": "user"
        }
      ],
      "createdOn": new Date("2025-06-10T12:30:00.000+00:00"),
      "lastModified": new Date("2025-06-10T12:30:00.000+00:00"),
      "_class": "pl.delukesoft.portfolioserver.domain.resume.Resume"
    },
    {
      "_id": 3,
      "shortcut": {
        "user": {
          "username": "candidate",
          "email": "alex.tech@example.com",
          "roles": [
            "ROLE_USER",
            "ROLE_CANDIDATE"
          ]
        },
        "title": "Lead Java Developer",
        "summary": "Experienced Backend Developer and Technical Lead with proven expertise in building scalable distributed systems and leading development teams."
      },
      "skills": [
        {
          "_id": 7,
          "name": "Kotlin",
          "level": 5,
          "description": "JVM",
          "username": "candidate",
          "domains": [
            {
              "$ref": "TechDomain",
              "$id": 1
            },
            {
              "$ref": "TechDomain",
              "$id": 2
            }
          ]
        },
        {
          "_id": 8,
          "name": "Spring Boot",
          "level": 5,
          "description": "Backend Framework",
          "username": "candidate",
          "domains": [
            {
              "$ref": "TechDomain",
              "$id": 1
            },
            {
              "$ref": "TechDomain",
              "$id": 2
            },
            {
              "$ref": "TechDomain",
              "$id": 4
            }
          ]
        },
        {
          "_id": 9,
          "name": "MongoDB",
          "level": 4,
          "description": "Database",
          "username": "candidate",
          "domains": [
            {
              "$ref": "TechDomain",
              "$id": 5
            }
          ]
        }
      ],
      "experience": [
        {
          "business": {
            "_id": 3,
            "name": "E-commerce Platform",
            "username": "candidate"
          },
          "position": "Backend Developer",
          "summary": "Backend development for high-load e-commerce platform",
          "description": "Developed and maintained high-load microservices architecture, implementing efficient caching strategies and optimizing database performance",
          "timeframe": {
            "start": new Date("2022-05-31T22:00:00.000+00:00"),
            "end": new Date("2024-01-14T23:00:00.000+00:00")
          },
          "skills": [
            {
              "skill": {
                "$ref": "Skill",
                "$id": 2
              },
              "level": 5,
              "detail": "JVM"
            },
            {
              "skill": {
                "$ref": "Skill",
                "$id": 9
              },
              "level": 4,
              "detail": "Database"
            }
          ]
        },
        {
          "business": {
            "_id": 4,
            "name": "IT Consulting",
            "username": "candidate"
          },
          "position": "Technical Lead",
          "summary": "Technical leadership and architecture design",
          "description": "Led multiple development teams, architected solution designs, and implemented best practices for code quality and development processes",
          "timeframe": {
            "start": new Date("2024-01-14T23:00:00.000+00:00"),
            "end": new Date("2025-07-14T22:00:00.000+00:00")
          },
          "skills": [
            {
              "skill": {
                "$ref": "Skill",
                "$id": 2
              },
              "level": 5,
              "detail": "JVM"
            },
            {
              "skill": {
                "$ref": "Skill",
                "$id": 8
              },
              "level": 5,
              "detail": "Backend Framework"
            }
          ]
        }
      ],
      "sideProjects": [],
      "hobbies": [
        {
          "_id": 7,
          "name": "Music Production",
          "username": "candidate"
        },
        {
          "_id": 8,
          "name": "Open Source Contributing",
          "username": "candidate"
        },
        {
          "_id": 9,
          "name": "Yoga",
          "username": "candidate"
        }
      ],
      "languages": [
        {
          "_id": 7,
          "name": "English",
          "level": 5,
          "username": "candidate"
        },
        {
          "_id": 8,
          "name": "Spanish",
          "level": 4,
          "username": "candidate"
        },
        {
          "_id": 9,
          "name": "French",
          "level": 2,
          "username": "candidate"
        }
      ],
      "createdOn": new Date("2025-07-15T07:45:00.000+00:00"),
      "lastModified": new Date("2025-07-15T07:45:00.000+00:00"),
      "_class": "pl.delukesoft.portfolioserver.domain.resume.Resume"
    }
  ]
);
db.createCollection('Hobby');
db.Hobby.insertMany([
    {
      "_id": 1,
      "name": "Music Production",
      "username": "admin",
      "_class": "pl.delukesoft.portfolioserver.domain.resume.hobby.Hobby"
    },
    {
      "_id": 2,
      "name": "Open Source Contributing",
      "username": "admin",
      "_class": "pl.delukesoft.portfolioserver.domain.resume.hobby.Hobby"
    },
    {
      "_id": 3,
      "name": "Yoga",
      "username": "admin",
      "_class": "pl.delukesoft.portfolioserver.domain.resume.hobby.Hobby"
    },
    {
      "_id": 4,
      "name": "Music Production",
      "username": "user",
      "_class": "pl.delukesoft.portfolioserver.domain.resume.hobby.Hobby"
    },
    {
      "_id": 5,
      "name": "Open Source Contributing",
      "username": "user",
      "_class": "pl.delukesoft.portfolioserver.domain.resume.hobby.Hobby"
    },
    {
      "_id": 6,
      "name": "Yoga",
      "username": "user",
      "_class": "pl.delukesoft.portfolioserver.domain.resume.hobby.Hobby"
    },
    {
      "_id": 7,
      "name": "Music Production",
      "username": "candidate",
      "_class": "pl.delukesoft.portfolioserver.domain.resume.hobby.Hobby"
    },
    {
      "_id": 8,
      "name": "Open Source Contributing",
      "username": "candidate",
      "_class": "pl.delukesoft.portfolioserver.domain.resume.hobby.Hobby"
    },
    {
      "_id": 9,
      "name": "Yoga",
      "username": "candidate",
      "_class": "pl.delukesoft.portfolioserver.domain.resume.hobby.Hobby"
    }
  ]
);
db.createCollection('TechDomain');
db.TechDomain.insertMany([
    {
      "_id": 1,
      "name": "JVM",
      "username": "admin",
      "_class": "pl.delukesoft.portfolioserver.domain.resume.skill.domain.SkillDomain"
    },
    {
      "_id": 2,
      "name": "Backend",
      "username": "admin",
      "_class": "pl.delukesoft.portfolioserver.domain.resume.skill.domain.SkillDomain"
    },
    {
      "_id": 3,
      "name": "Frontend",
      "username": "user",
      "_class": "pl.delukesoft.portfolioserver.domain.resume.skill.domain.SkillDomain"
    },
    {
      "_id": 4,
      "name": "Framework",
      "username": "user",
      "_class": "pl.delukesoft.portfolioserver.domain.resume.skill.domain.SkillDomain"
    },
    {
      "_id": 5,
      "name": "Database",
      "username": "candidate",
      "_class": "pl.delukesoft.portfolioserver.domain.resume.skill.domain.SkillDomain"
    }
  ]
);
db.createCollection('Business');
db.Business.insertMany([
    {
      "_id": 1,
      "name": "Bank",
      "username": "admin",
      "_class": "pl.delukesoft.portfolioserver.domain.resume.experience.business.Business"
    },
    {
      "_id": 2,
      "name": "Tech Startup",
      "username": "user",
      "_class": "pl.delukesoft.portfolioserver.domain.resume.experience.business.Business"
    },
    {
      "_id": 3,
      "name": "E-commerce Platform",
      "username": "candidate",
      "_class": "pl.delukesoft.portfolioserver.domain.resume.experience.business.Business"
    },
    {
      "_id": 4,
      "name": "IT Consulting",
      "username": "candidate",
      "_class": "pl.delukesoft.portfolioserver.domain.resume.experience.business.Business"
    }
  ]
);
db.createCollection('Language');
db.Language.insertMany([
    {
      "_id": 1,
      "name": "English",
      "level": 5,
      "username": "admin",
      "_class": "pl.delukesoft.portfolioserver.domain.resume.language.Language"
    },
    {
      "_id": 2,
      "name": "Spanish",
      "level": 4,
      "username": "admin",
      "_class": "pl.delukesoft.portfolioserver.domain.resume.language.Language"
    },
    {
      "_id": 3,
      "name": "French",
      "level": 2,
      "username": "admin",
      "_class": "pl.delukesoft.portfolioserver.domain.resume.language.Language"
    },
    {
      "_id": 4,
      "name": "English",
      "level": 5,
      "username": "user",
      "_class": "pl.delukesoft.portfolioserver.domain.resume.language.Language"
    },
    {
      "_id": 5,
      "name": "Spanish",
      "level": 4,
      "username": "user",
      "_class": "pl.delukesoft.portfolioserver.domain.resume.language.Language"
    },
    {
      "_id": 6,
      "name": "French",
      "level": 2,
      "username": "user",
      "_class": "pl.delukesoft.portfolioserver.domain.resume.language.Language"
    },
    {
      "_id": 7,
      "name": "English",
      "level": 5,
      "username": "candidate",
      "_class": "pl.delukesoft.portfolioserver.domain.resume.language.Language"
    },
    {
      "_id": 8,
      "name": "Spanish",
      "level": 4,
      "username": "candidate",
      "_class": "pl.delukesoft.portfolioserver.domain.resume.language.Language"
    },
    {
      "_id": 9,
      "name": "French",
      "level": 2,
      "username": "candidate",
      "_class": "pl.delukesoft.portfolioserver.domain.resume.language.Language"
    }
  ]
);
db.createCollection('ResumeVersion');
db.ResumeVersion.insertMany([
    {
      "_id": 1,
      "resume": {
        "_id": 1,
        "shortcut": {
          "user": {
            "username": "admin",
            "email": "john.doe@example.com",
            "roles": [
              "ROLE_USER",
              "ROLE_CANDIDATE",
              "ROLE_ADMIN"
            ]
          },
          "title": "Lead Java Developer",
          "summary": "Senior Java Developer with extensive experience in banking software development. Specialized in building robust, secure, and scalable applications.",
          "image": {
            "name": "lg.jpg",
            "src": "/images/lg.jpg"
          }
        },
        "skills": [
          {
            "_id": 1,
            "name": "Java",
            "level": 5,
            "description": "JVM",
            "username": "admin",
            "domains": [
              {
                "$ref": "TechDomain",
                "$id": 1
              },
              {
                "$ref": "TechDomain",
                "$id": 2
              }
            ]
          },
          {
            "_id": 2,
            "name": "Kotlin",
            "level": 4,
            "description": "JVM",
            "username": "admin",
            "domains": [
              {
                "$ref": "TechDomain",
                "$id": 1
              },
              {
                "$ref": "TechDomain",
                "$id": 2
              }
            ]
          },
          {
            "_id": 3,
            "name": "Scala",
            "level": 3,
            "description": "JVM",
            "username": "admin",
            "domains": [
              {
                "$ref": "TechDomain",
                "$id": 1
              },
              {
                "$ref": "TechDomain",
                "$id": 2
              }
            ]
          }
        ],
        "experience": [
          {
            "business": {
              "_id": 1,
              "name": "Bank",
              "username": "admin"
            },
            "position": "Senior Developer",
            "summary": "Lead developer for core banking systems",
            "description": "Development of core banking applications, implementing secure transaction processing systems and leading integration projects with external financial services",
            "timeframe": {
              "start": new Date("2022-12-31T23:00:00.000+00:00"),
              "end": new Date("2025-05-07T22:00:00.000+00:00")
            },
            "skills": [
              {
                "skill": {
                  "$ref": "Skill",
                  "$id": 1
                },
                "level": 5,
                "detail": "JVM"
              },
              {
                "skill": {
                  "$ref": "Skill",
                  "$id": 2
                },
                "level": 4,
                "detail": "JVM"
              }
            ]
          }
        ],
        "sideProjects": [],
        "hobbies": [
          {
            "_id": 1,
            "name": "Music Production",
            "username": "admin"
          },
          {
            "_id": 2,
            "name": "Open Source Contributing",
            "username": "admin"
          },
          {
            "_id": 3,
            "name": "Yoga",
            "username": "admin"
          }
        ],
        "languages": [
          {
            "_id": 1,
            "name": "English",
            "level": 5,
            "username": "admin"
          },
          {
            "_id": 2,
            "name": "Spanish",
            "level": 4,
            "username": "admin"
          },
          {
            "_id": 3,
            "name": "French",
            "level": 2,
            "username": "admin"
          }
        ],
        "createdOn": new Date("2025-05-08T15:04:57.824+00:00"),
        "lastModified": new Date("2025-05-08T15:04:57.824+00:00")
      },
      "version": 1,
      "state": "PUBLISHED",
      "_class": "pl.delukesoft.portfolioserver.domain.resumehistory.ResumeVersion"
    },
    {
      "_id": 2,
      "resume": {
        "_id": 2,
        "shortcut": {
          "user": {
            "username": "user",
            "email": "jane.smith@example.com",
            "roles": [
              "ROLE_USER"
            ]
          },
          "title": "Lead Java Developer",
          "summary": "Full Stack Developer with strong focus on modern web technologies and startup environment experience. Passionate about creating efficient and user-friendly applications.",
          "image": {
            "name": "lg.jpg",
            "src": "/images/lg.jpg"
          }
        },
        "skills": [
          {
            "_id": 4,
            "name": "Java",
            "level": 4,
            "description": "JVM",
            "username": "user",
            "domains": [
              {
                "$ref": "TechDomain",
                "$id": 1
              },
              {
                "$ref": "TechDomain",
                "$id": 2
              }
            ]
          },
          {
            "_id": 5,
            "name": "JavaScript",
            "level": 5,
            "description": "Web Development",
            "username": "user",
            "domains": [
              {
                "$ref": "TechDomain",
                "$id": 3
              }
            ]
          },
          {
            "_id": 6,
            "name": "React",
            "level": 5,
            "description": "Frontend Framework",
            "username": "user",
            "domains": [
              {
                "$ref": "TechDomain",
                "$id": 3
              },
              {
                "$ref": "TechDomain",
                "$id": 4
              }
            ]
          }
        ],
        "experience": [
          {
            "business": {
              "_id": 2,
              "name": "Tech Startup",
              "username": "user"
            },
            "position": "Full Stack Developer",
            "summary": "Full-stack development of modern web applications",
            "description": "Designed and implemented full-stack solutions using React and Spring Boot, including real-time data processing features and responsive UI components",
            "timeframe": {
              "start": new Date("2023-12-31T23:00:00.000+00:00"),
              "end": new Date("2025-06-09T22:00:00.000+00:00")
            },
            "skills": [
              {
                "skill": {
                  "$ref": "Skill",
                  "$id": 5
                },
                "level": 5,
                "detail": "Web Development"
              },
              {
                "skill": {
                  "$ref": "Skill",
                  "$id": 6
                },
                "level": 5,
                "detail": "Frontend Framework"
              }
            ]
          }
        ],
        "sideProjects": [],
        "hobbies": [
          {
            "_id": 4,
            "name": "Music Production",
            "username": "user"
          },
          {
            "_id": 5,
            "name": "Open Source Contributing",
            "username": "user"
          },
          {
            "_id": 6,
            "name": "Yoga",
            "username": "user"
          }
        ],
        "languages": [
          {
            "_id": 4,
            "name": "English",
            "level": 5,
            "username": "user"
          },
          {
            "_id": 5,
            "name": "Spanish",
            "level": 4,
            "username": "user"
          },
          {
            "_id": 6,
            "name": "French",
            "level": 2,
            "username": "user"
          }
        ],
        "createdOn": new Date("2025-06-10T12:30:00.000+00:00"),
        "lastModified": new Date("2025-06-10T12:30:00.000+00:00")
      },
      "version": 1,
      "state": "PUBLISHED",
      "_class": "pl.delukesoft.portfolioserver.domain.resumehistory.ResumeVersion"
    },
    {
      "_id": 3,
      "resume": {
        "_id": 3,
        "shortcut": {
          "user": {
            "username": "candidate",
            "email": "alex.tech@example.com",
            "roles": [
              "ROLE_USER",
              "ROLE_CANDIDATE"
            ]
          },
          "title": "Lead Java Developer",
          "summary": "Experienced Backend Developer and Technical Lead with proven expertise in building scalable distributed systems and leading development teams."
        },
        "skills": [
          {
            "_id": 7,
            "name": "Kotlin",
            "level": 5,
            "description": "JVM",
            "username": "candidate",
            "domains": [
              {
                "$ref": "TechDomain",
                "$id": 1
              },
              {
                "$ref": "TechDomain",
                "$id": 2
              }
            ]
          },
          {
            "_id": 8,
            "name": "Spring Boot",
            "level": 5,
            "description": "Backend Framework",
            "username": "candidate",
            "domains": [
              {
                "$ref": "TechDomain",
                "$id": 1
              },
              {
                "$ref": "TechDomain",
                "$id": 2
              },
              {
                "$ref": "TechDomain",
                "$id": 4
              }
            ]
          },
          {
            "_id": 9,
            "name": "MongoDB",
            "level": 4,
            "description": "Database",
            "username": "candidate",
            "domains": [
              {
                "$ref": "TechDomain",
                "$id": 5
              }
            ]
          }
        ],
        "experience": [
          {
            "business": {
              "_id": 3,
              "name": "E-commerce Platform",
              "username": "candidate"
            },
            "position": "Backend Developer",
            "summary": "Backend development for high-load e-commerce platform",
            "description": "Developed and maintained high-load microservices architecture, implementing efficient caching strategies and optimizing database performance",
            "timeframe": {
              "start": new Date("2022-05-31T22:00:00.000+00:00"),
              "end": new Date("2024-01-14T23:00:00.000+00:00")
            },
            "skills": [
              {
                "skill": {
                  "$ref": "Skill",
                  "$id": 2
                },
                "level": 5,
                "detail": "JVM"
              },
              {
                "skill": {
                  "$ref": "Skill",
                  "$id": 9
                },
                "level": 4,
                "detail": "Database"
              }
            ]
          },
          {
            "business": {
              "_id": 4,
              "name": "IT Consulting",
              "username": "candidate"
            },
            "position": "Technical Lead",
            "summary": "Technical leadership and architecture design",
            "description": "Led multiple development teams, architected solution designs, and implemented best practices for code quality and development processes",
            "timeframe": {
              "start": new Date("2024-01-14T23:00:00.000+00:00"),
              "end": new Date("2025-07-14T22:00:00.000+00:00")
            },
            "skills": [
              {
                "skill": {
                  "$ref": "Skill",
                  "$id": 2
                },
                "level": 5,
                "detail": "JVM"
              },
              {
                "skill": {
                  "$ref": "Skill",
                  "$id": 8
                },
                "level": 5,
                "detail": "Backend Framework"
              }
            ]
          }
        ],
        "sideProjects": [],
        "hobbies": [
          {
            "_id": 7,
            "name": "Music Production",
            "username": "candidate"
          },
          {
            "_id": 8,
            "name": "Open Source Contributing",
            "username": "candidate"
          },
          {
            "_id": 9,
            "name": "Yoga",
            "username": "candidate"
          }
        ],
        "languages": [
          {
            "_id": 7,
            "name": "English",
            "level": 5,
            "username": "candidate"
          },
          {
            "_id": 8,
            "name": "Spanish",
            "level": 4,
            "username": "candidate"
          },
          {
            "_id": 9,
            "name": "French",
            "level": 2,
            "username": "candidate"
          }
        ],
        "createdOn": new Date("2025-07-15T07:45:00.000+00:00"),
        "lastModified": new Date("2025-07-15T07:45:00.000+00:00")
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
      "collectionName": "skill_domain",
      "sequenceNumber": 6,
      "_class": "pl.delukesoft.portfolioserver.domain.sequence.Sequence"
    },
    {
      "_id": 2,
      "collectionName": "skill",
      "sequenceNumber": 10,
      "_class": "pl.delukesoft.portfolioserver.domain.sequence.Sequence"
    },
    {
      "_id": 3,
      "collectionName": "business",
      "sequenceNumber": 5,
      "_class": "pl.delukesoft.portfolioserver.domain.sequence.Sequence"
    },
    {
      "_id": 4,
      "collectionName": "language",
      "sequenceNumber": 10,
      "_class": "pl.delukesoft.portfolioserver.domain.sequence.Sequence"
    },
    {
      "_id": 5,
      "collectionName": "hobby",
      "sequenceNumber": 10,
      "_class": "pl.delukesoft.portfolioserver.domain.sequence.Sequence"
    },
    {
      "_id": 6,
      "collectionName": "resume",
      "sequenceNumber": 4,
      "_class": "pl.delukesoft.portfolioserver.domain.sequence.Sequence"
    },
    {
      "_id": 7,
      "collectionName": "resume_history",
      "sequenceNumber": 4,
      "_class": "pl.delukesoft.portfolioserver.domain.sequence.Sequence"
    },
    {
      "_id": 8,
      "collectionName": "resume_version",
      "sequenceNumber": 4,
      "_class": "pl.delukesoft.portfolioserver.domain.sequence.Sequence"
    }
  ]
);
db.createCollection('ResumeHistory');
db.ResumeHistory.insertMany([
    {
      "_id": 1,
      "defaultResume": {
        "_id": 1,
        "resume": {
          "_id": 1,
          "shortcut": {
            "user": {
              "username": "admin",
              "email": "john.doe@example.com",
              "roles": [
                "ROLE_USER",
                "ROLE_CANDIDATE",
                "ROLE_ADMIN"
              ]
            },
            "title": "Lead Java Developer",
            "summary": "Senior Java Developer with extensive experience in banking software development. Specialized in building robust, secure, and scalable applications.",
            "image": {
              "name": "lg.jpg",
              "src": "/images/lg.jpg"
            }
          },
          "skills": [
            {
              "_id": 1,
              "name": "Java",
              "level": 5,
              "description": "JVM",
              "username": "admin",
              "domains": [
                {
                  "$ref": "TechDomain",
                  "$id": 1
                },
                {
                  "$ref": "TechDomain",
                  "$id": 2
                }
              ]
            },
            {
              "_id": 2,
              "name": "Kotlin",
              "level": 4,
              "description": "JVM",
              "username": "admin",
              "domains": [
                {
                  "$ref": "TechDomain",
                  "$id": 1
                },
                {
                  "$ref": "TechDomain",
                  "$id": 2
                }
              ]
            },
            {
              "_id": 3,
              "name": "Scala",
              "level": 3,
              "description": "JVM",
              "username": "admin",
              "domains": [
                {
                  "$ref": "TechDomain",
                  "$id": 1
                },
                {
                  "$ref": "TechDomain",
                  "$id": 2
                }
              ]
            }
          ],
          "experience": [
            {
              "business": {
                "_id": 1,
                "name": "Bank",
                "username": "admin"
              },
              "position": "Senior Developer",
              "summary": "Lead developer for core banking systems",
              "description": "Development of core banking applications, implementing secure transaction processing systems and leading integration projects with external financial services",
              "timeframe": {
                "start": new Date("2022-12-31T23:00:00.000+00:00"),
                "end": new Date("2025-05-07T22:00:00.000+00:00")
              },
              "skills": [
                {
                  "skill": {
                    "$ref": "Skill",
                    "$id": 1
                  },
                  "level": 5,
                  "detail": "JVM"
                },
                {
                  "skill": {
                    "$ref": "Skill",
                    "$id": 2
                  },
                  "level": 4,
                  "detail": "JVM"
                }
              ]
            }
          ],
          "sideProjects": [],
          "hobbies": [
            {
              "_id": 1,
              "name": "Music Production",
              "username": "admin"
            },
            {
              "_id": 2,
              "name": "Open Source Contributing",
              "username": "admin"
            },
            {
              "_id": 3,
              "name": "Yoga",
              "username": "admin"
            }
          ],
          "languages": [
            {
              "_id": 1,
              "name": "English",
              "level": 5,
              "username": "admin"
            },
            {
              "_id": 2,
              "name": "Spanish",
              "level": 4,
              "username": "admin"
            },
            {
              "_id": 3,
              "name": "French",
              "level": 2,
              "username": "admin"
            }
          ],
          "createdOn": new Date("2025-05-08T15:04:57.824+00:00"),
          "lastModified": new Date("2025-05-08T15:04:57.824+00:00")
        },
        "version": 1,
        "state": "PUBLISHED"
      },
      "versions": [
        {
          "$ref": "ResumeVersion",
          "$id": 1
        }
      ],
      "user": {
        "username": "admin",
        "email": "john.doe@example.com",
        "roles": [
          "ROLE_USER",
          "ROLE_CANDIDATE",
          "ROLE_ADMIN"
        ]
      },
      "_class": "pl.delukesoft.portfolioserver.domain.resumehistory.ResumeHistory"
    },
    {
      "_id": 2,
      "defaultResume": {
        "_id": 2,
        "resume": {
          "_id": 2,
          "shortcut": {
            "user": {
              "username": "user",
              "email": "jane.smith@example.com",
              "roles": [
                "ROLE_USER"
              ]
            },
            "title": "Lead Java Developer",
            "summary": "Full Stack Developer with strong focus on modern web technologies and startup environment experience. Passionate about creating efficient and user-friendly applications.",
            "image": {
              "name": "lg.jpg",
              "src": "/images/lg.jpg"
            }
          },
          "skills": [
            {
              "_id": 4,
              "name": "Java",
              "level": 4,
              "description": "JVM",
              "username": "user",
              "domains": [
                {
                  "$ref": "TechDomain",
                  "$id": 1
                },
                {
                  "$ref": "TechDomain",
                  "$id": 2
                }
              ]
            },
            {
              "_id": 5,
              "name": "JavaScript",
              "level": 5,
              "description": "Web Development",
              "username": "user",
              "domains": [
                {
                  "$ref": "TechDomain",
                  "$id": 3
                }
              ]
            },
            {
              "_id": 6,
              "name": "React",
              "level": 5,
              "description": "Frontend Framework",
              "username": "user",
              "domains": [
                {
                  "$ref": "TechDomain",
                  "$id": 3
                },
                {
                  "$ref": "TechDomain",
                  "$id": 4
                }
              ]
            }
          ],
          "experience": [
            {
              "business": {
                "_id": 2,
                "name": "Tech Startup",
                "username": "user"
              },
              "position": "Full Stack Developer",
              "summary": "Full-stack development of modern web applications",
              "description": "Designed and implemented full-stack solutions using React and Spring Boot, including real-time data processing features and responsive UI components",
              "timeframe": {
                "start": new Date("2023-12-31T23:00:00.000+00:00"),
                "end": new Date("2025-06-09T22:00:00.000+00:00")
              },
              "skills": [
                {
                  "skill": {
                    "$ref": "Skill",
                    "$id": 5
                  },
                  "level": 5,
                  "detail": "Web Development"
                },
                {
                  "skill": {
                    "$ref": "Skill",
                    "$id": 6
                  },
                  "level": 5,
                  "detail": "Frontend Framework"
                }
              ]
            }
          ],
          "sideProjects": [],
          "hobbies": [
            {
              "_id": 4,
              "name": "Music Production",
              "username": "user"
            },
            {
              "_id": 5,
              "name": "Open Source Contributing",
              "username": "user"
            },
            {
              "_id": 6,
              "name": "Yoga",
              "username": "user"
            }
          ],
          "languages": [
            {
              "_id": 4,
              "name": "English",
              "level": 5,
              "username": "user"
            },
            {
              "_id": 5,
              "name": "Spanish",
              "level": 4,
              "username": "user"
            },
            {
              "_id": 6,
              "name": "French",
              "level": 2,
              "username": "user"
            }
          ],
          "createdOn": new Date("2025-06-10T12:30:00.000+00:00"),
          "lastModified": new Date("2025-06-10T12:30:00.000+00:00")
        },
        "version": 1,
        "state": "PUBLISHED"
      },
      "versions": [
        {
          "$ref": "ResumeVersion",
          "$id": 2
        }
      ],
      "user": {
        "username": "user",
        "email": "jane.smith@example.com",
        "roles": [
          "ROLE_USER"
        ]
      },
      "_class": "pl.delukesoft.portfolioserver.domain.resumehistory.ResumeHistory"
    },
    {
      "_id": 3,
      "defaultResume": {
        "_id": 3,
        "resume": {
          "_id": 3,
          "shortcut": {
            "user": {
              "username": "candidate",
              "email": "alex.tech@example.com",
              "roles": [
                "ROLE_USER",
                "ROLE_CANDIDATE"
              ]
            },
            "title": "Lead Java Developer",
            "summary": "Experienced Backend Developer and Technical Lead with proven expertise in building scalable distributed systems and leading development teams."
          },
          "skills": [
            {
              "_id": 7,
              "name": "Kotlin",
              "level": 5,
              "description": "JVM",
              "username": "candidate",
              "domains": [
                {
                  "$ref": "TechDomain",
                  "$id": 1
                },
                {
                  "$ref": "TechDomain",
                  "$id": 2
                }
              ]
            },
            {
              "_id": 8,
              "name": "Spring Boot",
              "level": 5,
              "description": "Backend Framework",
              "username": "candidate",
              "domains": [
                {
                  "$ref": "TechDomain",
                  "$id": 1
                },
                {
                  "$ref": "TechDomain",
                  "$id": 2
                },
                {
                  "$ref": "TechDomain",
                  "$id": 4
                }
              ]
            },
            {
              "_id": 9,
              "name": "MongoDB",
              "level": 4,
              "description": "Database",
              "username": "candidate",
              "domains": [
                {
                  "$ref": "TechDomain",
                  "$id": 5
                }
              ]
            }
          ],
          "experience": [
            {
              "business": {
                "_id": 3,
                "name": "E-commerce Platform",
                "username": "candidate"
              },
              "position": "Backend Developer",
              "summary": "Backend development for high-load e-commerce platform",
              "description": "Developed and maintained high-load microservices architecture, implementing efficient caching strategies and optimizing database performance",
              "timeframe": {
                "start": new Date("2022-05-31T22:00:00.000+00:00"),
                "end": new Date("2024-01-14T23:00:00.000+00:00")
              },
              "skills": [
                {
                  "skill": {
                    "$ref": "Skill",
                    "$id": 2
                  },
                  "level": 5,
                  "detail": "JVM"
                },
                {
                  "skill": {
                    "$ref": "Skill",
                    "$id": 9
                  },
                  "level": 4,
                  "detail": "Database"
                }
              ]
            },
            {
              "business": {
                "_id": 4,
                "name": "IT Consulting",
                "username": "candidate"
              },
              "position": "Technical Lead",
              "summary": "Technical leadership and architecture design",
              "description": "Led multiple development teams, architected solution designs, and implemented best practices for code quality and development processes",
              "timeframe": {
                "start": new Date("2024-01-14T23:00:00.000+00:00"),
                "end": new Date("2025-07-14T22:00:00.000+00:00")
              },
              "skills": [
                {
                  "skill": {
                    "$ref": "Skill",
                    "$id": 2
                  },
                  "level": 5,
                  "detail": "JVM"
                },
                {
                  "skill": {
                    "$ref": "Skill",
                    "$id": 8
                  },
                  "level": 5,
                  "detail": "Backend Framework"
                }
              ]
            }
          ],
          "sideProjects": [],
          "hobbies": [
            {
              "_id": 7,
              "name": "Music Production",
              "username": "candidate"
            },
            {
              "_id": 8,
              "name": "Open Source Contributing",
              "username": "candidate"
            },
            {
              "_id": 9,
              "name": "Yoga",
              "username": "candidate"
            }
          ],
          "languages": [
            {
              "_id": 7,
              "name": "English",
              "level": 5,
              "username": "candidate"
            },
            {
              "_id": 8,
              "name": "Spanish",
              "level": 4,
              "username": "candidate"
            },
            {
              "_id": 9,
              "name": "French",
              "level": 2,
              "username": "candidate"
            }
          ],
          "createdOn": new Date("2025-07-15T07:45:00.000+00:00"),
          "lastModified": new Date("2025-07-15T07:45:00.000+00:00")
        },
        "version": 1,
        "state": "PUBLISHED"
      },
      "versions": [
        {
          "$ref": "ResumeVersion",
          "$id": 3
        }
      ],
      "user": {
        "username": "candidate",
        "email": "alex.tech@example.com",
        "roles": [
          "ROLE_USER",
          "ROLE_CANDIDATE"
        ]
      },
      "_class": "pl.delukesoft.portfolioserver.domain.resumehistory.ResumeHistory"
    }
  ]
);
