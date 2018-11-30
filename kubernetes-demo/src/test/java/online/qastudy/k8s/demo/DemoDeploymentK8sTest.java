package online.qastudy.k8s.demo;

import org.junit.Test;

public class DemoDeploymentK8sTest {
    @Test
    public void t(){
        DemoDeploymentK8s demo = new DemoDeploymentK8s("default");
        demo.runPod().exposeService();
    }
}
