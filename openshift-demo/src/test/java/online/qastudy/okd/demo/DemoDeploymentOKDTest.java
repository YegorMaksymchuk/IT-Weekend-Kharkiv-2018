package online.qastudy.okd.demo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.assertj.core.api.Assertions.assertThat;


public class DemoDeploymentOKDTest {
    private DemoDeploymentOKD demoDeploymentOKD;
    private WebDriver driver;

    @Before
    public void deploy() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
        driver = new ChromeDriver();
        demoDeploymentOKD = new DemoDeploymentOKD("it-weekend-2018");
    }

    @After
    public void cleanup() {
        demoDeploymentOKD.close();
    }

    @Test
    public void testAppDeployment() {
        demoDeploymentOKD.login()
                .createNewProject("it-weekend-2018", "Demo for IT Weekend 2018", "Demo of Fabric8")
                .deployPod()
                .deployService()
                .createRout();

        TestHelper.wait30seconds();
        driver.navigate().to(demoDeploymentOKD.getApplicationURL());
        driver.navigate().refresh();

        assertThat(driver.findElement(By.className("center")).getText()).contains("Hello from POD!!");
    }
}
