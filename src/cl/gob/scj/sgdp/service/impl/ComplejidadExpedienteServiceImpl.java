package cl.gob.scj.sgdp.service.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dao.ComplejidadExpedienteDao;
import cl.gob.scj.sgdp.dao.InstanciaDeProcesoDao;
import cl.gob.scj.sgdp.dto.ComplejidadExpedienteDTO;
import cl.gob.scj.sgdp.dto.rest.ExpedienteRestActMetaDTO;
import cl.gob.scj.sgdp.model.ComplejidadExpediente;
import cl.gob.scj.sgdp.model.InstanciaDeProceso;
import cl.gob.scj.sgdp.model.LogTransaccion;
import cl.gob.scj.sgdp.service.ComplejidadExpedienteService;
import cl.gob.scj.sgdp.service.ComplejidadService;
import cl.gob.scj.sgdp.service.InstanciaDeProcesoService;
import cl.gob.scj.sgdp.tipos.EstadoDeProcesoType;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.GestorMetadataCMSService;


@Service
@Transactional(rollbackFor = Throwable.class)
public class ComplejidadExpedienteServiceImpl implements ComplejidadExpedienteService {
	
	private static final Logger log = Logger.getLogger(ComplejidadExpedienteServiceImpl.class);	
        
	private ComplejidadExpedienteDao complejidadExpedienteDao;
	
	private InstanciaDeProcesoService instanciaProcesoService;
	
	private InstanciaDeProcesoDao instanciaDeProcesoDao;

    private GestorMetadataCMSService gestorMetadataCMSService;
    
    private ComplejidadService complejidadService;
    
    @Autowired    
	public ComplejidadExpedienteServiceImpl(ComplejidadExpedienteDao complejidadExpedienteDao,
			InstanciaDeProcesoService instanciaProcesoService, InstanciaDeProcesoDao instanciaDeProcesoDao, GestorMetadataCMSService gestorMetadataCMSService,
			ComplejidadService complejidadService) {
		super();
		this.complejidadExpedienteDao = complejidadExpedienteDao;
		this.instanciaProcesoService = instanciaProcesoService;
		this.instanciaDeProcesoDao = instanciaDeProcesoDao;
		this.gestorMetadataCMSService = gestorMetadataCMSService;
		this.complejidadService = complejidadService;
	}

	@Override
	public ComplejidadExpedienteDTO getLastByNombreExpediente(String nombreExpediente) {
		try {
			ComplejidadExpedienteDTO complejidadExpedienteDTO = complejidadExpedienteDao.getLastByNombreExpediente(nombreExpediente, new ComplejidadExpedienteDTO());
			complejidadExpedienteDTO.setComplejidades(complejidadService.getComplejidades());
			return complejidadExpedienteDTO;
		} catch (Exception e ) {
			return null;
		}
	}

	@Override
	public ComplejidadExpedienteDTO guardarPorNombre(Usuario usuario, ComplejidadExpedienteDTO complejidadDTO) {
		try {
			ComplejidadExpediente complejidad = new ComplejidadExpediente();
			complejidad.setComplejidad(complejidadDTO.getComplejidad());
			complejidad.setMotivoComplejidad(complejidadDTO.getMotivoComplejidad());
			complejidad.setUsuario(complejidadDTO.getUsuario());
			InstanciaDeProceso ip = instanciaProcesoService.getInstanciaDeProcesoPorNombre(complejidadDTO.getNombreExpediente());
			complejidad.setInstanciaDeProceso(ip);
			complejidad.setFecha(new Date());
			complejidadExpedienteDao.insert(complejidad);
			ExpedienteRestActMetaDTO expedienteRestActMetaDTO = new ExpedienteRestActMetaDTO();
			expedienteRestActMetaDTO.setIdExpediente(ip.getIdExpediente());
			expedienteRestActMetaDTO.setComplejidad(complejidadDTO.getComplejidad());
			gestorMetadataCMSService.actualizaMetaDataExpediente(usuario, expedienteRestActMetaDTO);
            if (ip.getEstadoDeProceso().getCodigoEstadoDeProceso() == EstadoDeProcesoType.FINALIZADO.getCodigoEstadoDeProceso() 
            		|| ip.getEstadoDeProceso().getCodigoEstadoDeProceso() == EstadoDeProcesoType.ANULADO.getCodigoEstadoDeProceso()) {
            	complejidadDTO.setMessage("No es posible modificar la complejidad ya que el expediente " + ip.getEstadoDeProceso().getNombreEstadoDeProceso().toLowerCase());
                complejidadDTO.setType("warning");
                return complejidadDTO;
            } else {
            	 complejidadDTO.setMessage("Se ha actualizado la complejidad!");
                 complejidadDTO.setType("success");
                 return complejidadDTO;
            }
			
		} catch (Exception e ) {
			return null;
		}
	}

	@Override
	public ComplejidadExpedienteDTO guardar(Usuario usuario, ComplejidadExpedienteDTO complejidadDTO) {
		try {
			ComplejidadExpediente complejidad = new ComplejidadExpediente();
			complejidad.setComplejidad(complejidadDTO.getComplejidad());
			complejidad.setMotivoComplejidad(complejidadDTO.getMotivoComplejidad());
			complejidad.setUsuario(complejidadDTO.getUsuario());
			InstanciaDeProceso ip = instanciaDeProcesoDao.getInstanciaDeProcesoPorIdInstanciaDeProceso(complejidadDTO.getIdInstanciaDeProceso());
			complejidad.setInstanciaDeProceso(ip);
			complejidadExpedienteDao.insert(complejidad);
			ExpedienteRestActMetaDTO expedienteRestActMetaDTO = new ExpedienteRestActMetaDTO();
			expedienteRestActMetaDTO.setIdExpediente(ip.getIdExpediente());
			expedienteRestActMetaDTO.setComplejidad(complejidadDTO.getComplejidad());
			gestorMetadataCMSService.actualizaMetaDataExpediente(usuario, expedienteRestActMetaDTO);
			return complejidadDTO;
		} catch (Exception e ) {
			return null;
		}
	}
	
}
