package com.epps.carrental.matsers.price.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epps.carrental.matsers.location.dao.LocationMasterDao;
import com.epps.carrental.matsers.location.entity.LocationMaster;
import com.epps.carrental.matsers.price.dao.PriceMasterDao;
import com.epps.carrental.matsers.price.dto.PriceMasterDto;
import com.epps.carrental.matsers.price.dto.PriceMasterQuerydto;
import com.epps.carrental.matsers.price.entity.PriceMaster;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.Valid;

@Service
public class PriceMasterService {
	
	@Autowired
	private PriceMasterDao priceMasterDao;

	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private LocationMasterDao locationMasterDao;

	public List<PriceMasterDto> savePriceMaster(@Valid List<PriceMasterDto> priceMasterDtos) {

	    if (priceMasterDtos == null || priceMasterDtos.isEmpty()) {
	        throw new IllegalArgumentException("PriceMaster list cannot be empty");
	    }

	    List<PriceMaster> entityList = new ArrayList<>();

	    for (PriceMasterDto dto : priceMasterDtos) {

	        PriceMaster price = new PriceMaster();

	        price.setVehicle_type(dto.getVehicle_type());
	        price.setBase_price_per_day(dto.getBase_price_per_day());
	        price.setPrice_per_hour(dto.getPrice_per_hour());
	        price.setWeekend_price(dto.getWeekend_price());
	        price.setHoliday_price(dto.getHoliday_price());
	        price.setSecurity_deposit(dto.getSecurity_deposit());
	        price.setIs_active(dto.getIs_active());
	        price.setCreated_date(dto.getCreated_date());
	        price.setUpdated_date(dto.getUpdated_date());

	        if (dto.getLocation_id() != null) {

	            LocationMaster location = locationMasterDao.findById(dto.getLocation_id())
	                    .orElseThrow(() -> new RuntimeException(
	                            "Location not found with id: " + dto.getLocation_id()));

	            price.setLocationMaster(location);
	        } else {
	            throw new RuntimeException("Location ID is required for PriceMaster");
	        }

	        entityList.add(price);
	    }

	    List<PriceMaster> savedList = priceMasterDao.saveAll(entityList);

	    List<PriceMasterDto> result = new ArrayList<>();

	    for (PriceMaster saved : savedList) {

	        PriceMasterDto dto = new PriceMasterDto();
	
	        dto.setPrice_id(saved.getPrice_id());
	        dto.setVehicle_type(saved.getVehicle_type());
	        dto.setBase_price_per_day(saved.getBase_price_per_day());
	        dto.setPrice_per_hour(saved.getPrice_per_hour());
	        dto.setWeekend_price(saved.getWeekend_price());
	        dto.setHoliday_price(saved.getHoliday_price());
	        dto.setSecurity_deposit(saved.getSecurity_deposit());
	        dto.setIs_active(saved.getIs_active());
	        dto.setCreated_date(saved.getCreated_date());
	        dto.setUpdated_date(saved.getUpdated_date());

	        if (saved.getLocationMaster() != null) {
	            dto.setLocation_id(saved.getLocationMaster().getLocation_id());
	        }

	        result.add(dto);
	    }

	    return result;
	}

	public long getPriceMasterDataCount(@Valid PriceMasterQuerydto masterQueryDto) {

	    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Long> cq = cb.createQuery(Long.class);

	    Root<PriceMaster> root = cq.from(PriceMaster.class);	

	    cq.select(cb.countDistinct(root));

	    List<Predicate> predicates = new ArrayList<>();

	    if (masterQueryDto.getVehicle_type() != null) {
	        predicates.add(cb.equal(root.get("vehicle_type"), masterQueryDto.getVehicle_type()));
	    }

	    if (masterQueryDto.getIs_active() != null) {
	        predicates.add(cb.equal(root.get("is_active"), masterQueryDto.getIs_active()));
	    }

	    if (masterQueryDto.getLocation_id() != null) {
	        predicates.add(cb.equal(root.get("locationMaster").get("location_id"),
	                masterQueryDto.getLocation_id()));
	    }

	    cq.where(predicates.toArray(new Predicate[0]));

	    return entityManager.createQuery(cq).getSingleResult();
	}

	public List<PriceMasterDto> getPriceMasterDataList(@Valid PriceMasterQuerydto masterQueryDto) {

	    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<PriceMaster> cq = cb.createQuery(PriceMaster.class);

	    Root<PriceMaster> root = cq.from(PriceMaster.class);

	    List<Predicate> predicates = new ArrayList<>();

	    if (masterQueryDto.getPrice_id() != null) {
            predicates.add(cb.equal(root.get("price_id"), masterQueryDto.getPrice_id()));
        }
	    
	    if (masterQueryDto.getVehicle_type() != null) {
	        predicates.add(cb.equal(root.get("vehicle_type"), masterQueryDto.getVehicle_type()));
	    }

	    if (masterQueryDto.getIs_active() != null) {
	        predicates.add(cb.equal(root.get("is_active"), masterQueryDto.getIs_active()));
	    }

	    if (masterQueryDto.getLocation_id() != null) {
	        predicates.add(cb.equal(root.get("locationMaster").get("location_id"),
	                masterQueryDto.getLocation_id()));
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

	    List<PriceMaster> resultList = query.getResultList();

	    List<PriceMasterDto> dtoList = new ArrayList<>();

	    for (PriceMaster price : resultList) {

	        PriceMasterDto dto = new PriceMasterDto();

	        dto.setPrice_id(price.getPrice_id());
	        dto.setVehicle_type(price.getVehicle_type());
	        dto.setBase_price_per_day(price.getBase_price_per_day());
	        dto.setPrice_per_hour(price.getPrice_per_hour());
	        dto.setWeekend_price(price.getWeekend_price());
	        dto.setHoliday_price(price.getHoliday_price());
	        dto.setSecurity_deposit(price.getSecurity_deposit());
	        dto.setIs_active(price.getIs_active());
	        dto.setCreated_date(price.getCreated_date());
	        dto.setUpdated_date(price.getUpdated_date());

	        if (price.getLocationMaster() != null) {
	            dto.setLocation_id(price.getLocationMaster().getLocation_id());
	        }

	        dtoList.add(dto);
	    }

	    return dtoList;
	}

	public List<PriceMasterDto> updatePriceMaster(@Valid List<PriceMasterDto> priceMasterDtos) {

	    if (priceMasterDtos == null || priceMasterDtos.isEmpty()) {
	        throw new IllegalArgumentException("PriceMaster list cannot be empty");
	    }

	    List<PriceMasterDto> updatedList = new ArrayList<>();

	    for (PriceMasterDto dto : priceMasterDtos) {

	        if (dto.getPrice_id() == null) {
	            throw new RuntimeException("Price ID is required for update");
	        }

	        PriceMaster price = priceMasterDao.findById(dto.getPrice_id())
	                .orElseThrow(() -> new RuntimeException(
	                        "PriceMaster not found with id: " + dto.getPrice_id()));

	        if (dto.getVehicle_type() != null) {
	            price.setVehicle_type(dto.getVehicle_type());
	        }

	        if (dto.getBase_price_per_day() != null) {
	            price.setBase_price_per_day(dto.getBase_price_per_day());
	        }

	        if (dto.getPrice_per_hour() != null) {
	            price.setPrice_per_hour(dto.getPrice_per_hour());
	        }

	        if (dto.getWeekend_price() != null) {
	            price.setWeekend_price(dto.getWeekend_price());
	        }

	        if (dto.getHoliday_price() != null) {
	            price.setHoliday_price(dto.getHoliday_price());
	        }

	        if (dto.getSecurity_deposit() != null) {
	            price.setSecurity_deposit(dto.getSecurity_deposit());
	        }

	        if (dto.getIs_active() != null) {
	            price.setIs_active(dto.getIs_active());
	        }

	        if (dto.getUpdated_date() != null) {
	            price.setUpdated_date(dto.getUpdated_date());
	        }

	        if (dto.getLocation_id() != null) {

	            LocationMaster location = locationMasterDao.findById(dto.getLocation_id())
	                    .orElseThrow(() -> new RuntimeException(
	                            "Location not found with id: " + dto.getLocation_id()));

	            price.setLocationMaster(location);
	        }

	        PriceMaster saved = priceMasterDao.save(price);

	        PriceMasterDto updatedDto = new PriceMasterDto();

	        updatedDto.setPrice_id(saved.getPrice_id());
	        updatedDto.setVehicle_type(saved.getVehicle_type());
	        updatedDto.setBase_price_per_day(saved.getBase_price_per_day());
	        updatedDto.setPrice_per_hour(saved.getPrice_per_hour());
	        updatedDto.setWeekend_price(saved.getWeekend_price());
	        updatedDto.setHoliday_price(saved.getHoliday_price());
	        updatedDto.setSecurity_deposit(saved.getSecurity_deposit());
	        updatedDto.setIs_active(saved.getIs_active());
	        updatedDto.setCreated_date(saved.getCreated_date());
	        updatedDto.setUpdated_date(saved.getUpdated_date());

	        if (saved.getLocationMaster() != null) {
	            updatedDto.setLocation_id(saved.getLocationMaster().getLocation_id());
	        }

	        updatedList.add(updatedDto);
	    }

	    return updatedList;
	}

}
