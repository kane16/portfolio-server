package pl.delukesoft.portfolioserver.generator

import org.springframework.data.mongodb.repository.MongoRepository
import java.util.UUID

interface GeneratorRepository: MongoRepository<Sequence, UUID> {

  fun existsByCollectionName(name: String): Boolean

  fun findByCollectionName(name: String): Sequence?

}