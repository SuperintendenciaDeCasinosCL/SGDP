package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

//@JsonRootName(value = "data")
public class TokenDTO implements Serializable {


	@JsonProperty(value = "access_token")
	private String accessToken;

	@JsonProperty(value = "token_type")
	private String tokenType;

	@JsonProperty(value = "refresh_token")
	private String refreshToken;

	@JsonProperty(value = "expires_in")
	private String expiresIn;

	@JsonProperty(value = "scope")
	private String scope;
	
	@JsonProperty(value = "jti")
	private String jti;

	public TokenDTO() {
		super();
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}



	public String getJti() {
		return jti;
	}

	public void setJti(String jti) {
		this.jti = jti;
	}



	@Override
	public String toString() {
		return "TokenDTO [accessToken=" + accessToken + ", tokenType=" + tokenType + ", refreshToken=" + refreshToken
				+ ", expiresIn=" + expiresIn + ", scope=" + scope + ", jti=" + jti + "]";
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 926319757640202644L;
}
