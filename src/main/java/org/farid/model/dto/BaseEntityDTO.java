package org.farid.model.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class BaseEntityDTO<T> {
    protected T id;
    protected Timestamp createDate;
    protected Timestamp updateDate;
}
