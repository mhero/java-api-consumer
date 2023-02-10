package com.mac.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.mac.domain.CurrencyUpdate;

public interface CurrencyUpdateRepository
		extends PagingAndSortingRepository<CurrencyUpdate, Long>, CrudRepository<CurrencyUpdate, Long> {
	List<CurrencyUpdate> findAll();
}
