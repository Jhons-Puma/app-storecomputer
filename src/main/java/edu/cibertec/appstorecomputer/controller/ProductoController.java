package edu.cibertec.appstorecomputer.controller;

import edu.cibertec.appstorecomputer.dto.ProductoDTO;
import edu.cibertec.appstorecomputer.service.ProductoService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/productos")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Productos", description = "API para la gestión de productos")
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    @Operation(summary = "Obtener todos los productos", description = "Retorna una lista de todos los productos")
    @ApiResponse(responseCode = "200", description = "Productos encontrados",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductoDTO.class)))
    public ResponseEntity<List<ProductoDTO>> getAllProductos() {
        log.info("REST request to get all Productos");
        List<ProductoDTO> productos = productoService.findAll();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/pagina")
    @Operation(summary = "Obtener productos paginados", description = "Retorna una página de productos")
    @ApiResponse(responseCode = "200", description = "Productos encontrados",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)))
    public ResponseEntity<Page<ProductoDTO>> getAllProductosPaginated(Pageable pageable) {
        log.info("REST request to get a page of Productos");
        Page<ProductoDTO> page = productoService.findAllPaginated(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/activos")
    @Operation(summary = "Obtener productos activos", description = "Retorna una lista de productos activos")
    @ApiResponse(responseCode = "200", description = "Productos activos encontrados",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductoDTO.class)))
    public ResponseEntity<List<ProductoDTO>> getActiveProductos() {
        log.info("REST request to get active Productos");
        List<ProductoDTO> productos = productoService.findAllActive();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/activos/pagina")
    @Operation(summary = "Obtener productos activos paginados", description = "Retorna una página de productos activos")
    @ApiResponse(responseCode = "200", description = "Productos activos encontrados",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)))
    public ResponseEntity<Page<ProductoDTO>> getActiveProductosPaginated(Pageable pageable) {
        log.info("REST request to get a page of active Productos");
        Page<ProductoDTO> page = productoService.findAllActivePaginated(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Retorna un producto según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado",
                    content = @Content)
    })
    public ResponseEntity<ProductoDTO> getProductoById(
            @Parameter(description = "ID del producto", required = true)
            @PathVariable Long id) {
        log.info("REST request to get Producto : {}", id);
        ProductoDTO producto = productoService.findById(id);
        return ResponseEntity.ok(producto);
    }

    @GetMapping("/codigo/{codigo}")
    @Operation(summary = "Obtener producto por código", description = "Retorna un producto según su código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado",
                    content = @Content)
    })
    public ResponseEntity<ProductoDTO> getProductoByCodigo(
            @Parameter(description = "Código del producto", required = true)
            @PathVariable String codigo) {
        log.info("REST request to get Producto by codigo : {}", codigo);
        ProductoDTO producto = productoService.findByCodigo(codigo);
        return ResponseEntity.ok(producto);
    }

    @GetMapping("/categoria/{categoriaId}")
    @Operation(summary = "Obtener productos por categoría", description = "Retorna una lista de productos según su categoría")
    @ApiResponse(responseCode = "200", description = "Productos encontrados",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductoDTO.class)))
    public ResponseEntity<List<ProductoDTO>> getProductosByCategoria(
            @Parameter(description = "ID de la categoría", required = true)
            @PathVariable Long categoriaId) {
        log.info("REST request to get Productos by categoria : {}", categoriaId);
        List<ProductoDTO> productos = productoService.findByCategoria(categoriaId);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/marca/{marcaId}")
    @Operation(summary = "Obtener productos por marca", description = "Retorna una lista de productos según su marca")
    @ApiResponse(responseCode = "200", description = "Productos encontrados",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductoDTO.class)))
    public ResponseEntity<List<ProductoDTO>> getProductosByMarca(
            @Parameter(description = "ID de la marca", required = true)
            @PathVariable Long marcaId) {
        log.info("REST request to get Productos by marca : {}", marcaId);
        List<ProductoDTO> productos = productoService.findByMarca(marcaId);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/categoria/{categoriaId}/marca/{marcaId}")
    @Operation(summary = "Obtener productos por categoría y marca", description = "Retorna una lista de productos según su categoría y marca")
    @ApiResponse(responseCode = "200", description = "Productos encontrados",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductoDTO.class)))
    public ResponseEntity<List<ProductoDTO>> getProductosByCategoriaAndMarca(
            @Parameter(description = "ID de la categoría", required = true)
            @PathVariable Long categoriaId,
            @Parameter(description = "ID de la marca", required = true)
            @PathVariable Long marcaId) {
        log.info("REST request to get Productos by categoria : {} and marca : {}", categoriaId, marcaId);
        List<ProductoDTO> productos = productoService.findByCategoriaAndMarca(categoriaId, marcaId);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/nombre")
    @Operation(summary = "Obtener productos por nombre", description = "Retorna una lista de productos que contienen el nombre especificado")
    @ApiResponse(responseCode = "200", description = "Productos encontrados",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductoDTO.class)))
    public ResponseEntity<List<ProductoDTO>> getProductosByNombre(
            @Parameter(description = "Nombre del producto", required = true)
            @RequestParam String nombre) {
        log.info("REST request to get Productos by nombre : {}", nombre);
        List<ProductoDTO> productos = productoService.findByNombreContaining(nombre);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/precio")
    @Operation(summary = "Obtener productos por rango de precio", description = "Retorna una lista de productos dentro del rango de precio especificado")
    @ApiResponse(responseCode = "200", description = "Productos encontrados",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductoDTO.class)))
    public ResponseEntity<List<ProductoDTO>> getProductosByPrecio(
            @Parameter(description = "Precio mínimo", required = true)
            @RequestParam BigDecimal min,
            @Parameter(description = "Precio máximo", required = true)
            @RequestParam BigDecimal max) {
        log.info("REST request to get Productos by precio between : {} and {}", min, max);
        List<ProductoDTO> productos = productoService.findByPrecioBetween(min, max);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/stock-bajo")
    @Operation(summary = "Obtener productos con stock bajo", description = "Retorna una lista de productos con stock menor al especificado")
    @ApiResponse(responseCode = "200", description = "Productos encontrados",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductoDTO.class)))
    public ResponseEntity<List<ProductoDTO>> getProductosByStockBajo(
            @Parameter(description = "Stock mínimo", required = true)
            @RequestParam Integer stockMinimo) {
        log.info("REST request to get Productos with stock less than : {}", stockMinimo);
        List<ProductoDTO> productos = productoService.findByStockLessThan(stockMinimo);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/disponibles")
    @Operation(summary = "Obtener productos disponibles", description = "Retorna una lista de productos disponibles (activos y con stock)")
    @ApiResponse(responseCode = "200", description = "Productos disponibles encontrados",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductoDTO.class)))
    public ResponseEntity<List<ProductoDTO>> getAvailableProductos() {
        log.info("REST request to get available Productos");
        List<ProductoDTO> productos = productoService.findAvailableProducts();
        return ResponseEntity.ok(productos);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo producto", description = "Crea un nuevo producto y retorna el producto creado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Categoría o marca no encontrada",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Producto ya existe",
                    content = @Content)
    })
    public ResponseEntity<ProductoDTO> createProducto(
            @Parameter(description = "Datos del producto", required = true)
            @Valid @RequestBody ProductoDTO productoDTO) {
        log.info("REST request to save Producto : {}", productoDTO);
        ProductoDTO result = productoService.create(productoDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto", description = "Actualiza un producto existente y retorna el producto actualizado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Producto, categoría o marca no encontrada",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Código de producto ya existe",
                    content = @Content)
    })
    public ResponseEntity<ProductoDTO> updateProducto(
            @Parameter(description = "ID del producto", required = true)
            @PathVariable Long id,
            @Parameter(description = "Datos del producto", required = true)
            @Valid @RequestBody ProductoDTO productoDTO) {
        log.info("REST request to update Producto : {}, {}", id, productoDTO);
        ProductoDTO result = productoService.update(id, productoDTO);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto", description = "Elimina un producto existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado",
                    content = @Content)
    })
    public ResponseEntity<Void> deleteProducto(
            @Parameter(description = "ID del producto", required = true)
            @PathVariable Long id) {
        log.info("REST request to delete Producto : {}", id);
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}