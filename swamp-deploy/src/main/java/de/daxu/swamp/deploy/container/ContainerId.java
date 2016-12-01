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
}
