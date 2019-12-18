package com.luxiti.frls.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.luxiti.frls.domain.SLA} entity.
 */
public class SLADTO implements Serializable {

    private Long id;

    private Integer rts;


    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRts() {
        return rts;
    }

    public void setRts(Integer rts) {
        this.rts = rts;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SLADTO sLADTO = (SLADTO) o;
        if (sLADTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sLADTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SLADTO{" +
            "id=" + getId() +
            ", rts=" + getRts() +
            ", user=" + getUserId() +
            "}";
    }
}
