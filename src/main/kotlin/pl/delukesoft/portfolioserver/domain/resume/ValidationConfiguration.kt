package pl.delukesoft.portfolioserver.domain.resume

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.delukesoft.portfolioserver.domain.resume.experience.ExperienceValidator
import pl.delukesoft.portfolioserver.domain.resume.experience.business.BusinessValidator
import pl.delukesoft.portfolioserver.domain.resume.experience.skillexperience.SkillExperienceValidator
import pl.delukesoft.portfolioserver.domain.resume.timespan.ConsecutiveTimeframeValidator

@Configuration
class ValidationConfiguration {

  @Bean("consecutiveTimeframeValidator")
  fun consecutiveTimeframeValidator(): ConsecutiveTimeframeValidator {
    return ConsecutiveTimeframeValidator(false)
  }

  @Bean("lenientTimeframeValidator")
  fun lenientTimeframeValidator(): ConsecutiveTimeframeValidator {
    return ConsecutiveTimeframeValidator(true)
  }

  @Bean("jobExperienceValidator")
  fun jobExperienceValidator(
    @Qualifier("consecutiveTimeframeValidator") timeframeValidator: ConsecutiveTimeframeValidator,
    businessValidator: BusinessValidator,
    skillExperienceValidator: SkillExperienceValidator
  ): ExperienceValidator {
    return ExperienceValidator(
      timeframeValidator,
      businessValidator,
      skillExperienceValidator
    )
  }

  @Bean("sideProjectsValidator")
  fun sideProjectsValidator(
    @Qualifier("lenientTimeframeValidator") timeframeValidator: ConsecutiveTimeframeValidator,
    businessValidator: BusinessValidator,
    skillExperienceValidator: SkillExperienceValidator
  ): ExperienceValidator {
    return ExperienceValidator(
      timeframeValidator,
      businessValidator,
      skillExperienceValidator
    )
  }

}