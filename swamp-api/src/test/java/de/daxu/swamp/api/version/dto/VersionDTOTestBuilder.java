package de.daxu.swamp.api.version.dto;

public class VersionDTOTestBuilder {

    private String version = "a version";

    public static VersionDTOTestBuilder aVersionDTO() {
        return new VersionDTOTestBuilder();
    }

    public VersionDTOTestBuilder withVersion( String version ) {
        this.version = version;
        return this;
    }

    public VersionDTO build() {
        VersionDTO dto = new VersionDTO();
        dto.version = version;
        return dto;
    }
}