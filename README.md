# hackfest-alexa-bookie project

## Run locally

```shell
mvn clean quarkus:dev
```

## Build the image on openshift with s2i

```shell script
mvn clean package -Dquarkus.container-image.build=true -Dquarkus.kubernetes-client.trust-certs=true
```

## Deploy on openshift
```shell script
mvn clean package -Dquarkus.kubernetes.deploy=true -Dquarkus.kubernetes-client.trust-certs=true
```

## Expose the app on openshift
```shell script
mvn clean package -Dquarkus.openshift.expose=true -Dquarkus.kubernetes-client.trust-certs=true
```