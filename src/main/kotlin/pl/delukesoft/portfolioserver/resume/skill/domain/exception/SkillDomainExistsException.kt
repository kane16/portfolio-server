package pl.delukesoft.portfolioserver.resume.skill.domain.exception

import org.springframework.http.HttpStatus
import pl.delukesoft.portfolioserver.platform.exception.LoggableResponseStatusException

class SkillDomainExistsException(name: String) :
  LoggableResponseStatusException(HttpStatus.BAD_REQUEST, "Skill Domain with name $name already exists")