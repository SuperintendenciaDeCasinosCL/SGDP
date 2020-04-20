package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.ArchivoInfoDTO;
import cl.gob.scj.sgdp.dto.ArchivosInstDeTareaDTO;
import cl.gob.scj.sgdp.dto.DetalleDeArchivoDTO;
import cl.gob.scj.sgdp.dto.InstanciaDeTareaDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.model.InstanciaDeProceso;

@Service
public interface ObtenerArchivosExpedienteService {

	List<ArchivoInfoDTO> obtenerArchivosExpediente(Usuario usuario, String idExpediente, boolean filtraFirmas, boolean tareaPuedeVisarDocumentos, boolean tareaPuedeAplicarFEA, long idInstanciaDeTarea) throws SgdpException;
	
	void cargaAplicaFirmas(ArchivoInfoDTO archivoInfoDTO, boolean tareaPuedeVisarDocumentos, boolean tareaPuedeAplicarFEA, String idExpediente, long idInstanciaDeTarea) throws SgdpException;
	
	DetalleDeArchivoDTO obtenerDetalleDeArchivoDTOPorIdExpedienteIdUsuarioIdTipoDeDocumento(String idExpediente, Usuario usuario, long idTipoDeDocumento, long idInstanciaDeTarea) throws Exception;

    public boolean archivoEsEditable(String MimeType);	
    
    public int archivoCodigoMineType(String MimeType);	
    
    void cargaArchivosInfoDTODeSalidaPorIdInstanciaDeTarea(Usuario usuario, InstanciaDeTareaDTO instanciaDeTareaDTO, List<ArchivoInfoDTO> archivosInfoDTODeSalidaPorIdInstanciaDeTarea, List<ArchivoInfoDTO> archivosExpedienteDTO) throws SgdpException;

    void cargaDetalleDeArchivosDTOPorIdExpedienteIdUsuarioIdTipoDeDocumento(String idExpediente, 
			Usuario usuario, 
			long idTipoDeDocumento,
			List<DetalleDeArchivoDTO> detalleDeArchivosDTOPorIdExpedienteIdUsuarioIdTipoDeDocumento,
			boolean puedeVisarDocumentos,
			boolean puedeAplicarFEA,
			long idInstanciaDeTarea) throws Exception;
    
    List<ArchivoInfoDTO> getListaArchivoInfoDTOAdjuntos(Usuario usuario, String nombreArchivo, List<ArchivoInfoDTO> listaArchivoInfoDTO);
    
    void cargaArchivosObligatoriosDeInstDeTareaAnteriorPorIdInstanTarea(Usuario usuario, 
    		long idInstanciaDeTarea, 
    		List<DetalleDeArchivoDTO> detalleDeArchDTOObligatoriosDeInstDeTareaAntPorIdInstTarea,
    		List<ArchivoInfoDTO> archivosInfoDTODeSalidaPorIdInstanciaDeTarea) throws Exception;

    public List<String> listaDocumentosFirmado(InstanciaDeProceso instanciaDeProceso);
    
    void cargaDetalleDeArchivosDTO(String idExpediente, 
			Usuario usuario, 
			long idTipoDeDocumento,
			List<DetalleDeArchivoDTO> detalleDeArchivosDTOPorIdExpedienteIdUsuarioIdTipoDeDocumento,
			boolean puedeVisarDocumentos,
			boolean puedeAplicarFEA,
			long idInstanciaDeTarea) throws Exception;
    
    void cargaArchivosObligatoriosDeInstDeTareaAnteriorPorIdInstanTarea(Usuario usuario, 
			InstanciaDeTareaDTO instanciaDeTareaDTO, 
			List<DetalleDeArchivoDTO> detalleDeArchDTOObligatoriosDeInstDeTareaAntPorIdInstTarea
			) throws Exception;
    
    void filtraPorNumeroDeDocumento(Usuario usuario, List<ArchivosInstDeTareaDTO> listaDeDocumentos) throws Exception;

}
