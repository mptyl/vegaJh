package it.tylconsulting.vega.util.extjshelpers.protocol;

public class ExtLoadResponse<T> {
    boolean success=true;
    T data;
    String errorMessage;

    public ExtLoadResponse(boolean success, T data, String errorMessage) {
        this.success = success;
        this.data = data;
        this.errorMessage = errorMessage;
    }

    public ExtLoadResponse() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
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
