package vn.psvm.jhipstermongo.repository;

import vn.psvm.jhipstermongo.domain.Permission;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Permission entity.
 */
@SuppressWarnings("unused")
public interface PermissionRepository extends MongoRepository<Permission,String> {

}
