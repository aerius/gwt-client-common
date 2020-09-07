package nl.overheid.aerius.wui.replacing;

import java.util.Map;

import com.google.inject.Inject;

import nl.overheid.aerius.wui.event.BasicEventComponent;
import nl.overheid.aerius.wui.util.TemplatedString;

public abstract class GenericReplacementAssistant extends BasicEventComponent implements ReplacementAssistant {
  @Inject
  public GenericReplacementAssistant() {}

  protected TemplatedString replaceIndistinct(final Map<String, String> replacements, final TemplatedString ret) {
    replacements.forEach(ret::replace);

    return ret;
  }

  @Override
  public String replace(final String origin) {
    final TemplatedString ret = new TemplatedString(origin);

    final Map<String, String> replacements = createReplacements();
    replaceIndistinct(replacements, ret);

    // Purge all left-over replacement tags.
    ret.replaceClean("\\{([^}]+)\\}", "");

    return ret.toString();
  }

  @Override
  public String replaceStrict(final String origin) {
    final TemplatedString ret = new TemplatedString(origin);

    final Map<String, String> hardReplacements = createReplacements();
    replaceIndistinct(hardReplacements, ret);

    cleanSoftKeys(ret);
    final String returnString = ret.toString();

    // TODO I'd prefer to do this with a regex, but the regex (above) seems to be broken in some cases
    if (returnString.contains("{") && returnString.contains("}") && returnString.indexOf("{") < returnString.indexOf("}")) {
      throw new IllegalStateException("Unreplaced tags were left in origin String: " + returnString);
    }

    return returnString;
  }

  protected abstract void cleanSoftKeys(TemplatedString string);

  protected abstract Map<String, String> createReplacements();
}
