package de.daxu.swamp.scheduling.read;

import de.daxu.swamp.scheduling.read.containerinstance.ContainerInstanceView;
import de.daxu.swamp.scheduling.read.containerinstance.ContainerInstanceViewRepository;
import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static de.daxu.swamp.scheduling.ContainerInstanceTestConstants.ANOTHER_CONTAINER_INSTANCE_VIEW;
import static de.daxu.swamp.scheduling.ContainerInstanceTestConstants.CONTAINER_INSTANCE_ID;
import static de.daxu.swamp.scheduling.ContainerInstanceTestConstants.CONTAINER_INSTANCE_VIEW;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith( MockitoJUnitRunner.class )
public class ContainerInstanceReadServiceTest {

    @Mock
    private ContainerInstanceViewRepository containerInstanceViewRepository;

    @InjectMocks
    private ContainerInstanceReadService containerInstanceReadService;

    @Test
    public void getContainerInstanceViewsByStatus() throws Exception {
        when( containerInstanceViewRepository.getByStatus( ContainerInstanceStatus.STARTED ) )
                .thenReturn( newArrayList( CONTAINER_INSTANCE_VIEW, ANOTHER_CONTAINER_INSTANCE_VIEW ) );

        List<ContainerInstanceView> views = containerInstanceReadService
                .getContainerInstanceViewsByStatus( ContainerInstanceStatus.STARTED );

        assertThat( views ).isNotNull();
        assertThat( views.size() ).isEqualTo( 2 );
        assertThat( views ).containsExactlyInAnyOrder( CONTAINER_INSTANCE_VIEW, ANOTHER_CONTAINER_INSTANCE_VIEW );
    }

    @Test
    public void getContainerInstanceViewsById() throws Exception {
        when( containerInstanceViewRepository.getByContainerInstanceId( CONTAINER_INSTANCE_ID ) )
                .thenReturn( CONTAINER_INSTANCE_VIEW );

        ContainerInstanceView view = containerInstanceReadService
                .getContainerInstanceViewById( CONTAINER_INSTANCE_ID );

        assertThat( view ).isNotNull();
        assertThat( view ).isEqualToComparingFieldByField( CONTAINER_INSTANCE_VIEW );
    }

}