package de.daxu.swamp.common.axon;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Inherited
@Component
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ProcessManager {

}
