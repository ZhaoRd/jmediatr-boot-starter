package org.jmediatr;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 服务循环依赖测试
 */
@ExtendWith(SpringExtension.class)
@Import(
        value = {
                Mediator.class,
                ServiceCycTests.DemoAService.class,
                ServiceCycTests.DemoBService.class,
        }
)
public class ServiceCycTests {

    @Autowired
    IDemoAService aService;

    @Autowired
    IDemoBService bService;

    @Test
    public void should() {
        String a = aService.hello();
        assertThat(a).isEqualTo("call B in A");

        String b = bService.hello();
        assertThat(b).isEqualTo("call A in B");
    }

    public static interface IDemoAService {
        String hello();

        String helloWithB();
    }

    public static interface IDemoBService {
        String hello();

        String helloWithA();
    }

    public static class DemoAService implements IDemoAService, IRequestHandler<RequestAService, String> {
        //private final IDemoBService bService;

        private final IMediator mediator;

        public DemoAService(IMediator mediator) {
            this.mediator = mediator;
        }

        @Override
        public String hello() {
            return this.mediator.send(new RequestBService());
        }

        @Override
        public String helloWithB() {
            return "call A in B";
        }

        @Override
        public String handle(RequestAService request) {
            return this.helloWithB();
        }
    }

    public static class DemoBService implements IDemoBService, IRequestHandler<RequestBService, String> {
        //private final IDemoAService aService;
        private final IMediator mediator;

        public DemoBService(IMediator mediator) {
            this.mediator = mediator;
        }

        @Override
        public String hello() {
            return this.mediator.send(new RequestAService());
        }

        @Override
        public String helloWithA() {
            return "call B in A";
        }

        @Override
        public String handle(RequestBService request) {
            return this.helloWithA();
        }
    }

    public static class RequestAService implements IRequest<String> {
    }

    public static class RequestBService implements IRequest<String> {
    }

}

