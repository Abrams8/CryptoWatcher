package by.abramchik.cryptoProject.controllers;

import by.abramchik.cryptoProject.dao.CoinDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/coins")
public class CoinsController {

    private final CoinDAO coinDAO;

    @Autowired
    public CoinsController(CoinDAO coinDAO) {
        this.coinDAO = coinDAO;
    }

    @GetMapping()
    public String index() {
        return "coins/index";
    }

    @GetMapping("/allCoins")
    public String showAllCoins(Model model) {
        model.addAttribute("coins", coinDAO.index());
        return "coins/showAllCoins";
    }

    @GetMapping("/{symbol}")
    public String show(@PathVariable("symbol") String symbol, Model model) {
        model.addAttribute("coin", coinDAO.showCoinPrice(symbol));
        return "coins/show";
    }

    @GetMapping("/updatePrice")
    public String updatePrice() {
        coinDAO.updatePrice();
        return "coins/index";
    }
}
