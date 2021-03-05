package cl.gob.scj.sgdp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dao.HistoricoValorParametroDeTareaDao;
import cl.gob.scj.sgdp.dao.ParametroDeTareaDao;
import cl.gob.scj.sgdp.dao.TextoParametroDeTareaDao;
import cl.gob.scj.sgdp.dao.TipoParametroDeTareaDao;
import cl.gob.scj.sgdp.dao.ValorParametroDeTareaDao;
import cl.gob.scj.sgdp.dto.ParametroDeTareaDTO;
import cl.gob.scj.sgdp.dto.ParametroFormularioDTO;
import cl.gob.scj.sgdp.dto.TipoParametroDeTareaDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.model.HistoricoDeInstDeTarea;
import cl.gob.scj.sgdp.model.HistoricoValorParametroDeTarea;
import cl.gob.scj.sgdp.model.InstanciaDeTarea;
import cl.gob.scj.sgdp.model.ParametroDeTarea;
import cl.gob.scj.sgdp.model.TextoParametroDeTarea;
import cl.gob.scj.sgdp.model.TipoParametroDeTarea;
import cl.gob.scj.sgdp.model.ValorParametroDeTarea;
import cl.gob.scj.sgdp.service.ParametroDeTareaService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class ParametroDeTareaServiceImpl implements ParametroDeTareaService {
	
	private static final Logger log = Logger.getLogger(ParametroDeTareaServiceImpl.class);
	
	@Resource(name = "configProps")
	private Properties configProps;
	
	@Autowired
	private ParametroDeTareaDao parametroDeTareaDao;
	
	@Autowired
	private TipoParametroDeTareaDao tipoParametroDeTareaDao;
	
	@Autowired
	private TextoParametroDeTareaDao textoParametroDeTareaDao;
	
	@Autowired
	private HistoricoValorParametroDeTareaDao historicoValorParametroDeTareaDao;
	
	@Autowired
	private ValorParametroDeTareaDao valorParametroDeTareaDao;

	@Override
	public void cargarParametrosDeTareaDTO(List<ParametroFormularioDTO> listParametroFormularioDTO, long idInstanciaDeTarea) {
		List<ParametroDeTarea> listParamDeTarea = parametroDeTareaDao.getParametrosDeTareaPorIdInstanciaDeTarea(idInstanciaDeTarea);
		for (ParametroDeTarea parametroDeTarea: listParamDeTarea) {
			ParametroFormularioDTO parametroFormularioDTOEnLista = getParametroDTODeTareaEstaEnListaDeParametroDeTareaDTO(parametroDeTarea, listParametroFormularioDTO);
			if (parametroFormularioDTOEnLista == null) {
				ParametroFormularioDTO parametroFormularioDTO = new ParametroFormularioDTO();
				parametroFormularioDTO.setName(parametroDeTarea.getIdParamTarea().toString() + "_" + parametroDeTarea.getNombreParamTarea());
				parametroFormularioDTO.setValue(parametroDeTarea.getTipoParametroDeTarea().getValorParamNoSeteado());
				listParametroFormularioDTO.add(parametroFormularioDTO);	
			} else {
				parametroFormularioDTOEnLista.setValue(parametroDeTarea.getTipoParametroDeTarea().getValorParamSeteado());
			}	
		}
	}
	
	private ParametroFormularioDTO getParametroDTODeTareaEstaEnListaDeParametroDeTareaDTO(ParametroDeTarea parametroDeTarea, List<ParametroFormularioDTO> listParametroFormularioDTO) {
		for (ParametroFormularioDTO parametroFormularioDTO: listParametroFormularioDTO) {
			if (parametroFormularioDTO.getName().contains("_")) {
				String idParamTarea = parametroFormularioDTO.getName().split("_")[0];
				if (parametroDeTarea.getIdParamTarea() == Long.parseLong(idParamTarea)) {
					return parametroFormularioDTO;
				}				
			}
		}
		return null;
	}
	
	@Override
	public Map<String, List<ParametroDeTareaDTO>> getMapParametrosDeTareaDTOTituloPorIdInstanciaDeTarea(long idInstanciaDeTarea) {	
		
		Map<String, List<ParametroDeTareaDTO>> mapParametrosDeTareaDTOTitulo = new HashMap<String, List<ParametroDeTareaDTO>>();
		
		List<ParametroDeTareaDTO> parametrosDeTareaDTOTitulo = getParametrosDeTareaDTOTituloPorIdInstanciaDeTarea(idInstanciaDeTarea);
		
		Iterator<ParametroDeTareaDTO> it = parametrosDeTareaDTOTitulo.iterator();
		
		while (it.hasNext()) {
			ParametroDeTareaDTO parametroDeTareaDTO = it.next();
			if (mapParametrosDeTareaDTOTitulo.containsKey(parametroDeTareaDTO.getTitulo())) {
				List<ParametroDeTareaDTO> parametrosDeTareaDTO = mapParametrosDeTareaDTOTitulo.get(parametroDeTareaDTO.getTitulo());
				parametrosDeTareaDTO.add(parametroDeTareaDTO);
				it.remove();
				mapParametrosDeTareaDTOTitulo.put(parametroDeTareaDTO.getTitulo(), parametrosDeTareaDTO);
			} else {
				List<ParametroDeTareaDTO> parametrosDeTareaDTO = new ArrayList<ParametroDeTareaDTO>();
				parametrosDeTareaDTO.add(parametroDeTareaDTO);
				it.remove();
				mapParametrosDeTareaDTOTitulo.put(parametroDeTareaDTO.getTitulo(), parametrosDeTareaDTO);
			}
		}
		
		return mapParametrosDeTareaDTOTitulo;
	
	}
	
	private List<ParametroDeTareaDTO> getParametrosDeTareaDTOTituloPorIdInstanciaDeTarea(long idInstanciaDeTarea) {		
		List<ParametroDeTareaDTO> parametrosDeTareaDTOPorIdTarea = getParametrosDeTareaPorIdInstanciaDeTarea(idInstanciaDeTarea);
		String inputComentario = null;
		String abreSelect = null;
		String cierraSelect = null;
		TipoParametroDeTarea inputComentarioParam = tipoParametroDeTareaDao.getTipoParametroDeTareaPorNombreTipoParametroDeTarea("comentario");
		TipoParametroDeTarea abreSelectParam =  tipoParametroDeTareaDao.getTipoParametroDeTareaPorNombreTipoParametroDeTarea("abre_select");
		TipoParametroDeTarea cierraSelectParam =  tipoParametroDeTareaDao.getTipoParametroDeTareaPorNombreTipoParametroDeTarea("cierra_select");
		if (inputComentarioParam!=null ) {
			inputComentario = inputComentarioParam.getTextoHtml();
			log.debug("inputComentario: " + inputComentario);
		}
		if (abreSelectParam!=null ) {
			abreSelect = abreSelectParam.getTextoHtml();
			log.debug("abreSelect: " + abreSelect);
		}
		if (cierraSelectParam!=null ) {
			cierraSelect = cierraSelectParam.getTextoHtml();
			log.debug("cierraSelect: " + cierraSelect);
		}
		for (ParametroDeTareaDTO parametroDeTareaDTO : parametrosDeTareaDTOPorIdTarea) {
			List<TextoParametroDeTarea> textosParametroDeTarea = textoParametroDeTareaDao.getTextosParametroDeTareaPorIdParamTarea(parametroDeTareaDTO.getIdParamTarea());
			for (TextoParametroDeTarea textoParametroDeTarea : textosParametroDeTarea) {
				log.debug(textoParametroDeTarea.toString());
				String textoHtml = parametroDeTareaDTO.getTipoParametroDeTareaDTO().getTextoHtml().replace("${textoParametro}", textoParametroDeTarea.getTexto());				
				textoHtml = textoHtml.replace("${idParamTarea}", Long.toString(parametroDeTareaDTO.getIdParamTarea()));
				textoHtml = textoHtml.replace("${nombreDeTipoDeRequisito}", parametroDeTareaDTO.getNombreDeTipoDeRequisito());
				if (parametroDeTareaDTO.getTextoHtml()!=null && !parametroDeTareaDTO.getTextoHtml().isEmpty()) {
					StringBuilder textoHtmlTipoParam = new StringBuilder(parametroDeTareaDTO.getTextoHtml()); 
					textoHtmlTipoParam.append(" ");	
					textoHtmlTipoParam.append(textoHtml);					
					parametroDeTareaDTO.setTextoHtml(textoHtmlTipoParam.toString());
				} else {
					parametroDeTareaDTO.setTextoHtml(textoHtml);
				}	
			}
			if (parametroDeTareaDTO.getTipoParametroDeTareaDTO().getComenta()!=null && parametroDeTareaDTO.getTipoParametroDeTareaDTO().getComenta().booleanValue() == true) {
				if (parametroDeTareaDTO.getTextoHtml()!=null && !parametroDeTareaDTO.getTextoHtml().isEmpty()) {					
					StringBuilder textoHtmlTipoParamComentario = new StringBuilder(parametroDeTareaDTO.getTextoHtml()); 
					textoHtmlTipoParamComentario.append(" ");	
					if (inputComentario!=null) {
						textoHtmlTipoParamComentario.append(inputComentario.replace("${idParamTarea}", Long.toString(parametroDeTareaDTO.getIdParamTarea())));
					}		
					parametroDeTareaDTO.setTextoHtml(textoHtmlTipoParamComentario.toString());
				}
			}
			if (parametroDeTareaDTO.getTextoHtml()!=null && !parametroDeTareaDTO.getTextoHtml().isEmpty()
					&& parametroDeTareaDTO.getTextoHtml().contains("<option") && parametroDeTareaDTO.getTextoHtml().contains("<select")) {
				StringBuilder select = new StringBuilder(parametroDeTareaDTO.getTextoHtml());
				select.insert(0, abreSelect);
		    	select.insert(select.length(), cierraSelect);
		    	String selectTextoHtml = select.toString().replace("${idParamTarea}", Long.toString(parametroDeTareaDTO.getIdParamTarea()));
		    	parametroDeTareaDTO.setTextoHtml(selectTextoHtml);
			}	
			log.debug(parametroDeTareaDTO.toString());
		}
		return parametrosDeTareaDTOPorIdTarea;		
	}
	
	@Override
	public List<ParametroDeTareaDTO> getParametrosDeTareaPorIdInstanciaDeTarea(long idInstanciaDeTarea) {
		List<ParametroDeTareaDTO> parametrosDeTareaDTOPorIdTarea = new ArrayList<ParametroDeTareaDTO>();
		List<ParametroDeTarea> parametrosDeTareaPorIdTarea = parametroDeTareaDao.getParametrosDeTareaPorIdInstanciaDeTarea(idInstanciaDeTarea);
		for (ParametroDeTarea parametroDeTarea : parametrosDeTareaPorIdTarea) {
			ParametroDeTareaDTO parametroDeTareaDTO = new ParametroDeTareaDTO();
			parametroDeTareaDTO.setIdParamTarea(parametroDeTarea.getIdParamTarea());
			parametroDeTareaDTO.setNombreParamTarea(parametroDeTarea.getNombreParamTarea());
			parametroDeTareaDTO.setTitulo(parametroDeTarea.getTitulo());
			parametroDeTareaDTO.setEsSNC(parametroDeTarea.getEsSNC());
			TipoParametroDeTarea tipoParametroDeTarea = parametroDeTarea.getTipoParametroDeTarea();
			TipoParametroDeTareaDTO tipoParametroDeTareaDTO = new TipoParametroDeTareaDTO();
			tipoParametroDeTareaDTO.setIdTipoParametroDeTarea(tipoParametroDeTarea.getIdTipoParametroDeTarea());
			tipoParametroDeTareaDTO.setNombreTipoParametroDeTarea(tipoParametroDeTarea.getNombreTipoParametroDeTarea());
			tipoParametroDeTareaDTO.setTextoHtml(tipoParametroDeTarea.getTextoHtml());
			parametroDeTareaDTO.setTipoParametroDeTareaDTO(tipoParametroDeTareaDTO);
			tipoParametroDeTareaDTO.setComenta(tipoParametroDeTarea.getComenta());
			parametrosDeTareaDTOPorIdTarea.add(parametroDeTareaDTO);
		}		
		return parametrosDeTareaDTOPorIdTarea;
	}
	
	@Override
	public List<ParametroDeTareaDTO> getParametrosDeTareaDTOPorIdTarea(long idTarea) {
		List<ParametroDeTareaDTO> parametrosDeTareaDTOPorIdTarea = new ArrayList<ParametroDeTareaDTO>();
		List<ParametroDeTarea> parametrosDeTareaPorIdTarea = parametroDeTareaDao.getParametrosDeTareaPorIdTarea(idTarea);
		for (ParametroDeTarea parametroDeTarea : parametrosDeTareaPorIdTarea) {
			ParametroDeTareaDTO parametroDeTareaDTO = new ParametroDeTareaDTO();
			parametroDeTareaDTO.setIdParamTarea(parametroDeTarea.getIdParamTarea());
			parametroDeTareaDTO.setNombreParamTarea(parametroDeTarea.getNombreParamTarea());
			parametroDeTareaDTO.setEsSNC(parametroDeTarea.getEsSNC());
			TipoParametroDeTarea tipoParametroDeTarea = parametroDeTarea.getTipoParametroDeTarea();
			TipoParametroDeTareaDTO tipoParametroDeTareaDTO = new TipoParametroDeTareaDTO();
			tipoParametroDeTareaDTO.setIdTipoParametroDeTarea(tipoParametroDeTarea.getIdTipoParametroDeTarea());
			tipoParametroDeTareaDTO.setNombreTipoParametroDeTarea(tipoParametroDeTarea.getNombreTipoParametroDeTarea());
			tipoParametroDeTareaDTO.setTextoHtml(tipoParametroDeTarea.getTextoHtml());
			parametroDeTareaDTO.setTipoParametroDeTareaDTO(tipoParametroDeTareaDTO);
			parametrosDeTareaDTOPorIdTarea.add(parametroDeTareaDTO);
		}		
		return parametrosDeTareaDTOPorIdTarea;
	}
	
	@Override
	public void guardaValorParametroDeTarea(List<ParametroFormularioDTO> listParametroFormularioDTO, InstanciaDeTarea instanciaDeTarea) throws SgdpException {
		boolean insertaValorParametroDeTarea = false;
		cargarParametrosDeTareaDTO(listParametroFormularioDTO, instanciaDeTarea.getIdInstanciaDeTarea());
		for (ParametroFormularioDTO parametroFormularioDTO : listParametroFormularioDTO) {
			log.debug(parametroFormularioDTO.toString());
			if (parametroFormularioDTO.getName().contains("_")) {
				String idParamTarea = parametroFormularioDTO.getName().split("_")[0];
				ValorParametroDeTarea valorParametroDeTarea = valorParametroDeTareaDao.getValorParametroDeTareaPorIdParamIdInstanciaTarea(Long.parseLong(idParamTarea), instanciaDeTarea.getIdInstanciaDeTarea());
				ParametroDeTarea parametroDeTarea = parametroDeTareaDao.getParametroDeTareaPorIdParamTarea(Long.parseLong(idParamTarea));
				if (parametroDeTarea == null) {
					log.error("No se encontro ParametroDeTarea con idParamTarea: " + idParamTarea);
					throw new SgdpException(configProps.getProperty("errorAlAvanzarProceso"));
				}
				if (valorParametroDeTarea == null) {
					valorParametroDeTarea = new ValorParametroDeTarea();
					insertaValorParametroDeTarea = true;
				} 
				valorParametroDeTarea.setFecha(new Date());
				valorParametroDeTarea.setInstanciaDeTarea(instanciaDeTarea);
				valorParametroDeTarea.setParametroDeTarea(parametroDeTarea);
				valorParametroDeTarea.setComentario(getComentarioDePorIdParamTareaEnListParametroFormularioDTO(idParamTarea, listParametroFormularioDTO));
				if (parametroFormularioDTO.getValue() instanceof String) {
					String valor = (String) parametroFormularioDTO.getValue();
					valorParametroDeTarea.setValor(valor);
				} else if (parametroFormularioDTO.getValue() instanceof String[]) {
					String[] valor = (String[]) parametroFormularioDTO.getValue();
					valorParametroDeTarea.setValor(StringUtils.join(valor, ";"));
				}
				if (insertaValorParametroDeTarea == true) {
					valorParametroDeTareaDao.insert(valorParametroDeTarea);
				}
			}										
		}
	}
	
	
	@Override
	public void guardaHistoricoValorParametroDeTarea(List<ParametroFormularioDTO> listParametroFormularioDTO, HistoricoDeInstDeTarea historicoDeInstDeTarea, InstanciaDeTarea instanciaDeTarea, Usuario usuario) throws SgdpException {
		String valor;
		cargarParametrosDeTareaDTO(listParametroFormularioDTO, instanciaDeTarea.getIdInstanciaDeTarea());
		for (ParametroFormularioDTO parametroFormularioDTO : listParametroFormularioDTO) {
            log.debug(parametroFormularioDTO.toString());                    
			if (parametroFormularioDTO.getName().contains("_")) {
				String idParamTarea = parametroFormularioDTO.getName().split("_")[0];
				ParametroDeTarea parametroDeTarea = parametroDeTareaDao.getParametroDeTareaPorIdParamTarea(Long.parseLong(idParamTarea));
				HistoricoValorParametroDeTarea historicoValorParametroDeTarea = new HistoricoValorParametroDeTarea();
				if (parametroDeTarea == null) {
					log.error("No se encontro ParametroDeTarea con idParamTarea: " + idParamTarea);
					throw new SgdpException(configProps.getProperty("errorAlAvanzarProceso"));
                }
                if (parametroFormularioDTO.getValue() instanceof String) {
					valor = (String) parametroFormularioDTO.getValue();
					historicoValorParametroDeTarea.setValor(valor);
				} else if (parametroFormularioDTO.getValue() instanceof String[]) {
					String[] valorArray = (String[]) parametroFormularioDTO.getValue();
					valor = StringUtils.join(valorArray, ";");
					historicoValorParametroDeTarea.setValor(valor);
				}                        
                historicoValorParametroDeTarea.setComentario(getComentarioDePorIdParamTareaEnListParametroFormularioDTO(idParamTarea, listParametroFormularioDTO));
                historicoValorParametroDeTarea.setFecha(new Date());
                historicoValorParametroDeTarea.setInstanciaDeTarea(instanciaDeTarea);
                historicoValorParametroDeTarea.setHistoricoDeInstDeTarea(historicoDeInstDeTarea);
                historicoValorParametroDeTarea.setParametroDeTarea(parametroDeTarea);  
                historicoValorParametroDeTarea.setIdUsuario(usuario.getIdUsuario());
                historicoValorParametroDeTareaDao.insert(historicoValorParametroDeTarea);	
			}										
		}	
	}
	
	public String getComentarioDePorIdParamTareaEnListParametroFormularioDTO(String idParamTarea, List<ParametroFormularioDTO> listParametroFormularioDTO) {
		for (ParametroFormularioDTO parametroFormularioDTO : listParametroFormularioDTO) {	
			if (parametroFormularioDTO.getName().contains("-") && parametroFormularioDTO.getName().contains(idParamTarea)) {
				if (parametroFormularioDTO.getValue() instanceof String) {
					return(String) parametroFormularioDTO.getValue();					
				} else if (parametroFormularioDTO.getValue() instanceof String[]) {
					String[] valor = (String[]) parametroFormularioDTO.getValue();
					return StringUtils.join(valor, ";");
				}				
			}
		}		
		return null;
	}

}
