package cl.gob.scj.sgdp.converter;

import java.text.ParseException;

import cl.gob.scj.sgdp.dto.SubirArhivoDTO;
import cl.gob.scj.sgdp.enums.TipoEnum;
import cl.gob.scj.sgdp.model.ArchivosInstDeTareaMetadata;
import cl.gob.scj.sgdp.model.Tipo;
import cl.gob.scj.sgdp.util.FechaUtil;

public class SubirArchivoConverter {

	public static ArchivosInstDeTareaMetadata subirDocumentoToArchivoTareaMetadata(SubirArhivoDTO subirArhivoDTO) {
		ArchivosInstDeTareaMetadata entity = new ArchivosInstDeTareaMetadata();
		entity.setAutor(subirArhivoDTO.getAutor());
		entity.setTitulo(subirArhivoDTO.getTitulo());
		entity.setDigitalizado(subirArhivoDTO.isDigitalizado());
		if (subirArhivoDTO.getDestinatarios()!= null && !subirArhivoDTO.getDestinatarios().isEmpty()) {
			entity.setDestinatarios(subirArhivoDTO.getDestinatarios());
		}
		if (subirArhivoDTO.getTipoGestion()!= null && !subirArhivoDTO.getTipoGestion().isEmpty()) {
			entity.setTipo(new Tipo());
			entity.getTipo().setIdTipo(Long.parseLong(subirArhivoDTO.getTipoGestion()));	
		}
		
		if (TipoEnum.DOCUMENTO.getIdString().equals(subirArhivoDTO.getTipoGestion())) {
			if (subirArhivoDTO.getNombreInteresado()!= null && !subirArhivoDTO.getNombreInteresado().isEmpty()) {
				entity.setNombreInteresado(subirArhivoDTO.getNombreInteresado());	
			}
			if (subirArhivoDTO.getApellidoPaterno()!= null && !subirArhivoDTO.getApellidoPaterno().isEmpty()) {
				entity.setApellidoPaterno(subirArhivoDTO.getApellidoPaterno());	
			}
			if (subirArhivoDTO.getApellidoMaterno()!= null && !subirArhivoDTO.getApellidoMaterno().isEmpty()) {
				entity.setApellidoMaterno(subirArhivoDTO.getApellidoMaterno());	
			}
			if (subirArhivoDTO.getRut() != null && !subirArhivoDTO.getRut().isEmpty()) {
				entity.setRut(subirArhivoDTO.getRut());	
			}
			entity.setDigitalizado(subirArhivoDTO.isDigitalizado());
			try {
				entity.setFechaDocumento(FechaUtil.simpleDateFormatForm.parse(subirArhivoDTO.getFechaCreacionArchivo()));
			} catch (ParseException e) {
				entity.setFechaDocumento(null);
			}
			
			//TECNOVA: SE QUITA LA FECHA DE CAPTURA
//			if (subirArhivoDTO.getFechaCapturaDocumento()!= null && !subirArhivoDTO.getFechaCapturaDocumento().isEmpty()) {
//				try {
//					entity.setFechaCaptura(FechaUtil.simpleDateFormatForm.parse(subirArhivoDTO.getFechaCapturaDocumento()));
//				} catch (ParseException e) {
//					entity.setFechaCaptura(null);
//				}
//			}
			if (subirArhivoDTO.getRegion()!= null && !subirArhivoDTO.getRegion().isEmpty()) {
				entity.setRegion(subirArhivoDTO.getRegion());	
			}
			if (subirArhivoDTO.getComuna() != null && !subirArhivoDTO.getComuna().isEmpty()) {
				entity.setComuna(subirArhivoDTO.getComuna());	
			}
			if (subirArhivoDTO.getListaMetadata() != null && !subirArhivoDTO.getListaMetadata().isEmpty()) {
				entity.setMetadataCustom(subirArhivoDTO.getListaMetadata());
			}
			if (subirArhivoDTO.getEtiquetasDocumento()!= null && !subirArhivoDTO.getEtiquetasDocumento().isEmpty()) {
				entity.setEtiquetas(subirArhivoDTO.getEtiquetasDocumento().replace(",", "||%"));
			}
			if (subirArhivoDTO.getDestinatarios() != null && !subirArhivoDTO.getDestinatarios().isEmpty()) {
				entity.setDestinatarios(subirArhivoDTO.getDestinatarios().replace(",", "||%"));
			}

		}
		return entity;
	}

}
