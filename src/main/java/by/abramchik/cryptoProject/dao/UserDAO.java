package by.abramchik.cryptoProject.dao;

import by.abramchik.cryptoProject.models.Coin;
import by.abramchik.cryptoProject.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> index() {
        return jdbcTemplate.query("SELECT * FROM Users", new BeanPropertyRowMapper<>(User.class));
    }

    public User show(int id) {
        return jdbcTemplate.query("SELECT * FROM Users WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(User.class))
                .stream().findAny().orElse(null);
    }

    public void save(User user) {

        String symbol = user.getSymbol();
        Coin coin = new Coin();

        coin = jdbcTemplate.query("SELECT * FROM Coins WHERE symbol=?", new Object[]{symbol}, new BeanPropertyRowMapper<>(Coin.class))
                .stream().findAny().orElse(null);

        jdbcTemplate.update("INSERT INTO Users VALUES(null,?,?,?)", user.getName(), user.getSymbol(), coin.getPrice());

    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Users WHERE id=?", id);

    }
}