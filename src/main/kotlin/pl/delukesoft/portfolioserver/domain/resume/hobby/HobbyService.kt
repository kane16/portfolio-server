package pl.delukesoft.portfolioserver.domain.resume.hobby

import org.springframework.stereotype.Service
import pl.delukesoft.portfolioserver.domain.sequence.GeneratorService

@Service
class HobbyService(
  private val hobbyRepository: HobbyRepository,
  private val generatorService: GeneratorService
) {

  fun saveAll(hobbies: List<Hobby>): List<Hobby> {
    return hobbies.map { save(it) }
  }

  fun save(hobby: Hobby): Hobby {
    if (hobby.id == null) {
      val generatedId = generatorService.getAndIncrement("hobby")
      return hobbyRepository.save(hobby.copy(id = generatedId))
    }
    TODO("Not yet implemented")
  }

}