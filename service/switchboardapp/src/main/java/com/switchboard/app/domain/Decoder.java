package com.switchboard.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter@Getter
@JsonIgnoreProperties({"hibernateLazyIntializer","handler","device"})

public class Decoder {

    @Id
    @Column(name = "serial_number")
    private Long serialNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="serial_number" )
    @MapsId
    private Device device;
}
