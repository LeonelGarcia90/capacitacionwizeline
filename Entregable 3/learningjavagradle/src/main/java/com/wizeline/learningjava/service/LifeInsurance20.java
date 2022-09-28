package com.wizeline.learningjava.service;

import org.springframework.stereotype.Service;

import com.wizeline.learningjava.model.InsuranceBase;

@Service
public class LifeInsurance20 implements LifeInsuranceBase, Cloneable {

	@Override
	public InsuranceBase getInsurance(int cost) {
		InsuranceBase insurance20 = new InsuranceBase();
		insurance20.setName("Seguro de vida extendido");
		insurance20.setCost(cost);
		insurance20.setDescription("Cubre sus créditos pendientes y costos funerarios en caso de fallecimiento");
		return insurance20;
	}

}
