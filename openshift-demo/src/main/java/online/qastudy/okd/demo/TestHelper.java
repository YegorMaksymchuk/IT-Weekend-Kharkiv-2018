package online.qastudy.okd.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.HttpGet;
import sun.net.www.protocol.https.HttpsURLConnectionImpl;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

@Slf4j
public final class TestHelper {
    private TestHelper() {
    }

    public static void wait30seconds() {
        try {
            Thread.sleep(30 * 1000);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
