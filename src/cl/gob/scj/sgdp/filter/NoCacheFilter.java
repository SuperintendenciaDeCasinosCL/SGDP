package cl.gob.scj.sgdp.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class NoCacheFilter implements Filter {
	
	private static final Logger log = Logger.getLogger(NoCacheFilter.class);

	private String excludePatterns;
	
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
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		try {
			
			excludePatterns = filterConfig.getInitParameter("excludePatterns");
			
			/*String url = ((HttpServletRequest) request).getRequestURL().toString();
			
			if (!url.contains(excludePatterns)) {
				
			}*/
			
			
			if (response instanceof HttpServletResponse) {
				HttpServletResponse httpresponse = (HttpServletResponse)response ;
				// Set the Cache-Control and Expires header
				httpresponse.setHeader("Cache-Control", "no-cache");				
				// Set standard HTTP/1.0 no-cache header.
				httpresponse.setHeader("Pragma", "no-cache");
				httpresponse.setHeader("Expires", "0");
				// Print out the URL we're filtering
				String name = ((HttpServletRequest)request).getRequestURI();
				log.debug("No Cache Filtering: " + name) ;
			}
			chain.doFilter (request, response);
		} catch (IOException e) {
			log.debug("IOException in NoCacheFilter");			
		} catch (ServletException e) {
			log.debug("ServletException in NoCacheFilter");
		}

	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.filterConfig = config;

	}

}
