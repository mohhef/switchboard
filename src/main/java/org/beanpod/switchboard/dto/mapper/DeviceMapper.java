package org.beanpod.switchboard.dto.mapper;

import org.beanpod.switchboard.dto.DeviceDTO;
import org.beanpod.switchboard.entity.DeviceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DeviceMapper {

    DeviceMapper INSTANCE = Mappers.getMapper(DeviceMapper.class);

    DeviceDTO toDeviceDTO(DeviceEntity deviceEntity);

    List<DeviceDTO> toDeviceDTOs(List<DeviceEntity> deviceEntities);

    DeviceEntity toDeviceEntity(DeviceDTO deviceDTO);

}