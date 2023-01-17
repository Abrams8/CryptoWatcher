package by.abramchik.cryptoProject.dao;

import by.abramchik.cryptoProject.models.Coin;
import by.abramchik.cryptoProject.models.User;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

@Component
public class CoinDAO {

    private static final Logger log = Logger.getLogger(CoinDAO.class);

    public static final String URL = "https://api.coinlore.net/api/ticker/?id=90,80,48543";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CoinDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Coin> index() {
        return jdbcTemplate.query("SELECT * FROM Coins", new BeanPropertyRowMapper<>(Coin.class));
    }

    public Coin showCoinPrice(String symbol) {
        return jdbcTemplate.query("SELECT * FROM Coins WHERE symbol=?", new Object[]{symbol}, new BeanPropertyRowMapper<>(Coin.class))
                .stream().findAny().orElse(null);
    }


    public void updatePrice() {
            try {

                CloseableHttpClient httpclient = HttpClients.createDefault();

                HttpGet httpget = new HttpGet(URL);

                HttpResponse httpresponse = httpclient.execute(httpget);

                Scanner sc = new Scanner(httpresponse.getEntity().getContent());

                String data = sc.nextLine();

                Gson gson = new Gson();
                Coin[] coin = gson.fromJson(data, Coin[].class);

                for (int i = 0; i < coin.length; i++) {
                    jdbcTemplate.update("UPDATE coins SET price =? WHERE symbol =?", coin[i].getPrice(), coin[i].getSymbol());
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        List<Coin> coins = index();

        for (int i = 0; i < coins.size(); i++) {
            String symbol = coins.get(i).getSymbol();
            Double actualPrice = coins.get(i).getPrice();

            List<User> users = jdbcTemplate.query("SELECT * FROM Users WHERE symbol=?", new Object[]{symbol}, new BeanPropertyRowMapper<>(User.class));

            for (int j = 0; j < users.size(); j++) {
                Double registerPrice = users.get(j).getRegisterPrice();
                if (actualPrice > registerPrice * 1.01 || actualPrice < registerPrice * 0.99) {
                    // Double result = (actualPrice * 100 / registerPrice) - 100;
                    String result = String.format("%.3f", ((actualPrice * 100 / registerPrice) - 100));
                    log.warn(symbol + " : " + users.get(j).getName() + " : " + result + "%");
                }
            }

        }

    }


}
