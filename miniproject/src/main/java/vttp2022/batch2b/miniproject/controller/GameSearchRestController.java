package vttp2022.batch2b.miniproject.controller;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping(path = "/search/{id}")
    public ResponseEntity getArticleById(@PathVariable String id) {
        logger.info("inside get user codeblock");
        System.out.println("Path variable is: " + id);
        User user = redisService.findById(id);
        if(user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find user: " + id);
        }
        return ResponseEntity.ok(user);
    }
}
