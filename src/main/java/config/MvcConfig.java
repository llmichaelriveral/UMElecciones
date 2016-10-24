package config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter{

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/404").setViewName("404");
        /****************************************************************************/
        registry.addViewController("/admin/index").setViewName("admin/index");
        registry.addViewController("/admin/administrarEleccion").setViewName("admin/administrarEleccion");
        registry.addViewController("/admin/elecciones").setViewName("admin/elecciones");
        registry.addViewController("/admin/eleccion").setViewName("admin/eleccion");
        registry.addViewController("/admin/reportes").setViewName("admin/reportes");
        registry.addViewController("/admin/configuracion").setViewName("admin/configuracion");
        /****************************************************************************/
        registry.addViewController("/jurado/index").setViewName("jurado/index");
        registry.addViewController("/jurado/eleccion").setViewName("jurado/eleccion");
        registry.addViewController("/jurado/elecciones").setViewName("jurado/elecciones");
        registry.addViewController("/jurado/subirVotos").setViewName("jurado/subirVotos");
        registry.addViewController("/jurado/administrarEleccion").setViewName("jurado/administrarEleccion");
        /****************************************************************************/
        registry.addViewController("/user/index").setViewName("user/index");
        registry.addViewController("/user/eleccion").setViewName("user/eleccion");
    }    
    
    @Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
    
    @Bean
    public MultipartResolver multipartResolver() throws IOException {        
        return new StandardServletMultipartResolver();
    }    
}
