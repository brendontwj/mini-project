package vttp2022.batch2b.miniproject.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class GameSearch {
    private static final Logger logger = LoggerFactory.getLogger(GameSearch.class);

    private String searchQuery;
    private String plain;
    private List<Game> results = new ArrayList<Game>();
    private Game[] resultsArr;

    public String getPlain() {
        return plain;
    }

    public void setPlain(String plain) {
        this.plain = plain;
    }

    public String getSearchQuery() {
        return searchQuery;
    }


    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }


    public List<Game> getResults() {
        return results;
    }


    public void setResults(List<Game> results) {
        this.results = results;
    }


    public Game[] getResultsArr() {
        return resultsArr;
    }


    public void setResultsArr(Game[] resultsArr) {
        this.resultsArr = resultsArr;
    }


    public static GameSearch createListOfGames(String json) throws IOException {
        logger.info("Inside createListOfGames codeblock");
        GameSearch listOfGames = new GameSearch();

        try(InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader r = Json.createReader(is);
            JsonObject o = r.readObject();
            logger.info("Object read >>> " + o.toString());
            if(o.containsKey("data")) {
                logger.info("Inside containskey if block");
                JsonObject jsonObj = (JsonObject) o.get("data");
                logger.info(jsonObj.getJsonArray("results").toString());
                JsonArray tempResultArr = jsonObj.getJsonArray("results");
                for(int i = 0; i < tempResultArr.size(); i++) {
                    Game tempGame = new Game();
                    JsonObject tempObj = (JsonObject) tempResultArr.get(i);
                    tempGame.setId(tempObj.getInt("id"));
                    tempGame.setPlainName(tempObj.getString("plain"));
                    tempGame.setTitle(tempObj.getString("title"));
                    listOfGames.results.add(tempGame);
                }
            }
            // logger.info("Result List >> " + listOfGames.results.toString());
            listOfGames.resultsArr = listOfGames.results.toArray(new Game[0]);
            // logger.info("Result List >> " + listOfGames.resultsArr.toString());
            return listOfGames;
        }
        
    }
}
