package pl.lottery.infrastructure.controllers.homepage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageWebController {

    @GetMapping("/")
    public String showHomePageView(){
        return "index";
    }

}
