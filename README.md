# Spring Boot/Istio Playground

The project shows some of the Istio features and it uses some spring boot services for its demonstration.  

## Getting Started
  
### Prerequisites

* Minikube
* Helm
* Kubectl
* Docker
* Maven
* OpenJDK

### Installing Minikube
```
brew cask install minikube

# https://github.com/kubernetes/minikube/blob/master/docs/drivers.md#hyperkit-driver
curl -LO https://storage.googleapis.com/minikube/releases/latest/docker-machine-driver-hyperkit \
&& chmod +x docker-machine-driver-hyperkit \
&& sudo mv docker-machine-driver-hyperkit /usr/local/bin/ \
&& sudo chown root:wheel /usr/local/bin/docker-machine-driver-hyperkit \
&& sudo chmod u+s /usr/local/bin/docker-machine-driver-hyperkit
```

### Installing Kubectl
```
brew install kubernetes-cli
```

### Installing Helm
```
brew install kubernetes-helm
```

### Running Minikube
```
minikube start --vm-driver=hyperkit --cpus=3 --memory=4096

# smoking test
kubectl get nodes
```

### Adding namespaces

The namespaces are for deploying all istio components and sample spring boot services used during the demo. 
```
kubectl apply -f kube/namespaces/istio-system.yaml
kubectl apply -f kube/namespaces/sample.yaml
```

## Istio

The following steps describes how to deploy all Istio components to istio-system namespaces. There is an optional step for upgrading Istio from **0.8** to a newer version.

### Configuring Istio
```
kubectl apply -f kube/istio/istio.yaml
```

### Upgrading Istio (Optional)

This step is optional but once a new version of Istio gets released it might good to keep our current version (**0.8.0**) up-to-date.

```
export ISTIO_VERSION=0.8.0
curl -L -o tmp/istio-${ISTIO_VERSION}-linux.tar.gz https://github.com/istio/istio/releases/download/${ISTIO_VERSION}/istio-${ISTIO_VERSION}-linux.tar.gz
tar -xzvf tmp/istio-${ISTIO_VERSION}-linux.tar.gz -C tmp
helm template tmp/istio-${ISTIO_VERSION}/install/kubernetes/helm/istio \
--name istio \
--namespace istio-system \
--set global.mtls.enabled=true \
--set global.controlPlaneSecurityEnabled=true \
--set grafana.enabled=false \
--set prometheus.enabled=false \
--set servicegraph.enabled=false \
--set tracing.enabled=false \
> kube/istio/istio.yaml

kubectl apply -f kube/istio/istio.yaml
```

## Sample Services 

### Building Sample Services
```
eval $(minikube docker-env)
mvn clean install -f sample-services

docker build -t sample-web-service-a:0.0.6 -f sample-services/sample-web-service-a/docker/Dockerfile sample-services/sample-web-service-a
docker build -t sample-web-service-b:0.0.2 -f sample-services/sample-web-service-b/docker/Dockerfile sample-services/sample-web-service-b
```

### Deploying Sample Services
```
kubectl apply -f kube/sample-web-service-a/deployment.yaml -n sample
kubectl apply -f kube/sample-web-service-a/service.yaml -n sample

kubectl apply -f kube/sample-web-service-b/deployment.yaml -n sample
kubectl apply -f kube/sample-web-service-b/service.yaml -n sample
```

## Testing Istio Features

### Service Entries

TODO

### Mutual TLS

TODO

### Setting up all Istio rules 
```
kubectl apply -f kube/sample-istio-rules/policies.yaml -n sample
kubectl apply -f kube/sample-istio-rules/service-entries.yaml -n sample
```


## Technical Resources

* https://github.com/srinandan/istio-workshop
* https://istio.io/docs/setup/kubernetes/helm-install/