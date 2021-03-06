package de.daxu.swamp.docker.adapter;

import com.github.dockerjava.core.SSLConfig;
import com.github.dockerjava.core.util.CertificateUtils;
import de.daxu.swamp.core.server.Server;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.glassfish.jersey.SslConfigurator;

import javax.net.ssl.SSLContext;
import java.security.*;

public class DockerClientSSLConfig implements SSLConfig {

    private Server server;

    DockerClientSSLConfig(Server server) {
        this.server = server;
    }

    @Override
    public SSLContext getSSLContext() throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {
        try {
            Security.addProvider(new BouncyCastleProvider());

            String httpProtocols = System.getProperty("https.protocols");
            System.setProperty("https.protocols", "TLSv1");
            SslConfigurator sslConfig = SslConfigurator.newInstance(true);

            if (httpProtocols != null)
                System.setProperty("https.protocols", httpProtocols);

            String keyPem = server.getKey();
            String certPem = server.getCertificate();
            String caPem = server.getCaCertificate();

            sslConfig.keyStore(CertificateUtils.createKeyStore(keyPem, certPem));
            sslConfig.keyStorePassword("docker");
            sslConfig.trustStore(CertificateUtils.createTrustStore(caPem));

            return sslConfig.createSSLContext();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
