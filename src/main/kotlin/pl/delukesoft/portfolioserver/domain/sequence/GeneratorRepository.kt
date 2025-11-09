package pl.delukesoft.portfolioserver.domain.sequence

import org.springframework.data.mongodb.repository.MongoRepository

interface GeneratorRepository : MongoRepository<Sequence, Long> {

  fun existsByCollectionName(name: String): Boolean

  fun findByCollectionName(name: String): Sequence?

}