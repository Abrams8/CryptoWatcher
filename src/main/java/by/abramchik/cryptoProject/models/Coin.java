package by.abramchik.cryptoProject.models;

import com.google.gson.annotations.SerializedName;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

public class Coin {

    private int id;

    @Min(value = 1, message = "Coin id should be greater than 1")
    @NotNull
    @NotEmpty(message = "Coin id should not be empty")
    private int coinId;

    @NotEmpty(message = "Symbol should not be empty")
    @Pattern(regexp = "\\b(BTC|ETH|SOL|)\\b", message = "Only BTC or ETH or SOL")
    private String symbol;

    @NotNull
    @NotEmpty(message = "Price should not be empty")
    @SerializedName("price_usd")
    private Double price;

    public Coin(int id, int coinId, String symbol, Double price) {
        this.id = id;
        this.coinId = coinId;
        this.symbol = symbol;
        this.price = price;
    }

    public Coin() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCoinId() {
        return coinId;
    }

    public void setCoinId(int coinId) {
        this.coinId = coinId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coin coin = (Coin) o;
        return id == coin.id && coinId == coin.coinId && Objects.equals(symbol, coin.symbol) && Objects.equals(price, coin.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, coinId, symbol, price);
    }

    @Override
    public String toString() {
        return "Coin{" +
                "id=" + id +
                ", coinId=" + coinId +
                ", symbol='" + symbol + '\'' +
                ", price=" + price +
                '}';
    }
}
