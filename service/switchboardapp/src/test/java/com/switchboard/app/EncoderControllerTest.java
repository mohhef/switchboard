package com.switchboard.app;

import com.switchboard.app.controller.EncoderController;
import com.switchboard.app.dao.DeviceDaoImpl;
import com.switchboard.app.dao.EncoderDaoImpl;
import com.switchboard.app.domain.DeviceEntity;
import com.switchboard.app.domain.EncoderEntity;
import com.switchboard.app.exceptions.DeviceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class EncoderControllerTest {
    @InjectMocks
    EncoderController encoderController;

    @Mock
    EncoderDaoImpl encoderService;

    @Mock
    DeviceDaoImpl deviceService;

    //stubbed Objects
    private DeviceEntity device1;
    private EncoderEntity encoder1;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this); //to be able to initiate encoderController object

        //stubbing device and encoder objects
        device1 = new DeviceEntity("1","Encoder #1","Running",null,null);
        encoder1 = new EncoderEntity("1", device1);
    }

    @Test
    final void retrieveAllEncodersTest(){
        //stubbing DeviceEntity and EncoderEntity objects
        DeviceEntity device2 = new DeviceEntity("2","Encoder #2","Failing",null,null);
        EncoderEntity encoder2 = new EncoderEntity("2", device2);

        //Adding stubbed objects to the list that should be returned when getEncoders is called
        List<EncoderEntity> listOfEncoders= new ArrayList<EncoderEntity>();
        listOfEncoders.add(encoder1);
        listOfEncoders.add(encoder2);

        when(encoderService.getEncoders()).thenReturn(listOfEncoders);

        List allEncoders = encoderController.retrieveAllEncoders();

        assertFalse(allEncoders.isEmpty(),"allEncoders list is empty."); //check if an empty list was returned
        assertIterableEquals(listOfEncoders, allEncoders,"listOfEncoders and allEncoders lists are not equal."); //check both lists contents
    }

    //When a encoder is available in the DB
    @Test
    final void testRetrieveEncoder(){
        when(encoderService.findEncoder("1")).thenReturn(java.util.Optional.of(encoder1));

        EncoderEntity actualEncoder = encoderController.retrieveDevice("1").getContent();

        assertNotNull(actualEncoder, "actualEncoder object is null.");
        assertEquals(encoder1, actualEncoder, "expectedEncoder and actualEncoder objects are not equal.");
    }
}