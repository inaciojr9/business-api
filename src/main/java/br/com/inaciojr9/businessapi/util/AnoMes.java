package br.com.inaciojr9.businessapi.util;

import java.util.Calendar;
import java.util.Date;

public final class AnoMes {
	
	private final String mes;
	private final String ano;

	public AnoMes(Date anoMes) {
		super();
		Calendar calendar = Calendar.getInstance();
    	calendar.setTime(anoMes);
    	
    	this.mes = String.format("%02d", calendar.get(Calendar.MONTH));
    	this.ano = String.format("%04d", calendar.get(Calendar.YEAR));
	}
	
	public String getAno() {
		return this.ano;
	}
	
	public String getMes() {
		return this.mes;
	}

}
