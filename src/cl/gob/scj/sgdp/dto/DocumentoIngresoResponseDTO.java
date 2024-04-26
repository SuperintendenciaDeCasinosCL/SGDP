package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class DocumentoIngresoResponseDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private ResultDTO result;

    private String count;

    private String message;

    private String status;

    public ResultDTO getResult() {
        return result;
    }

    public void setResult(ResultDTO result) {
        this.result = result;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DocumentoIngresoResponse [result = " + result + ", count = " + count + ", message = " + message + ", status = "
                + status + "]";
    }

}