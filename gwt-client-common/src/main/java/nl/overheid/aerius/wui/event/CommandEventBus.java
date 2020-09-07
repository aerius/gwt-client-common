/*
 * Copyright Dutch Ministry of Agriculture, Nature and Food Quality
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
package nl.overheid.aerius.wui.event;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.SimpleEventBus;

import nl.overheid.aerius.wui.command.Command;

public class CommandEventBus extends SimpleEventBus {
  private static final boolean DEBUG;
  static {
    DEBUG = false;
  }

  private boolean commandInProgress;

  private final List<CommandSourceHandle> defferredCommands = new ArrayList<>();

  private static class CommandSourceHandle {
    private final Event<?> command;
    private Object source;

    public CommandSourceHandle(final Event<?> command) {
      this.command = command;
    }

    public CommandSourceHandle(final Event<?> command, final Object source) {
      this.command = command;
      this.source = source;
    }
  }

  @Override
  public void fireEvent(final Event<?> event) {
    final boolean deferred = checkDefer(event);
    if (deferred) {
      return;
    }

    final boolean enforce = prefire(event);

    if (enforce && DEBUG) {
      GWT.log("");
      GWT.log("Beginning command execution: " + event.getClass().getSimpleName());
    }

    super.fireEvent(event);

    if (enforce && DEBUG) {
      GWT.log("Command execution complete.");
    }

    postCommand(enforce, event);

    if (enforce && DEBUG) {
      GWT.log("Finishing command: " + event.getClass().getSimpleName());
      GWT.log("");
    }

    executeDeferred(enforce);
  }

  @Override
  public void fireEventFromSource(final Event<?> event, final Object source) {
    final boolean defer = checkDeferFromSource(event, source);
    if (defer) {
      return;
    }

    final boolean enforce = prefire(event);

    if (enforce && DEBUG) {
      GWT.log("Beginning command execution: " + event.getClass().getSimpleName());
    }

    super.fireEventFromSource(event, source);

    if (enforce && DEBUG) {
      GWT.log("Command execution complete.");
    }

    postCommand(enforce, event, source);

    if (enforce && DEBUG) {
      GWT.log("Finishing command: " + event.getClass().getSimpleName());
      GWT.log("");
    }

    executeDeferred(enforce);

  }

  private void executeDeferred(final boolean enforce) {
    if (!enforce || defferredCommands.isEmpty()) {
      return;
    }

    GWT.log("Executing deferred commands.");

    final CommandSourceHandle handle = defferredCommands.remove(0);

    if (handle.source != null) {
      fireEventFromSource(handle.command, handle.source);
    } else {
      fireEvent(handle.command);
    }
  }

  private boolean checkDefer(final Event<?> event) {
    final boolean command = isCommand(event);
    if (command && commandInProgress) {
      GWT.log("Deferring command. " + event);
      defferredCommands.add(new CommandSourceHandle(event));
      return true;
    }

    return false;
  }

  private boolean checkDeferFromSource(final Event<?> event, final Object source) {
    final boolean command = isCommand(event);
    if (command && commandInProgress) {
      GWT.log("Deferring command. " + event);
      defferredCommands.add(new CommandSourceHandle(event, source));
      return true;
    }

    return false;
  }

  private boolean prefire(final Event<?> event) {
    final boolean enforce = isCommand(event);
    if (enforce) {
      commandInProgress = true;
    }

    return enforce;
  }

  private void postCommand(final boolean enforce, final Event<?> event) {
    postCommand(enforce, event, null);
  }

  private void postCommand(final boolean enforce, final Event<?> event, final Object source) {
    if (enforce) {
      commandInProgress = false;

      // Silence state could have been changed in-flight
      if (!isSilent(event)) {
        // Finish the command by firing its event.
        if (source == null) {
          fireEvent(((Command<?>) event).getEvent());
        } else {
          fireEventFromSource(((Command<?>) event).getEvent(), source);
        }
      }
    }
  }

  private boolean isSilent(final Event<?> event) {
    return ((Command<?>) event).isSilent();
  }

  private boolean isCommand(final Event<?> command) {
    return command instanceof Command;
  }
}