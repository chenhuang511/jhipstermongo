package vn.psvm.jhipstermongo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TimingLog.
 */

@Document(collection = "timing_log")
public class TimingLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("datetime_log")
    private String datetimeLog;

    @NotNull
    @Field("switcher_id")
    private Long switcherId;

    @NotNull
    @Field("device_id")
    private Long deviceId;

    @NotNull
    @Field("start_time")
    private String startTime;

    @NotNull
    @Field("duration")
    private String duration;

    @NotNull
    @Field("uid")
    @Indexed(unique = true)
    private String uid;

    @Field("command")
    private Integer command;

    @Field("value_timing")
    private Integer valueTiming;

    @Field("value_loop")
    private String valueLoop;

    @Field("onofflog_id")
    private Long onofflogId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDatetimeLog() {
        return datetimeLog;
    }

    public TimingLog datetimeLog(String datetimeLog) {
        this.datetimeLog = datetimeLog;
        return this;
    }

    public void setDatetimeLog(String datetimeLog) {
        this.datetimeLog = datetimeLog;
    }

    public Long getSwitcherId() {
        return switcherId;
    }

    public TimingLog switcherId(Long switcherId) {
        this.switcherId = switcherId;
        return this;
    }

    public void setSwitcherId(Long switcherId) {
        this.switcherId = switcherId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public TimingLog deviceId(Long deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getStartTime() {
        return startTime;
    }

    public TimingLog startTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDuration() {
        return duration;
    }

    public TimingLog duration(String duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getUid() {
        return uid;
    }

    public TimingLog uid(String uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getCommand() {
        return command;
    }

    public TimingLog command(Integer command) {
        this.command = command;
        return this;
    }

    public void setCommand(Integer command) {
        this.command = command;
    }

    public Integer getValueTiming() {
        return valueTiming;
    }

    public TimingLog valueTiming(Integer valueTiming) {
        this.valueTiming = valueTiming;
        return this;
    }

    public void setValueTiming(Integer valueTiming) {
        this.valueTiming = valueTiming;
    }

    public String getValueLoop() {
        return valueLoop;
    }

    public TimingLog valueLoop(String valueLoop) {
        this.valueLoop = valueLoop;
        return this;
    }

    public void setValueLoop(String valueLoop) {
        this.valueLoop = valueLoop;
    }

    public Long getOnofflogId() {
        return onofflogId;
    }

    public TimingLog onofflogId(Long onofflogId) {
        this.onofflogId = onofflogId;
        return this;
    }

    public void setOnofflogId(Long onofflogId) {
        this.onofflogId = onofflogId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TimingLog timingLog = (TimingLog) o;
        if (timingLog.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, timingLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TimingLog{" +
            "id=" + id +
            ", datetimeLog='" + datetimeLog + "'" +
            ", switcherId='" + switcherId + "'" +
            ", deviceId='" + deviceId + "'" +
            ", startTime='" + startTime + "'" +
            ", duration='" + duration + "'" +
            ", uid='" + uid + "'" +
            ", command='" + command + "'" +
            ", valueTiming='" + valueTiming + "'" +
            ", valueLoop='" + valueLoop + "'" +
            ", onofflogId='" + onofflogId + "'" +
            '}';
    }
}
