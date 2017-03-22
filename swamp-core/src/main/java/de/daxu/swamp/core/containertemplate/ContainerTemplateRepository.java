package de.daxu.swamp.core.containertemplate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContainerTemplateRepository extends JpaRepository<ContainerTemplate, String> {

}
