package vn.psvm.jhipstermongo.utils;

/**
 * Created by chen on 3/12/17.
 */
public class BaseResponse {
    private int status;

    private String message;

    private Object data;

    private Object optional;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getOptional() {
        return optional;
    }

    public void setOptional(Object optional) {
        this.optional = optional;
    }

    public BaseResponse(int status, String message, Object data, Object optional) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.optional = optional;
    }
}
