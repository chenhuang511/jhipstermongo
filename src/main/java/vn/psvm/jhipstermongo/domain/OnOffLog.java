package vn.psvm.jhipstermongo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A OnOffLog.
 */

@Document(collection = "on_off_log")
public class OnOffLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("area_id")
    private Long areaId;

    @NotNull
    @Field("device_id")
    private Long deviceId;

    @NotNull
    @Field("switch_id")
    private Long switchId;

    @NotNull
    @Field("customer_id")
    private Long customerId;

    @NotNull
    @Field("datetime_log")
    private String datetimeLog;

    @Field("start_time")
    private String startTime;

    @Field("finish_time")
    private String finishTime;

    @NotNull
    @Field("uid")
    @Indexed(unique = true)
    private String uid;

    @Field("status")
    private Integer status;

    @Field("type")
    private Integer type;

    @NotNull
    @Field("command")
    private Integer command;

    @Field("add_info")
    private String addInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getAreaId() {
        return areaId;
    }

    public OnOffLog areaId(Long areaId) {
        this.areaId = areaId;
        return this;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public OnOffLog deviceId(Long deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getSwitchId() {
        return switchId;
    }

    public OnOffLog switchId(Long switchId) {
        this.switchId = switchId;
        return this;
    }

    public void setSwitchId(Long switchId) {
        this.switchId = switchId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public OnOffLog customerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getDatetimeLog() {
        return datetimeLog;
    }

    public OnOffLog datetimeLog(String datetimeLog) {
        this.datetimeLog = datetimeLog;
        return this;
    }

    public void setDatetimeLog(String datetimeLog) {
        this.datetimeLog = datetimeLog;
    }

    public String getStartTime() {
        return startTime;
    }

    public OnOffLog startTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public OnOffLog finishTime(String finishTime) {
        this.finishTime = finishTime;
        return this;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getUid() {
        return uid;
    }

    public OnOffLog uid(String uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getStatus() {
        return status;
    }

    public OnOffLog status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public OnOffLog type(Integer type) {
        this.type = type;
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCommand() {
        return command;
    }

    public OnOffLog command(Integer command) {
        this.command = command;
        return this;
    }

    public void setCommand(Integer command) {
        this.command = command;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public OnOffLog addInfo(String addInfo) {
        this.addInfo = addInfo;
        return this;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OnOffLog onOffLog = (OnOffLog) o;
        if (onOffLog.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, onOffLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OnOffLog{" +
            "id=" + id +
            ", areaId='" + areaId + "'" +
            ", deviceId='" + deviceId + "'" +
            ", switchId='" + switchId + "'" +
            ", customerId='" + customerId + "'" +
            ", datetimeLog='" + datetimeLog + "'" +
            ", startTime='" + startTime + "'" +
            ", finishTime='" + finishTime + "'" +
            ", uid='" + uid + "'" +
            ", status='" + status + "'" +
            ", type='" + type + "'" +
            ", command='" + command + "'" +
            ", addInfo='" + addInfo + "'" +
            '}';
    }
}
