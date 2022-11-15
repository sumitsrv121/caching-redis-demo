package com.srv.sumit.cachingredisdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    private String companyName;
    private String catchPhrase;
    private String buzzWord;
    private String industry;
    private String companyLocation;
}
