package edu.cibertec.appstorecomputer.service;

import edu.cibertec.appstorecomputer.dto.MarcaDTO;

import java.util.List;

/**
 * Service interface for managing brands
 */
public interface MarcaService {

    /**
     * Get all brands
     * @return List of all brands
     */
    List<MarcaDTO> findAll();

    /**
     * Get all active brands
     * @return List of active brands
     */
    List<MarcaDTO> findAllActive();

    /**
     * Get a brand by ID
     * @param id Brand ID
     * @return Brand with the given ID
     */
    MarcaDTO findById(Long id);

    /**
     * Get a brand by name
     * @param nombre Brand name
     * @return Brand with the given name
     */
    MarcaDTO findByNombre(String nombre);

    /**
     * Get brands by country of origin
     * @param paisOrigen Country of origin
     * @return List of brands from the given country
     */
    List<MarcaDTO> findByPaisOrigen(String paisOrigen);

    /**
     * Create a new brand
     * @param marcaDTO Brand data
     * @return Created brand
     */
    MarcaDTO create(MarcaDTO marcaDTO);

    /**
     * Update an existing brand
     * @param id Brand ID
     * @param marcaDTO Brand data
     * @return Updated brand
     */
    MarcaDTO update(Long id, MarcaDTO marcaDTO);

    /**
     * Delete a brand by ID
     * @param id Brand ID
     */
    void delete(Long id);

    /**
     * Check if a brand exists by name
     * @param nombre Brand name
     * @return true if exists, false otherwise
     */
    boolean existsByNombre(String nombre);
}