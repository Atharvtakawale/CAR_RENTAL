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

    // ================= SAVE =================
    public List<PriceMasterDto> savePriceMaster(@Valid List<PriceMasterDto> priceMasterDtos) {

        if (priceMasterDtos == null || priceMasterDtos.isEmpty()) {
            throw new IllegalArgumentException("PriceMaster list cannot be empty");
        }

        List<PriceMaster> entityList = new ArrayList<>();

        for (PriceMasterDto dto : priceMasterDtos) {

            PriceMaster price = new PriceMaster();

            price.setVehicleType(dto.getVehicleType());
            price.setBasePricePerDay(dto.getBasePricePerDay());
            price.setPricePerHour(dto.getPricePerHour());
            price.setWeekendPrice(dto.getWeekendPrice());
            price.setHolidayPrice(dto.getHolidayPrice());
            price.setSecurityDeposit(dto.getSecurityDeposit());
            price.setIsActive(dto.getIsActive());
            price.setCreatedDate(dto.getCreatedDate());
            price.setUpdatedDate(dto.getUpdatedDate());

            if (dto.getLocationId() != null) {
                LocationMaster location = locationMasterDao.findById(dto.getLocationId())
                        .orElseThrow(() -> new RuntimeException("Location not found"));
                price.setLocationMaster(location);
            } else {
                throw new RuntimeException("Location ID is required");
            }

            entityList.add(price);
        }

        List<PriceMaster> savedList = priceMasterDao.saveAll(entityList);

        List<PriceMasterDto> result = new ArrayList<>();

        for (PriceMaster saved : savedList) {

            PriceMasterDto dto = new PriceMasterDto();

            dto.setPriceId(saved.getPriceId());
            dto.setVehicleType(saved.getVehicleType());
            dto.setBasePricePerDay(saved.getBasePricePerDay());
            dto.setPricePerHour(saved.getPricePerHour());
            dto.setWeekendPrice(saved.getWeekendPrice());
            dto.setHolidayPrice(saved.getHolidayPrice());
            dto.setSecurityDeposit(saved.getSecurityDeposit());
            dto.setIsActive(saved.getIsActive());
            dto.setCreatedDate(saved.getCreatedDate());
            dto.setUpdatedDate(saved.getUpdatedDate());

            if (saved.getLocationMaster() != null) {
                dto.setLocationId(saved.getLocationMaster().getLocationId());
            }

            result.add(dto);
        }

        return result;
    }

    // ================= COUNT =================
    public long getPriceMasterDataCount(@Valid PriceMasterQuerydto masterQueryDto) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<PriceMaster> root = cq.from(PriceMaster.class);

        cq.select(cb.countDistinct(root));

        List<Predicate> predicates = new ArrayList<>();

        if (masterQueryDto.getVehicleType() != null) {
            predicates.add(cb.equal(root.get("vehicleType"), masterQueryDto.getVehicleType()));
        }

        if (masterQueryDto.getIsActive() != null) {
            predicates.add(cb.equal(root.get("isActive"), masterQueryDto.getIsActive()));
        }

        if (masterQueryDto.getLocationId() != null) {
            predicates.add(cb.equal(root.get("locationMaster").get("location_id"),
                    masterQueryDto.getLocationId()));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq).getSingleResult();
    }

    // ================= LIST =================
    public List<PriceMasterDto> getPriceMasterDataList(@Valid PriceMasterQuerydto masterQueryDto) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PriceMaster> cq = cb.createQuery(PriceMaster.class);

        Root<PriceMaster> root = cq.from(PriceMaster.class);

        List<Predicate> predicates = new ArrayList<>();

        if (masterQueryDto.getPriceId() != null) {
            predicates.add(cb.equal(root.get("priceId"), masterQueryDto.getPriceId()));
        }

        if (masterQueryDto.getVehicleType() != null) {
            predicates.add(cb.equal(root.get("vehicleType"), masterQueryDto.getVehicleType()));
        }

        if (masterQueryDto.getIsActive() != null) {
            predicates.add(cb.equal(root.get("isActive"), masterQueryDto.getIsActive()));
        }

        if (masterQueryDto.getLocationId() != null) {
            predicates.add(cb.equal(root.get("locationMaster").get("location_id"),
                    masterQueryDto.getLocationId()));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        var query = entityManager.createQuery(cq);

        List<PriceMaster> resultList = query.getResultList();

        List<PriceMasterDto> dtoList = new ArrayList<>();

        for (PriceMaster price : resultList) {

            PriceMasterDto dto = new PriceMasterDto();

            dto.setPriceId(price.getPriceId());
            dto.setVehicleType(price.getVehicleType());
            dto.setBasePricePerDay(price.getBasePricePerDay());
            dto.setPricePerHour(price.getPricePerHour());
            dto.setWeekendPrice(price.getWeekendPrice());
            dto.setHolidayPrice(price.getHolidayPrice());
            dto.setSecurityDeposit(price.getSecurityDeposit());
            dto.setIsActive(price.getIsActive());
            dto.setCreatedDate(price.getCreatedDate());
            dto.setUpdatedDate(price.getUpdatedDate());

            if (price.getLocationMaster() != null) {
                dto.setLocationId(price.getLocationMaster().getLocationId());
            }

            dtoList.add(dto);
        }

        return dtoList;
    }

    // ================= UPDATE =================
    public List<PriceMasterDto> updatePriceMaster(@Valid List<PriceMasterDto> priceMasterDtos) {

        List<PriceMasterDto> updatedList = new ArrayList<>();

        for (PriceMasterDto dto : priceMasterDtos) {

            if (dto.getPriceId() == null) {
                throw new RuntimeException("Price ID is required");
            }

            PriceMaster price = priceMasterDao.findById(dto.getPriceId())
                    .orElseThrow(() -> new RuntimeException("Price not found"));

            if (dto.getVehicleType() != null) {
                price.setVehicleType(dto.getVehicleType());
            }

            if (dto.getBasePricePerDay() != null) {
                price.setBasePricePerDay(dto.getBasePricePerDay());
            }

            if (dto.getPricePerHour() != null) {
                price.setPricePerHour(dto.getPricePerHour());
            }

            if (dto.getWeekendPrice() != null) {
                price.setWeekendPrice(dto.getWeekendPrice());
            }

            if (dto.getHolidayPrice() != null) {
                price.setHolidayPrice(dto.getHolidayPrice());
            }

            if (dto.getSecurityDeposit() != null) {
                price.setSecurityDeposit(dto.getSecurityDeposit());
            }

            if (dto.getIsActive() != null) {
                price.setIsActive(dto.getIsActive());
            }

            if (dto.getUpdatedDate() != null) {
                price.setUpdatedDate(dto.getUpdatedDate());
            }

            if (dto.getLocationId() != null) {
                LocationMaster location = locationMasterDao.findById(dto.getLocationId())
                        .orElseThrow(() -> new RuntimeException("Location not found"));
                price.setLocationMaster(location);
            }

            PriceMaster saved = priceMasterDao.save(price);

            PriceMasterDto updatedDto = new PriceMasterDto();

            updatedDto.setPriceId(saved.getPriceId());
            updatedDto.setVehicleType(saved.getVehicleType());
            updatedDto.setBasePricePerDay(saved.getBasePricePerDay());
            updatedDto.setPricePerHour(saved.getPricePerHour());
            updatedDto.setWeekendPrice(saved.getWeekendPrice());
            updatedDto.setHolidayPrice(saved.getHolidayPrice());
            updatedDto.setSecurityDeposit(saved.getSecurityDeposit());
            updatedDto.setIsActive(saved.getIsActive());
            updatedDto.setCreatedDate(saved.getCreatedDate());
            updatedDto.setUpdatedDate(saved.getUpdatedDate());

            if (saved.getLocationMaster() != null) {
                updatedDto.setLocationId(saved.getLocationMaster().getLocationId());
            }

            updatedList.add(updatedDto);
        }

        return updatedList;
    }
}
