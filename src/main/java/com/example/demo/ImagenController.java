package com.example.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/imagenes")
@CrossOrigin(origins = "*")
public class ImagenController {

    private static final String UPLOAD_DIR = "uploads/imagenes/";

    @PostMapping("/subir")
    public ResponseEntity<?> subirImagen(@RequestParam("archivo") MultipartFile archivo) {
        try {
            // Crea la carpeta si no existe
            Files.createDirectories(Paths.get(UPLOAD_DIR));

            String nombreArchivo = System.currentTimeMillis() + "_" + archivo.getOriginalFilename();
            Path rutaDestino = Paths.get(UPLOAD_DIR).resolve(nombreArchivo);
            Files.copy(archivo.getInputStream(), rutaDestino, StandardCopyOption.REPLACE_EXISTING);

            return ResponseEntity.ok(Map.of("url", "/imagenes/" + nombreArchivo));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(Map.of("error", "No se pudo subir la imagen: " + e.getMessage()));
        }
    }
}