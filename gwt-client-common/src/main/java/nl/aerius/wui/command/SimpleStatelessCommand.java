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
package nl.aerius.wui.command;

import com.google.gwt.user.client.TakesValue;
import com.google.web.bindery.event.shared.binder.GenericEvent;

/**
 * This command is used when no corresponding Event is (currently) necessary,
 * but the semantics of the pattern still dictate something is being
 * "commanded". i.e. this command is fired as a result of user action but no UI
 * reaction is expected.
 */
public class SimpleStatelessCommand<V> extends GenericEvent implements Command<GenericEvent>, TakesValue<V> {
  private V object;

  public SimpleStatelessCommand() {}

  public SimpleStatelessCommand(final V object) {
    this.object = object;
  }

  @Override
  public void setSilent(final boolean silent) {
    // Do nothing
  }

  @Override
  public boolean isSilent() {
    return true;
  }

  @Override
  public GenericEvent getEvent() {
    return null;
  }

  @Override
  public V getValue() {
    return object;
  }

  @Override
  public void setValue(final V current) {
    object = current;
  }
}
