package pl.delukesoft.portfolioserver.domain.resume.hobby

import org.springframework.data.mongodb.repository.MongoRepository

interface HobbyRepository: MongoRepository<Hobby, Long> {
}