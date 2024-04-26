package cl.gob.scj.sgdp.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;

import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dao.LogDocumentoDao;
import cl.gob.scj.sgdp.dto.ReportFilterDTO;
import cl.gob.scj.sgdp.model.LogDocumento;

@Repository
public class LogDocumentoDaoImpl extends GenericDaoImpl<LogDocumento> implements LogDocumentoDao  {

	private String SQL_LOG_DOCUMENTO = " FROM LogDocumento l "
			+ " where l.fechaLogDocumento BETWEEN :dateFrom and :dateTo "
			+ " and ( "
			+ " 	l.tipoOperacionLogDocumento LIKE :filterString "
			+ " 	or l.moduloLogDocumento LIKE :filterString "
			+ " 	or l.ipLogDocumento LIKE :filterString "
			+ " 	or l.nombreUsuarioLogDocumento LIKE :filterString "
			+ " 	or l.codigoExpedienteLogDocumento LIKE :filterString "
			+ " 	or l.nombreDocumento LIKE :filterString "
			+ " 	or cast(l.idLogDocumento as string) LIKE :filterString "
			+ " ) "
			;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<LogDocumento> getLogDocumentoWithLimit(ReportFilterDTO reportFilterDTO) {
		SimpleDateFormat formatter = new SimpleDateFormat(Constantes.FORMATO_FECHA_FORM_HH_MM_SS);
		try {
			Date dateFrom = formatter.parse(reportFilterDTO.getDateFrom());
			Date dateTo = formatter.parse(reportFilterDTO.getDateTo());
			String orderColumn = "l."+parseColumnDtoToEntity(reportFilterDTO.getOrderColumName());
			String orderDir = reportFilterDTO.getOrderDirection();
			String sql = "SELECT l "+ SQL_LOG_DOCUMENTO + " ORDER BY " + orderColumn + " " + orderDir;
			Query query = (Query) getSession()
					.createQuery(sql)
					.setParameter("filterString", "%" + reportFilterDTO.getTextFilter() + "%")
					.setTimestamp("dateFrom", dateFrom)
					.setTimestamp("dateTo", dateTo)
					.setFirstResult(reportFilterDTO.getStart())
					.setMaxResults(reportFilterDTO.getLength());
			return ((org.hibernate.Query) query).list();
			
		} catch (Exception e) {
			
		}
		return null;
	}

	@Override
	public Long getCountLogDocumento(ReportFilterDTO reportFilterDTO) {
		SimpleDateFormat formatter = new SimpleDateFormat(Constantes.FORMATO_FECHA_FORM_HH_MM_SS);
		Long count =0L;
		try {
			Date dateFrom = formatter.parse(reportFilterDTO.getDateFrom());
			Date dateTo = formatter.parse(reportFilterDTO.getDateTo());
			String sql = "SELECT count(*) "+SQL_LOG_DOCUMENTO;
			Query query = (Query) getSession()
					.createQuery(sql)
					.setParameter("filterString", "%" + reportFilterDTO.getTextFilter() + "%")
					.setTimestamp("dateFrom", dateFrom)
					.setTimestamp("dateTo", dateTo)
					;
			count = (Long) ((org.hibernate.Query) query).uniqueResult(); 
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return count;
	}

	private String parseColumnDtoToEntity(String columDto){
		String columnEntity = "";
		switch (columDto) {
		case "idLogDocumento":
			columnEntity = "idLogDocumento";
			break;
		case "tipoOperacionLogDocumento":
			columnEntity = "tipoOperacionLogDocumento";
			break;
		case "moduloLogDocumento":
			columnEntity = "moduloLogDocumento";
			break;
		case "fechaLogDocumento":
			columnEntity = "fechaLogDocumento"; 
			break;
		case "nombreUsuarioLogDocumento":
			columnEntity = "nombreUsuarioLogDocumento";
			break;
		case "codigoExpedienteLogDocumento":
			columnEntity = "codigoExpedienteLogDocumento";
			break;
		case "nombreDocumento":
			columnEntity = "nombreDocumento";
			break;
		default:
			columnEntity = "idLogDocumento";
			break;
		}
		return columnEntity;
	}


}
