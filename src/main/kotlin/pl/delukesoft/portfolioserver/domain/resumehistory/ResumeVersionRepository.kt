package pl.delukesoft.portfolioserver.domain.resumehistory

import org.springframework.data.mongodb.repository.MongoRepository

interface ResumeVersionRepository: MongoRepository<ResumeVersion, Long> {
}