package org.funding.mapping.userDonation.dto;

import lombok.Data;
import org.funding.global.S3.vo.S3ImageVO;

import java.util.List;

@Data
public class UserDonationDetailDTO {
    private Long userDonationId;
    private Integer donationAmount;
    private boolean anonymous;

    private String donationName;
    private String recipient;
    private Long targetAmount;

    private Long productId;
    private List<S3ImageVO> images;
}
