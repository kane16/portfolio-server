package pl.delukesoft.portfolioserver.platform.configuration

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.media.Schema
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.tags.Tag
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("!prod")
class OpenApiConfiguration {

  companion object {
    const val SECURITY_SCHEME_NAME = "Bearer Authentication"
  }

  @Bean
  fun openAPI(): OpenAPI {
    return OpenAPI()
      .info(apiInfo())
      .tags(
        listOf(
          Tag().name("Author").description("Author registration and retrieval"),
          Tag().name("Portfolio").description("Public portfolio/CV endpoints"),
          Tag().name("Resume").description("Resume management and editing"),
          Tag().name("Resume - Education").description("Education entries within a resume"),
          Tag().name("Resume - Experience").description("Work experience entries within a resume"),
          Tag().name("Resume - Hobbies").description("Hobby entries within a resume"),
          Tag().name("Resume - Languages").description("Language entries within a resume"),
          Tag().name("Resume - Side Projects").description("Side project entries within a resume"),
          Tag().name("Resume - Skills").description("Skill entries and skill domains"),
          Tag().name("Constraints").description("Resume constraint definitions"),
          Tag().name("PDF").description("PDF / HTML resume generation"),
          Tag().name("Validation").description("Resume validation endpoints"),
          Tag().name("Validation - Education").description("Education field validation"),
          Tag().name("Validation - Experience").description("Experience field validation"),
          Tag().name("Validation - Side Projects").description("Side project field validation"),
        )
      )
      .addSecurityItem(SecurityRequirement().addList(SECURITY_SCHEME_NAME))
      .components(
        Components()
          .addSecuritySchemes(SECURITY_SCHEME_NAME, securityScheme())
          .addSchemas("ErrorResponse", errorResponseSchema())
      )
  }

  private fun apiInfo(): Info {
    return Info()
      .title("Portfolio Server API")
      .description("Portfolio and Resume management API - manages CVs, resumes, skills, experience, education and more")
      .version("1.0.0")
      .contact(
        Contact()
          .name("DelukeSoft")
          .url("https://delukesoft.pl")
      )
      .license(
        License()
          .name("Proprietary")
          .url("https://delukesoft.pl/license")
      )
  }

  private fun securityScheme(): SecurityScheme {
    return SecurityScheme()
      .name(SECURITY_SCHEME_NAME)
      .type(SecurityScheme.Type.HTTP)
      .scheme("bearer")
      .bearerFormat("JWT")
      .description("Enter JWT access token")
  }

  private fun errorResponseSchema(): Schema<Any> {
    val schema = Schema<Any>()
    schema.type = "object"
    schema.description = "Standard error response"
    schema.addProperty(
      "status", Schema<Int>()
        .type("integer")
        .description("HTTP status code")
        .example(400)
    )
    schema.addProperty(
      "message", Schema<String>()
        .type("string")
        .description("Error message describing what went wrong")
        .example("Request invalid")
    )
    return schema
  }
}
