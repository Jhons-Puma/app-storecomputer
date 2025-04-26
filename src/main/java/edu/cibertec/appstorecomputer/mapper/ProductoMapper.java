package edu.cibertec.appstorecomputer.mapper;

import edu.cibertec.appstorecomputer.dto.ProductoDTO;
import edu.cibertec.appstorecomputer.model.Categoria;
import edu.cibertec.appstorecomputer.model.Marca;
import edu.cibertec.appstorecomputer.model.Producto;
import org.mapstruct.*;

import java.util.List;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    uses = {CategoriaMapper.class, MarcaMapper.class}
)
public interface ProductoMapper {

    @Mapping(target = "categoriaId", source = "categoria.id")
    @Mapping(target = "marcaId", source = "marca.id")
    @Mapping(target = "categoriaNombre", source = "categoria.nombre")
    @Mapping(target = "marcaNombre", source = "marca.nombre")
    ProductoDTO toDto(Producto producto);

    List<ProductoDTO> toDtoList(List<Producto> productos);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    @Mapping(target = "marca", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    Producto toEntity(ProductoDTO productoDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    @Mapping(target = "marca", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    void updateEntityFromDto(ProductoDTO productoDTO, @MappingTarget Producto producto);

    @AfterMapping
    default void setCategoriaAndMarca(ProductoDTO dto, @MappingTarget Producto producto) {
        if (dto.categoriaId() != null) {
            Categoria categoria = new Categoria();
            categoria.setId(dto.categoriaId());
            producto.setCategoria(categoria);
        }
        
        if (dto.marcaId() != null) {
            Marca marca = new Marca();
            marca.setId(dto.marcaId());
            producto.setMarca(marca);
        }
    }
}