package com.wizeline.learningjava.service;

import org.springframework.stereotype.Service;

import com.wizeline.learningjava.model.InsuranceBase;

@Service
public class LifeInsurance10 implements LifeInsuranceBase, Cloneable {

	@Override
	public InsuranceBase getInsurance(int cost) {
		InsuranceBase insurance10 = new InsuranceBase();
		insurance10.setName("Seguro de vida básico");
		insurance10.setCost(cost);
		insurance10.setDescription("Cubre sus créditos pendientes en caso de fallecimiento");
		return insurance10;
	}

}
