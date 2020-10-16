package com.switchboard.app.dto;

import com.switchboard.app.entity.DeviceEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EncoderDTO {
    private String serialNumber;
    private DeviceEntity device;
}
