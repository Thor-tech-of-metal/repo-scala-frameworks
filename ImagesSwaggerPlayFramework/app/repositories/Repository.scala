package repositories

import java.util.UUID

import scala.language.higherKinds
import cats.Monad
import com.outworkers.phantom.dsl.ResultSet
import models.entities.ImageEntity

case class Page[A](result: A, next: Option[String])

trait Repository[Effect[_]] {

  implicit val monad: Monad[Effect]

  def create(image: ImageEntity): Effect[ImageEntity]

  def find(imageId: UUID): Effect[Option[ImageEntity]]

  def updateById(image: ImageEntity): Effect[ResultSet]

  def deleteById(imageId: UUID): Effect[UUID]

  def findAll(pagingState: Option[String]): Effect[Page[Seq[ImageEntity]]]
}
