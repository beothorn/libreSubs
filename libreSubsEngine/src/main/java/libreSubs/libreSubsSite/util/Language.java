package libreSubs.libreSubsSite.util;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.libreSubsApplet.utils.LocaleUtil;

public class Language {
	
	public static List<String> getLanguagesAsStringList(final Locale locale) {
		final Locale[] sortedAvailableLocales = LocaleUtil.getSortedAvailableLocales();
		final String[] availableLocalesNames = new String[sortedAvailableLocales.length];
		for (int i = 0; i < sortedAvailableLocales.length; i++) {
			final Locale availableLocale = sortedAvailableLocales[i];
			availableLocalesNames[i] = availableLocale.getDisplayName(locale);
		}
		return Arrays.asList(availableLocalesNames);
	}
}
