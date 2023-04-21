package com.raf.restdemo;

import org.apache.activemq.broker.BrokerService;

public class MessageBroker {

    public static void main(String[] args) throws Exception {
        BrokerService broker = new BrokerService();
        // configure the broker
        broker.addConnector("tcp://localhost:61616");
        broker.start();
    }
}
