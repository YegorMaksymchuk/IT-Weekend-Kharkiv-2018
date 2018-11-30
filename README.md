# IT-Weekend-Kharkiv-2018
Demo for IT Weekend Kharkiv 2018

### Kubernetes
* minikube start
* minikube dashboard
* kubectl run --image=yemax/pod-demo:1 pod-demo --port=8081
* kubectl expose deployment pod-demo --port=8081 --name=pod-demo --type=LoadBalancer
* minikube service pod-demo
### OpenShift OKD
* oc cluster up
* oc login -u developer -p developer
* oc new-project it-weekend-2018 
* oc new-app yemax/pod-demo:1 --name=pod-demo
* oc expose svc/pod-demo

### Links:
* [Presentation](https://docs.google.com/presentation/d/1noGi-9WK75uz9XdjUJlojvblXpLn1Sddd4bHkDQPxBs/edit?usp=sharing)
* [Video](https://youtu.be/ywydlHm-CYQ)