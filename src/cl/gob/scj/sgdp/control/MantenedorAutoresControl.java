/**
 * 
 */
package cl.gob.scj.sgdp.control;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cl.gob.scj.sgdp.dto.AutorDTO;
import cl.gob.scj.sgdp.service.AutoresService;

/**
 * Author: Carlos Tapia
 * Description: CRUD de Autores del SGDP
 * Date: 26/09/2023
 */

@Controller
public class MantenedorAutoresControl {
	
	private static final Logger log = Logger.getLogger(MantenedorParametrosControl.class);
	
	@Autowired
	private AutoresService autoresService;
	
	@RequestMapping(value = "/mantenedorAutores", method = RequestMethod.GET)
	public String mantenedorAutores(
			Model model
			) {
		log.info("Inicio Mantenedor de Autores");
		List<AutorDTO> listaAutoresDTO = autoresService.getAutores();
		model.addAttribute("listaAutoresDTO", listaAutoresDTO);
		return "mantenedorAutores";
	}
	
	@RequestMapping(value = "/cargarAutores", method = RequestMethod.GET)
	public String cargaAutor(
			Model model
			) {
		log.info("Carga de autores");
		List<AutorDTO> listaAutoresDTO = autoresService.getAutores();
		model.addAttribute("listaAutoresDTO", listaAutoresDTO);
		return "div/mantenedorAutoresCuerpo";
	}
	
	@RequestMapping(value = "/crearAutor", method = RequestMethod.POST)
	public @ResponseBody AutorDTO crearAutor(
			@RequestBody AutorDTO autorDTO
			) {
		log.info("Inicio crearAutor");
		log.info(autorDTO.toString());
		try {
			autoresService.crearAutor(autorDTO);
			autorDTO.setRespuesta("OK");
			return autorDTO;
		} catch (Exception e) {
			log.info("Hubo un error al insertar el registro");
			log.info(e.getMessage());
			autorDTO.setRespuesta("ERROR");
			return autorDTO;
		}
	}
	
	@RequestMapping(value = "/editarAutor/{idAutor}", method = RequestMethod.GET)
	public String editarAutor(
			Model model,
			@PathVariable("idAutor") Long idAutor 
			) {
		log.info("Inicio editaAutor");
		log.info("ID Autor: " + idAutor);
		AutorDTO autorDTO = new AutorDTO();
		autorDTO = autoresService.buscarAutor(idAutor);
		model.addAttribute("autorDTO", autorDTO);
		return "div/editarAutorCuerpo";
	}
	
	@RequestMapping(value = "/actualizarAutor", method = RequestMethod.POST)
	public @ResponseBody AutorDTO actualizarAutor(
			@RequestBody AutorDTO autorDTO
			) {
		log.info("Inicio actualizarAutor");
		log.info(autorDTO.toString());
		try {
			autoresService.actualizarAutor(autorDTO);
			autorDTO.setRespuesta("OK");
			return autorDTO;
		} catch (Exception e) {
			log.info("Hubo un error al actualizar el registro");
			log.info(e.getMessage());
			autorDTO.setRespuesta("ERROR");
			return autorDTO;
		}
	}
	
	@RequestMapping(value = "/borrarAutor/{idAutor}", method = RequestMethod.GET)
	public @ResponseBody String borraAutor(
			@PathVariable("idAutor") Long idAutor
			) {
		log.info("Inicio actualizarAutor");
		log.info(idAutor);
		try {
			autoresService.borraAutor(idAutor);
			return "OK";
		} catch (Exception e) {
			log.info("Hubo un error al actualizar el registro");
			log.info(e.getMessage());
			return "ERROR";
		}
	}

}
