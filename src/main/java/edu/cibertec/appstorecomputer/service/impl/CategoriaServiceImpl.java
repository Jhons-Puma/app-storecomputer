package edu.cibertec.appstorecomputer.service.impl;

import edu.cibertec.appstorecomputer.dto.CategoriaDTO;
import edu.cibertec.appstorecomputer.exception.ResourceAlreadyExistsException;
import edu.cibertec.appstorecomputer.exception.ResourceNotFoundException;
import edu.cibertec.appstorecomputer.mapper.CategoriaMapper;
import edu.cibertec.appstorecomputer.model.Categoria;
import edu.cibertec.appstorecomputer.repository.CategoriaRepository;
import edu.cibertec.appstorecomputer.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaDTO> findAll() {
        log.info("Fetching all categories");
        List<Categoria> categorias = categoriaRepository.findAll();
        return categoriaMapper.toDtoList(categorias);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaDTO> findAllActive() {
        log.info("Fetching all active categories");
        List<Categoria> categorias = categoriaRepository.findByActivoTrue();
        return categoriaMapper.toDtoList(categorias);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaDTO findById(Long id) {
        log.info("Fetching category with id: {}", id);
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria", "id", id));
        return categoriaMapper.toDto(categoria);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaDTO findByNombre(String nombre) {
        log.info("Fetching category with nombre: {}", nombre);
        Categoria categoria = categoriaRepository.findByNombre(nombre)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria", "nombre", nombre));
        return categoriaMapper.toDto(categoria);
    }

    @Override
    public CategoriaDTO create(CategoriaDTO categoriaDTO) {
        log.info("Creating new category: {}", categoriaDTO.nombre());
        
        if (categoriaRepository.existsByNombre(categoriaDTO.nombre())) {
            throw new ResourceAlreadyExistsException("Categoria", "nombre", categoriaDTO.nombre());
        }
        
        Categoria categoria = categoriaMapper.toEntity(categoriaDTO);
        
        // Set default values if not provided
        if (categoria.getActivo() == null) {
            categoria.setActivo(true);
        }
        
        Categoria savedCategoria = categoriaRepository.save(categoria);
        log.info("Category created with id: {}", savedCategoria.getId());
        
        return categoriaMapper.toDto(savedCategoria);
    }

    @Override
    public CategoriaDTO update(Long id, CategoriaDTO categoriaDTO) {
        log.info("Updating category with id: {}", id);
        
        Categoria existingCategoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria", "id", id));
        
        // Check if name is being changed and if the new name already exists
        if (!existingCategoria.getNombre().equals(categoriaDTO.nombre()) && 
                categoriaRepository.existsByNombre(categoriaDTO.nombre())) {
            throw new ResourceAlreadyExistsException("Categoria", "nombre", categoriaDTO.nombre());
        }
        
        categoriaMapper.updateEntityFromDto(categoriaDTO, existingCategoria);
        
        Categoria updatedCategoria = categoriaRepository.save(existingCategoria);
        log.info("Category updated: {}", updatedCategoria.getId());
        
        return categoriaMapper.toDto(updatedCategoria);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting category with id: {}", id);
        
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria", "id", id));
        
        // Check if the category has associated products
        if (!categoria.getProductos().isEmpty()) {
            log.warn("Cannot delete category with id: {} because it has associated products", id);
            // Instead of deleting, mark as inactive
            categoria.setActivo(false);
            categoriaRepository.save(categoria);
            log.info("Category marked as inactive: {}", id);
        } else {
            categoriaRepository.delete(categoria);
            log.info("Category deleted: {}", id);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByNombre(String nombre) {
        return categoriaRepository.existsByNombre(nombre);
    }
}