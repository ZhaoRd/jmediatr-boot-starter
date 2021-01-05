package io.github.zhaord.jmediatr;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 单播测试
 */
@ExtendWith(SpringExtension.class)
@Import(
        value = {
                Mediator.class,
                PingPongTest.PingHandler.class,
        }
)
public class PingPongTest {

    @Autowired
    IMediator mediator;

    @Test
    public void should() {
        String send = mediator.send(new Ping());

        assertThat(send).isNotNull();
        assertThat(send).isEqualTo("Pong");
    }

    public static class Ping implements IRequest<String> {
    }

    public static class PingHandler implements IRequestHandler<Ping, String> {
        @Override
        public String handle(Ping request) {
            return "Pong";
        }
    }

}
