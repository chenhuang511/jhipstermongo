package vn.psvm.jhipstermongo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.psvm.jhipstermongo.domain.Permission;

/**
 * Created by chen on 3/12/17.
 */
public interface PermissionService {
    public Page<Permission> getByNameLike(String name, Pageable pageable) throws Exception;
}

