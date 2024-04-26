package cl.gob.scj.sgdp.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dao.ListaDeDistribucionDao;
import cl.gob.scj.sgdp.dao.TipoDeDestinatarioDao;
import cl.gob.scj.sgdp.dto.ListaDeDistribucionDTO;
import cl.gob.scj.sgdp.dto.RespuestaCargaListaDistribucionMasivaDTO;
import cl.gob.scj.sgdp.dto.TipoDeDestinatarioDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.model.ListaDeDistribucion;
import cl.gob.scj.sgdp.model.TipoDeDestinatario;
import cl.gob.scj.sgdp.service.ListaDeDistribucionService;
import cl.gob.scj.sgdp.service.LogAccionesUsuarioService;
import cl.gob.scj.sgdp.tipos.AccionType;
import cl.gob.scj.sgdp.tipos.ModuloType;
import cl.gob.scj.sgdp.util.FechaUtil;
import cl.gob.scj.sgdp.util.SGDPUtil;

@Service
@Transactional(rollbackFor = Throwable.class)
public class ListaDeDistribucionServiceImpl implements ListaDeDistribucionService {
	
	private static final Logger log = Logger.getLogger(ListaDeDistribucionServiceImpl.class);
	
	private ListaDeDistribucionDao listaDeDistribucionDao;
	
	private TipoDeDestinatarioDao tipoDeDestinatarioDao;
	
	private LogAccionesUsuarioService logAccionesUsuarioService;
	
	@Resource(name = "configProps")
	private Properties configProps;
	
	@Autowired
	public ListaDeDistribucionServiceImpl(ListaDeDistribucionDao listaDeDistribucionDao,
			TipoDeDestinatarioDao tipoDeDestinatarioDao, LogAccionesUsuarioService logAccionesUsuarioService) {
		super();
		this.listaDeDistribucionDao = listaDeDistribucionDao;
		this.tipoDeDestinatarioDao = tipoDeDestinatarioDao;
		this.logAccionesUsuarioService = logAccionesUsuarioService;
	}

	@Override
	public List<ListaDeDistribucionDTO> getListaDeDistribucion(boolean filtraVigentes) {
		List<ListaDeDistribucion> listaDis = listaDeDistribucionDao.getAll(ListaDeDistribucion.class);
		log.debug("listaDis.size(): " + listaDis.size());
		List<ListaDeDistribucionDTO> listaDisDTO = new ArrayList<ListaDeDistribucionDTO>();
		for (ListaDeDistribucion listaDeDistribucion : listaDis) {
			Date now = new Date();
			if ( 
					filtraVigentes && (
					(listaDeDistribucion.getFechaInicioVigencia() != null
					&& listaDeDistribucion.getFechaFinVigencia() != null
					&& now.compareTo(listaDeDistribucion.getFechaInicioVigencia()) >= 0 
					&& now.compareTo(listaDeDistribucion.getFechaFinVigencia()) <= 0)
					
					|| 
					
					(listaDeDistribucion.getFechaInicioVigencia() == null
					|| listaDeDistribucion.getFechaFinVigencia() == null) 
					) 
				){
				ListaDeDistribucionDTO listaDeDistribucionDTO = new ListaDeDistribucionDTO();
				TipoDeDestinatario tipoTipoDeDestinatario = listaDeDistribucion.getTipoDeDestinatario();
				TipoDeDestinatarioDTO tipoDeDestinatarioDTO = new TipoDeDestinatarioDTO();
				BeanUtils.copyProperties(listaDeDistribucion, listaDeDistribucionDTO);
				if (tipoTipoDeDestinatario!=null) {
					BeanUtils.copyProperties(tipoTipoDeDestinatario, tipoDeDestinatarioDTO);
				}			
				listaDeDistribucionDTO.setTipoDeDestinatarioDTO(tipoDeDestinatarioDTO);
				listaDisDTO.add(listaDeDistribucionDTO);
			} else if (!filtraVigentes) {
				ListaDeDistribucionDTO listaDeDistribucionDTO = new ListaDeDistribucionDTO();
				TipoDeDestinatario tipoTipoDeDestinatario = listaDeDistribucion.getTipoDeDestinatario();
				TipoDeDestinatarioDTO tipoDeDestinatarioDTO = new TipoDeDestinatarioDTO();
				BeanUtils.copyProperties(listaDeDistribucion, listaDeDistribucionDTO);
				if (tipoTipoDeDestinatario!=null) {
					BeanUtils.copyProperties(tipoTipoDeDestinatario, tipoDeDestinatarioDTO);
				}			
				listaDeDistribucionDTO.setTipoDeDestinatarioDTO(tipoDeDestinatarioDTO);
				listaDisDTO.add(listaDeDistribucionDTO);
			}
		}
		return listaDisDTO;
	}
	
	@Override
	public void borrarRegistroDeListaDeDistribucion(long idListaDeDistribucion, String motivo, Usuario usuario) throws JsonProcessingException {
		log.debug("Inicio borrarRegistroDeListaDeDistribucion");
		log.debug("idListaDeDistribucion: " + idListaDeDistribucion);
		ListaDeDistribucion listaDeDistribucion = listaDeDistribucionDao.find(idListaDeDistribucion);
		log.debug(listaDeDistribucion.toString());
		ListaDeDistribucionDTO listaDeDistribucionDTO = new ListaDeDistribucionDTO();
		BeanUtils.copyProperties(listaDeDistribucion, listaDeDistribucionDTO);
		listaDeDistribucionDTO.setMotivo(motivo);
		listaDeDistribucionDao.delete(listaDeDistribucion);
		logAccionesUsuarioService.guardaLogAccionesUsuario(usuario, listaDeDistribucionDTO, AccionType.BORRA_REGISTRO_LISTA_DISTRIBUCION.getNombreAccion(),
				ModuloType.LISTA_DE_DISTRIBUCION.getNombreModulo());
	}

	@Override
	public ListaDeDistribucionDTO getRegistroDeListaDeDistribucionPorId(long idListaDeDistribucion) {		
		ListaDeDistribucion listaDeDistribucion = listaDeDistribucionDao.find(idListaDeDistribucion);
		TipoDeDestinatario tipoDeDestinatario = listaDeDistribucion.getTipoDeDestinatario();
		TipoDeDestinatarioDTO tipoDeDestinatarioDTO = new TipoDeDestinatarioDTO();
		if (tipoDeDestinatario!=null) {
			BeanUtils.copyProperties(tipoDeDestinatario, tipoDeDestinatarioDTO);
		}
		ListaDeDistribucionDTO listaDeDistribucionDTO = new ListaDeDistribucionDTO();
		BeanUtils.copyProperties(listaDeDistribucion, listaDeDistribucionDTO);
		listaDeDistribucionDTO.setTipoDeDestinatarioDTO(tipoDeDestinatarioDTO);
		listaDeDistribucionDTO.setIdTipoDestinatario(tipoDeDestinatarioDTO.getIdTipoDestinatario());
		log.debug(listaDeDistribucionDTO.toString());
		return listaDeDistribucionDTO;
	}
	
	@Override
	public void actualizaRegistroDeListaDeDistribucion(ListaDeDistribucionDTO listaDeDistribucionDTO, Usuario usuario) throws JsonProcessingException, SgdpException {
		List<ListaDeDistribucion> ListaDeDistribucionList = listaDeDistribucionDao.getListaDistribucionPorEmailIdDistinto(listaDeDistribucionDTO.getEmail(), listaDeDistribucionDTO.getIdListaDeDistribucion());
		if (ListaDeDistribucionList!=null && !ListaDeDistribucionList.isEmpty()) {
			throw new SgdpException(configProps.getProperty("sgdp.respuesta.destinatario-ya-existe-otro-destinatario-con-el-mismo-correo"));
		}
		ListaDeDistribucion listaDeDistribucion = listaDeDistribucionDao.find(listaDeDistribucionDTO.getIdListaDeDistribucion());
		TipoDeDestinatario tipoDeDestinatario= tipoDeDestinatarioDao.find(Long.valueOf(listaDeDistribucionDTO.getIdTipoDestinatario()));
		listaDeDistribucion.setTipoDeDestinatario(tipoDeDestinatario);
		listaDeDistribucionDTO.setFechaCreacion(listaDeDistribucion.getFechaCreacion());
		listaDeDistribucionDTO.setIdUsuarioCreacion(listaDeDistribucion.getIdUsuarioCreacion());
		BeanUtils.copyProperties(listaDeDistribucionDTO, listaDeDistribucion);
		listaDeDistribucion.setIdUsuarioUltimaModificacion(usuario.getIdUsuario());
		listaDeDistribucion.setFechaUltimaModificacion(new Date());
		logAccionesUsuarioService.guardaLogAccionesUsuario(usuario, listaDeDistribucionDTO, AccionType.ACTUALIZA_REGISTRO_LISTA_DISTRIBUCION.getNombreAccion(),
				ModuloType.LISTA_DE_DISTRIBUCION.getNombreModulo());
	}
	
	@Override
	public void creaRegistroDeListaDeDistribucion(ListaDeDistribucionDTO listaDeDistribucionDTO, Usuario usuario) throws JsonProcessingException, IOException, SgdpException {
		List<ListaDeDistribucion> ListaDeDistribucionList = listaDeDistribucionDao.getListaDistribucionPorEmail(listaDeDistribucionDTO.getEmail());
		if (ListaDeDistribucionList!=null && !ListaDeDistribucionList.isEmpty()) {
			throw new SgdpException(configProps.getProperty("sgdp.respuesta.destinatario-ya-existe"));
		}
		ListaDeDistribucion listaDeDistribucion = new ListaDeDistribucion();
		TipoDeDestinatario tipoDeDestinatario = tipoDeDestinatarioDao.find(Long.valueOf(listaDeDistribucionDTO.getIdTipoDestinatario()));
		if (tipoDeDestinatario==null) {
			throw new SgdpException(configProps.getProperty("sgdp.respuesta.el-tipo-de-destinatario-no-puede-ser-vacio"));
		}
		listaDeDistribucion.setTipoDeDestinatario(tipoDeDestinatario);
		BeanUtils.copyProperties(listaDeDistribucionDTO, listaDeDistribucion);
		listaDeDistribucion.setIdUsuarioCreacion(usuario.getIdUsuario());
		listaDeDistribucion.setFechaCreacion(new Date());
		log.debug("FechaUtil.diasEntreFechas: " + FechaUtil.diasEntreFechas(listaDeDistribucion.getFechaInicioVigencia(), listaDeDistribucion.getFechaFinVigencia()));
		if (FechaUtil.diasEntreFechas(listaDeDistribucion.getFechaFinVigencia(), listaDeDistribucion.getFechaInicioVigencia()) < 1) {
			throw new SgdpException(configProps.getProperty("sgdp.respuesta.fecha-fin-mayor-fecha-inicio"));
		}
		listaDeDistribucionDao.insert(listaDeDistribucion);
		logAccionesUsuarioService.guardaLogAccionesUsuario(usuario, listaDeDistribucionDTO, AccionType.CREA_REGISTRO_LISTA_DISTRIBUCION.getNombreAccion(),
				ModuloType.LISTA_DE_DISTRIBUCION.getNombreModulo());
	}

	@Override
	public List<ListaDeDistribucionDTO> getListaDistribucionPorIdTipoDestinatario(long idTipoDestinatario) {
		List<ListaDeDistribucionDTO> listaDeDistribucionDTOL  = new ArrayList<>();
		List<ListaDeDistribucion> listaDistribucionPorTipoDest = listaDeDistribucionDao.getListaDistribucionPorIdTipoDestinatario(idTipoDestinatario);
		for (ListaDeDistribucion listaDeDistribucion : listaDistribucionPorTipoDest) {
			ListaDeDistribucionDTO listaDeDistribucionDTO = new ListaDeDistribucionDTO();
			BeanUtils.copyProperties(listaDeDistribucion, listaDeDistribucionDTO);
			listaDeDistribucionDTOL.add(listaDeDistribucionDTO);
		}
		return listaDeDistribucionDTOL;
	}
	
	@Override
	public RespuestaCargaListaDistribucionMasivaDTO cargaListaDistribucionMasivo(ListaDeDistribucionDTO listaDeDistribucionDTO, Usuario usuario) throws Exception, SgdpException {
		RespuestaCargaListaDistribucionMasivaDTO respuestaCargaListaDistribucionMasivaDTO = new RespuestaCargaListaDistribucionMasivaDTO();
		List<ListaDeDistribucionDTO> listaDeDistribucionDTOResultadoCargaMasiva = new ArrayList<ListaDeDistribucionDTO>();
		List<ListaDeDistribucionDTO> listaDeDistribucionDTOs = readExcelFile(listaDeDistribucionDTO.getArchivo());
		for (ListaDeDistribucionDTO listaDeDistribucionDTOA : listaDeDistribucionDTOs) {
			try {
				listaDeDistribucionDTOA.setIdTipoDestinatario(tipoDeDestinatarioDao.getTipoDeDestinatarioPorNombre(listaDeDistribucionDTOA.getNombreTipoDestinatario()).getIdTipoDestinatario());
				creaRegistroDeListaDeDistribucion(listaDeDistribucionDTOA, usuario);
				listaDeDistribucionDTOA.setFechaInicioVigenciaS(FechaUtil.toFormat(listaDeDistribucionDTOA.getFechaInicioVigencia(), FechaUtil.simpleDateFormat));
				listaDeDistribucionDTOA.setFechaFinVigenciaS(FechaUtil.toFormat(listaDeDistribucionDTOA.getFechaFinVigencia(), FechaUtil.simpleDateFormat));
			} catch (Exception e) {
				log.warn("Error al cargar el registro desde excel: " + listaDeDistribucionDTOA.toString() + " " + e.getMessage());
				log.warn(SGDPUtil.getStackTrace(e));
				listaDeDistribucionDTOA.setErrorAlGuardarRegistroMasivo(true);
				listaDeDistribucionDTOA.setMensajeErrorAlGuardarRegistroMasivo(e.getMessage());
			}
			listaDeDistribucionDTOResultadoCargaMasiva.add(listaDeDistribucionDTOA);
		}
		respuestaCargaListaDistribucionMasivaDTO.setListaDeDistribucionDTOResultadoCargaMasiva(listaDeDistribucionDTOResultadoCargaMasiva);
		respuestaCargaListaDistribucionMasivaDTO.setListaDeDistribucionDTO(getListaDeDistribucion(false));
		respuestaCargaListaDistribucionMasivaDTO.setCssStatus(configProps.getProperty("cssSucess"));
		return respuestaCargaListaDistribucionMasivaDTO;
	}
	
	private List<ListaDeDistribucionDTO> readExcelFile(MultipartFile file) throws Exception {
		List<ListaDeDistribucionDTO> listaDeDistribucionDTOs = new ArrayList<>();
		HSSFWorkbook workbook = null;
		try {
			workbook = new HSSFWorkbook(file.getInputStream());
	        Sheet sheet = workbook.getSheetAt(0);
	        for (Row row : sheet) {
	            if (row.getRowNum() == 0) {
	                continue;
	            }
	            log.debug("row.getCell(0).getStringCellValue(): " + row.getCell(0).getStringCellValue());
	            ListaDeDistribucionDTO listaDeDistribucionDTO = new ListaDeDistribucionDTO();
	            try {
	            	listaDeDistribucionDTO.setNombreCompleto(getStringCellValue(row.getCell(0)));
		            listaDeDistribucionDTO.setEmail(getStringCellValue(row.getCell(1)));
		            listaDeDistribucionDTO.setOrganizacion(getStringCellValue(row.getCell(2)));
		            listaDeDistribucionDTO.setCargo(getStringCellValue(row.getCell(3)));
		            listaDeDistribucionDTO.setFechaInicioVigencia(getDateCellValue(row.getCell(4)));
		            listaDeDistribucionDTO.setFechaFinVigencia(getDateCellValue(row.getCell(5)));
		            listaDeDistribucionDTO.setNumeroTelefono1(getLongCellValue(row.getCell(6)));
		            listaDeDistribucionDTO.setNumeroTelefono2(getLongCellValue(row.getCell(7)));
		            listaDeDistribucionDTO.setNombreTipoDestinatario(getStringCellValue(row.getCell(8)));
	            } catch (Exception e) {
	            	log.warn("Error al leer el registro desde excel: " + listaDeDistribucionDTO.toString() + " " + e.getMessage());
	            	listaDeDistribucionDTO.setErrorAlLeerRegistroMasivo(true);
	            	listaDeDistribucionDTO.setMensajeErrorAlLeerRegistroMasivo(e.getMessage());
	            }
	            listaDeDistribucionDTOs.add(listaDeDistribucionDTO);
	        } 
		} finally {
			if (workbook != null) {
                workbook.close();
            }
		}
        return listaDeDistribucionDTOs;
    }
	
	private String getStringCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        cell.setCellType(Cell.CELL_TYPE_STRING);
        return cell.getStringCellValue();
    }

    private Date getDateCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        return cell.getDateCellValue();
    }

    private Long getLongCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
        return (long) cell.getNumericCellValue();
    }

}
