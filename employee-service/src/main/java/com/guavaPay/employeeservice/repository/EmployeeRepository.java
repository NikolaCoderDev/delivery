package com.guavaPay.employeeservice.repository;

import com.guavaPay.employeeservice.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Modifying
    @Query(value = "update Employee set access_token = ? where access_token = ?", nativeQuery = true)
    void updateToken(String token, String tokenLoc);

    @Modifying
    @Query(value = "update Employee set access_token = ? where login = ?", nativeQuery = true)
    void updateAccessTokenByLogin(String accessToken, String login);

    @Modifying
    @Query(value = "update Employee set status = ? where id = ?", nativeQuery = true)
    void assignCourier(String status, long login);

    @Modifying
    @Query(value = "update Employee set latitude = ?, longitude = ?, address = ? where ip_address = ?", nativeQuery = true)
    void updateCourierCoordinates(String lat, String lng, String address, String ipAddress);

    Optional<Employee> getByAccessToken(String token);

    Optional<Employee> findByLogin(String login);

    Employee getByLogin(String login);

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Query(value = "select * from Employee", nativeQuery = true)
    List<Employee> getAll();

    @Modifying
    @Query(value = "update Employee set status = ? where id = ?", nativeQuery = true)
    void onDelivery(String status, long id);
}
