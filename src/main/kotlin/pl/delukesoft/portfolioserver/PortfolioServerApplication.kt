package pl.delukesoft.portfolioserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PortfolioServerApplication

fun main(args: Array<String>) {
  runApplication<PortfolioServerApplication>(*args)
}
