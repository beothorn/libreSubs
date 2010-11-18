package org.libreSubsCommons;

import java.util.ArrayList;
import java.util.List;

public enum Language {
	en_US,
	pt_BR;

	private static List<String> languagesAsStringList = loadLanguagesToStringArrary();

	private static List<String> loadLanguagesToStringArrary(){		
		final Language[] values = values();
		languagesAsStringList = new ArrayList<String>();
		for (final Language language : values) {
			languagesAsStringList.add(language.name());
		}
		return languagesAsStringList;
	}
	
	public static List<String> getLanguagesAsStringList() {
		return languagesAsStringList;
	}

	public static boolean isValidLanguage(final String language) {
		return languagesAsStringList.contains(language);
	}
}
