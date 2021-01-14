# jmediatr-boot-starter
java中介者类库，实现进程内消息发布与处理解耦

## 安装
``` yaml
<!-- https://mvnrepository.com/artifact/io.github.zhaord/jmediatr-boot-starter -->
<dependency>
    <groupId>io.github.zhaord</groupId>
    <artifactId>jmediatr-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

## 一对一消息使用
```java
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
```

## 一对多消息使用
```java
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
```

## 博客
https://www.yuque.com/zhugexiaoliang/ymi827/ox8omg