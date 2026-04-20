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
import com.epps.carrental.matsers.price.dto.PriceMasterDto;
import com.epps.carrental.matsers.price.entity.PriceMaster;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
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

		List<LocationMaster> entityList = locationMasterDtos.stream().filter(dto -> dto != null).map(dto -> {

			LocationMaster location = new LocationMaster();

			BeanUtils.copyProperties(dto, location);

			if (dto.getPriceMastersDtos() != null) {

				List<PriceMaster> priceList = dto.getPriceMastersDtos().stream().map(priceDto -> {

					PriceMaster price = new PriceMaster();
					BeanUtils.copyProperties(priceDto, price);

					price.setLocationMaster(location);

					return price;
				}).toList();

				location.setPriceMasters(priceList);
			}

			return location;
		}).toList();

		List<LocationMaster> savedList = locationMasterDao.saveAll(entityList);

		// Convert back to DTO
		return savedList.stream().map(entity -> {

			LocationMasterDto dto = new LocationMasterDto();
			BeanUtils.copyProperties(entity, dto);

			if (entity.getPriceMasters() != null) {

				List<PriceMasterDto> priceDtos = entity.getPriceMasters().stream().map(price -> {

					PriceMasterDto pDto = new PriceMasterDto();
					BeanUtils.copyProperties(price, pDto);

					return pDto;
				}).toList();

				dto.setPriceMastersDtos(priceDtos);
			}

			return dto;
		}).toList();
	}	

	public long getLocationMasterDataCount(@Valid LocationMasterQueryDto masterQueryDto) {

	    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Long> cq = cb.createQuery(Long.class);

	    Root<LocationMaster> root = cq.from(LocationMaster.class);

	    cq.select(cb.countDistinct(root)); // 🔥 use distinct (important for joins)

	    List<Predicate> predicates = new ArrayList<>();

	    if (masterQueryDto.getLocation_name() != null && !masterQueryDto.getLocation_name().isEmpty()) {
	        predicates.add(cb.like(root.get("locationName"), "%" + masterQueryDto.getLocation_name() + "%"));
	    }

	    cq.where(predicates.toArray(new Predicate[0]));

	    return entityManager.createQuery(cq).getSingleResult();
	}

	public List<LocationMasterDto> getLocationMasterDataList(@Valid LocationMasterQueryDto masterQueryDto) {

	    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<LocationMaster> cq = cb.createQuery(LocationMaster.class);

	    Root<LocationMaster> root = cq.from(LocationMaster.class);

	    root.fetch("priceMasters", JoinType.LEFT);

	    List<Predicate> predicates = new ArrayList<>();

	    if (masterQueryDto.getLocation_id() != null) {
            predicates.add(cb.equal(root.get("location_id"), masterQueryDto.getLocation_id()));
        }
	    
	    if (masterQueryDto.getLocation_name() != null && !masterQueryDto.getLocation_name().isEmpty()) {
	        predicates.add(cb.like(root.get("locationName"), "%" + masterQueryDto.getLocation_name() + "%"));
	    }

	    cq.where(predicates.toArray(new Predicate[0]));

	    if (masterQueryDto.getSortBy() != null) {
	        if ("desc".equalsIgnoreCase(masterQueryDto.getSortDirection())) {
	            cq.orderBy(cb.desc(root.get(masterQueryDto.getSortBy())));
	        } else {
	            cq.orderBy(cb.asc(root.get(masterQueryDto.getSortBy())));
	        }
	    }

	    cq.distinct(true); // 🔥 VERY IMPORTANT (avoid duplicate rows)

	    var query = entityManager.createQuery(cq);

	    if (masterQueryDto.getPageNo() != null && masterQueryDto.getPageSize() != null) {
	        int pageNo = masterQueryDto.getPageNo();
	        int pageSize = masterQueryDto.getPageSize();

	        query.setFirstResult(pageNo * pageSize);
	        query.setMaxResults(pageSize);
	    }

	    List<LocationMaster> resultList = query.getResultList();

	    List<LocationMasterDto> dtoList = new ArrayList<>();

	    for (LocationMaster location : resultList) {

	        LocationMasterDto dto = new LocationMasterDto();

	        dto.setLocation_id(location.getLocation_id());
	        dto.setLocation_name(location.getLocation_name());
	        dto.setAddress_line1(location.getAddress_line1());
	        dto.setAddress_line2(location.getAddress_line2());
	        dto.setCity(location.getCity());
	        dto.setState(location.getState());
	        dto.setCountry(location.getCountry());
	        dto.setPincode(location.getPincode());
	        dto.setIs_active(location.getIs_active());
	        dto.setCreated_date(location.getCreated_date());
	        dto.setUpdated_date(location.getUpdated_date());

	        if (location.getPriceMasters() != null) {

	            List<PriceMasterDto> priceDtos = location.getPriceMasters().stream()
	                    .map(price -> {
	                        PriceMasterDto pDto = new PriceMasterDto();

	                        pDto.setPrice_id(price.getPrice_id());
	                        pDto.setVehicle_type(price.getVehicle_type());
	                        pDto.setBase_price_per_day(price.getBase_price_per_day());
	                        pDto.setPrice_per_hour(price.getPrice_per_hour());
	                        pDto.setWeekend_price(price.getWeekend_price());
	                        pDto.setHoliday_price(price.getHoliday_price());
	                        pDto.setSecurity_deposit(price.getSecurity_deposit());
	                        pDto.setIs_active(price.getIs_active());

	                        return pDto;
	                    })
	                    .toList();

	            dto.setPriceMastersDtos(priceDtos);
	        }

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

	        if (dto.getUpdated_date() != null) {
	            location.setUpdated_date(dto.getUpdated_date());
	        }

	        if (dto.getPriceMastersDtos() != null) {

	            location.getPriceMasters().clear();

	            List<PriceMaster> newPriceList = new ArrayList<>();

	            for (PriceMasterDto priceDto : dto.getPriceMastersDtos()) {

	                PriceMaster price = new PriceMaster();

	                price.setPrice_id(priceDto.getPrice_id());
	                price.setVehicle_type(priceDto.getVehicle_type());
	                price.setBase_price_per_day(priceDto.getBase_price_per_day());
	                price.setPrice_per_hour(priceDto.getPrice_per_hour());
	                price.setWeekend_price(priceDto.getWeekend_price());
	                price.setHoliday_price(priceDto.getHoliday_price());
	                price.setSecurity_deposit(priceDto.getSecurity_deposit());
	                price.setIs_active(priceDto.getIs_active());
	                price.setCreated_date(priceDto.getCreated_date());
	                price.setUpdated_date(priceDto.getUpdated_date());

	                price.setLocationMaster(location);

	                newPriceList.add(price);
	            }

	            location.setPriceMasters(newPriceList);
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

	        if (savedLocation.getPriceMasters() != null) {

	            List<PriceMasterDto> priceDtos = savedLocation.getPriceMasters().stream()
	                    .map(price -> {
	                        PriceMasterDto pDto = new PriceMasterDto();

	                        pDto.setPrice_id(price.getPrice_id());
	                        pDto.setVehicle_type(price.getVehicle_type());
	                        pDto.setBase_price_per_day(price.getBase_price_per_day());
	                        pDto.setPrice_per_hour(price.getPrice_per_hour());
	                        pDto.setWeekend_price(price.getWeekend_price());
	                        pDto.setHoliday_price(price.getHoliday_price());
	                        pDto.setSecurity_deposit(price.getSecurity_deposit());
	                        pDto.setIs_active(price.getIs_active());
	                        pDto.setCreated_date(price.getCreated_date());
	                        pDto.setUpdated_date(price.getUpdated_date());

	                        return pDto;
	                    })
	                    .toList();

	            updatedDto.setPriceMastersDtos(priceDtos);
	        }

	        updatedList.add(updatedDto);
	    }

	    return updatedList;
	}

}
