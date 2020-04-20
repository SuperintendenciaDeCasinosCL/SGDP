package cl.gob.scj.sgdp.util;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

import org.springframework.http.client.SimpleClientHttpRequestFactory;

public class SgdpSimpleClientHttpRequestFactory extends SimpleClientHttpRequestFactory {

		private final HostnameVerifier verifier;

		public SgdpSimpleClientHttpRequestFactory(HostnameVerifier verifier) {
	      this.verifier = verifier;
		}
	
		@Override
		protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
	      if (connection instanceof HttpsURLConnection) {
	         ((HttpsURLConnection) connection).setHostnameVerifier(verifier);
	      }
	      super.prepareConnection(connection, httpMethod);
		}
	
}
