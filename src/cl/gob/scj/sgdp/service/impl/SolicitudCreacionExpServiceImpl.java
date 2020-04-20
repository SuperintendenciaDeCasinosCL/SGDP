package cl.gob.scj.sgdp.service.impl;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dao.AutorDao;
import cl.gob.scj.sgdp.dao.EstadoSolicitudCreacionExpDao;
import cl.gob.scj.sgdp.dao.HistoricoSolicitudCreacionExpDao;
import cl.gob.scj.sgdp.dao.InstanciaDeProcesoDao;
import cl.gob.scj.sgdp.dao.ProcesoDao;
import cl.gob.scj.sgdp.dao.SolicitudCreacionExpDao;
import cl.gob.scj.sgdp.dao.UsuarioResponsabilidadDao;
import cl.gob.scj.sgdp.dto.AutorDTO;
import cl.gob.scj.sgdp.dto.ExpedienteDTO;
import cl.gob.scj.sgdp.dto.ParametroDTO;
import cl.gob.scj.sgdp.dto.SolicitudCreacionExpDTO;
import cl.gob.scj.sgdp.dto.UsuarioResponsabilidadDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.model.Autor;
import cl.gob.scj.sgdp.model.EstadoSolicitudCreacionExp;
import cl.gob.scj.sgdp.model.HistoricoSolicitudCreacionExp;
import cl.gob.scj.sgdp.model.InstanciaDeProceso;
import cl.gob.scj.sgdp.model.Proceso;
import cl.gob.scj.sgdp.model.SolicitudCreacionExp;
import cl.gob.scj.sgdp.model.UsuarioResponsabilidad;
import cl.gob.scj.sgdp.service.CrearExpedienteService;
import cl.gob.scj.sgdp.service.EmailService;
import cl.gob.scj.sgdp.service.ParametroService;
import cl.gob.scj.sgdp.service.SolicitudCreacionExpService;
import cl.gob.scj.sgdp.service.UsuarioResponsabilidadService;
import cl.gob.scj.sgdp.tipos.AccionSolCreaExpType;
import cl.gob.scj.sgdp.tipos.EstadoSolicitudCreacionExpType;
import cl.gob.scj.sgdp.util.FechaUtil;
import cl.gob.scj.sgdp.util.SGDPUtil;

@Service
@Transactional(rollbackFor = Throwable.class)
public class SolicitudCreacionExpServiceImpl implements SolicitudCreacionExpService {
	
	private static final Logger log = Logger.getLogger(SolicitudCreacionExpService.class);
	
	@Autowired 
	private SolicitudCreacionExpDao solicitudCreacionExpDao;
	
	@Autowired 
	private UsuarioResponsabilidadService usuarioResponsabilidadService;
	
	@Autowired 
	private ProcesoDao procesoDao;
	
	@Autowired 
	private AutorDao autorDao;
	
	@Autowired
	private EstadoSolicitudCreacionExpDao estadoSolicitudCreacionExpDao;
	
	@Autowired
	private UsuarioResponsabilidadDao usuarioResponsabilidadDao;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private ParametroService parametroService;
	
	@Autowired
	private HistoricoSolicitudCreacionExpDao historicoSolicitudCreacionExpDao;
	
	@Autowired
	private CrearExpedienteService crearExpedienteService;
	    
    @Autowired
    private InstanciaDeProcesoDao instanciaDeProcesoDao;
	
	@Override
	public List<SolicitudCreacionExpDTO> getSolicitudesCreaExpSolicitadasPorOAsignadasA(SolicitudCreacionExpDTO solicitudCreacionExpDTO) {	
		log.debug("Inicio getSolicitudesCreaExpSolicitadasPorOAsignadasA");
		List<SolicitudCreacionExp> solicitudes = solicitudCreacionExpDao.getSolicitudesCreaExpSolicitadasPorOAsignadasA(solicitudCreacionExpDTO);
		List<SolicitudCreacionExpDTO> solicitudesDTO = new ArrayList<SolicitudCreacionExpDTO>();
		for (SolicitudCreacionExp solicitudCreacionExp : solicitudes) {
			SolicitudCreacionExpDTO solicitudDTO = new SolicitudCreacionExpDTO();
			BeanUtils.copyProperties(solicitudCreacionExp, solicitudDTO);
			EstadoSolicitudCreacionExp estadoSolicitudCreacionExp = solicitudCreacionExp.getEstadoSolicitudCreacionExp();
			solicitudDTO.setIdEstadoSolicitudCreacionExp(estadoSolicitudCreacionExp.getIdEstadoSolicitudCreacionExp());
			solicitudDTO.setNombreEstadoSolicitudCreacionExp(estadoSolicitudCreacionExp.getNombreEstadoSolicitudCreacionExp());
			solicitudDTO.setAsuntoMateria(solicitudCreacionExp.getAsuntoMateria());	
			solicitudDTO.setAutoresDTO(solicitudCreacionExpDTO.getAutoresDTO());			
			Proceso proceso = solicitudCreacionExp.getProceso();						
			if (proceso!=null) {
				solicitudDTO.setNombreSubProceso(proceso.getNombreProceso());
				solicitudDTO.setIdProceso(proceso.getIdProceso());
				cargaIdsUsuariosDestinatarios(solicitudDTO);
			}			
			if (solicitudCreacionExp.getAutor()!=null) {
				AutorDTO autorDTO = new AutorDTO(solicitudCreacionExp.getAutor().getIdAutor(), solicitudCreacionExp.getAutor().getNombreAutor());
				solicitudDTO.setAutorDTO(autorDTO);			
				ordenaAutoresEnBaseAIdAutor(solicitudDTO);
			}		
			solicitudesDTO.add(solicitudDTO);
			log.debug(solicitudDTO.toString());			
		}		
		for (SolicitudCreacionExpDTO solicitudCreacionExpDTO2 : solicitudesDTO) {
			log.debug("solicitudCreacionExpDTO2.getIdSolicitudCreacionExp() en servicio: " + solicitudCreacionExpDTO2.getIdSolicitudCreacionExp());
			for (AutorDTO autorDTO: solicitudCreacionExpDTO2.getAutoresDTO()) {
				log.debug(autorDTO.toString());
			}
		}		
		return solicitudesDTO;
	}
	
	private void ordenaAutoresEnBaseAIdAutor(SolicitudCreacionExpDTO solicitudCreacionExpDTO) {
		log.debug("Inicio ordenaAutoresEnBaseAIdAutor");
		log.debug("solicitudCreacionExpDTO.getAutoresDTO(): " + solicitudCreacionExpDTO.getAutoresDTO().size());
		int posicionAutor = solicitudCreacionExpDTO.getAutoresDTO().indexOf(solicitudCreacionExpDTO.getAutorDTO());
		log.debug("posicionAutor: " + posicionAutor);
		List<AutorDTO> autoresDTO = new ArrayList<AutorDTO>();
		if (posicionAutor > 0) {
			AutorDTO autorDTOPrimero = solicitudCreacionExpDTO.getAutoresDTO().get(0);
			AutorDTO autorDTOSolicitud = solicitudCreacionExpDTO.getAutoresDTO().get(posicionAutor);				
			solicitudCreacionExpDTO.getAutoresDTO().set(0, autorDTOSolicitud);
			solicitudCreacionExpDTO.getAutoresDTO().set(posicionAutor, autorDTOPrimero);
		}		
		for (AutorDTO autorDTO: solicitudCreacionExpDTO.getAutoresDTO()) {
			autoresDTO.add(autorDTO);
		}
		solicitudCreacionExpDTO.setAutoresDTO(autoresDTO);
	}	
	
	@Override
	public void cargaIdsUsuariosDestinatarios(SolicitudCreacionExpDTO solicitudCreacionExpDTO) {
		log.debug("Inicio cargaIdsUsuariosDestinatarios");
		List<UsuarioResponsabilidadDTO> usuariosResponsabilidadDTO = usuarioResponsabilidadService.getUsuariosResponsabilidadDTODeSegundasTareaPorIdProceso(solicitudCreacionExpDTO.getIdProceso());
		if (usuariosResponsabilidadDTO!=null && !usuariosResponsabilidadDTO.isEmpty()) {
			UsuarioResponsabilidadDTO usuarioResponsabilidadDTODest = new UsuarioResponsabilidadDTO();
			usuarioResponsabilidadDTODest.setIdUsuario(solicitudCreacionExpDTO.getIdUsuarioDestinatario());
			int posicionIdUsrDest = usuariosResponsabilidadDTO.indexOf(usuarioResponsabilidadDTODest);	
			log.debug("posicionIdUsrDest: " + posicionIdUsrDest);
			List<String> idsUsuariosDestinatarios = new ArrayList<String>();
			if (posicionIdUsrDest > 0) {
				UsuarioResponsabilidadDTO usuariosResponsabilidadDTOPrimero = usuariosResponsabilidadDTO.get(0);
				UsuarioResponsabilidadDTO usuarioResponsabilidadDTO = usuariosResponsabilidadDTO.get(posicionIdUsrDest);	
				usuariosResponsabilidadDTO.set(0, usuarioResponsabilidadDTO);
				usuariosResponsabilidadDTO.set(posicionIdUsrDest, usuariosResponsabilidadDTOPrimero);
			}
			for (UsuarioResponsabilidadDTO usuarioResponsabilidadDTO: usuariosResponsabilidadDTO) {		
				boolean usuarioRestDTOEstaLista = usuarioRestDTOEstaLista(usuarioResponsabilidadDTO, idsUsuariosDestinatarios);
				if (usuarioRestDTOEstaLista == false) {
					solicitudCreacionExpDTO.setIdsUsuariosDestinatarios(idsUsuariosDestinatarios);
				}
				idsUsuariosDestinatarios.add(usuarioResponsabilidadDTO.getIdUsuario());
			}
		}				
	}
	
	private boolean usuarioRestDTOEstaLista(UsuarioResponsabilidadDTO usuarioResponsabilidadDTODest, List<String> idsUsuariosDestinatarios) {
		for (String idUsuario : idsUsuariosDestinatarios) {
			if (usuarioResponsabilidadDTODest!=null && usuarioResponsabilidadDTODest.getIdUsuario()!=null && !usuarioResponsabilidadDTODest.getIdUsuario().isEmpty()
					&& idUsuario.equals(usuarioResponsabilidadDTODest.getIdUsuario())) {
				return true;
			}
		}
		return false;
	}	
	
	@Override
	public void nuevaSolicitudCreacionExp(Usuario usuario, SolicitudCreacionExpDTO solicitudCreacionExpDTO) {
		log.debug("Inicio nuevaSolicitudCreacionExp");
		SolicitudCreacionExp solicitudCreacionExp = new SolicitudCreacionExp();
		solicitudCreacionExp.setAsuntoMateria(solicitudCreacionExpDTO.getAsuntoMateria());
		solicitudCreacionExp.setComentario(solicitudCreacionExpDTO.getComentario());
		Autor autor = autorDao.getAutorPorIdAutor(solicitudCreacionExpDTO.getIdAutor());
		AutorDTO autorDTO = new AutorDTO(autor.getIdAutor(), autor.getNombreAutor());
		solicitudCreacionExpDTO.setAutorDTO(autorDTO);
		solicitudCreacionExp.setAutor(autor);
		EstadoSolicitudCreacionExp estadoSolicitudCreacionExp = estadoSolicitudCreacionExpDao.find(new Long(EstadoSolicitudCreacionExpType.SOLICITADA.getIdEstadoSolicitudCreacionExp()));
		solicitudCreacionExp.setEstadoSolicitudCreacionExp(estadoSolicitudCreacionExp);
		solicitudCreacionExp.setFechaSolicitud(new Date());
		Proceso proceso = procesoDao.getProcesoPorIdProceso(solicitudCreacionExpDTO.getIdProceso());
		solicitudCreacionExpDTO.setNombreSubProceso(proceso.getNombreProceso());
		List<UsuarioResponsabilidad> usuariosResponsabilidad = usuarioResponsabilidadDao.getUsuariosResponsabilidadesDePrimeraTareaExcluyePorIdProceso(proceso.getIdProceso());
		List<UsuarioResponsabilidadDTO> usuariosResponsabilidadDTO = new ArrayList<UsuarioResponsabilidadDTO>();
		for (UsuarioResponsabilidad usuarioResponsabilidad : usuariosResponsabilidad) {
			log.debug(usuarioResponsabilidad);
			
			usuarioResponsabilidad.getIdUsuario();
			
			UsuarioResponsabilidadDTO usuarioResponsabilidadDTO = new UsuarioResponsabilidadDTO();
			BeanUtils.copyProperties(usuarioResponsabilidad, usuarioResponsabilidadDTO);
			usuariosResponsabilidadDTO.add(usuarioResponsabilidadDTO);
		}		
		UsuarioResponsabilidadDTO usuarioResponsabilidadDTO = SGDPUtil.getUsuarioAletarorio(usuariosResponsabilidadDTO);		
		solicitudCreacionExp.setIdUsuarioSolicitante(usuario.getIdUsuario());
		solicitudCreacionExp.setIdUsuarioCreadorExpediente(usuarioResponsabilidadDTO.getIdUsuario());
		solicitudCreacionExp.setIdUsuarioDestinatario(solicitudCreacionExpDTO.getIdUsuarioDestinatario());
		solicitudCreacionExp.setProceso(proceso);
		solicitudCreacionExpDao.insert(solicitudCreacionExp);		
		HistoricoSolicitudCreacionExp historicoSolicitudCreacionExp = new HistoricoSolicitudCreacionExp();
		BeanUtils.copyProperties(solicitudCreacionExp, historicoSolicitudCreacionExp);
		historicoSolicitudCreacionExp.setSolicitudCreacionExp(solicitudCreacionExp);
		historicoSolicitudCreacionExp.setIdUsuario(usuario.getIdUsuario());
		historicoSolicitudCreacionExp.setFecha(new Date());
		historicoSolicitudCreacionExp.setTipoAccion(AccionSolCreaExpType.CREA_SOLICITUD.getNombreAccionSolCreaExp());
		historicoSolicitudCreacionExpDao.insert(historicoSolicitudCreacionExp);	
		solicitudCreacionExpDTO.setIdSolicitudCreacionExp(solicitudCreacionExp.getIdSolicitudCreacionExp());
		solicitudCreacionExpDTO.setIdUsuarioCreadorExpediente(usuarioResponsabilidadDTO.getIdUsuario());
		enviarCorreoNuevaSolicitudCreacionExp(usuario, solicitudCreacionExpDTO);
		solicitudCreacionExpDTO.setGlosa("OK");
		solicitudCreacionExpDTO.setEstado("0");
	}
	
	private void enviarCorreoNuevaSolicitudCreacionExp(Usuario usuario, SolicitudCreacionExpDTO solicitudCreacionExpDTO) {
		log.info(solicitudCreacionExpDTO.toString());
		ParametroDTO parametroMailFrom = parametroService.getParametroPorID(Constantes.ID_PARAM_FROM_CORREO);		
		ParametroDTO parametroMailSmtpHost = parametroService.getParametroPorID(Constantes.ID_PARAM_MAIL_SMTP_HOST);
		ParametroDTO parametroMailAsuntoCreacExp = parametroService.getParametroPorID(Constantes.ID_PARAM_ASUNTO_SOL_CREA_EXP);
		ParametroDTO parametroMailBodyMsgCreacExp = parametroService.getParametroPorID(Constantes.ID_PARAM_BODY_MENSAJE_SOL_CREA_EXP);
		ParametroDTO parametroMailTipoContenido = parametroService.getParametroPorID(Constantes.ID_PARAM_TIPO_CONTENIDO_CORREO);
		ParametroDTO parametroSgdpMail = parametroService.getParametroPorID(Constantes.ID_PARAM_SGDP_CORREO);
		ParametroDTO parametroDTOUrlSGDP = parametroService.getParametroPorID(Constantes.SGDP_URL);
		String asunto = parametroMailAsuntoCreacExp.getValorParametroChar().replace("$rechazo", "").replace("$idSolicitudCreacionExp", ""+solicitudCreacionExpDTO.getIdSolicitudCreacionExp()) ;
        String mensaje = parametroMailBodyMsgCreacExp.getValorParametroChar()
                .replace("$nombreSubProceso", solicitudCreacionExpDTO.getNombreSubProceso())
                .replace("$destinatario", solicitudCreacionExpDTO.getIdUsuarioDestinatario())
                .replace("$asunto", solicitudCreacionExpDTO.getAsuntoMateria())
                .replace("$autor", solicitudCreacionExpDTO.getAutorDTO().getNombreAutor())                
                .replace("$rechazo", "") 
                .replace("<li>Comentario: $comentario</li>", "")
				.replace("$urlSGDP", parametroDTOUrlSGDP.getValorParametroChar());
		String to = solicitudCreacionExpDTO.getIdUsuarioCreadorExpediente() + parametroSgdpMail.getValorParametroChar();
		emailService.enviarCorreo(parametroMailFrom.getValorParametroChar(), to, parametroMailSmtpHost.getValorParametroChar(), 
				asunto, mensaje, parametroMailTipoContenido.getValorParametroChar());
	}
	
	@Override
	public void rechazaSolicitudCreacionExp(Usuario usuario, SolicitudCreacionExpDTO solicitudCreacionExpDTO) {
		log.debug("Inicio rechazaSolicitudCreacionExp");
		SolicitudCreacionExp solicitudCreacionExp = solicitudCreacionExpDao.find(new Long(solicitudCreacionExpDTO.getIdSolicitudCreacionExp()));
		EstadoSolicitudCreacionExp estadoSolicitudCreacionExp = estadoSolicitudCreacionExpDao.find(new Long(EstadoSolicitudCreacionExpType.RECHAZADA.getIdEstadoSolicitudCreacionExp()));
		solicitudCreacionExp.setEstadoSolicitudCreacionExp(estadoSolicitudCreacionExp);
		solicitudCreacionExp.setComentario(solicitudCreacionExpDTO.getComentario());
		solicitudCreacionExp.setFechaAtencion(new Date());
		BeanUtils.copyProperties(solicitudCreacionExp, solicitudCreacionExpDTO);		
		Proceso proceso = solicitudCreacionExp.getProceso();
		if (proceso!=null) {
			solicitudCreacionExpDTO.setNombreSubProceso(proceso.getNombreProceso());
		}
		if (solicitudCreacionExp.getIdUsuarioDestinatario()!=null && !solicitudCreacionExp.getIdUsuarioDestinatario().isEmpty()) {
			solicitudCreacionExpDTO.setIdUsuarioDestinatario(solicitudCreacionExp.getIdUsuarioDestinatario());
		}
		Autor autor = solicitudCreacionExp.getAutor();
		if (autor!=null) {
			AutorDTO autorDTO = new AutorDTO(autor.getIdAutor(), autor.getNombreAutor());
			solicitudCreacionExpDTO.setAutorDTO(autorDTO);			
		}
		HistoricoSolicitudCreacionExp historicoSolicitudCreacionExp = new HistoricoSolicitudCreacionExp();
		BeanUtils.copyProperties(solicitudCreacionExp, historicoSolicitudCreacionExp);
		historicoSolicitudCreacionExp.setSolicitudCreacionExp(solicitudCreacionExp);
		historicoSolicitudCreacionExp.setIdUsuario(usuario.getIdUsuario());
		historicoSolicitudCreacionExp.setFecha(new Date());
		historicoSolicitudCreacionExp.setTipoAccion(AccionSolCreaExpType.RECHAZA_SOLICITUD.getNombreAccionSolCreaExp());
		historicoSolicitudCreacionExpDao.insert(historicoSolicitudCreacionExp);	
		enviarCorreoRechazaSolicitudCreacionExp(usuario, solicitudCreacionExpDTO);
		solicitudCreacionExpDTO.setGlosa("OK");
		solicitudCreacionExpDTO.setEstado("0");
	}
	
	private void enviarCorreoRechazaSolicitudCreacionExp(Usuario usuario, SolicitudCreacionExpDTO solicitudCreacionExpDTO) {
		log.info(solicitudCreacionExpDTO.toString());		
		ParametroDTO parametroMailFrom = parametroService.getParametroPorID(Constantes.ID_PARAM_FROM_CORREO);		
		ParametroDTO parametroMailSmtpHost = parametroService.getParametroPorID(Constantes.ID_PARAM_MAIL_SMTP_HOST);
		ParametroDTO parametroMailAsuntoCreacExp = parametroService.getParametroPorID(Constantes.ID_PARAM_ASUNTO_SOL_CREA_EXP);
		ParametroDTO parametroMailBodyMsgCreacExp = parametroService.getParametroPorID(Constantes.ID_PARAM_BODY_MENSAJE_SOL_CREA_EXP);
		ParametroDTO parametroMailTipoContenido = parametroService.getParametroPorID(Constantes.ID_PARAM_TIPO_CONTENIDO_CORREO);
		ParametroDTO parametroSgdpMail = parametroService.getParametroPorID(Constantes.ID_PARAM_SGDP_CORREO);
		ParametroDTO parametroDTOUrlSGDP = parametroService.getParametroPorID(Constantes.SGDP_URL);
		String asunto = parametroMailAsuntoCreacExp.getValorParametroChar().replace("$rechazo", "rechazo de ").replace("$idSolicitudCreacionExp", ""+solicitudCreacionExpDTO.getIdSolicitudCreacionExp()); 
		String mensaje = parametroMailBodyMsgCreacExp.getValorParametroChar()
                .replace("$nombreSubProceso", solicitudCreacionExpDTO.getNombreSubProceso()!=null && !solicitudCreacionExpDTO.getNombreSubProceso().isEmpty() ? solicitudCreacionExpDTO.getNombreSubProceso() : "Sin subproceso asociado")
                .replace("$destinatario", solicitudCreacionExpDTO.getIdUsuarioDestinatario()!=null &&  !solicitudCreacionExpDTO.getIdUsuarioDestinatario().isEmpty() ? solicitudCreacionExpDTO.getIdUsuarioDestinatario() : "Sin destinatario asociado")
                .replace("$asunto", solicitudCreacionExpDTO.getAsuntoMateria())
                .replace("$autor", solicitudCreacionExpDTO.getAutorDTO()!=null && solicitudCreacionExpDTO.getAutorDTO().getNombreAutor()!=null && !solicitudCreacionExpDTO.getAutorDTO().getNombreAutor().isEmpty() ? solicitudCreacionExpDTO.getAutorDTO().getNombreAutor() :"Sin autor asociado")
                .replace("$rechazo", "el rechazo de ")
                .replace("$comentario", solicitudCreacionExpDTO.getComentario())
				.replace("$urlSGDP", parametroDTOUrlSGDP.getValorParametroChar());
		String to = solicitudCreacionExpDTO.getIdUsuarioSolicitante() + parametroSgdpMail.getValorParametroChar();
		emailService.enviarCorreo(parametroMailFrom.getValorParametroChar(), to, parametroMailSmtpHost.getValorParametroChar(), 
				asunto, mensaje, parametroMailTipoContenido.getValorParametroChar());
	}
	
	@Override
	public void creaExpedienteSolicitudCreacionExp(Usuario usuario, SolicitudCreacionExpDTO solicitudCreacionExpDTO) throws SgdpException {
		log.debug("Inicio creaExpedienteSolicitudCreacionExp");
		SolicitudCreacionExp solicitudCreacionExp = solicitudCreacionExpDao.find(new Long(solicitudCreacionExpDTO.getIdSolicitudCreacionExp()));
		if (solicitudCreacionExp.getEstadoSolicitudCreacionExp().getIdEstadoSolicitudCreacionExp()!=EstadoSolicitudCreacionExpType.SOLICITADA.getIdEstadoSolicitudCreacionExp()
				&& solicitudCreacionExp.getEstadoSolicitudCreacionExp().getIdEstadoSolicitudCreacionExp()!=EstadoSolicitudCreacionExpType.SOLICITADA_EXT.getIdEstadoSolicitudCreacionExp()) {
			solicitudCreacionExpDTO.setGlosa("ERROR");
			solicitudCreacionExpDTO.setEstado("1");
			throw new SgdpException("ERROR la solicitud ya fue tramitada");
		}
		if (solicitudCreacionExp.getIdSolicitudCreacionExp()==null || solicitudCreacionExp.getIdUsuarioCreadorExpediente().isEmpty()) {
			solicitudCreacionExp.setIdUsuarioCreadorExpediente(usuario.getIdUsuario());
		}
		solicitudCreacionExp.setAsuntoMateria(solicitudCreacionExpDTO.getAsuntoMateria());
		solicitudCreacionExp.setIdUsuarioDestinatario(solicitudCreacionExpDTO.getIdUsuarioDestinatario());	
		Proceso proceso = procesoDao.getProcesoPorIdProceso(solicitudCreacionExpDTO.getIdProceso());
		Autor autor = autorDao.getAutorPorIdAutor(solicitudCreacionExpDTO.getIdAutor());
		solicitudCreacionExp.setProceso(proceso);
		solicitudCreacionExp.setAutor(autor);
		if (autor!=null) {
			AutorDTO autorDTO = new AutorDTO(autor.getIdAutor(), autor.getNombreAutor());
			solicitudCreacionExpDTO.setAutorDTO(autorDTO);			
		}
		solicitudCreacionExpDTO.setNombreSubProceso(proceso.getNombreProceso());
		solicitudCreacionExpDTO.setIdUsuarioCreadorExpediente(solicitudCreacionExp.getIdUsuarioCreadorExpediente());
		solicitudCreacionExpDTO.setIdUsuarioSolicitante(solicitudCreacionExp.getIdUsuarioSolicitante());
		EstadoSolicitudCreacionExp estadoSolicitudCreacionExp = estadoSolicitudCreacionExpDao.find(new Long(EstadoSolicitudCreacionExpType.FINALIZADA.getIdEstadoSolicitudCreacionExp()));
		solicitudCreacionExp.setEstadoSolicitudCreacionExp(estadoSolicitudCreacionExp);
		solicitudCreacionExp.setFechaAtencion(new Date());		
		ExpedienteDTO expedienteDTO = new ExpedienteDTO();
		expedienteDTO.setIdProceso(solicitudCreacionExpDTO.getIdProceso());
		expedienteDTO.setIdAutor(solicitudCreacionExpDTO.getIdAutor()+"");
		expedienteDTO.setMateria(solicitudCreacionExpDTO.getAsuntoMateria());
		String respuestaCrearExp = crearExpedienteService.crearExpediente(expedienteDTO, usuario);
		solicitudCreacionExpDTO.setNombreExpediente(expedienteDTO.getNombreExpediente());
		InstanciaDeProceso instanciaDeProceso = instanciaDeProcesoDao.getInstanciaDeProcesoPorNombreExpediente(expedienteDTO.getNombreExpediente());
		solicitudCreacionExp.setInstanciaDeProceso(instanciaDeProceso);
		HistoricoSolicitudCreacionExp historicoSolicitudCreacionExp = new HistoricoSolicitudCreacionExp();
		BeanUtils.copyProperties(solicitudCreacionExp, historicoSolicitudCreacionExp);
		historicoSolicitudCreacionExp.setSolicitudCreacionExp(solicitudCreacionExp);
		historicoSolicitudCreacionExp.setIdUsuario(usuario.getIdUsuario());
		historicoSolicitudCreacionExp.setFecha(new Date());
		historicoSolicitudCreacionExp.setTipoAccion(AccionSolCreaExpType.CREA_EXPEDIENTE.getNombreAccionSolCreaExp());
		historicoSolicitudCreacionExpDao.insert(historicoSolicitudCreacionExp);
		enviarCorreoCreaExpedienteSolicitudCreacionExp(usuario, solicitudCreacionExpDTO);
		if (respuestaCrearExp.equals("OK")) {
			solicitudCreacionExpDTO.setGlosa("OK");
			solicitudCreacionExpDTO.setEstado("0");
		} else {
			solicitudCreacionExpDTO.setGlosa("ERROR");
			solicitudCreacionExpDTO.setEstado("1");
			throw new SgdpException("ERROR al crear expediente en solicitud de creacion de expediente");
		}		
	}
	
	private void enviarCorreoCreaExpedienteSolicitudCreacionExp(Usuario usuario, SolicitudCreacionExpDTO solicitudCreacionExpDTO) {
		log.info(solicitudCreacionExpDTO.toString());		
		ParametroDTO parametroMailFrom = parametroService.getParametroPorID(Constantes.ID_PARAM_FROM_CORREO);		
		ParametroDTO parametroMailSmtpHost = parametroService.getParametroPorID(Constantes.ID_PARAM_MAIL_SMTP_HOST);
		ParametroDTO parametroMailAsuntoCreacExp = parametroService.getParametroPorID(Constantes.ID_PARAM_ASUNTO_CREA_EXP);
		ParametroDTO parametroMailBodyMsgCreacExp = parametroService.getParametroPorID(Constantes.ID_PARAM_BODY_MENSAJE_CREA_EXP);
		ParametroDTO parametroMailTipoContenido = parametroService.getParametroPorID(Constantes.ID_PARAM_TIPO_CONTENIDO_CORREO);
		ParametroDTO parametroSgdpMail = parametroService.getParametroPorID(Constantes.ID_PARAM_SGDP_CORREO);
		ParametroDTO parametroDTOUrlSGDP = parametroService.getParametroPorID(Constantes.SGDP_URL);
		String asunto = parametroMailAsuntoCreacExp.getValorParametroChar().replace("$idSolicitudCreacionExp", ""+solicitudCreacionExpDTO.getIdSolicitudCreacionExp()); 
		String mensaje = parametroMailBodyMsgCreacExp.getValorParametroChar()
                .replace("$nombreSubProceso", solicitudCreacionExpDTO.getNombreSubProceso())
                .replace("$destinatario", solicitudCreacionExpDTO.getIdUsuarioDestinatario())
                .replace("$asunto", solicitudCreacionExpDTO.getAsuntoMateria())
                .replace("$autor", solicitudCreacionExpDTO.getAutorDTO().getNombreAutor())                
                .replace("$expediente", solicitudCreacionExpDTO.getNombreExpediente())
				.replace("$urlSGDP", parametroDTOUrlSGDP.getValorParametroChar());
		String to = solicitudCreacionExpDTO.getIdUsuarioSolicitante() + parametroSgdpMail.getValorParametroChar();
		emailService.enviarCorreo(parametroMailFrom.getValorParametroChar(), to, parametroMailSmtpHost.getValorParametroChar(), 
				asunto, mensaje, parametroMailTipoContenido.getValorParametroChar());
		to = solicitudCreacionExpDTO.getIdUsuarioCreadorExpediente() + parametroSgdpMail.getValorParametroChar();
		emailService.enviarCorreo(parametroMailFrom.getValorParametroChar(), to, parametroMailSmtpHost.getValorParametroChar(), 
				asunto, mensaje, parametroMailTipoContenido.getValorParametroChar());
	}

	@Override
	public List<SolicitudCreacionExpDTO> getSolicitudesCreaExpFinalizadas(SolicitudCreacionExpDTO solicitudCreacionExpDTO) {
		List<SolicitudCreacionExp> solicitudes = solicitudCreacionExpDao.getSolicitudesCreaExpFinalizadas(solicitudCreacionExpDTO);
		List<SolicitudCreacionExpDTO> solicitudesDTO = new ArrayList<SolicitudCreacionExpDTO>();
		for (SolicitudCreacionExp solicitudCreacionExp : solicitudes) {
			SolicitudCreacionExpDTO solicitudDTO = new SolicitudCreacionExpDTO();
			BeanUtils.copyProperties(solicitudCreacionExp, solicitudDTO);
			EstadoSolicitudCreacionExp estadoSolicitudCreacionExp = solicitudCreacionExp.getEstadoSolicitudCreacionExp();
			solicitudDTO.setIdEstadoSolicitudCreacionExp(estadoSolicitudCreacionExp.getIdEstadoSolicitudCreacionExp());
			solicitudDTO.setNombreEstadoSolicitudCreacionExp(estadoSolicitudCreacionExp.getNombreEstadoSolicitudCreacionExp());
			solicitudDTO.setAsuntoMateria(solicitudCreacionExp.getAsuntoMateria());			
			Proceso proceso = solicitudCreacionExp.getProceso();						
			if (proceso!=null) {
				solicitudDTO.setNombreSubProceso(proceso.getNombreProceso());
				solicitudDTO.setIdProceso(proceso.getIdProceso());
			}			
			if (solicitudCreacionExp.getAutor()!=null) {
				AutorDTO autorDTO = new AutorDTO(solicitudCreacionExp.getAutor().getIdAutor(), solicitudCreacionExp.getAutor().getNombreAutor());
				solicitudDTO.setAutorDTO(autorDTO);			
			}	
			InstanciaDeProceso instanciaDeProceso = solicitudCreacionExp.getInstanciaDeProceso();
			if (instanciaDeProceso!=null) {
				solicitudDTO.setNombreExpediente(instanciaDeProceso.getNombreExpediente());
			}
			solicitudesDTO.add(solicitudDTO);
			log.debug(solicitudDTO.toString());
		}
		return solicitudesDTO;
	}
	
	@Override
	public int getTotalSolicitudesCreaExpFinalizadas(SolicitudCreacionExpDTO solicitudCreacionExpDTO) {
		return solicitudCreacionExpDao.getTotalSolicitudesCreaExpFinalizadas(solicitudCreacionExpDTO);
	}
	
	@Override
	public List<SolicitudCreacionExpDTO> getSolicitudesCreaExpPorParamBusqueda(String parametroDeBusqueda, List<SolicitudCreacionExpDTO> solicitudesCreaExpPorParamBusqueda) throws ParseException, UnsupportedEncodingException {		
		if (parametroDeBusqueda!=null && !parametroDeBusqueda.isEmpty()) {
			List<SolicitudCreacionExpDTO> solicitudesCreacionExpDTO = new ArrayList<SolicitudCreacionExpDTO>();			
			parametroDeBusqueda = parametroDeBusqueda.toUpperCase();	
			log.info("parametroDeBusqueda: " + parametroDeBusqueda);				
			for (SolicitudCreacionExpDTO solicitudCreacionExpDTO: solicitudesCreaExpPorParamBusqueda) {				
				String idSol = String.valueOf(solicitudCreacionExpDTO.getIdSolicitudCreacionExp()).toUpperCase();				
				String nomSubProc = solicitudCreacionExpDTO.getNombreSubProceso()!=null && !solicitudCreacionExpDTO.getNombreSubProceso().isEmpty() ? solicitudCreacionExpDTO.getNombreSubProceso().toUpperCase(): ""; 				
				String idUsrSol = solicitudCreacionExpDTO.getIdUsuarioSolicitante()!=null && !solicitudCreacionExpDTO.getIdUsuarioSolicitante().isEmpty() ? solicitudCreacionExpDTO.getIdUsuarioSolicitante().toUpperCase(): "";
				String idUsrCreaExp = solicitudCreacionExpDTO.getIdUsuarioCreadorExpediente()!=null && !solicitudCreacionExpDTO.getIdUsuarioCreadorExpediente().isEmpty() ? solicitudCreacionExpDTO.getIdUsuarioCreadorExpediente().toUpperCase() : "";
				String fechaSol = solicitudCreacionExpDTO.getFechaSolicitud()!=null ? FechaUtil.toFormat(solicitudCreacionExpDTO.getFechaSolicitud(), FechaUtil.simpleDateFormatForm): "";
				String fechaAtencion = solicitudCreacionExpDTO.getFechaAtencion()!=null ? FechaUtil.toFormat(solicitudCreacionExpDTO.getFechaAtencion(), FechaUtil.simpleDateFormatForm): "";
				String comentario = solicitudCreacionExpDTO.getComentario()!=null && !solicitudCreacionExpDTO.getComentario().isEmpty() ? solicitudCreacionExpDTO.getComentario().toUpperCase() : "";
				String expediente = solicitudCreacionExpDTO.getNombreExpediente()!=null && !solicitudCreacionExpDTO.getNombreExpediente().isEmpty() ? solicitudCreacionExpDTO.getNombreExpediente().toUpperCase() : "";
				log.info(nomSubProc.indexOf(parametroDeBusqueda) !=-1);
				if ( idSol.indexOf(parametroDeBusqueda) !=-1 
					|| nomSubProc.indexOf(parametroDeBusqueda) !=-1
					|| idUsrSol.indexOf(parametroDeBusqueda) !=-1
					|| idUsrCreaExp.indexOf(parametroDeBusqueda) !=-1
					|| fechaSol.toUpperCase().indexOf(parametroDeBusqueda) !=-1
					|| fechaAtencion.toUpperCase().indexOf(parametroDeBusqueda) !=-1
					|| comentario.toUpperCase().indexOf(parametroDeBusqueda) !=-1
					|| expediente.toUpperCase().indexOf(parametroDeBusqueda) !=-1
					) {
					solicitudesCreacionExpDTO.add(solicitudCreacionExpDTO);							
				}
			}
			if (!solicitudesCreacionExpDTO.isEmpty()) {
				log.info("retornado filtrado: " + solicitudesCreacionExpDTO.size());
				return solicitudesCreacionExpDTO;
			}
		}		
		return solicitudesCreaExpPorParamBusqueda;
	}	
	
}