package libreSubs.libreSubsSite.commons;

import java.util.Arrays;
import java.util.Locale;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.libreSubsApplet.utils.LocaleUtil;

@SuppressWarnings("serial")
public class LanguageChooserDropDown extends DropDownChoice<Locale> {

	public LanguageChooserDropDown(final String id) {
		super(id, Arrays.asList(LocaleUtil
				.getSortedAvailableLocales()),
				new LocaleChoiceRenderer());
		setNullValid(false);

	}
}
