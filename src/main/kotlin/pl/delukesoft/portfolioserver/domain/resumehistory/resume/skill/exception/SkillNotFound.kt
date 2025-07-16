package pl.delukesoft.portfolioserver.domain.resumehistory.resume.skill.exception

import org.springframework.http.HttpStatus
import pl.delukesoft.portfolioserver.utility.exception.LoggableResponseStatusException

class SkillNotFound(name: String): LoggableResponseStatusException(HttpStatus.NOT_FOUND, "Skill with name $name not found") {
}