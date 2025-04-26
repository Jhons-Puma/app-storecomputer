package edu.cibertec.appstorecomputer.controller;

import edu.cibertec.appstorecomputer.dto.CategoriaDTO;
import edu.cibertec.appstorecomputer.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Categorias", description = "API para la gestión de categorías")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    @Operation(summary = "Obtener todas las categorías", description = "Retorna una lista de todas las categorías")
    @ApiResponse(responseCode = "200", description = "Categorías encontradas",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoriaDTO.class)))
    public ResponseEntity<List<CategoriaDTO>> getAllCategorias() {
        log.info("REST request to get all Categorias");
        List<CategoriaDTO> categorias = categoriaService.findAll();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/activas")
    @Operation(summary = "Obtener categorías activas", description = "Retorna una lista de categorías activas")
    @ApiResponse(responseCode = "200", description = "Categorías activas encontradas",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoriaDTO.class)))
    public ResponseEntity<List<CategoriaDTO>> getActiveCategorias() {
        log.info("REST request to get active Categorias");
        List<CategoriaDTO> categorias = categoriaService.findAllActive();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener categoría por ID", description = "Retorna una categoría según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoriaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada",
                    content = @Content)
    })
    public ResponseEntity<CategoriaDTO> getCategoriaById(
            @Parameter(description = "ID de la categoría", required = true)
            @PathVariable Long id) {
        log.info("REST request to get Categoria : {}", id);
        CategoriaDTO categoria = categoriaService.findById(id);
        return ResponseEntity.ok(categoria);
    }

    @GetMapping("/nombre/{nombre}")
    @Operation(summary = "Obtener categoría por nombre", description = "Retorna una categoría según su nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoriaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada",
                    content = @Content)
    })
    public ResponseEntity<CategoriaDTO> getCategoriaByNombre(
            @Parameter(description = "Nombre de la categoría", required = true)
            @PathVariable String nombre) {
        log.info("REST request to get Categoria by nombre : {}", nombre);
        CategoriaDTO categoria = categoriaService.findByNombre(nombre);
        return ResponseEntity.ok(categoria);
    }

    @PostMapping
    @Operation(summary = "Crear una nueva categoría", description = "Crea una nueva categoría y retorna la categoría creada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoría creada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoriaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Categoría ya existe",
                    content = @Content)
    })
    public ResponseEntity<CategoriaDTO> createCategoria(
            @Parameter(description = "Datos de la categoría", required = true)
            @Valid @RequestBody CategoriaDTO categoriaDTO) {
        log.info("REST request to save Categoria : {}", categoriaDTO);
        CategoriaDTO result = categoriaService.create(categoriaDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una categoría", description = "Actualiza una categoría existente y retorna la categoría actualizada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría actualizada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoriaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Nombre de categoría ya existe",
                    content = @Content)
    })
    public ResponseEntity<CategoriaDTO> updateCategoria(
            @Parameter(description = "ID de la categoría", required = true)
            @PathVariable Long id,
            @Parameter(description = "Datos de la categoría", required = true)
            @Valid @RequestBody CategoriaDTO categoriaDTO) {
        log.info("REST request to update Categoria : {}, {}", id, categoriaDTO);
        CategoriaDTO result = categoriaService.update(id, categoriaDTO);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una categoría", description = "Elimina una categoría existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoría eliminada",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada",
                    content = @Content)
    })
    public ResponseEntity<Void> deleteCategoria(
            @Parameter(description = "ID de la categoría", required = true)
            @PathVariable Long id) {
        log.info("REST request to delete Categoria : {}", id);
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}