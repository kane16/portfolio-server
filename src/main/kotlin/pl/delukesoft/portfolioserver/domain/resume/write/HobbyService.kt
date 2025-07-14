package pl.delukesoft.portfolioserver.domain.resume.write

import org.springframework.stereotype.Service
import pl.delukesoft.portfolioserver.domain.resume.model.Hobby
import pl.delukesoft.portfolioserver.domain.resume.print.GeneratorService

@Service
class HobbyService(
  private val hobbyWriteRepository: HobbyWriteRepository,
  private val generatorService: GeneratorService
) {

  fun saveAll(hobbies: List<Hobby>): List<Hobby> {
    return hobbies.map { save(it) }
  }

  fun save(hobby: Hobby): Hobby {
    if (hobby.id == null) {
      val generatedId = generatorService.getAndIncrement("hobby")
      return hobbyWriteRepository.save(hobby.copy(id = generatedId))
    }
    TODO("Not yet implemented")
  }

}