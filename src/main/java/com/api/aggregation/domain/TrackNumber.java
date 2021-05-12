package com.api.aggregation.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class TrackNumber {

    @Pattern(message="invalid track number",regexp = "^\\\\d{9}$")
    private int trackNumber;
}
