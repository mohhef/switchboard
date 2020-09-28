package com.switchboard.app.encoder;

import com.switchboard.app.device.Device;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
public class Encoder {

    @Id
    @Column(name = "serial_number")
    private String serialNumber;

    @OneToOne
    @JoinColumn(name="serial_number")
    @MapsId
    private Device device;
}
