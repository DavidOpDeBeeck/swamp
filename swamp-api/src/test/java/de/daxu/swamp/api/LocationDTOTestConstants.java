package de.daxu.swamp.api;

import de.daxu.swamp.api.continent.dto.ContinentCreateDTO;
import de.daxu.swamp.api.datacenter.dto.DatacenterCreateDTO;
import de.daxu.swamp.api.server.dto.ServerCreateDTO;

import static de.daxu.swamp.core.LocationTestConstants.Continents.ANOTHER_CONTINENT_NAME;
import static de.daxu.swamp.core.LocationTestConstants.Continents.A_CONTINENT_NAME;
import static de.daxu.swamp.core.LocationTestConstants.Datacenters.ANOTHER_DATACENTER_NAME;
import static de.daxu.swamp.core.LocationTestConstants.Datacenters.A_DATACENTER_NAME;
import static de.daxu.swamp.core.LocationTestConstants.Servers.*;

public class LocationDTOTestConstants {

    public static class ContinentDTOs {

        public static ContinentCreateDTO aContinentCreateDTO() {
            return new ContinentCreateDTO.Builder()
                    .withName(A_CONTINENT_NAME)
                    .build();
        }

        public static ContinentCreateDTO anotherContinentCreateDTO() {
            return new ContinentCreateDTO.Builder()
                    .withName(ANOTHER_CONTINENT_NAME)
                    .build();
        }
    }

    public static class DatacenterDTOs {

        public static DatacenterCreateDTO aDatacenterCreateDTO() {
            return new DatacenterCreateDTO.Builder()
                    .withName(A_DATACENTER_NAME)
                    .build();
        }

        public static DatacenterCreateDTO anotherDatacenterCreateDTO() {
            return new DatacenterCreateDTO.Builder()
                    .withName(ANOTHER_DATACENTER_NAME)
                    .build();
        }
    }

    public static class ServerDTOs {

        public static ServerCreateDTO aServerCreateDTO() {
            return new ServerCreateDTO.Builder()
                    .withName(A_SERVER_NAME)
                    .withIp(A_SERVER_IP)
                    .withKey(A_SERVER_KEY)
                    .withCertificate(A_SERVER_CERTIFICATE)
                    .withCaCertificate(A_SERVER_CA_CERTIFICATE)
                    .build();
        }

        public static ServerCreateDTO anotherServerCreateDTO() {
            return new ServerCreateDTO.Builder()
                    .withName(ANOTHER_SERVER_NAME)
                    .withIp(ANOTHER_SERVER_IP)
                    .withKey(ANOTHER_SERVER_KEY)
                    .withCertificate(ANOTHER_SERVER_CERTIFICATE)
                    .withCaCertificate(ANOTHER_SERVER_CA_CERTIFICATE)
                    .build();
        }
    }
}
