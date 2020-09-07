/*******************************************************************************
 * Copyright 2014, 2018 gwt-ol3
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package ol.source;

import ol.Params;
import ol.Size;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 * @author Tino Desjardins
 */
@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class ImageWmsParams implements Params {

  @JsOverlay
  public static final String DELIMITER = ",";

  @JsProperty(name = "LAYERS")
  public native void setLayers(String layers);

  @JsOverlay
  public final void setLayers(final String... layerNames) {
    setLayers(String.join(ImageWmsParams.DELIMITER, layerNames));
  }

  @JsProperty(name = "LAYERS")
  public native String getLayers();

  @JsProperty(name = "WIDTH")
  public native void setWidth(int width);

  @JsProperty(name = "HEIGHT")
  public native void setHeight(int height);

  @JsProperty(name = "viewparams")
  public native void setViewParams(String params);

  @JsProperty(name = "STYLES")
  public native void setStyles(String styles);

  @JsProperty(name = "FORMAT")
  public native void setFormat(String format);

  @JsOverlay
  public final void setStyles(final String... styles) {
    setStyles(String.join(ImageWmsParams.DELIMITER, styles));
  }

  @JsOverlay
  public final void setSize(final Size size) {
    setWidth(size.getWidth());
    setHeight(size.getHeight());
  }

  @JsProperty(name = "VERSION")
  public native void setVersion(String version);

}
