package com.ccsw.tutorial.loan.model;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.game.model.Game;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "loan")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "end_date", nullable = false)
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
    public Game getGame() {
        return game;
    }

    /**
     * @param game new value of {@link #setGame}
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * @return
     */
    public Client getClient() {
        return client;
    }

    /**
     * @param client new value of {@link #setClient}
     */
    public void setClient(Client client) {
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
