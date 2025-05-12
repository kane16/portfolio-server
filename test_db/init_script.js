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

portfolio_db.CV.insertMany(
  [
    {
      "_id": NumberLong(1),
      "_class": "pl.delukesoft.portfolioserver.portfolio.model.Curriculum",
      "createdOn": ISODate("2025-05-08T17:04:57.824Z"),
      "experience": [
        {
          "_id": NumberLong(1),
          "business": {
            "_id": NumberLong(1),
            "name": "Bank"
          },
          "position": "Senior Developer",
          "shortDescription": "Development of banking applications"
        }
      ],
      "lastModified": ISODate("2025-05-08T17:04:57.824Z"),
      "shortDescription": "Experienced Java Developer",
      "skills": [
        {
          "_id": NumberLong(1),
          "name": "Java",
          "description": "JVM"
        },
        {
          "_id": NumberLong(2),
          "name": "Kotlin",
          "description": "JVM"
        },
        {
          "_id": NumberLong(3),
          "name": "Scala",
          "description": "JVM"
        }
      ]
    },
    {
      "_id": NumberLong(2),
      "_class": "pl.delukesoft.portfolioserver.portfolio.model.Curriculum",
      "createdOn": ISODate("2025-06-10T14:30:00.000Z"),
      "experience": [
        {
          "_id": NumberLong(2),
          "business": {
            "_id": NumberLong(2),
            "name": "Tech Startup"
          },
          "position": "Full Stack Developer",
          "shortDescription": "Building modern web applications"
        }
      ],
      "lastModified": ISODate("2025-06-10T14:30:00.000Z"),
      "shortDescription": "Full Stack Developer with startup experience",
      "skills": [
        {
          "_id": NumberLong(1),
          "name": "Java",
          "description": "JVM"
        },
        {
          "_id": NumberLong(4),
          "name": "JavaScript",
          "description": "Web Development"
        },
        {
          "_id": NumberLong(5),
          "name": "React",
          "description": "Frontend Framework"
        }
      ]
    },
    {
      "_id": NumberLong(3),
      "_class": "pl.delukesoft.portfolioserver.portfolio.model.Curriculum",
      "createdOn": ISODate("2025-07-15T09:45:00.000Z"),
      "experience": [
        {
          "_id": NumberLong(3),
          "business": {
            "_id": NumberLong(3),
            "name": "E-commerce Platform"
          },
          "position": "Backend Developer",
          "shortDescription": "Building scalable backend services"
        },
        {
          "_id": NumberLong(4),
          "business": {
            "_id": NumberLong(4),
            "name": "IT Consulting"
          },
          "position": "Technical Lead",
          "shortDescription": "Leading development teams"
        }
      ],
      "lastModified": ISODate("2025-07-15T09:45:00.000Z"),
      "shortDescription": "Backend specialist with leadership experience",
      "skills": [
        {
          "_id": NumberLong(2),
          "name": "Kotlin",
          "description": "JVM"
        },
        {
          "_id": NumberLong(6),
          "name": "Spring Boot",
          "description": "Backend Framework"
        },
        {
          "_id": NumberLong(7),
          "name": "MongoDB",
          "description": "Database"
        }
      ]
    }
  ]
)

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

