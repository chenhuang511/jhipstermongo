package vn.psvm.jhipstermongo.repository.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import vn.psvm.jhipstermongo.domain.Customer;
import vn.psvm.jhipstermongo.repository.CustomerRepository;

/**
 * Created by chen on 3/12/17.
 */
public interface CustomerDao extends MongoRepository<Customer, String> {
    @Query("{email: '?0'}")
    Customer findByEmail(String email);

    @Query("{email: {$regex: '?0'}, password: '?1'}")
    Page<Customer> findByPasswordAndEmailLike(String email, String password, Pageable pageable);
}
