package org.swisscheese.swisscheese.annotations;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Target;

@Target(TYPE)
public @interface NotThreadSafe {

}
