package de.daxu.swamp.common.axon;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Inherited
@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventListener {

    boolean replayable() default true;
}
