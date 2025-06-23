package pl.delukesoft.blog.image

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding
import org.springframework.stereotype.Service
import pl.delukesoft.blog.image.exception.ImageNotFound

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