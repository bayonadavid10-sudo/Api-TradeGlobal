package com.example.demo.config;

import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Carpeta en disco donde se guardan las imágenes subidas
    private static final String UPLOAD_DIR = "uploads/imagenes/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Convierte la ruta relativa a absoluta para que funcione desde cualquier directorio
        String absolutePath = Paths.get(UPLOAD_DIR).toAbsolutePath().toUri().toString();

        // /imagenes/** → carpeta uploads/imagenes/ en disco (fuera del classpath)
        // Así Spring sirve los archivos en tiempo real sin necesidad de reiniciar
        registry.addResourceHandler("/imagenes/**")
                .addResourceLocations(absolutePath);
    }
}
