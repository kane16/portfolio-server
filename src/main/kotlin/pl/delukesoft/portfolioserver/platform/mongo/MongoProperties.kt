package pl.delukesoft.portfolioserver.platform.mongo

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "mongo.migration")
data class MongoProperties (
    val connectionString: String
)