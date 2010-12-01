package libreSubs.libreSubsSite.downloadPage;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.util.value.ValueMap;
import org.libreSubsApplet.utils.SubtitleResourceResolver;

public class SubRequestParameters {

	private final String id;
	private final String language;
	private final String file;

	public SubRequestParameters(final ValueMap parameters) {
		final CharSequence idParamValue = parameters
				.getCharSequence(SubtitleResourceResolver.idParameter);

		if (idParamValue == null) {
			id = null;
		} else {
			id = idParamValue.toString();
		}

		final CharSequence langParamValue = parameters
				.getCharSequence(SubtitleResourceResolver.langParameter);

		if (langParamValue == null) {
			language = null;
		} else {
			language = langParamValue.toString();
		}

		final CharSequence fileParamValue = parameters
				.getCharSequence(SubtitleResourceResolver.fileParameter);

		if (fileParamValue == null) {
			file = null;
		} else {
			file = fileParamValue.toString();
		}
	}

	public boolean hasAllObrigatoryParameters() {
		return (getId() != null) && (getLanguage() != null);
	}

	public String getLackingParametersNames() {
		String parametersLacking = "";
		if (getId() == null)
			parametersLacking += SubtitleResourceResolver.idParameter + ",";
		if (getLanguage() == null)
			parametersLacking += SubtitleResourceResolver.langParameter + ",";
		return StringUtils.substringBeforeLast(parametersLacking, ",");
	}

	public String getId() {
		return id;
	}

	public String getLanguage() {
		return language;
	}

	public String getFile() {
		return file;
	}

}
