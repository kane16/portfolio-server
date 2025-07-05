Portfolio View is a view where guest or user can read and identify skills and information about provided portfolio. There is dynamic side that allows user to choose which skills or other information he finds interesting, as well as specify concrete expectations and check if given portfolio contains information about it.
### 1. Anonymous user enters portfolio view

When anonymous user enters [[Main page view]], default portfolio should be shown. Customization of shown skills, business and search phrase is possible. 

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

### 2. Candidate enters portfolio view

When Candidate enters [[Main page view]] his default portfolio should be shown, or default in application if his own is not existing.

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

### 3. Admin enters portfolio view

When Admin enters [[Main page view]] his default portfolio should be shown, or default in application if his own is not existing.

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

### 4. Navigation to different pages

There is Top Bar in [[Main page view]], so user can view CV and Blog page (which will point him to _blank_ page). After login, he will be able to see also Edit portfolio button that will navigate him to page which will show all portfolios that modification can be based on. 

> [!IMPORTANT]
>Search part should be fulltext field that will be triggered with delay not to overwhelm server. It should cause changes on both lists below that shows Skills and Experiences. 

> [!IMPORTANT]
> Two lists will also change depending on selected skills and businesses.

