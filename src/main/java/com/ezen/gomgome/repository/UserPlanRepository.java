package com.ezen.gomgome.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ezen.gomgome.entity.UserPlan;

public interface UserPlanRepository extends JpaRepository<UserPlan, Integer> {
	
}
