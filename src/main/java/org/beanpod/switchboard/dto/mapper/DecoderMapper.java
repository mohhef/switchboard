package org.beanpod.switchboard.dto.mapper;

import org.beanpod.switchboard.dto.DecoderDTO;
import org.beanpod.switchboard.entity.DecoderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DecoderMapper {

    DecoderMapper INSTANCE = Mappers.getMapper(DecoderMapper.class);

    DecoderDTO toDecoderDTO(DecoderEntity decoderEntity);

    List<DecoderDTO> toDecoderDTOs(List<DecoderEntity> decoderEntities);

    DecoderEntity toDecoderEntity(DecoderDTO DecoderDTO);
}