package nl.overheid.aerius.geo.wui;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.axellience.vuegwt.core.annotations.component.Component;
import com.axellience.vuegwt.core.annotations.component.Data;
import com.axellience.vuegwt.core.annotations.component.Prop;
import com.axellience.vuegwt.core.annotations.component.Watch;
import com.axellience.vuegwt.core.client.component.IsVueComponent;
import com.axellience.vuegwt.core.client.component.hooks.HasMounted;
import com.google.gwt.user.client.DOM;
import com.google.web.bindery.event.shared.EventBus;

import jsinterop.base.Js;

import nl.overheid.aerius.geo.domain.IsMapCohort;
import nl.overheid.aerius.geo.event.MapEventBus;

@Component(name = "ol3-map")
public class OL3MapComponent implements Map, HasMounted, IsVueComponent {
  @Data String uniqueId = "openlayers-3-map-auto-generated-" + DOM.createUniqueId();

  @Inject MapLayoutPanel map;

  @Prop EventBus eventBus;

  private MapEventBus mapEventBus;

  private final List<IsMapCohort> cohorts = new ArrayList<>();

  @Watch(value = "eventBus", isImmediate = true)
  public void watchMessage(final EventBus newValue, final EventBus oldValue) {
    setEventBus(newValue);
  }

  @Override
  public void setEventBus(final EventBus eventBus) {
    this.mapEventBus = new MapEventBus(eventBus, uniqueId);
    map.setEventBus(this.mapEventBus);
  }

  @Override
  public void mounted() {
    attach();
  }

  @Override
  public void attach() {
    map.setTarget(uniqueId);
  }

  @Override
  public void setUniqueId(final String uniqueId) {
    this.uniqueId = uniqueId;
  }

  @Override
  public String getUniqueId() {
    return uniqueId;
  }

  @Override
  public void registerEventCohort(final IsMapCohort cohort) {
    if (mapEventBus != null) {
      cohort.notifyMap(this, mapEventBus);
    }

    cohorts.add(cohort);
  }

  public MapEventBus getMapEventBus() {
    return mapEventBus;
  }

  @Override
  public <T> T getMap() {
    return Js.cast(map.getMap());
  }
}
