The project provides instructions about making Istio and Spring Boot play well together on minikube.

# Install required tools
```
brew cask install minikube
brew install kubernetes-helm


# https://github.com/kubernetes/minikube/blob/master/docs/drivers.md#hyperkit-driver
curl -LO https://storage.googleapis.com/minikube/releases/latest/docker-machine-driver-hyperkit \
&& chmod +x docker-machine-driver-hyperkit \
&& sudo mv docker-machine-driver-hyperkit /usr/local/bin/ \
&& sudo chown root:wheel /usr/local/bin/docker-machine-driver-hyperkit \
&& sudo chmod u+s /usr/local/bin/docker-machine-driver-hyperkit

```

# Run minikube cluster
```
minikube start --vm-driver=hyperkit --cpus=3 --memory=4096
kubectl get nodes
```

# Create all namespaces
```
kubectl apply -f kube/namespaces/istio-namespace.yaml
kubectl apply -f kube/namespaces/sample.yaml
```

# Configure Istio
```
kubectl apply -f kube/istio/istio.yaml
```

# Build Sample Services
```
eval $(minikube docker-env)
mvn clean install -f sample-services

docker build -t sample-web-service-a:0.0.6 -f sample-services/sample-web-service-a/docker/Dockerfile sample-services/sample-web-service-a
docker build -t sample-web-service-b:0.0.2 -f sample-services/sample-web-service-b/docker/Dockerfile sample-services/sample-web-service-b
```

# Deploy Sample Services
```
kubectl apply -f kube/sample-web-service-a/deployment.yaml -n sample
kubectl apply -f kube/sample-web-service-a/service.yaml -n sample

kubectl apply -f kube/sample-web-service-b/deployment.yaml -n sample
kubectl apply -f kube/sample-web-service-b/service.yaml -n sample
```

# Apply all istio rules
```
kubectl apply -f kube/sample-istio-rules/policies.yaml -n sample
kubectl apply -f kube/sample-istio-rules/service-entries.yaml -n sample
```


# Upgrade Istio
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
```

# Technical Resources
* https://github.com/srinandan/istio-workshop