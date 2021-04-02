package org.beanpod.switchboard.fixture;

import java.util.ArrayList;
import java.util.List;
import org.beanpod.switchboard.dto.StreamLogDto;
import org.beanpod.switchboard.entity.StreamLog;
import org.openapitools.model.StreamLogModel;

public class StreamLogFixture {
  public static StreamLogDto getStreamLogDto() {
    return StreamLogDto.builder()
        .id(LogFixture.id)
        .serialNumber(DeviceFixture.SERIAL_NUMBER2)
        .streamId(String.valueOf(StreamFixture.ID))
        .logEntity(LogFixture.getLogEntity())
        .build();
  }

  public static StreamLog getStreamLog() {
    return StreamLog.builder()
        .id(LogFixture.id)
        .serialNumber(DeviceFixture.SERIAL_NUMBER2)
        .streamId(String.valueOf(StreamFixture.ID))
        .logEntity(LogFixture.getLogEntity())
        .build();
  }

  public static StreamLogModel getStreamLogModel() {
    return new StreamLogModel()
        .id(LogFixture.id)
        .serialNumber(DeviceFixture.SERIAL_NUMBER2)
        .streamId((int) (long) StreamFixture.ID)
        .logEntity(LogFixture.getLogModel());
  }

  public static List<StreamLogModel> getListOfStreamLogsModel() {
    List<StreamLogModel> listOfStreamLogsModel = new ArrayList<>();
    listOfStreamLogsModel.add(getStreamLogModel());
    return listOfStreamLogsModel;
  }

  public static List<StreamLog> getListOfStreamLogs() {
    List<StreamLog> listOfStreamLogs = new ArrayList<>();
    listOfStreamLogs.add(getStreamLog());
    return listOfStreamLogs;
  }
}