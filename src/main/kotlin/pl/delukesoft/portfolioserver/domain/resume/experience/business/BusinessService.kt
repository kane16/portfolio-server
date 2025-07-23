package pl.delukesoft.portfolioserver.domain.resume.experience.business

import org.springframework.stereotype.Service
import pl.delukesoft.portfolioserver.domain.resume.experience.business.exception.BusinessNotFound
import pl.delukesoft.portfolioserver.domain.sequence.GeneratorService

@Service
class BusinessService(
  private val generatorService: GeneratorService,
  private val businessRepository: BusinessRepository,
) {

  fun getByName(name: String): Business {
    return businessRepository.findByName(name) ?: throw BusinessNotFound(name)
  }

  fun saveAll(businesses: List<Business>): List<Business> {
    return businesses.map { save(it) }
  }

  fun save(business: Business): Business {
    if (business.id == null) {
      val generatedId = generatorService.getAndIncrement("business")
      return businessRepository.save(business.copy(id = generatedId))
    }
    TODO("Not yet implemented")
  }

  fun getAll(): List<Business> {
    return businessRepository.findAll()
  }

}