package vn.psvm.jhipstermongo.web.rest.api;

import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.psvm.jhipstermongo.domain.Customer;
import vn.psvm.jhipstermongo.repository.CustomerRepository;
import vn.psvm.jhipstermongo.service.CustomerService;
import vn.psvm.jhipstermongo.utils.BaseResponse;

/**
 * Created by chen on 3/12/17.
 */
@RestController
@RequestMapping("/api")
public class CustomerApi {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/customers/email/{email:.+}")
    public ResponseEntity<BaseResponse> getByEmail(@PathVariable String email) {
        try {
            Customer customer = customerService.getByEmail(email);
            BaseResponse response = new BaseResponse(1, "Success", customer, null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            BaseResponse response = new BaseResponse(0, e.getMessage(), null, null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/customers/email-password/{email:.+}/{password}")
    public ResponseEntity<BaseResponse> getByPasswordAndNameLike(@PathVariable String email,
                                                                 @PathVariable String password,
                                                                 @ApiParam Pageable pageable) {
        try {
            Page<Customer> customers = customerService.getByPasswordAndEmailLike(email, password, pageable);
            BaseResponse response = new BaseResponse(1, "Success", customers.getContent(), null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            BaseResponse response = new BaseResponse(0, e.getMessage(), null, null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
