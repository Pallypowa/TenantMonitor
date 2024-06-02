package com.bigfoot.tenantmonitor.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PropertyController.API_PATH)
public class PropertyController {
    public static final String API_PATH = "/api/v1/property";
}
