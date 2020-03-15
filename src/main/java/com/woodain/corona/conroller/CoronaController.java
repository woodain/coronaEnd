package com.woodain.corona.conroller;

import com.woodain.corona.service.CoronaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CoronaController {

    CoronaService coronaService;

    @Autowired
    public CoronaController(CoronaService coronaService) {
        this.coronaService = coronaService;
    }

    @RequestMapping("/")
    public String main()
    {
        return "mainPage";
    }

    @RequestMapping("/domestic")
    public String getDomestic()
    {
        return coronaService.getCoronaDomesticInfo();
    }

    @RequestMapping("/foreign")
    public String getForeign()
    {
        return "foreign";
    }
}
