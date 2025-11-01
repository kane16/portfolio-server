To explain the differences, let’s start with their purposes.  
#Portfolio is a transfer object designed to simplify and summarize the internal structure into a more readable form. Its fields are intended to be used directly for printing in a CV.

When it comes to search, the criteria are defined at a higher level — independent of the details contained within #Resume.

## Backend model vs Frontend model

On backend we use #Resume as a model for storing and manipulating data. It is designed to be used in the backend and is
not intended for direct use in the frontend.

Transfer objects are as follows: ResumeEditDTO is for editing #Resume data, PortfolioDTO is for viewing #Resume data,
and ResumeSearchDTO is for searching #Resume data.

## Representation

#Resume is transfer object designed to work with full lifecycle of resume change. 