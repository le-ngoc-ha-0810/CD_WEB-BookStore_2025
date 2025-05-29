package vn.edu.hcmuaf.st.DACN_BookStore_2025.service;


import vn.edu.hcmuaf.st.DACN_BookStore_2025.dto.RoleDTO;

public interface IRoleService {
    public RoleDTO findRolebyName(String name);
}
