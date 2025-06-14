package pl.delukesoft.portfolioserver.pdf

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("Sequence")
data class Sequence(
  @Id val id: Long,
  val collectionName: String,
  val sequenceNumber: Long = 1L
)
