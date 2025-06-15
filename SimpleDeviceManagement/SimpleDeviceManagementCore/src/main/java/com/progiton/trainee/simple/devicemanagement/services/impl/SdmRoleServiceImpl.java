package com.progiton.trainee.simple.devicemanagement.services.impl;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import com.progiton.trainee.simple.devicemanagement.persistent.model.SdmRoleEntity;
import com.progiton.trainee.simple.devicemanagement.persistent.repositories.SdmRoleRepository;
import com.progiton.trainee.simple.devicemanagement.services.SdmRoleService;

@Service
@RequiredArgsConstructor
public class SdmRoleServiceImpl implements SdmRoleService {

    private final SdmRoleRepository sdmRoleRepository;

    @Override
    public List<SdmRoleEntity> findAll() {
        return sdmRoleRepository.findAll();
    }

    @Override
    public Optional<SdmRoleEntity> getRoleByName(String name) {
        return sdmRoleRepository.findByName(name);
    }

    @Override
    public SdmRoleEntity save(SdmRoleEntity sdmRoleEntity) {
        return sdmRoleRepository.save(sdmRoleEntity);
    }

	@Override
	public List<SdmRoleEntity> getAllRoles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SdmRoleEntity createRole(SdmRoleEntity sdmRoleEntity) {
		// TODO Auto-generated method stub
		return null;
	}
}