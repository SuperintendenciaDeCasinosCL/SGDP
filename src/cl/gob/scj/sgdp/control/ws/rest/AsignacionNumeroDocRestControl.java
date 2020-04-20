package cl.gob.scj.sgdp.control.ws.rest;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.rest.AsignacionesNumerosDocDto;
import cl.gob.scj.sgdp.dto.rest.BorraRegistroDocumentoRequestRest;
import cl.gob.scj.sgdp.dto.rest.BorraRegistroDocumentoResponseRest;
import cl.gob.scj.sgdp.dto.rest.GeneraRegistroDocumentoRequestRest;
import cl.gob.scj.sgdp.dto.rest.GeneraRegistroDocumentoResponseRest;
import cl.gob.scj.sgdp.dto.rest.RespuestaAsignacionesNumerosDocDto;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.exception.SgdpExceptionValidaTareaEnBE;
import cl.gob.scj.sgdp.service.RegistroDocumentoService;
import cl.gob.scj.sgdp.ws.rest.service.AsignacionNumeroDocService;

@RestController
@RequestMapping("/serviciosDoc")
public class AsignacionNumeroDocRestControl {

	private static final Logger log = Logger.getLogger(AsignacionNumeroDocRestControl.class);

	@Autowired
	AsignacionNumeroDocService asignacionNumeroDocService;

	@Autowired
	RegistroDocumentoService registroDocumentoService;

	@RequestMapping(value = "GuardarAsignacionDocumento", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody RespuestaAsignacionesNumerosDocDto guardarAsignacionDocumento(
			@RequestBody AsignacionesNumerosDocDto asignacionesNumerosDocDto) {

		log.info("Entro al control");
		log.info(asignacionesNumerosDocDto.toString());

		RespuestaAsignacionesNumerosDocDto respuestaAsignacionesNumerosDocDto = new RespuestaAsignacionesNumerosDocDto();

		// 0 = ingresar
		// 1 = modificar
		if (asignacionesNumerosDocDto.getAccion() != 0 && asignacionesNumerosDocDto.getAccion() != 1) {
			respuestaAsignacionesNumerosDocDto.setRespuesta("AccionNoValida");
			return respuestaAsignacionesNumerosDocDto;
		} else if (asignacionesNumerosDocDto.getAccion() == 0) {
			respuestaAsignacionesNumerosDocDto = asignacionNumeroDocService
					.guardarAsignacionDocumento(asignacionesNumerosDocDto);
		} else if (asignacionesNumerosDocDto.getAccion() == 1) {
			try {
				respuestaAsignacionesNumerosDocDto = asignacionNumeroDocService
						.modicarAsignacionDocumento(asignacionesNumerosDocDto);
			} catch (SgdpException | SgdpExceptionValidaTareaEnBE e) {
				respuestaAsignacionesNumerosDocDto.setRespuesta("Error al procesar... " + e.getMessage());
			}
		}

		log.info(respuestaAsignacionesNumerosDocDto.toString());

		return respuestaAsignacionesNumerosDocDto;

	}

	@RequestMapping(value = "cambiarStatusFirmaAvanzada", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody RespuestaAsignacionesNumerosDocDto cambiarStatusFirmaAvanzada(
			@RequestBody AsignacionesNumerosDocDto asignacionesNumerosDocDto) {

		log.info("Entro al control");
		log.info(asignacionesNumerosDocDto.toString());

		RespuestaAsignacionesNumerosDocDto respuestaAsignacionesNumerosDocDto = new RespuestaAsignacionesNumerosDocDto();

		try {
			respuestaAsignacionesNumerosDocDto = asignacionNumeroDocService
					.cambiarStatusFirmaAvanzada(asignacionesNumerosDocDto);
		} catch (SgdpException | SgdpExceptionValidaTareaEnBE e) {
			respuestaAsignacionesNumerosDocDto.setRespuesta("Error al procesar... " + e.getMessage());
		}

		log.info(respuestaAsignacionesNumerosDocDto.toString());
		return respuestaAsignacionesNumerosDocDto;

	}

	@RequestMapping(value = "GuardarAsignacionDocumentoXML", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody AsignacionesNumerosDocDto guardarAsignacionDocumento() {

		/*
		 * Rescatar el valor de la clase num EstadoAsignacionNumDocType
		 * estadoAsignacionNumDocTypeReservado = EstadoAsignacionNumDocType.RESERVADO;
		 * estadoAsignacionNumDocTypeReservado.getNombreEstadoAsignacionNumDoc()
		 */
		AsignacionesNumerosDocDto asignacionesNumerosDocDto = new AsignacionesNumerosDocDto();
		return asignacionesNumerosDocDto;
	}

	@RequestMapping(value = "generaRegistroDocumento", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody GeneraRegistroDocumentoResponseRest generaRegistroDocumento(
			@RequestBody GeneraRegistroDocumentoRequestRest generaRegistroDocumentoRequestRest, HttpServletResponse response) {
		GeneraRegistroDocumentoResponseRest generaRegistroDocumentoResponseRest;
		try {
			log.info(generaRegistroDocumentoRequestRest.toString());
		    Usuario usuario = new Usuario();
		    usuario.setIdUsuario(generaRegistroDocumentoRequestRest.getUsuario());
			generaRegistroDocumentoResponseRest = registroDocumentoService.generaRegistroDocumento(generaRegistroDocumentoRequestRest, usuario);
			if (generaRegistroDocumentoResponseRest!=null) {
				log.info(generaRegistroDocumentoResponseRest.toString());
			}
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			generaRegistroDocumentoResponseRest = new GeneraRegistroDocumentoResponseRest();
			generaRegistroDocumentoResponseRest.setEstado("1");
			generaRegistroDocumentoResponseRest.setGlosa("ERROR - " + e.getMessage());
		}
		return generaRegistroDocumentoResponseRest;
	}

	@RequestMapping(value = "borraRegistroDocumento", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody BorraRegistroDocumentoResponseRest borraRegistroDocumento(
			@RequestBody BorraRegistroDocumentoRequestRest borraRegistroDocumentoRequestRest, HttpServletResponse response) {
		BorraRegistroDocumentoResponseRest borraRegistroDocumentoResponseRest;
		try {
			log.info(borraRegistroDocumentoRequestRest.toString());					
			borraRegistroDocumentoResponseRest = registroDocumentoService.borraRegistroDocumento(borraRegistroDocumentoRequestRest);
			if (borraRegistroDocumentoResponseRest!=null) {
				log.info(borraRegistroDocumentoResponseRest.toString());
			}
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			borraRegistroDocumentoResponseRest = new BorraRegistroDocumentoResponseRest();
			borraRegistroDocumentoResponseRest.setEstado("1");
			borraRegistroDocumentoResponseRest.setGlosa("ERROR - " + e.getMessage());
		}
		return borraRegistroDocumentoResponseRest;
	}

}
