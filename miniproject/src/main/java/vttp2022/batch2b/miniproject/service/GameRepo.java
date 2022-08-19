package vttp2022.batch2b.miniproject.service;

import vttp2022.batch2b.miniproject.model.User;

public interface GameRepo {
    public void save(User user);

    public User findById(final String id);
}
