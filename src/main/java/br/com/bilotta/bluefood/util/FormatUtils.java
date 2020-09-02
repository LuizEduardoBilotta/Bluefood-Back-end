package br.com.bilotta.bluefood.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class FormatUtils {

	private static final Locale LOCALE_BRAZIL = new Locale("pt", "BR");
	
	public static NumberFormat newNumberFormat() {
		NumberFormat nf = NumberFormat.getCurrencyInstance(LOCALE_BRAZIL);
		
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(2);
		nf.setGroupingUsed(false);
		
		return nf;
	}
	
	public static String formatCurrency(BigDecimal number) {
		if (number == null) {
			return null;
		}
		
		return newNumberFormat().format(number);
	}

}
