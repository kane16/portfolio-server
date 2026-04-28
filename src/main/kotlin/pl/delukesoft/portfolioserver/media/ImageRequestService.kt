package pl.delukesoft.portfolioserver.media

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import pl.delukesoft.portfolioserver.media.Image

@FeignClient(name = "image", url = "\${auth-service.url}/images")
interface ImageRequestService {

  @GetMapping("{id}")
  fun getImageById(@PathVariable("id") imageId: Long): Image

}