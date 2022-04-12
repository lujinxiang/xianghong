package com.xianghong.life.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * Excel表头属性
 */
@AllArgsConstructor
@Data
public class ExcelTitle implements Serializable {
    private static final long serialVersionUID = -4563828429556406825L;

    public ExcelTitle(String columnName) {
        this.columnName = columnName;
    }

    /**
     * 列名称
     */
    private String columnName;
    /**
     * 列宽
     */
    private Integer columnWidth;
}