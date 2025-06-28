After opening /portfolio/pdf link - [[CV print]] should show up. Behaviour of print should be different depending on user that calls it. There are 3 user cases:

- Guest
- Candidate
- Admin

```plantuml
actor ANONYMOUS as guest
group Default print
guest -> SYSTEM: getDefaultCV() 
SYSTEM -> guest: <font color=green> 200 HTML Print Template PDF
end
group Default print doesn't exist
guest -> SYSTEM: getDefaultCV() 
SYSTEM -> guest: <font color=red> 404 No Print Template found
end
```

```plantuml
actor ROLE_CANDIDATE as candidate
group Candidate Default print
candidate -> SYSTEM: getDefaultCV() 
SYSTEM -> candidate: <font color=green> 200 HTML Print Specific to Candidate Template PDF
end
group Default print doesn't exist
candidate -> SYSTEM: getDefaultCV() 
SYSTEM -> candidate: <font color=red> 404 No Candidate Print Template found
end
```

```plantuml
actor ROLE_ADMIN as admin
group Default print
admin -> SYSTEM: getDefaultCV() 
SYSTEM -> admin: <font color=green> 200 HTML Admin Print Template PDF
end
group Specific User print
admin -> SYSTEM: getDefaultCV(request param CandidateId) 
SYSTEM -> admin: <font color=green> 200 HTML Candidate Print Template PDF
end
group Specific User print not found
admin -> SYSTEM: getDefaultCV(request param CandidateId) 
SYSTEM -> admin: <font color=red> 404 No Candidate Print Template found
end
group Default print doesn't exist
admin -> SYSTEM: getDefaultCV() 
SYSTEM -> admin: <font color=red> 404 No Admin Print Template found
end
```

All those cases should be covered with tests.