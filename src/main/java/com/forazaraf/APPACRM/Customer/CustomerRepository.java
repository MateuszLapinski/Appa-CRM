package com.forazaraf.APPACRM.Customer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<CustomerModel, Integer> {

    boolean existsByEmail(String email);
    List<CustomerModel> findByStatus(String status);

}
