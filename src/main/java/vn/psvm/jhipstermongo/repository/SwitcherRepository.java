package vn.psvm.jhipstermongo.repository;

import vn.psvm.jhipstermongo.domain.Switcher;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Switcher entity.
 */
@SuppressWarnings("unused")
public interface SwitcherRepository extends MongoRepository<Switcher,String> {

}
