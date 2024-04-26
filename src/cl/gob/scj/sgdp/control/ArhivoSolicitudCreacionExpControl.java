package cl.gob.scj.sgdp.control;

import cl.gob.scj.sgdp.dto.ResponseDto;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.service.ArhivoSolicitudCreacionExpService;
import cl.gob.scj.sgdp.util.SGDPUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ArhivoSolicitudCreacionExpControl {

    private static final Logger log = Logger.getLogger(ArhivoSolicitudCreacionExpControl.class);

    private ArhivoSolicitudCreacionExpService arhivoSolicitudCreacionExpService;

    @Autowired
    public ArhivoSolicitudCreacionExpControl(ArhivoSolicitudCreacionExpService arhivoSolicitudCreacionExpService) {
        this.arhivoSolicitudCreacionExpService = arhivoSolicitudCreacionExpService;
    }

    @RequestMapping(value = "/arhivoSolicitudCreacionExp/{idArchivosSolCreaExp}/{confidencialidadFinal}", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public @ResponseBody ResponseEntity<ResponseDto> actualizaConfidencialidad(
            @PathVariable("idArchivosSolCreaExp") long idArchivosSolCreaExp,
            @PathVariable("confidencialidadFinal") boolean confidencialidadFinal,
            HttpServletRequest request) {
        ResponseDto responseDto = new ResponseDto();
        try {
            arhivoSolicitudCreacionExpService.actualizaConfidencialidad(idArchivosSolCreaExp, confidencialidadFinal);
            responseDto = new ResponseDto();
            responseDto.setMessage("OK");
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (SgdpException e) {
            log.info(SGDPUtil.getStackTrace(e));
            responseDto = new ResponseDto();
            responseDto.setMessage(e.getMessage());
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch(Exception e) {
            log.error(SGDPUtil.getStackTrace(e));
            responseDto = new ResponseDto();
            responseDto.setMessage(e.getMessage());
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
