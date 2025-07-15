package pl.delukesoft.portfolioserver.domain.resumehistory.resume.experience.business

import org.springframework.data.mongodb.repository.MongoRepository

interface BusinessWriteRepository: MongoRepository<Business, Long> {
}