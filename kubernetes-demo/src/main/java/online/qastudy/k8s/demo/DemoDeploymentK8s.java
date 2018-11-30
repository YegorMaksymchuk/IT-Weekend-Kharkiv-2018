package online.qastudy.k8s.demo;

import io.fabric8.kubernetes.api.model.*;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.openshift.api.model.DeploymentConditionBuilder;
import lombok.extern.slf4j.Slf4j;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class DemoDeploymentK8s {

    public static final String HTTP = "http://";
    public static final String APP_URL = "pod-demo-xpdays-2018.127.0.0.1.nip.io";
    private static final String MASTER_URL = "http://192.168.99.100:30000";
    private static final String USERNAME = "developer";
    private static final String PASSWORD = "developer";
    private static final String POD_DEMO = "pod-demo";
    private static final int PORT = 8081;
    private static final String CLUSTER_IP = "172.30.152.124";
    private static final String DOCKER_IMAGE = "yemax/pod-demo:1";


    private Config config;
    private KubernetesClient client;
    Namespace ns;

    public DemoDeploymentK8s(String namespace) {
        ns = new NamespaceBuilder()
                .withNewMetadata()
                .withName(namespace)
                .addToLabels("this", "rocks")
                .endMetadata()
                .build();

        log.info(ns.getMetadata().getName());

        config = new ConfigBuilder()
                .withNamespace(ns.getMetadata().getName())
                .withTrustCerts(true)
                .build();

        log.info(config.getNamespace());
        client = new DefaultKubernetesClient(config);
        log.info(client.getConfiguration().toString());
    }

    private Map<String, String> getPodSelectorsAsMap() {
        Map<String, String> podSelectors = new HashMap<>();
        podSelectors.put("app", POD_DEMO);
        podSelectors.put("deploymentconfig", POD_DEMO);
        return podSelectors;
    }

    public DemoDeploymentK8s runPod() {
        ServiceAccount fabric8 = new ServiceAccountBuilder().withNewMetadata().withName("fabric8").endMetadata().build();
        client.serviceAccounts().inNamespace(client.getNamespace()).createOrReplace(fabric8);
        client.pods().inNamespace(client.getNamespace()).createOrReplaceWithNew()
                .withNewMetadata()
                .withName(POD_DEMO)
                .withNamespace(client.getNamespace())
                .endMetadata()
                .withNewSpec()
                .addNewContainer()
                .withName(POD_DEMO)
                .withImage(DOCKER_IMAGE)
                .addNewPort()
                .withContainerPort(8081)
                .withProtocol("TCP")
                .endPort()
                .endContainer()
                .withDnsPolicy("ClusterFirst")
                .withRestartPolicy("Always")
                .endSpec()
                .withNewStatus()
                .endStatus()
                .done();
        return this;
    }

    public DemoDeploymentK8s exposeService(){
        client.services().createOrReplaceWithNew()
                .withNewMetadata()
                .withName(POD_DEMO)
                .endMetadata()
                .withNewSpec()
                .withSelector(Collections.singletonMap("app", POD_DEMO))
                .addNewPort()
                .withName("test-port")
                .withProtocol("TCP")
                .withPort(8081)
                .withTargetPort(new IntOrString(8081))
                .endPort()
                .withType("LoadBalancer")
                .endSpec()
                .withNewStatus()
                .withNewLoadBalancer()
                .addNewIngress()
                .withIp("146.148.47.155")
                .endIngress()
                .endLoadBalancer()
                .endStatus()
                .done();
        return this;
    }
}
