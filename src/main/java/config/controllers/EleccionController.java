package config.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import domain.candidato.Candidato;
import domain.candidato.CandidatoDao;
import domain.conteo_electronico.ConteoElectronico;
import domain.conteo_electronico.ConteoElectronicoDao;
import domain.conteo_fisico.ConteoFisico;
import domain.conteo_fisico.ConteoFisicoDao;
import domain.eleccion.Eleccion;
import domain.eleccion.EleccionDao;
import domain.jurado.Jurado;
import domain.jurado.JuradoDao;
import domain.medio_voto.MedioVoto;
import domain.medio_voto.MedioVotoDao;
import domain.user.User;
import domain.user.UserDao;
import domain.voto.Voto;
import domain.voto.VotoDao;

@Controller
public class EleccionController {	
/****************************************************************************************************************************/
	/*MAPEO DEL INICIO*/
	@RequestMapping("user/index")
	public ModelAndView IndexUser() {
		CerrarElecciones();
		return listarElecciones("/user/index");
	}
	
	@RequestMapping("jurado/index")
	public ModelAndView AdminUser() {
		CerrarElecciones();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User customUser = (User) authentication.getPrincipal();
		if(customUser.getCedula() == null){
			return new ModelAndView("redirect:/404");
		}else{
			ModelAndView mav = new ModelAndView("/jurado/index");
			List<Jurado> eleccionesJurado = juradoDao.findByCedula(customUser.getCedula());
			mav.addObject("eleccionesJurado", eleccionesJurado);
			return mav;
		}
	}
	
	@RequestMapping("admin/index")
	public ModelAndView IndexAdmin() {
		CerrarElecciones();
		ModelAndView mav = new ModelAndView("/admin/index");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User customUser = (User) authentication.getPrincipal();
		List<Eleccion> eleccionesAdmin = eleccionDao.findByCedulaCreador(customUser.getCedula());
		mav.addObject("eleccionesAdmin", eleccionesAdmin);
		return mav;
	}
	
	@RequestMapping("jurado/elecciones")
	public ModelAndView EleccionesJurado() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User customUser = (User) authentication.getPrincipal();
		if(customUser.getCedula() == null){
			return new ModelAndView("redirect:/404");
		}else{
			ModelAndView mav = new ModelAndView("/jurado/elecciones");
			mav = listarElecciones("/jurado/elecciones");
			return mav;
		}
	}
	
	@RequestMapping("admin/elecciones")
	public ModelAndView EleccionesAdmin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User customUser = (User) authentication.getPrincipal();
		if(customUser.getCedula() == null){
			return new ModelAndView("redirect:/404");
		}else{
			ModelAndView mav = new ModelAndView("/admin/elecciones");
			mav = listarElecciones("/admin/elecciones");
			return mav;
		}
	}
	
	private ModelAndView listarElecciones(String url){
		/*Tomamos el Usuario actual*/
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User customUser = (User) authentication.getPrincipal();
		if(customUser.getCedula() == null){return new ModelAndView("redirect:/404");}
		/*Tomamos las elecciones en las que tiene el usuario permitido votar*/
		List<User> userApto = userDao.findByCedula(customUser.getCedula());
		List<Eleccion> listaElecciones = (List<Eleccion>) eleccionDao.findAll();
		List<Eleccion> listaEleccionesAptas = new ArrayList<>();
		for(int j=0; j<userApto.size(); j++){
			for(int i=0; i<listaElecciones.size(); i++){
				if(userApto.get(j) != null){
					if(listaElecciones.get(i).getId() == userApto.get(j).getId_elecciones().getId()){
						if(listaElecciones.get(i).getCierre()){
							if(FueraFechas(listaElecciones.get(i).getId())){
								if(!HaVotado(userApto.get(j).getCedula(), listaElecciones.get(i).getId())){
									listaEleccionesAptas.add(listaElecciones.get(i));
								}
							}
						}
					}
				}
			}
		}
		/*Enviamos las elecciones al usuario*/
		ModelAndView mav = new ModelAndView(url);
		mav.addObject("listaElecciones", listaEleccionesAptas);
		return mav;
	}
/****************************************************************************************************************************/
	public boolean FueraFechas(long idElecciones){
		Date hoy = new Date();
		Eleccion eleccion = eleccionDao.findOne(idElecciones);
		if(eleccion.getFechaInicio().compareTo(hoy) < 0){
			if(hoy.compareTo(eleccion.getFechaFin()) < 0){
				return true;
			}
		}
		return false;
	}
/****************************************************************************************************************************/
	/*MAPEO DE ELECCIÓN*/
	@RequestMapping("user/eleccion")
	public ModelAndView EleccionesUser(int id){
		/*Tomamos el Usuario actual*/
		return listarEleccion(id, "/user");
	}
	
	@RequestMapping("jurado/eleccion")
	public ModelAndView EleccionesJurado(int id){
		return listarEleccion(id, "/jurado");
	}
	
	@RequestMapping("admin/eleccion")
	public ModelAndView EleccionesAdim(int id){
		return listarEleccion(id, "/admin");
	}
	
	private ModelAndView listarEleccion(long id, String url){
		CerrarElecciones();
		/*Tomamos el Usuario actual*/
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User customUser = (User) authentication.getPrincipal();
		/*Validamos que sea apto para votar*/
		List<User> autenticado = userDao.findByCedula(customUser.getCedula());
		/*Tomamos la eleccion de la que se está usando*/
		Eleccion eleccionActual = eleccionDao.findOne(id);
		if(eleccionActual == null){return new ModelAndView("redirect:"+url+"index?NoApto");}
		if(autenticado != null){
			for(int i=0; i<autenticado.size(); i++){
				if(autenticado.get(i).getId_elecciones().getId() == eleccionActual.getId()){
					if(eleccionActual.getCierre()){
						if(!HaVotado(autenticado.get(i).getCedula(), eleccionActual.getId())){
							ModelAndView mav = new ModelAndView(url+"/eleccion");
							mav.addObject("eleccion", eleccionActual);
							mav.addObject("candidatos", candidatoDao.findByIdElecciones(eleccionActual));
							return mav;
						}
					}
				}				
			}
		}
		return new ModelAndView("redirect:"+url+"index?NoApto");
	}
/****************************************************************************************************************************/
	@RequestMapping(value = "/jurado/buscarUsuario", method = RequestMethod.GET)
	public @ResponseBody String BuscarUserAjax(long idEleccion, String cedula) {
		List<User> usuarios = userDao.findByCedula(cedula);
		Eleccion eleccion = null;User user = null;
		boolean continuar = false;
		for(int i=0; i<usuarios.size(); i++){
			if(usuarios.get(i).getId_elecciones().getId() == idEleccion){
				if(FueraFechas(usuarios.get(i).getId_elecciones().getId())){
					eleccion = usuarios.get(i).getId_elecciones();
					user = usuarios.get(i);
					continuar = true;
				}
			}
		}
		if(continuar){
			if(usuarios.size() > 0){
				if(HaVotado(cedula, idEleccion)){
					return "no";
				}else{
					try{
						MedioVoto medio = medioVotoDao.findByNombre("Fisico");
						Voto voto = new Voto(null, new Date(), "", eleccion, null, user, medio);
						votoDao.save(voto);
						return "si";
					}catch(Exception ex){
						return "no";
					}
				}
			}else{
				return "no";
			}
		}else{
			return "no";
		}
	}
/****************************************************************************************************************************/
	@RequestMapping(value = "/nuevoUsuario", method = RequestMethod.GET)
	public @ResponseBody boolean nuevoUsuario(String cedula, String email, String concepto, String nombres, Long idEleccion){
		/*Tomamos el Usuario actual*/
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User customUser = (User) authentication.getPrincipal();
		Eleccion eleccion = eleccionDao.findOne(idEleccion);
		Date fechaActual = new Date();
		if(eleccion.getCierre()){
			try{
				User user = new User(null, cedula, email, eleccion, concepto, fechaActual, customUser.getCedula(), nombres);
				userDao.save(user);
				return true;
			}catch(Exception ex){
				return false;
			}
		}else{
			return false;
		}
	}
/****************************************************************************************************************************/
	@RequestMapping(value = "/cerrarEleccion", method = RequestMethod.GET)
	public @ResponseBody boolean cerrarEleccion(Long idEleccion){
		/*Tomamos el Usuario actual*/
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User customUser = (User) authentication.getPrincipal();
		Eleccion eleccion = eleccionDao.findOne(idEleccion);
		if(eleccion != null){
			if(eleccion.getCierre()){
				eleccion.setCierre(false);
				eleccion.setFechaCierre(new Date());
				eleccion.setCedulaCierre(customUser.getCedula());
				eleccionDao.save(eleccion);
				return true;
			}
		}
		return false;
	}
/****************************************************************************************************************************/
	public boolean HaVotado(String cedula, long idEleccion){
		Eleccion eleccion = eleccionDao.findOne(idEleccion);
		List<Voto> votos = votoDao.findByIdElecciones(eleccion);
		for(int i=0; i<votos.size(); i++){
			if(votos.get(i).getId_usuarios_aptos().getCedula().equals(cedula)){
				return true;
			}
		}
		return false;
	}
/****************************************************************************************************************************/
	/*Administrar Elección*/
	@RequestMapping(value="jurado/administrarElecciones")
	public ModelAndView administrarEleccionJurado(long id){
		/*Tomamos el Usuario actual*/
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User customUser = (User) authentication.getPrincipal();
		Eleccion eleccion = eleccionDao.findOne(id);
		List<Jurado> jurado = juradoDao.findByIdElecciones(eleccion);
		if(jurado != null){
			if(eleccion.getCierre()){
				for(int i=0; i<jurado.size();i++){
					if(jurado.get(i).getCedula().equals(customUser.getCedula())){
						ModelAndView mav = new ModelAndView("/jurado/administrarEleccion");
						boolean fechasFuera = FueraFechas(id);
						mav.addObject("noEditar", fechasFuera);
						mav.addObject("eleccionActual", eleccion);
						return mav;
					}
				}
			}else{
				return new ModelAndView("redirect:/jurado/index");
			}
		}else{
			return new ModelAndView("redirect:/404");
		}
		return new ModelAndView("redirect:/404");
	}
/****************************************************************************************************************************/
	@RequestMapping(value="jurado/subirVotos")
	public ModelAndView listarEleccionesSubirVotos(){
		/*Tomamos el Usuario actual*/
		ModelAndView mav = new ModelAndView("jurado/subirVotos");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User customUser = (User) authentication.getPrincipal();
		List<Jurado> eleccionesJurado = juradoDao.findByCedula(customUser.getCedula());
		mav.addObject("elecciones", eleccionesJurado);
		return mav;
	}
/****************************************************************************************************************************/	
	@RequestMapping(value = "/cargarCandidatos", method = RequestMethod.GET)
	public @ResponseBody List<Candidato> CargarCandidatos(long idEleccion) {
		List<Candidato> candidatos = new ArrayList<>();
		Eleccion eleccion = eleccionDao.findOne(idEleccion);
		List<Candidato> candidatosEleccion = candidatoDao.findByIdElecciones(eleccion);
		ConteoFisico conteoAux;
		for(int i=0; i<candidatosEleccion.size(); i++){
			conteoAux = conteoFisicoDao.findByIdCandidatos(candidatosEleccion.get(i));
			if(conteoAux == null){
				candidatos.add(candidatosEleccion.get(i));
			}
		}
		if(candidatos.size() == 0){eleccion.setVotosFisicos(true);eleccionDao.save(eleccion);}
		return candidatos;
	}
/****************************************************************************************************************************/	
	@RequestMapping(value = "jurado/subirVotosFisicos", method = RequestMethod.GET)
	public @ResponseBody boolean subirVotosFisicos(long idEleccion, long idCandidato, int cantidad){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User customUser = (User) authentication.getPrincipal();
		Eleccion eleccion = eleccionDao.findOne(idEleccion);
		Candidato candidato = candidatoDao.findOne(idCandidato);
		try{
			ConteoFisico conteo = new ConteoFisico(null, cantidad, eleccion, candidato, new Date(), customUser.getCedula());
			conteoFisicoDao.save(conteo);
			return true;
		}catch(Exception ex){
			return false;
		}
	}
/****************************************************************************************************************************/
	public void CerrarElecciones(){
		List<Eleccion> eleccionesAbiertas = eleccionDao.findByCierre(true);
		Date hoy = new Date();
		for(int i=0; i<eleccionesAbiertas.size(); i++){
			if(eleccionesAbiertas.get(i).getCierre()){
				if(eleccionesAbiertas.get(i).getFechaFin().compareTo(hoy) < 0){
					eleccionesAbiertas.get(i).setCierre(false);
					eleccionDao.save(eleccionesAbiertas.get(i));
				}
			}
		}
	}
/****************************************************************************************************************************/
	@RequestMapping(value="admin/nuevaEleccion")
	public ModelAndView crearEleccion(String nombre, String descripcion, Date fechaInicio, Date fechaFin){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User customUser = (User) authentication.getPrincipal();
		Eleccion eleccion = new Eleccion(null, nombre, descripcion, true, fechaInicio, fechaFin, customUser.getCedula(), null, "");
		eleccionDao.save(eleccion);
		return new ModelAndView("redirect:/admin/index?Success");
	}
/****************************************************************************************************************************/
	/*Editar Elección*/
	@RequestMapping(value="/admin/editEleccion")
	public ModelAndView editarEleccionAdmin(long idEleccion, String nombre, Date fechaInicio, Date fechaFin, String descripcion){
		Eleccion eleccion = eleccionDao.findOne(idEleccion);
		if(!FueraFechas(idEleccion)){
			try{
				eleccion.setNombre(nombre);
				eleccion.setFechaInicio(fechaInicio);
				eleccion.setFechaFin(fechaFin);
				eleccion.setDescripcion(descripcion);
				eleccionDao.save(eleccion);
				return new ModelAndView("redirect:/admin/administrarElecciones?id="+idEleccion+"&SuccessUpdate");
			}catch(Exception ex){
				return new ModelAndView("redirect:/admin/administrarElecciones?id="+idEleccion+"&ErrorUpdate");
			}
		}else{
			return new ModelAndView("redirect:/admin/administrarElecciones?id="+idEleccion+"&FueraTiempo");
		}
	}
/****************************************************************************************************************************/
	@RequestMapping(value="admin/administrarElecciones")
	public ModelAndView administrarEleccionAdmin(long id){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User customUser = (User) authentication.getPrincipal();
		try{
			Eleccion eleccion = eleccionDao.findOne(id);
			if(eleccion == null){return new ModelAndView("redirect:/404");}
			if(customUser.getCedula().equals(eleccion.getCedulaCreador())){
				if(eleccion.getCierre()){
					List<Candidato> candidatos = candidatoDao.findByIdElecciones(eleccion);
					List<Jurado> jurados = juradoDao.findByIdElecciones(eleccion);
					ModelAndView mav = new ModelAndView("admin/administrarEleccion");
					boolean fechasFuera = FueraFechas(id);
					mav.addObject("noEditar", fechasFuera);
					mav.addObject("candidatos", candidatos);
					mav.addObject("jurados", jurados);
					mav.addObject("eleccion", eleccion);
					return mav;
				}else{
					return new ModelAndView("redirect:/404");					
				}
			}else{
				return new ModelAndView("redirect:/404");
			}
		}catch(Exception ex){
			return new ModelAndView("redirect:/404");
		}
	}
/****************************************************************************************************************************/
	@RequestMapping(value = "admin/cargarDatosEditarCandidato", method = RequestMethod.GET)
	public @ResponseBody String[] cargarDatosEditarCandidato(long id){
		Candidato candidato = candidatoDao.findOne(id);
		String[] datos = {candidato.getImagen(), candidato.getNombre(), ""+candidato.getNumeroTarjeton(), ""+candidato.getId()};
		return datos;
	}
/****************************************************************************************************************************/
	@RequestMapping(value = "admin/cargarDatosEditarJurado", method = RequestMethod.GET)
	public @ResponseBody String cargarDatosEditarJurado(long id){
		Jurado jurado = juradoDao.findOne(id);
		return jurado.getCedula();
	}
/****************************************************************************************************************************/
	@RequestMapping(value = "/admin/editarCandidato", method = RequestMethod.POST)
	@ResponseBody public boolean editarCandidatoForm(@RequestParam("imagen") MultipartFile imagen, 
										 @RequestParam("nombre") String nombre, 
										 @RequestParam("tarjeton") String tarjeton, 
										 @RequestParam("idCandidato") Long idCandidato,
										 @RequestParam("id") Long id) throws IOException {
		Candidato candidato = candidatoDao.findOne(idCandidato);
		if(candidato != null){
			if(!imagen.getOriginalFilename().equals("")){
				// Get the filename and build the local file path
		  	      String filename = ""+idCandidato+".jpg";
		  	      String directory = "src/main/webapp/resources/images";
		  	      String bdName = "http://35.160.223.41:8080/resources/images/"+filename;
		  	      String filepath = Paths.get(directory, filename).toString();
		  	      // Save the file locally
		  	      BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filepath)));
		  	      InputStream is = imagen.getInputStream();
		  	      byte [] bytes = new byte[1024];
		  		  int sizeRead;
		  		  while ((sizeRead = is.read(bytes,0, 1024)) > 0) {
		  			  stream.write(bytes, 0, sizeRead);
		  		  }
		  	      stream.close();
		  	      candidato.setImagen(bdName);
			}
			candidato.setNombre(nombre);
			candidato.setNumeroTarjeton(Integer.parseInt(tarjeton));
			candidatoDao.save(candidato);
			return true;
		}
		return false;
	}
/****************************************************************************************************************************/
	@RequestMapping(value = "/admin/elimCandidato")
	public ModelAndView eliminarCandidato(long id, long idEleccion){
		Candidato candidato = candidatoDao.findOne(id);
		if(candidato == null){
			return new ModelAndView("redirect:/404");
		}else{
			File file = new File(candidato.getImagen());
			file.delete();
			candidatoDao.delete(candidato);
			return new ModelAndView("redirect:/admin/administrarElecciones?id="+idEleccion);
		}
	}
/****************************************************************************************************************************/
	@RequestMapping(value = "/admin/eliminarJurado")
	public ModelAndView eliminarJurado(long id, long idEleccion){
		Jurado jurado = juradoDao.findOne(id);
		if(jurado == null){
			return new ModelAndView("redirect:/404");
		}else{
			juradoDao.delete(jurado);
			return new ModelAndView("redirect:/admin/administrarElecciones?id="+idEleccion);
		}
	}
/****************************************************************************************************************************/
	@RequestMapping(value = "/admin/nuevoJurado")
	public @ResponseBody boolean nuevoJurado(String cedula, long idEleccion){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User customUser = (User) authentication.getPrincipal();
		if(customUser.getCedula() != null){
			Eleccion eleccion = eleccionDao.findOne(idEleccion);
			List<Jurado> juradoAux = juradoDao.findByCedula(cedula);
			for(int i=0; i<juradoAux.size(); i++){
				if(juradoAux.get(i).getIdElecciones().getId() == eleccion.getId()){
					return false;
				}
			}
			if(eleccion != null){
				Jurado jurado = new Jurado(null, cedula, customUser.getCedula(), eleccion);
				juradoDao.save(jurado);
			}
		}
		return true;
	}
/****************************************************************************************************************************/
	@RequestMapping(value = "/admin/editarJurado")
	public @ResponseBody boolean editarJurado(String cedula, String old){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User customUser = (User) authentication.getPrincipal();
		if(customUser.getCedula() != null){
			List<Jurado> jurado = juradoDao.findByCedula(old);
			for(int i=0; i<jurado.size(); i++){
				jurado.get(i).setCedula(cedula);
				juradoDao.save(jurado.get(i));
			}
		}
		return true;
	}
/****************************************************************************************************************************/
	@RequestMapping(value="/admin/reportes")
	public ModelAndView Reportes(){
		return ReportesMethod("/admin/reportes");
	}
	
/****************************************************************************************************************************/
	@RequestMapping(value="/admin/cargarDatosReporte", method = RequestMethod.GET)
	public @ResponseBody String[] cargarDatosReporte(long id){
		return CargarDatosReporteMethod(id);
	}
/****************************************************************************************************************************/
	@RequestMapping(value="/admin/cargarCandidatosReporte", method = RequestMethod.GET)
	public @ResponseBody List<String> cargarCandidatosReporte(long id){
		return CargarCandidatosReporteMethod(id);
	}
/****************************************************************************************************************************/
	@RequestMapping(value="/jurado/reportes")
	public ModelAndView ReportesJurado(){
		return ReportesMethod("/jurado/reportes");
	}
	
/****************************************************************************************************************************/
	@RequestMapping(value="/jurado/cargarDatosReporte", method = RequestMethod.GET)
	public @ResponseBody String[] cargarDatosReporteJurado(long id){
		return CargarDatosReporteMethod(id);
	}
/****************************************************************************************************************************/
	@RequestMapping(value="/jurado/cargarCandidatosReporte", method = RequestMethod.GET)
	public @ResponseBody List<String> cargarCandidatosReporteJurado(long id){
		return CargarCandidatosReporteMethod(id);
	}
/****************************************************************************************************************************/
	public ModelAndView ReportesMethod(String url){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User customUser = (User) authentication.getPrincipal();
		if(customUser.getCedula() == null){
			return new ModelAndView("redirect:/404");
		}
		ModelAndView mav = new ModelAndView(url);
		if(url.equals("/jurado/reportes")){
			List<Jurado>  eleccionesJurado = juradoDao.findByCedula(customUser.getCedula());
			mav.addObject("elecciones", eleccionesJurado);
		}else{
			List<Eleccion> eleccionesAdmin = eleccionDao.findByCedulaCreador(customUser.getCedula());
			mav.addObject("elecciones", eleccionesAdmin);
		}
		return mav;
	}
	
	public String[] CargarDatosReporteMethod(long id){
		Eleccion eleccion = eleccionDao.findOne(id);
		List<ConteoElectronico> electronico = conteoElectronicoDao.findByIdElecciones(eleccion);
		List<ConteoFisico> fisico = conteoFisicoDao.findByIdElecciones(eleccion);
		List<Voto> vt = votoDao.findByIdElecciones(eleccion);;
		int totalFisico = 0, totalElectronico = 0;
		for(int i=0; i<fisico.size(); i++){totalFisico = totalFisico+ fisico.get(i).getSumatoria();}
		for(int i=0; i<electronico.size(); i++){totalElectronico = totalElectronico+ electronico.get(i).getSumatoria();}
		for(int j=0; j<vt.size(); j++){if(vt.get(j).getId_medio().getId() == 1){totalFisico++;}}
		String[] datos = {""+eleccion.getFechaInicio(), ""+eleccion.getFechaFin(), ""+totalElectronico, ""+totalFisico, ""+eleccion.getCierre()};
		return datos;
	}
	
	public List<String> CargarCandidatosReporteMethod(long id){
		Eleccion eleccion = eleccionDao.findOne(id);
		List<Candidato> candidatos = candidatoDao.findByIdElecciones(eleccion);
		List<String> datos = new ArrayList<>();
		ConteoElectronico co;
		ConteoFisico cf;
		int total = 0;
		String dato = "";
		for(int i=0; i<candidatos.size(); i++){
			total = 0;
			co = conteoElectronicoDao.findByIdCandidatos(candidatos.get(i));
			cf = conteoFisicoDao.findByIdCandidatos(candidatos.get(i));
			if(co != null){total = total + co.getSumatoria();}
			if(cf != null){total = total + cf.getSumatoria();}
			dato = candidatos.get(i).getNombre()+" - "+total+" votos";
			datos.add(dato);
		}
		return datos;
	}
/****************************************************************************************************************************/
	@RequestMapping(value = "/admin/subirCsv", method = RequestMethod.POST)
	public @ResponseBody boolean subirCsv(String row, long idEleccion){
		String[] datos = row.split(",");
		boolean continua = true;
		List<User> aux = userDao.findByCedula(datos[0]);
		System.out.println(datos[0]);
		if(aux != null){
			for(int i=0; i<aux.size(); i++){
				if(aux.get(i).getId_elecciones().getId() == idEleccion){
					return false;
				}
			}
		}
		if(continua){
			Eleccion eleccion = eleccionDao.findOne(idEleccion);
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User customUser = (User) authentication.getPrincipal();
			User user = new User(null, datos[0], datos[1], eleccion, "", new Date(), customUser.getCedula(), "");
			userDao.save(user);
		}
		return true;
	}
/****************************************************************************************************************************/
	@RequestMapping(value = "/admin/uploadFile", method = RequestMethod.POST)
	@ResponseBody public ResponseEntity<?> uploadFile(@RequestParam("imagen") MultipartFile uploadfile, 
										 @RequestParam("nombre") String nombre, 
										 @RequestParam("tarjeton") String tarjeton, 
										 @RequestParam("id") Long id) {
	    
	    Candidato aux = candidatoDao.findByNumeroTarjeton(Integer.parseInt(tarjeton));
	    if(null == aux){
	    	try {
	  	      // Get the filename and build the local file path
	  	      String filename = ""+id+tarjeton+".jpg";
	  	      String directory = "src/main/webapp/resources/images";
	  	      String bdName = "../resources/images/"+filename;
	  	      String filepath = Paths.get(directory, filename).toString();
	  	      // Save the file locally
	  	      BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filepath)));
	  	      InputStream is = uploadfile.getInputStream();
	  	      byte [] bytes = new byte[1024];
	  		  int sizeRead;
	  		  while ((sizeRead = is.read(bytes,0, 1024)) > 0) {
	  			  stream.write(bytes, 0, sizeRead);
	  		  }
	  	      stream.close();
	  	      Eleccion eleccion = eleccionDao.findOne(id);
	  	      Candidato candidato = new Candidato(null, nombre, Integer.parseInt(tarjeton), bdName, eleccion);
	  	      candidatoDao.save(candidato);
	  	    }
	  	    catch (Exception e) {
	  	    	System.out.println(e.getMessage());
	  	    	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	  	    }
	    	System.out.println("subió");
	  	    return new ResponseEntity<>(HttpStatus.OK);
	    }else{
	    	System.out.println("No elección");
	    	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
	}
	
/****************************************************************************************************************************/
	
	@Autowired
	EleccionDao eleccionDao;
	
	@Autowired
	CandidatoDao candidatoDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	VotoDao votoDao;
	
	@Autowired
	JuradoDao juradoDao;
	
	@Autowired
	ConteoFisicoDao conteoFisicoDao;
	
	@Autowired
	ConteoElectronicoDao conteoElectronicoDao;
	
	@Autowired
	MedioVotoDao medioVotoDao;
}
