package ol.style;

import ol.Options;
import ol.Size;
import ol.color.Color;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 * Options for the icon style.
 *
 * @author Tino Desjardins
 */
@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class IconOptions implements Options {

    /**
     * @param color Color to tint the icon. If not specified, the icon will be left as is.
     */
    @JsProperty
    public native void setColor(Color color);

    /**
     * AERIUS FIX: This property was missing in the original
     */
    @JsProperty
    public native void setAnchorXUnits(String units);

    /**
     * AERIUS FIX: This property was missing in the original
     */
    @JsProperty
    public native void setAnchorYUnits(String units);

    /**
     * AERIUS FIX: This property was missing in the original
     */
    @JsProperty
    public native void setAnchor(double[] anchor);

    /**
     * @param imgSize Image size in pixels. Only required if img is set and src is not, and
     * for SVG images in Internet Explorer 11. The provided imgSize needs to match the actual
     * size of the image.
     */
    @JsProperty
    public native void setImgSize(Size imgSize);

    /**
     * @param opacity Opacity of the icon. Default is 1.
     */
    @JsProperty
    public native void setOpacity(double opacity);

    /**
     * @param rotation Rotation in radians (positive rotation clockwise). Default is 0.
     */
    @JsProperty
    public native void setRotation(double rotation);

    /**
     * @param rotateWithView Whether to rotate the icon with the view. Default is false.
     */
    @JsProperty
    public native void setRotateWithView(boolean rotateWithView);

    /**
     * @param scale Scale.
     */
    @JsProperty
    public native void setScale(double scale);

    /**
     * @param size Icon size in pixel. Can be used together with offset to define the
     * sub-rectangle to use from the origin (sprite) icon image.
     */
    @JsProperty
    public native void setSize(Size size);

    /**
     * @param snapToPixel If true integral numbers of pixels are used as the X and Y pixel
     * coordinate when drawing the icon in the output canvas. If false fractional numbers
     * may be used. Using true allows for "sharp" rendering (no blur), while using false
     * allows for "accurate" rendering. Note that accuracy is important if the icon's position
     * is animated. Without it, the icon may jitter noticeably. Default value is true.
     */
    @JsProperty
    public native void setSnapToPixel(boolean snapToPixel);

    /**
     * @param imageUri Image source URI
     */
    @JsProperty
    public native void setSrc(String imageUri);

    /**
     * AERIUS FIX: This property was missing in the original
     */
    @JsProperty
    public native void setImg(Object src);

}
