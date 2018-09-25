## Deploy Kubernetes on Azure

```console
RESOURCE_GROUP_NAME=''
LOCATION=''
NAME=''
```

### create the cluster

```console
az aks create --node-vm-size Standard_D2_v2 --resource-group $RESOURCE_GROUP_NAME --name $NAME --node-count 3 --kubernetes-version 1.11.1 --location $LOCATION --generate-ssh-keys
```

| Parameter | Description |
| --- | --- | 
| AGENT_SIZE | The size of K8s's agent VM. Choose `Standard_NC6` for GPUs or `Standard_D2_v2` if you just want CPUs. Full list of [options here](https://github.com/Azure/azure-sdk-for-python/blob/master/azure-mgmt-containerservice/azure/mgmt/containerservice/models/container_service_client_enums.py#L21). |

# Getting the configuration file

```console
az aks get-credentials --name $NAME --resource-group $RESOURCE_GROUP_NAME

kubectl get nodes
kubectl describe node <NODE_NAME>
```

#Save this template somewhere and deploy it with:

```console
mkdir deployments

vi deployments/serverplan.yaml
```

Type `i` to enter insert model and copy the following:

A sample config file:

```yaml
apiVersion: apps/v1beta2 # Kubernetes API version for the object
kind: Deployment # The type of object described by this YAML, here a Deployment
metadata:
  name: nginx-deployment # Name of the deployment
spec: # Actual specifications of this deployment
  replicas: 3 # Number of replicas (instances) for this deployment. 1 replica = 1 pod
  template: 
    metadata:
      labels:
        app: nginx
    spec: # Specification for the Pod 
      containers: # These are the containers running inside our Pod, in our case a single one
      - name: nginx # Name of this container
        image: nginx:1.7.9 # Image to run
        ports: # Ports to expose
        - containerPort: 80
```

Press `Esc` to enter command mode

`:wq` and Enter

```console
kubectl create -f <path-to-your-template>
kubectl get job
```



## Trouble shooting

1. Azure CLI returning empty list for Resource Groups

By default, Azure CLI use a secondary subscription tied to your account. So it is necessary to change to the right subscription.

```console
az account set -s subscriptionID
```




