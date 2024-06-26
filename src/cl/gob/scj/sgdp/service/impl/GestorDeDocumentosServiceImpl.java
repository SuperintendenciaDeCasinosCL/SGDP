package cl.gob.scj.sgdp.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
	
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImage;
import com.itextpdf.text.pdf.PdfIndirectObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dao.ArchivosInstDeTareaDao;
import cl.gob.scj.sgdp.dao.HistoricoFirmaDao;
import cl.gob.scj.sgdp.dao.InstanciaDeProcesoDao;
import cl.gob.scj.sgdp.dao.InstanciaDeTareaDao;
import cl.gob.scj.sgdp.dao.TipoDeDocumentoDao;
import cl.gob.scj.sgdp.dto.ArchivosInstDeTareaDTO;
import cl.gob.scj.sgdp.dto.DetalleDeArchivoDTO;
import cl.gob.scj.sgdp.dto.FirmaAvanzadaDTO;
import cl.gob.scj.sgdp.dto.HistoricoFirmaDTO;
import cl.gob.scj.sgdp.dto.KeyParametroPorContextoDTO;
import cl.gob.scj.sgdp.dto.ParametroDTO;
import cl.gob.scj.sgdp.dto.ParametroPorContextoDTO;
import cl.gob.scj.sgdp.dto.RespuestaConversionArchivoDTO;
import cl.gob.scj.sgdp.dto.SubirArhivoDTO;
import cl.gob.scj.sgdp.dto.rest.BorraRegistroDocumentoRequestRest;
import cl.gob.scj.sgdp.dto.rest.BorraRegistroDocumentoResponseRest;
import cl.gob.scj.sgdp.dto.rest.GeneraRegistroDocumentoRequestRest;
import cl.gob.scj.sgdp.dto.rest.GeneraRegistroDocumentoResponseRest;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.model.ArchivosInstDeTarea;
import cl.gob.scj.sgdp.model.HistoricoFirma;
import cl.gob.scj.sgdp.model.InstanciaDeProceso;
import cl.gob.scj.sgdp.model.InstanciaDeTarea;
import cl.gob.scj.sgdp.model.TipoDeDocumento;
import cl.gob.scj.sgdp.service.GestorDeDocumentosService;
import cl.gob.scj.sgdp.service.HistoricoFirmaService;
import cl.gob.scj.sgdp.service.ObtenerDetalleDeArchivoService;
import cl.gob.scj.sgdp.service.ParametroPorContextoService;
import cl.gob.scj.sgdp.service.ParametroService;
import cl.gob.scj.sgdp.service.RegistroDocumentoService;
import cl.gob.scj.sgdp.service.SubirArchivoService;
import cl.gob.scj.sgdp.tipos.FirmaType;
import cl.gob.scj.sgdp.util.EncriptacionUtil;
import cl.gob.scj.sgdp.util.FechaUtil;
import cl.gob.scj.sgdp.util.FileUtil;
import cl.gob.scj.sgdp.util.SgdpMultipartFile;
import cl.gob.scj.sgdp.util.StringUtilSGDP;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.GestorDeDocumentosCMSService;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.SubirArchivoCMSService;
import cl.gob.scj.sgdp.ws.alfresco.rest.response.ArchivoImagenQRPorUsuarioResponse;
import cl.gob.scj.sgdp.ws.alfresco.rest.response.FirmaSimpleDocumentoResponse;
import cl.gob.scj.sgdp.ws.alfresco.rest.response.IdArchivoPorIdUsrNomCarpetaResponse;
import cl.gob.scj.sgdp.ws.alfresco.rest.response.SubirArchivoResponse;
import cl.gob.scj.sgdp.ws.firmaElectronica.rest.client.FirmaAvanzadaInterService;
import cl.gob.scj.sgdp.ws.firmaElectronica.rest.request.FirmaAvanzadaArchivoRequest;
import cl.gob.scj.sgdp.ws.firmaElectronica.rest.request.FirmaAvanzadaRequest;
import cl.gob.scj.sgdp.ws.firmaElectronica.rest.response.FileV3;
import cl.gob.scj.sgdp.ws.firmaElectronica.rest.response.FirmaAvanzadaMinSegPresResponseV3;
import java.awt.image.BufferedImage;


@Service
@Transactional(rollbackFor = Throwable.class)
public class GestorDeDocumentosServiceImpl implements GestorDeDocumentosService {

	private static final Logger log = Logger.getLogger(GestorDeDocumentosServiceImpl.class);	
	
	@Resource(name = "configProps")
	private Properties configProps;
	
	@Autowired
	private GestorDeDocumentosCMSService gestorDeDocumentosCMSService;
	
	@Autowired
	FirmaAvanzadaInterService firmaAvanzadaInterService;
	
	@Autowired
	ParametroPorContextoService parametroPorContextoService;
	
	@Autowired
	ParametroService parametroService;
	
	@Autowired
	private SubirArchivoService subirArchivoService;

	@Autowired
	private HistoricoFirmaDao historicoFirmaDao;
	
	@Autowired
	private InstanciaDeTareaDao instanciaDeTareaDao;
	
	@Autowired
	private TipoDeDocumentoDao tipoDeDocumentoDao;
	
	@Autowired
	private ArchivosInstDeTareaDao archivosInstDeTareaDao;	

	@Autowired
	private ObtenerDetalleDeArchivoService obtenerDetalleDeArchivoService;
	
	@Autowired
	private SubirArchivoCMSService subirArchivoCMSService;
	
	@Autowired
	private InstanciaDeProcesoDao instanciaDeProcesoDao;
	
	@Autowired
	private RegistroDocumentoService registroDocumentoService;
	
	@Autowired
	private HistoricoFirmaService historicoFirmaService;
	
	private FirmaType firmaTypeVisacion = FirmaType.VISACION;
	
	private FirmaType firmaTypeCentralizado = FirmaType.CENTRALIZADO;
		
	@Override
	public FirmaSimpleDocumentoResponse firmaSimpleDocumento(String idDocumento, Usuario usuario, long idInstanciaDeTarea, long idTipoDeDocumento) throws SgdpException {		
		log.debug("Inicio firmaSimpleDocumento");
		try {
			FirmaSimpleDocumentoResponse firmaSimpleDocumentoResponse = gestorDeDocumentosCMSService.firmaSimpleDocumento(idDocumento, usuario);
			HistoricoFirmaDTO historicoFirmaDTO = new HistoricoFirmaDTO();
			historicoFirmaDTO.setFechaFirma(new Date());
			historicoFirmaDTO.setIdArchivoCMS(idDocumento);
			historicoFirmaDTO.setIdInstanciaDeTarea(idInstanciaDeTarea);
			historicoFirmaDTO.setIdUsuario(usuario.getIdUsuario());
			historicoFirmaDTO.setTipoFirma(firmaTypeVisacion);
			historicoFirmaDTO.setIdTipoDeDocumento(idTipoDeDocumento);
			registraFirma(usuario.getIdUsuario(), historicoFirmaDTO);
			return firmaSimpleDocumentoResponse;
		} catch (Exception e) {
			throw new SgdpException(configProps.getProperty("errorAlAplicarFirmaSimpleADocumento"));
		}			
	}
	
	@Override
	public String getIdArchivoImagenQRPorUsuario(Usuario usuario) throws SgdpException {		
		log.debug("Inicio getIdArchivoImagenQRPorUsuario");
		ArchivoImagenQRPorUsuarioResponse archivoImagenQRPorUsuarioResponse;
		try {
			archivoImagenQRPorUsuarioResponse = gestorDeDocumentosCMSService.getArchivoImagenQRPorUsuario(usuario);
		} catch(Exception e) {
			throw new SgdpException("errorArchivoQR");
		}		
		return archivoImagenQRPorUsuarioResponse.getIdArchivo();		
	}

	@Override
	public byte[] getContenidoArchivo(String idArchivo, Usuario usuario)
			throws SgdpException {
		try {
			return gestorDeDocumentosCMSService.getContenidoArchivo(idArchivo, usuario);
		} catch (Exception e) {
			throw new SgdpException(configProps.getProperty("errorAlObtenerContenidoArchivo"));
		}
	}

	@Override
	public FirmaAvanzadaDTO firmarDocumentoConFEA(
			FirmaAvanzadaRequest firmaAvanzadaRequest,
			FirmaAvanzadaArchivoRequest firmaAvanzadaArchivoRequest,
			FirmaAvanzadaDTO firmaAvanzadaDTO,
			Usuario usuario,
			KeyParametroPorContextoDTO keyParametroPorContextoDTOTipoDocFeaPorMimeTypeEnCMS) {
		
		SubirArhivoDTO subirArhivoDTO = new SubirArhivoDTO();	
		SgdpMultipartFile sgdpMultipartFile = new SgdpMultipartFile();
		
		GeneraRegistroDocumentoRequestRest generaRegistroDocumentoRequestRest;
		GeneraRegistroDocumentoResponseRest generaRegistroDocumentoResponseRest;
		boolean puedeBorrarRegistroDoc = false;
		String codTipoDocumento = "";
		Long numeroDocRegistro = 0l;
		
		try {
			
			InstanciaDeProceso instanciaDeProceso = instanciaDeProcesoDao.getInstanciaDeProcesoPorIdExpediente(firmaAvanzadaDTO.getIdExpediente());
			InstanciaDeTarea instanciaDeTarea = instanciaDeTareaDao.getInstanciaDeTareaPorIdInstanciaDeTarea(firmaAvanzadaDTO.getIdInstanciaDeTarea());
			TipoDeDocumento tipoDeDocumento = tipoDeDocumentoDao.getTipoDeDocumentoPorIdTipoDeDocumento(firmaAvanzadaDTO.getIdTipoDeDocumento());
			
			boolean numeraAutoTipoDoc = (tipoDeDocumento.getNumAuto()!=null) ? tipoDeDocumento.getNumAuto() : false;
			boolean numeraAutoTarea = (instanciaDeTarea.getTarea().getNumAuto()!=null) ? instanciaDeTarea.getTarea().getNumAuto() : false;
		
			codTipoDocumento = tipoDeDocumento.getCodTipoDocumento();
			
			firmaAvanzadaDTO.setProposito(URLDecoder.decode(firmaAvanzadaDTO.getProposito(), parametroService.getParametroPorID(Constantes.ID_PARAM_ENCODE_CHARACTER_TRANSFORMATION_FEA).getValorParametroChar()));
			
			ParametroPorContextoDTO parametroPorContextoDTO = parametroPorContextoService.getParamPorContexto(keyParametroPorContextoDTOTipoDocFeaPorMimeTypeEnCMS);
			
			ParametroDTO parametroDTOIdLogoSCJ = parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_ID_LOGO_SCJ_PARA_FIRMA);
			
			log.debug(parametroPorContextoDTO.toString());
			log.debug(firmaAvanzadaDTO.toString());
			
			firmaAvanzadaArchivoRequest.setType(parametroPorContextoDTO.getValorParametroChar());
			firmaAvanzadaArchivoRequest.setDescription(firmaAvanzadaDTO.getNombreArchivo());
		
			byte[] byteArchivo = gestorDeDocumentosCMSService.getContenidoArchivo(firmaAvanzadaDTO.getIdDocumento(), usuario);			
			
			byte[] byteArchivoImagenLogoSCJ = gestorDeDocumentosCMSService.getContenidoArchivo(parametroDTOIdLogoSCJ.getValorParametroChar(), usuario);
			
			ByteArrayOutputStream ba = new ByteArrayOutputStream();
			
			PdfReader reader = new PdfReader(byteArchivo);
			PdfStamper stamper = new PdfStamper(reader, ba);	
			PdfContentByte over = stamper.getOverContent(reader.getNumberOfPages());
			

			String colocaImagenFea = parametroService.getParametroPorID(Constantes.ID_PARAM_COLOCA_IMAGEN_DE_FIRMA).getValorParametroChar();

			//String textoVerificaValidezFea = parametroService.getParametroPorID(Constantes.ID_PARAM_URL_VERIFICACION_DOC_FEA).getValorParametroChar();
			
			Font font = FontFactory.getFont(FontFactory.HELVETICA, 9); 
		
			if (colocaImagenFea!=null && colocaImagenFea.equals("SI")) {
				
				/*Agrega imagen de la firma en la ultima pagina*/
				
				ParametroDTO parametroDTONombreCarpetaImagenesFEA = parametroService.getParametroPorID(Constantes.ID_PARAM_NOMBRE_CARPETA_IMAGENES_FEA);
				String nombreCarpetaImagenesFEA = parametroDTONombreCarpetaImagenesFEA.getValorParametroChar();
				
				IdArchivoPorIdUsrNomCarpetaResponse idArchivoPorIdUsrNomCarpetaResponse = 
						gestorDeDocumentosCMSService.getIdArchivoPorIdUsrNomCarpeta(usuario, nombreCarpetaImagenesFEA);
				
				log.debug(idArchivoPorIdUsrNomCarpetaResponse.toString());				
				
				byte[] byteArchivoImagenFEA = gestorDeDocumentosCMSService.getContenidoArchivo(idArchivoPorIdUsrNomCarpetaResponse.getIdArchivo(), usuario);
				Image img = Image.getInstance(byteArchivoImagenFEA);		
				PdfImage stream = new PdfImage(img, "", null);            
	            PdfIndirectObject ref = stamper.getWriter().addToBody(stream);
	            img.setDirectReference(ref.getIndirectReference());
	            img.setAbsolutePosition(215, 55);            
	            img.scalePercent(30);     	            
	            over.addImage(img); 
			}			   
	            
            //QR	           
            String uuid = UUID.randomUUID().toString();	            
            ParametroDTO parametroDTODescargaDocumento = parametroService.getParametroPorID(Constantes.ID_PARAM_URL_DESCARGA_DOCUMENTO);	            
            String urlDescarga = parametroDTODescargaDocumento.getValorParametroChar() + uuid;	           
            
            BitMatrix matrix;
            Writer writer = new QRCodeWriter();
            matrix = writer.encode(urlDescarga, BarcodeFormat.QR_CODE, 145, 145);	            
            BufferedImage imagen = new BufferedImage(145, 145, BufferedImage.TYPE_INT_RGB);
            
            for(int y = 0; y < 145; y++) {
                for(int x = 0; x < 145; x++) {
                    int grayValue = (matrix.get(x, y) ? 0 : 1) & 0xff;
                    imagen.setRGB(x, y, (grayValue == 0 ? 0 : 0xFFFFFF));
                }
            }
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(imagen, "png", baos);
            Image imagenQR = Image.getInstance(baos.toByteArray());	            
            imagenQR.setAbsolutePosition(215, 4);            
            imagenQR.scalePercent(35);
            over.addImage(imagenQR); 	            	            
            //QR
            
            if (numeraAutoTipoDoc == true && numeraAutoTarea == true) {  
            	
            	generaRegistroDocumentoRequestRest = new GeneraRegistroDocumentoRequestRest();
            	generaRegistroDocumentoRequestRest.setExpDoc(firmaAvanzadaDTO.getNombreExpediente());
            	generaRegistroDocumentoRequestRest.setFechaDocS(FechaUtil.simpleDateFormat.format(new Date()));
            	generaRegistroDocumentoRequestRest.setFechaTramitacionS(FechaUtil.simpleDateFormat.format(new Date()));
            	generaRegistroDocumentoRequestRest.setUsuario(usuario.getIdUsuario());
            	generaRegistroDocumentoRequestRest.setCodTipoDoc(tipoDeDocumento.getCodTipoDocumento());
            	generaRegistroDocumentoRequestRest.setCodDivisionUnidadSGDP((int)instanciaDeProceso.getProceso().getUnidad().getIdUnidad());
            	generaRegistroDocumentoRequestRest.setUser(configProps.getProperty("usrRegistroDoc"));
            	generaRegistroDocumentoRequestRest.setPass(configProps.getProperty("passRegistroDoc"));
            	generaRegistroDocumentoResponseRest = registroDocumentoService.generaRegistroDocumento(generaRegistroDocumentoRequestRest, usuario);
            	
            	if (generaRegistroDocumentoResponseRest!=null) {
            		puedeBorrarRegistroDoc = true;
            		numeroDocRegistro = generaRegistroDocumentoResponseRest.getNumeroDoc();
            		firmaAvanzadaDTO.setNumeroDocumento(numeroDocRegistro.toString());
            		firmaAvanzadaDTO.setCategoriaDeDocumento(generaRegistroDocumentoResponseRest.getNombreTipoDocumento());
            	} 
            	
            	/*Agrega cabecera en todas las paginas*/
                Image imagenLogSCJ = Image.getInstance(byteArchivoImagenLogoSCJ);		
    			PdfImage streamLogoSCJ = new PdfImage(imagenLogSCJ, "", null);            
                PdfIndirectObject refLogoSCJ = stamper.getWriter().addToBody(streamLogoSCJ);
                imagenLogSCJ.setDirectReference(refLogoSCJ.getIndirectReference());                        
                imagenLogSCJ.scalePercent(25);
                Font fontCabeceraNumDoc = FontFactory.getFont(FontFactory.HELVETICA, 10);
                Font fontCabeceraNumPag = FontFactory.getFont(FontFactory.HELVETICA, 9);
                
                for (int i = 1 ; i<=reader.getNumberOfPages(); i++) {                	
                	float alturaPagina = reader.getPageSize(i).getHeight();
                	float anchoPagina = reader.getPageSize(i).getWidth();
                	log.debug("anchoPagina: " + anchoPagina);
                	log.debug("alturaPagina: " + alturaPagina);            	
                	float posXImagen = 84;
                	float posYImagen = alturaPagina - 56;
                	log.debug("posXImagen: " + posYImagen);
                	log.debug("posYImagen: " + posYImagen);
                 	float posXTipoDoc = 84;
                	float posYNombreExpediente = alturaPagina - 61;
                	float posXNumPag = anchoPagina - 142;            	
                	float posXFecha = 84;
                	float posYFecha = alturaPagina - 73;            	
                	imagenLogSCJ.setAbsolutePosition(Math.abs(posXImagen), Math.abs(posYImagen));            	
                	PdfContentByte overLogoSCJ = stamper.getOverContent(i);
                    overLogoSCJ.addImage(imagenLogSCJ);
                    ColumnText.showTextAligned(overLogoSCJ, Element.ALIGN_LEFT, 
                    		new Phrase(generaRegistroDocumentoResponseRest.getNombreTipoDocumento() + " N\u00B0 " + generaRegistroDocumentoResponseRest.getNumeroDoc() + " / " + FechaUtil.simpleDateFormatYear.format(new Date()), fontCabeceraNumDoc), posXTipoDoc , Math.abs(posYNombreExpediente), 0);
                    ColumnText.showTextAligned(overLogoSCJ, Element.ALIGN_LEFT, new Phrase("P\u00E1gina " + i + " de " + reader.getNumberOfPages() , fontCabeceraNumPag), posXNumPag , Math.abs(posYNombreExpediente), 0);
                    ColumnText.showTextAligned(overLogoSCJ, Element.ALIGN_LEFT, new Phrase("SANTIAGO, " + FechaUtil.simpleDateFormatForm.format(new Date()) + " " + instanciaDeProceso.getNombreExpediente() , fontCabeceraNumDoc), posXFecha , Math.abs(posYFecha), 0);                
                }  
                               	
              //MIG
               // ColumnText.showTextAligned(over, Element.ALIGN_LEFT, new Phrase(textoVerificaValidezFea, font), 60 , 20, 0);            	
            
            }                   
            

            //MIG
           // long idDocumentoFirmado = historicoFirmaService.getIdDocumentoFirmado();
       
            //MIG
           // ColumnText.showTextAligned(over, Element.ALIGN_LEFT, new Phrase("ID: " + Long.toString(idDocumentoFirmado), font), 60 , 30, 0);
            
            Date fechaPieFea = new Date();
            
            String fechaPieFeaS = FechaUtil.simpleDateFormatPieFea.format(fechaPieFea);
            log.info("fechaPieFea " + fechaPieFea);
            
            String reason = parametroService.getParametroPorID(Constantes.ID_PARAM_REASON_FEA).getValorParametroChar();
            String location = parametroService.getParametroPorID(Constantes.ID_PARAM_LOCATION_FEA).getValorParametroChar();
            
            log.info("Reason: " + reason);
            log.info("Location: " + location);
          
            Font font2 = FontFactory.getFont(FontFactory.HELVETICA, 7);       
            ColumnText.showTextAligned(over, Element.ALIGN_LEFT, new Phrase("Digitally signed by " + usuario.getNombreCompleto(), font2), 265 , 40, 0);
            ColumnText.showTextAligned(over, Element.ALIGN_LEFT, new Phrase("Date: " + fechaPieFeaS, font2), 265 , 30, 0);
            ColumnText.showTextAligned(over, Element.ALIGN_LEFT, new Phrase("Reason: " + reason, font2), 265 , 20, 0);
            ColumnText.showTextAligned(over, Element.ALIGN_LEFT, new Phrase("Location: " + location, font2), 265 , 10, 0);
            
            Font font3 = FontFactory.getFont(FontFactory.HELVETICA, 6);
            ColumnText.showTextAligned(over, Element.ALIGN_JUSTIFIED_ALL, new Phrase("Este documento est\u00E1 firmado con  una firma electr\u00f3nica ", font3), 46 , 34, 0);
            ColumnText.showTextAligned(over, Element.ALIGN_JUSTIFIED_ALL, new Phrase("avanzada, seg\u00FAn lo indica la ley N\u00B0 19.799. Su validez ", font3), 46 , 28, 0);
            ColumnText.showTextAligned(over, Element.ALIGN_JUSTIFIED_ALL, new Phrase("puede ser consultada a trav\u00E9s del c\u00f3digo QR, con el que ", font3), 46 , 22, 0);
            ColumnText.showTextAligned(over, Element.ALIGN_JUSTIFIED_ALL, new Phrase("puede obtener una copia del documento original desde el ", font3), 46 , 16, 0);
            ColumnText.showTextAligned(over, Element.ALIGN_JUSTIFIED_ALL, new Phrase("sitio Web de la Corporaci\u00f3n Nacional de Desarrollo Ind\u00edgena.", font3), 46 , 10, 0);
           
            stamper.close();
            reader.close();
            
            byteArchivo = ba.toByteArray();
			
			String checkSumSHA256 = FileUtil.checkSum(byteArchivo);			
			
			log.info("checkSumSHA256: " + checkSumSHA256);
			
			String archivoBase64 = FileUtil.encodeByteArrayToBase64(byteArchivo, parametroService.getParametroPorID(Constantes.ID_PARAM_ENCODE_CHARACTER_TRANSFORMATION_FEA).getValorParametroChar());			
			
			firmaAvanzadaArchivoRequest.setContent(archivoBase64);
			
			firmaAvanzadaArchivoRequest.setChecksum(checkSumSHA256);
			
			firmaAvanzadaRequest.getFiles().add(firmaAvanzadaArchivoRequest);
			
			firmaAvanzadaRequest.setApi_token_key(parametroService.getParametroPorID(Constantes.ID_PARAM_API_TOKEN_KEY_FEA).getValorParametroChar());	
			
			//String expiracion = FechaUtil.toFormat(new Date(), FechaUtil.simpleDateFormatUTCISOFEA);
			
			//log.debug("expiracion: " + expiracion);
			
			Calendar date = Calendar.getInstance();
			long timeInSecs = date.getTimeInMillis();
			Date expiracionD = new Date(timeInSecs + (2 * 60 * 1000));
			String expiracion = FechaUtil.toFormat(expiracionD, FechaUtil.simpleDateFormatUTCISOFEA);
			log.debug("expiracion: " + expiracion);
			
			firmaAvanzadaDTO.setExpiracion(expiracion);
			
			firmaAvanzadaDTO.setEntidad(parametroService.getParametroPorID(Constantes.ID_PARAM_ENTIDAD_TOKEN_FEA).getValorParametroChar());	
			
			log.debug(firmaAvanzadaDTO.toString());
			
			firmaAvanzadaRequest.setToken(EncriptacionUtil.encriptaConHS256(
																parametroService.getParametroPorID(Constantes.ID_PARAM_PASSWORD_TOKEN_FEA).getValorParametroChar(), 
																firmaAvanzadaDTO.getProposito(), 
																firmaAvanzadaDTO.getEntidad(), 
																firmaAvanzadaDTO.getExpiracion(), 
																firmaAvanzadaDTO.getRut()
															)
										);
			
			log.info("firmaAvanzadaRequest.getToken(): " + firmaAvanzadaRequest.getToken());
			
			//TokenResponse tokenResponse = firmaAvanzadaInterService.firmarDocumentoConFEA(firmaAvanzadaRequest);
			
			//log.info("tokenResponse.getSession_token(): " + tokenResponse.getSession_token());
			
			//FirmaAvanzadaMinSegPresResponse firmaAvanzadaMinSegPresResponse = firmaAvanzadaInterService.getDocumentosFEA(tokenResponse, firmaAvanzadaDTO.getOtp());
			
			FirmaAvanzadaMinSegPresResponseV3 firmaAvanzadaMinSegPresResponse = firmaAvanzadaInterService.firmarDocumentoConFEAV3(firmaAvanzadaRequest, firmaAvanzadaDTO.getOtp());
			
			//for (File fileMinSegPres : FirmaAvanzadaMinSegPresResponse.getFiles()) {
			for (FileV3 fileMinSegPres : firmaAvanzadaMinSegPresResponse.getFiles()) {
				
				log.info("Leyendo archivos");
				
				if (fileMinSegPres.getStatus().equals(
						parametroService.getParametroPorID(Constantes.ID_PARAM_STATUS_OK_FILE_FEA).getValorParametroChar())) {
					
					log.info("Checksum original");
					log.info("firmaAvanzadaArchivoRequest.getChecksum(): "+ firmaAvanzadaArchivoRequest.getChecksum());
					//log.info("fileMinSegPres.getChecksumOriginal(): "+ fileMinSegPres.getChecksumOriginal());
					log.info("fileMinSegPres.getChecksumOriginal(): "+ fileMinSegPres.getChecksum_original());
					
					byte[] archivoFirmadoByteArray = FileUtil.decodeBase64ToByteArray(fileMinSegPres.getContent(), parametroService.getParametroPorID(Constantes.ID_PARAM_ENCODE_CHARACTER_TRANSFORMATION_FEA).getValorParametroChar());
														
					String checkSumGet = FileUtil.checkSum(archivoFirmadoByteArray);
					
					log.info("fileMinSegPres.getChecksum(): " + fileMinSegPres.getChecksum_original());
					log.info("checkSumGet Calculado con FileUtil.checkSum: " + checkSumGet);
					
					//if (checkSumGet.equals(fileMinSegPres.getChecksum())) {
					if (firmaAvanzadaArchivoRequest.getChecksum().equals(fileMinSegPres.getChecksum_original())) {
						log.info("checkSumGet.equals(fileMinSegPres.getChecksum())");
						sgdpMultipartFile.setBytes(archivoFirmadoByteArray);
						sgdpMultipartFile.setContentType(firmaAvanzadaDTO.getMimeType());
						sgdpMultipartFile.setName(firmaAvanzadaDTO.getNombreArchivo());
						sgdpMultipartFile.setOriginalFilename(firmaAvanzadaDTO.getNombreArchivo());
						if (firmaAvanzadaDTO.getCategoriaDeDocumento()!=null) {								
							String extensionDocumento = firmaAvanzadaDTO.getNombreArchivo().substring(firmaAvanzadaDTO.getNombreArchivo().lastIndexOf('.'), firmaAvanzadaDTO.getNombreArchivo().length());
							sgdpMultipartFile.setName(URLEncoder.encode(firmaAvanzadaDTO.getCategoriaDeDocumento(), "UTF-8") + " " + "N%C2%B0 " + firmaAvanzadaDTO.getNumeroDocumento() + " " + FechaUtil.simpleDateFormatYear.format(new Date()) + " " + FechaUtil.simpleDateFormat.format(new Date()) + extensionDocumento);
							sgdpMultipartFile.setOriginalFilename(URLEncoder.encode(firmaAvanzadaDTO.getCategoriaDeDocumento(), "UTF-8")  + " " + "N%C2%B0" + firmaAvanzadaDTO.getNumeroDocumento() + " " + FechaUtil.simpleDateFormatYear.format(new Date()) + " " + FechaUtil.simpleDateFormat.format(new Date()) + extensionDocumento);
						} else {
							sgdpMultipartFile.setName(firmaAvanzadaDTO.getNombreArchivo());
							sgdpMultipartFile.setOriginalFilename(firmaAvanzadaDTO.getNombreArchivo());
						}				
						subirArhivoDTO.setArchivo(sgdpMultipartFile);						
						subirArhivoDTO.setIdExpedienteSubirArchivo(firmaAvanzadaDTO.getIdExpediente());
						subirArhivoDTO.setTipoDeDocumento(firmaAvanzadaDTO.getTipoDeDocumento());
						subirArhivoDTO.setIdTipoDeDocumentoSubir(firmaAvanzadaDTO.getIdTipoDeDocumento());
						subirArhivoDTO.setIdInstanciaDeTareaSubirArchivo(firmaAvanzadaDTO.getIdInstanciaDeTarea());	
						subirArhivoDTO.setNumeroDocumento(firmaAvanzadaDTO.getNumeroDocumento());
						subirArhivoDTO.setEmisor(firmaAvanzadaDTO.getEmisor());
						subirArhivoDTO.setCartaRelacionada(firmaAvanzadaDTO.getCartaRelacionada());						
						subirArchivoService.subirArchivo(usuario, subirArhivoDTO);
						HistoricoFirmaDTO historicoFirmaDTO = new HistoricoFirmaDTO();
						historicoFirmaDTO.setFechaFirma(new Date());
						historicoFirmaDTO.setIdArchivoCMS(subirArhivoDTO.getIdArchivoEnCMS());
						historicoFirmaDTO.setIdInstanciaDeTarea(firmaAvanzadaDTO.getIdInstanciaDeTarea());
						historicoFirmaDTO.setIdUsuario(usuario.getIdUsuario());
						historicoFirmaDTO.setTipoFirma(firmaTypeCentralizado);
						historicoFirmaDTO.setIdTipoDeDocumento(firmaAvanzadaDTO.getIdTipoDeDocumento());
						historicoFirmaDTO.setIdDocumentoFirmado(null);
						historicoFirmaDTO.setUuid(uuid);//ID PARA QR
						registraFirma(usuario.getIdUsuario(), historicoFirmaDTO);
					} else {
						log.info("checkSumGet.distinto(fileMinSegPres.getChecksum())");
						throw new Exception(configProps.getProperty("errorAlFirmarDocumento"));
					}
					
				} else {
					log.info("fileMinSegPres.getStatus(): " + fileMinSegPres.getStatus());
					throw new Exception(configProps.getProperty("errorAlFirmarDocumento"));
				}
			}
			
			//firmaAvanzadaDTO.setSessionToken(tokenResponse.getSession_token());
			firmaAvanzadaDTO.setResultadoFirmarDocumentoConFEA(configProps.getProperty("seFirmoCorrectamenteElDocumento"));
			firmaAvanzadaDTO.setCssStatus(configProps.getProperty("cssSucess"));
			
			return firmaAvanzadaDTO;
			
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			if (e instanceof SgdpException) {
				firmaAvanzadaDTO.setResultadoFirmarDocumentoConFEA(e.getMessage());
			} else {
				firmaAvanzadaDTO.setResultadoFirmarDocumentoConFEA(configProps.getProperty("errorAlFirmarDocumento"));
			}			
			firmaAvanzadaDTO.setCssStatus(configProps.getProperty("cssError"));	
			if (puedeBorrarRegistroDoc==true) {
				BorraRegistroDocumentoRequestRest borraRegistroDocumentoRequestRest = new BorraRegistroDocumentoRequestRest();
				borraRegistroDocumentoRequestRest.setExpDoc(firmaAvanzadaDTO.getNombreExpediente());
				borraRegistroDocumentoRequestRest.setCodTipoDoc(codTipoDocumento);
				borraRegistroDocumentoRequestRest.setNumeroDoc(numeroDocRegistro);
				borraRegistroDocumentoRequestRest.setMotivoAnulacion("Ocurrio un error durante la firma de segpres del documento en SGDP al usuario " + usuario.getIdUsuario());
				borraRegistroDocumentoRequestRest.setMensajeException(e.getMessage());
				borraRegistroDocumentoRequestRest.setUser(configProps.getProperty("usrRegistroDoc"));
				borraRegistroDocumentoRequestRest.setPass(configProps.getProperty("passRegistroDoc"));
				try {
					BorraRegistroDocumentoResponseRest borraRegistroDocumentoResponseREst = registroDocumentoService.borraRegistroDocumento(borraRegistroDocumentoRequestRest);
					log.info(borraRegistroDocumentoResponseREst.toString());
				} catch (IOException e1) {
					StringWriter sw1 = new StringWriter();
					e1.printStackTrace(new PrintWriter(sw1));
					String exceptionAsString1 = sw1.toString();
					log.error(exceptionAsString1);
				}
			}
			return firmaAvanzadaDTO;
		}
	}
	
	@Override
	public String actualizaMetaDataDeDocumento(Usuario usuario, DetalleDeArchivoDTO detalleDeArchivoDTO) throws SgdpException {		
		try {
			return gestorDeDocumentosCMSService.actualizaMetaDataDeDocumento(usuario, detalleDeArchivoDTO).getRespuesta();
		} catch (HttpClientErrorException e) {			
			throw new SgdpException(configProps.getProperty("errorAlActualizarMetadataDeDocumento"));
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);			
			throw new SgdpException(configProps.getProperty("errorAlActualizarMetadataDeDocumento"));
		}
	}

	@Override
	public String getJnlp(String iddoc, String ticket, String nameDoc, String idexpediente, long idTipoDeDocumento, Usuario usuario, long idInstanciaDeTarea) throws Exception {		
			InstanciaDeProceso instanciaDeProceso = instanciaDeProcesoDao.getInstanciaDeProcesoPorIdExpediente(idexpediente);
			InstanciaDeTarea instanciaDeTarea = instanciaDeTareaDao.getInstanciaDeTareaPorIdInstanciaDeTarea(idInstanciaDeTarea);
			TipoDeDocumento tipoDeDocumento = tipoDeDocumentoDao.getTipoDeDocumentoPorIdTipoDeDocumento(idTipoDeDocumento);	
			boolean numeraAutoTipoDoc = (tipoDeDocumento.getNumAuto()!=null) ? tipoDeDocumento.getNumAuto() : false;
			boolean numeraAutoTarea = (instanciaDeTarea.getTarea().getNumAuto()!=null) ? instanciaDeTarea.getTarea().getNumAuto() : false;
			String codTipoDocumento = tipoDeDocumento.getCodTipoDocumento();
			if (codTipoDocumento!=null && codTipoDocumento.isEmpty()) {
				codTipoDocumento = "null";
			}
			Long idUnidad= instanciaDeProceso.getProceso().getUnidad().getIdUnidad();
			String nombreExpediente = instanciaDeProceso.getNombreExpediente();
			String urlCodeBaseJNLPFea = parametroService.getParametroPorID(Constantes.ID_PARAM_URL_CODE_BASE_JNLP_FEA).getValorParametroChar();
			String ipCMS = parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_IP).getValorParametroChar();
			String ipSGDP = parametroService.getParametroPorID(Constantes.ID_PARAM_SGDP_IP).getValorParametroChar();
			ParametroDTO parametroDTOIdLogoSCJ = parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_ID_LOGO_SCJ_PARA_FIRMA);
			String idLogoSCJ = parametroDTOIdLogoSCJ.getValorParametroChar();
			ParametroDTO parametroDTONombreCarpetaImagenesFEA = parametroService.getParametroPorID(Constantes.ID_PARAM_NOMBRE_CARPETA_IMAGENES_FEA);
			String nombreCarpetaImagenesFEA = parametroDTONombreCarpetaImagenesFEA.getValorParametroChar();						
			IdArchivoPorIdUsrNomCarpetaResponse idArchivoDefirma = gestorDeDocumentosCMSService.getIdArchivoPorIdUsrNomCarpeta(usuario, nombreCarpetaImagenesFEA);	
			String colocaImagenFea = parametroService.getParametroPorID(Constantes.ID_PARAM_COLOCA_IMAGEN_DE_FIRMA).getValorParametroChar();			
			//MIG
			//	if (colocaImagenFea!=null && colocaImagenFea.equals("SI")) {
			//	idArchivoDefirma = gestorDeDocumentosCMSService.getIdArchivoPorIdUsrNomCarpeta(usuario, nombreCarpetaImagenesFEA);	
			//} else {
			//	idArchivoDefirma = new IdArchivoPorIdUsrNomCarpetaResponse();
			//	idArchivoDefirma.setIdArchivo("0");
			//}	
				
			//MIG
			String uuid = UUID.randomUUID().toString();	            
	        ParametroDTO parametroDTODescargaDocumento = parametroService.getParametroPorID(Constantes.ID_PARAM_URL_DESCARGA_DOCUMENTO);	            
	        String urlDescarga = parametroDTODescargaDocumento.getValorParametroChar() + uuid;	
	        String nombreUsuario = usuario.getNombreCompleto();	
	        
			String textoVerificaValidezFea = parametroService.getParametroPorID(Constantes.ID_PARAM_URL_VERIFICACION_DOC_FEA).getValorParametroChar();			
			long idDocumentoFirmado = historicoFirmaService.getIdDocumentoFirmado();
			log.info("urlCodeBaseJNLPFea: " + urlCodeBaseJNLPFea);
			log.info("iddoc: " + iddoc);
			log.info("ticket: " + ticket);
			log.info("nameDoc: " + nameDoc);
			log.info("idexpediente: " + idexpediente);
			log.info("idTipoDeDocumento: " + idTipoDeDocumento);
			log.info("idUsuario: " + usuario.getIdUsuario());
			log.info("idInstanciaDeTarea: " + idInstanciaDeTarea);
			log.info("numeraAutoTipoDoc: " + numeraAutoTipoDoc);			
			log.info("numeraAutoTarea: " + numeraAutoTarea);
			log.info("codTipoDocumento: " + codTipoDocumento);
			log.info("idUnidad: " + idUnidad);
			log.info("idLogoSCJ: " + idLogoSCJ);
			log.info("idArchivoDefirma: " + idArchivoDefirma.getIdArchivo());
			log.info("nombreExpediente: " + nombreExpediente);
			log.info("colocaImagenFea: " + colocaImagenFea);
			log.info("textoVerificaValidezFea: " + textoVerificaValidezFea);
			log.info("textoVerificaValrutidezFea: " + usuario.getRut());
			log.info("idDocumentoFirmado: " + idDocumentoFirmado);
			log.info("urlDescarga: " + urlDescarga);
			log.info("nombreUsuario: " + usuario.getNombreCompleto());
			log.info("uuid: " + uuid);
			return "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n" +		       
		         "<jnlp codebase=" + urlCodeBaseJNLPFea + " >\n" +
		              "<information>\n"+
		             		"<title>SWT Address Book</title>\n"+
		             		"<vendor>Eclipse</vendor>\n"+
		             		"<homepage href=\"http://www.eclipse.org\"/>\n"+		             	
		             		"<offline-allowed/>\n"+
		              "</information>\n"+
		              "<security>\n"+
		              		"<all-permissions/>\n"+
		              "</security>\n"+
	        			"<resources>\n"+
	        					"<j2se version=\"1.7+\"/>\n"+
	        					"<jar href=\"lib2/FirmaAvanzada-1.0.jar\"/>\n"+
	        					"<jar href=\"lib2/commons-io-1.3.2.jar\"/>\n"+
	        					"<jar href=\"lib2/itextpdf-5.0.6.jar\"/>\n"+
	        					"<jar href=\"lib2/org.apache.commons.httpclient.jar\"/>\n"+
	        					"<jar href=\"lib2/bcprov-1.45.jar\"/>\n"+
	        					"<jar href=\"lib2/spring-core-4.0.5.RELEASE.jar\"/>\n"+
	        					"<jar href=\"lib2/spring-web-4.0.5.RELEASE.jar\"/>\n"+
	        					"<jar href=\"lib2/spring-security-core-3.2.7.RELEASE.jar\"/>\n"+
	        					"<jar href=\"lib2/commons-logging-1.1.3.jar\"/>\n"+
	        					"<jar href=\"lib2/jackson-annotations-2.6.0.jar\"/>\n"+
	        					"<jar href=\"lib2/jackson-core-2.6.3.jar\"/>\n"+
	        					"<jar href=\"lib2/jackson-databind-2.6.3.jar\"/>\n"+
	        					"<jar href=\"lib2/org.apache.tika.jar\"/>\n"+
	        					"<jar href=\"lib2/core-3.3.3.jar\"/>\n"+
		              " </resources>\n"+
		              "<property name=\"jnlp.deleteJnlpFileOnExit\" value=\"true\" />\n" +
		              "<application-desc main-class=\"scj.firmaavanzada.FirmaAvanzada\">\n" +
		              		"<argument>"+iddoc+"</argument>\n" +
		              		"<argument>"+ticket+"</argument>\n" +
		              		"<argument>"+nameDoc+"</argument>\n" +
		              		"<argument>"+idexpediente+"</argument>\n" +
		              		"<argument>"+idTipoDeDocumento+"</argument>\n" +
		              		"<argument>"+ipSGDP+"</argument>\n" +
		              		"<argument>"+ipCMS+"</argument>\n" +
		              		"<argument>"+usuario.getIdUsuario()+"</argument>\n" +
		              		"<argument>"+idInstanciaDeTarea+"</argument>\n" +		              		
							"<argument>"+numeraAutoTipoDoc+"</argument>\n" +
							"<argument>"+numeraAutoTarea+"</argument>\n" +
							"<argument>"+codTipoDocumento+"</argument>\n" +
							"<argument>"+idUnidad+"</argument>\n" +
							"<argument>"+idLogoSCJ+"</argument>\n" +
							"<argument>"+idArchivoDefirma.getIdArchivo()+"</argument>\n" +
							"<argument>"+nombreExpediente+"</argument>\n" +
							"<argument>"+colocaImagenFea+"</argument>\n" +
							"<argument>"+textoVerificaValidezFea+"</argument>\n" +
							"<argument>"+usuario.getRut()+"</argument>\n" +
							"<argument>"+idDocumentoFirmado+"</argument>\n" +
							"<argument>"+urlDescarga+"</argument>\n" + 
							"<argument>"+nombreUsuario+"</argument>\n" + 
							"<argument>"+uuid+"</argument>\n" +
		              "</application-desc>" +
		              "</jnlp>";
	}
	
	@Override
	public void registraFirma(String idUsuario, HistoricoFirmaDTO historicoFirmaDTO) {			
		InstanciaDeTarea instanciaDeTarea = instanciaDeTareaDao.getInstanciaDeTareaPorIdInstanciaDeTarea(historicoFirmaDTO.getIdInstanciaDeTarea());
		TipoDeDocumento tipoDeDocumento = tipoDeDocumentoDao.getTipoDeDocumentoPorIdTipoDeDocumento(historicoFirmaDTO.getIdTipoDeDocumento());
		ArchivosInstDeTarea archivosInstDeTarea = archivosInstDeTareaDao.getArchivosInstDeTareaPorIdArchivoIdInstanciaDeTareaIdUsuario(
				historicoFirmaDTO.getIdArchivoCMS(), historicoFirmaDTO.getIdInstanciaDeTarea(), idUsuario);
		HistoricoFirma historicoFirma = new HistoricoFirma();
		historicoFirma.setFechaFirma(new Date());
		historicoFirma.setIdArchivoCMS(historicoFirmaDTO.getIdArchivoCMS());
		historicoFirma.setIdUsuario(idUsuario);
		historicoFirma.setInstanciaDeTarea(instanciaDeTarea);
		historicoFirma.setTipoFirma(historicoFirmaDTO.getTipoFirma().getNombreFirma());
		historicoFirma.setTipoDeDocumento(tipoDeDocumento);
		historicoFirma.setIdDocumentoFirmado(historicoFirmaDTO.getIdDocumentoFirmado());
		historicoFirma.setUuid(historicoFirmaDTO.getUuid());
		
        switch (historicoFirmaDTO.getTipoFirma().getCodigoFirma()) {
        	/*VISACION*/
        	case 1: {
            	archivosInstDeTarea.setEstaVisado(true);
            	break;
            }     
        	/*CENTRALIZADO*/
            case 2: {
            	archivosInstDeTarea.setEstaFirmadoConFEACentralizada(true);
            	 break;
            }
            /*WEB_START*/
            case 3: {
            	archivosInstDeTarea.setEstaFirmadoConFEAWebStart(true);
           	 	break;
            }        
        }
		historicoFirmaDao.insertaHistoricoFirma(historicoFirma);		
	}
	
	
	private RespuestaConversionArchivoDTO convertirArchivoAPDFYSubirACMS(Usuario usuario, String nombreDeArchivo, SubirArhivoDTO sdto) throws SgdpException {
		log.debug("Inicio convertirArchivoAPDFYSubirACMS. idArchivo: " + sdto.getIdArchivoEnCMS());
		byte[] byteArchivoAConvertir;
		byte[] byteArchivoConvertido;
		DetalleDeArchivoDTO detalleDeArchivoDTO;	
		RespuestaConversionArchivoDTO respuestaConversionArchivoDTO = new RespuestaConversionArchivoDTO();
		SgdpMultipartFile sgdpMultipartFile = new SgdpMultipartFile();
		SubirArhivoDTO subirArhivoDTO = new SubirArhivoDTO();
		try {
			detalleDeArchivoDTO = obtenerDetalleDeArchivoService.obtenerDetalleDeArchivo(usuario, sdto.getIdArchivoEnCMS());
			log.debug(detalleDeArchivoDTO.toString());			
			log.debug("nombreDeArchivo: " + nombreDeArchivo);
			String nombreArcvhivoConNvaExtension = StringUtilSGDP.cambiaExtension(nombreDeArchivo, "pdf");
			log.debug("nombreArcvhivoConNvaExtension: " + nombreArcvhivoConNvaExtension);
			byteArchivoAConvertir = gestorDeDocumentosCMSService.getContenidoArchivo(sdto.getIdArchivoEnCMS(), usuario);
			byteArchivoConvertido = gestorDeDocumentosCMSService.convertirArchivoAPDF(usuario, byteArchivoAConvertir);
			sgdpMultipartFile.setBytes(byteArchivoConvertido);
			sgdpMultipartFile.setContentType("application/pdf");
			sgdpMultipartFile.setName(nombreArcvhivoConNvaExtension);
			sgdpMultipartFile.setOriginalFilename(nombreArcvhivoConNvaExtension);
			subirArhivoDTO.setArchivo(sgdpMultipartFile);						
			subirArhivoDTO.setIdExpedienteSubirArchivo(detalleDeArchivoDTO.getIdExpedienteQueLoContiene());
			subirArhivoDTO.setTipoDeDocumento(sdto.getTipoDeDocumento());
			subirArhivoDTO.setNumeroDocumento(detalleDeArchivoDTO.getNumeroDocumento());
			subirArhivoDTO.setEmisor(detalleDeArchivoDTO.getEmisor());
			subirArhivoDTO.setCdr(detalleDeArchivoDTO.getCdr());
			subirArhivoDTO.setDescripcion(detalleDeArchivoDTO.getDescripcion());
			subirArhivoDTO.setCartaRelacionada(detalleDeArchivoDTO.getCartaRelacionada());
			subirArhivoDTO.setEmisor(detalleDeArchivoDTO.getEmisor());
			subirArhivoDTO.setOtro(detalleDeArchivoDTO.getResultadoObtenerDetalleDeArchivo());
			subirArhivoDTO.setEsDocumentoOficial(sdto.getEsDocumentoOficial());	
			subirArhivoDTO.setCategoriaDocumento(sdto.getCategoriaDocumento());
			SubirArchivoResponse subirArchivoResponse = subirArchivoCMSService.subirArchivo(usuario, subirArhivoDTO);
			log.debug(subirArchivoResponse.toString());
			respuestaConversionArchivoDTO.setIdArchivo(subirArchivoResponse.getIdArchivo());
			respuestaConversionArchivoDTO.setResultadoOperacion("1");
			respuestaConversionArchivoDTO.setNombreArchivo(nombreArcvhivoConNvaExtension);
			return respuestaConversionArchivoDTO;
		} catch (SgdpException e) {						
			log.error("ERROR_AL_OBTENER_DETALLE_DE_ARCHIVO");
			log.debug("Fin... convertirArchivoAPDF.. error");
			throw new SgdpException("Error al convertirArchivo a PDF");
		} catch (IOException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);		
			log.info("Fin... convertirArchivoAPDF.. error");
			throw new SgdpException("Error al convertirArchivo a PDF");
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);		
			log.info("Fin... convertirArchivoAPDF.. error");
			throw new SgdpException("Error al convertirArchivo a PDF");
		}		
	}

	@Override
	public RespuestaConversionArchivoDTO convertirArchivoAPDF(Usuario usuario, SubirArhivoDTO subirArhivoDTO) throws Exception {
		log.debug("Inicio convertirArchivoAPDF");
		RespuestaConversionArchivoDTO respuestaConversionArchivoDTO;		
		String nombreArchivo;
		if (subirArhivoDTO.getArchivo()!=null && subirArhivoDTO.getArchivo().getOriginalFilename()!=null) {
			log.debug("subirArhivoDTO.getArchivo().getOriginalFilename(): " + subirArhivoDTO.getArchivo().getOriginalFilename());
			nombreArchivo = subirArhivoDTO.getArchivo().getOriginalFilename();
		} else {				
			nombreArchivo = subirArhivoDTO.getNombreDeArchivo();
			nombreArchivo = URLEncoder.encode(nombreArchivo, "UTF-8");
		}	
		//nombreArchivo = subirArhivoDTO.getNombreDeArchivo();
		//nombreArchivo = URLEncoder.encode(nombreArchivo, "UTF-8");		
		log.debug("nombreArchivo: " + nombreArchivo);
		respuestaConversionArchivoDTO = convertirArchivoAPDFYSubirACMS(usuario, nombreArchivo, subirArhivoDTO);
		/*try {
			if (subirArhivoDTO.getArchivo()!=null && subirArhivoDTO.getArchivo().getOriginalFilename()!=null) {
				log.debug("subirArhivoDTO.getArchivo().getOriginalFilename(): " + subirArhivoDTO.getArchivo().getOriginalFilename());
				nombreArchivo = subirArhivoDTO.getArchivo().getOriginalFilename();
			} else {				
				nombreArchivo = subirArhivoDTO.getNombreDeArchivo();
				nombreArchivo = URLEncoder.encode(nombreArchivo, "UTF-8");
			}
			log.debug("nombreArchivo: " + nombreArchivo);			
			respuestaConversionArchivoDTO = convertirArchivoAPDFYSubirACMS(usuario, nombreArchivo, subirArhivoDTO);	
		} catch (SgdpException sgdpe) {			
			respuestaConversionArchivoDTO = gestorDeDocumentosCMSService.convertirArchivoAPDF(usuario, subirArhivoDTO.getIdArchivoEnCMS());
		} catch (UnsupportedEncodingException e) {
			respuestaConversionArchivoDTO = gestorDeDocumentosCMSService.convertirArchivoAPDF(usuario, subirArhivoDTO.getIdArchivoEnCMS());
		}*/ 		
		return respuestaConversionArchivoDTO;
	}
	
	@Override
	public byte[] getContenidoArchivoDesdeUrlYVersion(DetalleDeArchivoDTO detalleDeArchivoDTO, String version, Usuario usuario) throws Exception {
		return gestorDeDocumentosCMSService.getContenidoArchivoDesdeUrlYVersion(detalleDeArchivoDTO, version, usuario);
	}
	
	/*@Override
	public RespuestaSimpleDTO copiaArchivo(Usuario usuario, String idExpedienteOrigen, String idExpedienteDestino, String idArchivo, String nuevoNombre) throws Exception {
		return gestorDeDocumentosCMSService.copiaArchivo(usuario, idExpedienteOrigen, idExpedienteDestino, idArchivo, nuevoNombre);
	}*/
	
	@Override
	public SubirArhivoDTO subirArchivoDesdeOtroCambiandoNombre(Usuario usuario, String nombreDeArchivo, SubirArhivoDTO sdto) throws SgdpException {
		log.debug("Inicio subirArchivoDesdeOtro. idArchivo: " + sdto.getIdArchivoEnCMS());
		byte[] byteArchivo;
		DetalleDeArchivoDTO detalleDeArchivoDTO;
		SgdpMultipartFile sgdpMultipartFile = new SgdpMultipartFile();
		SubirArhivoDTO subirArhivoDTO = new SubirArhivoDTO();
		try {
			detalleDeArchivoDTO = obtenerDetalleDeArchivoService.obtenerDetalleDeArchivo(usuario, sdto.getIdArchivoEnCMS());
			log.debug(detalleDeArchivoDTO.toString());	
			nombreDeArchivo = URLEncoder.encode(nombreDeArchivo, "UTF-8");
			log.debug("nombreDeArchivo: " + nombreDeArchivo);
			byteArchivo = gestorDeDocumentosCMSService.getContenidoArchivo(sdto.getIdArchivoEnCMS(), usuario);			
			sgdpMultipartFile.setBytes(byteArchivo);
			sgdpMultipartFile.setContentType("application/pdf");
			sgdpMultipartFile.setName(nombreDeArchivo);
			sgdpMultipartFile.setOriginalFilename(nombreDeArchivo);
			subirArhivoDTO.setArchivo(sgdpMultipartFile);						
			subirArhivoDTO.setIdExpedienteSubirArchivo(detalleDeArchivoDTO.getIdExpedienteQueLoContiene());
			subirArhivoDTO.setTipoDeDocumento(sdto.getTipoDeDocumento());
			subirArhivoDTO.setNumeroDocumento(detalleDeArchivoDTO.getNumeroDocumento());
			subirArhivoDTO.setEmisor(detalleDeArchivoDTO.getEmisor());
			subirArhivoDTO.setCdr(detalleDeArchivoDTO.getCdr());
			subirArhivoDTO.setDescripcion(detalleDeArchivoDTO.getDescripcion());
			subirArhivoDTO.setCartaRelacionada(detalleDeArchivoDTO.getCartaRelacionada());
			subirArhivoDTO.setEmisor(detalleDeArchivoDTO.getEmisor());
			subirArhivoDTO.setOtro(detalleDeArchivoDTO.getResultadoObtenerDetalleDeArchivo());
			subirArhivoDTO.setEsDocumentoOficial(sdto.getEsDocumentoOficial());	
			subirArhivoDTO.setCategoriaDocumento(sdto.getCategoriaDocumento());
			subirArhivoDTO.setIdInstanciaDeTareaSubirArchivo(sdto.getIdInstanciaDeTareaSubirArchivo());
			subirArhivoDTO.setIdTipoDeDocumentoSubir(sdto.getIdTipoDeDocumentoSubir());
			subirArhivoDTO.setEsRequerido(sdto.getEsRequerido());
			SubirArchivoResponse subirArchivoResponse = subirArchivoCMSService.subirArchivo(usuario, subirArhivoDTO);
			subirArhivoDTO.setIdArchivoEnCMS(subirArchivoResponse.getIdArchivo());
			log.debug(subirArchivoResponse.toString());			
			return subirArhivoDTO;
		} catch (SgdpException e) {						
			log.error("ERROR_AL_OBTENER_DETALLE_DE_ARCHIVO");
			log.debug("Fin... subirArchivoDesdeOtroCambiandoNombre.. error");
			throw new SgdpException("Error al subirArchivoDesdeOtroCambiandoNombre");
		} /*catch (IOException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);		
			log.info("Fin... subirArchivoDesdeOtro.. error");
			throw new SgdpException("Error al subirArchivoDesdeOtro");
		} */catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);		
			log.info("Fin... subirArchivoDesdeOtroCambiandoNombre.. error");
			throw new SgdpException("Error al subirArchivoDesdeOtroCambiandoNombre");
		}
	}
	
	public ArchivosInstDeTareaDTO getUltimoArchivoInstDeTareaFirmado(long idInstanciaDeTarea, long idTipoDeDocumento, String idUsuario) {
		ArchivosInstDeTareaDTO archivosInstDeTareaDTO = new ArchivosInstDeTareaDTO();
		try {
			ArchivosInstDeTarea archivosInstDeTarea = archivosInstDeTareaDao.getUltimoArchivoInstDeTareaFirmado(idInstanciaDeTarea, idTipoDeDocumento, idUsuario);		
			if (archivosInstDeTarea!=null) {
				archivosInstDeTareaDTO.setGlosa("OK");
				BeanUtils.copyProperties(archivosInstDeTarea, archivosInstDeTareaDTO);
				archivosInstDeTareaDTO.setIdInstanciaDeTarea(idInstanciaDeTarea);
				archivosInstDeTareaDTO.setIdTipoDeDocumento(idTipoDeDocumento);	
			} else {
				archivosInstDeTareaDTO.setGlosa("ERROR");
			}
			return archivosInstDeTareaDTO;			
		} catch (Exception e) {			
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.info(exceptionAsString);
			archivosInstDeTareaDTO.setGlosa("ERROR");
			return archivosInstDeTareaDTO;
		}
		
	}
	
}