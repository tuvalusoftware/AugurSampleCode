package com.augur.sample;

import java.math.BigDecimal;
import java.util.Collections;

import com.augur.sample.indexer.DataStructures;
import com.augur.sample.interfaces.AugurIndexerInterface;

public class AugurService implements AugurIndexerInterface{

	@Override
	public BigDecimal getMedianTokenTransfer(String token) {
		
	int size = DataStructures.AveragesAndMeans.amounts.size();
	Collections.sort(DataStructures.AveragesAndMeans.amounts);
	int index =   Math.round(size/2);
	BigDecimal bd = DataStructures.AveragesAndMeans.amounts.get(index);
	return bd;
		 
		 
	}

	@Override
	public BigDecimal getAverageTokenTransfer(String token) {
		
		BigDecimal bd  =  DataStructures.AveragesAndMeans.token_average.get(token);
		return bd; 
		 
	}

	@Override
	public String getAddressHighestBalance(String token, long epoch) {
		
		Long key = DataStructures.TimeSeries.time_records.get(token).floorKey(new Long(epoch));
		String greatestBalance = DataStructures.TimeSeries.time_records.get(token).get(key).getAccountWithGreatestBalance();
		return greatestBalance;
	}
	

	@Override
	public String getAddressWithMostTransfers(String token, long epoch) {

		Long key = DataStructures.TimeSeries.time_records.get(token).floorKey(new Long(epoch));
		String greatestNumberOfTransactions = DataStructures.TimeSeries.time_records.get(token).get(key).getAccountWithGreatestNumberOfTransactions();
		return greatestNumberOfTransactions;
	 
	}

}
