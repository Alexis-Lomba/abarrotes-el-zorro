package unam.fes.aragon.tienda_el_zorro.infraestructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Ruta absoluta del escritorio (ajusta según tu sistema)
        String pathToImages = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "imagenes_productos" + File.separator;

        // Asegurarse de que el directorio existe
        File directory = new File(pathToImages);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + pathToImages);

        System.out.println("Configurando servicio de imágenes en: " + pathToImages);
    }
}