<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="requisitoStyle" value="tarea-rds" />

<c:if test="${not empty mapParametrosDeTareaDTOTitulo}">
				
	<form name="parametrosForm" id="parametrosForm">

		<c:forEach items="${mapParametrosDeTareaDTOTitulo}" var="entry" varStatus="loop"> 					
								   
			<div class="col-sm-12"><div class="col-sm-4"><strong>${entry.key}</strong></div><div class="col-sm-8"></div></div>	
	   	
	   		<c:forEach items="${entry.value}" var="parametroDeTareaDTO" varStatus="loopParametrosDeTareaDTOTitulo">
	   		
	   			<c:choose>

		    		<c:when test="${parametroDeTareaDTO.esSNC eq true}">
		    			
		    			<c:set var="requisitoStyle" value="tarea-snc" />
		    		
		    		</c:when>				    
	    		
	    		</c:choose> 
	    		
	    		<div class="col-sm-12 ${requisitoStyle}">
	    		
	    			${parametroDeTareaDTO.textoHtml} 			
	    		
	    		</div>
	    		
	    		<c:set var="requisitoStyle" value="tarea-rds" />
	   		
		   		<!-- <div class="col-sm-12" ${requisitoStyle}>							   		
		   			<c:choose>							   			
		   				<c:when test="${not empty entry.key}">
		   					<div class="col-sm-2">
		   						(${parametroDeTareaDTO.nombreDeTipoDeRequisito}) -	${parametroDeTareaDTO.nombreParamTarea}
		   					</div>
		   					${parametroDeTareaDTO.textoHtml}
		   				</c:when>							   				
		   				<c:otherwise>
		   					<div class="col-sm-1"></div>
		   					${parametroDeTareaDTO.textoHtml}
		   				</c:otherwise>							   			
		   			</c:choose>	
		   		</div>	
		   		 -->					   					   			
	   		
	   		</c:forEach>
	   		
	   		<div class="row">	   		
	   		</div> 	   		
		</c:forEach>
	
	</form>	

</c:if>	