import javax.inject.Singleton
import com.google.inject.{AbstractModule, TypeLiteral}
import com.outworkers.phantom.connectors.CassandraConnection
import net.codingwell.scalaguice.ScalaModule
import repositories.{DefaultDatabase, HasCassandraDatabase, Repository}
import repositories.interpreters.{CassandraConnectionProvider, ImagesRepository, ImagesRepositoryImpl}

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Await

import scala.concurrent.{Await, Future}

class Module extends AbstractModule with ScalaModule {


  override def configure(): Unit = {

    Await.result(DefaultDatabase.createAsync(), 5.seconds)

  }
}
