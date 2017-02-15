package de.daxu.swamp.common.util;

import org.springframework.aop.framework.Advised;

public class SpringUtils {

    public static String getTargetClassName(Object obj) {
        Advised advised = (Advised) obj;
        return advised.getTargetSource().getTargetClass().getSimpleName();
    }

}
