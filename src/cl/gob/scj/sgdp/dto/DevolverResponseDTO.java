package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class DevolverResponseDTO  implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3307696383640971750L;

    private String count;
    private String message;
    private String status;

    @Override
    public String toString() {
        return "AcuseReciboResponse: {" +
                " count='" + getCount() + "'" +
                ", message='" + getMessage() + "'" +
                ", status='" + getStatus() + "'" +
                "}";
    }

    public String getCount() {
        return this.count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
