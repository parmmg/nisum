package com.nisum.challenge.presenter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhonePresenter {
    private String number;
    private String cityCode;
    private String countryCode;
}
