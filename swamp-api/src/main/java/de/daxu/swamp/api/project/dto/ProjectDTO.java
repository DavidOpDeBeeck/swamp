package de.daxu.swamp.api.project.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.daxu.swamp.common.jackson.LocalDateTimeSerializer;

import java.time.LocalDateTime;

public class ProjectDTO {

    public String id;
    public String name;
    public String description;
    @JsonSerialize( using = LocalDateTimeSerializer.class )
    public LocalDateTime created;
    public int containers;

}
