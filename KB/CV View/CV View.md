[[CV print]] is page that shows information about the user in structured - CV like way. It allows user to print it, as it's styled to format print and raw page in correct way. 

### 1. CV Access

For specific users there are restrictions on viewing CV:
- Administrator will be able to visit all CV, not only his own.
- Anonymous user will see default site CV (There is only one). 
- Candidate will be able to view his own default CV on this page.

>[!NOTE]
>In case of Candidate, by default his CV should be shown first as he enters with authorization, if he doesn't have any then application default should be returned.

### 2. Pulling CV flow


```plantuml
actor user
participant System as system

group Default resume
user -> system: GET /pdf
system -> user: 200 Default Resume
end

group Resume by ID
user -> system: GET /pdf/{id}
system -> user: 200 Resume with id
end

```


### 2. Search criteria in CV view

Search criteria on CV view will affect results within CV itself, not total number of results. Following search criteria will be taken into account:
- business domain (multiple choice) - i.e. banking, logistics etc. - No strict validation, only mapping errors may occur
- skill (multiple choice) - i.e. Java, Scala, Kotlin - No strict validation, only mapping errors may occur
- Technical domain (multiple choice) - i.e. Backend, Fronend, Database, UX - No stricts validation, only mapping errors may occur

>[!important]
Search functionality will not be used within view itself, it will be extensively used to show results for [[Portfolio View]] and [[Resume Edit]] preview.

Search criteria will affect Skills, Work history and Side projects segments of CV.

```plantuml
actor user
participant System as system

group Default resume
user -> system: POST /pdf/{id} with PortfolioSearch body
system -> user: 200 Default Resume
end

group Resume by ID
user -> system: POST /pdf/{id} with PortfolioSearch body
system -> user: 200 Resume with id
end

```

### 3. Page usage

View will be used as Iframe in [[Portfolio View]] and [[Resume Edit]]

>[!Important]
>View will use [[Application connector model]]

