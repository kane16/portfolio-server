package pl.delukesoft.portfolioserver.resume.skill

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pl.delukesoft.authplugin.security.AuthRequired
import pl.delukesoft.portfolioserver.resume.ResumeFacade
import pl.delukesoft.portfolioserver.resume.author.PortfolioAuthorDTO
import pl.delukesoft.portfolioserver.resume.author.PortfolioAuthorMapper

@RestController
@Tag(name = "Resume - Skills", description = "Skill entries and skill domains")
class SkillController(
  private val skillFacade: SkillFacade,
  private val resumeFacade: ResumeFacade,
  private val authorMapper: PortfolioAuthorMapper
) {

  private val log = LoggerFactory.getLogger(this::class.java)

  @GetMapping("/skills")
  @AuthRequired(role = "ROLE_AUTHOR", app = "portfolio")
  @Operation(summary = "Get user skills", description = "Retrieve all skills for the current user")
  @SecurityRequirement(name = "Bearer Authentication")
  fun getUserSkills(
    @RequestHeader("Authorization") token: String?
  ): List<SkillDTO> {
    return skillFacade.getSkills()
  }

  @GetMapping("/skills/domains")
  @AuthRequired(role = "ROLE_AUTHOR", app = "portfolio")
  @Operation(
    summary = "Get skill domains",
    description = "Retrieve all available skill domain names"
  )
  @SecurityRequirement(name = "Bearer Authentication")
  fun getDomains(
    @RequestHeader("Authorization") token: String?
  ): List<String> {
    return skillFacade.getSkillDomains()
  }

  @GetMapping("/skills/resume/{resumeId}")
  @AuthRequired(role = "ROLE_AUTHOR", app = "portfolio")
  @Operation(
    summary = "Get skills by resume ID",
    description = "Retrieve all skills associated with a specific resume"
  )
  @SecurityRequirement(name = "Bearer Authentication")
  fun getSkillsByResumeId(
    @PathVariable("resumeId") resumeId: Long,
    @RequestHeader("Authorization") token: String?
  ): List<SkillDTO> {
    return resumeFacade.findSkillsByResumeId(resumeId)
  }

  @PostMapping("/skills/domains")
  @ResponseStatus(HttpStatus.CREATED)
  @AuthRequired(role = "ROLE_AUTHOR", app = "portfolio")
  @Operation(summary = "Add skill domain", description = "Create a new skill domain")
  @SecurityRequirement(name = "Bearer Authentication")
  fun addSkillDomain(
    @RequestBody name: String,
    @RequestHeader("Authorization") token: String?
  ): PortfolioAuthorDTO {
    return authorMapper.mapToDto(skillFacade.addDomain(name))
  }

  @AuthRequired(role = "ROLE_AUTHOR", app = "portfolio")
  @PostMapping("/resume/edit/{resumeId}/skills")
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Add skill to resume", description = "Add a skill entry to a resume")
  @SecurityRequirement(name = "Bearer Authentication")
  fun addSkillToResume(
    @PathVariable("resumeId") resumeId: Long,
    @RequestBody skill: SkillDTO,
    @RequestHeader("Authorization") token: String?,
  ): Boolean {
    log.info("Received request to add skill to portfolio")
    return resumeFacade.addSkillToResume(resumeId, skill)
  }

  @AuthRequired(role = "ROLE_AUTHOR", app = "portfolio")
  @DeleteMapping("/resume/edit/{resumeId}/skills/{skillName}")
  @Operation(
    summary = "Delete skill from resume",
    description = "Delete a skill from a resume by skill name"
  )
  @SecurityRequirement(name = "Bearer Authentication")
  fun deleteSkill(
    @PathVariable("resumeId") resumeId: Long,
    @PathVariable("skillName") skillName: String,
    @RequestHeader("Authorization") token: String?
  ): Boolean {
    return resumeFacade.deleteSkillFromResume(resumeId, skillName)
  }

  @AuthRequired(role = "ROLE_AUTHOR", app = "portfolio")
  @PutMapping("/resume/edit/{resumeId}/skills/{skillName}")
  @Operation(
    summary = "Edit skill in resume",
    description = "Edit a skill entry in a resume by skill name"
  )
  @SecurityRequirement(name = "Bearer Authentication")
  fun editSkill(
    @PathVariable("resumeId") resumeId: Long,
    @PathVariable("skillName") skillName: String,
    @RequestBody skill: SkillDTO,
    @RequestHeader("Authorization") token: String?
  ): Boolean {
    return resumeFacade.editSkillWithName(resumeId, skillName, skill)
  }

}
