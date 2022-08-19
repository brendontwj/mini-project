package vttp2022.batch2b.miniproject.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import vttp2022.batch2b.miniproject.model.GameSearch;

public class GameService {
    private static final Logger logger = LoggerFactory.getLogger(GameService.class);

    private String searchURL = "https://api.isthereanydeal.com/v02/search/search/";

    private String priceURL = "https://api.isthereanydeal.com/v01/game/prices/";

    public Optional<GameSearch> findGamePlainName(String searchInput) {
        String apikey = System.getenv("GAMEDEAL_API_KEY");
        String searchPlainsURL = UriComponentsBuilder.fromUriString(searchURL)
            .queryParam("key", apikey)
            .queryParam("q", searchInput)
            .toUriString();

        logger.info("URL is > " + searchPlainsURL);
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "text/html");
            HttpEntity request = new HttpEntity<>(headers);
            resp = template.exchange(searchPlainsURL, HttpMethod.GET,
            request, String.class, 1
            );
            logger.info("Body received > " + resp.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
