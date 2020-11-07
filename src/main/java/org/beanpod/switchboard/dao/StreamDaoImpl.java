package org.beanpod.switchboard.dao;

import lombok.RequiredArgsConstructor;
import org.beanpod.switchboard.dto.*;
import org.beanpod.switchboard.dto.mapper.DecoderMapper;
import org.beanpod.switchboard.dto.mapper.EncoderMapper;
import org.beanpod.switchboard.dto.mapper.StreamMapper;
import org.beanpod.switchboard.entity.StreamEntity;
import org.beanpod.switchboard.exceptions.ExceptionType;
import org.beanpod.switchboard.repository.StreamRepository;
import org.openapitools.model.CreateStreamRequest;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StreamDaoImpl {
    private final StreamRepository streamRepository;
    private final StreamMapper mapper;
    private final ChannelDaoImpl channelService;

    public List<Long> getStreams(){
        return streamRepository.getAllId();
    }

    public StreamDTO getStreamById(Long id){
        return mapper.toDto(streamRepository.getOne(id));
    }

    public void createStream(CreateStreamRequest createStreamRequest){
        InputChannelDTO inputChannelDTO = channelService.getInputChannelById(createStreamRequest.getInputChannelId());
        OutputChannelDTO outputChannelDTO = channelService.getOutputChannelById(createStreamRequest.getOutputChannelId());
        StreamDTO streamDto = StreamDTO.builder()
                .inputChannel(inputChannelDTO)
                .outputChannel(outputChannelDTO)
                .build();

        System.out.println(inputChannelDTO.getDecoder());
        System.out.println(outputChannelDTO.getEncoder());
        StreamEntity streamEntity = mapper.toEntity(streamDto);

        if(streamRepository.existsDuplicate(createStreamRequest.getInputChannelId(), createStreamRequest.getOutputChannelId())){
            throw new ExceptionType.StreamAlreadyExistsException(createStreamRequest.getInputChannelId(), createStreamRequest.getOutputChannelId());
        }
        streamRepository.save(streamEntity);
    }

    public void deleteStream(Long id){
        streamRepository.deleteById(id);
    }

    public void updateStream(StreamDTO streamDto){
        if(!streamRepository.existsById(streamDto.getId())){
            throw new ExceptionType.StreamDoesNotExistsException(streamDto.getId());
        }
        StreamEntity streamEntity = mapper.toEntity(streamDto);
        streamRepository.save(streamEntity);
    }
}
