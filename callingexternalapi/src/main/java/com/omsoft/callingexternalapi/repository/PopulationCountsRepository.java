package com.omsoft.callingexternalapi.repository;

import com.omsoft.callingexternalapi.models.PopulationCounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PopulationCountsRepository extends JpaRepository<PopulationCounts, Long> {
}
