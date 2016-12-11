package de.daxu.swamp.common.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.FeatureDescriptor;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class BeanUtils {

    private static String[] getIgnoredProperties( Object source ) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl( source );
        return Stream.of( wrappedSource.getPropertyDescriptors() )
                .map( FeatureDescriptor::getName )
                .filter( getNotWritableOrNullProperties( wrappedSource ) )
                .toArray( String[]::new );
    }

    private static Predicate<String> getNotWritableOrNullProperties( BeanWrapper bean ) {
        return propertyName -> !bean.isWritableProperty( propertyName )
                || bean.getPropertyValue( propertyName ) == null;
    }

    public static void copyProperties( Object src, Object target ) {
        org.springframework.beans.BeanUtils.copyProperties( src, target, getIgnoredProperties( src ) );
    }
}
