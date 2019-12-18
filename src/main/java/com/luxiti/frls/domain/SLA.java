package com.luxiti.frls.domain;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A SLA.
 */
@Entity
@Table(name = "sla")
public class SLA implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "rts")
    private Integer rts;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRts() {
        return rts;
    }

    public SLA rts(Integer rts) {
        this.rts = rts;
        return this;
    }

    public void setRts(Integer rts) {
        this.rts = rts;
    }

    public User getUser() {
        return user;
    }

    public SLA user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SLA)) {
            return false;
        }
        return id != null && id.equals(((SLA) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SLA{" +
            "id=" + getId() +
            ", rts=" + getRts() +
            "}";
    }
}
