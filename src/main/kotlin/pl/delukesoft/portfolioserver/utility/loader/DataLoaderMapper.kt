package pl.delukesoft.portfolioserver.utility.loader

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.adapters.auth.User
import pl.delukesoft.portfolioserver.domain.resume.model.Business
import pl.delukesoft.portfolioserver.domain.resume.model.Experience
import pl.delukesoft.portfolioserver.domain.resume.model.Hobby
import pl.delukesoft.portfolioserver.domain.resume.model.Language
import pl.delukesoft.portfolioserver.domain.resume.model.LanguageLevel
import pl.delukesoft.portfolioserver.domain.resume.model.Resume
import pl.delukesoft.portfolioserver.domain.resume.model.Skill
import pl.delukesoft.portfolioserver.domain.resume.model.SkillExperience
import pl.delukesoft.portfolioserver.domain.resume.model.WorkLanguage
import pl.delukesoft.portfolioserver.utility.exception.InvalidMappingException
import pl.delukesoft.portfolioserver.utility.loader.model.UploadExperience
import pl.delukesoft.portfolioserver.utility.loader.model.UploadResume
import pl.delukesoft.portfolioserver.utility.loader.model.UploadSkill
import pl.delukesoft.portfolioserver.utility.loader.model.UploadUser
import pl.delukesoft.portfolioserver.utility.loader.model.UploadWorkLanguage

@Component
class DataLoaderMapper {

  fun mapToResume(
    uploadResume: UploadResume,
    skills: List<Skill>,
    businesses: List<Business>,
    hobbies: List<Hobby>,
    languages: List<Language>
  ): Resume {
    return Resume(
      user = mapToUploadUser(uploadResume.user),
      title = uploadResume.title,
      summary = uploadResume.summary,
      experience = uploadResume.experience.map { mapToExperience(it, businesses, skills) },
      sideProjects = uploadResume.sideProjects.map { mapToExperience(it, businesses, skills) },
      hobbies = uploadResume.hobbies.map { mapToHobby(it, hobbies) },
      languages = uploadResume.languages.map { mapToLanguage(it, languages) },
      skills = uploadResume.skills.map { mapToSkill(it) },
      image = uploadResume.image,
      createdOn = uploadResume.createdOn,
      lastModified = uploadResume.lastModified,
    )
  }

  fun extractSkillsFromResume(resume: UploadResume): List<Skill> {
    return resume.skills.map { mapToSkill(it) }
  }

  fun extractBusinessesFromResume(resume: UploadResume): List<Business> {
    return resume.experience.map { Business(name = it.business.name) } + resume.sideProjects.map { Business(name = it.business.name) }
  }

  fun extractHobbiesFromResume(resume: UploadResume): List<Hobby> {
    return resume.hobbies.map { Hobby(name = it) }
  }

  fun extractLanguagesFromResume(resume: UploadResume): List<Language> {
    return resume.languages.map { Language(name = it.name) }
  }

  private fun mapToSkill(skill: UploadSkill): Skill {
    return Skill(
      name = skill.name,
      description = skill.description,
      level = skill.level.toInt()
    )
  }


  private fun mapToSkillExperience(skill: UploadSkill, availableSkills: List<Skill>): SkillExperience {
    return SkillExperience(
      availableSkills.find { it.name == skill.name } ?: throw InvalidMappingException("Skill not found: ${skill.name}"),
      skill.level.toInt(),
      skill.description ?: ""
    )
  }

  private fun mapToLanguage(workLanguage: UploadWorkLanguage, languages: List<Language>): WorkLanguage {
    return WorkLanguage(
      languages.find { it.name == workLanguage.name }
        ?: throw InvalidMappingException("Language not found: ${workLanguage.name}"),
      LanguageLevel.entries.find { it.level == workLanguage.level }
        ?: throw InvalidMappingException("Language level not found: ${workLanguage.level}"),
    )
  }

  private fun mapToHobby(hobby: String, hobbies: List<Hobby>): Hobby {
    return hobbies.find { it.name == hobby } ?: throw InvalidMappingException("Hobby not found: $hobby")
  }

  private fun mapToExperience(
    experience: UploadExperience,
    businesses: List<Business>,
    skills: List<Skill>
  ): Experience {
    return Experience(
      business = businesses.find { it.name == experience.business.name }
        ?: throw InvalidMappingException("Business not found: ${experience.business}"),
      position = experience.position,
      description = experience.description,
      skills = experience.skills.map { mapToSkillExperience(it, skills) },
      timespan = experience.timespan,
      summary = experience.summary
    )
  }

  private fun mapToUploadUser(user: UploadUser): User {
    return User(
      username = user.username,
      email = user.email,
      roles = user.roles
    )
  }


}