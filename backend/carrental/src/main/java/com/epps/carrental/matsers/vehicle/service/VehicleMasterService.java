package com.epps.carrental.matsers.vehicle.service;

import java.util.ArrayList;
import java.util.List;

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

    public List<VehicleMasterDto> saveVehicleMaster(@Valid List<VehicleMasterDto> vehicleDtos) {

        if (vehicleDtos == null || vehicleDtos.isEmpty()) {
            throw new IllegalArgumentException("Vehicle list cannot be empty");
        }

        List<VehicleMaster> entityList = vehicleDtos.stream()
                .filter(dto -> dto != null)
                .map(this::mapToEntity)
                .toList();

        List<VehicleMaster> savedList = vehicleMasterDao.saveAll(entityList);

        return savedList.stream()
                .map(this::mapToDto)
                .toList();
    }

    public long getVehicleMasterDataCount(@Valid VehicleMasterQueryDto queryDto) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<VehicleMaster> root = cq.from(VehicleMaster.class);

        cq.select(cb.count(root));

        List<Predicate> predicates = new ArrayList<>();

        if (queryDto.getModel() != null && !queryDto.getModel().isEmpty()) {
            predicates.add(cb.like(root.get("model"), "%" + queryDto.getModel() + "%"));
        }

        if (queryDto.getVehicleType() != null) {
            predicates.add(cb.equal(root.get("vehicleType"), queryDto.getVehicleType()));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq).getSingleResult();
    }

    public List<VehicleMasterDto> getVehicleMasterDataList(@Valid VehicleMasterQueryDto queryDto) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<VehicleMaster> cq = cb.createQuery(VehicleMaster.class);

        Root<VehicleMaster> root = cq.from(VehicleMaster.class);

        List<Predicate> predicates = new ArrayList<>();

        if (queryDto.getVehicleId() != null) {
            predicates.add(cb.equal(root.get("vehicleId"), queryDto.getVehicleId()));
        }

        if (queryDto.getModel() != null && !queryDto.getModel().isEmpty()) {
            predicates.add(cb.like(root.get("model"), "%" + queryDto.getModel() + "%"));
        }

        if (queryDto.getVehicleType() != null) {
            predicates.add(cb.equal(root.get("vehicleType"), queryDto.getVehicleType()));
        }

        cq.where(predicates.toArray(new Predicate[0]));

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

        return query.getResultList()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public List<VehicleMasterDto> updateVehicleMaster(@Valid List<VehicleMasterDto> vehicleDtos) {

        if (vehicleDtos == null || vehicleDtos.isEmpty()) {
            throw new IllegalArgumentException("Vehicle list cannot be empty");
        }

        List<VehicleMasterDto> updatedList = new ArrayList<>();

        for (VehicleMasterDto dto : vehicleDtos) {

            if (dto.getVehicleId() == null) {
                throw new RuntimeException("Vehicle ID is required for update");
            }

            VehicleMaster vehicle = vehicleMasterDao.findById(dto.getVehicleId())
                    .orElseThrow(() -> new RuntimeException(
                            "Vehicle not found with id: " + dto.getVehicleId()));

            if (dto.getModel() != null) vehicle.setModel(dto.getModel());
            if (dto.getVehicleType() != null) vehicle.setVehicleType(dto.getVehicleType());
            if (dto.getFuelType() != null) vehicle.setFuelType(dto.getFuelType());
            if (dto.getSeatingCapacity() != null) vehicle.setSeatingCapacity(dto.getSeatingCapacity());
            if (dto.getCreatedDate() != null) vehicle.setCreatedDate(dto.getCreatedDate());
            if (dto.getUpdatedDate() != null) vehicle.setUpdatedDate(dto.getUpdatedDate());

            VehicleMaster saved = vehicleMasterDao.save(vehicle);

            updatedList.add(mapToDto(saved));
        }

        return updatedList;
    }

    private VehicleMaster mapToEntity(VehicleMasterDto dto) {

        VehicleMaster entity = new VehicleMaster();

        entity.setVehicleId(dto.getVehicleId());
        entity.setModel(dto.getModel());
        entity.setVehicleType(dto.getVehicleType());
        entity.setFuelType(dto.getFuelType());
        entity.setSeatingCapacity(dto.getSeatingCapacity());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setUpdatedDate(dto.getUpdatedDate());

        return entity;
    }

    private VehicleMasterDto mapToDto(VehicleMaster entity) {

        VehicleMasterDto dto = new VehicleMasterDto();

        dto.setVehicleId(entity.getVehicleId());
        dto.setModel(entity.getModel());
        dto.setVehicleType(entity.getVehicleType());
        dto.setFuelType(entity.getFuelType());
        dto.setSeatingCapacity(entity.getSeatingCapacity());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedDate(entity.getUpdatedDate());

        return dto;
    }
}

