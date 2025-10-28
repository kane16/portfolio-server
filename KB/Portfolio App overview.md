## Application goals and drivers

The main goal of the application is to provide users with information about a candidate’s resume. This includes skills, professional experience, business history, and other data relevant to the candidate’s portfolio.

## Portfolio core functionalities

The application is divided into three independent parts, each serving a distinct role. To be more precise, the system involves three main actors:

- **Viewer (ROLE_USER):** Can browse resumes and optionally add comments.
    
- **Candidate (ROLE_CANDIDATE):** Can create their own resume, view its history, and edit it.
    
- **Administrator (ROLE_ADMIN):** Has all permissions of the other roles, plus the ability to view and edit resumes on behalf of other users.

## Available views

There are two views accessible to guests and one that requires additional permissions:

- **#CV-View** 
	- Displays the resume in a standardized, printable format.
- **#Portfolio-View** 
	- Allows recruiters to interact with the CV by searching for specific skills, phrases, or other relevant criteria.
- **#Edit-View**
    - Handles the full resume lifecycle — from creating a shortcut, through editing and validation, to final publication.  
    - Access is restricted to **ROLE_CANDIDATE** and **ROLE_ADMIN**. 
    - Opens in a new page when accessed.

## Data model used in views

Each view has it's own purposes. #CV-View and #Portfolio-View are using #Portfolio as base Transfer Object, while #Edit-View is using #Resume, because it's internal part of an application and should better reflect internal structure. Difference Between #Resume and #Portfolio  is described in [[Resume vs Portfolio]] page.
