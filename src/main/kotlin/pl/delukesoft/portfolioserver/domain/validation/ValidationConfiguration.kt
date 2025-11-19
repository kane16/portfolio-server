package pl.delukesoft.portfolioserver.domain.validation

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.delukesoft.portfolioserver.domain.constraint.ConstraintService
import pl.delukesoft.portfolioserver.domain.resume.experience.Experience
import pl.delukesoft.portfolioserver.domain.resume.experience.ExperienceValidator
import pl.delukesoft.portfolioserver.domain.resume.experience.business.Business
import pl.delukesoft.portfolioserver.domain.resume.experience.business.BusinessValidator
import pl.delukesoft.portfolioserver.domain.resume.experience.skillexperience.SkillExperience
import pl.delukesoft.portfolioserver.domain.resume.experience.skillexperience.SkillExperienceValidator
import pl.delukesoft.portfolioserver.domain.resume.skill.Skill
import pl.delukesoft.portfolioserver.domain.resume.skill.SkillValidator
import pl.delukesoft.portfolioserver.domain.resume.skill.domain.SkillDomain
import pl.delukesoft.portfolioserver.domain.resume.skill.domain.SkillDomainValidator
import pl.delukesoft.portfolioserver.domain.resume.timespan.TimeframeValidator

@Configuration
class ValidationConfiguration {

  @Bean("consecutiveTimeframeValidator")
  fun consecutiveTimeframeValidator(): TimeframeValidator {
    return TimeframeValidator(false)
  }

  @Bean("lenientTimeframeValidator")
  fun lenientTimeframeValidator(): TimeframeValidator {
    return TimeframeValidator(true)
  }

  @Bean("jobExperienceValidator")
  fun jobExperienceValidator(
    @Qualifier("consecutiveTimeframeValidator") timeframeValidator: TimeframeValidator,
    @Qualifier("businessValidator") businessValidator: Validator<Business>,
    @Qualifier("skillExperienceValidator") skillExperienceValidator: Validator<SkillExperience>,
    constraintService: ConstraintService
  ): Validator<Experience> {
    return ObligatoryElementValidatorDecorator(
      ExperienceValidator(
        timeframeValidator,
        businessValidator,
        skillExperienceValidator,
        constraintService
      )
    )
  }

  @Bean("businessValidator")
  fun businessValidator(constraintService: ConstraintService): Validator<Business> {
    return ObligatoryElementValidatorDecorator(
      BusinessValidator(constraintService)
    )
  }

  @Bean("skillExperienceValidator")
  fun skillExperienceValidator(
    @Qualifier("skillValidator") skillValidator: Validator<Skill>,
    constraintService: ConstraintService
  ): Validator<SkillExperience> {
    return ObligatoryElementValidatorDecorator(
      SkillExperienceValidator(
        skillValidator,
        constraintService
      )
    )
  }

  @Bean("skillValidator")
  fun skillValidator(
    @Qualifier("skillDomainValidator") skillDomainValidator: Validator<SkillDomain>,
    constraintService: ConstraintService
  ): Validator<Skill> {
    return ObligatoryElementValidatorDecorator(
      SkillValidator(
        skillDomainValidator,
        constraintService
      )
    )
  }

  @Bean("skillDomainValidator")
  fun skillDomainValidator(
    constraintService: ConstraintService
  ): Validator<SkillDomain> {
    return ObligatoryElementValidatorDecorator(
      SkillDomainValidator(constraintService)
    )
  }

  @Bean("sideProjectsValidator")
  fun sideProjectsValidator(
    @Qualifier("lenientTimeframeValidator") timeframeValidator: TimeframeValidator,
    @Qualifier("businessValidator") businessValidator: Validator<Business>,
    @Qualifier("skillExperienceValidator") skillExperienceValidator: Validator<SkillExperience>,
    constraintService: ConstraintService
  ): Validator<Experience> {
    return ExperienceValidator(
      timeframeValidator,
      businessValidator,
      skillExperienceValidator,
      constraintService
    )
  }

}