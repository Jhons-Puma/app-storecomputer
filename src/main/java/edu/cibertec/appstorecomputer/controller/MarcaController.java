package edu.cibertec.appstorecomputer.controller;

import edu.cibertec.appstorecomputer.dto.MarcaDTO;
import edu.cibertec.appstorecomputer.service.MarcaService;
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
@RequestMapping("/marcas")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Marcas", description = "API para la gestión de marcas")
public class MarcaController {

    private final MarcaService marcaService;

    @GetMapping
    @Operation(summary = "Obtener todas las marcas", description = "Retorna una lista de todas las marcas")
    @ApiResponse(responseCode = "200", description = "Marcas encontradas",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MarcaDTO.class)))
    public ResponseEntity<List<MarcaDTO>> getAllMarcas() {
        log.info("REST request to get all Marcas");
        List<MarcaDTO> marcas = marcaService.findAll();
        return ResponseEntity.ok(marcas);
    }

    @GetMapping("/activas")
    @Operation(summary = "Obtener marcas activas", description = "Retorna una lista de marcas activas")
    @ApiResponse(responseCode = "200", description = "Marcas activas encontradas",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MarcaDTO.class)))
    public ResponseEntity<List<MarcaDTO>> getActiveMarcas() {
        log.info("REST request to get active Marcas");
        List<MarcaDTO> marcas = marcaService.findAllActive();
        return ResponseEntity.ok(marcas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener marca por ID", description = "Retorna una marca según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Marca encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MarcaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Marca no encontrada",
                    content = @Content)
    })
    public ResponseEntity<MarcaDTO> getMarcaById(
            @Parameter(description = "ID de la marca", required = true)
            @PathVariable Long id) {
        log.info("REST request to get Marca : {}", id);
        MarcaDTO marca = marcaService.findById(id);
        return ResponseEntity.ok(marca);
    }

    @GetMapping("/nombre/{nombre}")
    @Operation(summary = "Obtener marca por nombre", description = "Retorna una marca según su nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Marca encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MarcaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Marca no encontrada",
                    content = @Content)
    })
    public ResponseEntity<MarcaDTO> getMarcaByNombre(
            @Parameter(description = "Nombre de la marca", required = true)
            @PathVariable String nombre) {
        log.info("REST request to get Marca by nombre : {}", nombre);
        MarcaDTO marca = marcaService.findByNombre(nombre);
        return ResponseEntity.ok(marca);
    }

    @GetMapping("/pais/{paisOrigen}")
    @Operation(summary = "Obtener marcas por país de origen", description = "Retorna una lista de marcas según su país de origen")
    @ApiResponse(responseCode = "200", description = "Marcas encontradas",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MarcaDTO.class)))
    public ResponseEntity<List<MarcaDTO>> getMarcasByPaisOrigen(
            @Parameter(description = "País de origen", required = true)
            @PathVariable String paisOrigen) {
        log.info("REST request to get Marcas by paisOrigen : {}", paisOrigen);
        List<MarcaDTO> marcas = marcaService.findByPaisOrigen(paisOrigen);
        return ResponseEntity.ok(marcas);
    }

    @PostMapping
    @Operation(summary = "Crear una nueva marca", description = "Crea una nueva marca y retorna la marca creada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Marca creada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MarcaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Marca ya existe",
                    content = @Content)
    })
    public ResponseEntity<MarcaDTO> createMarca(
            @Parameter(description = "Datos de la marca", required = true)
            @Valid @RequestBody MarcaDTO marcaDTO) {
        log.info("REST request to save Marca : {}", marcaDTO);
        MarcaDTO result = marcaService.create(marcaDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una marca", description = "Actualiza una marca existente y retorna la marca actualizada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Marca actualizada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MarcaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Marca no encontrada",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Nombre de marca ya existe",
                    content = @Content)
    })
    public ResponseEntity<MarcaDTO> updateMarca(
            @Parameter(description = "ID de la marca", required = true)
            @PathVariable Long id,
            @Parameter(description = "Datos de la marca", required = true)
            @Valid @RequestBody MarcaDTO marcaDTO) {
        log.info("REST request to update Marca : {}, {}", id, marcaDTO);
        MarcaDTO result = marcaService.update(id, marcaDTO);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una marca", description = "Elimina una marca existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Marca eliminada",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Marca no encontrada",
                    content = @Content)
    })
    public ResponseEntity<Void> deleteMarca(
            @Parameter(description = "ID de la marca", required = true)
            @PathVariable Long id) {
        log.info("REST request to delete Marca : {}", id);
        marcaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}