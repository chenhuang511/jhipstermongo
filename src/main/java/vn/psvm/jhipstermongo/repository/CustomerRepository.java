package vn.psvm.jhipstermongo.repository;

import vn.psvm.jhipstermongo.domain.Customer;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Customer entity.
 */
@SuppressWarnings("unused")
public interface CustomerRepository extends MongoRepository<Customer,String> {

}
