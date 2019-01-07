package models.entities

import java.util.UUID

import ai.x.play.json.Jsonx

case class ImageEntity(id: UUID, name: String, path: String, description: String)


object ImageEntity {
  implicit lazy val imageDescription = Jsonx.formatCaseClassUseDefaults[ImageEntity]
}

