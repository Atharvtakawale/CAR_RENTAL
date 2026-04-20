package com.epps.carrental.matsers.vehicle.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epps.carrental.matsers.vehicle.dao.VehicleMasterDao;
import com.epps.carrental.matsers.vehicle.dto.VehicleMasterDto;
import com.epps.carrental.matsers.vehicle.dto.VehicleMasterQueryDto;
import com.epps.carrental.matsers.vehicle.entity.VehicleMaster;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.Valid;	

@Service
public class VehicleMasterService {
	
	@Autowired
	private VehicleMasterDao vehicleMasterDao;

	@Autowired
    private EntityManager entityManager;
	
	public List<VehicleMasterDto> saveVehicleMaster(@Valid List<VehicleMasterDto> vehicleMasterDtos) {
		 if (vehicleMasterDtos == null || vehicleMasterDtos.isEmpty()) {
	            throw new IllegalArgumentException("User list cannot be empty");
	        }

	        List<VehicleMaster> list = vehicleMasterDtos.stream()
	                .filter(dto -> dto != null)
	                .map(dto -> {
	                    VehicleMaster entity = new VehicleMaster();
	                    BeanUtils.copyProperties(dto, entity);
	                    return entity;
	                })
	                .toList();

	        List<VehicleMaster> savedList = vehicleMasterDao.saveAll(list);

	        return savedList.stream()
	                .map(entity -> {
	                    VehicleMasterDto dto = new VehicleMasterDto();	
	                    BeanUtils.copyProperties(entity, dto);
	                    return dto;
	                })
	                .toList();
	}

	public long getVehicleMasterDataCount(@Valid VehicleMasterQueryDto masterQueryDto) {
		 CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

	        Root<VehicleMaster> root = cq.from(VehicleMaster.class);

	        cq.select(cb.count(root));

	        Predicate predicate = cb.conjunction(); 

	        if (masterQueryDto.getModel() != null && !masterQueryDto.getModel().isEmpty()) {
	            predicate = cb.and(predicate,
	                    cb.like(root.get("model"), "%" + masterQueryDto.getModel() + "%"));
	        }

	        cq.where(predicate);

	        return entityManager.createQuery(cq).getSingleResult();
	}

	public List<VehicleMasterDto> getVehicleMasterDataList(@Valid VehicleMasterQueryDto masterQueryDto) {
		 CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	        CriteriaQuery<VehicleMaster> cq = cb.createQuery(VehicleMaster.class);

	        Root<VehicleMaster> root = cq.from(VehicleMaster.class);

	        List<Predicate> predicates = new ArrayList<>();

	        if (masterQueryDto.getVehicle_id() != null) {
	            predicates.add(cb.equal(root.get("vehicle_id"), masterQueryDto.getVehicle_id()));
	        }
	        
	        if (masterQueryDto.getModel() != null && !masterQueryDto.getModel().isEmpty()) {
	            predicates.add(cb.like(root.get("model"), "%" + masterQueryDto.getModel() + "%"));
	        }


	        cq.where(predicates.toArray(new Predicate[0]));

	        if (masterQueryDto.getSortBy() != null) {
	            if ("desc".equalsIgnoreCase(masterQueryDto.getSortDirection())) {
	                cq.orderBy(cb.desc(root.get(masterQueryDto.getSortBy())));
	            } else {
	                cq.orderBy(cb.asc(root.get(masterQueryDto.getSortBy())));
	            }
	        }

	        var query = entityManager.createQuery(cq);

	        if (masterQueryDto.getPageNo() != null && masterQueryDto.getPageSize() != null) {
	            int pageNo = masterQueryDto.getPageNo();
	            int pageSize = masterQueryDto.getPageSize();

	            query.setFirstResult(pageNo * pageSize); 
	            query.setMaxResults(pageSize);           
	        }

	        List<VehicleMaster> resultList = query.getResultList();

	        List<VehicleMasterDto> dtoList = new ArrayList<>();

	        for (VehicleMaster vehicle : resultList) {
	        	VehicleMasterDto dto = new VehicleMasterDto();
	        	dto.setFuel_TYPE(vehicle.getFuel_TYPE());
	        	dto.setModel(vehicle.getModel());
	        	dto.setType(vehicle.getType());
	        	dto.setVehicle_id(vehicle.getVehicle_id());
	        	dto.setSeating_capacity(vehicle.getSeating_capacity());
	        	dto.setCreated_date(vehicle.getCreated_date());
	        	dto.setUpdated_date(vehicle.getUpdated_date());
	            dtoList.add(dto);
	        }

	        return dtoList;
	}

	public List<VehicleMasterDto> updateVehicleMaster(@Valid List<VehicleMasterDto> vehicleMasterDtos) {
		List<VehicleMasterDto> updatedList = new ArrayList<>();

	    for (VehicleMasterDto dto : vehicleMasterDtos) {

	        if (dto.getVehicle_id() == null) {
	            throw new RuntimeException("User ID is required for update");
	        }

	        VehicleMaster vehicle = vehicleMasterDao.findById(dto.getVehicle_id())
	                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + dto.getVehicle_id()));

	        if (dto.getModel() != null) {
	        	vehicle.setModel(dto.getModel());
	        }

	        if (dto.getVehicle_id() != null) {
	        	vehicle.setVehicle_id(dto.getVehicle_id());
	        }
	        
	        if (dto.getCreated_date() != null) {
	        	vehicle.setCreated_date(dto.getCreated_date());
	        }
	        
	        if (dto.getUpdated_date() != null) {
	        	vehicle.setUpdated_date(dto.getUpdated_date());
	        }
	        
	        if (dto.getFuel_TYPE() != null) {
	        	vehicle.setFuel_TYPE(dto.getFuel_TYPE());
	        }
	        
	        if (dto.getType() != null) {
	        	vehicle.setType(dto.getType());
	        }
	        
	        if (dto.getSeating_capacity() != null) {
	        	vehicle.setSeating_capacity(dto.getSeating_capacity());
	        }
	        

	        VehicleMaster savedUser = vehicleMasterDao.save(vehicle);

	        VehicleMasterDto updatedDto = new VehicleMasterDto();
	        updatedDto.setType(savedUser.getType());
	        updatedDto.setVehicle_id(savedUser.getVehicle_id());
	        updatedDto.setModel(savedUser.getModel());
	        updatedDto.setFuel_TYPE(savedUser.getFuel_TYPE());
	        updatedDto.setCreated_date(savedUser.getCreated_date());
	        updatedDto.setUpdated_date(savedUser.getUpdated_date());
	        updatedDto.setSeating_capacity(savedUser.getSeating_capacity());

	        updatedList.add(updatedDto);
	    }

	    return updatedList;
	}

	
}
