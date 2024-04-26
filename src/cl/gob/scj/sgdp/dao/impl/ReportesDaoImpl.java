package cl.gob.scj.sgdp.dao.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DoubleType;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.ReportesDao;
import cl.gob.scj.sgdp.dto.reportes.ProcesosDTO;
import cl.gob.scj.sgdp.dto.reportes.TareasFinalizadasDTO;
import cl.gob.scj.sgdp.dto.reportes.TareasPendientesDTO;
import cl.gob.scj.sgdp.dto.reportes.TareasPorAreaDTO;
import cl.gob.scj.sgdp.util.FechaUtil;

@Repository
public class ReportesDaoImpl implements ReportesDao {
	
	private static final Logger log = Logger.getLogger(ReportesDaoImpl.class);
		
	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public List<?> getTareasPorAreaData(String desde, String hasta) throws SQLException, IOException, ParseException {
		String queryString = "SELECT\r\n"
				+ "t1.\"ID_USUARIO\" as usuario,\r\n"
				+ "sgdp.\"SGDP_UNIDADES\".\"A_CODIGO_UNIDAD\" as area,\r\n"
				+ "			(\r\n"
				+ "			SELECT COUNT\r\n"
				+ "				( sgdp.\"SGDP_HISTORICO_DE_INST_DE_TAREAS\".\"ID_INSTANCIA_DE_TAREA_DE_ORIGEN\" ) \r\n"
				+ "			FROM\r\n"
				+ "				sgdp.\"SGDP_HISTORICO_DE_INST_DE_TAREAS\"\r\n"
				+ "				INNER JOIN sgdp.\"SGDP_INSTANCIAS_DE_TAREAS\" ON sgdp.\"SGDP_HISTORICO_DE_INST_DE_TAREAS\".\"ID_INSTANCIA_DE_TAREA_DE_ORIGEN\" = sgdp.\"SGDP_INSTANCIAS_DE_TAREAS\".\"ID_INSTANCIA_DE_TAREA\" \r\n"
				+ "			WHERE\r\n"
				+ "				sgdp.\"SGDP_HISTORICO_DE_INST_DE_TAREAS\".\"ID_ACCION_HISTORICO_INST_DE_TAREA\" NOT IN ( 4, 1, 5, 7 ) \r\n"
				+ "				AND sgdp.\"SGDP_HISTORICO_DE_INST_DE_TAREAS\".\"ID_USUARIO_ORIGEN\" = t1.\"ID_USUARIO\" \r\n"
				+ "				and sgdp.\"SGDP_HISTORICO_DE_INST_DE_TAREAS\".\"D_FECHA_MOVIMIENTO\" between 'ddd' and 'hhh' "
				+ "			) AS outDia,\r\n"
				+ "			(\r\n"
				+ "			SELECT COUNT\r\n"
				+ "				( sgdp.\"SGDP_USUARIOS_ASIGNADOS_A_TAREAS\".\"ID_INSTANCIA_DE_TAREA\" ) \r\n"
				+ "			FROM\r\n"
				+ "				sgdp.\"SGDP_USUARIOS_ASIGNADOS_A_TAREAS\"\r\n"
				+ "				INNER JOIN sgdp.\"SGDP_INSTANCIAS_DE_TAREAS\" ON sgdp.\"SGDP_USUARIOS_ASIGNADOS_A_TAREAS\".\"ID_INSTANCIA_DE_TAREA\" = sgdp.\"SGDP_INSTANCIAS_DE_TAREAS\".\"ID_INSTANCIA_DE_TAREA\"\r\n"
				+ "				INNER JOIN sgdp.\"SGDP_TAREAS\" ON sgdp.\"SGDP_INSTANCIAS_DE_TAREAS\".\"ID_TAREA\" = sgdp.\"SGDP_TAREAS\".\"ID_TAREA\" \r\n"
				+ "			WHERE\r\n"
				+ "				sgdp.\"SGDP_USUARIOS_ASIGNADOS_A_TAREAS\".\"ID_USUARIO\" = t1.\"ID_USUARIO\" \r\n"
				+ "				AND sgdp.\"SGDP_TAREAS\".\"B_ESPERAR_RESP\" = false \r\n"
				+ "				and sgdp.\"SGDP_INSTANCIAS_DE_TAREAS\".\"D_FECHA_INICIO\" between 'ddd' and 'hhh' "
				+ "			) AS enBandeja ,\r\n"
				+ "			(\r\n"
				+ "			SELECT\r\n"
				+ "				sgdp.\"SGDP_HISTORICO_DE_INST_DE_TAREAS\".\"D_FECHA_MOVIMIENTO\"\r\n"
				+ "				--sgdp.\"SGDP_HISTORICO_DE_INST_DE_TAREAS\".\"ID_USUARIO_ORIGEN\" \r\n"
				+ "			FROM\r\n"
				+ "				sgdp.\"SGDP_HISTORICO_DE_INST_DE_TAREAS\" \r\n"
				+ "			WHERE\r\n"
				+ "				sgdp.\"SGDP_HISTORICO_DE_INST_DE_TAREAS\".\"ID_USUARIO_ORIGEN\" = t1.\"ID_USUARIO\"\r\n"
				+ "				and sgdp.\"SGDP_HISTORICO_DE_INST_DE_TAREAS\".\"D_FECHA_MOVIMIENTO\" between 'ddd' and 'hhh'  "
				+ "			ORDER BY\r\n"
				+ "				sgdp.\"SGDP_HISTORICO_DE_INST_DE_TAREAS\".\"D_FECHA_MOVIMIENTO\" ASC \r\n"
				+ "			LIMIT 1\r\n"
				+ "			) as fechaMovimiento,\r\n"
				+ "			(\r\n"
				+ "			SELECT\r\n"
				+ "			--ht1.\"ID_USUARIO_ORIGEN\",\r\n"
				+ "			AVG (\r\n"
				+ "				extract(day from (\r\n"
				+ "					ht1.\"D_FECHA_MOVIMIENTO\" - (\r\n"
				+ "					SELECT\r\n"
				+ "						sgdp.\"SGDP_HISTORICO_DE_INST_DE_TAREAS\".\"D_FECHA_MOVIMIENTO\" \r\n"
				+ "					FROM\r\n"
				+ "						sgdp.\"SGDP_HISTORICO_DE_INST_DE_TAREAS\" \r\n"
				+ "					WHERE\r\n"
				+ "						sgdp.\"SGDP_HISTORICO_DE_INST_DE_TAREAS\".\"ID_INSTANCIA_DE_TAREA_DESTINO\" = ht1.\"ID_INSTANCIA_DE_TAREA_DE_ORIGEN\" \r\n"
				+ "						AND sgdp.\"SGDP_HISTORICO_DE_INST_DE_TAREAS\".\"D_FECHA_MOVIMIENTO\" < ht1.\"D_FECHA_MOVIMIENTO\" \r\n"
				+ "						AND sgdp.\"SGDP_HISTORICO_DE_INST_DE_TAREAS\".\"ID_ACCION_HISTORICO_INST_DE_TAREA\" NOT IN ( 4, 5, 7 ) \r\n"
				+ "						and sgdp.\"SGDP_HISTORICO_DE_INST_DE_TAREAS\".\"D_FECHA_MOVIMIENTO\" between 'ddd' and 'hhh'  "
				+ "					ORDER BY\r\n"
				+ "						sgdp.\"SGDP_HISTORICO_DE_INST_DE_TAREAS\".\"D_FECHA_MOVIMIENTO\" DESC \r\n"
				+ "						LIMIT 1 \r\n"
				+ "					) \r\n"
				+ "				) )\r\n"
				+ "			) AS respuestaPromedio \r\n"
				+ "		FROM\r\n"
				+ "			sgdp.\"SGDP_HISTORICO_DE_INST_DE_TAREAS\" AS ht1\r\n"
				+ "			INNER JOIN sgdp.\"SGDP_INSTANCIAS_DE_TAREAS\" ON ht1.\"ID_INSTANCIA_DE_TAREA_DE_ORIGEN\" = sgdp.\"SGDP_INSTANCIAS_DE_TAREAS\".\"ID_INSTANCIA_DE_TAREA\"\r\n"
				+ "			INNER JOIN sgdp.\"SGDP_TAREAS\" ON sgdp.\"SGDP_INSTANCIAS_DE_TAREAS\".\"ID_TAREA\" = sgdp.\"SGDP_TAREAS\".\"ID_TAREA\"\r\n"
				+ "		WHERE\r\n"
				+ "			ht1.\"ID_USUARIO_ORIGEN\" = t1.\"ID_USUARIO\"\r\n"
				+ "			AND ht1.\"ID_ACCION_HISTORICO_INST_DE_TAREA\" NOT IN ( 1, 4, 5, 7 ) AND\r\n"
				+ "			sgdp.\"SGDP_TAREAS\".\"B_ESPERAR_RESP\" = false\r\n"
				+ "			and ht1.\"D_FECHA_MOVIMIENTO\" between 'ddd' and 'hhh'  "
				+ "		GROUP BY\r\n"
				+ "			ht1.\"ID_USUARIO_ORIGEN\"\r\n"
				+ "			 )\r\n"
				+ "		FROM\r\n"
				+ "			sgdp.\"SGDP_USUARIOS_ROLES\" AS t1\r\n"
				+ "			INNER JOIN sgdp.\"SGDP_UNIDADES\" ON t1.\"ID_UNIDAD\" = sgdp.\"SGDP_UNIDADES\".\"ID_UNIDAD\" \r\n"
				+ "		WHERE\r\n"
				+ "			t1.\"ID_USUARIO\" NOT IN ( 'user1', 'dfsgdp', 'ibesgdp', 'prtgadmin', 'isaynsgdp', 'user2', 'user3' ) \r\n"
				+ "		GROUP BY\r\n"
				+ "			t1.\"ID_USUARIO\",\r\n"
				+ "			sgdp.\"SGDP_UNIDADES\".\"A_CODIGO_UNIDAD\" \r\n"
				+ "		ORDER BY\r\n"
				+ "			sgdp.\"SGDP_UNIDADES\".\"A_CODIGO_UNIDAD\" ASC,\r\n"
				+ "			outDia DESC";
		queryString = queryString.replaceAll("ddd", FechaUtil.toFormat(desde, FechaUtil.simpleDateFormat, FechaUtil.simpleDateFormatCMS));
		queryString = queryString.replaceAll("hhh", FechaUtil.toFormat(hasta, FechaUtil.simpleDateFormat, FechaUtil.simpleDateFormatCMS));
		log.debug(queryString);
		SQLQuery query = getSession().createSQLQuery(queryString);
		query.addScalar("usuario", StandardBasicTypes.STRING);
		query.addScalar("area", StandardBasicTypes.STRING);
		query.addScalar("outDia", StandardBasicTypes.FLOAT);
		query.addScalar("enBandeja", StandardBasicTypes.INTEGER);
		query.addScalar("fechaMovimiento", StandardBasicTypes.STRING);
		query.addScalar("respuestaPromedio", new DoubleType());
		query.setResultTransformer(Transformers.aliasToBean(TareasPorAreaDTO.class));
		@SuppressWarnings("unchecked")
		List<TareasPorAreaDTO> result = query.list();
		for (TareasPorAreaDTO tareasPorAreaDTO : result) {
			tareasPorAreaDTO.calcular();
		}
		return result;
	}
	
	@Override
	public List<?> getTareasFinalizadasData(String desde, String hasta) throws SQLException, IOException, ParseException {
		String queryString = "SELECT DISTINCT\r\n"
				+ "			ur.\"ID_USUARIO\" as usuario,\r\n"
				+ "			un.\"A_CODIGO_UNIDAD\" as area,\r\n"
				+ "			(SELECT \r\n"
				+ "				COUNT ( hit.\"ID_INSTANCIA_DE_TAREA_DE_ORIGEN\" ) \r\n"
				+ "			FROM \r\n"
				+ "				sgdp.\"SGDP_HISTORICO_DE_INST_DE_TAREAS\" hit\r\n"
				+ "				INNER JOIN sgdp.\"SGDP_INSTANCIAS_DE_TAREAS\" it ON hit.\"ID_INSTANCIA_DE_TAREA_DE_ORIGEN\" = it.\"ID_INSTANCIA_DE_TAREA\" \r\n"
				+ "			WHERE \r\n"
				+ "				hit.\"ID_ACCION_HISTORICO_INST_DE_TAREA\" NOT IN ( 4, 1, 5, 7 ) \r\n"
				+ "				and it.\"D_FECHA_ASIGNACION\" between 'ddd' and 'hhh' "
				+ "				AND hit.\"ID_USUARIO_ORIGEN\" = ur.\"ID_USUARIO\" \r\n"
				+ "				AND (EXTRACT ( DAY FROM ( it.\"D_FECHA_ASIGNACION\" - it.\"D_FECHA_INICIO\" )) BETWEEN 0 AND 5)\r\n"
				+ "			) as ceroAcinco,\r\n"
				+ "			(SELECT \r\n"
				+ "				COUNT ( hit.\"ID_INSTANCIA_DE_TAREA_DE_ORIGEN\" ) \r\n"
				+ "			FROM \r\n"
				+ "				sgdp.\"SGDP_HISTORICO_DE_INST_DE_TAREAS\" hit\r\n"
				+ "				INNER JOIN sgdp.\"SGDP_INSTANCIAS_DE_TAREAS\" it ON hit.\"ID_INSTANCIA_DE_TAREA_DE_ORIGEN\" = it.\"ID_INSTANCIA_DE_TAREA\" \r\n"
				+ "			WHERE \r\n"
				+ "				hit.\"ID_ACCION_HISTORICO_INST_DE_TAREA\" NOT IN ( 4, 1, 5, 7 ) \r\n"
				+ "				and it.\"D_FECHA_ASIGNACION\" between 'ddd' and 'hhh' "
				+ "				AND hit.\"ID_USUARIO_ORIGEN\" = ur.\"ID_USUARIO\" \r\n"
				+ "				AND (EXTRACT ( DAY FROM ( it.\"D_FECHA_ASIGNACION\" - it.\"D_FECHA_INICIO\" )) BETWEEN 6 AND 10)\r\n"
				+ "			) as seisADiez,\r\n"
				+ "			(SELECT \r\n"
				+ "				COUNT ( hit.\"ID_INSTANCIA_DE_TAREA_DE_ORIGEN\" ) \r\n"
				+ "			FROM \r\n"
				+ "				sgdp.\"SGDP_HISTORICO_DE_INST_DE_TAREAS\" hit\r\n"
				+ "				INNER JOIN sgdp.\"SGDP_INSTANCIAS_DE_TAREAS\" it ON hit.\"ID_INSTANCIA_DE_TAREA_DE_ORIGEN\" = it.\"ID_INSTANCIA_DE_TAREA\" \r\n"
				+ "			WHERE \r\n"
				+ "				hit.\"ID_ACCION_HISTORICO_INST_DE_TAREA\" NOT IN ( 4, 1, 5, 7 ) \r\n"
				+ "				and it.\"D_FECHA_ASIGNACION\" between 'ddd' and 'hhh' "
				+ "				AND hit.\"ID_USUARIO_ORIGEN\" = ur.\"ID_USUARIO\" \r\n"
				+ "				AND (EXTRACT ( DAY FROM ( it.\"D_FECHA_ASIGNACION\" - it.\"D_FECHA_INICIO\" )) BETWEEN 11 AND 20)\r\n"
				+ "			) as onceAVeinte,\r\n"
				+ "			(SELECT \r\n"
				+ "				COUNT ( hit.\"ID_INSTANCIA_DE_TAREA_DE_ORIGEN\" ) \r\n"
				+ "			FROM \r\n"
				+ "				sgdp.\"SGDP_HISTORICO_DE_INST_DE_TAREAS\" hit\r\n"
				+ "				INNER JOIN sgdp.\"SGDP_INSTANCIAS_DE_TAREAS\" it ON hit.\"ID_INSTANCIA_DE_TAREA_DE_ORIGEN\" = it.\"ID_INSTANCIA_DE_TAREA\" \r\n"
				+ "			WHERE \r\n"
				+ "				hit.\"ID_ACCION_HISTORICO_INST_DE_TAREA\" NOT IN ( 4, 1, 5, 7 ) \r\n"
				+ "				and it.\"D_FECHA_ASIGNACION\" between 'ddd' and 'hhh' "
				+ "				AND hit.\"ID_USUARIO_ORIGEN\" = ur.\"ID_USUARIO\" \r\n"
				+ "				AND (EXTRACT ( DAY FROM ( it.\"D_FECHA_ASIGNACION\" - it.\"D_FECHA_INICIO\" )) BETWEEN 21 AND 40)\r\n"
				+ "			) as veintiunoAcuarenta,\r\n"
				+ "			(SELECT \r\n"
				+ "				COUNT ( hit.\"ID_INSTANCIA_DE_TAREA_DE_ORIGEN\" ) \r\n"
				+ "			FROM \r\n"
				+ "				sgdp.\"SGDP_HISTORICO_DE_INST_DE_TAREAS\" hit\r\n"
				+ "				INNER JOIN sgdp.\"SGDP_INSTANCIAS_DE_TAREAS\" it ON hit.\"ID_INSTANCIA_DE_TAREA_DE_ORIGEN\" = it.\"ID_INSTANCIA_DE_TAREA\" \r\n"
				+ "			WHERE \r\n"
				+ "				hit.\"ID_ACCION_HISTORICO_INST_DE_TAREA\" NOT IN ( 4, 1, 5, 7 ) \r\n"
				+ "				and it.\"D_FECHA_ASIGNACION\" between 'ddd' and 'hhh' "
				+ "				AND hit.\"ID_USUARIO_ORIGEN\" = ur.\"ID_USUARIO\" \r\n"
				+ "				AND (EXTRACT ( DAY FROM ( it.\"D_FECHA_ASIGNACION\" - it.\"D_FECHA_INICIO\" )) BETWEEN 41 AND 80)\r\n"
				+ "			) as cuarentayunoAochenta,\r\n"
				+ "			(SELECT \r\n"
				+ "				COUNT ( hit.\"ID_INSTANCIA_DE_TAREA_DE_ORIGEN\" ) \r\n"
				+ "			FROM \r\n"
				+ "				sgdp.\"SGDP_HISTORICO_DE_INST_DE_TAREAS\" hit\r\n"
				+ "				INNER JOIN sgdp.\"SGDP_INSTANCIAS_DE_TAREAS\" it ON hit.\"ID_INSTANCIA_DE_TAREA_DE_ORIGEN\" = it.\"ID_INSTANCIA_DE_TAREA\" \r\n"
				+ "			WHERE \r\n"
				+ "				hit.\"ID_ACCION_HISTORICO_INST_DE_TAREA\" NOT IN ( 4, 1, 5, 7 ) \r\n"
				+ "				and it.\"D_FECHA_ASIGNACION\" between 'ddd' and 'hhh' "
				+ "				AND hit.\"ID_USUARIO_ORIGEN\" = ur.\"ID_USUARIO\" \r\n"
				+ "				AND (EXTRACT ( DAY FROM ( it.\"D_FECHA_ASIGNACION\" - it.\"D_FECHA_INICIO\" )) BETWEEN 81 AND 999)\r\n"
				+ "			) as ochentayunoYMas\r\n"
				+ "		FROM\r\n"
				+ "			sgdp.\"SGDP_USUARIOS_ROLES\" ur\r\n"
				+ "			INNER JOIN sgdp.\"SGDP_UNIDADES\" un ON ur.\"ID_UNIDAD\" = un.\"ID_UNIDAD\" \r\n"
				+ "		WHERE\r\n"
				+ "			ur.\"B_ACTIVO\" = true\r\n"
				+ "			AND ur.\"ID_USUARIO\" NOT IN ( 'user1', 'dfsgdp', 'user2', 'user3' ) \r\n"
				+ "			--'.$cond.'\r\n"
				+ "		ORDER BY\r\n"
				+ "			un.\"A_CODIGO_UNIDAD\" ASC,\r\n"
				+ "			ur.\"ID_USUARIO\" ASC";
		queryString = queryString.replaceAll("ddd", FechaUtil.toFormat(desde, FechaUtil.simpleDateFormat, FechaUtil.simpleDateFormatCMS));
		queryString = queryString.replaceAll("hhh", FechaUtil.toFormat(hasta, FechaUtil.simpleDateFormat, FechaUtil.simpleDateFormatCMS));
		log.debug(queryString);
		SQLQuery query = getSession().createSQLQuery(queryString);
		query.addScalar("usuario", StandardBasicTypes.STRING);
		query.addScalar("area", StandardBasicTypes.STRING);
		query.addScalar("ceroACinco", StandardBasicTypes.INTEGER);
		query.addScalar("seisADiez", StandardBasicTypes.INTEGER);
		query.addScalar("onceAveinte", StandardBasicTypes.INTEGER);
		query.addScalar("veintiunoAcuarenta", StandardBasicTypes.INTEGER);
		query.addScalar("cuarentayunoAochenta", StandardBasicTypes.INTEGER);
		query.addScalar("ochentayunoYMas", StandardBasicTypes.INTEGER);
		query.setResultTransformer(Transformers.aliasToBean(TareasFinalizadasDTO.class));
		@SuppressWarnings("unchecked")
		List<TareasFinalizadasDTO> tfs = query.list();
		for (TareasFinalizadasDTO tareasFinalizadasDTO : tfs) {
			tareasFinalizadasDTO.calcular();
		}
		return tfs;
	}
	
	public List<?> getTareasPendientesData(String desde, String hasta) throws SQLException, IOException, ParseException {
		String queryString = "SELECT DISTINCT\r\n"
				+ "			ur.\"ID_USUARIO\" as usuario,\r\n"
				+ "			un.\"A_CODIGO_UNIDAD\" as area,\r\n"
				+ "			(SELECT\r\n"
				+ "					count(\r\n"
				+ "					uat.\"ID_INSTANCIA_DE_TAREA\"\r\n"
				+ "					)\r\n"
				+ "				FROM\r\n"
				+ "					sgdp.\"SGDP_USUARIOS_ASIGNADOS_A_TAREAS\" uat\r\n"
				+ "					INNER JOIN sgdp.\"SGDP_INSTANCIAS_DE_TAREAS\" it ON uat.\"ID_INSTANCIA_DE_TAREA\" = it.\"ID_INSTANCIA_DE_TAREA\"\r\n"
				+ "					INNER JOIN sgdp.\"SGDP_ESTADOS_DE_TAREAS\" et ON it.\"ID_ESTADO_DE_TAREA\" = et.\"ID_ESTADO_DE_TAREA\"\r\n"
				+ "					INNER JOIN sgdp.\"SGDP_TAREAS\" t ON it.\"ID_TAREA\" = t.\"ID_TAREA\" \r\n"
				+ "				WHERE\r\n"
				+ "					t.\"B_ESPERAR_RESP\" = false AND\r\n"
				+ "					uat.\"ID_USUARIO\" =  ur.\"ID_USUARIO\"\r\n"
				+ "					and it.\"D_FECHA_ASIGNACION\" between 'ddd' and 'hhh' "
				+ "					AND (EXTRACT (\r\n"
				+ "						DAY \r\n"
				+ "				FROM\r\n"
				+ "					( now() - it.\"D_FECHA_ASIGNACION\" )) BETWEEN 0 AND 5)\r\n"
				+ "			) ceroAcinco,\r\n"
				+ "			(SELECT\r\n"
				+ "					count(\r\n"
				+ "					uat.\"ID_INSTANCIA_DE_TAREA\"\r\n"
				+ "					)\r\n"
				+ "				FROM\r\n"
				+ "					sgdp.\"SGDP_USUARIOS_ASIGNADOS_A_TAREAS\" uat\r\n"
				+ "					INNER JOIN sgdp.\"SGDP_INSTANCIAS_DE_TAREAS\" it ON uat.\"ID_INSTANCIA_DE_TAREA\" = it.\"ID_INSTANCIA_DE_TAREA\"\r\n"
				+ "					INNER JOIN sgdp.\"SGDP_ESTADOS_DE_TAREAS\" et ON it.\"ID_ESTADO_DE_TAREA\" = et.\"ID_ESTADO_DE_TAREA\"\r\n"
				+ "					INNER JOIN sgdp.\"SGDP_TAREAS\" t ON it.\"ID_TAREA\" = t.\"ID_TAREA\" \r\n"
				+ "				WHERE\r\n"
				+ "					t.\"B_ESPERAR_RESP\" = false AND\r\n"
				+ "					uat.\"ID_USUARIO\" =  ur.\"ID_USUARIO\"\r\n"
				+ "					and it.\"D_FECHA_ASIGNACION\" between 'ddd' and 'hhh' "
				+ "					AND (EXTRACT (\r\n"
				+ "						DAY \r\n"
				+ "				FROM\r\n"
				+ "					( now() - it.\"D_FECHA_ASIGNACION\" )) BETWEEN 6 AND 10)\r\n"
				+ "			) as seisADiez,\r\n"
				+ "			(SELECT\r\n"
				+ "					count(\r\n"
				+ "					uat.\"ID_INSTANCIA_DE_TAREA\"\r\n"
				+ "					)\r\n"
				+ "				FROM\r\n"
				+ "					sgdp.\"SGDP_USUARIOS_ASIGNADOS_A_TAREAS\" uat\r\n"
				+ "					INNER JOIN sgdp.\"SGDP_INSTANCIAS_DE_TAREAS\" it ON uat.\"ID_INSTANCIA_DE_TAREA\" = it.\"ID_INSTANCIA_DE_TAREA\"\r\n"
				+ "					INNER JOIN sgdp.\"SGDP_ESTADOS_DE_TAREAS\" et ON it.\"ID_ESTADO_DE_TAREA\" = et.\"ID_ESTADO_DE_TAREA\"\r\n"
				+ "					INNER JOIN sgdp.\"SGDP_TAREAS\" t ON it.\"ID_TAREA\" = t.\"ID_TAREA\" \r\n"
				+ "				WHERE\r\n"
				+ "					t.\"B_ESPERAR_RESP\" = false AND\r\n"
				+ "					uat.\"ID_USUARIO\" =  ur.\"ID_USUARIO\"\r\n"
				+ "					and it.\"D_FECHA_ASIGNACION\" between 'ddd' and 'hhh' "
				+ "					AND (EXTRACT (\r\n"
				+ "						DAY \r\n"
				+ "				FROM\r\n"
				+ "					( now() - it.\"D_FECHA_ASIGNACION\" )) BETWEEN 11 AND 20)\r\n"
				+ "			) as onceAVeinte,\r\n"
				+ "			(SELECT\r\n"
				+ "					count(\r\n"
				+ "					uat.\"ID_INSTANCIA_DE_TAREA\"\r\n"
				+ "					)\r\n"
				+ "				FROM\r\n"
				+ "					sgdp.\"SGDP_USUARIOS_ASIGNADOS_A_TAREAS\" uat\r\n"
				+ "					INNER JOIN sgdp.\"SGDP_INSTANCIAS_DE_TAREAS\" it ON uat.\"ID_INSTANCIA_DE_TAREA\" = it.\"ID_INSTANCIA_DE_TAREA\"\r\n"
				+ "					INNER JOIN sgdp.\"SGDP_ESTADOS_DE_TAREAS\" et ON it.\"ID_ESTADO_DE_TAREA\" = et.\"ID_ESTADO_DE_TAREA\"\r\n"
				+ "					INNER JOIN sgdp.\"SGDP_TAREAS\" t ON it.\"ID_TAREA\" = t.\"ID_TAREA\" \r\n"
				+ "				WHERE\r\n"
				+ "					t.\"B_ESPERAR_RESP\" = false AND\r\n"
				+ "					uat.\"ID_USUARIO\" =  ur.\"ID_USUARIO\"\r\n"
				+ "					and it.\"D_FECHA_ASIGNACION\" between 'ddd' and 'hhh' "
				+ "					AND (EXTRACT (\r\n"
				+ "						DAY \r\n"
				+ "				FROM\r\n"
				+ "					( now() - it.\"D_FECHA_ASIGNACION\" )) BETWEEN 21 AND 41)\r\n"
				+ "			) as veintiunoAcuarenta,\r\n"
				+ "			(SELECT\r\n"
				+ "					count(\r\n"
				+ "					uat.\"ID_INSTANCIA_DE_TAREA\"\r\n"
				+ "					)\r\n"
				+ "				FROM\r\n"
				+ "					sgdp.\"SGDP_USUARIOS_ASIGNADOS_A_TAREAS\" uat\r\n"
				+ "					INNER JOIN sgdp.\"SGDP_INSTANCIAS_DE_TAREAS\" it ON uat.\"ID_INSTANCIA_DE_TAREA\" = it.\"ID_INSTANCIA_DE_TAREA\"\r\n"
				+ "					INNER JOIN sgdp.\"SGDP_ESTADOS_DE_TAREAS\" et ON it.\"ID_ESTADO_DE_TAREA\" = et.\"ID_ESTADO_DE_TAREA\"\r\n"
				+ "					INNER JOIN sgdp.\"SGDP_TAREAS\" t ON it.\"ID_TAREA\" = t.\"ID_TAREA\" \r\n"
				+ "				WHERE\r\n"
				+ "					t.\"B_ESPERAR_RESP\" = false AND\r\n"
				+ "					uat.\"ID_USUARIO\" =  ur.\"ID_USUARIO\"\r\n"
				+ "					and it.\"D_FECHA_ASIGNACION\" between 'ddd' and 'hhh' "
				+ "					AND (EXTRACT (\r\n"
				+ "						DAY \r\n"
				+ "				FROM\r\n"
				+ "					( now() - it.\"D_FECHA_ASIGNACION\" )) BETWEEN 41 AND 80)\r\n"
				+ "			) as cuarentayunoAochenta,\r\n"
				+ "			(SELECT\r\n"
				+ "					count(\r\n"
				+ "					uat.\"ID_INSTANCIA_DE_TAREA\"\r\n"
				+ "					)\r\n"
				+ "				FROM\r\n"
				+ "					sgdp.\"SGDP_USUARIOS_ASIGNADOS_A_TAREAS\" uat\r\n"
				+ "					INNER JOIN sgdp.\"SGDP_INSTANCIAS_DE_TAREAS\" it ON uat.\"ID_INSTANCIA_DE_TAREA\" = it.\"ID_INSTANCIA_DE_TAREA\"\r\n"
				+ "					INNER JOIN sgdp.\"SGDP_ESTADOS_DE_TAREAS\" et ON it.\"ID_ESTADO_DE_TAREA\" = et.\"ID_ESTADO_DE_TAREA\"\r\n"
				+ "					INNER JOIN sgdp.\"SGDP_TAREAS\" t ON it.\"ID_TAREA\" = t.\"ID_TAREA\" \r\n"
				+ "				WHERE\r\n"
				+ "					t.\"B_ESPERAR_RESP\" = false AND\r\n"
				+ "					uat.\"ID_USUARIO\" =  ur.\"ID_USUARIO\"\r\n"
				+ "					and it.\"D_FECHA_ASIGNACION\" between 'ddd' and 'hhh' "
				+ "					AND (EXTRACT (\r\n"
				+ "						DAY \r\n"
				+ "				FROM\r\n"
				+ "					( now() - it.\"D_FECHA_ASIGNACION\" )) BETWEEN 81 and 999)\r\n"
				+ "			) as ochentayunoYMas\r\n"
				+ "		FROM\r\n"
				+ "			sgdp.\"SGDP_USUARIOS_ROLES\" ur\r\n"
				+ "			INNER JOIN sgdp.\"SGDP_UNIDADES\" un ON ur.\"ID_UNIDAD\" = un.\"ID_UNIDAD\" \r\n"
				+ "		WHERE\r\n"
				+ "			ur.\"B_ACTIVO\" = true\r\n"
				+ "			AND ur.\"ID_USUARIO\" NOT IN ( 'user1', 'dfsgdp', 'user2', 'user3' ) \r\n"
				+ "			--'.$cond.'\r\n"
				+ "		ORDER BY\r\n"
				+ "			un.\"A_CODIGO_UNIDAD\" ASC,\r\n"
				+ "			ur.\"ID_USUARIO\" ASC";
		queryString = queryString.replaceAll("ddd", FechaUtil.toFormat(desde, FechaUtil.simpleDateFormat, FechaUtil.simpleDateFormatCMS));
		queryString = queryString.replaceAll("hhh", FechaUtil.toFormat(hasta, FechaUtil.simpleDateFormat, FechaUtil.simpleDateFormatCMS));
		log.debug(queryString);
		SQLQuery query = getSession().createSQLQuery(queryString);
		query.addScalar("usuario", StandardBasicTypes.STRING);
		query.addScalar("area", StandardBasicTypes.STRING);
		query.addScalar("ceroACinco", StandardBasicTypes.INTEGER);
		query.addScalar("seisADiez", StandardBasicTypes.INTEGER);
		query.addScalar("onceAveinte", StandardBasicTypes.INTEGER);
		query.addScalar("veintiunoAcuarenta", StandardBasicTypes.INTEGER);
		query.addScalar("cuarentayunoAochenta", StandardBasicTypes.INTEGER);
		query.addScalar("ochentayunoYMas", StandardBasicTypes.INTEGER);
		query.setResultTransformer(Transformers.aliasToBean(TareasPendientesDTO.class));
		@SuppressWarnings("unchecked")
		List<TareasPendientesDTO> tps = query.list();
		for (TareasPendientesDTO tareasPendientesDTO : tps) {
			tareasPendientesDTO.calcular();
		}
		return tps;
	}
	
	@Override
	public List<?> getProcesosData(String desde, String hasta) throws SQLException, ParseException {
		String queryString = "SELECT DISTINCT\r\n"
				+ "			t1.\"ID_PROCESO\" as idProceso,\r\n"
				+ "			t1.\"A_NOMBRE_PROCESO\" as nombreProceso,\r\n"
				+ "			t1.\"A_CODIGO_PROCESO\" as codigoProceso ,\r\n"
				+ "			( SELECT COUNT ( sgdp.\"SGDP_PROCESOS\".\"ID_PROCESO\" ) FROM sgdp.\"SGDP_PROCESOS\" WHERE sgdp.\"SGDP_PROCESOS\".\"A_CODIGO_PROCESO\" = t1.\"A_CODIGO_PROCESO\" ) AS contv,\r\n"
				+ "			sgdp.\"SGDP_MACRO_PROCESOS\".\"A_NOMBRE_MACRO_PROCESO\" as nombroMacroProceso,\r\n"
				+ "			sgdp.\"SGDP_UNIDADES\".\"A_CODIGO_UNIDAD\" as codigoUnidad,\r\n"
				+ "			sgdp.\"SGDP_UNIDADES\".\"A_NOMBRE_COMPLETO_UNIDAD\" as nombreCompletoUnidad,\r\n"
				+ "			(\r\n"
				+ "				select \r\n"
				+ "					contin || ',' || contout || ',' || tot || ',' || rat \r\n"
				+ "				from (\r\n"
				+ "					select \r\n"
				+ "						sum(contin) as contin,\r\n"
				+ "						sum(contout) as contout,\r\n"
				+ "						count(*) as tot,\r\n"
				+ "						round((sum(contin)::decimal / count(*)::decimal) * 100) as rat\r\n"
				+ "					from (\r\n"
				+ "						select\r\n"
				+ "							case when sgdp.\"SGDP_INSTANCIAS_DE_PROCESOS\".\"D_FECHA_VENCIMIENTO\" >= sgdp.\"SGDP_INSTANCIAS_DE_PROCESOS\".\"D_FECHA_FIN\" then 1 else 0 end contin,\r\n"
				+ "							case when sgdp.\"SGDP_INSTANCIAS_DE_PROCESOS\".\"D_FECHA_VENCIMIENTO\" < sgdp.\"SGDP_INSTANCIAS_DE_PROCESOS\".\"D_FECHA_FIN\" then 1 else 0 end contout\r\n"
				+ "						FROM\r\n"
				+ "							sgdp.\"SGDP_PROCESOS\"\r\n"
				+ "							INNER JOIN sgdp.\"SGDP_INSTANCIAS_DE_PROCESOS\" ON sgdp.\"SGDP_INSTANCIAS_DE_PROCESOS\".\"ID_PROCESO\" = sgdp.\"SGDP_PROCESOS\".\"ID_PROCESO\"\r\n"
				+ "							INNER JOIN sgdp.\"SGDP_ESTADOS_DE_PROCESOS\" ON sgdp.\"SGDP_INSTANCIAS_DE_PROCESOS\".\"ID_ESTADO_DE_PROCESO\" = sgdp.\"SGDP_ESTADOS_DE_PROCESOS\".\"ID_ESTADO_DE_PROCESO\" \r\n"
				+ "						WHERE\r\n"
				+ "							sgdp.\"SGDP_PROCESOS\".\"A_CODIGO_PROCESO\" = t1.\"A_CODIGO_PROCESO\" \r\n"
				+ "							AND sgdp.\"SGDP_INSTANCIAS_DE_PROCESOS\".\"ID_ESTADO_DE_PROCESO\" = 3 \r\n"
				+ "					) as m \r\n"
				+ "				) as n\r\n"
				+ "			) as dentroPlazo,\r\n"
				+ "			(select\r\n"
				+ "				avg(dura)  || ',' || min(dura) || ',' || max(dura)  || ',' || sum(dura)\r\n"
				+ "			from (\r\n"
				+ "				SELECT\r\n"
				+ "					( EXTRACT ( DAY FROM ( sgdp.\"SGDP_INSTANCIAS_DE_PROCESOS\".\"D_FECHA_FIN\" - sgdp.\"SGDP_INSTANCIAS_DE_PROCESOS\".\"D_FECHA_VENCIMIENTO\" )) ) AS en_plazo,\r\n"
				+ "					( EXTRACT ( DAY FROM ( sgdp.\"SGDP_INSTANCIAS_DE_PROCESOS\".\"D_FECHA_FIN\" - sgdp.\"SGDP_INSTANCIAS_DE_PROCESOS\".\"D_FECHA_INICIO\" )) ) AS dura \r\n"
				+ "				FROM\r\n"
				+ "					sgdp.\"SGDP_INSTANCIAS_DE_PROCESOS\"\r\n"
				+ "					INNER JOIN sgdp.\"SGDP_PROCESOS\" ON sgdp.\"SGDP_INSTANCIAS_DE_PROCESOS\".\"ID_PROCESO\" = sgdp.\"SGDP_PROCESOS\".\"ID_PROCESO\" \r\n"
				+ "				WHERE\r\n"
				+ "	  				sgdp.\"SGDP_INSTANCIAS_DE_PROCESOS\".\"ID_ESTADO_DE_PROCESO\" = 3 AND\r\n"
				+ "					sgdp.\"SGDP_PROCESOS\".\"A_CODIGO_PROCESO\" = t1.\"A_CODIGO_PROCESO\" \r\n"
				+ "			) as d) as tiempoPromedio,\r\n"
				+ "			 (\r\n"
				+ "				select\r\n"
				+ "					'env=' || max(env) || ',dev=' || max(dev) || ',fin=' || max(fin) || ',tot=' || sum(env) + sum(dev) + sum(fin)\r\n"
				+ "				from (\r\n"
				+ "					select \r\n"
				+ "						case when hist = 3 then cant else 0 end as env,\r\n"
				+ "						case when hist = 2 then cant else 0 end as dev,\r\n"
				+ "						case when hist = 6 then cant else 0 end as fin\r\n"
				+ "					from (\r\n"
				+ "						SELECT \r\n"
				+ "							COUNT( sgdp.\"SGDP_HISTORICO_DE_INST_DE_TAREAS\".\"ID_HISTORICO_DE_INST_DE_TAREA\" ) as cant,\r\n"
				+ "							sgdp.\"SGDP_HISTORICO_DE_INST_DE_TAREAS\".\"ID_ACCION_HISTORICO_INST_DE_TAREA\" as hist\r\n"
				+ "						FROM\r\n"
				+ "							sgdp.\"SGDP_HISTORICO_DE_INST_DE_TAREAS\"\r\n"
				+ "							INNER JOIN sgdp.\"SGDP_INSTANCIAS_DE_TAREAS\" ON sgdp.\"SGDP_HISTORICO_DE_INST_DE_TAREAS\".\"ID_INSTANCIA_DE_TAREA_DE_ORIGEN\" = sgdp.\"SGDP_INSTANCIAS_DE_TAREAS\".\"ID_INSTANCIA_DE_TAREA\"\r\n"
				+ "							INNER JOIN sgdp.\"SGDP_TAREAS\" ON sgdp.\"SGDP_INSTANCIAS_DE_TAREAS\".\"ID_TAREA\" = sgdp.\"SGDP_TAREAS\".\"ID_TAREA\"\r\n"
				+ "							INNER JOIN sgdp.\"SGDP_PROCESOS\" ON sgdp.\"SGDP_TAREAS\".\"ID_PROCESO\" = sgdp.\"SGDP_PROCESOS\".\"ID_PROCESO\" \r\n"
				+ "						WHERE\r\n"
				+ "							sgdp.\"SGDP_HISTORICO_DE_INST_DE_TAREAS\".\"ID_ACCION_HISTORICO_INST_DE_TAREA\" NOT IN (1,7) AND\r\n"
				+ "							sgdp.\"SGDP_PROCESOS\".\"A_CODIGO_PROCESO\" = t1.\"A_CODIGO_PROCESO\" \r\n"
				+ "						GROUP BY\r\n"
				+ "							sgdp.\"SGDP_HISTORICO_DE_INST_DE_TAREAS\".\"ID_ACCION_HISTORICO_INST_DE_TAREA\"\r\n"
				+ "					) as f\r\n"
				+ "				) as c\r\n"
				+ "			) as tareaEjec,\r\n"
				+ "			(\r\n"
				+ "				SELECT  COUNT\r\n"
				+ "					( DISTINCT sgdp.\"SGDP_INSTANCIAS_DE_TAREAS\".\"ID_INSTANCIA_DE_TAREA\" ) \r\n"
				+ "				FROM\r\n"
				+ "					sgdp.\"SGDP_HISTORICO_DE_INST_DE_TAREAS\"\r\n"
				+ "					INNER JOIN sgdp.\"SGDP_INSTANCIAS_DE_TAREAS\" ON sgdp.\"SGDP_HISTORICO_DE_INST_DE_TAREAS\".\"ID_INSTANCIA_DE_TAREA_DE_ORIGEN\" = sgdp.\"SGDP_INSTANCIAS_DE_TAREAS\".\"ID_INSTANCIA_DE_TAREA\"\r\n"
				+ "					INNER JOIN sgdp.\"SGDP_TAREAS\" ON sgdp.\"SGDP_INSTANCIAS_DE_TAREAS\".\"ID_TAREA\" = sgdp.\"SGDP_TAREAS\".\"ID_TAREA\"\r\n"
				+ "					INNER JOIN sgdp.\"SGDP_PROCESOS\" ON sgdp.\"SGDP_TAREAS\".\"ID_PROCESO\" = sgdp.\"SGDP_PROCESOS\".\"ID_PROCESO\"\r\n"
				+ "					INNER JOIN sgdp.\"SGDP_ACCIONES_HIST_INST_DE_TAREAS\" ON sgdp.\"SGDP_HISTORICO_DE_INST_DE_TAREAS\".\"ID_ACCION_HISTORICO_INST_DE_TAREA\" = sgdp.\"SGDP_ACCIONES_HIST_INST_DE_TAREAS\".\"ID_ACCION_HISTORICO_INST_DE_TAREA\" \r\n"
				+ "				WHERE\r\n"
				+ "					sgdp.\"SGDP_HISTORICO_DE_INST_DE_TAREAS\".\"ID_ACCION_HISTORICO_INST_DE_TAREA\" NOT IN ( 1, 7 ) \r\n"
				+ "					AND sgdp.\"SGDP_PROCESOS\".\"A_CODIGO_PROCESO\" =  t1.\"A_CODIGO_PROCESO\"\r\n"
				+ "			) as tareasTotales\r\n"
				+ "		FROM\r\n"
				+ "			sgdp.\"SGDP_PROCESOS\" AS t1\r\n"
				+ "			INNER JOIN sgdp.\"SGDP_MACRO_PROCESOS\" ON t1.\"ID_MACRO_PROCESO\" = sgdp.\"SGDP_MACRO_PROCESOS\".\"ID_MACRO_PROCESO\"\r\n"
				+ "			INNER JOIN sgdp.\"SGDP_UNIDADES\" ON t1.\"ID_UNIDAD\" = sgdp.\"SGDP_UNIDADES\".\"ID_UNIDAD\" \r\n"
				+ "		ORDER BY\r\n"
				+ "			t1.\"A_CODIGO_PROCESO\" ASC,\r\n"
				+ "			t1.\"ID_PROCESO\" DESC,\r\n"
				+ "			t1.\"A_NOMBRE_PROCESO\" ASC,\r\n"
				+ "			sgdp.\"SGDP_MACRO_PROCESOS\".\"A_NOMBRE_MACRO_PROCESO\" asc";
		//queryString = queryString.replaceAll("ddd", FechaUtil.toFormat(desde, FechaUtil.simpleDateFormat, FechaUtil.simpleDateFormatCMS));
		//queryString = queryString.replaceAll("hhh", FechaUtil.toFormat(hasta, FechaUtil.simpleDateFormat, FechaUtil.simpleDateFormatCMS));
		log.debug(queryString);
		SQLQuery query = getSession().createSQLQuery(queryString);
		query.addScalar("idProceso", StandardBasicTypes.LONG);
		query.addScalar("nombreProceso", StandardBasicTypes.STRING);
		query.addScalar("codigoProceso", StandardBasicTypes.STRING);
		query.addScalar("contv", StandardBasicTypes.INTEGER);
		query.addScalar("nombroMacroProceso", StandardBasicTypes.STRING);
		query.addScalar("codigoUnidad", StandardBasicTypes.STRING);
		query.addScalar("nombreCompletoUnidad", StandardBasicTypes.STRING);
		query.addScalar("dentroPlazo", StandardBasicTypes.STRING);
		query.addScalar("tiempoPromedio", StandardBasicTypes.STRING);
		query.addScalar("tareaEjec", StandardBasicTypes.STRING);
		query.addScalar("tareasTotales", StandardBasicTypes.INTEGER);
		query.setResultTransformer(Transformers.aliasToBean(ProcesosDTO.class));
		return query.list();
	}
		
}
