package pl.delukesoft.portfolioserver.domain.resume.experience.business

import org.springframework.data.mongodb.repository.MongoRepository

interface BusinessRepository: MongoRepository<Business, Long> {

  fun findByName(name: String): Business?

}