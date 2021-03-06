package org.beanpod.switchboard.dao;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.beanpod.switchboard.dto.DeviceDto;
import org.beanpod.switchboard.dto.mapper.DeviceMapper;
import org.beanpod.switchboard.entity.DeviceEntity;
import org.beanpod.switchboard.entity.UserEntity;
import org.beanpod.switchboard.repository.DeviceRepository;
import org.beanpod.switchboard.util.MaintainDeviceStatus;
import org.openapitools.model.CreateDeviceRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeviceDaoImpl {

  private final DeviceRepository deviceRepository;
  private final DeviceMapper deviceMapper;

  public DeviceDto save(UserEntity user, DeviceDto device) {
    Optional<DeviceDto> deviceDto = findDevice(user, device.getSerialNumber());
    deviceMapper.updateDeviceFromDto(device, deviceDto.orElse(null));
    return deviceMapper.toDto(deviceRepository.save(deviceMapper.toEntity(deviceDto.orElse(null))));
  }

  public DeviceDto createDevice(
      UserEntity user, CreateDeviceRequest createDeviceRequest, String publicIpAddress) {
    DeviceDto deviceDto = deviceMapper.toDto(user, createDeviceRequest, publicIpAddress);
    deviceDto.setStatus(MaintainDeviceStatus.OFFLINE_STATUS);
    DeviceEntity deviceEntity = deviceMapper.toEntity(deviceDto);
    DeviceEntity savedDeviceEntity = deviceRepository.save(deviceEntity);
    return deviceMapper.toDto(savedDeviceEntity);
  }

  public List<DeviceEntity> getDevices(UserEntity user) {
    return deviceRepository.findByUser(user);
  }

  public Optional<DeviceDto> findDevice(String serialNumber) {
    return deviceRepository.findBySerialNumber(serialNumber).map(deviceMapper::toDto);
  }

  public Optional<DeviceDto> findDevice(UserEntity user, String serialNumber) {
    return deviceRepository.findByUserAndSerialNumber(user, serialNumber).map(deviceMapper::toDto);
  }

  public Long deleteDevice(UserEntity user, String serialNumber) {
    return deviceRepository.deleteByUserAndSerialNumber(user, serialNumber);
  }
}
