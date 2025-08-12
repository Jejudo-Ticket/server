package org.funding.global.S3.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.funding.global.S3.vo.enumType.ImageType;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class S3ImageVO {
    private ImageType imageType;
    private Long postId;
    private String imageUrl;
    private LocalDateTime createdAt;

    public S3ImageVO(String imageType, Long postId, String imageUrl, LocalDateTime createdAt) {
        this.imageType = ImageType.valueOf(imageType);
        this.postId = postId;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
    }
}
