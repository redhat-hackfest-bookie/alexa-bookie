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

## Configuring Knative

Follow the steps in [Openshift 4.3 Docs](https://docs.openshift.com/container-platform/4.3/serverless/installing_serverless/installing-openshift-serverless.html).

When you have the namespace `knative-serving` properly configured you can start configure the quarkus app.

The quarkus app should have the following properties in the `application.properties`:

```properties
quarkus.container-image.registry= image-registry.openshift-image-registry.svc:5000 <1>
quarkus.container-image.group= [app namespace/project] <2>
quarkus.kubernetes.deployment-target= knative <3>
```
 1. Basically, tells `knative` from where it should get the app image;
 2. This property is used to let quarkus know what is the used to store the image
 3. This property enables the generation of the `knative` files 
 
This version of quarkus also has some small, but necessary, fixtures that need to be done in the generated yaml file.
 
Run the following commands to generate the `knative.yml` files:

```shell
./mvnw clean package
```

Apply the patch to the target file `target/kubernetes/knative.yml`:

```shell
patch target/kubernetes/knative.yml knative/knative.patch
```

