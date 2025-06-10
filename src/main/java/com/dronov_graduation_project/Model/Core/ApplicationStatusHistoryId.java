package com.dronov_graduation_project.Model.Core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationStatusHistoryId implements Serializable {

    private Long application;
    private Long status;
    private LocalDate changeDate;  // добавляем дату в составной ключ

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationStatusHistoryId that = (ApplicationStatusHistoryId) o;
        return Objects.equals(application, that.application) &&
                Objects.equals(status, that.status) &&
                Objects.equals(changeDate, that.changeDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(application, status, changeDate);
    }
}
