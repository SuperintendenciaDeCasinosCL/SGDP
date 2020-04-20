package cl.gob.scj.sgdp.util;

import java.util.HashMap;

import com.auth0.jwt.JWTSigner;

public class EncriptacionUtil {

	public static String encriptaConHS256(String secreto, String proposito, String entidad, String expiracion, String rut) {
		
		JWTSigner signer = new JWTSigner(secreto);
		HashMap<String, Object> claims = new HashMap<String, Object>();
		claims.put("purpose", proposito);
		claims.put("entity", entidad);
		claims.put("expiration", expiracion);
		claims.put("run", rut);
		
		String jwt = signer.sign(claims);
		
		return jwt;
	}	
	
}