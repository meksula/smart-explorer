package com.smartexplorer.core.domain.subject.spot.stats;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author
 * Karol Meksuła
 * 18-06-2018
 * */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OpinionStat {
}
