## An Example of a Microservice using: 

* Play Framework 
* Swagger 
* Cassandra (Phantom) 
* Docker  
* Kubernetes 



#### Build and Run the application 

```aidl

    1) sbt clean compile 
    
    2) sbt run
```

#### Run the application in Degug mode

```aidl

    1) sbt -jvm-debug 9999  run
    
    2) Set up a remote degug in intelij and use port 9999
    
```
Read the following link to setup a remote debug.

https://stackoverflow.com/questions/21114066/attach-intellij-idea-debugger-to-a-running-java-process

#### Swagger

Use the following url to see the swagger docs. http://localhost:9000/docs/#/


remember that all api rest full endpoints are described in: 

* ImagesSwaggerPlayFramework/conf/routes


Docker
======

There is a project/Docker file which contains all docker statements to 
* add project files in the docker volume 
* compile the project and build target
* chmod files and directories 
* add run.sh 
* define the entry point scripts for docker run 
   
Run the following from your project root directory.  

1) The command will create a docker image and tag it using name and version
2) It will run the docker image using the tag name 
3) It will find the dcoker ip machine.
4) It will print all active containers, from there you can get your IMAGE_ID and CONTAINER_ID
5) It will execute the bash command line of the  docker image. 

show docker containers
docker ps -a

show images 
docker images 


> **_images:v1_** 

```aidl

    1) docker build -t imagesapplication:v1 .
    
    2) docker run -it imagesapplication:v1 -p 9000:9000 
    
    3) docker inspect --format '{{ .NetworkSettings.IPAddress }}' $(docker ps -q)
    
    4) docker ps -a
     
    5) docker exec -it  [CONTAINDER_ID]   bash

```

https://github.com/tianhao-au/docker-scala-play/blob/master/project/build.properties

https://github.com/dpfeiffer/play-docker-showcase/blob/master/build.sbt

#### Cassandra 

This application is going to create the cassandra keyspace and their tables using cassandra Phantom
framwork. for more information about cassandra and how to install and use it please refere to : 

https://github.com/Thor-tech-of-metal/repo-it-notes/tree/master/my-notes/cassandra





