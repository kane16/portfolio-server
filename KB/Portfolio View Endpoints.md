# Endpoints for Resume access via portfolio view

Base path: `/cv` (`PortfolioController`)

- POST `/cv/{id}` — Get resume by id as a portfolio view
    - Auth: `ROLE_ADMIN` or `ROLE_CANDIDATE`
    - Request: optional body `PortfolioSearch` (filters content)
    - Response: `PortfolioDTO`

- POST `/cv` — Get default (current user) resume as a portfolio view
    - Auth: none specified (public endpoint in controller), but typically behind infra auth; verify deployment settings
    - Request: optional body `PortfolioSearch`
    - Response: `PortfolioDTO`

`PortfolioSearch` is mapped to internal search criteria and applied by `ResumeFacade` when building the view.