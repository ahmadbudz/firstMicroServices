package com.microservice.job.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservice.job.model.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long>{

}
