package edu.cibertec.appstorecomputer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for Categoria entity
 */
public record CategoriaDTO(
        Long id,
        
        @NotBlank(message = "El nombre de la categoría es obligatorio")
        @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
        String nombre,
        
        @Size(max = 255, message = "La descripción no debe exceder los 255 caracteres")
        String descripcion,
        
        Boolean activo
) {
    // Constructor for creating a new category (without ID)
    public CategoriaDTO(
            @NotBlank(message = "El nombre de la categoría es obligatorio")
            @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
            String nombre,
            
            @Size(max = 255, message = "La descripción no debe exceder los 255 caracteres")
            String descripcion,
            
            Boolean activo
    ) {
        this(null, nombre, descripcion, activo);
    }
}