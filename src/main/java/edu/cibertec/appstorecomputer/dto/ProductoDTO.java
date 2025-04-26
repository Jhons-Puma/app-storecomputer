package edu.cibertec.appstorecomputer.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for Producto entity
 */
public record ProductoDTO(
        Long id,
        
        @NotBlank(message = "El nombre del producto es obligatorio")
        @Size(min = 2, max = 150, message = "El nombre debe tener entre 2 y 150 caracteres")
        String nombre,
        
        @Size(max = 500, message = "La descripción no debe exceder los 500 caracteres")
        String descripcion,
        
        @NotNull(message = "El precio es obligatorio")
        @Positive(message = "El precio debe ser mayor que cero")
        BigDecimal precio,
        
        @NotNull(message = "El stock es obligatorio")
        @Min(value = 0, message = "El stock no puede ser negativo")
        Integer stock,
        
        @NotBlank(message = "El código del producto es obligatorio")
        @Size(min = 2, max = 50, message = "El código debe tener entre 2 y 50 caracteres")
        String codigo,
        
        @Size(max = 255, message = "La URL de la imagen no debe exceder los 255 caracteres")
        String imagenUrl,
        
        LocalDateTime fechaCreacion,
        
        Boolean activo,
        
        @NotNull(message = "La categoría es obligatoria")
        Long categoriaId,
        
        @NotNull(message = "La marca es obligatoria")
        Long marcaId,
        
        // These fields are for response only, not required in requests
        String categoriaNombre,
        String marcaNombre
) {
    // Constructor for creating a new product (without ID and fechaCreacion)
    public ProductoDTO(
            @NotBlank(message = "El nombre del producto es obligatorio")
            @Size(min = 2, max = 150, message = "El nombre debe tener entre 2 y 150 caracteres")
            String nombre,
            
            @Size(max = 500, message = "La descripción no debe exceder los 500 caracteres")
            String descripcion,
            
            @NotNull(message = "El precio es obligatorio")
            @Positive(message = "El precio debe ser mayor que cero")
            BigDecimal precio,
            
            @NotNull(message = "El stock es obligatorio")
            @Min(value = 0, message = "El stock no puede ser negativo")
            Integer stock,
            
            @NotBlank(message = "El código del producto es obligatorio")
            @Size(min = 2, max = 50, message = "El código debe tener entre 2 y 50 caracteres")
            String codigo,
            
            @Size(max = 255, message = "La URL de la imagen no debe exceder los 255 caracteres")
            String imagenUrl,
            
            Boolean activo,
            
            @NotNull(message = "La categoría es obligatoria")
            Long categoriaId,
            
            @NotNull(message = "La marca es obligatoria")
            Long marcaId
    ) {
        this(null, nombre, descripcion, precio, stock, codigo, imagenUrl, null, activo, categoriaId, marcaId, null, null);
    }
}