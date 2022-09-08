package vttp2022.batch2b.miniproject.model;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class User {
    private Game game;
    private String name;
    private GameSearch search;
    private GameDiscount discount;
    private List<Game> favouritesList;
    private String favouriteName;
    private String favouriteTitle;
    private String deleteName;

    public String getDeleteName() {
        return deleteName;
    }
    public void setDeleteName(String deleteName) {
        this.deleteName = deleteName;
    }
    public String getFavouriteTitle() {
        return favouriteTitle;
    }
    public void setFavouriteTitle(String favouriteTitle) {
        this.favouriteTitle = favouriteTitle;
    }
    public Game getGame() {
        return game;
    }
    public void setGame(Game game) {
        this.game = game;
    }

    public String getFavouriteName() {
        return favouriteName;
    }
    public void setFavouriteName(String favouriteName) {
        this.favouriteName = favouriteName;
    }
    public List<Game> getFavouritesList() {
        return favouritesList;
    }
    public void setFavouritesList(List<Game> favouritesList) {
        this.favouritesList = favouritesList;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public GameSearch getSearch() {
        return search;
    }
    public void setSearch(GameSearch search) {
        this.search = search;
    }
    public GameDiscount getDiscount() {
        return discount;
    }
    public void setDiscount(GameDiscount discount) {
        this.discount = discount;
    }

}
