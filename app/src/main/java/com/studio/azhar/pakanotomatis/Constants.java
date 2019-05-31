package com.studio.azhar.pakanotomatis;

public class Constants {

    //only for publish
    public static final String MQTT_BROKER_URL = "tcp://m10.cloudmqtt.com:10611";

    public static final String PUBLISH_TOPIC = "IOT/PAKAN/STATUS";

    public static final String CLIENT_ID = "androidClient";
}

//NOTE: paho MQTT client just for publish message, all setting broker here