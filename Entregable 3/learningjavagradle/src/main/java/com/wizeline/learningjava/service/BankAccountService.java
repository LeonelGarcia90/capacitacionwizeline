package com.wizeline.learningjava.service;

import java.util.List;

import com.wizeline.learningjava.model.BankAccountDTO;

public interface BankAccountService {

	List<BankAccountDTO> getAccounts();

	BankAccountDTO getAccountDetails(String user, String lastUsage);
}