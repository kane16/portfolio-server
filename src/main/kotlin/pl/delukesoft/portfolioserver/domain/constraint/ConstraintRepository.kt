package pl.delukesoft.portfolioserver.domain.constraint

import org.springframework.cache.annotation.Cacheable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface ConstraintRepository : MongoRepository<FieldConstraint, String> {

  @Cacheable("constraints")
  @Query("{}")
  fun findCachedConstraints(): List<FieldConstraint>

}