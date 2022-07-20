package by.abramchik.cryptoProject.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

public class User {
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;

    @NotEmpty(message = "Symbol should not be empty")
    @Pattern(regexp = "\\b(BTC|ETH|SOL|)\\b", message = "Only BTC or ETH or SOL")
    private String symbol;

    private Double registerPrice;

    public User() {
    }

    public User(int id, String name, String symbol) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getRegisterPrice() {
        return registerPrice;
    }

    public void setRegisterPrice(Double registerPrice) {
        this.registerPrice = registerPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(name, user.name) && Objects.equals(symbol, user.symbol) && Objects.equals(registerPrice, user.registerPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, symbol, registerPrice);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                ", registerPrice=" + registerPrice +
                '}';
    }
}