package pl.delukesoft.portfolioserver.domain.resumehistory.resume.hobby

import org.springframework.data.mongodb.repository.MongoRepository

interface HobbyRepository: MongoRepository<Hobby, Long> {
}