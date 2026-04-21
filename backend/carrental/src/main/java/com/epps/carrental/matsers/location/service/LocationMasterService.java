package com.epps.carrental.matsers.location.service;

import java.util.ArrayList;
import java.util.List;

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

    public List<LocationMasterDto> saveLocationMaster(@Valid List<LocationMasterDto> locationDtos) {

        if (locationDtos == null || locationDtos.isEmpty()) {
            throw new IllegalArgumentException("Location list cannot be empty");
        }

        List<LocationMaster> entityList = new ArrayList<>();

        for (LocationMasterDto dto : locationDtos) {

            LocationMaster location = new LocationMaster();

            location.setLocationName(dto.getLocationName());
            location.setAddressLine1(dto.getAddressLine1());
            location.setAddressLine2(dto.getAddressLine2());
            location.setCity(dto.getCity());
            location.setState(dto.getState());
            location.setCountry(dto.getCountry());
            location.setPincode(dto.getPincode());
            location.setIsActive(dto.getIsActive());
            location.setCreatedDate(dto.getCreatedDate());
            location.setUpdatedDate(dto.getUpdatedDate());

            if (dto.getPriceMastersDtos() != null && !dto.getPriceMastersDtos().isEmpty()) {

                List<PriceMaster> priceList = new ArrayList<>();

                for (PriceMasterDto priceDto : dto.getPriceMastersDtos()) {

                    PriceMaster price = new PriceMaster();

                    price.setVehicleType(priceDto.getVehicleType());
                    price.setBasePricePerDay(priceDto.getBasePricePerDay());
                    price.setPricePerHour(priceDto.getPricePerHour());
                    price.setWeekendPrice(priceDto.getWeekendPrice());
                    price.setHolidayPrice(priceDto.getHolidayPrice());
                    price.setSecurityDeposit(priceDto.getSecurityDeposit());
                    price.setIsActive(priceDto.getIsActive());
                    price.setCreatedDate(priceDto.getCreatedDate());
                    price.setUpdatedDate(priceDto.getUpdatedDate());

                    price.setLocationMaster(location);

                    priceList.add(price);
                }

                location.setPriceMasters(priceList);
            }

            entityList.add(location);
        }

        List<LocationMaster> savedList = locationMasterDao.saveAll(entityList);

        return mapToDtoList(savedList);
    }

    public long getLocationMasterDataCount(@Valid LocationMasterQueryDto queryDto) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<LocationMaster> root = cq.from(LocationMaster.class);

        cq.select(cb.countDistinct(root));

        List<Predicate> predicates = new ArrayList<>();

        if (queryDto.getLocationName() != null && !queryDto.getLocationName().isEmpty()) {
            predicates.add(cb.like(root.get("locationName"), "%" + queryDto.getLocationName() + "%"));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq).getSingleResult();
    }

    public List<LocationMasterDto> getLocationMasterDataList(@Valid LocationMasterQueryDto queryDto) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<LocationMaster> cq = cb.createQuery(LocationMaster.class);

        Root<LocationMaster> root = cq.from(LocationMaster.class);

        root.fetch("priceMasters", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();

        if (queryDto.getLocationId() != null) {
            predicates.add(cb.equal(root.get("locationId"), queryDto.getLocationId()));
        }

        if (queryDto.getLocationName() != null && !queryDto.getLocationName().isEmpty()) {
            predicates.add(cb.like(root.get("locationName"), "%" + queryDto.getLocationName() + "%"));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        cq.distinct(true);

        if (queryDto.getSortBy() != null) {
            if ("desc".equalsIgnoreCase(queryDto.getSortDirection())) {
                cq.orderBy(cb.desc(root.get(queryDto.getSortBy())));
            } else {
                cq.orderBy(cb.asc(root.get(queryDto.getSortBy())));
            }
        }

        var query = entityManager.createQuery(cq);

        if (queryDto.getPageNo() != null && queryDto.getPageSize() != null) {
            query.setFirstResult(queryDto.getPageNo() * queryDto.getPageSize());
            query.setMaxResults(queryDto.getPageSize());
        }

        return mapToDtoList(query.getResultList());
    }

    public List<LocationMasterDto> updateLocationMaster(List<LocationMasterDto> locationDtos) {

        List<LocationMasterDto> updatedList = new ArrayList<>();

        for (LocationMasterDto dto : locationDtos) {

            if (dto.getLocationId() == null) {
                throw new RuntimeException("Location ID is required");
            }

            LocationMaster location = locationMasterDao.findById(dto.getLocationId())
                    .orElseThrow(() -> new RuntimeException("Location not found"));

            if (dto.getLocationName() != null) location.setLocationName(dto.getLocationName());
            if (dto.getAddressLine1() != null) location.setAddressLine1(dto.getAddressLine1());
            if (dto.getAddressLine2() != null) location.setAddressLine2(dto.getAddressLine2());
            if (dto.getCity() != null) location.setCity(dto.getCity());
            if (dto.getState() != null) location.setState(dto.getState());
            if (dto.getCountry() != null) location.setCountry(dto.getCountry());
            if (dto.getPincode() != null) location.setPincode(dto.getPincode());
            if (dto.getIsActive() != null) location.setIsActive(dto.getIsActive());
            if (dto.getUpdatedDate() != null) location.setUpdatedDate(dto.getUpdatedDate());

            if (dto.getPriceMastersDtos() != null) {

                location.getPriceMasters().clear();

                List<PriceMaster> newList = new ArrayList<>();

                for (PriceMasterDto priceDto : dto.getPriceMastersDtos()) {

                    PriceMaster price = new PriceMaster();

                    price.setPriceId(priceDto.getPriceId());
                    price.setVehicleType(priceDto.getVehicleType());
                    price.setBasePricePerDay(priceDto.getBasePricePerDay());
                    price.setPricePerHour(priceDto.getPricePerHour());
                    price.setWeekendPrice(priceDto.getWeekendPrice());
                    price.setHolidayPrice(priceDto.getHolidayPrice());
                    price.setSecurityDeposit(priceDto.getSecurityDeposit());
                    price.setIsActive(priceDto.getIsActive());
                    price.setCreatedDate(priceDto.getCreatedDate());
                    price.setUpdatedDate(priceDto.getUpdatedDate());

                    price.setLocationMaster(location);

                    newList.add(price);
                }

                location.setPriceMasters(newList);
            }

            LocationMaster saved = locationMasterDao.save(location);

            updatedList.add(mapToDto(saved));
        }

        return updatedList;
    }

    // ================= COMMON MAPPER =================

    private List<LocationMasterDto> mapToDtoList(List<LocationMaster> list) {
        return list.stream().map(this::mapToDto).toList();
    }

    private LocationMasterDto mapToDto(LocationMaster location) {

        LocationMasterDto dto = new LocationMasterDto();

        dto.setLocationId(location.getLocationId());
        dto.setLocationName(location.getLocationName());
        dto.setAddressLine1(location.getAddressLine1());
        dto.setAddressLine2(location.getAddressLine2());
        dto.setCity(location.getCity());
        dto.setState(location.getState());
        dto.setCountry(location.getCountry());
        dto.setPincode(location.getPincode());
        dto.setIsActive(location.getIsActive());
        dto.setCreatedDate(location.getCreatedDate());
        dto.setUpdatedDate(location.getUpdatedDate());

        if (location.getPriceMasters() != null) {

            List<PriceMasterDto> priceDtos = location.getPriceMasters().stream().map(price -> {

                PriceMasterDto p = new PriceMasterDto();

                p.setPriceId(price.getPriceId());
                p.setVehicleType(price.getVehicleType());
                p.setBasePricePerDay(price.getBasePricePerDay());
                p.setPricePerHour(price.getPricePerHour());
                p.setWeekendPrice(price.getWeekendPrice());
                p.setHolidayPrice(price.getHolidayPrice());
                p.setSecurityDeposit(price.getSecurityDeposit());
                p.setIsActive(price.getIsActive());
                p.setCreatedDate(price.getCreatedDate());
                p.setUpdatedDate(price.getUpdatedDate());

                return p;

            }).toList();

            dto.setPriceMastersDtos(priceDtos);
        }

        return dto;
    }
}

