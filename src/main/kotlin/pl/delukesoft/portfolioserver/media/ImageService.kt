package pl.delukesoft.portfolioserver.media

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding
import org.springframework.stereotype.Service
import pl.delukesoft.portfolioserver.media.exception.ImageNotFound

@Service
@RegisterReflectionForBinding(Image::class)
class ImageService(val imageRequestService: ImageRequestService) {

  fun getImageById(imageId: Long): Image {
    try {
      return imageRequestService.getImageById(imageId)
    } catch (exc: Exception) {
      throw ImageNotFound()
    }
  }

}
