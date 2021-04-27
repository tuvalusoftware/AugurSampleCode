package com.augur.sample.pojos;

public class ReportingDataPOJO {
	
	public ReportingDataPOJO(String accountWithGreatestNumberOfTransactions, 
			String accountWithGreatestBalance)
	{
		this.accountWithGreatestBalance = accountWithGreatestBalance;
		this.accountWithGreatestNumberOfTransactions = accountWithGreatestNumberOfTransactions;
		
	}
	 
	public String getAccountWithGreatestNumberOfTransactions() {
		return accountWithGreatestNumberOfTransactions;
	}
	public void setAccountWithGreatestNumberOfTransactions(String accountWithGreatestNumberOfTransactions) {
		this.accountWithGreatestNumberOfTransactions = accountWithGreatestNumberOfTransactions;
	}
	public String getAccountWithGreatestBalance() {
		return accountWithGreatestBalance;
	}
	public void setAccountWithGreatestBalance(String accountWithGreatestBalance) {
		this.accountWithGreatestBalance = accountWithGreatestBalance;
	}
	private String accountWithGreatestNumberOfTransactions;
	private String accountWithGreatestBalance;

}
