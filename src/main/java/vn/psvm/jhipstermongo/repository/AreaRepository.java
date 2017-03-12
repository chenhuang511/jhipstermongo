package vn.psvm.jhipstermongo.repository;

import vn.psvm.jhipstermongo.domain.Area;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Area entity.
 */
@SuppressWarnings("unused")
public interface AreaRepository extends MongoRepository<Area,String> {

}
