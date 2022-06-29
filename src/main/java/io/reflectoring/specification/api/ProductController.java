package io.reflectoring.specification.api;

import io.reflectoring.specification.domain.ProductDto;
import io.reflectoring.specification.error.ErrorResponse;
import io.reflectoring.specification.error.NoSuchElementFoundException;
import io.reflectoring.specification.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping(value= "", produces = "application/json")
    public List<ProductDto> getAllProducts() {
        return productService.getAll();
    }

    @GetMapping(value= "/{id}", produces = "application/json")
    public ProductDto getProductById(@PathVariable String id) {
        return productService.getById(id).orElseThrow(() -> new NoSuchElementFoundException("Product not found"));
    }

    @ExceptionHandler(NoSuchElementFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleItemNotFoundException(
            NoSuchElementFoundException exception,
            WebRequest request
    ){
        log.error("Failed to find the requested element", exception);
        return buildErrorResponse(exception, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(
            Exception exception,
            HttpStatus httpStatus
    ) {
        ErrorResponse errorResponse = new ErrorResponse(
                httpStatus.value(),
                exception.getMessage()
        );

        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
}
