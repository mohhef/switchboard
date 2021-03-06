package org.beanpod.switchboard.dao;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.beanpod.switchboard.dto.LogDto;
import org.beanpod.switchboard.dto.mapper.LogMapper;
import org.beanpod.switchboard.repository.LogRepository;
import org.openapitools.model.LogModel;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogDaoImpl {

  private final LogRepository logRepository;
  private final LogMapper logMapper;

  public List<LogModel> getLogs(Long userId) {
    return logMapper.toModels(logRepository.findAll(userId));
  }

  public List<LogModel> getDeviceLogs(String serialNumber, Long userId) {
    return logMapper.toModels(logRepository.findBySerialNumber(serialNumber, userId));
  }

  public LogDto createLog(LogDto logDto) {
    return logMapper.toDto(logRepository.save(logMapper.toEntity(logDto)));
  }
}
