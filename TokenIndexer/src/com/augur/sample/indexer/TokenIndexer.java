package com.augur.sample.indexer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentSkipListMap;
import com.augur.sample.pojos.RecordFoundEventPOJO;
import com.augur.sample.pojos.ReportingDataPOJO;
import com.augur.sample.read.TokenNotificationInterface;
import java.util.logging.*;
 
/*
 * 
 *  performs the logic of the indexing 
 * 
 */
public class TokenIndexer implements TokenNotificationInterface {

 

	private static Logger logger = Logger.getLogger("com.augur.sample");

	/*
	 *  Callback function called one per log entry
	 */
	
	@Override
	public void newLogEntry(RecordFoundEventPOJO pojo) {
		
		
         
	 
		if (pojo.isValid()) {

			String sender = pojo.getSender();
			String receiver = pojo.getRecipient();
			String token = pojo.getToken();
			BigDecimal value = pojo.getValue();
			Long timestamp = pojo.getTimestamp();
			String accountWithMostTransactions = null;
			String accountWithHighestBalance = null;

			init(token);

			if (!pojo.isFirstEntry()) {	
				
				accountWithMostTransactions = accountWithMostTransactions(token,sender); 
			}

			 accountWithHighestBalance = accountWithMostHighestBalance( token, receiver, sender, value, pojo.isFirstEntry() ) ;
			 DataStructures.TimeSeries.time_records.get(token).put(timestamp,new ReportingDataPOJO(accountWithMostTransactions,accountWithHighestBalance));
		
			 
			
			 DataStructures.AveragesAndMeans.amounts.add(value);
			 DataStructures.AveragesAndMeans.token_average.putIfAbsent(token,new BigDecimal(1)); 
			 
			 
			 
			 //New average = old average * (n-1)/n + new value /n
			 int  size  =   DataStructures.TimeSeries.time_records.get(token).keySet().size();
			
			 BigDecimal size2  = new BigDecimal(size);
			 BigDecimal one  = new BigDecimal(1);
			 DataStructures.AveragesAndMeans.token_average.computeIfPresent(token, (k, average) -> average.multiply( (size2.subtract(one) ).divide(size2,RoundingMode.HALF_UP).add(value.divide(size2,RoundingMode.HALF_UP))));

			 System.out.println(pojo);
			 
		} else {
			
			logger.fine(pojo.toString());
 
		}

	}
	/*
	 *  initialize the data structures when a new token address is found
	 */
	public void init(String token) {
 


		if (! DataStructures.Transfers.num_transfers.containsKey(token)) {
			ConcurrentSkipListMap<Long, String> sl = new ConcurrentSkipListMap<Long, String>();
			 DataStructures.Transfers.num_transfers.put(token, sl);

		}
	 

		if (! DataStructures.Transfers.num_account_transfers.containsKey(token)) {
			ConcurrentSkipListMap<String, Long> list = new ConcurrentSkipListMap<String, Long>();
			 DataStructures.Transfers.num_account_transfers.put(token, list);
		}

		if (! DataStructures.Balance.holders_balance.containsKey(token)) {

			ConcurrentSkipListMap<String, BigDecimal> slvd = new ConcurrentSkipListMap<String, BigDecimal>();
			 DataStructures.Balance.holders_balance.put(token, slvd);

		}

		if (! DataStructures.Balance.balance_holder.containsKey(token)) {
			ConcurrentSkipListMap<BigDecimal, String> blh = new ConcurrentSkipListMap<BigDecimal, String>();

			 DataStructures.Balance.balance_holder.put(token, blh);

		}
		
		if(! DataStructures.TimeSeries.time_records.containsKey(token))
		{
			ConcurrentSkipListMap<Long, ReportingDataPOJO> tr = new ConcurrentSkipListMap<Long, ReportingDataPOJO>();
			 DataStructures.TimeSeries.time_records.put(token, tr);
		}
		
		
	 

	}
 
	/*
	 *  logic performed an a new record to persist data that can be used to find the account with most transactions
	 */
	public String accountWithMostTransactions(String token,String sender) {

		Long l =  DataStructures.Transfers.num_account_transfers.get(token).computeIfPresent(sender, (k, v) -> v = new Long(v.longValue() + 1));

		if (l == null) {
			l =  DataStructures.Transfers.num_account_transfers.get(token).putIfAbsent(sender, new Long(0));
		}
		 DataStructures.Transfers.num_transfers.get(token).put( DataStructures.Transfers.num_account_transfers.get(token).get(sender), sender);
		Long key =  DataStructures.Transfers.num_transfers.get(token).lastKey();
		String accountWithMostTransactions =  DataStructures.Transfers.num_transfers.get(token).get(key);
		return accountWithMostTransactions;

	}
	
	/*
	 *  logic performed an a new record to persist data that can be used to find the account with the highest balance
	 */
	public String accountWithMostHighestBalance(String token,String receiver,String sender, BigDecimal amount, boolean isFirst ) {

		// update the receivers account balance
		BigDecimal bd =  DataStructures.Balance.holders_balance.get(token).computeIfPresent(receiver, (k, v) -> v.add(amount));
		if (bd == null) {
			 DataStructures.Balance.holders_balance.get(token).putIfAbsent(receiver, new BigDecimal(0));

		} else {
			 DataStructures.Balance.balance_holder.get(token).put(bd, receiver);
		}

		// update the senders balance
		if (!isFirst) {
			bd =  DataStructures.Balance.holders_balance.get(token).computeIfPresent(sender, (k, v) -> v.subtract(amount));
			if (bd == null) {
				 DataStructures.Balance.holders_balance.get(token).putIfAbsent(sender, new BigDecimal(0));
			}
		}

		if (bd != null) {
			 DataStructures.Balance.balance_holder.get(token).put(bd, sender);
		} else {

			 DataStructures.Balance.balance_holder.get(token).put(new BigDecimal(0), sender);
		}

		BigDecimal highestValue =  DataStructures.Balance.balance_holder.get(token).lastKey();
		String accountWithHighestBalance =  DataStructures.Balance.balance_holder.get(token).get(highestValue);
		
		return accountWithHighestBalance;

	}

}
