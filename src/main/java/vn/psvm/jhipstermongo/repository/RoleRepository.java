package vn.psvm.jhipstermongo.repository;

import vn.psvm.jhipstermongo.domain.Role;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Role entity.
 */
@SuppressWarnings("unused")
public interface RoleRepository extends MongoRepository<Role,String> {

}
