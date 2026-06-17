package pl.delukesoft.portfolioserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.EnableAspectJAutoProxy

@SpringBootApplication(
  scanBasePackages = ["pl.delukesoft.portfolioserver", "pl.delukesoft.authplugin"]
)
@EnableAspectJAutoProxy
@ConfigurationPropertiesScan
@EnableCaching
class PortfolioServerApplication

fun main(args: Array<String>) {
  runApplication<PortfolioServerApplication>(*args)
}
