package pl.delukesoft.portfolioserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.EnableAspectJAutoProxy

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableFeignClients
class PortfolioServerApplication

fun main(args: Array<String>) {
  runApplication<PortfolioServerApplication>(*args)
}
