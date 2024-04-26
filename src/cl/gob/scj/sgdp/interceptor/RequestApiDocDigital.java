package cl.gob.scj.sgdp.interceptor;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.util.StringUtilSGDP;

public class RequestApiDocDigital implements ClientHttpRequestInterceptor {

	private static final Logger LOGGER = Logger.getLogger(RequestApiDocDigital.class);
	
	private static final List<String> END_POINTS_API_DOC_DIGITAL = Arrays.asList("documento/ingresar/", "/usuario/usuarios/", "/entidad/entidades/" );
	
	private static final String NOMBRE_HEADER_LOGIN_MULTI_OFICINA = "Login-Multi-Oficina";
	
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
		if(StringUtilSGDP.valorContienAlgunElementoEnListaDeString(request.getURI().toString(), END_POINTS_API_DOC_DIGITAL)) {
			Usuario usuario = (Usuario)RequestContextHolder.getRequestAttributes().getAttribute("usuario", RequestAttributes.SCOPE_SESSION);
			LOGGER.info(usuario.toString());
			if (usuario.getUnidadDTO().getLoginHeaderMultiOficinaB64()!=null && !usuario.getUnidadDTO().getLoginHeaderMultiOficinaB64().isEmpty()) {
				request.getHeaders().set(NOMBRE_HEADER_LOGIN_MULTI_OFICINA, usuario.getUnidadDTO().getLoginHeaderMultiOficinaB64());
			}
		}
		ClientHttpResponse response = execution.execute(request, body);
        return response;
	}

}