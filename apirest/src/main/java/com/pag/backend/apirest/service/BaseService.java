package com.pag.backend.apirest.service;

import java.util.List;

public interface BaseService<Dto> {

	void save (Dto dto);
	
	void update (Dto dto);
	
	void delete (Dto dto);
	
	Dto findById(String id);
	
	List<Dto> findByAll();
}
