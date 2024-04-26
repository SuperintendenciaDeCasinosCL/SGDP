package cl.gob.scj.sgdp.service;

import java.util.List;

import cl.gob.scj.sgdp.dto.*;
import cl.gob.scj.sgdp.exception.SgdpException;
import org.springframework.http.ResponseEntity;


public interface ApiDocDigitalService {
    List<EntidadDTO> getEntidades() throws SgdpException;

    List<UsuarioEntidadDTO> getUsuarioEntidad(Integer idEntidad) throws SgdpException;

    DocumentoIngresoResponseDTO ingresarDocumentoApiDocDig(DocumentoIngresoRequestDTO documentoIngresoRequest)throws SgdpException;

    EntidadDTO getEntidadToken() throws SgdpException;

    ResponseEntity<AcuseReciboResponseDTO> acuseRecibo(Long idDocumento);

    ResponseEntity<DevolverResponseDTO> devolver(Long idDocumento, String motivoRechazo);

}
