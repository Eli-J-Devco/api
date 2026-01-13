/********************************************************
 * Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
 * All rights reserved.
 *
 *********************************************************/
package com.nwm.api.services;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.TicketEntity;
import org.apache.ibatis.session.SqlSession;

import java.time.LocalDateTime;

public class TicketService extends DB {
    public TicketEntity createTicket(TicketEntity obj) {
        SqlSession session = this.beginTransaction();
        try {
            obj.setSite_id(275);
            obj.setDevice_category_id(5);
            obj.setOpened(LocalDateTime.of(2025,11,12,10,00));

            session.insert("Ticket.insertTicket", obj);

            session.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.rollback();
        }
        return obj;
    }
}
