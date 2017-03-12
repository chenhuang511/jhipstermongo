package vn.psvm.jhipstermongo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.psvm.jhipstermongo.domain.Customer;

/**
 * Created by chen on 3/12/17.
 */
public interface CustomerService {
    public Customer getByEmail(String email) throws Exception;

    public Page<Customer> getByPasswordAndEmailLike(String email, String password, Pageable pageable) throws Exception;
}
