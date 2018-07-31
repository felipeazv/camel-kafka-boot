package com.feazesa.camel.boot;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
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

        //kafka to log

        from("kafka:camelTopic?brokers=192.168.99.100:9092&groupId=camelKafka")
                .to("log:kafka")
                .end()
                .to("stream:out");

        //TCP (netty4) to log
        from("netty4:tcp://localhost:8081?keepAlive=true&textline=true&sync=true")
                .routeId("netty4tcptest")
                .routeGroup("netty4tcptest-group")
                .to("log:netty4tcptest")
                .end()
                .to("stream:out");

    }


}
