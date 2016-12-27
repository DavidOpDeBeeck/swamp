package de.daxu.swamp.deploy.group;

public class GroupId {

    private final String value;

    public static GroupId of( String value ) {
        return new GroupId( value );
    }

    private GroupId( String value ) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals( Object o ) {
        if( this == o ) return true;
        if( o == null || getClass() != o.getClass() ) return false;

        GroupId that = ( GroupId ) o;

        return value != null ? value.equals( that.value ) : that.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
