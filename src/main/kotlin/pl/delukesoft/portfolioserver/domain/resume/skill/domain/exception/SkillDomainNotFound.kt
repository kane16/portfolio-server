package pl.delukesoft.portfolioserver.domain.resume.skill.domain.exception

import org.springframework.http.HttpStatus
import pl.delukesoft.portfolioserver.utility.exception.LoggableResponseStatusException

class SkillDomainNotFound(name: String): LoggableResponseStatusException(HttpStatus.NOT_FOUND, "Skill Domain with name $name not found") {
}