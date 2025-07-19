package pl.delukesoft.portfolioserver.utility.loader

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.domain.resume.ResumeService
import pl.delukesoft.portfolioserver.domain.resume.experience.business.BusinessService
import pl.delukesoft.portfolioserver.domain.resume.hobby.HobbyService
import pl.delukesoft.portfolioserver.domain.resume.language.LanguageService
import pl.delukesoft.portfolioserver.domain.resume.skill.SkillService
import pl.delukesoft.portfolioserver.domain.resume.skill.domain.SkillDomainService
import pl.delukesoft.portfolioserver.domain.resumehistory.ResumeHistoryService
import pl.delukesoft.portfolioserver.utility.loader.model.UploadResume

@Component
class DataLoaderFacade(
  private val resumeService: ResumeService,
  private val hobbyService: HobbyService,
  private val languageService: LanguageService,
  private val businessService: BusinessService,
  private val dataLoaderMapper: DataLoaderMapper,
  private val skillService: SkillService,
  private val resumeHistoryService: ResumeHistoryService,
  private val skillDomainService: SkillDomainService,
) {

  fun loadDataFromReadResumes(data: List<UploadResume>): Boolean {
    val allSkillDomains = skillDomainService.saveAll(
      data.flatMap { dataLoaderMapper.extractSkillDomainsFromResume(it) }.distinctBy { it.name }
    )
    val allSkills =
      skillService.saveAll(data.flatMap { dataLoaderMapper.extractSkillsFromResume(it, allSkillDomains) }.distinctBy { it.name })
    val allBusiness =
      businessService.saveAll(data.flatMap { dataLoaderMapper.extractBusinessesFromResume(it) }.distinctBy { it.name })
    val allLanguages =
      languageService.saveAll(data.flatMap { dataLoaderMapper.extractLanguagesFromResume(it) }.distinctBy { it.name })
    val allHobbies =
      hobbyService.saveAll(data.flatMap { dataLoaderMapper.extractHobbiesFromResume(it) }.distinctBy { it.name })
    data.map {
      Pair(
        dataLoaderMapper.mapToResume(
          it,
          allSkills,
          allBusiness,
          allHobbies,
          allLanguages
        ),
        dataLoaderMapper.mapToUser(it.user)
      )
    }.map { Pair(resumeService.save(it.first), it.second) }
      .forEach {
        resumeHistoryService.save(
          dataLoaderMapper.mapToResumeHistory(
            it.first,
            it.second
          )
        )
      }
    return true
  }

}