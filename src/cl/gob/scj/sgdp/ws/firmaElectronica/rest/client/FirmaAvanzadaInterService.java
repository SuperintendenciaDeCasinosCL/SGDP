package cl.gob.scj.sgdp.ws.firmaElectronica.rest.client;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.ws.firmaElectronica.rest.request.FirmaAvanzadaRequest;
import cl.gob.scj.sgdp.ws.firmaElectronica.rest.response.FirmaAvanzadaMinSegPresResponse;
import cl.gob.scj.sgdp.ws.firmaElectronica.rest.response.TokenResponse;

@Service	
public interface FirmaAvanzadaInterService {

	public TokenResponse firmarDocumentoConFEA(FirmaAvanzadaRequest firmaAvanzadaRequest) throws Exception;
	
	public FirmaAvanzadaMinSegPresResponse getDocumentosFEA(TokenResponse tokenResponse, String otp) throws Exception;
	
}
