package repositories

import com.outworkers.phantom.connectors.{CassandraConnection, ContactPoints}
import com.outworkers.phantom.dsl.Database
import repositories.interpreters.{ImagesRepository, ImagesRepositoryImpl}
import com.typesafe.config.{Config, ConfigFactory}

class DefaultDatabase(override val connector: CassandraConnection) extends Database[DefaultDatabase](connector) {

  object imagesRepositoryImpl extends ImagesRepositoryImpl with connector.Connector{

    implicit override lazy val ks = HasCassandraDatabase.connector.provider.space
    implicit override lazy val session = HasCassandraDatabase.connector.provider.session
  }
}

object DefaultDatabase extends DefaultDatabase(HasCassandraDatabase.connector)

trait HasCassandraDatabase extends HasCassandraDatabase.connector.Connector {
  lazy val cassandra = DefaultDatabase
}

object HasCassandraDatabase {

  val ConfigLoader: Config = ConfigFactory.load

  implicit val connector = ContactPoints(Seq(ConfigLoader.getString("db.session.contactPoints"))).keySpace(ConfigLoader.getString("db.keyspace"))
}
