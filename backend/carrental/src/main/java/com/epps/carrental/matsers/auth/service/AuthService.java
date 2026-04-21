package com.epps.carrental.matsers.auth.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epps.carrental.matsers.user.dao.UserMasterDao;
import com.epps.carrental.matsers.user.dto.UserMasterDto;
import com.epps.carrental.matsers.user.entity.UserMaster;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

@Service
public class AuthService {

	@Autowired
    private UserMasterDao userMasterDao	;

    public UserMasterDto register(UserMasterDto userDtos) {
    	 if (userDtos == null) {
             throw new IllegalArgumentException("User cannot be empty");
         }
    	 
    	 UserMaster master = new UserMaster();
    	 BeanUtils.copyProperties(userDtos, master);
    	 
    	 UserMaster save = userMasterDao.save(master);
    	 
    	 UserMasterDto updated = new UserMasterDto();
    	 BeanUtils.copyProperties(save, updated);
    	 return updated;
         
    }

    public UserMaster login(String email, String password) {
        Optional<UserMaster> user = userMasterDao.findByEmail(email);

        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user.get();
        }
        throw new RuntimeException("Invalid credentials");
    }
}
