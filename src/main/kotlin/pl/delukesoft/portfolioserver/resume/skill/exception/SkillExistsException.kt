package pl.delukesoft.portfolioserver.resume.skill.exception

import org.springframework.http.HttpStatus
import pl.delukesoft.portfolioserver.platform.exception.LoggableResponseStatusException

class SkillExistsException(name: String) :
  LoggableResponseStatusException(HttpStatus.BAD_REQUEST, "Skill with name $name already exists")