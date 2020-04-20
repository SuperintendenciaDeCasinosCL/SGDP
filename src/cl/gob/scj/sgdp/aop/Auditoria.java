package cl.gob.scj.sgdp.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import cl.gob.scj.sgdp.config.Constantes;

@Component
@Aspect
public class Auditoria {
	
	private static final Logger log = Logger.getLogger(Auditoria.class);
	
	@Pointcut(Constantes.POINT_CUT_INSERT_DAO)
	public void insertDao() {}
	
	@After("insertDao()")
	public void logAfter(JoinPoint joinPoint) {
		
		log.debug("logAfter() is running!");
		log.debug("hijacked : " + joinPoint.getSignature().getName());
		log.debug("******");

	}

}
