package cl.gob.scj.sgdp.ws.firmaElectronica.rest.client;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.ws.firmaElectronica.rest.request.FirmaAvanzadaRequest;
import cl.gob.scj.sgdp.ws.firmaElectronica.rest.response.FirmaAvanzadaMinSegPresResponse;
import cl.gob.scj.sgdp.ws.firmaElectronica.rest.response.FirmaAvanzadaMinSegPresResponseV3;
import cl.gob.scj.sgdp.ws.firmaElectronica.rest.response.TokenResponse;

@Service	
public interface FirmaAvanzadaInterService {

	TokenResponse firmarDocumentoConFEA(FirmaAvanzadaRequest firmaAvanzadaRequest) throws Exception;
	
	FirmaAvanzadaMinSegPresResponse getDocumentosFEA(TokenResponse tokenResponse, String otp) throws Exception;
	
	FirmaAvanzadaMinSegPresResponseV3 firmarDocumentoConFEAV3(FirmaAvanzadaRequest firmaAvanzadaRequest, String otp) throws Exception;
	
}