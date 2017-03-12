package vn.psvm.jhipstermongo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Device.
 */

@Document(collection = "device")
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("serial")
    @Indexed(unique = true)
    private String serial;

    @NotNull
    @Field("type")
    private String type;

    @NotNull
    @Field("count_switch")
    private Integer countSwitch;

    @NotNull
    @Field("active_code")
    private String activeCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSerial() {
        return serial;
    }

    public Device serial(String serial) {
        this.serial = serial;
        return this;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getType() {
        return type;
    }

    public Device type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCountSwitch() {
        return countSwitch;
    }

    public Device countSwitch(Integer countSwitch) {
        this.countSwitch = countSwitch;
        return this;
    }

    public void setCountSwitch(Integer countSwitch) {
        this.countSwitch = countSwitch;
    }

    public String getActiveCode() {
        return activeCode;
    }

    public Device activeCode(String activeCode) {
        this.activeCode = activeCode;
        return this;
    }

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Device device = (Device) o;
        if (device.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, device.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Device{" +
            "id=" + id +
            ", serial='" + serial + "'" +
            ", type='" + type + "'" +
            ", countSwitch='" + countSwitch + "'" +
            ", activeCode='" + activeCode + "'" +
            '}';
    }
}
