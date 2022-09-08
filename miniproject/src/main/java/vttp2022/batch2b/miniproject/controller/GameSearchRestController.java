package vttp2022.batch2b.miniproject.controller;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import vttp2022.batch2b.miniproject.model.Game;
import vttp2022.batch2b.miniproject.model.User;
import vttp2022.batch2b.miniproject.service.GameRedis;
import vttp2022.batch2b.miniproject.service.GameService;

@RestController
public class GameSearchRestController {
    private static final Logger logger = LoggerFactory.getLogger(GameSearchRestController.class);

    @Autowired
    GameService gameService;

    @Autowired
    GameRedis redisService;

    @Autowired
    User restUser;

}
