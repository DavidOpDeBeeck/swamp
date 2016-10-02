package de.daxu.swamp.repository.location;

import de.daxu.swamp.core.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface LocationBaseRepository<T extends Location> extends JpaRepository<T, String> {

}
