package edu.cibertec.appstorecomputer.mapper;

import edu.cibertec.appstorecomputer.dto.MarcaDTO;
import edu.cibertec.appstorecomputer.model.Marca;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface MarcaMapper {

    MarcaDTO toDto(Marca marca);

    List<MarcaDTO> toDtoList(List<Marca> marcas);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productos", ignore = true)
    Marca toEntity(MarcaDTO marcaDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productos", ignore = true)
    void updateEntityFromDto(MarcaDTO marcaDTO, @MappingTarget Marca marca);
}