package com.telemedicine.myhealth.role.service;

import com.telemedicine.myhealth.res.Response;
import com.telemedicine.myhealth.role.entity.Role;

import java.util.List;

public interface RoleService {
    Response<Role> createRole(Role roleRequest);
    Response<Role> updateRole(Role roleRequest);
    Response<List<Role>> getAllRoles();
    Response<?> deleteRole(Long id);
}
