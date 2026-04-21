package com.epps.carrental.matsers.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epps.carrental.matsers.user.dao.UserMasterDao;
import com.epps.carrental.matsers.user.dto.UserMasterDto;
import com.epps.carrental.matsers.user.dto.UserMasterQueryDto;
import com.epps.carrental.matsers.user.entity.UserMaster;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.Valid;

@Service
public class UserMasterService {

    @Autowired
    private UserMasterDao userMasterDao;

    @Autowired
    private EntityManager entityManager;

    // ================= SAVE =================
    public List<UserMasterDto> saveUserMaster(List<UserMasterDto> userDtos) {

        if (userDtos == null || userDtos.isEmpty()) {
            throw new IllegalArgumentException("User list cannot be empty");
        }

        List<UserMaster> entityList = userDtos.stream()
                .filter(dto -> dto != null)
                .map(this::mapToEntity)
                .toList();

        List<UserMaster> savedList = userMasterDao.saveAll(entityList);

        return savedList.stream()
                .map(this::mapToDto)
                .toList();
    }

    // ================= COUNT =================
    public long getUserMasterDataCount(UserMasterQueryDto queryDto) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<UserMaster> root = cq.from(UserMaster.class);

        cq.select(cb.count(root));

        List<Predicate> predicates = new ArrayList<>();

        if (queryDto.getEmail() != null && !queryDto.getEmail().isEmpty()) {
            predicates.add(cb.like(root.get("email"), "%" + queryDto.getEmail() + "%"));
        }

        if (queryDto.getUserName() != null && !queryDto.getUserName().isEmpty()) {
            predicates.add(cb.like(root.get("userName"), "%" + queryDto.getUserName() + "%"));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq).getSingleResult();
    }

    // ================= LIST =================
    public List<UserMasterDto> getUserMasterDataList(UserMasterQueryDto queryDto) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserMaster> cq = cb.createQuery(UserMaster.class);

        Root<UserMaster> root = cq.from(UserMaster.class);

        List<Predicate> predicates = new ArrayList<>();

        if (queryDto.getUserId() != null) {
            predicates.add(cb.equal(root.get("userId"), queryDto.getUserId()));
        }

        if (queryDto.getEmail() != null && !queryDto.getEmail().isEmpty()) {
            predicates.add(cb.like(root.get("email"), "%" + queryDto.getEmail() + "%"));
        }

        if (queryDto.getUserName() != null && !queryDto.getUserName().isEmpty()) {
            predicates.add(cb.like(root.get("userName"), "%" + queryDto.getUserName() + "%"));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        // sorting
        if (queryDto.getSortBy() != null) {
            if ("desc".equalsIgnoreCase(queryDto.getSortDirection())) {
                cq.orderBy(cb.desc(root.get(queryDto.getSortBy())));
            } else {
                cq.orderBy(cb.asc(root.get(queryDto.getSortBy())));
            }
        }

        var query = entityManager.createQuery(cq);

        // pagination
        if (queryDto.getPageNo() != null && queryDto.getPageSize() != null) {
            query.setFirstResult(queryDto.getPageNo() * queryDto.getPageSize());
            query.setMaxResults(queryDto.getPageSize());
        }

        return query.getResultList().stream()
                .map(this::mapToDto)
                .toList();
    }

    // ================= UPDATE =================
    public List<UserMasterDto> updateUserMaster(@Valid List<UserMasterDto> userDtos) {

        if (userDtos == null || userDtos.isEmpty()) {
            throw new IllegalArgumentException("User list cannot be empty");
        }

        List<UserMasterDto> updatedList = new ArrayList<>();

        for (UserMasterDto dto : userDtos) {

            if (dto.getUserId() == null) {
                throw new RuntimeException("User ID is required for update");
            }

            UserMaster user = userMasterDao.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId()));

            // update only non-null fields
            if (dto.getUserName() != null) user.setUserName(dto.getUserName());
            if (dto.getEmail() != null) user.setEmail(dto.getEmail());
            if (dto.getPhoneNumber() != null) user.setPhoneNumber(dto.getPhoneNumber());
            if (dto.getLicenseNumber() != null) user.setLicenseNumber(dto.getLicenseNumber());
            if (dto.getRole() != null) user.setRole(dto.getRole());
            if (dto.getCreatedDate() != null) user.setCreatedDate(dto.getCreatedDate());
            if (dto.getUpdatedDate() != null) user.setUpdatedDate(dto.getUpdatedDate());
            if (dto.getPassword() != null) user.setPassword(dto.getPassword());

            UserMaster savedUser = userMasterDao.save(user);

            updatedList.add(mapToDto(savedUser));
        }

        return updatedList;
    }

    // ================= COMMON MAPPER =================

    private UserMaster mapToEntity(UserMasterDto dto) {

        UserMaster entity = new UserMaster();

        entity.setUserId(dto.getUserId());
        entity.setUserName(dto.getUserName());
        entity.setEmail(dto.getEmail());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setLicenseNumber(dto.getLicenseNumber());
        entity.setRole(dto.getRole());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setUpdatedDate(dto.getUpdatedDate());
        entity.setPassword(dto.getPassword());

        return entity;
    }

    private UserMasterDto mapToDto(UserMaster entity) {

        UserMasterDto dto = new UserMasterDto();

        dto.setUserId(entity.getUserId());
        dto.setUserName(entity.getUserName());
        dto.setEmail(entity.getEmail());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setLicenseNumber(entity.getLicenseNumber());
        dto.setRole(entity.getRole());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedDate(entity.getUpdatedDate());
        dto.setPassword(entity.getPassword());

        return dto;
    }
}
