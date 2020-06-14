package com.ibgdn.vhr.model;

/**
 * 响应实体类
 */
public class ResponseBean {
    private Integer status;
    private String message;
    private Object object;

    public static ResponseBean ok(String message) {
        return new ResponseBean(200, message, null);
    }

    public static ResponseBean ok(String message, Object object) {
        return new ResponseBean(200, message, object);
    }

    public static ResponseBean error(String message) {
        return new ResponseBean(500, message, null);
    }

    public static ResponseBean error(String message, Object object) {
        return new ResponseBean(500, message, object);
    }

    public ResponseBean() {
    }

    public ResponseBean(Integer status, String message, Object object) {
        this.status = status;
        this.message = message;
        this.object = object;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "ResponseBean{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", object=" + object +
                '}';
    }
}
