package com.guavaPay.orderservice.repository;

import com.guavaPay.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

       @Transactional(isolation = Isolation.READ_COMMITTED)
       List<Order> getOrdersByUserId(String id);

       @Transactional(isolation = Isolation.READ_COMMITTED)
       List<Order> getOrdersByEmployeeId(String id);

       @Modifying
       @Transactional
       @Query(value = "update Orders set status = ? where user_id = ? and id = ?", nativeQuery = true)
       void cancelOrderByUserId(String canceled, String userId, long orderId);

       @Modifying
       @Transactional
       @Query(value = "update Orders set delivery_address = ?, delivery_latitude = ?, delivery_longitude = ? where user_id = ? and id = ?", nativeQuery = true)
       void updateAddressByUserId(String address, String lat, String lng, String userId, long orderId);

       @Modifying
       @Transactional
       @Query(value = "update Orders set status = ? where employee_id = ? and id = ?", nativeQuery = true)
       void acceptOrderByCourierId(String accepted, String employeeId, long id);

       @Modifying
       @Transactional
       @Query(value = "update Orders set status = ? where employee_id = ? and id = ?", nativeQuery = true)
       void assignCourierToOrder(String signed, String courierId, long ordererId);
}