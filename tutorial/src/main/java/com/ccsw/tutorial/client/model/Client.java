package com.ccsw.tutorial.client.model;

import jakarta.persistence.*;

@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id new value of {@link #getId}
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name new value of {@link #getName}
     */
    public void setName(String name) {
        this.name = name;
    }
}