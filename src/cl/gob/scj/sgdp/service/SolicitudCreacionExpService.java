package cl.gob.scj.sgdp.service;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.ArchivosSolCreaExpDTO;
import cl.gob.scj.sgdp.dto.SolicitudCreacionExpDTO;
import cl.gob.scj.sgdp.exception.SgdpException;

@Service
public interface SolicitudCreacionExpService {
	List<SolicitudCreacionExpDTO> getSolicitudesCreaExpSolicitadasPorOAsignadasA(SolicitudCreacionExpDTO solicitudCreacionExpDTO, Usuario usuario);

	void nuevaSolicitudCreacionExp(Usuario usuario, SolicitudCreacionExpDTO solicitudCreacionExpDTO);

	void cargaIdsUsuariosDestinatarios(SolicitudCreacionExpDTO solicitudCreacionExpDTO);

	void rechazaSolicitudCreacionExp(Usuario usuario, SolicitudCreacionExpDTO solicitudCreacionExpDTO);

	void creaExpedienteSolicitudCreacionExp(Usuario usuario, SolicitudCreacionExpDTO solicitudCreacionExpDTO) throws SgdpException, Exception;

	List<SolicitudCreacionExpDTO> getSolicitudesCreaExpFinalizadas(SolicitudCreacionExpDTO solicitudCreacionExpDTO);

	int getTotalSolicitudesCreaExpFinalizadas(SolicitudCreacionExpDTO solicitudCreacionExpDTO);

	List<SolicitudCreacionExpDTO> getSolicitudesCreaExpPorParamBusqueda(String parametroDeBusqueda, List<SolicitudCreacionExpDTO> solicitudesCreaExpPorParamBusqueda) throws ParseException, UnsupportedEncodingException;

	List<ArchivosSolCreaExpDTO> getArchivosCrearExp(ArchivosSolCreaExpDTO archivosSolCreaExpDTO, Usuario usuario) throws Exception;

	SolicitudCreacionExpDTO getSolicitudCreacionExpDTOByID(long idSolicitudCreacionExp);
}
