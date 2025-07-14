package pl.delukesoft.portfolioserver.utility.loader

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.domain.resume.write.*
import pl.delukesoft.portfolioserver.utility.loader.model.UploadResume

@Component
class DataLoaderFacade(
  private val resumeService: ResumeWriteService,
  private val hobbyService: HobbyService,
  private val languageService: LanguageService,
  private val businessService: BusinessService,
  private val dataLoaderMapper: DataLoaderMapper,
  private val skillService: SkillService
) {

  fun loadDataFromReadResumes(data: List<UploadResume>): Boolean {
    val allSkills = skillService.saveAll(data.flatMap { dataLoaderMapper.extractSkillsFromResume(it) }.distinctBy { it.name })
    val allBusiness = businessService.saveAll(data.flatMap { dataLoaderMapper.extractBusinessesFromResume(it) }.distinctBy { it.name })
    val allLanguages = languageService.saveAll(data.flatMap { dataLoaderMapper.extractLanguagesFromResume(it) }.distinctBy { it.name })
    val allHobbies = hobbyService.saveAll(data.flatMap { dataLoaderMapper.extractHobbiesFromResume(it) }.distinctBy { it.name })
    resumeService.saveAll(data.map {
      dataLoaderMapper.mapToResume(
        it,
        allSkills,
        allBusiness,
        allHobbies,
        allLanguages
      )
    })
    return true
  }

}