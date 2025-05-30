package vn.edu.hcmuaf.st.DACN_BookStore_2025.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.edu.hcmuaf.st.DACN_BookStore_2025.dto.RoleDTO;
import vn.edu.hcmuaf.st.DACN_BookStore_2025.entity.RoleEntity;

@Component
public class RoleConverter {
    @Autowired
    private ModelMapper modelMapper;

    public RoleEntity toEntity(RoleDTO roleDto) {
        return modelMapper.map(roleDto, RoleEntity.class);
    }

    public RoleDTO toDTO(RoleEntity roleEntity) {
        return modelMapper.map(roleEntity, RoleDTO.class);
    }
}
