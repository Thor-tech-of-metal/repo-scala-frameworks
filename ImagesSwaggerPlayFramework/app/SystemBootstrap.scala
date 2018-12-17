import javax.inject._

import repositories.{DefaultDatabase, HasCassandraDatabase}

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

trait Bootstrap {
  def applicationStart(): Unit
  applicationStart()
}

@Singleton
class SystemBootstrap @Inject() () extends Bootstrap {

  def applicationStart() = {

    implicit val ks = HasCassandraDatabase.connector.provider.space
    implicit val session = HasCassandraDatabase.connector.provider.session

    Await.result(DefaultDatabase.createAsync(), 5.seconds)

  }

}
