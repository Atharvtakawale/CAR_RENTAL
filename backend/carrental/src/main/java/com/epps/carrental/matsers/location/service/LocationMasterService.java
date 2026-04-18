package com.epps.carrental.matsers.location.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epps.carrental.matsers.location.dao.LocationMasterDao;
import com.epps.carrental.matsers.location.dto.LocationMasterDto;
import com.epps.carrental.matsers.location.dto.LocationMasterQueryDto;
import com.epps.carrental.matsers.location.entity.LocationMaster;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.Valid;

@Service
public class LocationMasterService {

	@Autowired
	private LocationMasterDao locationMasterDao;
	
	@Autowired
    private EntityManager entityManager;
	
	public List<LocationMasterDto> saveLocationMaster(@Valid List<LocationMasterDto> locationMasterDtos) {
		
		 if (locationMasterDtos == null || locationMasterDtos.isEmpty()) {
	            throw new IllegalArgumentException("Location list cannot be empty");
	        }

	        List<LocationMaster> list = locationMasterDtos.stream()
	                .filter(dto -> dto != null)
	                .map(dto -> {
	                	LocationMaster entity = new LocationMaster();
	                    BeanUtils.copyProperties(dto, entity);
	                    return entity;
	                })
	                .toList();

	        List<LocationMaster> savedList = locationMasterDao.saveAll(list);

	        return savedList.stream()
	                .map(entity -> {
	                	LocationMasterDto dto = new LocationMasterDto();	
	                    BeanUtils.copyProperties(entity, dto);
	                    return dto;
	                })
	                .toList();
	}

	public long getLocationMasterDataCount(@Valid LocationMasterQueryDto masterQueryDto) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<LocationMaster> root = cq.from(LocationMaster.class);

        cq.select(cb.count(root));

        Predicate predicate = cb.conjunction(); 

        if (masterQueryDto.getLocation_name() != null && !masterQueryDto.getLocation_name().isEmpty()) {
            predicate = cb.and(predicate,
                    cb.like(root.get("name"), "%" + masterQueryDto.getLocation_name() + "%"));
        }

        cq.where(predicate);

        return entityManager.createQuery(cq).getSingleResult();
	}

	public List<LocationMasterDto> getLocationMasterDataList(@Valid LocationMasterQueryDto masterQueryDto) {
		
		 CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	        CriteriaQuery<LocationMaster> cq = cb.createQuery(LocationMaster.class);

	        Root<LocationMaster> root = cq.from(LocationMaster.class);

	        List<Predicate> predicates = new ArrayList<>();

	        if (masterQueryDto.getLocation_name() != null && !masterQueryDto.getLocation_name().isEmpty()) {
	            predicates.add(cb.like(root.get("name"), "%" + masterQueryDto.getLocation_name() + "%"));
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

	        List<LocationMaster> resultList = query.getResultList();

	        List<LocationMasterDto> dtoList = new ArrayList<>();

	        for (LocationMaster locationMaster  : resultList) {
	        	LocationMasterDto dto = new LocationMasterDto();
	        	dto.setLocation_id(locationMaster.getLocation_id());
	            dto.setLocation_name(locationMaster.getLocation_name());
	            dto.setAddress_line1(locationMaster.getAddress_line1());
	            dto.setAddress_line2(locationMaster.getAddress_line2());
	            dto.setCity(locationMaster.getCity());
	            dto.setState(locationMaster.getState());	
	            dto.setCountry(locationMaster.getCountry());
	            dto.setPincode(locationMaster.getPincode());
	            dto.setIs_active(locationMaster.getIs_active());
	            dto.setCreated_date(locationMaster.getCreated_date());
	            dto.setUpdated_date(locationMaster.getUpdated_date());
	            dtoList.add(dto);
	        }

	        return dtoList;
	}

	public List<LocationMasterDto> updateLocationMaster(List<LocationMasterDto> locationDtos) {

	    List<LocationMasterDto> updatedList = new ArrayList<>();

	    for (LocationMasterDto dto : locationDtos) {

	        if (dto.getLocation_id() == null) {
	            throw new RuntimeException("Location ID is required for update");
	        }

	        LocationMaster location = locationMasterDao.findById(dto.getLocation_id())
	                .orElseThrow(() -> new RuntimeException(
	                        "Location not found with id: " + dto.getLocation_id()));

	        if (dto.getLocation_name() != null) {
	            location.setLocation_name(dto.getLocation_name());
	        }

	        if (dto.getAddress_line1() != null) {
	            location.setAddress_line1(dto.getAddress_line1());
	        }

	        if (dto.getAddress_line2() != null) {
	            location.setAddress_line2(dto.getAddress_line2());
	        }

	        if (dto.getCity() != null) {
	            location.setCity(dto.getCity());
	        }

	        if (dto.getState() != null) {
	            location.setState(dto.getState());
	        }

	        if (dto.getCountry() != null) {
	            location.setCountry(dto.getCountry());
	        }

	        if (dto.getPincode() != null) {
	            location.setPincode(dto.getPincode());
	        }

	        if (dto.getIs_active() != null) {
	            location.setIs_active(dto.getIs_active());
	        }

	        if (dto.getCreated_date() != null) {
	            location.setCreated_date(dto.getCreated_date());
	        }

	        if (dto.getUpdated_date() != null) {
	            location.setUpdated_date(dto.getUpdated_date());
	        }

	        LocationMaster savedLocation = locationMasterDao.save(location);

	        LocationMasterDto updatedDto = new LocationMasterDto();

	        updatedDto.setLocation_id(savedLocation.getLocation_id());
	        updatedDto.setLocation_name(savedLocation.getLocation_name());
	        updatedDto.setAddress_line1(savedLocation.getAddress_line1());
	        updatedDto.setAddress_line2(savedLocation.getAddress_line2());
	        updatedDto.setCity(savedLocation.getCity());
	        updatedDto.setState(savedLocation.getState());
	        updatedDto.setCountry(savedLocation.getCountry());
	        updatedDto.setPincode(savedLocation.getPincode());
	        updatedDto.setIs_active(savedLocation.getIs_active());
	        updatedDto.setCreated_date(savedLocation.getCreated_date());
	        updatedDto.setUpdated_date(savedLocation.getUpdated_date());

	        updatedList.add(updatedDto);
	    }

	    return updatedList;
	}

}
