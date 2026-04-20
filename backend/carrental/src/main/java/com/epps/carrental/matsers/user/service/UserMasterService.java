package com.epps.carrental.matsers.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
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

    public List<UserMasterDto> saveUserMaster(List<UserMasterDto> userMasterDtos) {

        if (userMasterDtos == null || userMasterDtos.isEmpty()) {
            throw new IllegalArgumentException("User list cannot be empty");
        }

        List<UserMaster> list = userMasterDtos.stream()
                .filter(dto -> dto != null)
                .map(dto -> {
                    UserMaster entity = new UserMaster();
                    BeanUtils.copyProperties(dto, entity);
                    return entity;
                })
                .toList();

        List<UserMaster> savedList = userMasterDao.saveAll(list);

        return savedList.stream()
                .map(entity -> {
                    UserMasterDto dto = new UserMasterDto();	
                    BeanUtils.copyProperties(entity, dto);
                    return dto;
                })
                .toList();
    }

    public long getUserMasterDataCount(UserMasterQueryDto masterQueryDto) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<UserMaster> root = cq.from(UserMaster.class);

        cq.select(cb.count(root));

        Predicate predicate = cb.conjunction(); 

        if (masterQueryDto.getEmail() != null && !masterQueryDto.getEmail().isEmpty()) {
            predicate = cb.and(predicate,
                    cb.like(root.get("email"), "%" + masterQueryDto.getEmail() + "%"));
        }

        if (masterQueryDto.getUser_name() != null && !masterQueryDto.getUser_name().isEmpty()) {
            predicate = cb.and(predicate,
                    cb.like(root.get("name"), "%" + masterQueryDto.getUser_name() + "%"));
        }

        cq.where(predicate);

        return entityManager.createQuery(cq).getSingleResult();
    }

    public List<UserMasterDto> getUserMasterDataList(UserMasterQueryDto masterQueryDto) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserMaster> cq = cb.createQuery(UserMaster.class);

        Root<UserMaster> root = cq.from(UserMaster.class);

        List<Predicate> predicates = new ArrayList<>();

        if (masterQueryDto.getUser_id() != null) {
            predicates.add(cb.equal(root.get("user_id"), masterQueryDto.getUser_id()));
        }
        
        if (masterQueryDto.getEmail() != null && !masterQueryDto.getEmail().isEmpty()) {
            predicates.add(cb.like(root.get("email"), "%" + masterQueryDto.getEmail() + "%"));
        }

        if (masterQueryDto.getUser_name() != null && !masterQueryDto.getUser_name().isEmpty()) {
            predicates.add(cb.like(root.get("name"), "%" + masterQueryDto.getUser_name() + "%"));
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

        List<UserMaster> resultList = query.getResultList();

        List<UserMasterDto> dtoList = new ArrayList<>();

        for (UserMaster user : resultList) {
            UserMasterDto dto = new UserMasterDto();
            dto.setUser_id(user.getUser_id());
            dto.setUser_name(user.getUser_name());
            dto.setEmail(user.getEmail());
            dto.setLicense_number(user.getLicense_number());
            dto.setPhone_number(user.getPhone_number());
            dto.setCreated_date(user.getCreated_date());
            dto.setUpdated_date(user.getUpdated_date());
            dto.setRole(user.getRole());
            dtoList.add(dto);
        }

        return dtoList;
    }

	public List<UserMasterDto> updateUserMaster(@Valid List<UserMasterDto> userMasterDtos) {
		
		List<UserMasterDto> updatedList = new ArrayList<>();

	    for (UserMasterDto dto : userMasterDtos) {

	        if (dto.getUser_id() == null) {
	            throw new RuntimeException("User ID is required for update");
	        }

	        UserMaster user = userMasterDao.findById(dto.getUser_id())
	                .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUser_id()));

	        if (dto.getUser_name() != null) {
	            user.setUser_name(dto.getUser_name());
	        }

	        if (dto.getEmail() != null) {
	            user.setEmail(dto.getEmail());
	        }
	        
	        if (dto.getCreated_date() != null) {
	            user.setCreated_date(dto.getCreated_date());
	        }
	        
	        if (dto.getPhone_number() != null) {
	            user.setPhone_number(dto.getPhone_number());
	        }
	        
	        if (dto.getLicense_number() != null) {
	            user.setLicense_number(dto.getLicense_number());
	        }
	        
	        if (dto.getRole() != null) {
	            user.setRole(dto.getRole());
	        }
	        
	        if (dto.getUpdated_date() != null) {
	            user.setUpdated_date(dto.getUpdated_date());
	        }

	        UserMaster savedUser = userMasterDao.save(user);

	        UserMasterDto updatedDto = new UserMasterDto();
	        updatedDto.setUser_id(savedUser.getUser_id());
	        updatedDto.setUser_name(savedUser.getUser_name());
	        updatedDto.setEmail(savedUser.getEmail());
	        updatedDto.setLicense_number(savedUser.getLicense_number());
	        updatedDto.setPhone_number(savedUser.getPhone_number());
	        updatedDto.setCreated_date(savedUser.getCreated_date());
	        updatedDto.setUpdated_date(savedUser.getUpdated_date());
	        updatedDto.setRole(savedUser.getRole());

	        updatedList.add(updatedDto);
	    }

	    return updatedList;
		
	}
    
    
}	
