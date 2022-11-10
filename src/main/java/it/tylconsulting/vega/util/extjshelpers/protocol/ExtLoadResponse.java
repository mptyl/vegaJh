package it.tylconsulting.vega.util.extjshelpers.protocol;

public class ExtLoadResponse {
    boolean success=true;
    Object data;
    String errorMessage;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "ExtLoadResponse{" +
            "success=" + success +
            ", data=" + data +
            ", errorMessage='" + errorMessage + '\'' +
            '}';
    }
}
