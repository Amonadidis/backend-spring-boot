package de.jannishornfeck.controller;

import de.jannishornfeck.util.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class IndexController extends Logger {

    @GetMapping(path = "/")
    public String getIndex() {
        return "index";
    }

    @GetMapping(path = "/log-test")
    @ResponseBody
    public String getLogTest() {
        this.logger.trace("Trace Log Test");
        this.logger.debug("Debug Log Test");
        this.logger.info("Info Log Test");
        this.logger.warn("Warn Log Test");
        this.logger.error("Error Log Test");

        return "Log Test";
    }

}
