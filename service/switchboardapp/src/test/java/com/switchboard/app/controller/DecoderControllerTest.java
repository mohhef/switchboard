package com.switchboard.app.controller;

import com.switchboard.app.dao.DecoderDaoImpl;
import com.switchboard.app.dao.DeviceDaoImpl;
import com.switchboard.app.dto.DecoderDTO;
import com.switchboard.app.dto.mapper.DecoderMapper;
import com.switchboard.app.dto.mapper.DecoderMapperImpl;
import com.switchboard.app.entity.ChannelEntity;
import com.switchboard.app.entity.DecoderEntity;
import com.switchboard.app.entity.DeviceEntity;
import com.switchboard.app.exceptions.ExceptionType;
import com.switchboard.app.fixture.ChannelFixture;
import com.switchboard.app.fixture.DecoderFixture;
import com.switchboard.app.fixture.DeviceFixture;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DecoderControllerTest {

    @InjectMocks
    private DecoderController decoderController;

    @Mock
    private DecoderDaoImpl decoderService;

    @Mock
    private DeviceDaoImpl deviceService;

    private DecoderMapper decoderMapper;

    //stubbed Objects
    static private DeviceEntity device1, device2;
    static private DecoderEntity decoder1, decoder2;
    static private List<DecoderEntity> listOfDecoders;
    static private Set<ChannelEntity> setOfChannels;

    @BeforeAll
    static void decoderFixture() throws ParseException {
        //stubbing device and decoder objects
        device1 = DeviceFixture.getDevice1();
        device2 = DeviceFixture.getDevice2();

        setOfChannels = ChannelFixture.getSetOfChannels();

        decoder1 = DecoderFixture.getDecoder1(device1,setOfChannels);
        decoder2 = DecoderFixture.getDecoder2(device2,setOfChannels);

        listOfDecoders = DecoderFixture.getListOfDecoders(decoder1, decoder2);
    }

    @BeforeEach
    void setup(){
        decoderMapper = Mockito.spy(new DecoderMapperImpl()); //to spy on DecoderMapper object
        MockitoAnnotations.initMocks(this); //to be able to initiate decoderController object
    }

    @Test
    final void testRetrieveAllDecoders(){
        when(decoderService.getDecoders()).thenReturn(listOfDecoders);

        List<DecoderDTO> allDecoders = decoderController.retrieveAllDecoders();
        List<DecoderDTO> listOfExpectDTODecoders = decoderMapper.toDecoderDTOs(listOfDecoders); //covert List<DecoderEntity> to List<DecoderDTO>

        assertFalse(allDecoders.isEmpty(),"allDecoders list is empty."); //check if an empty list was returned
        assertIterableEquals(listOfExpectDTODecoders, allDecoders,"listOfExpectDTODecoders and allDecoders lists are not equal."); //check both lists contents
    }

    //When a decoder is available in the DB
    @Test
    final void testRetrieveDecoder(){
        when(decoderService.findDecoder("1")).thenReturn(java.util.Optional.of(decoder1));

        ResponseEntity<EntityModel<DecoderDTO>> actualDecoder = decoderController.retrieveDecoder("1");

        assertNotNull(actualDecoder, "actualDecoder object is null.");
        assertEquals(200,actualDecoder.getStatusCodeValue(),"Status code is not 200");
        assertEquals(decoder1.getSerialNumber(), actualDecoder.getBody().getContent().getSerialNumber(), "expectedDecoder and actualDecoder objects are not equal.");
    }

    //When a decoder is unavailable in the DB
    @Test
    final void testRetrieveDecoderEmpty(){
        assertThrows(ExceptionType.DeviceNotFoundException.class, () -> {
            decoderController.retrieveDecoder("NotAvailable");
        }, "DeviceNotFoundException exception should have been thrown.");
    }

    //When a device is unavailable in the DB
    @Test
    final void testCreateDecoder() {
        when(deviceService.findDevice("1")).thenReturn(java.util.Optional.of(device1));
        when(decoderService.save(decoder1)).thenReturn(decoder1);

        //mock a request
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServerName("localhost/decoder");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        //request response
        ResponseEntity response = decoderController.createDecoder(decoder1);

        assertEquals(201, response.getStatusCodeValue(), "The status code is not 201.");
        assertEquals("http://localhost/decoder/1", response.getHeaders().get("Location").get(0), "The returned location is incorrect.");
    }

    //When a device is available in the DB
    @Test
    final void testCreateDecoderAlreadyExists(){
        assertThrows(ExceptionType.DeviceNotFoundException.class, () -> {
            decoderController.createDecoder(decoder1);
        }, "DeviceNotFoundException should have been thrown.");
    }

    //When a decoder is available in the DB
    @Test
    final void testDeleteDecoder(){
        when(decoderService.deleteDecoder("1")).thenReturn(Long.valueOf(1));

        ResponseEntity<String> response = decoderController.deleteDecoder("1");

        assertEquals(200, response.getStatusCodeValue(), "The status code is not 200.");
        assertEquals("Decoder with serial number 1 Deleted", response.getBody(), "Returned response does not match the expected.");
    }

    //When a decoder is unavailable in the DB
    @Test
    final void testDeleteDecoderNotExisting(){
        assertThrows(ExceptionType.DeviceNotFoundException.class, () -> {
            decoderController.deleteDecoder("Not Available decoder");
        }, "DeviceNotFoundException should have been thrown.");
    }


    //When a encoder is available in the DB
    @Test
    final void testUpdateDecoder(){

        when(decoderService.findDecoder("1")).thenReturn(java.util.Optional.of(decoder1));
        decoder1.getInputs().clear();
        when(decoderService.save(decoder1)).thenReturn(decoder1);
        DecoderDTO decoderDTO1 = decoderMapper.toDecoderDTO(decoder1);
        ResponseEntity<DecoderDTO> response = decoderController.updateDecoder("1", decoderDTO1);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().getInputs().isEmpty());
    }

    //Test exceptions when updating encoder
    @Test
    final void testUpdateDecoderExceptions(){
        DecoderDTO decoderDTO1 = decoderMapper.toDecoderDTO(decoder1);

        //When device is unavailable in the DB
        assertThrows(ExceptionType.DeviceNotFoundException.class, () -> {
            decoderController.updateDecoder("3", decoderDTO1);
        }, "DeviceNotFoundException should have been thrown.");
    }
}