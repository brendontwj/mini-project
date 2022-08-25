package vttp2022.batch2b.miniproject.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import vttp2022.batch2b.miniproject.model.GameDiscount;
import vttp2022.batch2b.miniproject.model.GameSearch;
import vttp2022.batch2b.miniproject.service.GameRedis;
import vttp2022.batch2b.miniproject.service.GameService;

@Controller
public class GameSearchController {
    private static final Logger logger = LoggerFactory.getLogger(GameSearchController.class);

    @Autowired
    GameService gameService;

    @Autowired
    GameRedis redisService;

    @GetMapping("/")
    public String showInputPage(Model model) {
        logger.info("Showing input page");
        GameSearch searchObj = new GameSearch();
        model.addAttribute("SearchObject", searchObj);
        return "index";
    }

    @PostMapping("/gamesList")
    public String showGameList(@ModelAttribute GameSearch search, Model model) {
        String searchTerms = search.getSearchQuery();
        if(!gameService.findGamePlainName(searchTerms).isEmpty()) {
            search = gameService.findGamePlainName(searchTerms).get();
        }
        model.addAttribute("GamesList", search);
        model.addAttribute("GameDiscount", new GameDiscount());
        return "gamesList";
    }

    @PostMapping("/discount")
    public String showDiscount(@ModelAttribute GameDiscount discount, Model model) {
        String gamePlain = discount.getPlain();
        String gameTitle = discount.getTitle();
        if(!gameService.findDiscountForGame(gamePlain).isEmpty()) {
            discount = gameService.findDiscountForGame(gamePlain).get();
        }
        discount.setPlain(gamePlain);
        discount.setTitle(gameTitle);
        model.addAttribute("GameDiscount", discount);
        logger.info("discount plain name > " + discount.getPlain());
        return "discount";
    }
}
