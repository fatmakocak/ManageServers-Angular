package com.example.server.service.Impl;

import com.example.server.model.Server;
import com.example.server.model.Status;
import com.example.server.repo.ServerRepository;
import com.example.server.service.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

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

        return serverRepository.findAll(PageRequest.of(0, limit)).toList();

    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("pinging server IP:{}", ipAddress);
        Server server = serverRepository.findByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000) ? Status.SERVER_UP : Status.SERVER_DOWN);
        serverRepository.save(server);
        return server;

    }

    @Override
    public Server get(Long id) {
        log.info("Fetching server by id: {}", id);
        return serverRepository.findById(id).get();

    }

    @Override
    public Server update(Server server) {
        log.info("Updating server:{}", server.getName());

        return serverRepository.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("delete server by id: {}", id);

        serverRepository.deleteById(id);
        return true;


    }

    private String setServerImageUrl() {

        String[] imageNames = {"server1.jpg", "server2.png", "server3.png", "server4.png", "server5.png"};
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/images" + imageNames[new Random().nextInt(4)]).toUriString();

    }
}
