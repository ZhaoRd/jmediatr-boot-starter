package org.jmediatr;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 多播测试
 */
@ExtendWith(SpringExtension.class)
@Import(
        value = {
                Mediator.class,
                PingNoticeTests.Pong1.class,
                PingNoticeTests.Pong2.class,
        }
)
public class PingNoticeTests {

    @Autowired
    IMediator mediator;

    @Autowired
    Pong1 pong1;

    @Autowired
    Pong2 pong2;

    @Test
    public void should() {
        mediator.publish(new Ping());

        assertThat(pong1.getCode()).isEqualTo("Pon1");
        assertThat(pong2.getCode()).isEqualTo("Pon2");
    }

    public static class Ping implements INotification {
    }

    public static class Pong1 implements INotificationHandler<Ping> {

        private String code;

        public String getCode() {
            return code;
        }

        @Override
        public void handle(Ping notification) {
            this.code = "Pon1";
        }
    }

    public static class Pong2 implements INotificationHandler<Ping> {

        private String code;

        public String getCode() {
            return code;
        }

        @Override
        public void handle(Ping notification) {
            this.code = "Pon2";
        }
    }


}
