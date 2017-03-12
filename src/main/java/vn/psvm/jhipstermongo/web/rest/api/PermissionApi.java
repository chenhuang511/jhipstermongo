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
import vn.psvm.jhipstermongo.domain.Permission;
import vn.psvm.jhipstermongo.service.PermissionService;
import vn.psvm.jhipstermongo.utils.BaseResponse;

/**
 * Created by chen on 3/12/17.
 */
@RestController
@RequestMapping("/api")
public class PermissionApi {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/permissions/name/{name}")
    public ResponseEntity<BaseResponse> getByNameLike(@PathVariable String name, @ApiParam Pageable pageable) {
        try {
            Page<Permission> permissions = permissionService.getByNameLike(name, pageable);
            BaseResponse response = new BaseResponse(1, "Success", permissions.getContent(), null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            BaseResponse response = new BaseResponse(0, e.getMessage(), null, null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
