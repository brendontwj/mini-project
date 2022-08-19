package vttp2022.batch2b.miniproject.model;

import java.util.List;

public class User {
    private String name;

    private List<Game> gameList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Game> getGameList() {
        return gameList;
    }

    public void setGameList(List<Game> gameList) {
        this.gameList = gameList;
    }
}
