package pl.delukesoft.portfolioserver.utility.loader

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.domain.resume.ResumeService
import pl.delukesoft.portfolioserver.domain.resume.experience.business.BusinessService
import pl.delukesoft.portfolioserver.domain.resume.hobby.HobbyService
import pl.delukesoft.portfolioserver.domain.resume.language.LanguageService
import pl.delukesoft.portfolioserver.domain.resume.skill.SkillService
import pl.delukesoft.portfolioserver.domain.resume.skill.domain.SkillDomainService
import pl.delukesoft.portfolioserver.utility.loader.model.UploadResume

@Component
class DataLoaderFacade(
  private val resumeService: ResumeService,
  private val hobbyService: HobbyService,
  private val languageService: LanguageService,
  private val businessService: BusinessService,
  private val dataLoaderMapper: DataLoaderMapper,
  private val skillService: SkillService,
  private val skillDomainService: SkillDomainService,
) {

  fun loadDataFromReadResumes(data: List<UploadResume>): Boolean {
    val allSkillDomains = skillDomainService.saveAll(
      data.flatMap { dataLoaderMapper.extractSkillDomainsFromResume(it) }.distinctBy { "${it.name}-${it.username}" }
    )
    val allSkills =
      skillService.saveAll(data.flatMap {
        dataLoaderMapper.extractSkillsFromResume(
          it,
          allSkillDomains,
          dataLoaderMapper.mapToUser(it.user)
        )
      }
        .distinctBy { "${it.name}-${it.username}" })
    val allBusiness =
      businessService.saveAll(data.flatMap {
        dataLoaderMapper.extractBusinessesFromResume(
          it,
          dataLoaderMapper.mapToUser(it.user)
        )
      }.distinctBy { "${it.name}-${it.username}" })
    val allLanguages =
      languageService.saveAll(data.flatMap {
        dataLoaderMapper.extractLanguagesFromResume(
          it,
          dataLoaderMapper.mapToUser(it.user)
        )
      }.distinctBy { "${it.name}-${it.username}" })
    val allHobbies =
      hobbyService.saveAll(data.flatMap {
        dataLoaderMapper.extractHobbiesFromResume(
          it,
          dataLoaderMapper.mapToUser(it.user)
        )
      }.distinctBy { "${it.name}-${it.username}" })
    data.map {
      dataLoaderMapper.mapToResume(
        it,
        allSkills,
        allBusiness,
        allHobbies,
        allLanguages,
        dataLoaderMapper.mapToUser(it.user),
        allSkillDomains
      )
    }.map {
      resumeService.addResume(
        it
      )
    }
    return true
  }

}