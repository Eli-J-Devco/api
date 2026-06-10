/********************************************************
 * Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
 * All rights reserved.
 *
 *********************************************************/
package com.nwm.api.controllers;

import com.nwm.api.entities.TicketEntity;
import com.nwm.api.services.TicketService;
import com.nwm.api.utils.Constants;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/ticket")
public class TicketController extends BaseController {
    @PostMapping("/create")
    public Object createTicket(@RequestBody TicketEntity ticketEntity) {
        TicketEntity result = new TicketEntity();
        try {
            TicketService ticketService = new TicketService();
            result = ticketService.createTicket(ticketEntity);
            Integer id = result.getTicket_id();

            System.out.println(id);

            return this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, result, 1);
        } catch(Exception e) {
            return this.jsonResult(true, Constants.SAVE_ERROR_MSG, result, 0);
        }
    }
}
