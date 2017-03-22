package de.daxu.swamp.api.version.dto;

import de.daxu.swamp.common.ValueObject;

public class VersionDTO extends ValueObject {

    private String version;

    @SuppressWarnings("unused")
    private VersionDTO() {
    }

    public VersionDTO(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }
}
