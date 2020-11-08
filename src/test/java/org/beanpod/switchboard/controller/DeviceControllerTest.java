package org.beanpod.switchboard.controller;

import org.beanpod.switchboard.dao.DeviceDaoImpl;
import org.beanpod.switchboard.dto.DeviceDTO;
import org.beanpod.switchboard.dto.mapper.DeviceMapper;
import org.beanpod.switchboard.dto.mapper.DeviceMapperImpl;
import org.beanpod.switchboard.entity.DeviceEntity;
import org.beanpod.switchboard.exceptions.ExceptionType;
import org.beanpod.switchboard.fixture.DeviceFixture;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DeviceControllerTest {
    @InjectMocks
    private DeviceController deviceController;

    @Mock
    private DeviceDaoImpl deviceService;

    private DeviceMapper deviceMapper;

    //stubbed DeviceEntity object
    static private DeviceEntity device1, device2;
    static private List<DeviceEntity> listOfDevices;

    @BeforeEach
    void setup(){
        device1 = DeviceFixture.getDevice1();
        device2 = DeviceFixture.getDevice2();
        listOfDevices = DeviceFixture.getListOfDevices();
        deviceMapper = Mockito.spy(new DeviceMapperImpl());
        MockitoAnnotations.initMocks(this);
    }

    @Test
    final void testRetrieveAllDevices(){
        when(deviceService.getDevices()).thenReturn(listOfDevices);
        List<DeviceDTO> allDevices = deviceController.retrieveAllDevices();
        List<DeviceDTO> listOfExpectDTODevices = deviceMapper.toDeviceDTOs(listOfDevices);

        assertFalse(allDevices.isEmpty(),"allDevices list is empty."); //check if an empty list was returned
        assertIterableEquals(listOfExpectDTODevices, allDevices,"listOfExpectDTODevices and allDevices lists are not equal."); //check both lists contents
    }

    //When a device is available in the DB
    @Test
    final void testRetrieveDevice(){
        when(deviceService.findDevice("1")).thenReturn(Optional.of(device1));
        ResponseEntity<EntityModel<DeviceDTO>> actualDevice = deviceController.retrieveDevice("1");

        assertNotNull(actualDevice, "actualDevice object is null.");
        assertEquals(200, actualDevice.getStatusCodeValue(), "Status code is not 200");
        assertEquals(device1.getSerialNumber(), actualDevice.getBody().getContent().getSerialNumber(), "expectedDevice and actualDevice objects are not equal.");
    }

    //When a device is unavailable in the DB
    @Test
    final void testRetrieveDeviceEmpty(){
        assertThrows(ExceptionType.DeviceNotFoundException.class, () -> {
            deviceController.retrieveDevice("NotAvailable");
        }, "DeviceNotFoundException exception should have been thrown.");
    }

    //When a device is unavailable in the DB
    @Test
    final void testCreateDeviceAlreadyExists(){
        when(deviceService.findDevice("1")).thenReturn(Optional.of(device1));

        assertThrows(ExceptionType.DeviceAlreadyExistsException.class, () -> {
            deviceController.createDevice(deviceMapper.toDeviceDTO(device1));
        }, "DeviceAlreadyExistsException should have been thrown.");
    }

    //When a device is unavailable in the DB
    @Test
    final void testCreateDevice() {
        when(deviceService.save(device1)).thenReturn(device1);

        //mock a request
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServerName("localhost/device");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        //request response
        ResponseEntity response = deviceController.createDevice(deviceMapper.toDeviceDTO(device1));

        assertEquals(201, response.getStatusCodeValue(), "The status code is not 201.");
        assertEquals("http://localhost/device/1", response.getHeaders().get("Location").get(0), "The returned location is incorrect.");
    }

    //When a device is available in the DB
    @Test
    final void testDeleteDevice(){
        when(deviceService.deleteDevice("1")).thenReturn(Long.valueOf(1));
        ResponseEntity<String> response = deviceController.deleteDevice("1");

        assertEquals(200, response.getStatusCodeValue(), "The status code is not 200.");
        assertEquals("Device with serial number 1 Deleted", response.getBody(), "Returned response does not match the expected.");
    }

    //When a device is unavailable in the DB
    @Test
    final void testDeleteDeviceNotExisting(){
        assertThrows(ExceptionType.DeviceNotFoundException.class, () -> {
            deviceController.deleteDevice("Not Available device");
        }, "DeviceNotFoundException should have been thrown.");
    }

    //When a device is available in the DB
    @Test
    final void testUpdateDevice(){

        when(deviceService.findDevice("1")).thenReturn(Optional.of(device1));
        device1.setStatus("Stopped");
        when(deviceService.save(device1)).thenReturn(device1);
        DeviceDTO deviceDTO1 = deviceMapper.toDeviceDTO(device1);
        ResponseEntity<DeviceDTO> response = deviceController.updateDevice(deviceDTO1);

        assertEquals(200, response.getStatusCodeValue(), "The status code is not 200");
        assertEquals("Stopped", response.getBody().getStatus(), "The device is not updated");
    }

    /*
     * test all exceptions in the updateDevice controller
     * serial number = 3 doesn't exist
     */
    @Test
    final void testUpdateDeviceExceptions(){
        DeviceDTO deviceDTO1 = deviceMapper.toDeviceDTO(device1);

        //When device is unavailable in the DB
        assertThrows(ExceptionType.DeviceNotFoundException.class, () -> {
            deviceController.updateDevice(deviceDTO1);
        }, "DeviceNotFoundException should have been thrown.");

        //This stubbing is needed for the following exception to be tested
        when(deviceService.findDevice("1")).thenReturn(java.util.Optional.of(device1));
    }
}