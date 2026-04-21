package com.epps.carrental.transactions.booking.service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epps.carrental.matsers.location.dao.LocationMasterDao;
import com.epps.carrental.matsers.location.entity.LocationMaster;
import com.epps.carrental.matsers.price.dao.PriceMasterDao;
import com.epps.carrental.matsers.price.entity.PriceMaster;
import com.epps.carrental.matsers.user.dao.UserMasterDao;
import com.epps.carrental.matsers.user.entity.UserMaster;
import com.epps.carrental.matsers.vehicle.dao.VehicleMasterDao;
import com.epps.carrental.matsers.vehicle.entity.VehicleMaster;
import com.epps.carrental.transactions.booking.dao.BookingDao;
import com.epps.carrental.transactions.booking.dto.BookingDto;
import com.epps.carrental.transactions.booking.dto.BookingQueryDto;
import com.epps.carrental.transactions.booking.entity.BOOKING_STATUS;
import com.epps.carrental.transactions.booking.entity.Booking;
import com.epps.carrental.transactions.booking.entity.PAYMENT_STATUS;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.Valid;

@Service
public class BookingService {
	
	@Autowired
	private BookingDao bookingDao;
	
	@Autowired
	private UserMasterDao userMasterDao;
	
	@Autowired
	private VehicleMasterDao vehicleMasterDao;
		
	@Autowired
	private LocationMasterDao locationMasterDao;
	
	@Autowired
	private PriceMasterDao priceMasterDao;
	
	@Autowired
	private EntityManager entityManager;

	public List<BookingDto> saveBookingMaster(@Valid List<BookingDto> bookingDtos) {

	    if (bookingDtos == null || bookingDtos.isEmpty()) {
	        throw new IllegalArgumentException("Booking list cannot be empty");
	    }

	    List<Booking> bookingList = new ArrayList<>();

	    for (BookingDto dto : bookingDtos) {

	        Booking booking = new Booking();

	        if (dto.getUserId() == null || dto.getVehicleId() == null
	                || dto.getPickupLocationId() == null || dto.getDropLocationId() == null) {
	            throw new RuntimeException("User, Vehicle, Pickup & Drop location are required");
	        }

	        UserMaster user = userMasterDao.findById(dto.getUserId())
	                .orElseThrow(() -> new RuntimeException("User not found"));

	        VehicleMaster vehicle = vehicleMasterDao.findById(dto.getVehicleId())
	                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

	        LocationMaster pickup = locationMasterDao.findById(dto.getPickupLocationId())
	                .orElseThrow(() -> new RuntimeException("Pickup location not found"));

	        LocationMaster drop = locationMasterDao.findById(dto.getDropLocationId())
	                .orElseThrow(() -> new RuntimeException("Drop location not found"));

	        booking.setUser(user);
	        booking.setVehicle(vehicle);
	        booking.setPickupLocation(pickup);
	        booking.setDropLocation(drop);

	        booking.setPickupDate(dto.getPickupDate());
	        booking.setDropDate(dto.getDropDate());

	        if (dto.getPickupDate() != null && dto.getDropDate() != null) {
	            long days = ChronoUnit.DAYS.between(dto.getPickupDate(), dto.getDropDate());
	            booking.setTotalDays((int) (days == 0 ? 1 : days));
	        }

	        double basePricePerDay = 0.0;

	        PriceMaster price = priceMasterDao
	                .findByVehicleTypeAndLocationMaster(vehicle.getVehicleType(), pickup);

	        if (price != null) {
	            basePricePerDay = price.getBasePricePerDay();
	        }

	        double totalBaseAmount = basePricePerDay * booking.getTotalDays();
	        booking.setBaseAmount(totalBaseAmount);

	        booking.setExtraCharges(dto.getExtraCharges() != null ? dto.getExtraCharges() : 0.0);
	        booking.setDiscountAmount(dto.getDiscountAmount() != null ? dto.getDiscountAmount() : 0.0);

	        double totalAmount = booking.getBaseAmount()
	                + booking.getExtraCharges()
	                - booking.getDiscountAmount();

	        booking.setTotalAmount(totalAmount);

	        booking.setBookingStatus(dto.getBookingStatus() != null
	                ? dto.getBookingStatus()
	                : BOOKING_STATUS.BOOKED);

	        booking.setPaymentStatus(dto.getPaymentStatus() != null
	                ? dto.getPaymentStatus()
	                : PAYMENT_STATUS.PENDING);

	        bookingList.add(booking);
	    }

	    List<Booking> savedList = bookingDao.saveAll(bookingList);

	    List<BookingDto> result = new ArrayList<>();

	    for (Booking saved : savedList) {

	        BookingDto dto = new BookingDto();

	        dto.setBookingId(saved.getBookingId());

	        dto.setUserId(saved.getUser().getUserId());
	        dto.setVehicleId(saved.getVehicle().getVehicleId());
	        dto.setPickupLocationId(saved.getPickupLocation().getLocationId());
	        dto.setDropLocationId(saved.getDropLocation().getLocationId());

	        dto.setPickupDate(saved.getPickupDate());
	        dto.setDropDate(saved.getDropDate());
	        dto.setTotalDays(saved.getTotalDays());

	        dto.setBaseAmount(saved.getBaseAmount());
	        dto.setExtraCharges(saved.getExtraCharges());
	        dto.setDiscountAmount(saved.getDiscountAmount());
	        dto.setTotalAmount(saved.getTotalAmount());

	        dto.setBookingStatus(saved.getBookingStatus());
	        dto.setPaymentStatus(saved.getPaymentStatus());

	        dto.setCreatedDate(saved.getCreatedDate());
	        dto.setUpdatedDate(saved.getUpdatedDate());

	        result.add(dto);
	    }

	    return result;
	}

	public long getBookingMasterDataCount(@Valid BookingQueryDto masterQueryDto) {

	    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Long> cq = cb.createQuery(Long.class);

	    Root<Booking> root = cq.from(Booking.class);

	    cq.select(cb.count(root));

	    List<Predicate> predicates = new ArrayList<>();

	    if (masterQueryDto.getBookingId() != null) {
	        predicates.add(cb.equal(root.get("bookingId"), masterQueryDto.getBookingId()));
	    }

	    if (masterQueryDto.getUserId() != null) {
	        predicates.add(cb.equal(root.get("user").get("userId"), masterQueryDto.getUserId()));
	    }

	    if (masterQueryDto.getVehicleId() != null) {
	        predicates.add(cb.equal(root.get("vehicle").get("vehicleId"), masterQueryDto.getVehicleId()));
	    }

	    if (masterQueryDto.getPickupLocationId() != null) {
	        predicates.add(cb.equal(root.get("pickupLocation").get("locationId"),
	                masterQueryDto.getPickupLocationId()));
	    }

	    if (masterQueryDto.getDropLocationId() != null) {
	        predicates.add(cb.equal(root.get("dropLocation").get("locationId"),
	                masterQueryDto.getDropLocationId()));
	    }

	    if (masterQueryDto.getBookingStatus() != null) {
	        predicates.add(cb.equal(root.get("bookingStatus"), masterQueryDto.getBookingStatus()));
	    }

	    if (masterQueryDto.getFromDate() != null && masterQueryDto.getToDate() != null) {
	        predicates.add(cb.between(root.get("pickupDate"),
	                masterQueryDto.getFromDate(),
	                masterQueryDto.getToDate()));
	    }		

	    cq.where(predicates.toArray(new Predicate[0]));

	    return entityManager.createQuery(cq).getSingleResult();
	}

	public List<BookingDto> getBookingMasterDataList(@Valid BookingQueryDto masterQueryDto) {

	    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Booking> cq = cb.createQuery(Booking.class);

	    Root<Booking> root = cq.from(Booking.class);

	    List<Predicate> predicates = new ArrayList<>();

	    if (masterQueryDto.getBookingId() != null) {
	        predicates.add(cb.equal(root.get("bookingId"), masterQueryDto.getBookingId()));
	    }

	    if (masterQueryDto.getUserId() != null) {
	        predicates.add(cb.equal(root.get("user").get("userId"), masterQueryDto.getUserId()));
	    }

	    if (masterQueryDto.getVehicleId() != null) {
	        predicates.add(cb.equal(root.get("vehicle").get("vehicleId"), masterQueryDto.getVehicleId()));
	    }

	    if (masterQueryDto.getPickupLocationId() != null) {
	        predicates.add(cb.equal(root.get("pickupLocation").get("locationId"),
	                masterQueryDto.getPickupLocationId()));
	    }

	    if (masterQueryDto.getDropLocationId() != null) {
	        predicates.add(cb.equal(root.get("dropLocation").get("locationId"),
	                masterQueryDto.getDropLocationId()));
	    }

	    if (masterQueryDto.getBookingStatus() != null) {
	        predicates.add(cb.equal(root.get("bookingStatus"), masterQueryDto.getBookingStatus()));
	    }

	    if (masterQueryDto.getFromDate() != null && masterQueryDto.getToDate() != null) {
	        predicates.add(cb.between(root.get("pickupDate"),
	                masterQueryDto.getFromDate(),
	                masterQueryDto.getToDate()));
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

	    List<Booking> resultList = query.getResultList();

	    List<BookingDto> dtoList = new ArrayList<>();

	    for (Booking booking : resultList) {

	        BookingDto dto = new BookingDto();

	        dto.setBookingId(booking.getBookingId());

	        dto.setUserId(booking.getUser().getUserId());
	        dto.setVehicleId(booking.getVehicle().getVehicleId());
	        dto.setPickupLocationId(booking.getPickupLocation().getLocationId());
	        dto.setDropLocationId(booking.getDropLocation().getLocationId());

	        dto.setPickupDate(booking.getPickupDate());
	        dto.setDropDate(booking.getDropDate());
	        dto.setTotalDays(booking.getTotalDays());

	        dto.setBaseAmount(booking.getBaseAmount());
	        dto.setExtraCharges(booking.getExtraCharges());
	        dto.setDiscountAmount(booking.getDiscountAmount());
	        dto.setTotalAmount(booking.getTotalAmount());

	        dto.setBookingStatus(booking.getBookingStatus());
	        dto.setPaymentStatus(booking.getPaymentStatus());	

	        dto.setCreatedDate(booking.getCreatedDate());
	        dto.setUpdatedDate(booking.getUpdatedDate());

	        dtoList.add(dto);
	    }

	    return dtoList;
	}
	public List<BookingDto> updateBookingMaster(@Valid List<BookingDto> bookingDtos) {
		// TODO Auto-generated method stub
		return null;
	}

}
