package de.daxu.swamp.core;

import de.daxu.swamp.core.datacenter.Datacenter;
import de.daxu.swamp.core.server.Server;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class LocationTestConstants {

    public static class Continents {

        public final static String CONTINENT_NAME = "continentName";
        public final static String ANOTHER_CONTINENT_NAME = "anotherContinentName";
        public final static Set<Datacenter> CONTINENT_DATACENTERS = newHashSet();
        public final static Set<Datacenter> ANOTHER_CONTINENT_DATACENTERS = newHashSet();

    }

    public static class Datacenters {

        public final static String DATACENTER_NAME = "datacenterName";
        public final static String ANOTHER_DATACENTER_NAME = "anotherDatacenterName";
        public final static Set<Server> DATACENTER_SERVERS = newHashSet();
        public final static Set<Server> ANOTHER_DATACENTER_SERVERS = newHashSet();

    }

    public static class Servers {

        public final static String SERVER_NAME = "serverName";
        public final static String ANOTHER_SERVER_NAME = "anotherServerName";
        public final static String SERVER_IP = "serverIp";
        public final static String ANOTHER_SERVER_IP = "anotherServerIp";
        public final static String SERVER_CA_CERTIFICATE = "serverCACertificate";
        public final static String ANOTHER_SERVER_CA_CERTIFICATE = "anotherServerCACertificat";
        public final static String SERVER_CERTIFICATE = "serverCertificate";
        public final static String ANOTHER_SERVER_CERTIFICATE = "anotherServerCertificate";
        public final static String SERVER_KEY = "serverKey";
        public final static String ANOTHER_SERVER_KEY = "anotherServerKey";

    }

}
