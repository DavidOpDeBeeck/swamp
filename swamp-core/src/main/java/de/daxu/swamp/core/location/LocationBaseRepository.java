package de.daxu.swamp.core.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

@NoRepositoryBean
public interface LocationBaseRepository<T extends Location> extends JpaRepository<T, String> {

    T findByName( @Param( "name" ) String name );

}
