These are the steps to run a single cassandra instance
======================================================

1) Pull the cassandra image
```
docker pull cassandra
```

2) Get the image name and version
```
docker images | grep cassandra
```

**_The image name:version  is cassandra:latest_**

3) Run the image in a container and name the container as "cassandra" use --name to give a name for the container.

```
docker run --name cassandra -d cassandra:latest
```

3) connect to the cassandra cqlsh

```
docker run -it --link cassandra --rm cassandra cqlsh cassandra
```

4) Connect to the cassandra machine bash
```
docker exec -it cassandra bash
```

Configuring Cassandra
======================

* Provide configuration to the cassandra image a custom /etc/cassandra/cassandra.yaml file.

* Provide this file to the container (via short Dockerfile with FROM + COPY, via Docker Configs, via runtime bind-mount, etc).

* Provide a different file name (for example, to avoid all image-provided configuration behavior) use :
```
docker run  --name cassandra -Dcassandra.config=/path/to/cassandra.yaml
```

