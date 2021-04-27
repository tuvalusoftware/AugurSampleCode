package com.augur.sample.read;

import java.util.ArrayList;

import com.augur.sample.pojos.RecordFoundEventPOJO;

public interface TokenNotificationInterface {

	public void newLogEntry(RecordFoundEventPOJO pojo);

}
