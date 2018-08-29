package com.imhungryapp.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imhungryapp.demo.model.PurchaseOrder;

public interface PurchaseOrderRepository  extends JpaRepository<PurchaseOrder, Integer> {

}
