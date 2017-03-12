package vn.psvm.jhipstermongo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Switcher.
 */

@Document(collection = "switcher")
public class Switcher implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("device_id")
    private Long deviceId;

    @Field("name")
    private String name;

    @NotNull
    @Field("number")
    @Indexed(unique = true)
    private Integer number;

    @Field("status")
    private Integer status;

    @Field("counter")
    private Long counter;

    @Field("count_timing")
    private Long countTiming;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public Switcher deviceId(Long deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getName() {
        return name;
    }

    public Switcher name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public Switcher number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getStatus() {
        return status;
    }

    public Switcher status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCounter() {
        return counter;
    }

    public Switcher counter(Long counter) {
        this.counter = counter;
        return this;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    public Long getCountTiming() {
        return countTiming;
    }

    public Switcher countTiming(Long countTiming) {
        this.countTiming = countTiming;
        return this;
    }

    public void setCountTiming(Long countTiming) {
        this.countTiming = countTiming;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Switcher switcher = (Switcher) o;
        if (switcher.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, switcher.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Switcher{" +
            "id=" + id +
            ", deviceId='" + deviceId + "'" +
            ", name='" + name + "'" +
            ", number='" + number + "'" +
            ", status='" + status + "'" +
            ", counter='" + counter + "'" +
            ", countTiming='" + countTiming + "'" +
            '}';
    }
}
