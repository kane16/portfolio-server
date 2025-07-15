package pl.delukesoft.portfolioserver.domain.resumehistory.resume.skill

import org.springframework.data.mongodb.repository.MongoRepository

interface SkillRepository: MongoRepository<Skill, Long> {
}