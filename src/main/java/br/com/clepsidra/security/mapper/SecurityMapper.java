package br.com.clepsidra.security.mapper;

import br.com.clepsidra.domain.entity.Usuario;
import br.com.clepsidra.security.view.vo.RegisterVO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SecurityMapper {
    RegisterVO toVO(Usuario usuario);

}
