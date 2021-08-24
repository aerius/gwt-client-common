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

import com.google.web.bindery.event.shared.binder.GenericEvent;

/**
 * A command that is in fact a GenericEvent, and exists solely to make the
 * semantics of the command pattern continue to make sense. This command is used
 * when no corresponding Event is (currently) necessary, but the semantics of
 * the pattern still dictate something is being "commanded". i.e. this command
 * is fired as a result of user action but no UI reaction is expected.
 *
 * It is expected that most commands are of this type. Note: Extending a
 * GenericEvent instead of a StatelessCommand is also completely acceptable.
 */
public class StatelessCommand extends GenericEvent {}
