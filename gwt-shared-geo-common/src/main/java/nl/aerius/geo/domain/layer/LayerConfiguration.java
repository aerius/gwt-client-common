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
package nl.aerius.geo.domain.layer;

import java.io.Serializable;

public class LayerConfiguration implements Serializable {
  private static final long serialVersionUID = 1L;

  private final String name;
  private final String title;
  private final boolean visible;
  private final double opacity;
  private final String bundleName;
  private final String clusterName;
  private final String friendlyName;

  private LayerConfiguration(
      final String name,
      final String title,
      final boolean visible,
      final double opacity,
      final String bundleName,
      final String clusterName,
      final String friendlyName) {
    this.name = name;
    this.title = title;
    this.visible = visible;
    this.opacity = opacity;
    this.bundleName = bundleName;
    this.clusterName = clusterName;
    this.friendlyName = friendlyName;
  }

  public static Builder builder() {
    return new LayerConfiguration.Builder()
        .visible(true)
        .opacity(1D);
  }

  public String name() {
    return name;
  }

  public String title() {
    return title;
  }

  public boolean visible() {
    return visible;
  }

  public double opacity() {
    return opacity;
  }

  public String bundleName() {
    return bundleName;
  }

  public String clusterName() {
    return clusterName;
  }

  public String friendlyName() {
    return friendlyName;
  }

  @Override
  public String toString() {
    return "LayerConfiguration{"
        + "name=" + name + ", "
        + "title=" + title + ", "
        + "visible=" + visible + ", "
        + "opacity=" + opacity + ", "
        + "bundleName=" + bundleName + ", "
        + "clusterName=" + clusterName + ", "
        + "friendlyName=" + friendlyName
        + "}";
  }

  @Override
  public boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof LayerConfiguration) {
      final LayerConfiguration that = (LayerConfiguration) o;
      return this.name.equals(that.name())
          && this.title.equals(that.title())
          && this.visible == that.visible()
          && Double.doubleToLongBits(this.opacity) == Double.doubleToLongBits(that.opacity())
          && (this.bundleName == null ? that.bundleName() == null : this.bundleName.equals(that.bundleName()))
          && (this.clusterName == null ? that.clusterName() == null : this.clusterName.equals(that.clusterName()))
          && (this.friendlyName == null ? that.friendlyName() == null : this.friendlyName.equals(that.friendlyName()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= name.hashCode();
    h$ *= 1000003;
    h$ ^= title.hashCode();
    h$ *= 1000003;
    h$ ^= visible ? 1231 : 1237;
    h$ *= 1000003;
    h$ ^= (int) ((Double.doubleToLongBits(opacity) >>> 32) ^ Double.doubleToLongBits(opacity));
    h$ *= 1000003;
    h$ ^= (bundleName == null) ? 0 : bundleName.hashCode();
    h$ *= 1000003;
    h$ ^= (clusterName == null) ? 0 : clusterName.hashCode();
    h$ *= 1000003;
    h$ ^= (friendlyName == null) ? 0 : friendlyName.hashCode();
    return h$;
  }

  public static final class Builder {
    private String name;
    private String title;
    private Boolean visible;
    private Double opacity;
    private String bundleName;
    private String clusterName;
    private String friendlyName;

    Builder() {
    }

    public LayerConfiguration.Builder name(final String name) {
      if (name == null) {
        throw new NullPointerException("Null name");
      }
      this.name = name;
      return this;
    }

    public LayerConfiguration.Builder title(final String title) {
      if (title == null) {
        throw new NullPointerException("Null title");
      }
      this.title = title;
      return this;
    }

    public LayerConfiguration.Builder visible(final boolean visible) {
      this.visible = visible;
      return this;
    }

    public LayerConfiguration.Builder opacity(final double opacity) {
      this.opacity = opacity;
      return this;
    }

    public LayerConfiguration.Builder bundleName(final String bundleName) {
      this.bundleName = bundleName;
      return this;
    }

    public LayerConfiguration.Builder clusterName(final String clusterName) {
      this.clusterName = clusterName;
      return this;
    }

    public LayerConfiguration.Builder friendlyName(final String friendlyName) {
      this.friendlyName = friendlyName;
      return this;
    }

    public LayerConfiguration build() {
      String missing = "";
      if (this.name == null) {
        missing += " name";
      }
      if (this.title == null) {
        missing += " title";
      }
      if (this.visible == null) {
        missing += " visible";
      }
      if (this.opacity == null) {
        missing += " opacity";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new LayerConfiguration(
          this.name,
          this.title,
          this.visible,
          this.opacity,
          this.bundleName,
          this.clusterName,
          this.friendlyName);
    }
  }
}
