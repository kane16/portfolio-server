## Application goals and drivers

Main goal of the app is to provide user information about candidate resume, in form of skills, business, experience and
other data relevant for candidate portfolio.

## Portfolio core functionalities

There are three separate parts in application independent on each other:

To reiterate and be more specific, we differentiate between 3 actors in the system:

* Viewer user with potential option to comment - ROLE_USER
* Candidate that is able to create his own resume, view resume history and edit it - ROLE_CANDIDATE
* Administrator that will have all permissions of previous users, also will be able to view and change resume of other
  users, create new candidates

Two views are available for guests - [[CV View]] and [[Portfolio View]]. In order to be able to enter [[Resume Edit]],
visit edit functionality - ROLE_CANDIDATE or ROLE_ADMIN are needed. After clicking link specific page should show up
in _blank page

