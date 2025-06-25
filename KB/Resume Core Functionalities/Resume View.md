After opening [[Main page view]], there will be main user resume shown - user that is simultaneously candidate and administrator - if:
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
actor ROLE_ADMIN
group Default resume
ROLE_ADMIN -> SYSTEM: getDefaultCV() 
SYSTEM -> ROLE_ADMIN: <font color=green> 200 ResumeDTO
end
group Specific resume by id
ROLE_ADMIN -> SYSTEM: getResumeById(id: Long)
SYSTEM -> ROLE_ADMIN: <font color=green> 200 ResumeDTO
end
group Default resume doesn't exist
ROLE_ADMIN -> SYSTEM: getDefaultCV() 
SYSTEM -> ROLE_ADMIN: <font color=red> 404 No default resume found
end
```

In case of Candidate, by default his CV should be shown first as he enters with authorization, if he doesn't have any then application default should be returned.