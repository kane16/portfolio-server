package pl.delukesoft.portfolioserver.steps

data class UserTokenDTO(
    var jwtDesc: String? = null,
    var username: String? = null,
    var email: String? = null,
    var roles: List<String>? = null
)