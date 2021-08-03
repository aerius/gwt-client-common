/*
 * Copyright the State of the Netherlands
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
package nl.aerius.wui.replacing;

import java.util.Map;

import com.google.inject.Inject;

import nl.aerius.wui.event.BasicEventComponent;
import nl.aerius.wui.util.BracketedTemplatedString;

public abstract class GenericReplacementAssistant extends BasicEventComponent implements ReplacementAssistant {
  @Inject
  public GenericReplacementAssistant() {}

  protected BracketedTemplatedString replaceIndistinct(final Map<String, String> replacements, final BracketedTemplatedString ret) {
    replacements.forEach(ret::replace);

    return ret;
  }

  @Override
  public String replace(final String origin) {
    final BracketedTemplatedString ret = new BracketedTemplatedString(origin);

    final Map<String, String> replacements = createReplacements();
    replaceIndistinct(replacements, ret);

    // Purge all left-over replacement tags.
    ret.replaceClean("\\{([^}]+)\\}", "");

    return ret.toString();
  }

  @Override
  public String replaceStrict(final String origin) {
    final BracketedTemplatedString ret = new BracketedTemplatedString(origin);

    final Map<String, String> hardReplacements = createReplacements();
    replaceIndistinct(hardReplacements, ret);

    cleanSoftKeys(ret);
    final String returnString = ret.toString();

    // TODO I'd prefer to do this with a regex, but the regex (above) seems to be
    // broken in some cases
    if (returnString.contains("{") && returnString.contains("}") && returnString.indexOf("{") < returnString.indexOf("}")) {
      throw new IllegalStateException("Unreplaced tags were left in origin String: " + returnString);
    }

    return returnString;
  }

  protected abstract void cleanSoftKeys(BracketedTemplatedString string);

  protected abstract Map<String, String> createReplacements();
}
