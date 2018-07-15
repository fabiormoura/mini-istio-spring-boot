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

# Configure Istio
```
kubectl apply -f kube/istio-namespace.yaml
kubectl apply -f kube/istio.yaml

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
--set grafana.enabled=false \
--set grafana.enabled=false \
--set prometheus.enabled=false \
--set servicegraph.enabled=false \
--set tracing.enabled=false \
> kube/istio.yaml
```
