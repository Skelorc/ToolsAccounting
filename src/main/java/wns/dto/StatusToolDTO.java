package wns.dto;

import lombok.Data;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import wns.constants.StatusTools;
import wns.entity.Status;
import wns.entity.Tools;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Data
public class StatusToolDTO {
    private long id;
    private ToolsDTO tools;
    private StatusTools statusTools;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime created;
    private String employee;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime start;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime end;
    private String executor;
    private String phone_number;
    private String note;
    private Set<String> photos_status = new HashSet<>();
    private int priceRepair;
    private int priceSell;
    private int priceOff;

    public static Status createStatusWithTools(Tools tools, StatusTools status_tool)
    {
        Status status = new Status();
        status.setStatusTools(status_tool);
        status.setTools(tools);
        status.setCreated(LocalDateTime.now());
        status.setEmployee(SecurityContextHolder.getContext().getAuthentication().getName());
        status.setPhotos(tools.getPhotos());
        return status;
    }



    @Override
    public String toString() {
        return "StatusToolsDTO{" +
                "id=" + id +
                ", statusTools=" + statusTools +
                ", created=" + created +
                ", employee='" + employee + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", executor='" + executor + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", note='" + note + '\'' +
                ", photos_status=" + photos_status +
                ", priceRepair=" + priceRepair +
                ", priceSell=" + priceSell +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        StatusToolDTO that = (StatusToolDTO) o;

        return new EqualsBuilder().append(id, that.id).append(priceRepair, that.priceRepair).append(priceSell, that.priceSell).append(statusTools, that.statusTools).append(created, that.created).append(employee, that.employee).append(start, that.start).append(end, that.end).append(executor, that.executor).append(phone_number, that.phone_number).append(note, that.note).append(photos_status, that.photos_status).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(statusTools).append(created).append(employee).append(start).append(end).append(executor).append(phone_number).append(note).append(photos_status).append(priceRepair).append(priceSell).toHashCode();
    }
}
