package com.gasmonitor.service;
//--2

import com.gasmonitor.entity.GasEvent;
import com.gasmonitor.entity.GasHazelcast;
import com.gasmonitor.utils.InfluxdbService;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.ITopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

//--11

//--20
@Service
@ConfigurationProperties(prefix = "event")
public class GasEventService {

    @Autowired
    HazelcastInstance hazelcastInstance;
    @Autowired
    InfluxdbService influxdbService;

    private ITopic<GasHazelcast> topic;
    private IMap<String, String> map;
    private String topicName = "gasEvent";

    //get the hazelcastInstance's topic--29
    @PostConstruct
    public void initContext() {
        topic = hazelcastInstance.getTopic(topicName);
        map = hazelcastInstance.getMap("tenant");
//delete this line before online 
        mockHazelcastMap();

    }

    public int process(GasEvent event) throws Exception {
        display(event);
        GasHazelcast hazelcastEvent = new GasHazelcast();
        hazelcastEvent.setGasEvent(event);
        String tenant = "";
        tenant = map.get(event.getHardwareId());
        if (tenant != null) {
            hazelcastEvent.setTenantId(tenant);
            topic.publish(hazelcastEvent);
        }
        return 1;
    }

    //service for query history oof measurements
//    public List<GasEvent> query(String hardwareId, Timestamp begin, Timestamp end) {
    public List<GasEvent> query(String hardwareId, long begin, long end) {
        String tenant = map.get(hardwareId);
        System.out.println("\n========================================================The tenant id is" + tenant);
        return influxdbService.query(tenant, hardwareId, begin, end);
    }

    //to display data
    public void display(GasEvent event) {
        System.out.println("================================================================================The received even is :");
        System.out.println("The received even is :");
        System.out.println(event);
    }

    public void mockHazelcastMap() {
        int count = 0;
        while (count < 10000) {
            map.put("ID" + count, "999");
            count++;
        }
    }
}
