package vn.psvm.jhipstermongo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.psvm.jhipstermongo.domain.Permission;
import vn.psvm.jhipstermongo.repository.dao.PermissionDao;
import vn.psvm.jhipstermongo.service.PermissionService;

/**
 * Created by chen on 3/12/17.
 */
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    public Page<Permission> getByNameLike(String name, Pageable pageable) throws Exception {
        return permissionDao.findByNameLike(name, pageable);
    }
}
