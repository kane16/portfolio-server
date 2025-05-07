package pl.delukesoft.portfolioserver

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
  fromApplication<PortfolioServerApplication>().with(TestcontainersConfiguration::class).run(*args)
}
