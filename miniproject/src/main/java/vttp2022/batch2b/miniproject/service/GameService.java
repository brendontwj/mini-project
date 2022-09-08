package vttp2022.batch2b.miniproject.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import vttp2022.batch2b.miniproject.model.GameDiscount;
import vttp2022.batch2b.miniproject.model.GameSearch;

@Service
public class GameService {
    private static final Logger logger = LoggerFactory.getLogger(GameService.class);

    private static final String apikey = System.getenv("GAMEDEAL_API_KEY");

    private String searchURL = "https://api.isthereanydeal.com/v02/search/search/";

    private String priceURL = "https://api.isthereanydeal.com/v01/game/prices/";

    public Optional<GameDiscount> findDiscountForGame(String plainName) {
        String searchDiscountURL = UriComponentsBuilder.fromUriString(priceURL)
            .queryParam("key", apikey)
            .queryParam("plains", plainName)
            .build(false)
            .toUriString();

        logger.info("Discount URL is > " + searchDiscountURL);
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;

        try {
            resp = template.getForEntity(searchDiscountURL, String.class);
            logger.info("Body received > " + resp.getBody());
            GameDiscount listOfDiscounts = GameDiscount.createListOfDiscounts(resp.getBody(), plainName);
            logger.info("Discounts list made > " + listOfDiscounts.getDiscountArr().toString());
            return Optional.of(listOfDiscounts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<GameSearch> findGamePlainName(String searchInput) {
        String searchPlainsURL = UriComponentsBuilder.fromUriString(searchURL)
            .queryParam("key", apikey)
            .queryParam("q", searchInput)
            .build(false).toUriString();

        searchPlainsURL.replace("%20", " ");

        logger.info("Search URL is > " + searchPlainsURL);
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;

        try {
            resp = template.getForEntity(searchPlainsURL, String.class);
            logger.info("Body received > " + resp.getBody());
            GameSearch listOfGames = GameSearch.createListOfGames(resp.getBody());
            logger.info("Games list made >>>> " + listOfGames.getResults().toString());
            return Optional.of(listOfGames);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
