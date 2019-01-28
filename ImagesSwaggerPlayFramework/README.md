## An Example of a Microservice using: 

* Play Framework 
* Swagger 
* Cassandra (Phantom) 
* Docker  
* Kubernetes 


#### 1) Build a cassandra local image.

Please refer to the section [docker-cassandra](docker-cassandra/run_cassandra_in_docker.md "docker-cassandra")

In this section we created a cassandra Db inside docker and we named it: **cassandra**
Then we will link the docker application image with this created docker images called: **cassandra** 

#### Cassandra 

This application is going to create the cassandra keyspace and their tables using cassandra Phantom
framwork. 

* Please refer [docker-cassandra](docker-cassandra/run_cassandra_in_docker.md "docker-cassandra") to the section to bash cassandra docker image 

* Please refer [docker-cassandra](docker-cassandra/run_cassandra_in_docker.md "docker-cassandra") to the section to see cassandra logs

* Change your application to use the linked cassandra container ip address.

    Get your docker cassandra container external ip 

    ```docker inspect --format '{{ .NetworkSettings.IPAddress }}' cassandra``` 

* Update conf/application.conf to look like the following    
  
```aidl  
    db {
      keyspace = "images"
      preparedStatementCacheSize = 100
      session {
        contactPoints = "THE CONTAINER IP ADDRESS"
        queryOptions {
          consistencyLevel = "LOCAL_QUORUM"
        }
      }
    }
```
#### Build and Run the application 

```aidl

    1) sbt clean compile 
    2) your docker cassandra container is running if not use refer to Cassandra section.
    3) sbt run
```

#### Run the application in Degug mode

```aidl

    1) sbt -jvm-debug 9999  run
    
    2) Set up a remote degug in intelij and use port 9999
    
```
Read the following link to setup a remote debug.

https://stackoverflow.com/questions/21114066/attach-intellij-idea-debugger-to-a-running-java-process




Docker
======



####  Build the application in docker and use cassandra docker container

* Update conf/application.conf to use the "contactPoints" pointing to the cassandra image  
  
```aidl  
    db {
      keyspace = "images"
      preparedStatementCacheSize = 100
      session {
        contactPoints = "cassandra"
        queryOptions {
          consistencyLevel = "LOCAL_QUORUM"
        }
      }
    }
```

There is a project/Docker file which contains all docker statements to 
* add project files in the docker volume 
* compile the project and build target
* chmod files and directories 
* add run.sh 
* define the entry point scripts for docker run 
   
Run the following from your project root directory.  

1) The command will create a docker image and tag it using name and version
2) It will run the docker image using the tag name and link it with to the  **cassandra** docker container created previously
3) It will find the docker ip machine.
4) It will print all active containers, from there you can get your IMAGE_ID and CONTAINER_ID
5) It will execute the bash command line of the  docker image. 



> **_images:v1_** 

```aidl

    1) docker build -t imagesapplication:v1 .
    
    2) docker run --link cassandra  -it imagesapplication:v1 -p 9000:9000  
    
    3) docker inspect --format '{{ .NetworkSettings.IPAddress }}' imagesapplication:v1
    
    4) docker ps -a
     
    5) docker exec -it  [CONTAINDER_ID]   bash

```


Swagger
======


Remember that all api rest full endpoints are described in: 

* ImagesSwaggerPlayFramework/conf/routes


#### 1) Swagger from local machine without docker 
Use the following url to see the swagger docs if you are running local without docker

http://localhost:9000/docs/#/

#### 2) Swagger from local machine inside a docker container 

* Use the following url to see the swagger docs if you are running  without docker

http://[dockerIP]:9000/docs/#/


* Comple the Swagger base with http://[dockerIP]:9000/swagger.json








