package entity;

import java.io.Serializable;

public class Result implements Serializable {

    private boolean success;
    private String message;

    public Result() {
    }

    public Result(boolean status, String message) {
        this.success = status;
        this.message = message;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
