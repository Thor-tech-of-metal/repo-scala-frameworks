package repositories.interpreters

import javax.inject.{Inject, Provider}

import com.outworkers.phantom.connectors.{CassandraConnection, ContactPoints}
import play.Configuration
import repositories.{DefaultDatabase, HasCassandraDatabase}

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Await

class CassandraConnectionProvider @Inject()(config: Configuration) extends Provider[CassandraConnection] {

  override def get(): CassandraConnection = {

    Await.result(DefaultDatabase.createAsync(), 5.seconds)

    HasCassandraDatabase.connector
  }
}
