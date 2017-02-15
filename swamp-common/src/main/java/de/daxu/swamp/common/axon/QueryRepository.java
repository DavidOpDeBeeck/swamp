package de.daxu.swamp.common.axon;

import org.springframework.stereotype.Repository;

import java.lang.annotation.*;

@Inherited
@Repository
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface QueryRepository {

}
