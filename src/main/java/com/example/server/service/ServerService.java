package com.example.server.service;

import com.example.server.model.Server;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;

@Service
public interface ServerService {


    Server  create(Server server);
    Collection<Server> list(int limit);
    Server ping(String ipAddress) throws IOException;
    Server get(Long id);
    Server update(Server server);
    Boolean delete(Long id);
}
