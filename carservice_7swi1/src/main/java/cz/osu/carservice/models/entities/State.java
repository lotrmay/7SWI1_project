package cz.osu.carservice.models.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "states")
@NamedQueries({
        @NamedQuery(name = "State.findAll", query = "SELECT c FROM State c"),
        @NamedQuery(name = "State.findByShortCut", query = "SELECT c FROM State c WHERE c.state_short = :state_shortcut")})
public class State implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id",unique=true,nullable = false)
    private long id;

    @Column(name = "state_short",nullable = false,length = 3)
    private String state_short;

    @Column(name = "state_full",nullable = false,length = 50)
    private String state_full;

    @Column(name = "telephone_code",nullable = false,length = 5)
    private String telephone_code;

    @OneToMany(mappedBy = "state",cascade=CascadeType.PERSIST)
    private Set<Address> addresses;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getState_short() {
        return state_short;
    }

    public void setState_short(String state_short) {
        this.state_short = state_short;
    }

    public String getState_full() {
        return state_full;
    }

    public void setState_full(String state_full) {
        this.state_full = state_full;
    }

    public String getTelephone_code() {
        return telephone_code;
    }

    public void setTelephone_code(String telephone_code) {
        this.telephone_code = telephone_code;
    }

    public State() {
    }

    @Override
    public String toString() {
        return "States{" +
                "id=" + id +
                ", state_short='" + state_short + '\'' +
                ", state_full='" + state_full + '\'' +
                ", telephone_code='" + telephone_code + '\'' +
                '}';
    }
}
