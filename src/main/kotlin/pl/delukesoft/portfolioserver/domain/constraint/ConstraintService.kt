package pl.delukesoft.portfolioserver.domain.constraint

import org.slf4j.LoggerFactory
import org.springframework.cache.CacheManager
import org.springframework.cache.interceptor.SimpleKey
import org.springframework.stereotype.Service
import pl.delukesoft.portfolioserver.domain.validation.ValidationResult

@Service
class ConstraintService(
  private val constraintRepository: ConstraintRepository,
  private val cacheManager: CacheManager? = null
) {
  private val log = LoggerFactory.getLogger(this::class.java)

  fun getConstraintsForEndpoint(): List<FieldConstraint> {
    val source = if (isConstraintsCacheHit()) "cache" else "db"
    val constraints = constraintRepository.findCachedConstraints()
    log.info("Constraints requested, source={}", source)
    return constraints
  }

  fun getConstraints(): List<FieldConstraint> {
    return constraintRepository.findCachedConstraints()
  }

  fun validateConstraint(path: String, value: String?): ValidationResult {
    return getConstraints().find { it.path == path }?.validate(value)
      ?: ValidationResult.build("Constraint not found for path: $path")
  }

  private fun isConstraintsCacheHit(): Boolean {
    return cacheManager
      ?.getCache(CONSTRAINTS_CACHE_NAME)
      ?.get(SimpleKey.EMPTY) != null
  }

  companion object {
    private const val CONSTRAINTS_CACHE_NAME = "constraints"
  }

}
