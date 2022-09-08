package vttp2022.batch2b.miniproject.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import vttp2022.batch2b.miniproject.model.Game;
import vttp2022.batch2b.miniproject.model.GameDiscount;
import vttp2022.batch2b.miniproject.model.GameSearch;
import vttp2022.batch2b.miniproject.model.User;
import vttp2022.batch2b.miniproject.service.GameRedis;
import vttp2022.batch2b.miniproject.service.GameService;

@Controller
public class GameSearchController {
    private static final Logger logger = LoggerFactory.getLogger(GameSearchController.class);

    @Autowired
    GameService gameService;

    @Autowired
    GameRedis redisService;

    @Autowired
    User wiredUser;

    @GetMapping("/")
    public String showInputPage(Model model) {
        logger.info("Showing input page");
        User userObj = new User();
        model.addAttribute("userObj", userObj);
        return "userLogin";
    }

    @PostMapping("userLogin")
    public String showSearchInputPage(@ModelAttribute User user, Model model) {
        logger.info("showSearchInput username > " + user.getName());
        String name = user.getName();
        User tempUser = redisService.findById(name);
        if(!(tempUser == null)) {
            logger.info(tempUser.getFavouritesList().toString());
            wiredUser = tempUser;
            user = tempUser;
        } else {
            wiredUser = user;
        }
        model.addAttribute("userObj", user);
        return "searchInput";
    }

    @GetMapping("userLogin")
    public String showSearchPageAfterUserCreated(@ModelAttribute User user, Model model) {
        user = wiredUser;
        model.addAttribute("userObj", user);
        return "searchInput";
    }


    @PostMapping("/gamesList")
    public String showGameList(@ModelAttribute User user, Model model) {
        logger.info("Show game list username >" + user.getName());
        String searchTerms = user.getSearch().getSearchQuery();
        if(!gameService.findGamePlainName(searchTerms).isEmpty()) {
            user.setSearch(gameService.findGamePlainName(searchTerms).get());
        }
        model.addAttribute("userObj", user);
        model.addAttribute("searchObj", new GameDiscount());
        wiredUser.setSearch(user.getSearch());
        return "gamesList";
    }

    @PostMapping("/discount")
    public String showDiscount(@ModelAttribute User user, @ModelAttribute GameDiscount discount, Model model) {
        user.setDiscount(discount);
        String gamePlain = user.getDiscount().getPlain();
        String gameTitle = user.getDiscount().getTitle();
        logger.info("Gameplain > " + gamePlain);
        logger.info("Game Title >" + gameTitle);
        if(!gameService.findDiscountForGame(gamePlain).isEmpty()) {
            user.setDiscount(gameService.findDiscountForGame(gamePlain).get());
        }
        user.getDiscount().setPlain(gamePlain);
        user.getDiscount().setTitle(gameTitle);
        model.addAttribute("userObj", user);
        logger.info("user discount plain name > " + user.getDiscount().getPlain());
        wiredUser.setDiscount(user.getDiscount());
        return "discount";
    }

    @PostMapping("/saveToFavourites") 
    public String saveToFavourites(@ModelAttribute User user, Model model) {
        logger.info("Inside saveToFavourites code block");
        logger.info(wiredUser.getName());
        logger.info(user.getFavouriteName());
        String gameName = user.getFavouriteName();
        String gameTitle = user.getFavouriteTitle();
        Game newFavourite = new Game();
        newFavourite.setPlainName(gameName);
        newFavourite.setTitle(gameTitle);
        List<Game> tempList = new ArrayList<Game>();
        if(wiredUser.getFavouritesList() != null) {
            boolean gameAlreadyListed = false;
            tempList = wiredUser.getFavouritesList();
            for(Game tempGame : tempList) {
                if (tempGame.getPlainName().equals(newFavourite.getPlainName())) {
                    gameAlreadyListed = true;
                }
            }
            if(!gameAlreadyListed) {
                tempList.add(newFavourite);
            }
        } else {
            tempList.add(newFavourite);
        }
        logger.info(tempList.toString());
        wiredUser.setFavouritesList(tempList);
        redisService.save(wiredUser);
        user = wiredUser;
        model.addAttribute("userObj", user);
        return "searchInput";
    }

    @PostMapping("/favouritesList")
    public String showFavesList(@ModelAttribute User user, Model model) {
        user = wiredUser;
        model.addAttribute("userFavourites", user);
        model.addAttribute("searchObj", new GameDiscount());
        return "favouritesList";
    }

    @PostMapping("deleteFavourite")
    public String deleteFavourite(@ModelAttribute User user, Model model) {
        String deleteName = user.getDeleteName();
        wiredUser.setDeleteName(deleteName);
        logger.info("favourites list > " + wiredUser.getFavouritesList().toString());
        List tempGameList = new LinkedList<Game>();
        for(Game tempGame: wiredUser.getFavouritesList()) {
            if(!tempGame.getPlainName().equals(deleteName)) {
                tempGameList.add(tempGame);
            }
        }
        wiredUser.setFavouritesList(tempGameList);
        user = wiredUser;
        model.addAttribute("userFavourites", user);
        redisService.save(wiredUser);
        return "favouritesList";
    }
}
