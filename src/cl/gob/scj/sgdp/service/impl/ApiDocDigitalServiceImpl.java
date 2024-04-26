package cl.gob.scj.sgdp.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import cl.gob.scj.sgdp.dto.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;



import cl.gob.scj.sgdp.config.Constantes;

import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.service.ApiDocDigitalService;
import cl.gob.scj.sgdp.service.ParametroService;
import cl.gob.scj.sgdp.util.SingleObjectFactory;


@Service
@Transactional(rollbackFor = Throwable.class)
public class ApiDocDigitalServiceImpl implements ApiDocDigitalService {

    private static final Logger log = Logger.getLogger(ApiDocDigitalServiceImpl.class);

    @Autowired
    private ParametroService parametroService;

    @Resource(name = "configProps")
    private Properties configProps;

    @Override
    public List<EntidadDTO> getEntidades() {

        log.info("Entidades Service");
        List<EntidadDTO> listaEntidades = new ArrayList<>();
        RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();
        try {
            ParametroDTO parametroDTO = parametroService.getParametroPorID(Constantes.ID_PARAM_URL_ENTIDADES);
            String urlEntidades = parametroDTO.getValorParametroChar();
            ArrayList<?> array = (ArrayList<?>) restTemplate.getForObject(urlEntidades, Map.class).get("result");
            for(Object entidad  : array) {
                Map<String, Object> map = (Map<String, Object>) entidad;
                EntidadDTO entidadDTO = new EntidadDTO();
                OrganismoDTO organismoDTO = new OrganismoDTO();
                entidadDTO.setIdEntidad((Integer) map.get("entidad_id"));
                entidadDTO.setNombreEntidad((String) map.get("entidad_nombre"));
                Map<String, Object>organismo =  (Map<String, Object>) map.get("organismo");
                organismoDTO.setIdOrganismo((Integer) organismo.get("organismo_id"));
                organismoDTO.setNombreOrganismo((String) organismo.get("organismo_nombre"));
                entidadDTO.setOrganismo(organismoDTO);
                listaEntidades.add(entidadDTO);
            }
            return listaEntidades;
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            log.error(exceptionAsString);
            throw  e;
        }
    }



    @Override
    public List<UsuarioEntidadDTO> getUsuarioEntidad(Integer idEntidad)  {

        log.info("Usuarios Entidad Service");
        List<UsuarioEntidadDTO> listaUsuarios = new ArrayList<>();
        RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();
        Map<String, String> parametrosURL = new HashMap<String, String>();
        try {
            parametrosURL.put("idEntidad", idEntidad.toString());
            ParametroDTO parametroDTO = parametroService.getParametroPorID(Constantes.ID_PARAM_URL_USUARIOS);
            String urlUsua = parametroDTO.getValorParametroChar();

            ArrayList<?> array = (ArrayList<?>) restTemplate.getForObject(urlUsua, Map.class, parametrosURL).get("result");
            for(Object entidad  : array) {
                Map<String, Object> map = (Map<String, Object>) entidad;
                UsuarioEntidadDTO usuarioEntidadDTO = new UsuarioEntidadDTO();

                usuarioEntidadDTO.setIdUsuario((Integer) map.get("usuario_id"));
                usuarioEntidadDTO.setRunUsuario((String) map.get("usuario_run"));
                usuarioEntidadDTO.setNombreUsuario((String) map.get("usuario_nombre"));
                usuarioEntidadDTO.setEmailUsuario((String) map.get("usuario_email"));
                usuarioEntidadDTO.setCargoUsuario((String) map.get("usuario_cargo"));
                usuarioEntidadDTO.setIdEntidad((String) map.get("entidad_id"));
                usuarioEntidadDTO.setNombreEntidad((String) map.get("entidad_nombre"));
                usuarioEntidadDTO.setIdOrganismo((String) map.get("organismo_id"));
                usuarioEntidadDTO.setNombreOrganismo((String) map.get("organismo_nombre"));

                listaUsuarios.add(usuarioEntidadDTO);
            }
            return listaUsuarios;
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            log.error(exceptionAsString);
            throw e;
        }
    }



    @Override
    public DocumentoIngresoResponseDTO ingresarDocumentoApiDocDig(DocumentoIngresoRequestDTO ingresarDocDTO) throws SgdpException {
        log.info("Ingresar Documento Service: "+ingresarDocDTO);


        RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();
        try {
            ParametroDTO parametroDTO = parametroService.getParametroPorID(Constantes.ID_PARAM_INGERSAR_DOCUMENTO);
            String urlIngDoc = parametroDTO.getValorParametroChar();
            DocumentoIngresoResponseDTO tocumentoIngresoResponseDTO = restTemplate.postForObject(urlIngDoc, ingresarDocDTO, DocumentoIngresoResponseDTO.class);
            log.info(tocumentoIngresoResponseDTO.toString());
            return tocumentoIngresoResponseDTO;

        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            log.error(exceptionAsString);
            throw new SgdpException(configProps.getProperty("errorIngresarDocumentoApiDocService"));
        }

    }



    @Override
    public EntidadDTO getEntidadToken()  {

        log.info("Entidad Token Service");

        RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();
        try {
            ParametroDTO parametroDTO = parametroService.getParametroPorID(Constantes.ID_PARAM_URL_ENTIDAD_TOKEN);
            String urlEntidades = parametroDTO.getValorParametroChar();
            EntidadDTO entidadDto = new EntidadDTO();

            Map<String, Object> map = (Map<String, Object>) restTemplate.getForObject(urlEntidades, Map.class).get("result");
            entidadDto.setIdEntidad((Integer) map.get("entidad_id"));
            entidadDto.setNombreEntidad(map.get("entidad_nombre").toString());
            OrganismoDTO organismoDTO = new OrganismoDTO();
            Map<String, Object>organismo =  (Map<String, Object>) map.get("organismo");
            organismoDTO.setIdOrganismo((Integer) organismo.get("organismo_id"));
            organismoDTO.setNombreOrganismo((String) organismo.get("organismo_nombre"));
            entidadDto.setOrganismo(organismoDTO);

            return entidadDto;
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            log.error(exceptionAsString);
            throw e;
        }

    }
    @Override
    public ResponseEntity<AcuseReciboResponseDTO> acuseRecibo(Long idDocumento) {
        RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();
        ParametroDTO parametroDTO = parametroService.getParametroPorID(Constantes.ID_PARAM_URL_ACUSE_RECIVO_DOC_DIGITAL);
        String requestBody = "";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(requestBody, headers);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return restTemplate.exchange(parametroDTO.getValorParametroChar(), HttpMethod.PUT, entity, AcuseReciboResponseDTO.class, idDocumento);
    }

    public ResponseEntity<DevolverResponseDTO> devolver(Long idDocumento, String motivoRechazo) {
        RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();
        ParametroDTO parametroDTO = parametroService.getParametroPorID(Constantes.ID_PARAM_URL_DEVUELVE_DOC_DIGITAL);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        DevolverRequest devolverRequest = new DevolverRequest();
        devolverRequest.setMotivoRechazo(motivoRechazo);
        HttpEntity<DevolverRequest> entity = new HttpEntity<>(devolverRequest, headers);
        return restTemplate.exchange(parametroDTO.getValorParametroChar(), HttpMethod.PUT, entity, DevolverResponseDTO.class, idDocumento);
    }



}
