package com.augur.sample.read;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;

import com.augur.sample.pojos.RecordFoundEventPOJO;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
/*
 * 
 *  Reads the file and parses it into pojos to be sent as events
 * 
 */
public class TokenFileLogReader {

	long sleepTime = 1000;
	// assumes that the files is in the project directory 
	String fileName = "token_transfers.json";
	TokenNotificationInterface eventHandler;
	/*
	 * 
	 *  initialized with event handler that will receive new log entry events
	 * 
	 */
	public TokenFileLogReader(TokenNotificationInterface eventHandler, String fileName, long sleepTime) {

		this.fileName = fileName;
		this.eventHandler = eventHandler;
		this.sleepTime = sleepTime;

	}
	/*
	 * 
	 * reads the next 5 lines in the file to create a record
	 * 
	 */
	public String createRecordString(BufferedReader input ) throws IOException
	{
		String currentLine = null;
		
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("{");
		currentLine = input.readLine();
		strBuilder.append(currentLine);
		currentLine = input.readLine();
		strBuilder.append(currentLine);
		currentLine = input.readLine();
		strBuilder.append(currentLine);
		currentLine = input.readLine();
		strBuilder.append(currentLine);
		currentLine = input.readLine();
		strBuilder.append(currentLine);
		strBuilder.append("}");
		
		return strBuilder.toString();
		
		
	}
	/*
	 * 
	 *  runs continuously sleeping sleepTime between executions
	 * 
	 */
	public void refreshRecords() throws IOException {

		long sleepTime = 1000;
		BufferedReader input = new BufferedReader(new FileReader(fileName));
		String currentLine = null;

		while (true) {

			RecordFoundEventPOJO pojo = new RecordFoundEventPOJO();

			if ((currentLine = input.readLine()) != null) {

				currentLine.trim();
				boolean isStartOfNewRecord = currentLine.contains("{");

				if (isStartOfNewRecord) {
					
					String record =createRecordString(input);

					JsonReader jsonReader = new JsonReader(new StringReader(record));
					jsonReader.setLenient(true);

					try {

						String name = null;
						long number = 0;
						while (jsonReader.hasNext()) {
							JsonToken nextToken = jsonReader.peek();

							if (JsonToken.BEGIN_OBJECT.equals(nextToken)) {

								jsonReader.beginObject();

							} else if (JsonToken.NAME.equals(nextToken)) {

								name = jsonReader.nextName();

								switch (name) {
								case "token":
									String value = jsonReader.nextString();
									pojo.setToken(value);
									// System.out.println("token "+ value );
									break;
								case "sender":
									value = jsonReader.nextString();
									pojo.setSender(value);
									// System.out.println("sender " + value);
									break;
								case "recipient":
									value = jsonReader.nextString();
									pojo.setRecipient(value);
									// System.out.println("recipeint " + value);
									break;
								case "value":
									value = jsonReader.nextString();
									BigDecimal bigDecimal = new BigDecimal(value);
									pojo.setValue(bigDecimal);
									// System.out.println("value "+ bigDecimal.toString() );
									break;
								case "timestamp":
									number = jsonReader.nextLong();
									pojo.setTimestamp(number);
									// System.out.println("timestampe "+ number );
									break;
								default:
									// System.out.println("no match");
								}

								continue;

							}

						}

					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						jsonReader.close();
					}

				 
					eventHandler.newLogEntry(pojo);
				}

				continue;
			}

			try {

				Thread.sleep(sleepTime);

			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
				break;
			}

		}
		input.close();

	}

}
