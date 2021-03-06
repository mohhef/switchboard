package org.beanpod.switchboard.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.file.attribute.UserPrincipal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.beanpod.switchboard.dao.DeviceDaoImpl;
import org.beanpod.switchboard.dao.EncoderDaoImpl;
import org.beanpod.switchboard.dao.UserDaoImpl;
import org.beanpod.switchboard.dto.DeviceDto;
import org.beanpod.switchboard.dto.EncoderDto;
import org.beanpod.switchboard.dto.mapper.EncoderMapper;
import org.beanpod.switchboard.dto.mapper.StreamMapper;
import org.beanpod.switchboard.entity.DeviceEntity;
import org.beanpod.switchboard.entity.EncoderEntity;
import org.beanpod.switchboard.entity.UserEntity;
import org.beanpod.switchboard.exceptions.ExceptionType;
import org.beanpod.switchboard.fixture.DeviceFixture;
import org.beanpod.switchboard.fixture.EncoderFixture;
import org.beanpod.switchboard.fixture.StreamFixture;
import org.beanpod.switchboard.fixture.UserFixture;
import org.beanpod.switchboard.service.EncoderService;
import org.beanpod.switchboard.util.MaintainDeviceStatus;
import org.beanpod.switchboard.util.UserMockUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openapitools.model.EncoderModel;
import org.openapitools.model.StreamModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class EncoderControllerTest {

  // stubbed Objects
  private static List<DeviceEntity> listOfDevices;
  private static DeviceDto deviceDto;
  private static EncoderEntity encoder;
  private static EncoderDto encoderDTO;
  private static EncoderModel encoderModel;
  private static List<EncoderEntity> listOfEncoders;
  private static UserEntity user;
  @InjectMocks private EncoderController encoderController;
  @Mock private EncoderDaoImpl encoderDao;
  @Mock private DeviceDaoImpl deviceService;
  @Mock private EncoderService encoderService;
  @Mock private EncoderMapper encoderMapper;
  @Mock private StreamMapper streamMapper;
  @Mock private MaintainDeviceStatus maintainDeviceStatus;
  @Mock private HttpServletRequest httpServletRequest;
  @Mock private UserPrincipal userPrincipal;
  @Mock private UserDaoImpl userDao;

  @BeforeEach
  void setup() {
    setupEncoderFixture();

    MockitoAnnotations.initMocks(this);

    UserMockUtil.mockUser(user, httpServletRequest, userPrincipal, userDao);
  }

  private void setupEncoderFixture() {
    listOfDevices = DeviceFixture.getListOfDevices();
    deviceDto = DeviceFixture.getDeviceDto();
    encoder = EncoderFixture.getEncoderEntity1();
    encoderDTO = EncoderFixture.getEncoderDto();
    encoderModel = EncoderFixture.getEncoderModelWithOutputChannel();
    listOfEncoders = EncoderFixture.getListOfEncoder();
    user = UserFixture.getUserEntity();
  }

  @Test
  final void testRetrieveAllEncoders() {
    when(encoderDao.getEncoders(user)).thenReturn(listOfEncoders);
    when(encoderMapper.toDtos(any())).thenReturn(EncoderFixture.getEncoderDtos());
    when(encoderMapper.toModels(any())).thenReturn(EncoderFixture.getEncoderModels());

    ResponseEntity<List<EncoderModel>> response = encoderController.retrieveAllEncoders();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    List<EncoderModel> allEncoders = response.getBody();
    assertNotNull(allEncoders);
    assertFalse(allEncoders.isEmpty());
    assertIterableEquals(EncoderFixture.getEncoderModels(), allEncoders);
  }

  // When a encoder is available in the DB
  @Test
  final void testRetrieveEncoder() {
    when(encoderDao.findEncoder(user, EncoderFixture.SERIAL_NUMBER))
        .thenReturn(Optional.of(encoderDTO));
    when(maintainDeviceStatus.maintainStatusField(anyList())).thenReturn(listOfDevices);
    when(encoderMapper.toModel(any())).thenReturn(EncoderFixture.getEncoderModel());

    ResponseEntity<EncoderModel> actualEncoder =
        encoderController.retrieveEncoder(EncoderFixture.SERIAL_NUMBER);

    assertNotNull(actualEncoder);
    assertEquals(200, actualEncoder.getStatusCodeValue());
    assertNotNull(actualEncoder.getBody());
    assertEquals(encoder.getSerialNumber(), actualEncoder.getBody().getSerialNumber());
  }

  // When a encoder is unavailable in the DB
  @Test
  final void testRetrieveEncoderEmpty() {
    assertThrows(
        ExceptionType.DeviceNotFoundException.class,
        () -> encoderController.retrieveEncoder("NotAvailable"));
  }

  // When a device is available in the DB
  @Test
  final void testCreateEncoder() {
    when(deviceService.findDevice(user, EncoderFixture.SERIAL_NUMBER))
        .thenReturn(Optional.of(deviceDto));
    when(encoderMapper.toDto(encoderModel)).thenReturn(encoderDTO);
    when(encoderDao.save(user, encoderDTO)).thenReturn(encoderDTO);
    when(encoderMapper.toModel(encoderDTO)).thenReturn(encoderModel);
    ResponseEntity<EncoderModel> response = encoderController.createEncoder(encoderModel);
    assertEquals(200, response.getStatusCodeValue());
  }

  @Test
  final void testCreateEncoderWithoutChannels() {
    encoderModel.setOutput(Collections.emptyList());
    assertThrows(
        ExceptionType.MissingChannelsException.class,
        () -> encoderController.createEncoder(encoderModel));
  }

  // When a device is unavailable in the DB
  @Test
  final void testCreateEncoderAlreadyExists() {
    assertThrows(
        ExceptionType.DeviceNotFoundException.class,
        () -> encoderController.createEncoder(encoderModel));
  }

  // When an encoder is available in the DB
  @Test
  final void testDeleteEncoder() {
    when(encoderDao.deleteEncoder(user, EncoderFixture.SERIAL_NUMBER)).thenReturn(Long.valueOf(1));
    ResponseEntity<String> response = encoderController.deleteEncoder(EncoderFixture.SERIAL_NUMBER);
    assertEquals(200, response.getStatusCodeValue(), "The status code is not 200.");
    assertEquals("Encoder with serial number 1 Deleted", response.getBody());
  }

  // When a encoder is unavailable in the DB
  @Test
  final void testDeleteEncoderNotExisting() {
    assertThrows(
        ExceptionType.DeviceNotFoundException.class,
        () -> encoderController.deleteEncoder("Not Available encoder"));
  }

  // When a encoder is available in the DB
  @Test
  final void testUpdateEncoder() {
    EncoderDto encoderDto = EncoderFixture.getEncoderDto();
    when(encoderDao.findEncoder(user, EncoderFixture.SERIAL_NUMBER))
        .thenReturn(Optional.of(encoderDto));

    when(encoderMapper.toDto(any(EncoderModel.class))).thenReturn(EncoderFixture.getEncoderDto());

    when(encoderDao.save(user, encoderDto)).thenReturn(encoderDto);

    when(encoderMapper.toModel(any())).thenReturn(EncoderFixture.getEncoderModel());

    ResponseEntity<EncoderModel> response = encoderController.updateEncoder(encoderModel);

    assertEquals(200, response.getStatusCodeValue());
  }

  // Test exceptions when updating encoder
  @Test
  final void testUpdateEncoderExceptions() {
    EncoderDto encoderDto = EncoderFixture.getEncoderDto();
    when(encoderDao.findEncoder(user, encoderDto.getSerialNumber())).thenReturn(Optional.empty());

    assertThrows(
        ExceptionType.DeviceNotFoundException.class,
        () -> encoderController.updateEncoder(encoderModel));
  }

  @Test
  final void testGetEncoderStreams() {
    when(encoderService.getEncoderStreams(eq(user), any(String.class)))
        .thenReturn(List.of(StreamFixture.getStreamDto()));
    when(streamMapper.toModels(anyList())).thenReturn(StreamFixture.getStreamModelList());

    ResponseEntity<List<StreamModel>> response =
        encoderController.getEncoderStreams(EncoderFixture.SERIAL_NUMBER);

    verify(encoderService).getEncoderStreams(user, EncoderFixture.SERIAL_NUMBER);
    verify(streamMapper).toModels(List.of(StreamFixture.getStreamDto()));

    assertEquals(200, response.getStatusCodeValue());
    assertIterableEquals(List.of(StreamFixture.getStreamModel()), response.getBody());
  }
}
