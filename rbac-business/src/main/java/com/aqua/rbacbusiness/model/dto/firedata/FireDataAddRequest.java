package com.aqua.rbacbusiness.model.dto.firedata;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author water king
 * @time 2024/2/8
 */
@Data
public class FireDataAddRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private float temperature;

    private float humidity;

    private float smokeConcentration;

    private float gasConcentration;

    private int illumination;

    private String deviceStatus;

    private Long deviceId;

    private int batteryLever;

    private int connectionStatus;

    private String alarmType;

    private Date alarmLocation;
}
