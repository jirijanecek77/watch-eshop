package com.example.watcheshop.api.v1.dto;

import javax.validation.constraints.NotBlank;

public class WatchInputDTO {

    @NotBlank
    public String title;

    @NotBlank
    public String price;

    @NotBlank
    public String description;

    @NotBlank
    public String fountain; // base 64 encoded icon
}
