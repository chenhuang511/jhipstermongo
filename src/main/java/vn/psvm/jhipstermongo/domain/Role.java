package vn.psvm.jhipstermongo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Role.
 */

@Document(collection = "role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("customer_id")
    private Long customerId;

    @NotNull
    @Field("switcher_id")
    private Long switcherId;

    @Field("permission_id")
    private String permissionId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Role customerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getSwitcherId() {
        return switcherId;
    }

    public Role switcherId(Long switcherId) {
        this.switcherId = switcherId;
        return this;
    }

    public void setSwitcherId(Long switcherId) {
        this.switcherId = switcherId;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public Role permissionId(String permissionId) {
        this.permissionId = permissionId;
        return this;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Role role = (Role) o;
        if (role.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Role{" +
            "id=" + id +
            ", customerId='" + customerId + "'" +
            ", switcherId='" + switcherId + "'" +
            ", permissionId='" + permissionId + "'" +
            '}';
    }
}
