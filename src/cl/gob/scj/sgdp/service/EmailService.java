package cl.gob.scj.sgdp.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.InstanciaDeTareaDTO;
import cl.gob.scj.sgdp.dto.RespuestaMailDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.model.InstanciaDeProceso;
import cl.gob.scj.sgdp.model.InstanciaDeTarea;

@Service
public interface EmailService {
	
	RespuestaMailDTO enviarMail(Usuario usuario, InstanciaDeTarea instanciaDeTarea, List<String> listaDeUsuariosAsignados, String comentario, InstanciaDeTarea instanciaDeTareaDeDestino) throws SgdpException;

	RespuestaMailDTO enviarMailConAsunto(Usuario usuario, InstanciaDeTarea instanciaDeTarea,
										List<String> listaDeUsuariosAsignados, String cuerpo, String asunto) throws SgdpException;
	
	void enviarMailNotificacionPorTarea(InstanciaDeTareaDTO instanciaDeTareaDTO, String to, Usuario usuario) throws SgdpException;
	
	void enviarCorreosConAdjuntosAListaDeDistribucion (List<String> correosDeDistribucion,
			Set<String> idArchivosADistribuir,
			String idExpediente,
			String nombreExpediente,
			long idInstanciaDeTarea,
			Usuario usuario,
			String asunto,
			List<String> nombreArchivosADistribuir) throws Exception;
	
	void enviarCorreoeX (String from, String to, String mailSmtpHost, String asunto, String mensaje, String tipoDeContenido) throws Exception;
	
	void enviarMailNotificacionPorNumeroDeDocumento(Object o, Usuario usuario, InstanciaDeProceso instanciaDeProceso, boolean anula) throws SgdpException;
	
	void enviarCorreo (String from, String to, String mailSmtpHost, String asunto, String mensaje, String tipoDeContenido);
	
}
