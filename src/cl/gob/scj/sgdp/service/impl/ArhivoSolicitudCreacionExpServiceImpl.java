package cl.gob.scj.sgdp.service.impl;

import cl.gob.scj.sgdp.dao.ArchivosSolCreaExpDao;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.model.ArchivosSolCreaExp;
import cl.gob.scj.sgdp.service.ArhivoSolicitudCreacionExpService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Properties;

@Service
@Transactional(rollbackFor = Throwable.class)
public class ArhivoSolicitudCreacionExpServiceImpl implements ArhivoSolicitudCreacionExpService {

    private static final Logger log = Logger.getLogger(ArhivoSolicitudCreacionExpServiceImpl.class);

    private ArchivosSolCreaExpDao archivosSolCreaExpDao;
    @Autowired
    public ArhivoSolicitudCreacionExpServiceImpl(ArchivosSolCreaExpDao archivosSolCreaExpDao) {
        this.archivosSolCreaExpDao = archivosSolCreaExpDao;
    }

    @Resource(name = "configProps")
    private Properties configProps;

    @Override
    public void actualizaConfidencialidad(long idArchivosSolCreaExp, boolean confidencialidad) throws SgdpException {
        ArchivosSolCreaExp archivoSolCreaExp = archivosSolCreaExpDao.find(idArchivosSolCreaExp);
        log.info(archivoSolCreaExp.toString());
        if (archivoSolCreaExp.getUsuariosConfidenciales()==null || archivoSolCreaExp.getUsuariosConfidenciales().equals("null") || archivoSolCreaExp.getUsuariosConfidenciales().isEmpty()) {
            throw new SgdpException(configProps.getProperty("sgdp.respuesta.validacion.actualiza-confidencialidad.sin-usuarios-reservados"));
        }
        archivoSolCreaExp.setConfidencialidadFinal(confidencialidad);
    }

}
