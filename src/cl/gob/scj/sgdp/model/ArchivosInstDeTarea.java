package cl.gob.scj.sgdp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cl.gob.scj.sgdp.config.Constantes;

@Entity
@Table(name="\"SGDP_ARCHIVOS_INST_DE_TAREA\"")
@NamedQueries({
	@NamedQuery(name="ArchivosInstDeTarea.findAll", query="SELECT a FROM ArchivosInstDeTarea a"),
	@NamedQuery(name="ArchivosInstDeTarea.getTodosArchivosSubidosRequeridosPorIdExpediente", 
	query="SELECT HA FROM ArchivosInstDeTarea HA, DocumentoDeSalidaDeTarea D "
			+ "INNER JOIN HA.instanciaDeTarea IT "
			+ "INNER JOIN IT.instanciaDeProceso IP "
			+ "INNER JOIN HA.tipoDeDocumento TD "
			+ "INNER JOIN IT.tarea T "						
			+ "WHERE D.id.tarea.idTarea = T.idTarea "
			+ "AND D.id.tipoDeDocumento.idTipoDeDocumento = TD.idTipoDeDocumento "
			+ "AND IP.idExpediente = :idExpediente"),
	
	@NamedQuery(name="ArchivosInstDeTarea.getArchivosPorIdArchivo", 
	query="SELECT HA FROM ArchivosInstDeTarea HA "					
			+ "WHERE HA.idArchivoCms = :idArchivoCms "),
	
	@NamedQuery(name="ArchivosInstDeTarea.getArchivosPorIdArchivoEnTareaConNumero", 
	query="SELECT HA FROM ArchivosInstDeTarea HA "					
			+ "WHERE HA.idArchivoCms = :idArchivoCms "
			+ "AND (HA.instanciaDeTarea.tarea.asignaNumDoc = true OR HA.instanciaDeTarea.tarea.numAuto = true)"),
	
	@NamedQuery(name="ArchivosInstDeTarea.getArchivosPorIdInstanciaDeTarea", 
	query="SELECT HA FROM ArchivosInstDeTarea HA "					
			+ "WHERE HA.instanciaDeTarea.idInstanciaDeTarea =:idInstanciaDeTarea"),
	
	@NamedQuery(name="ArchivosInstDeTarea.getArchivosPorIdInstanciaDeTareaIdUsuario", 
				query="SELECT HA FROM ArchivosInstDeTarea HA "					
						+ "WHERE HA.idUsuario =:idUsuario "
						+ "AND HA.instanciaDeTarea.idInstanciaDeTarea =:idInstanciaDeTarea"),
	
	@NamedQuery(name="ArchivosInstDeTarea.getArchivosInstDeTareaPorIdArchivoIdInstanciaDeTareaIdUsuario", 
	query="SELECT HA FROM ArchivosInstDeTarea HA "					
			+ "WHERE HA.idArchivoCms =:idArchivo "
			+ "AND HA.idUsuario =:idUsuario "
			+ "AND HA.instanciaDeTarea.idInstanciaDeTarea =:idInstanciaDeTarea"),
	
	@NamedQuery(name="ArchivosInstDeTarea.getArchivosInstDeTareaPorIdInstanciaDeTareaIdUsuarioNomArchivo", 
	query="SELECT HA FROM ArchivosInstDeTarea HA "					
			+ "WHERE HA.idUsuario =:idUsuario "
			+ "AND HA.instanciaDeTarea.idInstanciaDeTarea =:idInstanciaDeTarea "
			+ "AND HA.nombreArchivo =:nombreArchivo"),	
	
	@NamedQuery(name="ArchivosInstDeTarea.getTodosLosDocSubidosEnInstaciaDeProcPorIdInstTareaDeInstDeTareasAntOIguales", 
	query="SELECT ai FROM ArchivosInstDeTarea ai "
			+ "INNER JOIN ai.instanciaDeTarea i "
			+ "INNER JOIN i.tarea t "
			+ "WHERE t.orden <= "
			+ "( "
			+ "SELECT t2.orden FROM Tarea t2, InstanciaDeTarea i2 "
			+ "WHERE i2.tarea.idTarea = t2.idTarea "
			+ "AND t2.proceso.idProceso = t.proceso.idProceso "
			+ "AND i2.instanciaDeProceso.idInstanciaDeProceso = i.instanciaDeProceso.idInstanciaDeProceso "
			+ "AND i2.idInstanciaDeTarea = :idInstanciaDeTarea "
			+ ")"),
	
	@NamedQuery(name="ArchivosInstDeTarea.getTodosLosDocSubidosPorIdInstTareaDeInstDeTareasAntOIguales", 
	query="SELECT i.idInstanciaDeTarea as idInstanciaDeTarea, t.nombreTarea as nombreTarea, " 
			+ "ai.idArchivoCms as idArchivoCms, ai.mimeType as mimeType, ai.nombreArchivo as nombreArchivo, ai.idUsuario as idUsuario, max(ai.fechaSubido) as fechaSubido, "
			+ "td.idTipoDeDocumento as idTipoDeDocumento, td.nombreDeTipoDeDocumento as nombreDeTipoDeDocumento, "
			+ "( "
			+ " SELECT h.comentario from HistoricoDeInstDeTarea h "
			+ "WHERE h.instanciaDeTareaDeOrigen.idInstanciaDeTarea = i.idInstanciaDeTarea "
			+ "AND h.fechaMovimiento = "
			+ "( "
			+ " SELECT MAX(h2.fechaMovimiento) FROM HistoricoDeInstDeTarea h2 "
			+ " WHERE h2.instanciaDeTareaDeOrigen.idInstanciaDeTarea = i.idInstanciaDeTarea "
			+ " AND h2.comentario IS NOT NULL AND h2.comentario <> '' "
			+ ") "
			+ ") as ultimoComentario, "
			+ "t.puedeVisarDocumentos as puedeVisarDocumentos, t.puedeAplicarFEA as puedeAplicarFEA "
			+ "FROM ArchivosInstDeTarea ai "
			+ "INNER JOIN ai.instanciaDeTarea i "
			+ "INNER JOIN i.tarea t "
            + "INNER JOIN ai.tipoDeDocumento td " 
			+ "WHERE t.orden <= "
			+ "( "
			+ "SELECT t2.orden FROM Tarea t2, InstanciaDeTarea i2 "
			+ "WHERE i2.tarea.idTarea = t2.idTarea "
			+ "AND t2.proceso.idProceso = t.proceso.idProceso "
			+ "AND i2.instanciaDeProceso.idInstanciaDeProceso = i.instanciaDeProceso.idInstanciaDeProceso "
			+ "AND i2.idInstanciaDeTarea = :idInstanciaDeTarea "
			+ ") "
      + "GROUP BY i.idInstanciaDeTarea, t.nombreTarea, ai.idArchivoCms, ai.mimeType, ai.nombreArchivo, "
      + "ai.idUsuario, td.idTipoDeDocumento, td.nombreDeTipoDeDocumento, t.puedeVisarDocumentos, t.puedeAplicarFEA "),
	
	@NamedQuery(name="ArchivosInstDeTarea.getArchivosPorIdInstanciaDeTareaIdUsuarioTiposDeDocDeTarea", 
	query=" SELECT HA FROM ArchivosInstDeTarea HA "					
			+ " WHERE HA.idUsuario =:idUsuario "
			+ " AND HA.instanciaDeTarea.idInstanciaDeTarea =:idInstanciaDeTarea "
			+ " AND HA.tipoDeDocumento.idTipoDeDocumento IN (SELECT ti.idTipoDeDocumento FROM Tarea t, DocumentoDeSalidaDeTarea d, TipoDeDocumento ti "
			+ " WHERE HA.instanciaDeTarea.tarea.idTarea = t.idTarea "
			+ " AND t.idTarea = d.id.tarea.idTarea "
			+ " AND d.id.tipoDeDocumento.idTipoDeDocumento = ti.idTipoDeDocumento)"
			+ "	AND HA.fechaSubido >= "
			+ "	 	( SELECT max(HIT2.fechaMovimiento) "
			+ "		FROM HistoricoDeInstDeTarea HIT2 "
			+ "		WHERE HIT2.instanciaDeTareaDeDestino.idInstanciaDeTarea =  HA.instanciaDeTarea.idInstanciaDeTarea "
			//+ "		ORDER BY HIT2.fechaMovimiento DESC"
			+ "		 ) " ),
			
	@NamedQuery(name="ArchivosInstDeTarea.getArchivosDeInstDeTareaAnteriorPorIdInstanTareaPorIdTipoDeDocumento", 
	query="SELECT distinct ai FROM ArchivosInstDeTarea ai, HistoricoDeInstDeTarea h "
			+ "WHERE ai.instanciaDeTarea.idInstanciaDeTarea = h.instanciaDeTareaDeOrigen.idInstanciaDeTarea "
			+ "AND h.instanciaDeTareaDeDestino.idInstanciaDeTarea = :idInstanciaDeTarea "
			+ "AND h.accionHistInstDeTarea.idAccionHistoricoInstDeTarea = 3 "
			+ "AND ai.fechaSubido = "
			+ "( "
			+ "SELECT MAX (ai2.fechaSubido) FROM ArchivosInstDeTarea ai2, HistoricoDeInstDeTarea h2 "
			+ "WHERE ai2.instanciaDeTarea.idInstanciaDeTarea = h2.instanciaDeTareaDeOrigen.idInstanciaDeTarea "
			+ "AND h2.instanciaDeTareaDeDestino.idInstanciaDeTarea = :idInstanciaDeTarea "	
			+ "AND h2.accionHistInstDeTarea.idAccionHistoricoInstDeTarea = 3 "
			+ "AND ai2.tipoDeDocumento.idTipoDeDocumento = :idTipoDeDocumento"
			+ ") "
			+ "AND ai.tipoDeDocumento.idTipoDeDocumento = :idTipoDeDocumento"
			),	
	
	@NamedQuery(name="ArchivosInstDeTarea.getArchivosObligatoriosDeInstDeTareaAnteriorPorIdInstanTarea", 
	query="SELECT distinct ai FROM ArchivosInstDeTarea ai, HistoricoDeInstDeTarea h, DocumentoDeSalidaDeTarea d "
			+ "WHERE ai.instanciaDeTarea.idInstanciaDeTarea = h.instanciaDeTareaDeOrigen.idInstanciaDeTarea "
			+ "AND h.instanciaDeTareaDeDestino.idInstanciaDeTarea = :idInstanciaDeTarea "
			+ "AND h.accionHistInstDeTarea.idAccionHistoricoInstDeTarea = 3 "	
			+ "AND ai.tipoDeDocumento.idTipoDeDocumento = d.id.tipoDeDocumento.idTipoDeDocumento"			
			),
	
	@NamedQuery(name="ArchivosInstDeTarea.actualizaEstatusDeVisacionYFirmasAFalsePorIdArchivoIdProceso",
	query="UPDATE ArchivosInstDeTarea HA "
			+ "SET HA.estaVisado = false "
			+ ", HA.estaFirmadoConFEAWebStart = false "
			+ ", HA.estaFirmadoConFEACentralizada = false "
			+ "WHERE HA.idArchivoCms = :idArchivoCms "
			+ "AND HA.instanciaDeTarea.instanciaDeProceso.idInstanciaDeProceso = :idInstanciaDeProceso"		
			),
	@NamedQuery(name="ArchivosInstDeTarea.getArchivosInstDeTareaPorIdProcesoDistinIdInstanTarea",
	query="SELECT HA FROM ArchivosInstDeTarea HA "		
			+ "WHERE HA.idArchivoCms = :idArchivoCms "				
			+ "AND HA.instanciaDeTarea.instanciaDeProceso.idInstanciaDeProceso = :idInstanciaDeProceso "
			+ "AND HA.instanciaDeTarea.idInstanciaDeTarea != :idInstanciaDeTarea "
			),
	
	@NamedQuery(name="ArchivosInstDeTarea.getArchivosPorIdInstanciaDeTareaIdTipoDeDocumentoIdUsuarioNombreArchivo", 
	query="SELECT HA FROM ArchivosInstDeTarea HA "					
			+ "WHERE HA.idUsuario = :idUsuario "
			+ "AND HA.instanciaDeTarea.idInstanciaDeTarea = :idInstanciaDeTarea "
			+ "AND HA.tipoDeDocumento.idTipoDeDocumento = :idTipoDeDocumento "
			+ "AND HA.nombreArchivo = :nombreArchivo"),
	
	@NamedQuery(name="ArchivosInstDeTarea.getArchivosPorIdInstanciaDeTareaIdTipoDeDocumentoIdUsuario", 
	query="SELECT HA FROM ArchivosInstDeTarea HA "					
			+ "WHERE HA.idUsuario = :idUsuario "
			+ "AND HA.instanciaDeTarea.idInstanciaDeTarea = :idInstanciaDeTarea "
			+ "AND HA.tipoDeDocumento.idTipoDeDocumento = :idTipoDeDocumento"),
	
	@NamedQuery(name="ArchivosInstDeTarea.getArchivoPorIdInstanciaDeTareaIdTipoDeDocumento", 
	query="SELECT HA FROM ArchivosInstDeTarea HA "					
			+ "WHERE HA.instanciaDeTarea.idInstanciaDeTarea = :idInstanciaDeTarea "
			+ "AND HA.tipoDeDocumento.idTipoDeDocumento = :idTipoDeDocumento"),
	
	@NamedQuery(name="ArchivosInstDeTarea.getTodosLosDocSubidosPorIdInstTarea", 
	query="SELECT i.idInstanciaDeTarea as idInstanciaDeTarea, t.nombreTarea as nombreTarea, " 
			+ "ai.idArchivoCms as idArchivoCms, ai.mimeType as mimeType, ai.nombreArchivo as nombreArchivo, ai.idUsuario as idUsuario, max(ai.fechaSubido) as fechaSubido, "
			+ "td.idTipoDeDocumento as idTipoDeDocumento, td.nombreDeTipoDeDocumento as nombreDeTipoDeDocumento, "
			+ "( "
			+ " SELECT h.comentario from HistoricoDeInstDeTarea h "
			+ "WHERE h.instanciaDeTareaDeOrigen.idInstanciaDeTarea = i.idInstanciaDeTarea "
			+ "AND h.fechaMovimiento = "
			+ "( "
			+ " SELECT MAX(h2.fechaMovimiento) FROM HistoricoDeInstDeTarea h2 "
			+ " WHERE h2.instanciaDeTareaDeOrigen.idInstanciaDeTarea = i.idInstanciaDeTarea "
			+ " AND h2.comentario IS NOT NULL AND h2.comentario <> '' "
			+ ") "
			+ ") as ultimoComentario, "
			+ "t.puedeVisarDocumentos as puedeVisarDocumentos, t.puedeAplicarFEA as puedeAplicarFEA, "
			+ "ip.idExpediente as idExpediente, "
			+ "( "
			+ "SELECT rs.nombreResponsabilidad "
			+ "FROM ResponsabilidadTarea rt "
			+ "INNER JOIN rt.id.responsabilidad rs "
			+ "WHERE rt.id.tarea.idTarea = t.idTarea "
			+ "AND rs.idResponsabilidad = (SELECT MAX(w.idResponsabilidad) FROM ResponsabilidadTarea k INNER JOIN k.id.responsabilidad w WHERE k.id.tarea.idTarea = t.idTarea ) "
			+ ") as nombreResponsabilidad, "			
			+ "( "
			+ "SELECT l.estaFirmadoConFEAWebStart "
			+ "FROM ArchivosInstDeTarea l "
			+ "WHERE l.instanciaDeTarea.idInstanciaDeTarea = i.idInstanciaDeTarea AND l.idArchivoCms = ai.idArchivoCms "
			+ "AND l.idArchivosInstDeTarea = ( SELECT MAX(l2.idArchivosInstDeTarea) FROM ArchivosInstDeTarea l2 WHERE l2.instanciaDeTarea.idInstanciaDeTarea = i.idInstanciaDeTarea AND l2.idArchivoCms = ai.idArchivoCms ) "
			+ ") as estaFirmadoConFEAWebStart, "			
			+ "( "
			+ "SELECT l.estaFirmadoConFEACentralizada "
			+ "FROM ArchivosInstDeTarea l "
			+ "WHERE l.instanciaDeTarea.idInstanciaDeTarea = i.idInstanciaDeTarea AND l.idArchivoCms = ai.idArchivoCms "
			+ "AND l.idArchivosInstDeTarea = ( SELECT MAX(l2.idArchivosInstDeTarea) FROM ArchivosInstDeTarea l2 WHERE l2.instanciaDeTarea.idInstanciaDeTarea = i.idInstanciaDeTarea AND l2.idArchivoCms = ai.idArchivoCms ) "
			+ ") as estaFirmadoConFEACentralizada, "
			+ "td.conformaExpediente as conformaExpediente "
			+ "FROM ArchivosInstDeTarea ai "
			+ "INNER JOIN ai.instanciaDeTarea i "
			+ "INNER JOIN i.tarea t "
            + "INNER JOIN ai.tipoDeDocumento td " 
			+ "INNER JOIN ai.instanciaDeTarea.instanciaDeProceso ip "
			+ "WHERE ip.idInstanciaDeProceso = "
			+ "( "
			+ "SELECT i2.instanciaDeProceso.idInstanciaDeProceso FROM InstanciaDeTarea i2 "
			+ "WHERE i2.idInstanciaDeTarea = :idInstanciaDeTarea "
			+ ") "
      + "GROUP BY i.idInstanciaDeTarea, t.nombreTarea, ai.idArchivoCms, ai.mimeType, ai.nombreArchivo, ai.estaFirmadoConFEAWebStart, ai.estaFirmadoConFEACentralizada, "
      + "ai.idUsuario, td.idTipoDeDocumento, td.nombreDeTipoDeDocumento, t.puedeVisarDocumentos, t.puedeAplicarFEA, ip.idExpediente, t.idTarea ORDER BY fechaSubido DESC "),
	
	@NamedQuery(name="ArchivosInstDeTarea.getArchivosEnviadosPorIdInstanciaDeTareaIdTipoDeDocumento",
	query="SELECT DISTINCT HA FROM ArchivosInstDeTarea HA, DocumentoDeSalidaDeTarea D "
			+ "INNER JOIN HA.instanciaDeTarea IT "
			+ "INNER JOIN IT.instanciaDeProceso IP "
			+ "INNER JOIN IT.tarea T "
			+ "INNER JOIN IT.estadoDeTarea ET "					
			+ "WHERE IP.idInstanciaDeProceso = ( "
												+ "SELECT IP2.idInstanciaDeProceso "
												+ "FROM InstanciaDeTarea IT2 "
												+ "INNER JOIN IT2.instanciaDeProceso IP2 "												
												+ "WHERE IT2.idInstanciaDeTarea = :idInstanciaDeTarea "												
											+ ") "
			+ "AND T.orden = ( "
								+ "SELECT MAX (T3.orden) "
								+ "FROM ArchivosInstDeTarea HA2 "
								+ "INNER JOIN HA2.instanciaDeTarea IT3 "
								+ "INNER JOIN IT3.instanciaDeProceso IP3 "
								+ "INNER JOIN IT3.estadoDeTarea ET3 "
								+ "INNER JOIN IT3.tarea T3 "							
								+ "WHERE IP3.idInstanciaDeProceso = ( "
																	+ "SELECT IP4.idInstanciaDeProceso "
																	+ "FROM InstanciaDeTarea IT4 "
																	+ "INNER JOIN IT4.instanciaDeProceso IP4 "
																	+ "WHERE IT4.idInstanciaDeTarea = :idInstanciaDeTarea "
																+ " ) "
								+ "AND ET3.idEstadoDeTarea = 3 "
						+ " ) "
			+ "AND T.idTarea = D.id.tarea.idTarea "
			+ "AND HA.tipoDeDocumento.idTipoDeDocumento = :idTipoDeDocumento "
			+ "AND D.id.tipoDeDocumento.idTipoDeDocumento = :idTipoDeDocumento "
			+ "AND ET.idEstadoDeTarea = 3 "
			+ "GROUP BY HA.idArchivosInstDeTarea "
			+ "HAVING HA.fechaSubido = ( "
										+ "SELECT MAX(HA3.fechaSubido) FROM ArchivosInstDeTarea HA3 "
										+ "WHERE HA3.idArchivosInstDeTarea IN ( "
																			/****/
																				+ "SELECT HA3.idArchivosInstDeTarea "
																				+ "FROM ArchivosInstDeTarea HA3, DocumentoDeSalidaDeTarea D2 "
																				+ "INNER JOIN HA3.instanciaDeTarea IT5 "
																				+ "INNER JOIN IT5.instanciaDeProceso IP5 "
																				+ "INNER JOIN IT5.tarea T5 "
																				+ "INNER JOIN IT5.estadoDeTarea ET5 "					
																				+ "WHERE IP5.idInstanciaDeProceso = ( "
																														+ "SELECT IP6.idInstanciaDeProceso "
																														+ "FROM InstanciaDeTarea IT6 "
																														+ "INNER JOIN IT6.instanciaDeProceso IP6 "
																														+ "WHERE IT6.idInstanciaDeTarea = :idInstanciaDeTarea "																														
																													+ ") "
																				+ "AND T5.orden = ( "
																									+ "SELECT MAX (T7.orden) "
																									+ "FROM ArchivosInstDeTarea HA4 "
																									+ "INNER JOIN HA4.instanciaDeTarea IT7 "
																									+ "INNER JOIN IT7.tarea T7 "
																									+ "INNER JOIN IT7.estadoDeTarea ET7 "
																									+ "INNER JOIN IT7.instanciaDeProceso IP7 "
																									+ "WHERE IP7.idInstanciaDeProceso = ( "
																																							+ "SELECT IP8.idInstanciaDeProceso "
																																							+ "FROM InstanciaDeTarea IT8 "
																																							+ "INNER JOIN IT8.instanciaDeProceso IP8 "
																																							+ "WHERE IT8.idInstanciaDeTarea = :idInstanciaDeTarea "
																																							+ ") "
																									+ "AND ET7.idEstadoDeTarea = 3 "
																								+ ") "
																				+ "AND T5.idTarea = D2.id.tarea.idTarea "
																				+ "AND D2.id.tipoDeDocumento.idTipoDeDocumento = :idTipoDeDocumento "
																				+ "AND ET5.idEstadoDeTarea = 3 "
																			/****/
																			+ ") "
									+ " AND HA3.tipoDeDocumento.idTipoDeDocumento = :idTipoDeDocumento   )"),
	
	@NamedQuery(name="ArchivosInstDeTarea.getArchivosEnviadosPorIdInstanciaDeTareaNombreDeDocumento",
	query="SELECT DISTINCT HA FROM ArchivosInstDeTarea HA, DocumentoDeSalidaDeTarea D, TipoDeDocumento TD "
			+ "INNER JOIN HA.instanciaDeTarea IT "
			+ "INNER JOIN IT.instanciaDeProceso IP "
			+ "INNER JOIN IT.tarea T "
			+ "INNER JOIN IT.estadoDeTarea ET "					
			+ "WHERE IP.idInstanciaDeProceso = ( "
												+ "SELECT IP2.idInstanciaDeProceso "
												+ "FROM InstanciaDeTarea IT2 "
												+ "INNER JOIN IT2.instanciaDeProceso IP2 "												
												+ "WHERE IT2.idInstanciaDeTarea = :idInstanciaDeTarea "												
											+ ") "	
			+ "AND T.idTarea = D.id.tarea.idTarea "
			+ "AND HA.tipoDeDocumento.idTipoDeDocumento = D.id.tipoDeDocumento.idTipoDeDocumento "
			+ "AND D.id.tipoDeDocumento.idTipoDeDocumento = TD.idTipoDeDocumento "
			//+ "AND TD.nombreDeTipoDeDocumento = :nombreDeTipoDeDocumento "
			+ "AND levenshtein(upper(replace(TD.nombreDeTipoDeDocumento, ' ', '')), upper(replace(:nombreDeTipoDeDocumento, ' ', ''))) <= (SELECT p.valorParametroNumerico FROM Parametro p where p.idParametro = 63) "
			+ "AND ET.idEstadoDeTarea = 3 "
			+ "GROUP BY HA.idArchivosInstDeTarea "
			+ "HAVING to_char(HA.fechaSubido,'DD/MM/YYYY HH:MI') = ( "
										+ "SELECT MAX(to_char(HA3.fechaSubido,'DD/MM/YYYY HH:MI')) FROM ArchivosInstDeTarea HA3, TipoDeDocumento TD2 "
										+ "WHERE HA3.idArchivosInstDeTarea IN ( "
																			/****/
																				+ "SELECT HA4.idArchivosInstDeTarea "
																				+ "FROM ArchivosInstDeTarea HA4, DocumentoDeSalidaDeTarea D2, TipoDeDocumento TD3 "
																				+ "INNER JOIN HA4.instanciaDeTarea IT5 "
																				+ "INNER JOIN IT5.instanciaDeProceso IP5 "
																				+ "INNER JOIN IT5.tarea T5 "
																				+ "INNER JOIN IT5.estadoDeTarea ET5 "					
																				+ "WHERE IP5.idInstanciaDeProceso = ( "
																														+ "SELECT IP6.idInstanciaDeProceso "
																														+ "FROM InstanciaDeTarea IT6 "
																														+ "INNER JOIN IT6.instanciaDeProceso IP6 "
																														+ "WHERE IT6.idInstanciaDeTarea = :idInstanciaDeTarea "																														
																													+ ") "																			
																				+ "AND T5.idTarea = D2.id.tarea.idTarea "
																				+ "AND D2.id.tipoDeDocumento.idTipoDeDocumento = TD3.idTipoDeDocumento "
																				+ "AND D2.id.tipoDeDocumento.idTipoDeDocumento =  HA4.tipoDeDocumento.idTipoDeDocumento "
																				//+ "AND TD3.nombreDeTipoDeDocumento = :nombreDeTipoDeDocumento "
																				+ "AND levenshtein(upper(replace(TD3.nombreDeTipoDeDocumento, ' ', '')), upper(replace(:nombreDeTipoDeDocumento, ' ', ''))) <= (SELECT p.valorParametroNumerico FROM Parametro p where p.idParametro = 63) "
																				+ "AND ET5.idEstadoDeTarea = 3 "
																			/****/
																			+ ") "
									+ " AND HA3.tipoDeDocumento.idTipoDeDocumento =  TD2.idTipoDeDocumento  "
									//+ " AND TD2.nombreDeTipoDeDocumento = :nombreDeTipoDeDocumento "
									//+ "AND levenshtein(upper(replace(TD2.nombreDeTipoDeDocumento, ' ', '')), upper(replace(:nombreDeTipoDeDocumento, ' ', ''))) <= (SELECT p.valorParametroNumerico FROM Parametro p where p.idParametro = 63) "
									+ ")"),
			
	@NamedQuery(name="ArchivosInstDeTarea.getArchivosRequPorNombreArchivoIdExpediente", 
	query="SELECT HA FROM ArchivosInstDeTarea HA "						
			+ "WHERE HA.nombreArchivo = :nombreArchivo "
			+ "AND HA.instanciaDeTarea.instanciaDeProceso.idExpediente = :idExpediente "
			+ "AND HA.tipoDeDocumento.idTipoDeDocumento IN ( "
			+ "				SELECT DS.id.tipoDeDocumento.idTipoDeDocumento FROM DocumentoDeSalidaDeTarea DS "
			+ "				WHERE DS.id.tarea.idTarea IN ( "
			+ "											SELECT it.tarea.idTarea FROM InstanciaDeTarea it "
			+ "											WHERE it.instanciaDeProceso.idExpediente = :idExpediente "
			+ "											)"
			+ "				)"
			+ ""),
	
	@NamedQuery(name="ArchivosInstDeTarea.buscaArchivoFirmadoDeLaInstanciaDeTarea", 
	query=" SELECT ha FROM ArchivosInstDeTarea ha "
			+ " INNER JOIN ha.tipoDeDocumento  td "
			+ " where td.conformaExpediente = true   "
			+ " and ha.idArchivoCms = :idObjeto  "),
	
	@NamedQuery(name="ArchivosInstDeTarea.getIdDeDocumentosSubidosOficiales", 
	query="SELECT DISTINCT(HA.idArchivoCms) FROM ArchivosInstDeTarea HA "					
			+ "WHERE HA.tipoDeDocumento.conformaExpediente = true "
			+ "AND HA.instanciaDeTarea.tarea.conformaExpediente = true"),
	
	@NamedQuery(name="ArchivosInstDeTarea.getArchivosInstDeTareaPorIdInstTareaIdUsuarioNombreTipoDoc", 
	query="SELECT HA FROM ArchivosInstDeTarea HA "
			+ "INNER JOIN HA.tipoDeDocumento D "					
			+ "WHERE HA.instanciaDeTarea.idInstanciaDeTarea = :idInstanciaDeTarea "
			+ "AND HA.idUsuario = :idUsuario "
			+ "AND levenshtein(upper(replace(D.nombreDeTipoDeDocumento, ' ', '')), upper(replace(:nombreDeTipoDeDocumento, ' ', ''))) <= (SELECT p.valorParametroNumerico FROM Parametro p where p.idParametro = 63)"),
	
	@NamedQuery(name="ArchivosInstDeTarea.getArchivosPorIdInstanciaDeTareaTiposDeDocDeTarea", 
	query=" SELECT HA FROM ArchivosInstDeTarea HA "					
			+ " WHERE  HA.instanciaDeTarea.idInstanciaDeTarea =:idInstanciaDeTarea "
			+ " AND HA.tipoDeDocumento.idTipoDeDocumento IN (SELECT ti.idTipoDeDocumento FROM Tarea t, DocumentoDeSalidaDeTarea d, TipoDeDocumento ti "
			+ " WHERE HA.instanciaDeTarea.tarea.idTarea = t.idTarea "
			+ " AND t.idTarea = d.id.tarea.idTarea "
			+ " AND d.id.tipoDeDocumento.idTipoDeDocumento = ti.idTipoDeDocumento)"),
	
	@NamedQuery(name="ArchivosInstDeTarea.getTodosLosDocSubidosPorIdExpediente", 
	query="SELECT i.idInstanciaDeTarea as idInstanciaDeTarea, t.nombreTarea as nombreTarea, " 
			+ "ai.idArchivoCms as idArchivoCms, ai.mimeType as mimeType, ai.nombreArchivo as nombreArchivo, ai.idUsuario as idUsuario, max(ai.fechaSubido) as fechaSubido, "
			+ "td.idTipoDeDocumento as idTipoDeDocumento, td.nombreDeTipoDeDocumento as nombreDeTipoDeDocumento, "
			+ "( "
			+ " SELECT h.comentario from HistoricoDeInstDeTarea h "
			+ "WHERE h.instanciaDeTareaDeOrigen.idInstanciaDeTarea = i.idInstanciaDeTarea "
			+ "AND h.fechaMovimiento = "
			+ "( "
			+ " SELECT MAX(h2.fechaMovimiento) FROM HistoricoDeInstDeTarea h2 "
			+ " WHERE h2.instanciaDeTareaDeOrigen.idInstanciaDeTarea = i.idInstanciaDeTarea "
			+ " AND h2.comentario IS NOT NULL AND h2.comentario <> '' "
			+ ") "
			+ ") as ultimoComentario, "
			+ "t.puedeVisarDocumentos as puedeVisarDocumentos, t.puedeAplicarFEA as puedeAplicarFEA, "
			+ "ip.idExpediente as idExpediente, "
			+ "( "
			+ "SELECT rs.nombreResponsabilidad "
			+ "FROM ResponsabilidadTarea rt "
			+ "INNER JOIN rt.id.responsabilidad rs "
			+ "WHERE rt.id.tarea.idTarea = t.idTarea "
			+ "AND rs.idResponsabilidad = (SELECT MAX(w.idResponsabilidad) FROM ResponsabilidadTarea k INNER JOIN k.id.responsabilidad w WHERE k.id.tarea.idTarea = t.idTarea ) "
			+ ") as nombreResponsabilidad, "			
			+ "( "
			+ "SELECT l.estaFirmadoConFEAWebStart "
			+ "FROM ArchivosInstDeTarea l "
			+ "WHERE l.instanciaDeTarea.idInstanciaDeTarea = i.idInstanciaDeTarea AND l.idArchivoCms = ai.idArchivoCms "
			+ "AND l.idArchivosInstDeTarea = ( SELECT MAX(l2.idArchivosInstDeTarea) FROM ArchivosInstDeTarea l2 WHERE l2.instanciaDeTarea.idInstanciaDeTarea = i.idInstanciaDeTarea AND l2.idArchivoCms = ai.idArchivoCms ) "
			+ ") as estaFirmadoConFEAWebStart, "			
			+ "( "
			+ "SELECT l.estaFirmadoConFEACentralizada "
			+ "FROM ArchivosInstDeTarea l "
			+ "WHERE l.instanciaDeTarea.idInstanciaDeTarea = i.idInstanciaDeTarea AND l.idArchivoCms = ai.idArchivoCms "
			+ "AND l.idArchivosInstDeTarea = ( SELECT MAX(l2.idArchivosInstDeTarea) FROM ArchivosInstDeTarea l2 WHERE l2.instanciaDeTarea.idInstanciaDeTarea = i.idInstanciaDeTarea AND l2.idArchivoCms = ai.idArchivoCms ) "
			+ ") as estaFirmadoConFEACentralizada "
			+ "FROM ArchivosInstDeTarea ai "
			+ "INNER JOIN ai.instanciaDeTarea i "
			+ "INNER JOIN i.tarea t "
            + "INNER JOIN ai.tipoDeDocumento td " 
			+ "INNER JOIN ai.instanciaDeTarea.instanciaDeProceso ip "
			+ "WHERE ip.idExpediente = :idExpediente "			
      + "GROUP BY i.idInstanciaDeTarea, t.nombreTarea, ai.idArchivoCms, ai.mimeType, ai.nombreArchivo, ai.estaFirmadoConFEAWebStart, ai.estaFirmadoConFEACentralizada, "
      + "ai.idUsuario, td.idTipoDeDocumento, td.nombreDeTipoDeDocumento, t.puedeVisarDocumentos, t.puedeAplicarFEA, ip.idExpediente, t.idTarea ORDER BY fechaSubido DESC "),
	
	@NamedQuery(name="ArchivosInstDeTarea.getUltimoArchivoInstDeTareaFirmado", 
	query="SELECT HA FROM ArchivosInstDeTarea HA, HistoricoFirma HF "				
			+ "WHERE HA.instanciaDeTarea.idInstanciaDeTarea = :idInstanciaDeTarea "
			+ "AND HA.tipoDeDocumento.idTipoDeDocumento = :idTipoDeDocumento "
			+ "AND HA.idUsuario = :idUsuario "
			+ "AND HA.idArchivoCms = HF.idArchivoCMS "
			+ "AND HA.instanciaDeTarea.idInstanciaDeTarea = HF.instanciaDeTarea.idInstanciaDeTarea "
			+ "AND HA.tipoDeDocumento.idTipoDeDocumento = HF.tipoDeDocumento.idTipoDeDocumento "
			+ "AND HA.idUsuario = HF.idUsuario "
			+ "ORDER BY HF.fechaFirma DESC "			
			+ ") "),
	
	@NamedQuery(name="ArchivosInstDeTarea.getArchivosInstDeTareaPorIdInstTareaIdUsuarioNombreTipoDocFechaSubidoMayorA", 
	query="SELECT HA FROM ArchivosInstDeTarea HA "
			+ "INNER JOIN HA.tipoDeDocumento D "					
			+ "WHERE HA.instanciaDeTarea.idInstanciaDeTarea = :idInstanciaDeTarea "
			+ "AND HA.idUsuario = :idUsuario "
			+ "AND HA.fechaSubido >= :fechaSubido "
			+ "AND levenshtein(upper(replace(D.nombreDeTipoDeDocumento, ' ', '')), upper(replace(:nombreDeTipoDeDocumento, ' ', ''))) <= (SELECT p.valorParametroNumerico FROM Parametro p where p.idParametro = 63)"),

})
public class ArchivosInstDeTarea {
	
	@Id
	@SequenceGenerator(name="SEQ_ID_ARCHIVOS_INST_DE_TAREA", sequenceName="\"SEQ_ID_ARCHIVOS_INST_DE_TAREA\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_ARCHIVOS_INST_DE_TAREA")
	@Column(name="\"ID_ARCHIVOS_INST_DE_TAREA\"")
	private long idArchivosInstDeTarea;	
	
	@ManyToOne
	@JoinColumn(name="\"ID_INSTANCIA_DE_TAREA\"")
	private InstanciaDeTarea instanciaDeTarea;
	
	@ManyToOne
	@JoinColumn(name="\"ID_TIPO_DE_DOCUMENTO\"")
	private TipoDeDocumento tipoDeDocumento;
	
	@Column(name="\"ID_ARCHIVO_CMS\"")
	private String idArchivoCms;

	@Column(name="\"A_MIME_TYPE\"")
	private String mimeType;

	@Column(name="\"A_NOMBRE_ARCHIVO\"")
	private String nombreArchivo;
	
	@Column(name="\"A_VERSION\"")
	private String version;
	
	@Column(name="\"ID_USUARIO\"")
	private String idUsuario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA_SUBIDO\"")
	private Date fechaSubido;
	
	@Column(name="\"B_ESTA_VISADO\"")
	private Boolean estaVisado;
	
	@Column(name="\"B_ESTA_FIRMADO_CON_FEA_WEB_START\"")
	private Boolean estaFirmadoConFEAWebStart;
	
	@Column(name="\"B_ESTA_FIRMADO_CON_FEA_CENTRALIZADA\"")
	private Boolean estaFirmadoConFEACentralizada;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA_DOCUMENTO\"")
	private Date fechaDocumento;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA_RECEPCION\"")
	private Date fechaRecepcion;
	
	@Column(name="\"B_ANULADO\"")
	private Boolean anulado;

	public ArchivosInstDeTarea() {
		super();
	}

	public long getIdArchivosInstDeTarea() {
		return idArchivosInstDeTarea;
	}

	public void setIdArchivosInstDeTarea(long idArchivosInstDeTarea) {
		this.idArchivosInstDeTarea = idArchivosInstDeTarea;
	}

	public InstanciaDeTarea getInstanciaDeTarea() {
		return instanciaDeTarea;
	}

	public void setInstanciaDeTarea(InstanciaDeTarea instanciaDeTarea) {
		this.instanciaDeTarea = instanciaDeTarea;
	}

	public TipoDeDocumento getTipoDeDocumento() {
		return tipoDeDocumento;
	}

	public void setTipoDeDocumento(TipoDeDocumento tipoDeDocumento) {
		this.tipoDeDocumento = tipoDeDocumento;
	}

	public String getIdArchivoCms() {
		return idArchivoCms;
	}

	public void setIdArchivoCms(String idArchivoCms) {
		this.idArchivoCms = idArchivoCms;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Date getFechaSubido() {
		return fechaSubido;
	}

	public void setFechaSubido(Date fechaSubido) {
		this.fechaSubido = fechaSubido;
	}

	public Boolean getEstaVisado() {
		return estaVisado;
	}

	public void setEstaVisado(Boolean estaVisado) {
		this.estaVisado = estaVisado;
	}

	public Boolean getEstaFirmadoConFEAWebStart() {
		return estaFirmadoConFEAWebStart;
	}

	public void setEstaFirmadoConFEAWebStart(Boolean estaFirmadoConFEAWebStart) {
		this.estaFirmadoConFEAWebStart = estaFirmadoConFEAWebStart;
	}

	public Boolean getEstaFirmadoConFEACentralizada() {
		return estaFirmadoConFEACentralizada;
	}

	public void setEstaFirmadoConFEACentralizada(Boolean estaFirmadoConFEACentralizada) {
		this.estaFirmadoConFEACentralizada = estaFirmadoConFEACentralizada;
	}
	
	public Date getFechaDocumento() {
		return fechaDocumento;
	}

	public void setFechaDocumento(Date fechaDocumento) {
		this.fechaDocumento = fechaDocumento;
	}

	public Date getFechaRecepcion() {
		return fechaRecepcion;
	}

	public void setFechaRecepcion(Date fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}
	
	public Boolean getAnulado() {
		return anulado;
	}

	public void setAnulado(Boolean anulado) {
		this.anulado = anulado;
	}

	@Override
	public String toString() {
		return "ArchivosInstDeTarea [idArchivosInstDeTarea=" + idArchivosInstDeTarea + ", idArchivoCms=" + idArchivoCms
				+ ", mimeType=" + mimeType + ", nombreArchivo=" + nombreArchivo + ", version=" + version
				+ ", idUsuario=" + idUsuario + ", fechaSubido=" + fechaSubido 
				+ ", estaVisado=" + estaVisado 
				+ ", estaFirmadoConFEAWebStart=" + estaFirmadoConFEAWebStart 
				+ ", estaFirmadoConFEACentralizada=" + estaFirmadoConFEACentralizada 
				+ ", fechaDocumento=" + fechaDocumento 
				+ ", fechaRecepcion=" + fechaRecepcion 
				+ ", anulado=" + anulado 
				+ "]";
	}	

}
