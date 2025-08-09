package pl.delukesoft.portfolioserver.utility.loader

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.adapters.auth.User
import pl.delukesoft.portfolioserver.domain.resume.Resume
import pl.delukesoft.portfolioserver.domain.resume.ResumeShortcut
import pl.delukesoft.portfolioserver.domain.resume.experience.Experience
import pl.delukesoft.portfolioserver.domain.resume.experience.business.Business
import pl.delukesoft.portfolioserver.domain.resume.experience.skill.SkillExperience
import pl.delukesoft.portfolioserver.domain.resume.hobby.Hobby
import pl.delukesoft.portfolioserver.domain.resume.language.Language
import pl.delukesoft.portfolioserver.domain.resume.language.LanguageLevel
import pl.delukesoft.portfolioserver.domain.resume.skill.Skill
import pl.delukesoft.portfolioserver.domain.resume.skill.domain.SkillDomain
import pl.delukesoft.portfolioserver.utility.exception.InvalidMappingException
import pl.delukesoft.portfolioserver.utility.loader.model.*

@Component
class DataLoaderMapper {

  fun mapToResume(
    uploadResume: UploadResume,
    skills: List<Skill>,
    businesses: List<Business>,
    hobbies: List<Hobby>,
    languages: List<Language>,
    user: User,
    skillDomains: List<SkillDomain>
  ): Resume {
    return Resume(
      shortcut = ResumeShortcut(
        title = uploadResume.title,
        summary = uploadResume.summary,
        image = uploadResume.image,
        user = user
      ),
      experience = uploadResume.experience.map { mapToExperience(it, businesses, skills) },
      sideProjects = uploadResume.sideProjects.map { mapToExperience(it, businesses, skills) },
      hobbies = uploadResume.hobbies.map { mapToHobby(it, hobbies, user) },
      languages = uploadResume.languages.map { mapToLanguage(it, languages, user) },
      skills = uploadResume.skills.map { mapToSkill(it, skills, skillDomains, user) },
      createdOn = uploadResume.createdOn,
      lastModified = uploadResume.lastModified,
    )
  }

  fun extractSkillDomainsFromResume(resume: UploadResume): List<SkillDomain> {
    return resume.skills.flatMap {
      it.techDomains.map {
        SkillDomain(
          name = it,
          username = resume.user.username
        )
      }
    }.distinctBy { it.name }
  }

  fun extractSkillsFromResume(resume: UploadResume, domains: List<SkillDomain>, user: User): List<Skill> {
    return resume.skills.map { Skill(
      name = it.name,
      description = it.description,
      level = it.level.toInt(),
      domains = it.techDomains.map {
        domains.find { d -> d.name == it } ?: throw InvalidMappingException("Skill domain not found: $it")
      },
      username = user.username
    ) }
  }

  fun extractBusinessesFromResume(resume: UploadResume, user: User): List<Business> {
    return resume.experience.map {
      Business(
        name = it.business.name,
        username = user.username
      )
    } + resume.sideProjects.map {
      Business(
        name = it.business.name,
        username = user.username
      )
    }
  }

  fun extractHobbiesFromResume(resume: UploadResume, user: User): List<Hobby> {
    return resume.hobbies.map { Hobby(
      name = it,
      username = user.username
    ) }
  }

  fun extractLanguagesFromResume(resume: UploadResume, user: User): List<Language> {
    return resume.languages.map { Language(
      name = it.name,
      username = user.username,
      level = LanguageLevel.entries.find { l -> l.level == it.level }
        ?: throw InvalidMappingException("Language level not found: ${it.level}")
    ) }
  }

  private fun mapToSkill(skill: UploadSkill, skills: List<Skill>, skillDomains: List<SkillDomain>, user: User): Skill {
    return Skill(
      id = skills.find { it.name == skill.name && it.username == user.username }?.id,
      name = skill.name,
      description = skill.description,
      level = skill.level.toInt(),
      domains = skill.techDomains.map {
        skillDomains.find { d -> d.name == it } ?: throw InvalidMappingException("Skill domain not found: $it")
      },
      username = user.username
    )
  }


  private fun mapToSkillExperience(skill: UploadSkill, availableSkills: List<Skill>): SkillExperience {
    return SkillExperience(
      availableSkills.find { it.name == skill.name } ?: throw InvalidMappingException("Skill not found: ${skill.name}"),
      skill.level.toInt(),
      skill.description ?: ""
    )
  }

  private fun mapToLanguage(workLanguage: UploadWorkLanguage, languages: List<Language>, user: User): Language {
    return languages.find { it.name == workLanguage.name && it.username == user.username }
      ?: throw InvalidMappingException("Language not found: ${workLanguage.name}")
  }

  private fun mapToHobby(hobby: String, hobbies: List<Hobby>, user: User): Hobby {
    return hobbies.find { it.name == hobby && it.username == user.username }
      ?: throw InvalidMappingException("Hobby not found: $hobby")
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

  fun mapToUser(user: UploadUser): User {
    return User(
      username = user.username,
      email = user.email,
      roles = user.roles
    )
  }


}