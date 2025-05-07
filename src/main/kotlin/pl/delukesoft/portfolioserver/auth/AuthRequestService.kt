package pl.delukesoft.portfolioserver.auth

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "auth", url = "\${auth-service.url}/users")
interface AuthRequestService {

  @GetMapping("contextUser")
  fun getUser(@RequestHeader("Authorization") token: String): User

  @GetMapping("{id}")
  fun getUserById(@RequestHeader("Authorization") token: String, @PathVariable("id") userId: Long): User

  @GetMapping("authorities")
  fun getUserAuthorities(@RequestHeader("Authorization") token: String): List<String>

}