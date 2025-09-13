package pl.delukesoft.portfolioserver.domain.resume.skill.domain.exception

import org.springframework.http.HttpStatus
import pl.delukesoft.portfolioserver.utility.exception.LoggableResponseStatusException

class SkillDomainExistsException(name: String) :
  LoggableResponseStatusException(HttpStatus.BAD_REQUEST, "Skill Domain with name $name already exists")