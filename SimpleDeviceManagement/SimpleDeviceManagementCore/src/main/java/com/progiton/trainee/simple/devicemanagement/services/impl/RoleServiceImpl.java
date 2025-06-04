package com.progiton.trainee.simple.devicemanagement.services.impl;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import com.progiton.trainee.simple.devicemanagement.persistent.model.RoleEntity;
import com.progiton.trainee.simple.devicemanagement.persistent.repositories.RoleRepository;
import com.progiton.trainee.simple.devicemanagement.services.RoleService;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<RoleEntity> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<RoleEntity> getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public RoleEntity save(RoleEntity roleEntity) {
        return roleRepository.save(roleEntity);
    }

	@Override
	public List<RoleEntity> getAllRoles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RoleEntity createRole(RoleEntity roleEntity) {
		// TODO Auto-generated method stub
		return null;
	}
}