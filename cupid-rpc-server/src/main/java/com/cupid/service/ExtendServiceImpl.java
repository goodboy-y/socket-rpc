package com.cupid.service;

import com.cupid.api.ExtendService;

@ServiceName("ExtendService")
public class ExtendServiceImpl implements ExtendService {

	@Override
	public Integer add(Integer a, Integer b) {
		return a + b;
	}

}
