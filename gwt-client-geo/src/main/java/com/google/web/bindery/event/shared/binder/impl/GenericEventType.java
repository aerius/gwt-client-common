/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.web.bindery.event.shared.binder.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.GenericEvent;

/**
 * Utility to maintain a mapping from subtypes of {@link GenericEvent} to {@link Type}s for those events. Users shouldn't need to reference this class
 * directly.
 *
 * @author ekuefler@google.com (Erik Kuefler)
 */
public class GenericEventType extends Type<GenericEventHandler> {

  private static final Map<Class<?>, GenericEventType> TYPE_MAP = new HashMap<Class<?>, GenericEventType>();

  /**
   * Creates a new EventType for the given event class. Repeated invocations of this method for the same type will return the same object. This method
   * is called by generated {@link EventBinder}s and shouldn't normally have to be called directly by users.
   */
  public static <T extends GenericEvent> GenericEventType getTypeOf(final Class<T> clazz) {

    GenericEventType eventType = TYPE_MAP.get(clazz);

    if (eventType == null) {
      eventType = new GenericEventType();
      TYPE_MAP.put(clazz, eventType);
    }

    return eventType;
  }

  @SuppressWarnings("unchecked")
  public static <T> Class<T> getReverseType(final GenericEventType type) {
    for (final Entry<Class<?>, GenericEventType> entry : TYPE_MAP.entrySet()) {
      if (entry.getValue().equals(type)) {
        return (Class<T>) entry.getKey();
      }
    }

    return null;
  }

  private GenericEventType() {}
}
