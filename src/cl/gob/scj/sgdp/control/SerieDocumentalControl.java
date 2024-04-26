package cl.gob.scj.sgdp.control;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.CargaProcesoDataInicialDTO;
import cl.gob.scj.sgdp.dto.MacroProcesoDTO;
import cl.gob.scj.sgdp.dto.ProcesoDTO;
import cl.gob.scj.sgdp.dto.SerieDocumentalDTO;
import cl.gob.scj.sgdp.dto.SuperProcesoDTO;
import cl.gob.scj.sgdp.dto.TipoDeDocumentoSerieDocumentalDTO;
import cl.gob.scj.sgdp.service.CargaDeProcesosService;
import cl.gob.scj.sgdp.service.SerieDocumentalService;
import cl.gob.scj.sgdp.tipos.PermisoType;


@Controller
public class SerieDocumentalControl {
	
	@Autowired
	private CargaDeProcesosService cargaDeProcesosService;
	
	@Autowired
	private SerieDocumentalService serieDocumentalService;
	
	private static final Logger log = Logger.getLogger(SerieDocumentalControl.class);

	@RequestMapping(value = "/serieDocumental", method = RequestMethod.GET)
	public String cargaPantallaCargaProcesos(Model model, HttpServletRequest request) {
		log.debug("Inicio serie documental");
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		String vista = "serieDocumental";
		String permisoMantenedorSeriesDoc = PermisoType.PUEDE_MANTENER_SERIES_DOCUMENTALES.getNombrePermiso();
		String permisoUsrMantenedorSeriesDocs = usuario.getPermisos().get(permisoMantenedorSeriesDoc);
		log.debug("permisoMantenedorSeriesDoc: " + permisoMantenedorSeriesDoc);
		log.debug("permisoUsrMantenedorSeriesDocs: " + permisoUsrMantenedorSeriesDocs);		
		if (permisoUsrMantenedorSeriesDocs==null || (permisoUsrMantenedorSeriesDocs!=null && !permisoUsrMantenedorSeriesDocs.equals(permisoMantenedorSeriesDoc))) {
			log.debug("Devolviendo vista 403");
			vista = "error403";
		} 
		return vista;
	}

	
	@RequestMapping(value="/serieDocumental/dataInicial", method=RequestMethod.GET)
	public @ResponseBody CargaProcesoDataInicialDTO getDataInicial() {			
		return cargaDeProcesosService.getDataInicial(new CargaProcesoDataInicialDTO());
	}
	
	@RequestMapping(value="/serieDocumental/perspectivas", method=RequestMethod.GET)
	public @ResponseBody CargaProcesoDataInicialDTO getPerspectivas() {			
		return cargaDeProcesosService.getDataInicial(new CargaProcesoDataInicialDTO());
	}
	
	@RequestMapping(value="/serieDocumental/macroProcesos/{perspectiva}", method=RequestMethod.GET)
	public @ResponseBody List<MacroProcesoDTO> getMacroProcesos(@PathVariable Long perspectiva) {			
		return cargaDeProcesosService.getTodosLosMacroProcesos(perspectiva, new ArrayList<MacroProcesoDTO>());
	}
	
	@RequestMapping(value="/serieDocumental/superProcesos/{macroProceso}", method=RequestMethod.GET)
	public @ResponseBody List<SuperProcesoDTO> getSuperProcesos(@PathVariable Long macroProceso) {			
		return cargaDeProcesosService.getTodosLosSuperProcesos(macroProceso, new ArrayList<SuperProcesoDTO>());
	}
	
	@RequestMapping(value="/serieDocumental/procesos/{superProceso}", method=RequestMethod.GET)
	public @ResponseBody List<ProcesoDTO> getProcesos(@PathVariable Long superProceso) {			
		List<ProcesoDTO> listaProcesos = cargaDeProcesosService.getTodosLosProcesosBySuperProceso(superProceso, new ArrayList<ProcesoDTO>());
		List<SerieDocumentalDTO> listaSeries = serieDocumentalService.list();
		List<String> codigosProcesosConSerie = new ArrayList<>();
		for(SerieDocumentalDTO serie: listaSeries) {
			codigosProcesosConSerie.add(serie.getCodigoProceso());
		}
		List<ProcesoDTO> listaFinal = new ArrayList<>();
		for(ProcesoDTO proceso: listaProcesos) {
			if(!codigosProcesosConSerie.contains(proceso.getCodigoProceso())) {
				listaFinal.add(proceso);
			}
		}
		return listaFinal;
	}
	
	@RequestMapping(value="/serieDocumental/list", method=RequestMethod.GET)
	public @ResponseBody List<SerieDocumentalDTO> list() {			
		return serieDocumentalService.list();
	}
	
	@RequestMapping(value = "/serieDocumental", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<SerieDocumentalDTO> guarda(@RequestBody SerieDocumentalDTO serieDocumental, HttpServletRequest request) {
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		String permisoMantenedorSeriesDoc = PermisoType.PUEDE_MANTENER_SERIES_DOCUMENTALES.getNombrePermiso();
		String permisoUsrMantenedorSeriesDocs = usuario.getPermisos().get(permisoMantenedorSeriesDoc);
		log.debug("permisoMantenedorSeriesDoc: " + permisoMantenedorSeriesDoc);
		log.debug("permisoUsrMantenedorSeriesDocs: " + permisoUsrMantenedorSeriesDocs);		
		if (permisoUsrMantenedorSeriesDocs!=null && permisoUsrMantenedorSeriesDocs.equals(permisoMantenedorSeriesDoc)) {
			return new ResponseEntity<>(serieDocumentalService.guardar(serieDocumental), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
	
	@RequestMapping(value = "/serieDocumental", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<SerieDocumentalDTO> actualiza(@RequestBody SerieDocumentalDTO serieDocumental, HttpServletRequest request) {
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		String permisoMantenedorSeriesDoc = PermisoType.PUEDE_MANTENER_SERIES_DOCUMENTALES.getNombrePermiso();
		String permisoUsrMantenedorSeriesDocs = usuario.getPermisos().get(permisoMantenedorSeriesDoc);
		log.debug("permisoMantenedorSeriesDoc: " + permisoMantenedorSeriesDoc);
		log.debug("permisoUsrMantenedorSeriesDocs: " + permisoUsrMantenedorSeriesDocs);		
		if (permisoUsrMantenedorSeriesDocs!=null && permisoUsrMantenedorSeriesDocs.equals(permisoMantenedorSeriesDoc)) {
			return new ResponseEntity<>(serieDocumentalService.actualizar(serieDocumental), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
	
	@RequestMapping(value = "/serieDocumental", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Boolean> elimina(@RequestBody SerieDocumentalDTO serieDocumental, HttpServletRequest request) {
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		String permisoMantenedorSeriesDoc = PermisoType.PUEDE_MANTENER_SERIES_DOCUMENTALES.getNombrePermiso();
		String permisoUsrMantenedorSeriesDocs = usuario.getPermisos().get(permisoMantenedorSeriesDoc);
		log.debug("permisoMantenedorSeriesDoc: " + permisoMantenedorSeriesDoc);
		log.debug("permisoUsrMantenedorSeriesDocs: " + permisoUsrMantenedorSeriesDocs);		
		if (permisoUsrMantenedorSeriesDocs!=null && permisoUsrMantenedorSeriesDocs.equals(permisoMantenedorSeriesDoc)) {
			return new ResponseEntity<>(serieDocumentalService.elimina(serieDocumental), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
	
	@RequestMapping(value="/serieDocumental/tablaRetencion/{codigoProceso}", method=RequestMethod.GET)
	public @ResponseBody List<TipoDeDocumentoSerieDocumentalDTO> listTablaRetencion(@PathVariable String codigoProceso, HttpServletRequest request) {			
		return serieDocumentalService.documentosDeProceso(codigoProceso);
	}
	
	@RequestMapping(value="/serieDocumental/tablaRetencion", method=RequestMethod.PUT)
	public ResponseEntity<TipoDeDocumentoSerieDocumentalDTO> save(@RequestBody TipoDeDocumentoSerieDocumentalDTO td, HttpServletRequest request) {	
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		String permisoMantenedorSeriesDoc = PermisoType.PUEDE_MANTENER_SERIES_DOCUMENTALES.getNombrePermiso();
		String permisoUsrMantenedorSeriesDocs = usuario.getPermisos().get(permisoMantenedorSeriesDoc);
		log.debug("permisoMantenedorSeriesDoc: " + permisoMantenedorSeriesDoc);
		log.debug("permisoUsrMantenedorSeriesDocs: " + permisoUsrMantenedorSeriesDocs);		
		if (permisoUsrMantenedorSeriesDocs!=null && permisoUsrMantenedorSeriesDocs.equals(permisoMantenedorSeriesDoc)) {
			return new ResponseEntity<>(serieDocumentalService.saveTablaRetencion(td), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
	
}
