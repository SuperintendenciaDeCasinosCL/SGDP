package cl.gob.scj.sgdp.dao;

import java.util.List;

import cl.gob.scj.sgdp.dto.ReportFilterDTO;
import cl.gob.scj.sgdp.model.LogDocumento;

public interface LogDocumentoDao extends GenericDao<LogDocumento> {

	List<LogDocumento> getLogDocumentoWithLimit(ReportFilterDTO reportFilterDTO);
	
	Long getCountLogDocumento(ReportFilterDTO reportFilterDTO);
	
}
