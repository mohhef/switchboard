package org.beanpod.switchboard.controller;

import org.beanpod.switchboard.dao.DeviceDaoImpl;
import org.beanpod.switchboard.dto.DeviceDTO;
import org.beanpod.switchboard.dto.mapper.DeviceMapper;
import org.beanpod.switchboard.entity.DeviceEntity;
import org.beanpod.switchboard.exceptions.ExceptionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    DeviceDaoImpl service;

    @Autowired
    DeviceMapper deviceMapper;

    @GetMapping
    public List<DeviceDTO> retrieveAllDevices() {
        return (deviceMapper.toDeviceDTOs(service.getDevices()));
    }

    @GetMapping("/{serialNumber}")
    public ResponseEntity<EntityModel<DeviceDTO>> retrieveDevice(@PathVariable @Valid String serialNumber) {

        Optional<DeviceEntity> device = service.findDevice(serialNumber);
        if (device.isEmpty()) {
            throw new ExceptionType.DeviceNotFoundException(serialNumber);
        }
        EntityModel<DeviceDTO> resource = EntityModel.of(deviceMapper.toDeviceDTO(device.get()));
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllDevices());
        resource.add(linkTo.withRel("all-devices"));
        return ResponseEntity.ok(resource);

    }

    @PostMapping
    public ResponseEntity createDevice(@RequestBody @Valid DeviceEntity device) {

        Optional<DeviceEntity> deviceLookup = service.findDevice(device.getSerialNumber());
        if (deviceLookup.isPresent()) {
            throw new ExceptionType.DeviceAlreadyExistsException(device.getSerialNumber());
        }
        DeviceEntity savedDevice = service.save(device);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().
                path("/{serialNumber}").buildAndExpand(savedDevice.getSerialNumber()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{serialNumber}")
    @Transactional
    public ResponseEntity<String> deleteDevice(@PathVariable String serialNumber) {
        Long response = service.deleteDevice(serialNumber);
        if (response != 1) {
            throw new ExceptionType.DeviceNotFoundException(serialNumber);
        }
        return ResponseEntity.ok("Device with serial number " + serialNumber + " Deleted");
    }

    @PutMapping("/{serialNumber}")
    @Transactional
    public ResponseEntity<DeviceDTO> updateDevice(@PathVariable String serialNumber, @RequestBody DeviceDTO device) {

        Optional<DeviceEntity> deviceLookup = service.findDevice(device.getSerialNumber());
        if (deviceLookup.isEmpty()) {
            throw new ExceptionType.DeviceNotFoundException(serialNumber);
        }

        if (!serialNumber.equals(device.getSerialNumber())) {
            throw new ExceptionType.DevicePrimaryKeyRestriction(serialNumber);
        }
        DeviceEntity deviceEntity = service.save(deviceMapper.toDeviceEntity(device));
        return new ResponseEntity<>(deviceMapper.toDeviceDTO(deviceEntity), HttpStatus.OK);

    }

}