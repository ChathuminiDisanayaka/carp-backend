package com.carp.carpentry.repository;

import com.carp.carpentry.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
