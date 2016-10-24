package config.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import config.SendMail;
import domain.candidato.Candidato;
import domain.candidato.CandidatoDao;
import domain.conteo_electronico.ConteoElectronico;
import domain.conteo_electronico.ConteoElectronicoDao;
import domain.eleccion.Eleccion;
import domain.eleccion.EleccionDao;
import domain.medio_voto.MedioVoto;
import domain.medio_voto.MedioVotoDao;
import domain.user.User;
import domain.user.UserDao;
import domain.voto.Voto;
import domain.voto.VotoDao;

@Controller
public class VotoController {

	/*Formato de fecha*/
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	@RequestMapping("/user/votar")
	public ModelAndView nuevoVotoUser(long idEleccion, long idCandidato){
		return nuevoVoto(idCandidato, idEleccion, "/user");
	}
	
	@RequestMapping("/jurado/votar")
	public ModelAndView nuevoVotoJurado(long idEleccion, long idCandidato){
		/*Tomamos el Usuario actual*/
		return nuevoVoto(idCandidato, idEleccion, "/jurado");
	}
	
	@RequestMapping("/admin/votar")
	public ModelAndView nuevoVotoAdmin(long idEleccion, long idCandidato){
		/*Tomamos el Usuario actual*/
		return nuevoVoto(idCandidato, idEleccion, "/admin");
	}
	
	public ModelAndView nuevoVoto(long idCandidato, long idEleccion, String url){
		CerrarElecciones();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User customUser = (User) authentication.getPrincipal();
		/*Tomamos todos los votos que hay por esta eleccion*/
		Candidato candidato = candidatoDao.findOne(idCandidato);
		Eleccion eleccion = eleccionDao.findOne(idEleccion);
		if(!eleccion.getCierre()){return new ModelAndView("redirect:"+url+"/index?FueraFechas");}
		MedioVoto medio = medioVotoDao.findOne((long)0);
		List<Voto> votosEleccion = votoDao.findByIdElecciones(eleccion);
		/*Validamos si hay o no algun voto de ese usuario*/
		boolean puedeVotar = true;
		boolean existeConteo = false;
		for(int i=0; i<votosEleccion.size(); i++){
			if(customUser.getCedula().equals(votosEleccion.get(i).getId_usuarios_aptos().getCedula())){
				puedeVotar = false;
				String medioVoto = votosEleccion.get(i).getId_medio().getNombre();
				return new ModelAndView("redirect:"+url+"/index?YaVoto="+medioVoto);
			}
		}
		if(puedeVotar){
			Date fechaVoto = new Date();
			List<User> userActual = userDao.findByCedula(customUser.getCedula());
			User userVotante = null;
			for(int i=0; i<userActual.size(); i++){
				if(userActual.get(i).getId_elecciones().getId() == eleccion.getId()){
					userVotante = userActual.get(i);
				}
			}
			Voto votoNuevo = new Voto(null, fechaVoto, "", eleccion, candidato, userVotante, medio);
			/*Se envía correo*/
			notificationService.sendNotification(userVotante.getEmail(), eleccion.getNombre());
			/*Creamos el conteo o lo aumentamos*/
			List<ConteoElectronico> listaConteo = conteoElectronicoDao.findByIdElecciones(eleccion);
			ConteoElectronico conteo = null;
			for(int i=0; i<listaConteo.size();i++){
				if(listaConteo.get(i).getId_candidatos().getId() == candidato.getId()){
					conteo = listaConteo.get(i);
					existeConteo = true;
				}
			}
			if(existeConteo){
				conteo.setSumatoria(conteo.getSumatoria()+1);
			}else{
				conteo = new ConteoElectronico(null, 1, eleccion, candidato);
			}
			votoDao.save(votoNuevo);
			conteoElectronicoDao.save(conteo);
			return new ModelAndView("redirect:"+url+"/index?VotoExitoso");			
		}else{
			return new ModelAndView("redirect:"+url+"/index?YaVoto");
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
	@Autowired
	private SendMail notificationService;
	
	@Autowired
	EleccionDao eleccionDao;
	
	@Autowired
	VotoDao votoDao;
	
	@Autowired
	CandidatoDao candidatoDao;
	
	@Autowired
	ConteoElectronicoDao conteoElectronicoDao;
	
	@Autowired
	MedioVotoDao medioVotoDao;
	
	@Autowired
	UserDao userDao;
}
