package com.augur.sample.interfaces;

import java.math.BigDecimal;

 /*
  * 
  *  this interface defines the methods on supported by the service
  * 
  */

public interface AugurIndexerInterface {
	
	
	BigDecimal getMedianTokenTransfer(String address);
	BigDecimal getAverageTokenTransfer(String address);
	String getAddressHighestBalance(String token,long epoch);
	String getAddressWithMostTransfers(String token,long epoch);
}
