package edu.cibertec.appstorecomputer.service.impl;

import edu.cibertec.appstorecomputer.dto.ProductoDTO;
import edu.cibertec.appstorecomputer.exception.ResourceAlreadyExistsException;
import edu.cibertec.appstorecomputer.exception.ResourceNotFoundException;
import edu.cibertec.appstorecomputer.mapper.ProductoMapper;
import edu.cibertec.appstorecomputer.model.Categoria;
import edu.cibertec.appstorecomputer.model.Marca;
import edu.cibertec.appstorecomputer.model.Producto;
import edu.cibertec.appstorecomputer.repository.CategoriaRepository;
import edu.cibertec.appstorecomputer.repository.MarcaRepository;
import edu.cibertec.appstorecomputer.repository.ProductoRepository;
import edu.cibertec.appstorecomputer.service.ProductoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final MarcaRepository marcaRepository;
    private final ProductoMapper productoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> findAll() {
        log.info("Fetching all products");
        List<Producto> productos = productoRepository.findAll();
        return productoMapper.toDtoList(productos);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductoDTO> findAllPaginated(Pageable pageable) {
        log.info("Fetching all products with pagination");
        Page<Producto> productos = productoRepository.findAll(pageable);
        return productos.map(productoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> findAllActive() {
        log.info("Fetching all active products");
        List<Producto> productos = productoRepository.findByActivoTrue();
        return productoMapper.toDtoList(productos);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductoDTO> findAllActivePaginated(Pageable pageable) {
        log.info("Fetching all active products with pagination");
        Page<Producto> productos = productoRepository.findByActivoTrue(pageable);
        return productos.map(productoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoDTO findById(Long id) {
        log.info("Fetching product with id: {}", id);
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto", "id", id));
        return productoMapper.toDto(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoDTO findByCodigo(String codigo) {
        log.info("Fetching product with codigo: {}", codigo);
        Producto producto = productoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ResourceNotFoundException("Producto", "codigo", codigo));
        return productoMapper.toDto(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> findByCategoria(Long categoriaId) {
        log.info("Fetching products by categoria id: {}", categoriaId);
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria", "id", categoriaId));
        List<Producto> productos = productoRepository.findByCategoria(categoria);
        return productoMapper.toDtoList(productos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> findByMarca(Long marcaId) {
        log.info("Fetching products by marca id: {}", marcaId);
        Marca marca = marcaRepository.findById(marcaId)
                .orElseThrow(() -> new ResourceNotFoundException("Marca", "id", marcaId));
        List<Producto> productos = productoRepository.findByMarca(marca);
        return productoMapper.toDtoList(productos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> findByCategoriaAndMarca(Long categoriaId, Long marcaId) {
        log.info("Fetching products by categoria id: {} and marca id: {}", categoriaId, marcaId);
        // Verify that both categoria and marca exist
        if (!categoriaRepository.existsById(categoriaId)) {
            throw new ResourceNotFoundException("Categoria", "id", categoriaId);
        }
        if (!marcaRepository.existsById(marcaId)) {
            throw new ResourceNotFoundException("Marca", "id", marcaId);
        }
        
        List<Producto> productos = productoRepository.findByCategoriaAndMarca(categoriaId, marcaId);
        return productoMapper.toDtoList(productos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> findByNombreContaining(String nombre) {
        log.info("Fetching products containing nombre: {}", nombre);
        List<Producto> productos = productoRepository.findByNombreContainingIgnoreCase(nombre);
        return productoMapper.toDtoList(productos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> findByPrecioBetween(BigDecimal precioMin, BigDecimal precioMax) {
        log.info("Fetching products with price between {} and {}", precioMin, precioMax);
        List<Producto> productos = productoRepository.findByPrecioBetween(precioMin, precioMax);
        return productoMapper.toDtoList(productos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> findByStockLessThan(Integer stockMinimo) {
        log.info("Fetching products with stock less than {}", stockMinimo);
        List<Producto> productos = productoRepository.findByStockLessThan(stockMinimo);
        return productoMapper.toDtoList(productos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> findAvailableProducts() {
        log.info("Fetching available products");
        List<Producto> productos = productoRepository.findAvailableProducts();
        return productoMapper.toDtoList(productos);
    }

    @Override
    public ProductoDTO create(ProductoDTO productoDTO) {
        log.info("Creating new product: {}", productoDTO.nombre());
        
        if (productoRepository.existsByCodigo(productoDTO.codigo())) {
            throw new ResourceAlreadyExistsException("Producto", "codigo", productoDTO.codigo());
        }
        
        // Verify that categoria exists
        Categoria categoria = categoriaRepository.findById(productoDTO.categoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria", "id", productoDTO.categoriaId()));
        
        // Verify that marca exists
        Marca marca = marcaRepository.findById(productoDTO.marcaId())
                .orElseThrow(() -> new ResourceNotFoundException("Marca", "id", productoDTO.marcaId()));
        
        Producto producto = productoMapper.toEntity(productoDTO);
        producto.setCategoria(categoria);
        producto.setMarca(marca);
        
        // Set default values if not provided
        if (producto.getActivo() == null) {
            producto.setActivo(true);
        }
        
        Producto savedProducto = productoRepository.save(producto);
        log.info("Product created with id: {}", savedProducto.getId());
        
        return productoMapper.toDto(savedProducto);
    }

    @Override
    public ProductoDTO update(Long id, ProductoDTO productoDTO) {
        log.info("Updating product with id: {}", id);
        
        Producto existingProducto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto", "id", id));
        
        // Check if code is being changed and if the new code already exists
        if (!existingProducto.getCodigo().equals(productoDTO.codigo()) && 
                productoRepository.existsByCodigo(productoDTO.codigo())) {
            throw new ResourceAlreadyExistsException("Producto", "codigo", productoDTO.codigo());
        }
        
        // Verify that categoria exists if it's being changed
        if (productoDTO.categoriaId() != null && 
                !existingProducto.getCategoria().getId().equals(productoDTO.categoriaId())) {
            categoriaRepository.findById(productoDTO.categoriaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoria", "id", productoDTO.categoriaId()));
        }
        
        // Verify that marca exists if it's being changed
        if (productoDTO.marcaId() != null && 
                !existingProducto.getMarca().getId().equals(productoDTO.marcaId())) {
            marcaRepository.findById(productoDTO.marcaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Marca", "id", productoDTO.marcaId()));
        }
        
        productoMapper.updateEntityFromDto(productoDTO, existingProducto);
        
        // Update categoria if it's being changed
        if (productoDTO.categoriaId() != null && 
                !existingProducto.getCategoria().getId().equals(productoDTO.categoriaId())) {
            Categoria categoria = new Categoria();
            categoria.setId(productoDTO.categoriaId());
            existingProducto.setCategoria(categoria);
        }
        
        // Update marca if it's being changed
        if (productoDTO.marcaId() != null && 
                !existingProducto.getMarca().getId().equals(productoDTO.marcaId())) {
            Marca marca = new Marca();
            marca.setId(productoDTO.marcaId());
            existingProducto.setMarca(marca);
        }
        
        Producto updatedProducto = productoRepository.save(existingProducto);
        log.info("Product updated: {}", updatedProducto.getId());
        
        return productoMapper.toDto(updatedProducto);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting product with id: {}", id);
        
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto", "id", id));
        
        productoRepository.delete(producto);
        log.info("Product deleted: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByCodigo(String codigo) {
        return productoRepository.existsByCodigo(codigo);
    }
}