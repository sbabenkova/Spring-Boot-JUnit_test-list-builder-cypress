ARG DOCKER_PREFIX
FROM ${DOCKER_PREFIX}/openjdk:11-jre-slim
VOLUME /tmp
ENV TESTLISTBUILDERCYPRESS_PORT=20018
RUN apt update && apt install -y git
COPY target/testlistbuildercypress.jar app.jar
COPY entrypoint.sh entrypoint.sh
RUN chmod +x ./entrypoint.sh
ENTRYPOINT ["./entrypoint.sh"]