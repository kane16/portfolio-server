## Application goals and drivers

Main goal of the app is to provide user information about candidate resume, in form of skills, business experience. Candidate should have possibility to create, edit and expand his resume in order for his profile to be more visible. Admin should have an option to make all actions and add candidate, remove, clear candidate data. Data should be shown for admin in easy to grasp way, showing visible resume and in progress ones.

## Portfolio App functionalities

* [[Resume view]]
## Portfolio core functionalities

To reiterate and be more specific, we differentiate between 3 actors in the system:
* Viewer user with potential option to comment - ROLE_USER
* Candidate that is able to create his own resume, view resume history and edit it - ROLE_CANDIDATE
* Administrator that will have all permissions of previous users, also will be able to view and change resume of other users, create new candidates
#### Resume Edit

Edit will consist set of operations that after opening the Resume will be available to user:
* Admin - for any Resume
* Candidate - for his own Resume
For the time of opened Resume, changes will be temporary in a sense that if changes will be rejected Resume will get back to initial state.
