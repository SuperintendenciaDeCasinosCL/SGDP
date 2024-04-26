package cl.gob.scj.sgdp.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class BasicAuthFilter implements Filter {
	
	private static final Logger log = Logger.getLogger(BasicAuthFilter.class);

	public static final String PARAM_USER = "user";
	public static final String PARAM_PASSWORD = "password";

	private String user;
	private String password;
	
	private FilterConfig filterConfig;
	
    public FilterConfig getFilterConfig() {
        return this.filterConfig;
    }
    public void setFilterConfig (FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }
	
	@Override
	public void destroy() {
		this.filterConfig = null;

	}

	@Override
   public void doFilter( @NotNull final ServletRequest request, @NotNull final ServletResponse response,
           @NotNull final FilterChain chain ) throws IOException, ServletException {
       final HttpServletRequest httpRequest = (HttpServletRequest) request;
       final HttpServletResponse httpResponse = (HttpServletResponse) response;
       Enumeration<String> headersNames = httpRequest.getHeaderNames();
       while (headersNames.hasMoreElements()) {
    	   String headerName = headersNames.nextElement();
    	   log.info(headerName + ": " + httpRequest.getHeader(headerName));
       }
       final String auth = httpRequest.getHeader("Authorization");
       if ( auth != null ) {
           final int index = auth.indexOf(' ');
           if ( index > 0 ) {
               final String[] credentials = StringUtils.split( new String( Base64.decodeBase64( auth.substring( index ) ), Charsets.UTF_8), ':' );
               if ( credentials.length == 2 && user.equals( credentials[0] ) && password.equals( credentials[1] ) ) {
                   chain.doFilter( httpRequest, httpResponse );
                   return;
               }
           }
       }
       httpResponse.setHeader( "WWW-Authenticate", "Basic realm=api" );
       httpResponse.sendError( HttpServletResponse.SC_UNAUTHORIZED );
   }

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.filterConfig = config;
		user = config.getInitParameter(PARAM_USER);
		password = config.getInitParameter(PARAM_PASSWORD);
		if (user==null || user.isEmpty() ) {
			throw new ServletException( "No user provided in filter configuration" );
		}

		if (password==null || password.isEmpty() ) {
			throw new ServletException("No password provided in filter configuration");
		}

	}

}

