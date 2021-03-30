package com.soen390.erp.configuration.repository;

import com.soen390.erp.configuration.model.Log;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface LogRepository extends PagingAndSortingRepository<Log, String> {

    List<Log> findAll();
}
