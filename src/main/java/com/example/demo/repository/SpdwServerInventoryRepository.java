package com.example.demo.repository;

import com.example.demo.model.SpdwServerInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpdwServerInventoryRepository extends JpaRepository<SpdwServerInventory, Long> {
}
