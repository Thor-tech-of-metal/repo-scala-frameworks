FROM ysihaoy/scala-play:2.12.2-2.6.0-sbt-0.13.15

# caching dependencies
COPY ["build.sbt", "/tmp/build/"]
COPY ["project/plugins.sbt","project/Dependencies.scala","project/Common.scala","project/build.properties", "/tmp/build/project/"]
RUN cd /tmp/build && \
 sbt compile && \
 sbt test:compile && \
 rm -rf /tmp/build

# copy code
COPY . /root/app/
WORKDIR /root/app
RUN sbt compile && sbt test:compile

COPY ["docker/run.sh", "/run.sh"]
RUN ["chmod", "+x", "/run.sh"]

ENTRYPOINT ["/run.sh"]

EXPOSE 9000
