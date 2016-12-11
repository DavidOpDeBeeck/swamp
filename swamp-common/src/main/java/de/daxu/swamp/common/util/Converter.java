package de.daxu.swamp.common.util;

public interface Converter<FROM, TO> {

    TO convert( FROM from );
}
