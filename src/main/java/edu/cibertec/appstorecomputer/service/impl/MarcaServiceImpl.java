package edu.cibertec.appstorecomputer.service.impl;

import edu.cibertec.appstorecomputer.dto.MarcaDTO;
import edu.cibertec.appstorecomputer.exception.ResourceAlreadyExistsException;
import edu.cibertec.appstorecomputer.exception.ResourceNotFoundException;
import edu.cibertec.appstorecomputer.mapper.MarcaMapper;
import edu.cibertec.appstorecomputer.model.Marca;
import edu.cibertec.appstorecomputer.repository.MarcaRepository;
import edu.cibertec.appstorecomputer.service.MarcaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MarcaServiceImpl implements MarcaService {

    private final MarcaRepository marcaRepository;
    private final MarcaMapper marcaMapper;

    @Override
    @Transactional(readOnly = true)
    public List<MarcaDTO> findAll() {
        log.info("Fetching all brands");
        List<Marca> marcas = marcaRepository.findAll();
        return marcaMapper.toDtoList(marcas);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MarcaDTO> findAllActive() {
        log.info("Fetching all active brands");
        List<Marca> marcas = marcaRepository.findByActivoTrue();
        return marcaMapper.toDtoList(marcas);
    }

    @Override
    @Transactional(readOnly = true)
    public MarcaDTO findById(Long id) {
        log.info("Fetching brand with id: {}", id);
        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Marca", "id", id));
        return marcaMapper.toDto(marca);
    }

    @Override
    @Transactional(readOnly = true)
    public MarcaDTO findByNombre(String nombre) {
        log.info("Fetching brand with nombre: {}", nombre);
        Marca marca = marcaRepository.findByNombre(nombre)
                .orElseThrow(() -> new ResourceNotFoundException("Marca", "nombre", nombre));
        return marcaMapper.toDto(marca);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MarcaDTO> findByPaisOrigen(String paisOrigen) {
        log.info("Fetching brands from country: {}", paisOrigen);
        List<Marca> marcas = marcaRepository.findByPaisOrigen(paisOrigen);
        return marcaMapper.toDtoList(marcas);
    }

    @Override
    public MarcaDTO create(MarcaDTO marcaDTO) {
        log.info("Creating new brand: {}", marcaDTO.nombre());
        
        if (marcaRepository.existsByNombre(marcaDTO.nombre())) {
            throw new ResourceAlreadyExistsException("Marca", "nombre", marcaDTO.nombre());
        }
        
        Marca marca = marcaMapper.toEntity(marcaDTO);
        
        // Set default values if not provided
        if (marca.getActivo() == null) {
            marca.setActivo(true);
        }
        
        Marca savedMarca = marcaRepository.save(marca);
        log.info("Brand created with id: {}", savedMarca.getId());
        
        return marcaMapper.toDto(savedMarca);
    }

    @Override
    public MarcaDTO update(Long id, MarcaDTO marcaDTO) {
        log.info("Updating brand with id: {}", id);
        
        Marca existingMarca = marcaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Marca", "id", id));
        
        // Check if name is being changed and if the new name already exists
        if (!existingMarca.getNombre().equals(marcaDTO.nombre()) && 
                marcaRepository.existsByNombre(marcaDTO.nombre())) {
            throw new ResourceAlreadyExistsException("Marca", "nombre", marcaDTO.nombre());
        }
        
        marcaMapper.updateEntityFromDto(marcaDTO, existingMarca);
        
        Marca updatedMarca = marcaRepository.save(existingMarca);
        log.info("Brand updated: {}", updatedMarca.getId());
        
        return marcaMapper.toDto(updatedMarca);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting brand with id: {}", id);
        
        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Marca", "id", id));
        
        // Check if the brand has associated products
        if (!marca.getProductos().isEmpty()) {
            log.warn("Cannot delete brand with id: {} because it has associated products", id);
            // Instead of deleting, mark as inactive
            marca.setActivo(false);
            marcaRepository.save(marca);
            log.info("Brand marked as inactive: {}", id);
        } else {
            marcaRepository.delete(marca);
            log.info("Brand deleted: {}", id);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByNombre(String nombre) {
        return marcaRepository.existsByNombre(nombre);
    }
}