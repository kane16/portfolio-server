package pl.delukesoft.portfolioserver.utility.configuration

import org.springframework.aot.hint.MemberCategory
import org.springframework.aot.hint.RuntimeHints
import org.springframework.aot.hint.RuntimeHintsRegistrar
import org.springframework.aot.hint.TypeReference
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.ImportRuntimeHints

@Configuration
@ImportRuntimeHints(
  NativeImageConfiguration.KotlinCollectionsRuntimeHints::class,
  NativeImageConfiguration.CaffeineRuntimeHints::class,
  NativeImageConfiguration.JjwtRuntimeHints::class
)
class NativeImageConfiguration {

  class KotlinCollectionsRuntimeHints : RuntimeHintsRegistrar {
    override fun registerHints(hints: RuntimeHints, classLoader: ClassLoader?) {
      // Register internal Kotlin collection classes by name for Jackson serialization
      listOf(
        "kotlin.collections.EmptyList",
        "kotlin.collections.EmptyMap",
        "kotlin.collections.EmptySet"
      ).forEach { className ->
        hints.reflection().registerTypeIfPresent(
          classLoader,
          className,
          MemberCategory.INVOKE_DECLARED_CONSTRUCTORS,
          MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS,
          MemberCategory.INVOKE_DECLARED_METHODS,
          MemberCategory.INVOKE_PUBLIC_METHODS,
          MemberCategory.DECLARED_FIELDS,
          MemberCategory.PUBLIC_FIELDS
        )
      }
    }
  }

  class CaffeineRuntimeHints : RuntimeHintsRegistrar {
    override fun registerHints(hints: RuntimeHints, classLoader: ClassLoader?) {
      // Register Caffeine cache internal classes for native image compilation
      listOf(
        "com.github.benmanes.caffeine.cache.SSMSA",
        "com.github.benmanes.caffeine.cache.SSMS",
        "com.github.benmanes.caffeine.cache.SSMSAW",
        "com.github.benmanes.caffeine.cache.SSMSW",
        "com.github.benmanes.caffeine.cache.PSMS",
        "com.github.benmanes.caffeine.cache.PSMSA",
        "com.github.benmanes.caffeine.cache.PSMSW",
        "com.github.benmanes.caffeine.cache.PSAMS",
        "com.github.benmanes.caffeine.cache.PSMSAW"
      ).forEach { className ->
        hints.reflection().registerTypeIfPresent(
          classLoader,
          className,
          MemberCategory.INVOKE_DECLARED_CONSTRUCTORS,
          MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS,
          MemberCategory.INVOKE_DECLARED_METHODS,
          MemberCategory.INVOKE_PUBLIC_METHODS,
          MemberCategory.DECLARED_FIELDS,
          MemberCategory.PUBLIC_FIELDS
        )
      }
    }
  }

  class JjwtRuntimeHints : RuntimeHintsRegistrar {
    override fun registerHints(hints: RuntimeHints, classLoader: ClassLoader?) {
      // Register JJWT internal classes for native image compilation
      listOf(
        "io.jsonwebtoken.impl.DefaultJwtBuilder",
        "io.jsonwebtoken.impl.DefaultJwtParserBuilder",
        "io.jsonwebtoken.impl.DefaultClaimsBuilder",
        "io.jsonwebtoken.impl.DefaultJwtHeaderBuilder",
        "io.jsonwebtoken.impl.security.StandardSecureDigestAlgorithms",
        "io.jsonwebtoken.impl.security.StandardKeyAlgorithms",
        "io.jsonwebtoken.impl.security.StandardEncryptionAlgorithms",
        "io.jsonwebtoken.impl.security.StandardHashAlgorithms",
        "io.jsonwebtoken.impl.security.StandardKeyOperations",
        "io.jsonwebtoken.impl.io.StandardCompressionAlgorithms",
        "io.jsonwebtoken.jackson.io.JacksonSerializer",
        "io.jsonwebtoken.jackson.io.JacksonDeserializer"
      ).forEach { className ->
        hints.reflection().registerType(
          TypeReference.of(className),
          MemberCategory.INVOKE_DECLARED_CONSTRUCTORS,
          MemberCategory.INVOKE_PUBLIC_METHODS
        )
      }
      hints.resources().registerPattern("META-INF/services/io.jsonwebtoken.*")
    }
  }

}
