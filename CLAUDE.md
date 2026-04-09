# CLAUDE.md

This file provides guidance to Claude Code when working in this repository.

## Project Instructions

Always follow the conventions, architectural patterns, and quality standards defined
in [AI_AGENT_INSTRUCTIONS.md](./AI_AGENT_INSTRUCTIONS.md). That document is the source of truth for:

- Layer responsibilities (Controller → Facade → Service → Repository)
- Naming conventions and package structure
- Exception handling, authentication, validation, caching, logging
- DTO ↔ domain mapping, repository patterns, domain models
- Testing and code style standards
- Anti-patterns to avoid

Before making changes, consult `AI_AGENT_INSTRUCTIONS.md` to confirm your approach matches the established patterns.

## Hard Rules

- **Do NOT write production logic code.** Do not implement, modify, or add business logic in production source files (
  controllers, facades, services, repositories, mappers, domain models, etc. under `src/main/`). If a task would require
  writing production logic, stop and ask the user how to proceed instead of writing it.
