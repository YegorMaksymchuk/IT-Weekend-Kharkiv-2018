package online.qastudy.okd.demo;

import lombok.extern.slf4j.Slf4j;

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
