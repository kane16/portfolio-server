package pl.delukesoft.portfolioserver.utility.loader

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.domain.resume.write.BusinessWriteRepository
import pl.delukesoft.portfolioserver.domain.resume.write.HobbyWriteRepository
import pl.delukesoft.portfolioserver.domain.resume.write.LanguageWriteRepository
import pl.delukesoft.portfolioserver.domain.resume.write.ResumeWriteRepository
import pl.delukesoft.portfolioserver.domain.resume.write.SkillWriteRepository
import pl.delukesoft.portfolioserver.utility.loader.model.UploadResume

@Component
class DataLoaderFacade(
  private val resumeRepository: ResumeWriteRepository,
  private val hobbyRepository: HobbyWriteRepository,
  private val skillRepository: SkillWriteRepository,
  private val languageRepository: LanguageWriteRepository,
  private val businessRepository: BusinessWriteRepository,
  private val dataLoaderMapper: DataLoaderMapper
) {

  fun loadDataFromReadResumes(data: List<UploadResume>): Boolean {
    val allSkills = skillRepository.saveAll(data.flatMap { dataLoaderMapper.extractSkillsFromResume(it) }.distinctBy { it.name })
    val allBusiness = businessRepository.saveAll(data.flatMap { dataLoaderMapper.extractBusinessesFromResume(it) }.distinctBy { it.name })
    val allLanguages = languageRepository.saveAll(data.flatMap { dataLoaderMapper.extractLanguagesFromResume(it) }.distinctBy { it.name })
    val allHobbies = hobbyRepository.saveAll(data.flatMap { dataLoaderMapper.extractHobbiesFromResume(it) }.distinctBy { it.name })
    data.map {
      dataLoaderMapper.mapToResume(
        it,
        allSkills,
        allBusiness,
        allHobbies,
        allLanguages
      )
    }
    return true
  }

}