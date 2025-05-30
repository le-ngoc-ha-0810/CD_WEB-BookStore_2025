package vn.edu.hcmuaf.st.DACN_BookStore_2025.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.edu.hcmuaf.st.DACN_BookStore_2025.dto.CartDTO;
import vn.edu.hcmuaf.st.DACN_BookStore_2025.entity.CartEntity;


@Component
public class CartConverter {
    @Autowired
    private ModelMapper modelMapper;

    public CartEntity toEntity(CartDTO cartDto) {
        return modelMapper.map(cartDto, CartEntity.class);
    }

    public CartDTO toDTO(CartEntity cartEntity) {
        return modelMapper.map(cartEntity, CartDTO.class);
    }
}
