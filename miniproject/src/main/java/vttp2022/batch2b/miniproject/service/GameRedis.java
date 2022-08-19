package vttp2022.batch2b.miniproject.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import vttp2022.batch2b.miniproject.model.User;

@Service
public class GameRedis implements GameRepo {
    private static final Logger logger = LoggerFactory.getLogger(GameRedis.class);

    @Autowired
    RedisTemplate<String, User> template;

    @Override
    public void save(User user) {
        template.opsForValue().set(user.getName(), user);
        logger.info("Saving article named >>>>> " + user.getName());
    }

    @Override
    public User findById(String name) {
        User article = (User) template.opsForValue().get(name);
        return article;
    }
}
