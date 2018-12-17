package models.dtos

import ai.x.play.json.Jsonx

case class Image( name:String, path:String, description: String)

object Image {  implicit lazy val imageDescription = Jsonx.formatCaseClassUseDefaults[Image] }