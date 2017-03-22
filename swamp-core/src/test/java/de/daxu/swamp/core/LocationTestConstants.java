package de.daxu.swamp.core;

import de.daxu.swamp.core.continent.Continent;
import de.daxu.swamp.core.datacenter.Datacenter;
import de.daxu.swamp.core.server.Server;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class LocationTestConstants {

    public static class Continents {

        public final static String A_CONTINENT_NAME = "continentName";
        public final static String ANOTHER_CONTINENT_NAME = "anotherContinentName";

        public static Continent aContinent() {
            return aContinentBuilder()
                    .build();
        }

        public static Continent aContinent(Datacenter datacenter) {
            return aContinentBuilder()
                    .withDatacenters(newHashSet(datacenter))
                    .build();
        }

        public static Continent anotherContinent() {
            return aContinentBuilder()
                    .withName(ANOTHER_CONTINENT_NAME)
                    .build();
        }

        public static Continent.Builder aContinentBuilder() {
            return new Continent.Builder()
                    .withName(A_CONTINENT_NAME)
                    .withDatacenters(newHashSet());
        }

    }

    public static class Datacenters {

        public final static String A_DATACENTER_NAME = "datacenterName";
        public final static String ANOTHER_DATACENTER_NAME = "anotherDatacenterName";
        public final static Set<Server> DATACENTER_SERVERS = newHashSet();
        public final static Set<Server> ANOTHER_DATACENTER_SERVERS = newHashSet();

        public static Datacenter aDatacenter() {
            return aDatacenterBuilder()
                    .build();
        }

        public static Datacenter aDatacenter(Server server) {
            return aDatacenterBuilder()
                    .withServers(newHashSet(server))
                    .build();
        }

        public static Datacenter anotherDatacenter() {
            return aDatacenterBuilder()
                    .withName(ANOTHER_DATACENTER_NAME)
                    .withServers(newHashSet())
                    .build();
        }

        public static Datacenter.Builder aDatacenterBuilder() {
            return new Datacenter.Builder()
                    .withName(A_DATACENTER_NAME)
                    .withServers(newHashSet());
        }

    }

    public static class Servers {

        public final static String A_SERVER_NAME = "serverName";
        public final static String ANOTHER_SERVER_NAME = "anotherServerName";
        public final static String A_SERVER_IP = "serverIp";
        public final static String ANOTHER_SERVER_IP = "anotherServerIp";
        public final static String A_SERVER_CA_CERTIFICATE = "serverCaCertificate";
        public final static String ANOTHER_SERVER_CA_CERTIFICATE = "anotherServerCaCertificate";
        public final static String A_SERVER_CERTIFICATE = "serverCertificate";
        public final static String ANOTHER_SERVER_CERTIFICATE = "anotherServerCertificate";
        public final static String A_SERVER_KEY = "serverKey";
        public final static String ANOTHER_SERVER_KEY = "anotherServerKey";

        public static Server aServer() {
            return aServerBuilder().build();
        }

        public static Server anotherServer() {
            return aServerBuilder()
                    .withName(ANOTHER_SERVER_NAME)
                    .withIp(ANOTHER_SERVER_IP)
                    .withKey(ANOTHER_SERVER_KEY)
                    .withCertificate(ANOTHER_SERVER_CERTIFICATE)
                    .withCaCertificate(ANOTHER_SERVER_CA_CERTIFICATE)
                    .build();
        }

        public static Server.Builder aServerBuilder() {
            return new Server.Builder()
                    .withName(A_SERVER_NAME)
                    .withIp(A_SERVER_IP)
                    .withKey(A_SERVER_KEY)
                    .withCertificate(A_SERVER_CERTIFICATE)
                    .withCaCertificate(A_SERVER_CA_CERTIFICATE);
        }
    }

}
