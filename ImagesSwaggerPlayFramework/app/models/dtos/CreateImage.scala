package models.dtos

import java.util.UUID

import ai.x.play.json.Jsonx
import com.outworkers.phantom.dsl.UUID
import models.entities.ImageEntity
import play.api.libs.json.{Format, Json, Reads, Writes}

object Gender extends Enumeration {
  type Gender = Value
  val Male, Female = Value

  implicit val enumReads: Format[Gender] = EnumerationJsonFormat.enumFormat(Gender)


}

case class CreateImage(id: UUID, name: String, path: String, description: String)




object CreateImage {

  implicit val createImageReads: Reads[CreateImage] = Reads[CreateImage] { json =>

    for {
      name <- (json \ "name").validate[String]
      path <- (json \ "path").validate[String]
      description <- (json \ "description").validate[String]
      id <- (json \ "id").validate[String]
    } yield CreateImage(UUID.fromString(id), name, path, description)
  }

  implicit lazy val imageDescription = Jsonx.formatCaseClassUseDefaults[CreateImage]
}

case class ImageResult(results: Seq[ImageEntity], nextPage: Option[String])

object ImageResult {

  implicit val imageResultWrites: Writes[ImageResult] = Json.writes[ImageResult]
}
