[[CV print]] is shown to user, it's dynamically generated data based on portfolio that was filled 

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

>[!NOTE]
>In case of Candidate, by default his CV should be shown first as he enters with authorization, if he doesn't have any then application default should be returned.

