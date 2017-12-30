package org.kryptonmlt.multiplayer.models.db;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Kurt
 */
@Entity
@Table(name = "room")
public class Room {

    private long id;

    private String secret;

    private int maxPlayers;

    private Date dateStarted;

    public Room() {
    }

    public Room(String secret, int maxPlayers) {
        this.secret = secret;
        this.maxPlayers = maxPlayers;
    }

    public Date getDateStarted() {
        return dateStarted;
    }

    public void setDateStarted(Date dateStarted) {
        this.dateStarted = dateStarted;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public String toString() {
        return "Room{" + "id=" + id + ", secret=" + secret + ", maxPlayers=" + maxPlayers + '}';
    }

}
