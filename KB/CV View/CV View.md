[[CV print]] is page that shows information about the user in structured - CV like way. It allows user to print it, as it's styled to format print and raw page in correct way.

### 1. CV for anonymous user

```plantuml
actor ANONYMOUS as guest
actor SYSTEM as system
group Default print
guest -> system: GET /pdf
system -> guest: 200 HTML Print Template PDF
end
group Default print with search
guest -> system: POST /pdf with PortfolioSearchDTO 
system -> guest: <font color=green> 200 HTML Print Template PDF
end
group Default print doesn't exist
guest -> system: GET /pdf
system -> guest: <font color=red> 404 No Print Template found
end
```

Anonymous user will see default site CV (There is only one). 

### 2. CV for Candidate

```plantuml
actor ROLE_CANDIDATE as candidate
participant SYSTEM as system
group Candidate Default print
candidate -> system: GET /pdf/{id} 
system -> candidate: <font color=green> 200 HTML Print Specific to Candidate Template PDF
end
group Candidate Default print with search
candidate -> system: POST /pdf/{id} PortfolioSearchDTO 
system -> candidate: <font color=green> 200 HTML Print Specific to Candidate Template PDF
end
group Default print doesn't exist
candidate -> system: GET /pdf/{id} 
system -> candidate: <font color=red> 404 No Candidate Print Template found
end
group Candidate Default print with search doesn't exist
candidate -> system: POST /pdf/{id} PortfolioSearchDTO 
system -> candidate: <font color=green> 404 No Candidate Print Template found
end
group Candidate wants to view preview of his edited CV
candidate -> system: GET /pdf/edit/{id}
system -> candidate: <font color=green> 200 HTML Print of edited Template PDF
end
group Candidate wants to view preview of non-existent user edited CV
candidate -> system: GET /pdf/edit/{id}
system -> candidate: <font color=red> 404 No Candidate Print template found
end
group Candidate wants to view preview of different user edited CV
candidate -> system: GET /pdf/edit/{id}
system -> candidate: <font color=red> 404 No Candidate Print template found
end
```

Candidate will be able to view his own default CV on this page.

>[!NOTE]
>In case of Candidate, by default his CV should be shown first as he enters with authorization, if he doesn't have any then application default should be returned.
### 3. CV for Admin

```plantuml
actor ROLE_ADMIN as admin
participant SYSTEM as system
group Default print
admin -> system: getDefaultCV() 
system -> admin: <font color=green> 200 HTML Admin Print Template PDF
end
group Specific User print
admin -> system: getDefaultCV(request param CandidateId) 
system -> admin: <font color=green> 200 HTML Candidate Print Template PDF
end
group Specific User print not found
admin -> system: getDefaultCV(request param CandidateId) 
system -> admin: <font color=red> 404 No Candidate Print Template found
end
group Default print doesn't exist
admin -> system: getDefaultCV() 
system -> admin: <font color=red> 404 No Admin Print Template found
end
group Admin wants to view preview of his edited CV
admin -> system: GET /pdf/edit/{id}
system -> admin: <font color=green> 200 HTML Print of edited Template PDF
end
group Admin wants to view preview of different user edited CV
admin -> system: GET /pdf/edit/{id}
system -> admin: <font color=green> 200 HTML Print of edited Template PDF
end
group Admin wants to view preview of non-existent user edited CV
admin -> system: GET /pdf/edit/{id}
system -> admin: <font color=red> 404 No Candidate Print template found
end
```

Admin should be able to see any CV on the website, by default he will see his own CV.

### 4. Page usage

View will be used as Iframe in [[Portfolio View]] and [[Resume Edit]]

>[!Important]
>View will use [[Application connector model]]

