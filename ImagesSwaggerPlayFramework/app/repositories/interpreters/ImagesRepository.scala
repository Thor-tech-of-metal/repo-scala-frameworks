package repositories.interpreters

import java.util.UUID

import cats.Monad
import com.datastax.driver.core.PagingState
import com.outworkers.phantom.CassandraTable
import com.outworkers.phantom.dsl._
import models.entities.ImageEntity
import repositories.{HasCassandraDatabase, Page, Repository}

import scala.concurrent.Future
import scala.util.Try

class ImagesRepository() extends CassandraTable[ImagesRepository, ImageEntity] {

  override val tableName:String = "images"

  object id extends UUIDColumn(this) with PartitionKey

  object name extends StringColumn(this) {
    override def name: String = "name"
  }

  object path extends StringColumn(this) {
    override def name: String = "path"
  }

  object description extends StringColumn(this) {
    override def name: String = "description"
  }
}

abstract class ImagesRepositoryImpl extends ImagesRepository with RootConnector with Repository[Future] {

  implicit lazy val ks = HasCassandraDatabase.connector.provider.space
  implicit lazy val session = HasCassandraDatabase.connector.provider.session
  override implicit val monad: Monad[Future] = cats.instances.future.catsStdInstancesForFuture

  override def create(image: ImageEntity): Future[ImageEntity] =
    insert.value(_.id, image.id)
      .value(_.name, image.name)
      .value(_.path, image.path)
      .value(_.description, image.description)
      .consistencyLevel_=(ConsistencyLevel.LOCAL_QUORUM)
      .future()
      .map(_ => image)


  override def find(imageId: UUID): Future[Option[ImageEntity]] =
    select.where(_.id eqs imageId)
      .consistencyLevel_=(ConsistencyLevel.LOCAL_QUORUM)
      .one()

  override def updateById(image: ImageEntity): Future[ResultSet] =

    update.where(_.id eqs image.id)
      .modify(_.name setTo image.name)
      .and(_.path setTo image.path)
      .and(_.description setTo image.description)
      .future()


  override def deleteById(imageId: UUID): Future[UUID] =

    delete.where(_.id eqs imageId)
      .consistencyLevel_=(ConsistencyLevel.LOCAL_QUORUM)
      .future()
      .map(_ => imageId)

  override def findAll(pagingState: Option[String]): Future[Page[Seq[ImageEntity]]] = {

    val cassandraPagingState = for {
      pageString <- pagingState
      pageState <- Try(PagingState.fromString(pageString)).toOption
    } yield pageState

    for {
      result <- select.all.paginateRecord { statement =>
        cassandraPagingState.foreach(pagingState => statement.setPagingState(pagingState))
        statement.setFetchSize(4)
      }
    } yield Page(result.records, Option(result.pagingState).map(_.toString))
  }

}