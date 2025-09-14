Portfolio View is a view where guest or user can read and identify skills and information about provided portfolio.
There is dynamic side that allows user to choose which skills or other information he finds interesting, as well as
specify concrete expectations and check if given portfolio contains information about it.

### 1. Anonymous user enters portfolio view

### 2. Navigation to different pages

There is Top Bar in [[Main page view]], so user can view [[CV View]] and Blog posts (which will point him to _blank_
page). After login, he will be able to see also Edit portfolio button that will navigate him to page which will show all
portfolios that modification can be based on.

> [!IMPORTANT]
> Search part should be fulltext field that will be triggered with delay not to overwhelm server. It should cause
> changes on both lists below that shows Skills and Experiences.

> [!IMPORTANT]
> Two lists will also change depending on selected skills and businesses.

### 3. Portfolio search

Search functionality uses [[Resume model]] from server to retrieve specific. Whole backend part follows CQRS
architectural pattern to divide read part from write part. Read part will be far simpler, there will be at most one
resume per user. Write part will store different resume versions and user will be able to base new default resume on
chosen version, not just default one.

> [!Important]
> View will use [[Application connector model]]

Search criteria on CV view will affect results within CV itself, not total number of results. Following search criteria
will be taken into account:

- business domain (multiple choice) - i.e. banking, logistics etc. - No strict validation, only mapping errors may occur
- skill (multiple choice) - i.e. Java, Scala, Kotlin - No strict validation, only mapping errors may occur
- Technical domain (multiple choice) - i.e. Backend, Fronend, Database, UX - No stricts validation, only mapping errors
  may occur

> [!important]
> Search functionality will not be used within view itself, it will be extensively used to show results
> for [[Portfolio View]] and [[Resume Edit]] preview.

Search criteria will affect Skills, Work history and Side projects segments of CV.

```plantuml
actor user
participant System as system
database db

group Default resume
user -> system: POST /pdf/{id} with PortfolioSearch body
system -> db: findDefaultResume()
db -> system: Default Resume
system -> system: Filter out parts of Resume according to criteria
system -> user: 200 Default Resume
end

group Resume by ID
user -> system: POST /pdf/{id} with PortfolioSearch body
system -> user: 200 Resume with id
end

```
