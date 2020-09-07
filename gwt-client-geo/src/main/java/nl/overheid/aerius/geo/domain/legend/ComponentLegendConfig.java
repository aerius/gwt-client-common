package nl.overheid.aerius.geo.domain.legend;

import java.util.Map;

public class ComponentLegendConfig extends LegendConfig {
  private String componentName;
  private String componentSource;

  private Map<String, String> parameters;

  public ComponentLegendConfig() {
    super(LegendType.COMPONENT);
  }

  public String getComponentName() {
    return componentName;
  }

  public void setComponentName(final String componentName) {
    this.componentName = componentName;
  }

  public String getComponentSource() {
    return componentSource;
  }

  public void setComponentSource(final String componentSource) {
    this.componentSource = componentSource;
  }

  public Map<String, String> getParameters() {
    return parameters;
  }

  public void setParameters(final Map<String, String> parameters) {
    this.parameters = parameters;
  }
}
