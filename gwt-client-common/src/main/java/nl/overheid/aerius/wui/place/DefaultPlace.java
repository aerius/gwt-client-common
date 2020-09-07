package nl.overheid.aerius.wui.place;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.google.inject.BindingAnnotation;

/**
 * Annotation for injecting the application specific default place.
 */
@Retention(RetentionPolicy.RUNTIME)
@BindingAnnotation
public @interface DefaultPlace {}