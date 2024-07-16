package io.system.demo.exceptionalHandling.response;

public class ProductNotFoundErrorResponse {

    private final Long id;
    private final String message;

    public ProductNotFoundErrorResponse(Long id) {
        this.id = id;
        message = "Did not found product: " + id;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
