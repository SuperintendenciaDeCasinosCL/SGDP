package cl.gob.scj.sgdp.dao;

import java.util.List;

import cl.gob.scj.sgdp.model.VinculacionExp;

public interface VinculacionExpDao extends GenericDao<VinculacionExp> {
	
	List<VinculacionExp> getVinculacionExpSucesoresPorIdInstProcAntecesor(long idInstanciaDeProceso);
	
	List<VinculacionExp> getVinculacionExpPorNombreExp(String nombreExpediente);
	
	VinculacionExp getVinculacionExpPorIdInstProcIdInstProcAntecesor(long idInstanciaDeProceso, long idInstanciaDeProcesoAntecesor);

}