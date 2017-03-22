package de.daxu.swamp.core.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServerService {

    private final ServerRepository serverRepository;

    @Autowired
    public ServerService(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    public Server getServer(String id) {
        return serverRepository.findOne(id);
    }

    public Server getServerByName(String name) {
        return serverRepository.findByName(name);
    }

    public Server updateServer(Server server) {
        return serverRepository.save(server);
    }

    public List<Server> getAllServers() {
        return serverRepository.findAll();
    }

}
