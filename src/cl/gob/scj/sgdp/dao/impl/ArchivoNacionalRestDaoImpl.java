package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.ArchivoNacionalRestDao;
import cl.gob.scj.sgdp.dto.rest.TiposDocumetosArchivoNacionalDTO;
import cl.gob.scj.sgdp.dto.rest.TiposDocumetosArchivoNacionalResponse;

@Repository
public class ArchivoNacionalRestDaoImpl implements ArchivoNacionalRestDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<TiposDocumetosArchivoNacionalDTO> buscarTiposDocumetosArchivoNacionalDao(String nombreExpediente) {
		
		Query query = getSession().createSQLQuery("SELECT sgdp.\"SGDP_TIPOS_DE_DOCUMENTOS\".\"A_NOMBRE_DE_TIPO_DE_DOCUMENTO\", "
				+ "sgdp.\"SGDP_ARCHIVOS_INST_DE_TAREA\".\"ID_ARCHIVO_CMS\", "
				+ "sgdp.\"SGDP_TIPOS_DE_DOCUMENTOS\".\"B_CONFORMA_EXPEDIENTE\", "
				+ "sgdp.\"SGDP_ARCHIVOS_INST_DE_TAREA\".\"A_NOMBRE_ARCHIVO\", "
				+ "sgdp.\"SGDP_ARCHIVOS_INST_DE_TAREA\".\"D_FECHA_SUBIDO\" "
				+ "FROM sgdp.\"SGDP_TIPOS_DE_DOCUMENTOS\" "
				+ "INNER JOIN sgdp.\"SGDP_ARCHIVOS_INST_DE_TAREA\" ON sgdp.\"SGDP_ARCHIVOS_INST_DE_TAREA\".\"ID_TIPO_DE_DOCUMENTO\" = sgdp.\"SGDP_TIPOS_DE_DOCUMENTOS\".\"ID_TIPO_DE_DOCUMENTO\" "
				+ "INNER JOIN sgdp.\"SGDP_INSTANCIAS_DE_TAREAS\" ON sgdp.\"SGDP_ARCHIVOS_INST_DE_TAREA\".\"ID_INSTANCIA_DE_TAREA\" = sgdp.\"SGDP_INSTANCIAS_DE_TAREAS\".\"ID_INSTANCIA_DE_TAREA\" "
				+ "INNER JOIN sgdp.\"SGDP_INSTANCIAS_DE_PROCESOS\" ON sgdp.\"SGDP_INSTANCIAS_DE_TAREAS\".\"ID_INSTANCIA_DE_PROCESO\" = sgdp.\"SGDP_INSTANCIAS_DE_PROCESOS\".\"ID_INSTANCIA_DE_PROCESO\" "
				+ "WHERE "
				+ "sgdp.\"SGDP_INSTANCIAS_DE_PROCESOS\".\"A_NOMBRE_EXPEDIENTE\" =:nombreExpediente AND "
				+ "sgdp.\"SGDP_TIPOS_DE_DOCUMENTOS\".\"B_CONFORMA_EXPEDIENTE\" = true");
		query.setString("nombreExpediente", nombreExpediente);
		return query.list();
	}

}
