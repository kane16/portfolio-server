package pl.delukesoft.portfolioserver.configuration

import org.springframework.aot.hint.MemberCategory
import org.springframework.aot.hint.RuntimeHints
import org.springframework.aot.hint.RuntimeHintsRegistrar
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.ImportRuntimeHints

@Configuration
@ImportRuntimeHints(NativeImageConfiguration.KotlinCollectionsRuntimeHints::class)
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

}
