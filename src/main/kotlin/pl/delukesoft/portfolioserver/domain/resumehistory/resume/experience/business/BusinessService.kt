package pl.delukesoft.portfolioserver.domain.resumehistory.resume.experience.business

import org.springframework.stereotype.Service
import pl.delukesoft.portfolioserver.domain.resumehistory.resume.experience.business.exception.BusinessNotFound
import pl.delukesoft.portfolioserver.domain.resumehistory.resume.print.GeneratorService

@Service
class BusinessService(
  private val generatorService: GeneratorService,
  private val businessWriteRepository: BusinessWriteRepository,
) {

  fun findByName(name: String): Business {
    return businessWriteRepository.findByName(name) ?: throw BusinessNotFound(name)
  }

  fun saveAll(businesses: List<Business>): List<Business> {
    return businesses.map { save(it) }
  }

  fun save(business: Business): Business {
    if (business.id == null) {
      val generatedId = generatorService.getAndIncrement("business")
      return businessWriteRepository.save(business.copy(id = generatedId))
    }
    TODO("Not yet implemented")
  }

}