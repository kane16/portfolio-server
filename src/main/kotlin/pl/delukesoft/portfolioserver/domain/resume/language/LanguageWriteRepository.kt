package pl.delukesoft.portfolioserver.domain.resume.language

import org.springframework.data.mongodb.repository.MongoRepository

interface LanguageWriteRepository: MongoRepository<Language, Long> {
}