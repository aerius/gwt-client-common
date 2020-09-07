package nl.overheid.aerius.geo.legend;

import java.io.Serializable;

/**
 * Data class for a legend with a image representing the legend.
 */
public class ImageLegend implements Legend, Serializable {
  private static final long serialVersionUID = 3095825941472442382L;

  private String imageUrl;

  public ImageLegend() {}

  public ImageLegend(final String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(final String imageUrl) {
    this.imageUrl = imageUrl;
  }

  @Override
  public String toString() {
    return "ImageLegend [imageUrl=" + imageUrl + "]" + super.toString();
  }
}

