package org.beanpod.switchboard.service;

import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import org.beanpod.switchboard.dao.StreamLogDaoImpl;
import org.beanpod.switchboard.dto.StreamLogDto;
import org.beanpod.switchboard.dto.mapper.LogStreamMapper;
import org.beanpod.switchboard.entity.LogEntity;
import org.beanpod.switchboard.entity.StreamLog;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StreamLogService {
  private final LogStreamMapper logStreamMapper;
  private final StreamLogDaoImpl streamLogDao;

  // used in StreamAspect class to create stream-related logs
  public StreamLogDto createLog(
      OffsetDateTime dateTime,
      String message,
      String decoderSerial,
      String encoderSerial,
      String streamId) {
    LogEntity logEntity = new LogEntity(dateTime, message, "info", decoderSerial);
    StreamLog streamLog = new StreamLog(encoderSerial, streamId);

    streamLog.setLogEntity(logEntity);

    StreamLogDto streamLogDto = logStreamMapper.toLogStreamDto(streamLog);

    return streamLogDao.createStreamLog(streamLogDto);
  }
}