package vn.psvm.jhipstermongo.repository;

import vn.psvm.jhipstermongo.domain.Device;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Device entity.
 */
@SuppressWarnings("unused")
public interface DeviceRepository extends MongoRepository<Device,String> {

}
