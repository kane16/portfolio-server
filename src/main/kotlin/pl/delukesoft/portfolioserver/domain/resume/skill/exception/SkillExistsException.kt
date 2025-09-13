package pl.delukesoft.portfolioserver.domain.resume.skill.exception

import org.springframework.http.HttpStatus
import pl.delukesoft.portfolioserver.utility.exception.LoggableResponseStatusException

class SkillExistsException(name: String) :
  LoggableResponseStatusException(HttpStatus.BAD_REQUEST, "Skill with name $name already exists")