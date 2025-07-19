package pl.delukesoft.portfolioserver.domain.resume.language

import org.springframework.data.mongodb.repository.MongoRepository

interface LanguageRepository: MongoRepository<Language, Long> {

  fun findByName(name: String): Language?

}