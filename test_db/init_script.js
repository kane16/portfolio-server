let blog_db = db.getSiblingDB('blog');

blog_db.createUser(
  {user: "root", pwd: "pass", roles: [{role: "readWrite", db: "blog"}]});

blog_db.createCollection('Tag');

blog_db.Tag.insertMany(
  [
    {
      _id: NumberLong(1),
      "name": "Back-End"
    },
    {
      _id: NumberLong(2),
      "name": "Front-End"
    }
  ]
);


blog_db.createCollection("Article");

blog_db.createCollection("Sequence");

blog_db.Sequence.insertMany(
  [
    {
      _id: NumberLong(1),
      collectionName: "article",
      sequenceNumber: NumberLong(101)
    },
    {
      _id: NumberLong(2),
      collectionName: "section",
      sequenceNumber: NumberLong(10)
    },
    {
      _id: NumberLong(3),
      collectionName: "element",
      sequenceNumber: NumberLong(150)
    },
    {
      _id: NumberLong(4),
      collectionName: "tag",
      sequenceNumber: NumberLong(50)
    }
  ]
);

let portfolio_db = db.getSiblingDB('portfolio');

portfolio_db.createUser(
  {user: "root", pwd: "pass", roles: [{role: "readWrite", db: "portfolio"}]});

portfolio_db.createCollection('CV');

// Drop existing documents and insert new ones with level field in skills
portfolio_db.CV.deleteMany({});

portfolio_db.CV.insertMany([
  {
    "_id": NumberLong(1),
    "_class": "pl.delukesoft.portfolioserver.portfolio.model.Resume",
    "user": {
      "_id": NumberLong(1),
      "username": "john.doe",
      "email": "john.doe@example.com"
    },
    "image": {
      "_id": NumberLong(1),
      "name": "lg.jpg",
      "src": "/images/lg.jpg"
    },
    "title": "Lead Java Developer",
    "summary": "Senior Java Developer with extensive experience in banking software development. Specialized in building robust, secure, and scalable applications.",
    "skills": [
      {
        "_id": NumberLong(1),
        "name": "Java",
        "description": "JVM",
        "level": 5
      },
      {
        "_id": NumberLong(2),
        "name": "Kotlin",
        "description": "JVM",
        "level": 4
      },
      {
        "_id": NumberLong(3),
        "name": "Scala",
        "description": "JVM",
        "level": 3
      }
    ],
    "hobbies": ["Music Production", "Open Source Contributing", "Yoga"],
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
    "experience": [
      {
        "_id": NumberLong(1),
        "business": {
          "_id": NumberLong(1),
          "name": "Bank"
        },
        "position": "Senior Developer",
        "summary": "Lead developer for core banking systems",
        "description": "Development of core banking applications, implementing secure transaction processing systems and leading integration projects with external financial services",
        "timespan": {
          "start": ISODate("2023-01-01"),
          "end": ISODate("2025-05-08")
        },
        "skills": [
          {
            "_id": NumberLong(1),
            "name": "Java",
            "description": "JVM",
            "level": 5
          },
          {
            "_id": NumberLong(2),
            "name": "Kotlin",
            "description": "JVM",
            "level": 4
          }
        ]
      }
    ],
    "createdOn": ISODate("2025-05-08T17:04:57.824Z"),
    "lastModified": ISODate("2025-05-08T17:04:57.824Z")
  },
  {
    "_id": NumberLong(2),
    "_class": "pl.delukesoft.portfolioserver.portfolio.model.Resume",
    "user": {
      "_id": NumberLong(2),
      "username": "jane.smith",
      "email": "jane.smith@example.com"
    },
    "image": {
      "_id": NumberLong(1),
      "name": "lg.jpg",
      "src": "/images/lg.jpg"
    },
    "title": "Lead Java Developer",
    "summary": "Full Stack Developer with strong focus on modern web technologies and startup environment experience. Passionate about creating efficient and user-friendly applications.",
    "skills": [
      {
        "_id": NumberLong(1),
        "name": "Java",
        "description": "JVM",
        "level": 4
      },
      {
        "_id": NumberLong(4),
        "name": "JavaScript",
        "description": "Web Development",
        "level": 5
      },
      {
        "_id": NumberLong(5),
        "name": "React",
        "description": "Frontend Framework",
        "level": 5
      }
    ],
    "hobbies": ["Music Production", "Open Source Contributing", "Yoga"],
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
    "experience": [
      {
        "_id": NumberLong(2),
        "business": {
          "_id": NumberLong(2),
          "name": "Tech Startup"
        },
        "position": "Full Stack Developer",
        "summary": "Full-stack development of modern web applications",
        "description": "Designed and implemented full-stack solutions using React and Spring Boot, including real-time data processing features and responsive UI components",
        "timespan": {
          "start": ISODate("2024-01-01"),
          "end": ISODate("2025-06-10")
        },
        "skills": [
          {
            "_id": NumberLong(4),
            "name": "JavaScript",
            "description": "Web Development",
            "level": 5
          },
          {
            "_id": NumberLong(5),
            "name": "React",
            "description": "Frontend Framework",
            "level": 5
          }
        ]
      }
    ],
    "createdOn": ISODate("2025-06-10T14:30:00.000Z"),
    "lastModified": ISODate("2025-06-10T14:30:00.000Z")
  },
  {
    "_id": NumberLong(3),
    "_class": "pl.delukesoft.portfolioserver.portfolio.model.Resume",
    "user": {
      "_id": NumberLong(3),
      "username": "alex.tech",
      "email": "alex.tech@example.com"
    },
    "title": "Lead Java Developer",
    "summary": "Experienced Backend Developer and Technical Lead with proven expertise in building scalable distributed systems and leading development teams.",
    "skills": [
      {
        "_id": NumberLong(2),
        "name": "Kotlin",
        "description": "JVM",
        "level": 5
      },
      {
        "_id": NumberLong(6),
        "name": "Spring Boot",
        "description": "Backend Framework",
        "level": 5
      },
      {
        "_id": NumberLong(7),
        "name": "MongoDB",
        "description": "Database",
        "level": 4
      }
    ],
    "hobbies": ["Music Production", "Open Source Contributing", "Yoga"],
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
    "experience": [
      {
        "_id": NumberLong(3),
        "business": {
          "_id": NumberLong(3),
          "name": "E-commerce Platform"
        },
        "position": "Backend Developer",
        "summary": "Backend development for high-load e-commerce platform",
        "description": "Developed and maintained high-load microservices architecture, implementing efficient caching strategies and optimizing database performance",
        "timespan": {
          "start": ISODate("2022-06-01"),
          "end": ISODate("2024-01-15")
        },
        "image": {
          "_id": NumberLong(1),
          "name": "profile_photo.jpg",
          "src": "/images/lg.jpg"
        },
        "skills": [
          {
            "_id": NumberLong(2),
            "name": "Kotlin",
            "description": "JVM",
            "level": 5
          },
          {
            "_id": NumberLong(7),
            "name": "MongoDB",
            "description": "Database",
            "level": 4
          }
        ]
      },
      {
        "_id": NumberLong(4),
        "business": {
          "_id": NumberLong(4),
          "name": "IT Consulting"
        },
        "position": "Technical Lead",
        "summary": "Technical leadership and architecture design",
        "description": "Led multiple development teams, architected solution designs, and implemented best practices for code quality and development processes",
        "timespan": {
          "start": ISODate("2024-01-15"),
          "end": ISODate("2025-07-15")
        },
        "skills": [
          {
            "_id": NumberLong(2),
            "name": "Kotlin",
            "description": "JVM",
            "level": 5
          },
          {
            "_id": NumberLong(6),
            "name": "Spring Boot",
            "description": "Backend Framework",
            "level": 5
          }
        ]
      }
    ],
    "createdOn": ISODate("2025-07-15T09:45:00.000Z"),
    "lastModified": ISODate("2025-07-15T09:45:00.000Z")
  }
]);

portfolio_db.createCollection("Sequence");

portfolio_db.Sequence.insertMany(
  [
    {
      _id: NumberLong(1),
      collectionName: "cv",
      sequenceNumber: NumberLong(3)
    },
    {
      _id: NumberLong(2),
      collectionName: "skill",
      sequenceNumber: NumberLong(7)
    },
    {
      _id: NumberLong(3),
      collectionName: "business",
      sequenceNumber: NumberLong(4)
    },
    {
      _id: NumberLong(4),
      collectionName: "experience",
      sequenceNumber: NumberLong(4)
    }
  ]
);

