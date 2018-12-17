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

#### Cassandra 

This application is going to create the cassandra keyspace and their tables using cassandra Phantom
framwork. for more information about cassandra and how to install and use it please refere to : 

https://github.com/Thor-tech-of-metal/repo-it-notes/tree/master/my-notes/cassandra





