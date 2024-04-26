package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class DevolverRequest implements Serializable {
    private String motivoRechazo;

    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    public void setMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }

    @Override
    public String toString() {
        return "DevolverRequest{" +
                "motivoRechazo='" + motivoRechazo + '\'' +
                '}';
    }
}
