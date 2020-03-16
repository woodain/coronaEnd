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

    @RequestMapping(value="/")
    public String main()
    {
        return "index";
    }

    @RequestMapping(value="/test")
    public String test()
    {
        return "index";
    }

    @RequestMapping(value="/domestic")
    public String getDomestic()
    {
        return coronaService.getCoronaDomesticInfo();
    }

    @RequestMapping(value="/foreign")
    public String getForeign()
    {
        return coronaService.getCoronaForeignInfo();
    }
}
