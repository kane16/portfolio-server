## Application goals and drivers

Main goal of the app is to provide user information about candidate resume, in form of skills, business experience. Candidate should have possibility to create, edit and expand his resume in order for his profile to be more visible. Admin should have an option to make all actions and add candidate, remove, clear candidate data. Data should be shown for admin in easy to grasp way, showing visible resume and in progress ones.
## Portfolio core functionalities

To reiterate and be more specific, we differentiate between 3 actors in the system:
* Viewer user with potential option to comment - ROLE_USER
* Candidate that is able to create his own resume, view resume history and edit it - ROLE_CANDIDATE
* Administrator that will have all permissions of previous users, also will be able to view and change resume of other users, create new candidates
#### Resume view

After entering main page, there will be main user resume shown - user that is simultaneously candidate and administrator - if:
* Resume doesn't exist
* User being candidate and admin does not exist
Main page should show error page with comment:
* No default resume found
To view specific resume, special authorities are needed - viewing entity should have ROLE_USER, anonymous access will be rejected.

```plantuml
actor ANONYMOUS
group Default resume
ANONYMOUS -> SYSTEM: getDefaultCV() 
SYSTEM -> ANONYMOUS: <font color=green> 200 ResumeDTO
end
group Specific resume by id
ANONYMOUS -> SYSTEM: getResumeById(id: Long)
SYSTEM -> ANONYMOUS: <font color=red> 401 Anonymous access is restricted to this endpoint 
end
group Default resume doesn't exist
ANONYMOUS -> SYSTEM: getDefaultCV() 
SYSTEM -> ANONYMOUS: <font color=red> 404 No default resume found
end
```

```plantuml
actor ROLE_USER
group Default resume
ROLE_USER -> SYSTEM: getDefaultCV() 
SYSTEM -> ROLE_USER: <font color=green> 200 ResumeDTO
end
group Specific resume by id
ROLE_USER -> SYSTEM: getResumeById(id: Long)
SYSTEM -> ROLE_USER: <font color=green> 200 ResumeDTO
end
group Default resume doesn't exist
ROLE_USER -> SYSTEM: getDefaultCV() 
SYSTEM -> ROLE_USER: <font color=red> 404 No default resume found
end
```

#### Resume Edit


