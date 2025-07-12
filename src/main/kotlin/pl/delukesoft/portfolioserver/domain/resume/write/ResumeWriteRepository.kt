package pl.delukesoft.portfolioserver.domain.resume.write

import org.springframework.data.mongodb.repository.MongoRepository
import pl.delukesoft.portfolioserver.domain.resume.model.Resume

interface ResumeWriteRepository: MongoRepository<Resume, Long> {


}