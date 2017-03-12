package vn.psvm.jhipstermongo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Customer.
 */

@Document(collection = "customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("email")
    @Indexed(unique = true)
    private String email;

    @Field("password")
    private String password;

    @Field("created_date")
    private String createdDate;

    @Field("image")
    private String image;

    @Field("social_id")
    private String socialId;

    @Field("social_type")
    private String socialType;

    @Field("phone")
    private String phone;

    @Field("device_id")
    private String deviceId;

    @Field("area_id")
    private String areaId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public Customer email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public Customer password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public Customer createdDate(String createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getImage() {
        return image;
    }

    public Customer image(String image) {
        this.image = image;
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSocialId() {
        return socialId;
    }

    public Customer socialId(String socialId) {
        this.socialId = socialId;
        return this;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public String getSocialType() {
        return socialType;
    }

    public Customer socialType(String socialType) {
        this.socialType = socialType;
        return this;
    }

    public void setSocialType(String socialType) {
        this.socialType = socialType;
    }

    public String getPhone() {
        return phone;
    }

    public Customer phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public Customer deviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAreaId() {
        return areaId;
    }

    public Customer areaId(String areaId) {
        this.areaId = areaId;
        return this;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Customer customer = (Customer) o;
        if (customer.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Customer{" +
            "id=" + id +
            ", email='" + email + "'" +
            ", password='" + password + "'" +
            ", createdDate='" + createdDate + "'" +
            ", image='" + image + "'" +
            ", socialId='" + socialId + "'" +
            ", socialType='" + socialType + "'" +
            ", phone='" + phone + "'" +
            ", deviceId='" + deviceId + "'" +
            ", areaId='" + areaId + "'" +
            '}';
    }
}
