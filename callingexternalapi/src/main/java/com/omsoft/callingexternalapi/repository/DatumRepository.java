package com.omsoft.callingexternalapi.repository;

import com.omsoft.callingexternalapi.models.Datum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatumRepository extends JpaRepository<Datum, Long> {
}
