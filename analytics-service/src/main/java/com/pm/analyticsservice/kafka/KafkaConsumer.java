package com.pm.analyticsservice.kafka;

import org.apache.kafka.shaded.com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Service
public class KafkaConsumer {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics="patient",groupId="analytics-service")
    public void consumeEvent(byte[] event){
        try{
            PatientEvent patientEvent = PatientEvent.parseFrom(event);
            //perform business logic
            log.info("Received Patient Event:[Patinent Id:{},Name:{},Email:{}]",patientEvent.getPatientId(),patientEvent.getName(),patientEvent.getEmail());
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
            log.error("Error deserializing patient event:{}",e.getMessage());
        }
    }
}
