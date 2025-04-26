package edu.cibertec.appstorecomputer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for Marca entity
 */
public record MarcaDTO(
        Long id,
        
        @NotBlank(message = "El nombre de la marca es obligatorio")
        @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
        String nombre,
        
        @Size(max = 100, message = "El país de origen no debe exceder los 100 caracteres")
        String paisOrigen,
        
        @Size(max = 255, message = "El sitio web no debe exceder los 255 caracteres")
        String sitioWeb,
        
        Boolean activo
) {
    // Constructor for creating a new brand (without ID)
    public MarcaDTO(
            @NotBlank(message = "El nombre de la marca es obligatorio")
            @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
            String nombre,
            
            @Size(max = 100, message = "El país de origen no debe exceder los 100 caracteres")
            String paisOrigen,
            
            @Size(max = 255, message = "El sitio web no debe exceder los 255 caracteres")
            String sitioWeb,
            
            Boolean activo
    ) {
        this(null, nombre, paisOrigen, sitioWeb, activo);
    }
}