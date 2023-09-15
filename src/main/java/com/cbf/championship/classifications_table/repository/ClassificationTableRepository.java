package com.cbf.championship.classifications_table.repository;

import com.cbf.championship.classifications_table.ClassificationsTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassificationTableRepository extends JpaRepository<ClassificationsTable, Integer> {
}
