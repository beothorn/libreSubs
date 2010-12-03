package org.libreSubsApplet.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

public class LocaleUtil {
	
	public static Locale[] getSortedAvailableLocales() {
		final Locale[] sortedAvailableLocales = Locale.getAvailableLocales();
		final Comparator<Locale> localeComparator = new Comparator<Locale>() {
		    public int compare( final Locale l1, final Locale l2 ) {  
		        final String l1DisplayName = l1.getDisplayName();
				final String l2DisplayName = l2.getDisplayName();
				return l1DisplayName.compareTo(l2DisplayName); 
		    }
		};
		Arrays.sort(sortedAvailableLocales,localeComparator);
		final ArrayList<Locale> validLocales = new ArrayList<Locale>();
		for (final Locale locale : sortedAvailableLocales) {
			if(isValidLanguage(locale.toString())){
				validLocales.add(locale);
			}
		}
		return validLocales.toArray(new Locale[0]);
	}
	
	public static boolean isValidLanguage(final String language) {
		return language.matches("[a-z]{2}_[A-Z]{2}");
	}
}
