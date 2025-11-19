
let db = db.getSiblingDB("portfolio");

db.createUser({user: "root", pwd: "pass", roles: [{role: "readWrite", db: "portfolio"}]});
db.createCollection('Resume');
db.Resume.insertMany([
    {
      "_id": 1,
      "shortcut": {
        "user": {
          "username": "admin",
          "email": "john.doe@example.com",
          "roles": ["ROLE_USER", "ROLE_CANDIDATE", "ROLE_ADMIN"]
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
          "name": "Java",
          "level": 5,
          "description": "JVM",
          "domains": [
            {
              "name": "JVM"
            },
            {
              "name": "Backend"
            }
          ]
        },
        {
          "name": "Kotlin",
          "level": 4,
          "description": "JVM",
          "domains": [
            {
              "name": "JVM"
            },
            {
              "name": "Backend"
            }
          ]
        },
        {
          "name": "Scala",
          "level": 3,
          "description": "JVM",
          "domains": [
            {
              "name": "JVM"
            },
            {
              "name": "Backend"
            }
          ]
        }
      ],
      "experience": [
        {
          "business": {
            "name": "Bank"
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
                "name": "Java",
                "level": 5,
                "description": "JVM",
                "domains": [
                  {
                    "name": "JVM"
                  },
                  {
                    "name": "Backend"
                  }
                ]
              },
              "level": 5,
              "detail": "JVM"
            },
            {
              "skill": {
                "name": "Kotlin",
                "level": 4,
                "description": "JVM",
                "domains": [
                  {
                    "name": "JVM"
                  },
                  {
                    "name": "Backend"
                  }
                ]
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
          "name": "Music Production"
        },
        {
          "name": "Open Source Contributing"
        },
        {
          "name": "Yoga"
        }
      ],
      "languages": [
        {
          "name": "English",
          "level": 5
        },
        {
          "name": "Spanish",
          "level": 4
        },
        {
          "name": "French",
          "level": 2
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
          "roles": ["ROLE_USER"]
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
          "name": "Java",
          "level": 4,
          "description": "JVM",
          "domains": [
            {
              "name": "JVM"
            },
            {
              "name": "Backend"
            }
          ]
        },
        {
          "name": "JavaScript",
          "level": 5,
          "description": "Web Development",
          "domains": [
            {
              "name": "Frontend"
            }
          ]
        },
        {
          "name": "React",
          "level": 5,
          "description": "Frontend Framework",
          "domains": [
            {
              "name": "Frontend"
            },
            {
              "name": "Framework"
            }
          ]
        }
      ],
      "experience": [
        {
          "business": {
            "name": "Tech Startup"
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
                "name": "JavaScript",
                "level": 5,
                "description": "Web Development",
                "domains": [
                  {
                    "name": "Frontend"
                  }
                ]
              },
              "level": 5,
              "detail": "Web Development"
            },
            {
              "skill": {
                "name": "React",
                "level": 5,
                "description": "Frontend Framework",
                "domains": [
                  {
                    "name": "Frontend"
                  },
                  {
                    "name": "Framework"
                  }
                ]
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
          "name": "Music Production"
        },
        {
          "name": "Open Source Contributing"
        },
        {
          "name": "Yoga"
        }
      ],
      "languages": [
        {
          "name": "English",
          "level": 5
        },
        {
          "name": "Spanish",
          "level": 4
        },
        {
          "name": "French",
          "level": 2
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
          "roles": ["ROLE_USER", "ROLE_CANDIDATE"]
        },
        "title": "Lead Java Developer",
        "summary": "Experienced Backend Developer and Technical Lead with proven expertise in building scalable distributed systems and leading development teams."
      },
      "skills": [
        {
          "name": "Kotlin",
          "level": 5,
          "description": "JVM",
          "domains": [
            {
              "name": "JVM"
            },
            {
              "name": "Backend"
            }
          ]
        },
        {
          "name": "Spring Boot",
          "level": 5,
          "description": "Backend Framework",
          "domains": [
            {
              "name": "JVM"
            },
            {
              "name": "Backend"
            },
            {
              "name": "Framework"
            }
          ]
        },
        {
          "name": "MongoDB",
          "level": 4,
          "description": "Database",
          "domains": [
            {
              "name": "Database"
            }
          ]
        }
      ],
      "experience": [
        {
          "business": {
            "name": "E-commerce Platform"
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
                "name": "Kotlin",
                "level": 4,
                "description": "JVM",
                "domains": [
                  {
                    "name": "JVM"
                  },
                  {
                    "name": "Backend"
                  }
                ]
              },
              "level": 5,
              "detail": "JVM"
            },
            {
              "skill": {
                "name": "MongoDB",
                "level": 4,
                "description": "Database",
                "domains": [
                  {
                    "name": "Database"
                  }
                ]
              },
              "level": 4,
              "detail": "Database"
            }
          ]
        },
        {
          "business": {
            "name": "IT Consulting"
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
                "name": "Kotlin",
                "level": 4,
                "description": "JVM",
                "domains": [
                  {
                    "name": "JVM"
                  },
                  {
                    "name": "Backend"
                  }
                ]
              },
              "level": 5,
              "detail": "JVM"
            },
            {
              "skill": {
                "name": "Spring Boot",
                "level": 5,
                "description": "Backend Framework",

                "domains": [
                  {
                    "name": "JVM"
                  },
                  {
                    "name": "Backend"
                  },
                  {
                    "name": "Framework"
                  }
                ]
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
          "name": "Music Production"
        },
        {
          "name": "Open Source Contributing"
        },
        {
          "name": "Yoga"
        }
      ],
      "languages": [
        {
          "name": "English",
          "level": 5
        },
        {
          "name": "Spanish",
          "level": 4
        },
        {
          "name": "French",
          "level": 2
        }
      ],
      "createdOn": new Date("2025-07-15T07:45:00.000+00:00"),
      "lastModified": new Date("2025-07-15T07:45:00.000+00:00"),
      "_class": "pl.delukesoft.portfolioserver.domain.resume.Resume"
    }
  ]
);
db.createCollection('ResumeVersion');
db.ResumeVersion.insertMany([
    {
      "_id": 1,
      "resume": {"$ref": "Resume", "$id": 1},
      "version": 1,
      "state": "PUBLISHED",
      "_class": "pl.delukesoft.portfolioserver.domain.resumehistory.ResumeVersion"
    },
    {
      "_id": 2,
      "resume": {"$ref": "Resume", "$id": 2},
      "version": 1,
      "state": "PUBLISHED",
      "_class": "pl.delukesoft.portfolioserver.domain.resumehistory.ResumeVersion"
    },
    {
      "_id": 3,
      "resume": {"$ref": "Resume", "$id": 3},
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
    },
  {
    "_id": 9,
    "collectionName": "field_constraint",
    "sequenceNumber": 15,
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
        "resume": {"$ref": "Resume", "$id": 1},
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
        "roles": ["ROLE_USER", "ROLE_CANDIDATE", "ROLE_ADMIN"]
      },
      "_class": "pl.delukesoft.portfolioserver.domain.resumehistory.ResumeHistory"
    },
    {
      "_id": 2,
      "defaultResume": {
        "_id": 2,
        "resume": {"$ref": "Resume", "$id": 2},
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
        "roles": ["ROLE_USER"]
      },
      "_class": "pl.delukesoft.portfolioserver.domain.resumehistory.ResumeHistory"
    },
    {
      "_id": 3,
      "defaultResume": {
        "_id": 3,
        "resume": {"$ref": "Resume", "$id": 3},
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
        "roles": ["ROLE_USER", "ROLE_CANDIDATE"]
      },
      "_class": "pl.delukesoft.portfolioserver.domain.resumehistory.ResumeHistory"
    }
  ]
);


// FieldConstraint collection with validation constraints for resume fields
// Inserted by automation to seed Mongo with constraints used by validators
// If collection already exists in your environment, you may remove the createCollection call below.
db.createCollection('FieldConstraint');
db.FieldConstraint.insertMany([
  {
    "_id": 1,
    "path": "resume.education.title",
    "constraints": {"minLength": 5, "maxLength": 30, "nullable": false},
    "_class": "pl.delukesoft.portfolioserver.domain.constraint.FieldConstraint"
  },
  {
    "_id": 2,
    "path": "resume.shortcut.title",
    "constraints": {"minLength": 5, "maxLength": 30, "nullable": false},
    "_class": "pl.delukesoft.portfolioserver.domain.constraint.FieldConstraint"
  },
  {
    "_id": 3,
    "path": "resume.language.name",
    "constraints": {"minLength": 3, "maxLength": 50, "nullable": false},
    "_class": "pl.delukesoft.portfolioserver.domain.constraint.FieldConstraint"
  },
  {
    "_id": 4,
    "path": "resume.skill.description",
    "constraints": {"minLength": 3, "maxLength": 100, "nullable": false},
    "_class": "pl.delukesoft.portfolioserver.domain.constraint.FieldConstraint"
  },
  {
    "_id": 5,
    "path": "resume.skill.name",
    "constraints": {"minLength": 1, "maxLength": 50, "nullable": false},
    "_class": "pl.delukesoft.portfolioserver.domain.constraint.FieldConstraint"
  },
  {
    "_id": 6,
    "path": "resume.skill.domain.name",
    "constraints": {"minLength": 1, "maxLength": 50, "nullable": false},
    "_class": "pl.delukesoft.portfolioserver.domain.constraint.FieldConstraint"
  },
  {
    "_id": 7,
    "path": "resume.experience.business.name",
    "constraints": {"minLength": 3, "maxLength": 50, "nullable": false},
    "_class": "pl.delukesoft.portfolioserver.domain.constraint.FieldConstraint"
  },
  {
    "_id": 8,
    "path": "resume.shortcut.summary",
    "constraints": {"minLength": 30, "maxLength": 1000, "nullable": false},
    "_class": "pl.delukesoft.portfolioserver.domain.constraint.FieldConstraint"
  },
  {
    "_id": 9,
    "path": "resume.experience.skill.detail",
    "constraints": {"minLength": 3, "maxLength": 50, "nullable": false},
    "_class": "pl.delukesoft.portfolioserver.domain.constraint.FieldConstraint"
  },
  {
    "_id": 10,
    "path": "resume.hobby.name",
    "constraints": {"minLength": 3, "maxLength": 50, "nullable": false},
    "_class": "pl.delukesoft.portfolioserver.domain.constraint.FieldConstraint"
  },
  {
    "_id": 11,
    "path": "resume.experience.summary",
    "constraints": {"minLength": 5, "maxLength": 1000, "nullable": false},
    "_class": "pl.delukesoft.portfolioserver.domain.constraint.FieldConstraint"
  },
  {
    "_id": 12,
    "path": "resume.education.fieldOfStudy",
    "constraints": {"minLength": 3, "maxLength": 50, "nullable": false},
    "_class": "pl.delukesoft.portfolioserver.domain.constraint.FieldConstraint"
  },
  {
    "_id": 13,
    "path": "resume.experience.position",
    "constraints": {"minLength": 3, "maxLength": 50, "nullable": false},
    "_class": "pl.delukesoft.portfolioserver.domain.constraint.FieldConstraint"
  },
  {
    "_id": 14,
    "path": "resume.experience.description",
    "constraints": {"minLength": 0, "maxLength": 2147483647, "nullable": true},
    "_class": "pl.delukesoft.portfolioserver.domain.constraint.FieldConstraint"
  }
]);
