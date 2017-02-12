package de.daxu.swamp.scheduling.query.build;

import de.daxu.swamp.scheduling.command.build.BuildId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildViewRepository extends JpaRepository<BuildView, String> {

    BuildView getByBuildId(@Param( "build_id" ) BuildId buildId);
}
