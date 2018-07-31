package com.feazesa.camel.boot;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class EventRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        /*
        from("timer:hello?period={{timer.period}}")
                .routeId("hello")
                .routeGroup("hello-group")
                .transform()
                .method("myBean", "saySomething")
                .filter(simple("${body} contains 'foo'"))
                .to("log:foo")
                .end()
                .to("stream:out");
        */
        from("kafka:camelTopic?brokers=192.168.99.100:9092&groupId=camelKafka")
                .to("log:kafka")
                .end()
                .to("stream:out");
    }

}
