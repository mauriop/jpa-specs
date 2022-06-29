package io.reflectoring.specification.domain;

import lombok.*;

import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductDto {
    private String id;

    private String name;

    private Double price;

    private LocalDateTime manufacturingDate;

    private Double weight;
}
