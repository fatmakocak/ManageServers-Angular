package com.example.server.service.Impl;

import com.example.server.model.Server;
import com.example.server.model.Status;
import com.example.server.repo.ServerRepository;
import com.example.server.service.ServerService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {

    private final ServerRepository serverRepository;

    @Override
    public Server create(Server server) {
        log.info("saving new server:{}", server.getName());
       server.setImageUrl(setServerImageUrl());
       return serverRepository.save(server);
    }


    @Override
    public Collection<Server> list(int limit) {
        log.info("fetching all servers");

        return serverRepository.findAll(PageRequest.of(0,limit)).toList();

    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("pinging server IP:{}",ipAddress);
        Server server = serverRepository.findByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000) ? Status.SERVER_UP:Status.SERVER_DOWN);
        serverRepository.save(server);
        return server;

    }

    @Override
    public Server get(Long id) {
        log.info("Fetching server by id: {}",id);
        return serverRepository.findById(id).get();

    }

    @Override
    public Server update(Server server) {
        log.info("Updating server:{}", server.getName());

        return serverRepository.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("delete server by id: {}",id);

         serverRepository.deleteById(id);
         return true;


    }
    private String setServerImageUrl() {
        return null;
    }

}
