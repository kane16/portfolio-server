# Implementation pointers

- All resume operations should go through `ResumeFacade` to ensure user/author context and validation are applied.
- For UI: use `ResumeEditDTO` for the edit screens and `PortfolioDTO` for read-only CV rendering.
- Consider refactoring `fullname` mapping to use an actual user profile field.
- Validate `ResumeShortcutDTO` constraints on the client side to reduce 400 responses.
- Publishing rules: only one published version per user; unpublish before publishing another.
