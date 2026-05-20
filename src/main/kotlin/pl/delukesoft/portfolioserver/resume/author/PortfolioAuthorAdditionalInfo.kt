package pl.delukesoft.portfolioserver.resume.author

import pl.delukesoft.authplugin.author.AdditionalInfo
import pl.delukesoft.portfolioserver.resume.experience.business.Business
import pl.delukesoft.portfolioserver.resume.skill.Skill
import pl.delukesoft.portfolioserver.resume.skill.domain.SkillDomain

data class PortfolioAuthorAdditionalInfo(
    val roles: List<String> = emptyList(),
    val skills: List<Skill> = emptyList(),
    val domains: List<SkillDomain> = emptyList(),
    val businesses: List<Business> = emptyList()
): AdditionalInfo
