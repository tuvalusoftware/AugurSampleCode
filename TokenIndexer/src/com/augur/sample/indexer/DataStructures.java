package com.augur.sample.indexer;

 
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;
import java.util.concurrent.ConcurrentSkipListMap;

import com.augur.sample.pojos.ReportingDataPOJO;

/*
 * 
 *  contains all the data structures used by the indexer
 * 
 */
public final  class DataStructures {
	
	
	 public  static class Transfers
	  {
			/// number of transfers records
		 public static ConcurrentSkipListMap<String, ConcurrentSkipListMap<Long, String>> num_transfers = new ConcurrentSkipListMap<String, ConcurrentSkipListMap<Long, String>>();
		 public static ConcurrentSkipListMap<String, ConcurrentSkipListMap<String, Long>> num_account_transfers = new ConcurrentSkipListMap<String, ConcurrentSkipListMap<String, Long>>();  
	  }
	  
	  public  static class Balance
	  {
		  public  static ConcurrentSkipListMap<String, ConcurrentSkipListMap<String, BigDecimal>> holders_balance = new ConcurrentSkipListMap<String, ConcurrentSkipListMap<String, BigDecimal>>();
		  public static ConcurrentSkipListMap<String, ConcurrentSkipListMap<BigDecimal, String>> balance_holder = new ConcurrentSkipListMap<String, ConcurrentSkipListMap<BigDecimal, String>>();
			  
		  
	  }
	  
	  public  static class TimeSeries
	  {
		  public  static ConcurrentSkipListMap<String, ConcurrentSkipListMap<Long, ReportingDataPOJO>> time_records = new ConcurrentSkipListMap<String, ConcurrentSkipListMap<Long, ReportingDataPOJO>>();

	  }
	  
	  public static class AveragesAndMeans
	  {
			// average records
			public static ConcurrentSkipListMap<String,BigDecimal> token_average = new ConcurrentSkipListMap<String,BigDecimal>();
			public static Vector<BigDecimal> amounts = new  Vector<BigDecimal>();

			  
		  
	  }


}
