package pl.delukesoft.portfolioserver.adapters.mongo

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "mongo.migration")
data class MongoProperties (
    val connectionString: String
)