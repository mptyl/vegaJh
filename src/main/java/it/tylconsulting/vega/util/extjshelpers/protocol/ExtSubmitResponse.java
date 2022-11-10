package it.tylconsulting.vega.util.extjshelpers.protocol;

public class ExtSubmitResponse {
    Boolean success=true;
    String msg;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ExtSubmitResponse{" +
            "success=" + success +
            ", msg='" + msg + '\'' +
            '}';
    }
}
