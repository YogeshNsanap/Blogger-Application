package com.brainworks.rest.payloads.response;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CatogoryDto {

    private Integer cid;
    @NotEmpty
    private String catogoryTitle;
    @NotEmpty
    private String catogoryDiscription;

}
