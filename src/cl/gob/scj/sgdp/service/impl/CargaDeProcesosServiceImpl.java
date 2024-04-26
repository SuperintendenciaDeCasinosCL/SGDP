
package cl.gob.scj.sgdp.service.impl;

import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import java.nio.file.Paths;

import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dao.MacroProcesoDao;
import cl.gob.scj.sgdp.dao.PerspectivaDao;
import cl.gob.scj.sgdp.dao.ProcesoDao;
import cl.gob.scj.sgdp.dao.ReferenciaDeTareaDao;
import cl.gob.scj.sgdp.dao.ResponsabilidadDao;
import cl.gob.scj.sgdp.dao.SuperProcesoDao;
import cl.gob.scj.sgdp.dao.TareaDao;
import cl.gob.scj.sgdp.dao.TipoDeDocumentoDao;
import cl.gob.scj.sgdp.dao.UnidadDao;
import cl.gob.scj.sgdp.dto.CargaProcesoDataInicialDTO;
import cl.gob.scj.sgdp.dto.MacroProcesoDTO;
import cl.gob.scj.sgdp.dto.ParametroDeTareaDTO;
import cl.gob.scj.sgdp.dto.PerspectivaDTO;
import cl.gob.scj.sgdp.dto.ProcesoDTO;
import cl.gob.scj.sgdp.dto.ResponsabilidadDTO;
import cl.gob.scj.sgdp.dto.SuperProcesoDTO;
import cl.gob.scj.sgdp.dto.TareaDTO;
import cl.gob.scj.sgdp.dto.TipoDeDocumentoDTO;
import cl.gob.scj.sgdp.dto.UnidadDTO;
import cl.gob.scj.sgdp.model.DocumentoDeSalidaDeTarea;
import cl.gob.scj.sgdp.model.DocumentoDeSalidaDeTareaPK;
import cl.gob.scj.sgdp.model.Etapa;
import cl.gob.scj.sgdp.model.MacroProceso;
import cl.gob.scj.sgdp.model.ParametroDeTarea;
import cl.gob.scj.sgdp.model.ParametroRelacionTarea;
import cl.gob.scj.sgdp.model.ParametroRelacionTareaPK;
import cl.gob.scj.sgdp.model.Perspectiva;
import cl.gob.scj.sgdp.model.Proceso;
import cl.gob.scj.sgdp.model.ReferenciaDeTarea;
import cl.gob.scj.sgdp.model.Responsabilidad;
import cl.gob.scj.sgdp.model.ResponsabilidadTarea;
import cl.gob.scj.sgdp.model.ResponsabilidadTareaPK;
import cl.gob.scj.sgdp.model.SuperProceso;
import cl.gob.scj.sgdp.model.Tarea;
import cl.gob.scj.sgdp.model.TextoParametroDeTarea;
import cl.gob.scj.sgdp.model.TipoDeDocumento;
import cl.gob.scj.sgdp.model.TipoParametroDeTarea;
import cl.gob.scj.sgdp.model.Unidad;
import cl.gob.scj.sgdp.service.CargaDeProcesosService;

@SuppressWarnings({ "unused" })
@Service
@Transactional(rollbackFor = Throwable.class)
public class CargaDeProcesosServiceImpl implements CargaDeProcesosService {

	private final Path root = Paths.get(Constantes.DIRECTORIO_IMAGENES);

	private Map<String, ParseObject> relationsMap = new HashMap<String, ParseObject>();
	private Map<String, ParseObject> rolesMap = new HashMap<String, ParseObject>();
	private Map<String, ParseObject> docsMap = new HashMap<String, ParseObject>();

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	private static final Logger log = Logger.getLogger(CargaDeProcesosServiceImpl.class);

	@Autowired
	private MacroProcesoDao macroProcesoDao;

	@Autowired
	private SuperProcesoDao superProcesoDao;

	@Autowired
	private PerspectivaDao perspectivaDao;

	@Autowired
	private UnidadDao unidadDao;

	@Autowired
	private ProcesoDao procesoDao;

	@Autowired
	private TareaDao tareaDao;

	@Autowired
	private TipoDeDocumentoDao tipoDoctoDao;

	@Autowired
	private ResponsabilidadDao responsabilidadDao;

	@Autowired
	private ReferenciaDeTareaDao refTareaDao;

	@Override
	public CargaProcesoDataInicialDTO getDataInicial(CargaProcesoDataInicialDTO dataInicial) {
		dataInicial.setPerspectivas(getTodasLasPerspectivas(new ArrayList<PerspectivaDTO>()));
		dataInicial.setUnidades(getUnidades(new ArrayList<UnidadDTO>()));
		return dataInicial;
	}

	@Override
	public List<PerspectivaDTO> getTodasLasPerspectivas(List<PerspectivaDTO> perspectivas) {
		List<Perspectiva> pDaolist = perspectivaDao.getTodasLasPerspectivas();
		for (Perspectiva p : pDaolist) {
			perspectivas.add(
					new PerspectivaDTO(p.getIdPerspectiva(), p.getDescripcionPerspectiva(), p.getNombrePerspectiva()));
		}
		return perspectivas;
	}

	@Override
	public List<MacroProcesoDTO> getTodosLosMacroProcesos(Long idperspectiva, List<MacroProcesoDTO> macroProcesosDTO) {
		List<MacroProceso> macroProcesos = macroProcesoDao.getTodosLosMacroProcesos();

		for (MacroProceso macroProceso : macroProcesos) {
			MacroProcesoDTO macroProcesoDTO = new MacroProcesoDTO(macroProceso.getIdMacroProceso(),
					macroProceso.getDescripcionMacroProceso(), macroProceso.getNombreMacroProceso(),
					macroProceso.getPerspectiva().getIdPerspectiva());
			macroProcesosDTO.add(macroProcesoDTO);
		}

		return macroProcesosDTO;
	}

	@Override
	public List<SuperProcesoDTO> getTodosLosSuperProcesos(Long idMacroProceso, List<SuperProcesoDTO> superProcesos) {

		List<SuperProceso> superProcesosDao = superProcesoDao.getTodosLosSuperProcesosByIDMacroProceso(idMacroProceso);

		for (SuperProceso sp : superProcesosDao) {
			SuperProcesoDTO superProcesoDTO = new SuperProcesoDTO();
			superProcesoDTO.setId(sp.getIdSuperProceso());
			superProcesoDTO.setDescripcion(sp.getDescripcionSuperProceso());
			superProcesoDTO.setNombre(sp.getNombreSuperProceso());
			superProcesoDTO.setIdMacroProceso(sp.getMacroProceso().getIdMacroProceso());
			superProcesos.add(superProcesoDTO);
		}

		return superProcesos;
	}

	@Override
	public List<ProcesoDTO> getTodosLosProcesosBySuperProceso(Long idSuperProceso, List<ProcesoDTO> procesos) {
		List<Proceso> procesosDao = procesoDao.getProcesosPorSuperProceso(idSuperProceso);

		for (Proceso p : procesosDao) {
			ProcesoDTO procesoDTO = new ProcesoDTO();
			procesoDTO.setIdProceso(p.getIdProceso());
			procesoDTO.setCodigoProceso(p.getCodigoProceso());
			procesoDTO.setDescripcionProceso(p.getDescripcionProceso());
			procesoDTO.setNombreProceso(p.getNombreProceso());
			procesos.add(procesoDTO);
		}

		return procesos;
	}

	@Override
	public List<UnidadDTO> getUnidades(List<UnidadDTO> unidadesDTO) {
		List<Unidad> unidades = unidadDao.getTodasLasUnidades();
		for (Unidad u : unidades) {
			unidadesDTO.add(new UnidadDTO(u.getIdUnidad(), u.getCodigoUnidad(), u.getNombreCompletoUnidad()));
		}
		return unidadesDTO;
	}

	private Proceso insertarEnBOProceso(ProcesoDTO proceso, Session session) {
		Proceso p = new Proceso();
		p.setArchivoImagen(proceso.getNombreArchivo());
		p.setNombreProceso(proceso.getNombreProceso());
		p.setDescripcionProceso(proceso.getDescripcionProceso());
		p.setTieneRdsSnc(proceso.getTieneParametrosPorTarea());

		MacroProceso mp = new MacroProceso();
		mp.setIdMacroProceso(proceso.getIdMacroproceso());
		p.setMacroProceso(mp);

		SuperProceso sp = new SuperProceso();
		sp.setIdSuperProceso(proceso.getIdSuperProceso());
		p.setSuperProceso(sp);

		p.setVigente(true);
		p.setEsConfidencial(proceso.getPrivado());
		p.setDiasHabilesMaxDuracion(proceso.getDiasHabilesMaxDuracion());
		p.setXml(proceso.getXml());

		Unidad u = new Unidad();
		u.setIdUnidad(proceso.getIdUnidad());
		p.setUnidad(u);

		p.setCodigoProceso(proceso.getCodigoProceso());
		// p.setFechaCreacion(proceso.getFechaCreacion());

		List<Tarea> tareas = new ArrayList<Tarea>();

		// paso1: guardar nombre de proceso
		p.setIdProceso((Long) procesoDao.insertaProceso(p, session));
		return p;
	}

	@SuppressWarnings("finally")
	private String guardarImagen(MultipartFile file, String nombreProceso) {
		String name = "";
		try {
			name = new Date().getTime() + "-" + nombreProceso;
			Files.copy(file.getInputStream(), this.root.resolve(name));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return name;
		}
	}

	private Boolean deshabilitarProceso(Proceso p, Session session) {
		procesoDao.deshabilitaProceso(p.getIdProceso(), p.getCodigoProceso(), session);
		return true;
	}

	private Boolean guardarDocumentos(ProcesoDTO procesoDTO, Session session) {

		for (TipoDeDocumentoDTO doc : procesoDTO.getDocs()) {
			TipoDeDocumento td = new TipoDeDocumento();
			td.setNombreDeTipoDeDocumento(doc.getNombreDeTipoDeDocumento());
			td.setCodTipoDocumento(doc.getCodigoTipoDocumonto());
			td.setConformaExpediente(doc.getConformaExpediente());
			td.setAplicaVisacion(doc.getAplicaVisacion());
			td.setAplicaFEA(doc.getAplicaFEA());
			td.setEsDocumentoConductor(doc.getEsDocumentoConductor());
			td.setNumAuto(doc.isNumeracionAuto());

			td.setIdTipoDeDocumento(tipoDoctoDao.guardar(td, session));
			docsMap.put(doc.getNombreEnDiagrama(), new ParseObject(td, new ArrayList<Object>()));

		}

		return true;

	}

	private Boolean guardarRoles(ProcesoDTO procesoDTO, Session session) {

		for (ResponsabilidadDTO rol : procesoDTO.getRoles()) {
			Responsabilidad r = new Responsabilidad();
			r.setNombreResponsabilidad(rol.getNombre());

			r.setIdResponsabilidad(responsabilidadDao.guardar(r, session));
			rolesMap.put(rol.getNombre(), new ParseObject(r, new ArrayList<Object>()));
		}
		return true;
	}

	private List<Tarea> guardarTarea(ProcesoDTO procesoDTO, Proceso p, Session session) {
		List<Tarea> tareas = new ArrayList<Tarea>();

		for (TareaDTO tarea : procesoDTO.getTareas()) {
			Tarea t = new Tarea();
			t.setProceso(p);
			t.setNombreTarea(tarea.getNombreTarea());
			t.setIdDiagrama(tarea.getIdDiagrama());
			t.setDescripcionTarea(tarea.getDescripcionTarea());
			t.setProceso(p);
			t.setDiasHabilesMaxDuracion(tarea.getDiasHabilesMaxDuracion());
			t.setOrden(tarea.getOrden());
			t.setVigente(tarea.getVigente());
			t.setSoloInformar(tarea.getSoloInformar());

			Etapa etapa = new Etapa();
			etapa.setIdEtapa(tarea.getIdEtapa());
			t.setEtapa(etapa);
			t.setObligatoria(tarea.getObligatoria());
			t.setEsUltimaTarea(tarea.getUlitimaTarea());
			t.setTipoDeBifurcacion(tarea.getTipoBifurcacion());
			t.setPuedeVisarDocumentos(tarea.getVisa() == null ? false : tarea.getVisa());
			t.setPuedeAplicarFEA(tarea.getFea() == null ? false : tarea.getFea());
			t.setAsignaNumDoc(tarea.getNumOc() == null ? false : tarea.getNumOc());
			t.setEsperarResp(tarea.getEsperarRespuesta() == null ? false : tarea.getEsperarRespuesta());
			t.setTipoReseteo(tarea.getTipoReseteo());
			t.setDiasReseteo(tarea.getNumeroDiasReseteo());
			t.setConformaExpediente(tarea.getConformaExpediente() == null ? false : tarea.getConformaExpediente());
			t.setDistribuye(tarea.getDistribuye() == null ? false : tarea.getDistribuye());
			t.setNumAuto(tarea.getNumeracionAuto() == null ? false : tarea.getNumeracionAuto());

			tarea.setIdTarea(tareaDao.guardarTarea(t, session));
			tareas.add(t);

			agruparRelacionesEntreTareas(tarea, t);
			agruparDocumentosYTareas(tarea, t);
			agruparRolesYTareas(tarea, t);

			guardaParametrosDeTarea(tarea, t, session);

		}
		return tareas;
	}

	private Boolean agruparRelacionesEntreTareas(TareaDTO tareaDTO, Tarea tareaModel) {
		List<Object> objectList = new ArrayList<Object>(tareaDTO.getTareaSiguiente());
		relationsMap.put(tareaDTO.getIdDiagrama(),
				new ParseObject(tareaModel, new ArrayList<Object>(tareaDTO.getTareaSiguiente())));

		return true;
	}

	private Boolean agruparDocumentosYTareas(TareaDTO tareaDTO, Tarea tareaModel) {
		if (tareaDTO.getDocs() == null)
			return true;

		for (String d : tareaDTO.getDocs()) {
			ParseObject po = docsMap.get(d);
			if (po != null) {
				List<Object> list = po.getReferencia();
				list.add(tareaModel);
			}
		}
		return true;
	}

	private Boolean agruparRolesYTareas(TareaDTO tareaDTO, Tarea tareaModel) {
		if (tareaDTO.getRoles() == null)
			return true;

		for (String r : tareaDTO.getRoles()) {
			ParseObject po = rolesMap.get(r);
			if (po != null) {
				List<Object> list = po.getReferencia();
				list.add(tareaModel);
			}
		}
		return true;
	}

	private Boolean guardaParametrosDeTarea(TareaDTO tareaDTO, Tarea tareaModel, Session session) {
		if (tareaDTO.getParametros() != null) {
			for (ParametroDeTareaDTO pdtdto : tareaDTO.getParametros()) {
				// Paso 1
				ParametroDeTarea pdt = new ParametroDeTarea();
				pdt.setEsSNC(pdtdto.getEsSNC());
				pdt.setNombreParamTarea(pdtdto.getNombreParamTarea());
				pdt.setVigente(pdtdto.getVigente());
				
				TipoParametroDeTarea tpt = new TipoParametroDeTarea();
				tpt.setIdTipoParametroDeTarea((long) pdtdto.getTipoParametroDeTareaDTO().getIdTipoParametroDeTarea());
				
				pdt.setTipoParametroDeTarea(tpt);
				pdt.setIdParamTarea((Long) session.save(pdt));

				// Paso 2
				ParametroRelacionTarea prt = new ParametroRelacionTarea();
				ParametroRelacionTareaPK prtPk = new ParametroRelacionTareaPK();

				prtPk.setParametroDeTarea(pdt);
				prtPk.setTarea(tareaModel);

				prt.setId(prtPk);

				session.save(prt);

				// Paso 3
				TextoParametroDeTarea txtpt = new TextoParametroDeTarea();

				txtpt.setParametroDeTarea(pdt);
				txtpt.setTexto(pdtdto.getNombreParamTarea());

				session.save(txtpt);
			}
		}

		return true;
	}

	@Override
	public ProcesoDTO guardarProceso(ProcesoDTO procesoDTO) {

		/*
		 * MANTENER ESTE ORDEN DE EJECUCION, LOS NUMEROS DE PASOS SON REFERENCIALES AL
		 * ARCHIVO DE CARGA EN PHP
		 */

		Transaction tx = null;
		Session session = null;
		List<Tarea> tareas = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();

			// 1.1 renombrar foto
			// TODO
			String nombreArchivo = guardarImagen(procesoDTO.getImage(), procesoDTO.getNombreProceso());
			procesoDTO.setNombreArchivo(nombreArchivo);

			// PASO 1
			Proceso procesoModel = insertarEnBOProceso(procesoDTO, session);

			// paso 1.0.1: deshabilitar el proceso antiguo vigente
			deshabilitarProceso(procesoModel, session);

			// paso 3: guardar documentos del proceso
			guardarDocumentos(procesoDTO, session);

			// paso 4: guardar roles del proceso
			guardarRoles(procesoDTO, session);

			// paso2: guardar tareas del proceso
			tareas = guardarTarea(procesoDTO, procesoModel, session);

			// paso 5: guardar relacion entre tareas
			guardarRelacionEntreTareas(relationsMap, session);

			// paso 6: guardar relacion tareas-documentos
			guardarRelacionTareaDocumento(docsMap, session);

			// paso 7: guardar relacion tareas-roles
			guardarRelacionTareaRol(rolesMap, session);

			tx.commit();
			procesoDTO.setImage(null);
			return procesoDTO;
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return null;

	}

	private Boolean guardarRelacionTareaDocumento(Map<String, ParseObject> docsMap, Session session) {
		for (Map.Entry<String, ParseObject> set : docsMap.entrySet()) {
			int i = 0;
			for (Object to : set.getValue().getReferencia()) {
				DocumentoDeSalidaDeTarea docSalida = new DocumentoDeSalidaDeTarea();
				DocumentoDeSalidaDeTareaPK docSalidaPK = new DocumentoDeSalidaDeTareaPK();

				docSalidaPK.setTarea((Tarea) to);
				docSalidaPK.setTipoDeDocumento((TipoDeDocumento) set.getValue().getEntidad());
				docSalidaPK.setOrden(i);

				docSalida.setId(docSalidaPK);

				session.save(docSalida);
				i++;
			}
		}
		return true;
	}

	private Boolean guardarRelacionTareaRol(Map<String, ParseObject> rolesMap, Session sossion) {
		for (Map.Entry<String, ParseObject> set : rolesMap.entrySet()) {
			int i = 0;
			for (Object to : set.getValue().getReferencia()) {
				ResponsabilidadTarea respTarea = new ResponsabilidadTarea();
				ResponsabilidadTareaPK respTareaPK = new ResponsabilidadTareaPK();

				respTareaPK.setTarea((Tarea) to);

				respTareaPK.setResponsabilidad((Responsabilidad) set.getValue().getEntidad());

				respTarea.setId(respTareaPK);

				sossion.saveOrUpdate(respTarea);
				i++;
			}
		}
		return true;
	}

	private Boolean guardarRelacionEntreTareas(Map<String, ParseObject> relationsMap, Session session) {
		for (Map.Entry<String, ParseObject> keySet : relationsMap.entrySet()) {
			Tarea to = (Tarea) keySet.getValue().getEntidad();
			for (Object v : keySet.getValue().getReferencia()) {
				String idDiagrama = (String) v;
				if (relationsMap.containsKey(idDiagrama)) {
					ReferenciaDeTarea ref = new ReferenciaDeTarea();
					ref.setTarea(to);
					ref.setTareaSiguiente((Tarea) relationsMap.get(idDiagrama).getEntidad());
					refTareaDao.guardar(ref, session);
				}
			}
		}
		return true;
	}

}

class ParseObject {
	private Object entidad;
	private List<Object> referencia = new ArrayList<Object>();

	public ParseObject(Object entidad, List<Object> referencia) {
		this.entidad = entidad;
		this.referencia = referencia;
	}

	public Object getEntidad() {
		return entidad;
	}

	public void setEntidad(Object entidad) {
		this.entidad = entidad;
	}

	public List<Object> getReferencia() {
		return referencia;
	}

	public void setReferencia(List<Object> referencia) {
		this.referencia = referencia;
	}

}