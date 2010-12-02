package libreSubs.libreSubsSite.commons;

import java.util.Locale;

import org.apache.wicket.markup.html.form.IChoiceRenderer;

@SuppressWarnings("serial")
public class LocaleChoiceRenderer implements IChoiceRenderer<Locale> {

	@Override
	public Object getDisplayValue(final Locale localeModel) {
		return localeModel.getDisplayName();
	}

	@Override
	public String getIdValue(final Locale localeModel, final int index) {
		return localeModel.toString();
	}

}
