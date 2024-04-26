package cl.gob.scj.sgdp.service;

import cl.gob.scj.sgdp.exception.SgdpException;
import org.springframework.stereotype.Service;

@Service
public interface ArhivoSolicitudCreacionExpService {

    void actualizaConfidencialidad(long idArchivosSolCreaExp, boolean confidencialidad) throws SgdpException;

}

