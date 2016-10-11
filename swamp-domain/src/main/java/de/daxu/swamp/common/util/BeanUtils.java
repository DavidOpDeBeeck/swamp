package de.daxu.swamp.common.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.FeatureDescriptor;
import java.util.stream.Stream;

public class BeanUtils {

    private static String[] getNullPropertyNames( Object source ) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl( source );
        return Stream.of( wrappedSource.getPropertyDescriptors() )
                .map( FeatureDescriptor::getName )
                .filter( propertyName -> wrappedSource.getPropertyValue( propertyName ) == null )
                .toArray( String[]::new );
    }

    public static void copyProperties( Object src, Object target ) {
        org.springframework.beans.BeanUtils.copyProperties( src, target, getNullPropertyNames( src ) );
    }
}
