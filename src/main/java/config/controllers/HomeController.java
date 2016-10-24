package config.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import config.UserDetailsImpl;
import domain.admin.Admin;
import domain.admin.AdminDao;
import domain.jurado.Jurado;
import domain.jurado.JuradoDao;
import domain.user.User;

@Controller
public class HomeController {
	
	@RequestMapping(value = "/home")
	@ResponseBody
	public ModelAndView HomeMapped(String cedula){
		List<Admin> admin = adminDao.findByCedula(cedula);
		if(admin.size() > 0){
			CrearUsuario("ROLE_SUPER", cedula);
			return new ModelAndView("redirect:"+"admin/index");
		}else{
			List<Jurado> jurado = juradoDao.findByCedula(cedula);
			if(jurado.size() > 0){
				CrearUsuario("ROLE_ADMIN", cedula);
				return new ModelAndView("redirect:"+"jurado/index");				
			}else{
				CrearUsuario("ROLE_USER", cedula);
				return new ModelAndView("redirect:"+"user/index");
			}
		}
	}
	
	public void CrearUsuario(String role, String cedula){
		User user = new User();
		user.setCedula(cedula);
		UserDetails userDetails = (UserDetails) new UserDetailsImpl(user, role);
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
	/* LOGOUT USERS */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		SecurityContextHolder.clearContext();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:http://www.google.com";
	}
	
	@Autowired
	AdminDao adminDao;
	
	@Autowired
	JuradoDao juradoDao;
}
