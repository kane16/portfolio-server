package pl.delukesoft.portfolioserver.utility

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.TaskExecutor
import org.springframework.core.task.VirtualThreadTaskExecutor

@Configuration
class AsyncConfig {

  @Bean
  fun applicationTaskExecutor(): TaskExecutor {
    return VirtualThreadTaskExecutor()
}

}