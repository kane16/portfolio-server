package pl.delukesoft.portfolioserver.platform.configuration

import io.swagger.v3.oas.models.*
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.media.Content
import io.swagger.v3.oas.models.media.MediaType
import io.swagger.v3.oas.models.media.Schema
import io.swagger.v3.oas.models.parameters.RequestBody
import io.swagger.v3.oas.models.responses.ApiResponse
import io.swagger.v3.oas.models.responses.ApiResponses
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.tags.Tag
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
  NativeImageConfiguration.JjwtRuntimeHints::class,
  NativeImageConfiguration.SpringDocRuntimeHints::class,
  NativeImageConfiguration.LiquibaseMongoRuntimeHints::class
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
        "io.jsonwebtoken.impl.security.KeysBridge",
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

  class SpringDocRuntimeHints : RuntimeHintsRegistrar {
    override fun registerHints(hints: RuntimeHints, classLoader: ClassLoader?) {
      hints.resources().registerPattern("META-INF/resources/**")
      hints.resources().registerPattern("META-INF/resources/webjars/**")

      listOf(
        OpenAPI::class.java,
        Components::class.java,
        Paths::class.java,
        PathItem::class.java,
        Operation::class.java,
        Info::class.java,
        Contact::class.java,
        License::class.java,
        Tag::class.java,
        SecurityScheme::class.java,
        SecurityScheme.Type::class.java,
        SecurityScheme.In::class.java,
        SecurityRequirement::class.java,
        Schema::class.java,
        Content::class.java,
        MediaType::class.java,
        RequestBody::class.java,
        ApiResponse::class.java,
        ApiResponses::class.java
      ).forEach { clazz ->
        hints.reflection().registerType(
          clazz,
          MemberCategory.INVOKE_DECLARED_CONSTRUCTORS,
          MemberCategory.INVOKE_PUBLIC_METHODS
        )
      }
    }
  }

  class LiquibaseMongoRuntimeHints : RuntimeHintsRegistrar {
    override fun registerHints(hints: RuntimeHints, classLoader: ClassLoader?) {
      hints.resources().registerPattern("META-INF/services/**")
      hints.resources().registerPattern("www.liquibase.org/xml/ns/mongodb/**")
      hints.resources().registerPattern("liquibase.parser.core.xml/**")
      hints.resources().registerResourceBundle("liquibase/i18n/liquibase-mongo")

      listOf(
        "liquibase.ext.mongodb.database.MongoClientDriver",
        "liquibase.ext.mongodb.database.MongoConnection",
        "liquibase.ext.mongodb.database.MongoLiquibaseDatabase",
        "liquibase.ext.mongodb.change.AdminCommandChange",
        "liquibase.ext.mongodb.change.CreateCollectionChange",
        "liquibase.ext.mongodb.change.CreateIndexChange",
        "liquibase.ext.mongodb.change.DropCollectionChange",
        "liquibase.ext.mongodb.change.DropIndexChange",
        "liquibase.ext.mongodb.change.InsertManyChange",
        "liquibase.ext.mongodb.change.InsertOneChange",
        "liquibase.ext.mongodb.change.RunCommandChange",
        "liquibase.ext.mongodb.precondition.CollectionExistsPrecondition",
        "liquibase.ext.mongodb.precondition.DocumentExistsPrecondition",
        "liquibase.ext.mongodb.precondition.ExpectedDocumentCountPrecondition",
        "liquibase.ext.mongodb.lockservice.MongoLockService",
        "liquibase.ext.mongodb.changelog.MongoHistoryService",
        "liquibase.nosql.executor.NoSqlExecutor",
        "liquibase.nosql.executor.NoSqlGenerator",
        "liquibase.nosql.parser.json.JsonNoSqlChangeLogParser",
        "liquibase.nosql.snapshot.NoSqlSnapshotGenerator",
        "liquibase.changelog.FastCheckService",
        "liquibase.changelog.ChangeLogHistoryServiceFactory",
        "liquibase.changelog.visitor.ValidatingVisitorGeneratorFactory",
        "liquibase.change.ChangeFactory",
        "liquibase.changeset.ChangeSetServiceFactory",
        "liquibase.command.CommandFactory",
        "liquibase.configuration.ConfiguredValueModifierFactory",
        "liquibase.configuration.GlobalConfiguration",
        "liquibase.configuration.LiquibaseConfiguration",
        "liquibase.configuration.core.DefaultsFileValueProvider",
        "liquibase.configuration.core.DeprecatedConfigurationValueProvider",
        "liquibase.configuration.core.EnvironmentValueProvider",
        "liquibase.configuration.core.ScopeValueProvider",
        "liquibase.configuration.core.SystemPropertyValueProvider",
        "liquibase.database.DatabaseFactory",
        "liquibase.database.LiquibaseTableNamesFactory",
        "liquibase.executor.ExecutorService",
        "liquibase.ext.mongodb.configuration.MongoConfiguration",
        "liquibase.GlobalConfiguration",
        "liquibase.integration.commandline.LiquibaseCommandLineConfiguration",
        "liquibase.lockservice.LockServiceFactory",
        "liquibase.logging.LogFactory",
        "liquibase.logging.core.LogServiceFactory",
        "liquibase.logging.core.DefaultLoggerConfiguration",
        "liquibase.logging.mdc.MdcManagerFactory",
        "liquibase.parser.ChangeLogParserConfiguration",
        "liquibase.parser.ChangeLogParserFactory",
        "liquibase.parser.SqlParserFactory",
        "liquibase.precondition.PreconditionFactory",
        "liquibase.resource.PathHandlerFactory",
        "liquibase.report.ShowSummaryGeneratorFactory",
        "liquibase.report.StandardShowSummaryGenerator",
        "liquibase.serializer.ChangeLogSerializerFactory",
        "liquibase.serializer.SnapshotSerializerFactory",
        "liquibase.snapshot.SnapshotGeneratorFactory",
        "liquibase.sql.SqlConfiguration",
        "liquibase.sqlgenerator.SqlGeneratorFactory",
        "liquibase.ui.UIServiceFactory"
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
