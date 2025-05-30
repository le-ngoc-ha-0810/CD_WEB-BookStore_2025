package vn.edu.hcmuaf.st.DACN_BookStore_2025.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.st.DACN_BookStore_2025.entity.UserEntity;
import vn.edu.hcmuaf.st.DACN_BookStore_2025.repository.RoleRepository;
import vn.edu.hcmuaf.st.DACN_BookStore_2025.repository.UsersRepository;

import java.util.ArrayList;
import java.util.List;
@Service
public class UserDetailServiceImp implements UserDetailsService {
    @Autowired
    private UsersRepository userRepo;
    @Autowired
    private RoleRepository roleRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepo.findByEmailIgnoreCaseAndIsEnableAndStatus(email, true, true);
        //tìm trong dtb xem user có email đó có tồn tại hay khong
        if (userEntity == null) throw new UsernameNotFoundException(email + " không tồn tại trong database");
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        //tìm tất cả role name của user
        List<String> roleNames = roleRepo.findAllByUserID(userEntity.getUserID());

        //tạo grantedAuthority với roleNames tương ứng
        for (String name : roleNames) {
            grantedAuthorities.add(new SimpleGrantedAuthority(name));
        }
        //tao doi tuong userdetail
        UserDetails userDetais = User.withUsername(userEntity.getEmail()).password(userEntity.getPassword()).authorities(grantedAuthorities).build();
        return userDetais;
    }
}