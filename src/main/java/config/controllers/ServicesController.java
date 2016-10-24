package config.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
import domain.usuarios.Usuarios;
import domain.usuarios.UsuariosDao;
import domain.voto.Voto;
import domain.voto.VotoDao;

@RestController
public class ServicesController {
/********************************************************************************************************************/
	/*LOGIN*/
	@RequestMapping(value = "services/login", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usuarios> restDatosLogin(String cedula, String pass) {
		Usuarios usuario;
		if((pass == null) || (cedula == null)){usuario = null;}
		String passMd5 = getMD5hash(pass);
		usuario = usuariosDao.findByCedula(cedula);
		if(usuario != null){
			if(!(usuario.getPassword().equals(passMd5))){usuario = null;}
		}
		return new ResponseEntity<Usuarios>(usuario, HttpStatus.OK);
	}
	
	private String getMD5hash(String password) {
	    return MD5Generator.getMd5HashCode(password);
	}
/********************************************************************************************************************/
	/*Votar*/
	@RequestMapping(value = "services/elecciones", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Eleccion>> restDatosElecciones(String cedula) {
		/*Tomamos las elecciones en las que tiene el usuario permitido votar*/
		List<User> userApto = userDao.findByCedula(cedula);
		List<Eleccion> listaElecciones = (List<Eleccion>) eleccionDao.findAll();
		List<Eleccion> listaEleccionesAptas = new ArrayList<>();
		Date hoy = new Date();
		for(int j=0; j<userApto.size(); j++){
			for(int i=0; i<listaElecciones.size(); i++){
				if(userApto.get(j) != null){
					if(listaElecciones.get(i).getId() == userApto.get(j).getId_elecciones().getId()){
						if(listaElecciones.get(i).getCierre()){
							if(listaElecciones.get(i).getFechaInicio().compareTo(hoy) < 0){
								if(hoy.compareTo(listaElecciones.get(i).getFechaFin()) < 0){
									if(!HaVotado(userApto.get(j).getCedula(), listaElecciones.get(i).getId())){
										listaEleccionesAptas.add(listaElecciones.get(i));
									}
								}
							}
						}
					}
				}
			}
		}
		if(listaEleccionesAptas.size() == 0){
			listaEleccionesAptas = null;
		}
		return new ResponseEntity<List<Eleccion>>(listaEleccionesAptas, HttpStatus.OK);
	}
	
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
/********************************************************************************************************************/
	/*Candidatos*/
	@RequestMapping(value = "services/candidatos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Candidato>> restDatosCandidatos(long id) {
		/*Tomamos las elecciones en las que tiene el usuario permitido votar*/
		Eleccion eleccion = eleccionDao.findOne(id);
		List<Candidato> candidatos = null;
		if(eleccion != null){
			candidatos = candidatoDao.findByIdElecciones(eleccion);
		}
		return new ResponseEntity<List<Candidato>>(candidatos, HttpStatus.OK);
	}
/********************************************************************************************************************/
	/*Votar*/
	@RequestMapping(value = "services/votar", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<String>> restDatosVota(long idCandidato, long idEleccion, String cedula, String direccion) {
		List<String> response = new ArrayList<>();
		/*Tomamos todos los votos que hay por esta eleccion*/
		Candidato candidato = candidatoDao.findOne(idCandidato);
		Eleccion eleccion = eleccionDao.findOne(idEleccion);
		MedioVoto medio = medioVotoDao.findOne((long)2);
		List<Voto> votosEleccion = votoDao.findByIdElecciones(eleccion);
		/*Validamos si hay o no algun voto de ese usuario*/
		boolean puedeVotar = true;
		boolean existeConteo = false;
		for(int i=0; i<votosEleccion.size(); i++){
			if(cedula.equals(votosEleccion.get(i).getId_usuarios_aptos().getCedula())){
				puedeVotar = false;
				String medioVoto = votosEleccion.get(i).getId_medio().getNombre();
				response.add("Usted ya ha registrado un voto "+medioVoto);
			}
		}
		if(puedeVotar){
			if(!HaVotado(cedula, idEleccion)){
				if(eleccion.getCierre()){
					Date fechaVoto = new Date();
					List<User> userActual = userDao.findByCedula(cedula);
					User userVotante = null;
					for(int i=0; i<userActual.size(); i++){
						if(userActual.get(i).getId_elecciones().getId() == eleccion.getId()){
							userVotante = userActual.get(i);
						}
					}
					Voto votoNuevo = new Voto(null, fechaVoto, direccion, eleccion, candidato, userVotante, medio);
					response.add("Su voto se ha registrado.");
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
				}else{
					response.add("Se encuentra fuera de las fechas de elección.");
				}
			}
		}
		return new ResponseEntity<List<String>>(response, HttpStatus.OK);
	}
/********************************************************************************************************************/
	@Autowired
	private SendMail notificationService;
	
	@Autowired
	EleccionDao eleccionDao;
	
	@Autowired
	UsuariosDao usuariosDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	VotoDao votoDao;
	
	@Autowired
	CandidatoDao candidatoDao;
	
	@Autowired
	MedioVotoDao medioVotoDao;
	
	@Autowired
	ConteoElectronicoDao conteoElectronicoDao;
	
	public final static class MD5Generator {
		public static String getMd5HashCode(String requestParameters) {
		    return DigestUtils.md5DigestAsHex(requestParameters.getBytes());
		}
	}
	
}