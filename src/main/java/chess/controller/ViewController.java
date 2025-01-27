package chess.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String index() {
        return "/index.html";
    }

    @GetMapping("/game/{id}")
    public String game() {
        return "/game.html";
    }
}
