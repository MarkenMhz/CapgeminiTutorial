package com.ccsw.tutorial.loan.model;

import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.game.model.GameDto;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class LoanDto {
    private Long id;

    private GameDto game;

    private ClientDto client;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date endDate;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id new value of {@link #setId}
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return
     */
    public GameDto getGame() {
        return game;
    }

    /**
     * @param game new value of {@link #setGame}
     */
    public void setGame(GameDto game) {
        this.game = game;
    }

    /**
     * @return
     */
    public ClientDto getClient() {
        return client;
    }

    /**
     * @param client new value of {@link #setClient}
     */
    public void setClient(ClientDto client) {
        this.client = client;
    }

    /**
     * @return
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate new value of {@link #setStartDate}
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate new value of {@link #setEndDate}
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
