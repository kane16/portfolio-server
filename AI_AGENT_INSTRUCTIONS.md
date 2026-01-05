# AI Agent Instructions - Portfolio Server

> **Purpose**: Maintain code consistency, architectural patterns, and quality standards when assisting with the
> Portfolio Server codebase.

---

## Project Context

**Tech Stack**: Spring Boot 3.4.5 + Kotlin 1.9.25 + MongoDB + GraalVM Native
**Architecture**: Layered architecture with Controller → Facade → Service → Repository
**Domain**: Resume/CV management with versioning, filtering, and role-based access

---

## Architectural Patterns (MUST FOLLOW)

### 1. **Layer Responsibilities**

```
Controller (application/)
  ↓ calls
Facade (application/)
  ↓ calls
Service (domain/)
  ↓ calls
Repository (domain/)
```

**Rules**:

- **Controllers**: Handle HTTP, extract user context, delegate to Facades. NO business logic.
- **Facades**: Orchestrate services, map DTOs ↔ domain models, handle cross-service coordination.
- **Services**: Contain business logic, domain rules, validation. Marked with `@Service`.
- **Repositories**: Data access only. MongoDB operations. Extend `MongoRepository` or custom `@Repository`.
- **Mappers**: `@Component` classes that convert between DTOs and domain models. Separate from Facades.

**Never**:

- ❌ Put business logic in Controllers
- ❌ Call Services directly from Controllers (always use Facades)
- ❌ Put HTTP concerns in Services
- ❌ Mix mapping logic with business logic

### 2. **Naming Conventions**

| Layer      | Pattern              | Example                                      |
|------------|----------------------|----------------------------------------------|
| Controller | `{Entity}Controller` | `SkillController`, `ResumeController`        |
| Facade     | `{Entity}Facade`     | `ResumeFacade`, `SkillFacade`                |
| Service    | `{Entity}Service`    | `ResumeService`, `SkillService`              |
| Repository | `{Entity}Repository` | `ResumeRepository`                           |
| Mapper     | `{Entity}Mapper`     | `SkillMapper`, `PortfolioMapper`             |
| DTO        | `{Entity}DTO`        | `SkillDTO`, `ResumeEditDTO`                  |
| Exception  | `{Entity}{Reason}`   | `SkillNotFound`, `ResumeOperationNotAllowed` |

### 3. **Package Structure**

```
src/main/kotlin/pl/delukesoft/portfolioserver/
├── adapters/          # External integrations (auth, image, print)
├── application/       # Controllers, Facades, DTOs, Mappers (presentation layer)
│   ├── {feature}/     # Feature-specific (resume, portfolio, constraint)
│   │   ├── {Feature}Controller.kt
│   │   ├── {Feature}Facade.kt
│   │   ├── {Feature}Mapper.kt (if needed)
│   │   └── model/     # DTOs specific to this feature
├── domain/            # Business logic, Services, Repositories, domain models
│   ├── {entity}/      # Entity-specific (resume, skill, education)
│   │   ├── {Entity}.kt          # Domain model
│   │   ├── {Entity}Service.kt   # Business logic
│   │   ├── {Entity}Repository.kt
│   │   ├── {Entity}Validator.kt (if validation needed)
│   │   └── exception/           # Domain-specific exceptions
├── configuration/     # Spring configuration
└── utility/           # Cross-cutting concerns (logging, exceptions)
```

**Rule**: Keep features cohesive. If adding a new feature (e.g., "certification"), create the full vertical slice:

```
application/resume/certification/CertificationController.kt
application/resume/certification/CertificationFacade.kt
domain/resume/certification/Certification.kt
domain/resume/certification/CertificationService.kt
```

---

## Code Patterns

### 4. **Exception Handling**

All exceptions inherit from `LoggableResponseStatusException`:

```kotlin
// Bad ❌
throw Exception("Resume not found")

// Good ✅
class ResumeNotFound(id: Long? = null) :
  LoggableResponseStatusException(HttpStatus.NOT_FOUND, "Resume ${id ?: ""} not found")
```

**Pattern**: Create specific exceptions in `domain/{entity}/exception/` package.

**Global Handler**: `ErrorAdvisor` handles all exceptions. Don't add try-catch unless you need specific recovery logic.

### 5. **Authentication & Authorization**

```kotlin
// Controller level
@AuthRequired("ROLE_CANDIDATE", "ROLE_ADMIN")
@GetMapping("/resume/{id}")
fun getResume(@PathVariable id: Long, @RequestHeader("Authorization") token: String?): ResumeDTO {
  // Implementation
}
```

**Rules**:

- Always use `@AuthRequired` annotation on protected endpoints
- Always include `@RequestHeader("Authorization") token: String?` parameter (even if not used in code)
- Use `UserContext` (injected service) to get current user in Facades/Services
- Validate user ownership in Facades using: `resumeService.getResumeById(id, userContext.user)`

**UserContext Pattern in Facades**:

```kotlin
@Component
class ResumeFacade(
  private val userContext: UserContext,
  // other dependencies
) {
  private val currentUser
    get() = requireNotNull(userContext.user) { "Authenticated user is required" }

  fun someMethod() {
    // Use currentUser.username, currentUser.roles
  }
}
```

### 6. **Validation**

Two approaches used:

**A) Spring Validation (DTOs)**:

```kotlin
data class SkillDTO(
  @field:NotBlank(message = "Skill name is required")
  val name: String,
  @field:Min(1) @field:Max(10)
  val level: Int
)

@PostMapping
fun create(@Valid @RequestBody skill: SkillDTO) {
}
```

**B) Custom Domain Validation (Domain Models)**:

```kotlin
// Domain model
data class Skill(...) : WithConstraints {
  override fun validateConstraintPaths(validationFunc: (String, String?) -> ValidationResult): List<ValidationResult> {
    return listOf(
      validationFunc("resume.skill.name", name),
      validationFunc("resume.skill.description", description)
    )
  }
}

// Service with validation
@Service
class SkillService(private val validator: Validator) {

  @ValidateShortcut  // Custom annotation that triggers validation interceptor
  fun addSkill(skill: Skill): Boolean {
    // Validation happens via AOP before this executes
  }
}
```

**Rule**: Use Spring validation for API input (DTOs). Use domain validation for complex business rules.

### 7. **Caching**

```kotlin
// In search/filter models, implement toCacheKey() for consistent cache keys
data class PortfolioSearch(
  val business: List<String> = listOf(),
  val skills: List<String> = listOf(),
  val technologyDomain: List<String> = listOf()
) {
  fun toCacheKey(): String {
    val sortedBusiness = business.sorted().joinToString(",")
    val sortedSkills = skills.sorted().joinToString(",")
    val sortedTechnologyDomain = technologyDomain.sorted().joinToString(",")
    return "business:[$sortedBusiness];skills:[$sortedSkills];technologyDomain:[$sortedTechnologyDomain]"
  }
}

// In Facades, use toCacheKey() in @Cacheable annotations
@Cacheable("portfolio", key = "'id:' + #id + ';' + (#portfolioSearch?.toCacheKey() ?: 'default')")
fun getCvById(id: Long, portfolioSearch: PortfolioSearch? = null): PortfolioDTO {
}

@Cacheable("portfolio", key = "#portfolioSearch?.toCacheKey() ?: 'default'")
fun getDefaultCV(portfolioSearch: PortfolioSearch? = null): PortfolioDTO {
}

@CacheEvict(cacheNames = ["portfolio"], allEntries = true)
fun publishResume(version: ResumeVersion): Boolean {
}
```

**Rules**:

- Use `@Cacheable` for read operations (typically in Facades)
- Use `@CacheEvict` when data changes (Services that modify data)
- Always provide meaningful cache keys using `toCacheKey()` method for search/filter parameters
- Implement `toCacheKey()` in search/filter data classes to generate consistent, sorted cache keys
- Use safe-call operator with fallback: `#searchParam?.toCacheKey() ?: 'default'`
- Cache names: `"portfolio"` (for CV data), `"constraints"` (for validation constraints)

### 8. **Logging**

```kotlin
class ResumeController(...) {
  private val log = LoggerFactory.getLogger(this::class.java)

  @GetMapping("/{id}")
  fun getResume(@PathVariable id: Long): ResumeDTO {
    log.info("Received request to fetch Resume by id: {}", id)
    // Implementation
  }
}
```

**Rules**:

- Use SLF4J: `LoggerFactory.getLogger(this::class.java)`
- Log at **Controller entry points** (info level)
- Log **Service operations** (info for success, warn/error for issues)
- Use structured logging with `{}` placeholders
- Don't log sensitive data (tokens, passwords)

### 9. **Data Mapping (DTO ↔ Domain)**

```kotlin
@Component
class SkillMapper {

  fun mapToDTO(skill: Skill): SkillDTO {
    return SkillDTO(
      name = skill.name,
      level = skill.level,
      description = skill.description,
      domains = skill.domains.map { it.name }
    )
  }

  fun mapToSkill(dto: SkillDTO, availableDomains: List<SkillDomain>): Skill {
    return Skill(
      name = dto.name,
      level = dto.level,
      description = dto.description,
      domains = dto.domains.mapNotNull { domainName ->
        availableDomains.find { it.name == domainName }
      }
    )
  }
}
```

**Rules**:

- Create dedicated Mapper components (annotated with `@Component`)
- Keep mapping logic out of Facades and Services
- Handle nullable fields explicitly
- Mappers can depend on Services if needed (e.g., fetching related data)

### 10. **Repository Pattern**

```kotlin
// Interface
interface ResumeRepository : MongoRepository<Resume, Long> {
  fun findResumeById(id: Long): Resume?
  fun findResumeByIdAndUsername(id: Long, username: String): Resume?
}

// Custom implementations
@Repository
class ResumeModifyRepository(private val mongoTemplate: MongoTemplate) {
  fun changeShortcutInResume(shortcut: ResumeShortcut, resume: Resume): Boolean {
    // Complex MongoDB operations using mongoTemplate
  }
}
```

**Rules**:

- Simple CRUD: Extend `MongoRepository<Entity, ID>`
- Complex queries: Custom `@Repository` with `MongoTemplate`
- Return nullable types (`?`) when entity might not exist
- Name methods descriptively: `findResumeByIdAndUsername` vs `getResume`

### 11. **Domain Models**

```kotlin
@Document(collection = "Resume")
data class Resume(
  @Id val id: Long? = null,
  val shortcut: ResumeShortcut,
  val skills: List<Skill> = emptyList(),
  val experience: List<Experience> = emptyList(),
  val createdOn: LocalDateTime = LocalDateTime.now(),
  val lastModified: LocalDateTime = LocalDateTime.now()
)
```

**Rules**:

- Use `data class` for domain models
- Use `@Document` for MongoDB root collections
- Provide sensible defaults (empty lists, `LocalDateTime.now()`)
- Use nullable `id: Long?` (null before persisted)
- Avoid mutable fields (`var`) unless necessary
- Use composition: `Resume` contains `ResumeShortcut`, `List<Skill>`, etc.

---

## Common Implementation Patterns

### 12. **Adding a New Entity to Resume**

Example: Adding "certifications" to resume.

**Step 1: Create domain model**

```kotlin
// src/main/kotlin/.../domain/resume/certification/Certification.kt
data class Certification(
  val id: Long? = null,
  val name: String,
  val issuer: String,
  val dateObtained: LocalDate
) : WithConstraints {
  // ... validation
}
```

**Step 2: Add to Resume model**

```kotlin
@Document(collection = "Resume")
data class Resume(
  // ... existing fields
  val certifications: List<Certification> = emptyList()
)
```

**Step 3: Create Service**

```kotlin
@Service
class CertificationService(
  private val resumeModifyRepository: ResumeModifyRepository
) {
  fun addCertificationToResume(resume: Resume, certification: Certification): Boolean {
    return resumeModifyRepository.addCertification(resume, certification)
  }
}
```

**Step 4: Create Facade**

```kotlin
@Component
class CertificationFacade(
  private val resumeService: ResumeService,
  private val certificationService: CertificationService,
  private val userContext: UserContext
) {
  private val currentUser
    get() = requireNotNull(userContext.user) { "Authenticated user is required" }

  fun addCertification(resumeId: Long, certificationDTO: CertificationDTO): Boolean {
    val resume = resumeService.getResumeById(resumeId, currentUser)
    val certification = // map DTO to domain
      return certificationService.addCertificationToResume(resume, certification)
  }
}
```

**Step 5: Create Controller**

```kotlin
@RestController
class CertificationController(
  private val certificationFacade: CertificationFacade
) {
  private val log = LoggerFactory.getLogger(this::class.java)

  @PostMapping("/resume/edit/{resumeId}/certifications")
  @ResponseStatus(HttpStatus.CREATED)
  @AuthRequired("ROLE_CANDIDATE")
  fun addCertification(
    @PathVariable resumeId: Long,
    @Valid @RequestBody certification: CertificationDTO,
    @RequestHeader("Authorization") token: String?
  ): Boolean {
    log.info("Adding certification to resume {}", resumeId)
    return certificationFacade.addCertification(resumeId, certification)
  }
}
```

### 13. **Handling Business Logic Errors**

```kotlin
// In Service
fun publishResume(version: ResumeVersion, username: String): Boolean {
  val publishedVersion = resumeHistoryService.findPublishedResumeVersion(username)
  if (publishedVersion != null) {
    throw ResumeOperationNotAllowed("Published version already exists")
  }
  // proceed with publish
}

// Exception definition
class ResumeOperationNotAllowed(message: String) :
  LoggableResponseStatusException(HttpStatus.BAD_REQUEST, message)
```

**Pattern**: Validate business rules early, throw domain-specific exceptions.

---

## Testing Patterns

### 14. **Testing Guidelines**

**Location**: Tests mirror source structure in `src/test/kotlin/...`

**Integration Tests (BDD style)**:

```kotlin
// Uses Cucumber
@SpringBootTest
class ResumeStepDefinitions {
  // Given, When, Then steps
}
```

**Unit Tests**:

```kotlin
@Test
fun `should add skill to resume`() {
  // Given
  val resume = Resume(...)
  val skill = Skill(...)

  // When
  val result = skillService.addSkillToResume(resume, skill)

  // Then
  assertTrue(result)
}
```

**Mocking**:

```kotlin
@MockkBean
private lateinit var resumeRepository: ResumeRepository

every { resumeRepository.findResumeById(any()) } returns mockResume
```

---

## Code Quality Standards

### 15. **Code Style**

- **Indentation**: 2 spaces (Kotlin standard)
- **Line length**: Keep under 120 characters
- **Null safety**: Use Kotlin's `?`, `!!` sparingly, prefer `requireNotNull()`
- **Immutability**: Prefer `val` over `var`
- **Expression bodies**: Use when single expression: `fun getUser() = userService.find()`
- **Trailing commas**: Use in multi-line parameter lists

### 16. **Documentation**

**When to document**:

- Complex business logic that isn't obvious
- Public APIs (if building library)
- Algorithms with non-trivial complexity

**When NOT to document**:

- Self-explanatory code (good naming is better)
- CRUD operations
- Simple mappings

**Format**:

```kotlin
/**
 * Filters resume based on search criteria (skills, business, domains).
 * Returns subset of experience/skills matching the criteria.
 */
fun filterResumeWithCriteria(resume: Resume, search: ResumeSearch): Resume {
  // implementation
}
```

### 17. **Configuration Management**

**Profiles**:

- `dev`: Local development (`application-dev.yml`)
- `docker`: Docker deployment (`application-docker.yml`)
- `test`: Testing (`application-test.yml`)
- `prod`: Production (`application-prod.yml`)

**Never**:

- ❌ Hardcode URLs, credentials, or environment-specific values
- ❌ Commit `.env` files or secrets
- ✅ Use `application-{profile}.yml` for config
- ✅ Use environment variables for secrets

---

## Change Checklist

When implementing new features or changes, verify:

- [ ] **Layer separation maintained**: Controller → Facade → Service → Repository
- [ ] **Naming conventions followed**: Consistent with existing codebase
- [ ] **Package structure correct**: Feature placed in appropriate package
- [ ] **Authentication added**: `@AuthRequired` on protected endpoints with Authorization header
- [ ] **Exception handling**: Domain-specific exceptions, no generic throws
- [ ] **Logging added**: At Controller and Service entry points
- [ ] **Validation applied**: DTOs validated, domain rules enforced
- [ ] **Mapping separated**: DTOs mapped via dedicated Mapper components
- [ ] **Cache management**: `@Cacheable` for reads, `@CacheEvict` for writes if applicable
- [ ] **Tests written**: Unit tests for Services, integration tests for workflows
- [ ] **No business logic in Controllers**: All logic in Services/Facades
- [ ] **Null safety handled**: Proper use of `?`, `?:`, `requireNotNull()`
- [ ] **GraalVM compatibility**: No reflection without hints, check `NativeImageConfiguration.kt`

---

## Anti-Patterns to Avoid

### ❌ **Don't Do This**

1. **Mixing layers**:

```kotlin
// BAD: Business logic in Controller
@GetMapping
fun getResume(): ResumeDTO {
  val resume = repository.findById(1)  // ❌ Calling repo directly
  if (resume.skills.isEmpty()) {       // ❌ Business logic
    throw Exception("No skills")
  }
  return map(resume)                   // ❌ Mapping in controller
}
```

2. **Generic exceptions**:

```kotlin
throw Exception("Not found")  // ❌
throw RuntimeException("Error")  // ❌
```

3. **Missing user context validation**:

```kotlin
// BAD: No ownership check
fun deleteResume(id: Long) {
  resumeRepository.deleteById(id)  // ❌ Any user can delete any resume!
}

// GOOD: Validate ownership
fun deleteResume(id: Long) {
  val resume = resumeService.getResumeById(id, currentUser)  // ✅ Checks ownership
  resumeRepository.delete(resume)
}
```

4. **Tight coupling**:

```kotlin
// BAD
class ResumeFacade {
  val service = ResumeService()  // ❌ Creating dependencies
}

// GOOD
class ResumeFacade(
  private val service: ResumeService  // ✅ Constructor injection
)
```

---

## Final Reminders

1. **Consistency > Perfection**: Match existing patterns even if you'd do it differently
2. **Security first**: Always validate user permissions and input
3. **Read before writing**: Check similar features for patterns
4. **Test your changes**: Ensure existing tests pass, add new tests
5. **Keep it simple**: Don't over-engineer, follow YAGNI (You Aren't Gonna Need It)

When in doubt, find a similar feature in the codebase and follow its pattern.
