After user opens main /portfolio page, [[Main page view]] should be shown. It will show errors in cases:
* Resume doesn't exist
* User being candidate and admin does not exist
Main page should show error page with comment:
* No default resume found
To view specific resume, special authorities are needed - viewing entity should have ROLE_USER, anonymous access will be rejected.

```plantuml
actor ANONYMOUS
group Default resume
ANONYMOUS -> SYSTEM: getDefaultCV(searchText, business, skill) 
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
actor ROLE_CANDIDATE as candidate
group Default resume
candidate -> SYSTEM: getDefaultCV(searchText, business, skill)  
SYSTEM -> candidate: <font color=green> 200 ResumeDTO
end
group Specific resume by id being candidate resume
candidate -> SYSTEM: getResumeById(id: Long, searchText, business, skill)
SYSTEM -> candidate: <font color=green> 200 ResumeDTO 
end
group Specific resume by id not being candidate resume
candidate -> SYSTEM: getResumeById(id: Long, searchText, business, skill)
SYSTEM -> candidate: <font color=red> User is not authorized to access the resource 
end
group Default resume doesn't exist
candidate -> SYSTEM: getDefaultCV(searchText, business, skill) 
SYSTEM -> candidate: <font color=red> 404 No default resume found
end
```

```plantuml
actor ROLE_ADMIN
group Default resume
ROLE_ADMIN -> SYSTEM: getDefaultCV(searchText, business, skill)
SYSTEM -> ROLE_ADMIN: <font color=green> 200 ResumeDTO
end
group Specific resume by id
ROLE_ADMIN -> SYSTEM: getResumeById(id: Long, searchText, business, skill)
SYSTEM -> ROLE_ADMIN: <font color=green> 200 ResumeDTO
end
group Default resume doesn't exist
ROLE_ADMIN -> SYSTEM: getDefaultCV(searchText, business, skill) 
SYSTEM -> ROLE_ADMIN: <font color=red> 404 No default resume found
end
```

In case of Candidate, by default his CV should be shown first as he enters with authorization, if he doesn't have any then application default should be returned.

Search part should be fulltext field that will be triggered with delay not to overwhelm server. It should cause changes on both lists below that shows Skills and Experiences. 

Two lists will also change depending on selected skills and businesses.