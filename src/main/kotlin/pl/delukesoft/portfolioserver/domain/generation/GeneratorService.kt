package pl.delukesoft.portfolioserver.domain.generation

import org.springframework.stereotype.Service
import pl.delukesoft.portfolioserver.domain.sequence.Sequence

@Service
class GeneratorService(val generatorRepository: GeneratorRepository) {

  @Synchronized
  fun getAndIncrement(collectionName: String): Long {
    val sequence: Sequence =
      generatorRepository.findByCollectionName(collectionName)
        ?: Sequence(generatorRepository.count() + 1, collectionName)
    val generationNumber = sequence.sequenceNumber
    generatorRepository.save(sequence.copy(sequenceNumber = generationNumber + 1))
    return generationNumber
  }

}