package de.daxu.swamp.deploy.container;

public class ContainerId {

    private final String value;

    public static ContainerId of( String value ) {
        return new ContainerId( value );
    }

    private ContainerId( String value ) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals( Object o ) {
        if( this == o ) return true;
        if( o == null || getClass() != o.getClass() ) return false;

        ContainerId that = ( ContainerId ) o;

        return value != null ? value.equals( that.value ) : that.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
