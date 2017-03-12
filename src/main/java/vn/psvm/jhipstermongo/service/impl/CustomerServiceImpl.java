package vn.psvm.jhipstermongo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.psvm.jhipstermongo.domain.Customer;
import vn.psvm.jhipstermongo.repository.dao.CustomerDao;
import vn.psvm.jhipstermongo.service.CustomerService;

/**
 * Created by chen on 3/12/17.
 */
@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Override
    public Customer getByEmail(String email) throws Exception {
        return customerDao.findByEmail(email);
    }

    public Page<Customer> getByPasswordAndEmailLike(String email, String password, Pageable pageable) throws Exception {
        return customerDao.findByPasswordAndEmailLike(email, password, pageable);
    }
}
