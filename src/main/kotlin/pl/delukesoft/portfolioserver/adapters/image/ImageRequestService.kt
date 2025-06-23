package pl.delukesoft.blog.image

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "image", url = "\${auth-service.url}/images")
interface ImageRequestService {

  @GetMapping("{id}")
  fun getImageById(@PathVariable("id") imageId: Long): Image

}