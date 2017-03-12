package vn.psvm.jhipstermongo.repository.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.psvm.jhipstermongo.domain.Permission;
import vn.psvm.jhipstermongo.repository.PermissionRepository;

/**
 * Created by chen on 3/12/17.
 */
public interface PermissionDao extends PermissionRepository {
    Page<Permission> findByNameLike(String name, Pageable pageable);
}
