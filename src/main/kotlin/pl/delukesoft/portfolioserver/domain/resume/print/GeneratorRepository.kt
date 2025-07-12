package pl.delukesoft.portfolioserver.domain.resume.print

import org.springframework.data.mongodb.repository.MongoRepository
import pl.delukesoft.portfolioserver.domain.sequence.Sequence
import java.util.UUID

interface GeneratorRepository: MongoRepository<Sequence, UUID> {

  fun existsByCollectionName(name: String): Boolean

  fun findByCollectionName(name: String): Sequence?

}