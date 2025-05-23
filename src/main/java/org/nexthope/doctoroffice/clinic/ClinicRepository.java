package org.nexthope.doctoroffice.clinic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Long> {

    boolean existsByNameAndAddressAndCity(String name, String address, String city);
}
