package io.system.demo.exceptionalHandling;

public class ProductNotFoundException extends RuntimeException {

    private final Long id;

    public ProductNotFoundException(String message, Long id) {
        super(message);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
