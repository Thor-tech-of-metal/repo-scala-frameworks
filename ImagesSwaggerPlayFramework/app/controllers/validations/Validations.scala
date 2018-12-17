package controllers.validations

import java.util.UUID

import scala.util.{Success, Try}


object Validations {

  def validateUUID(uuid:String): Option[UUID] = {

    Try(UUID.fromString(uuid)) match {
      case Success(id) => Some(id)
      case _ => None
    }
  }

}
