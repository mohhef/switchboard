package com.switchboard.app.repository;

import com.switchboard.app.entity.DecoderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DecoderRepository extends JpaRepository<DecoderEntity, String> {

    List<DecoderEntity> findAll();
    DecoderEntity save(DecoderEntity decoderEntity);
    Optional<DecoderEntity> findDecoderBySerialNumber(String serialNumber);
    Long deleteDecoderEntityBySerialNumber(String serialNumber);

}
