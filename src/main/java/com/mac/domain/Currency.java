package com.mac.domain;

public enum Currency {

	AUD("Australia", ""), 
	BGN("Bulgaria", ""), 
	BRL("Brazil", ""), 
	CAD("Canada", ""), 
	CHF("Switzerland", ""),
	CNY("China", ""), 
	CYP("Cyprus", "up to the end of December 2007"), 
	CZK("Czechia", ""), 
	DKK("Denmark", ""),
	EEK("Estonia", "up to the end of December 2010"), 
	GBP("United Kingdom", ""),
	GRD("Greece", "up to the end of December 2000"), 
	HKD("Hong Kong", ""),
	HRK("Croatia", "up to the end of December 2022"), 
	HUF("Hungary", ""), 
	IDR("Indonesia", ""), 
	ILS("Israel", ""),
	INR("India", ""), 
	ISK("Iceland", ""),
	JPY("Japan", ""), 
	KRW("Korea, Republic of", ""),
	LTL("Lithuania", "up to the end of December 2014"), 
	LVL("Latvia", "up to the end of December 2013"),
	MTL("Malta", "up to the end of December 2007"),
	MXN("Mexico", ""), 
	MYR("Malaysia", ""), 
	NOK("Norway", ""),
	NZD("New Zealand", ""), 
	PHP("Philippines", ""), 
	PLN("Poland", ""), 
	ROL("Romania", "up to the end of June 2005"),
	RON("Romania", "from July 2005"), 
	RUB("Russian Federation", ""), 
	SEK("Sweden", ""), 
	SGD("Singapore", ""),
	SIT("Slovenia", "up to the end of December 2006"),
	SKK("Slovakia", "up to the end of December 2008"),
	THB("Thailand", ""), 
	TRL("Turkey", "up to the end of December 2004"), 
	TRY("Turkey", "from January 2005"),
	USD("United States", ""), 
	ZAR("South Africa", ""),
	EUR("EURO", "");

	public final String label;
	public final String details;

	Currency(String label, String details) {
		this.label = label;
		this.details = details;
	}

}
