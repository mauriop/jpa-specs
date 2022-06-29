package io.reflectoring.specification.service;

import io.reflectoring.specification.domain.ProductDto;
import io.reflectoring.specification.model.Product;
import io.reflectoring.specification.repository.CustomProductRepository;
import io.reflectoring.specification.repository.Filter;
import io.reflectoring.specification.repository.QueryOperator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final CustomProductRepository customProductRepository;

    public List<ProductDto> getAll() {
        return customProductRepository.getQueryResult(Collections.emptyList()).stream()
                .map(getProductDto())
                .collect(Collectors.toList());
    }

    public Optional<ProductDto> getById(String id) {
        return customProductRepository.getQueryResult(Collections
                .singletonList(Filter.builder()
                        .operator(QueryOperator.EQUALS)
                        .field("id")
                        .value(id)
                        .build())).stream().map(getProductDto()).findFirst();

    }

    private Function<Product, ProductDto> getProductDto() {
        return product -> ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .manufacturingDate(product.getManufacturingDate())
                .weight(product.getWeight())
                .build();
    }
}
