package in.co.rays.project_3.dto;

import java.util.Date;

public class ShoppingCartDTO extends BaseDTO {
    private long id;
    private String name;
    private String product;
    private int quantity;
    private Date productDate;
    private String category;

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getProduct() {
        return product;
    }
    public void setProduct(String product) {
        this.product = product;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public Date getProductDate() {
        return productDate;
    }
    public void setProductDate(Date productDate) {
        this.productDate = productDate;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String getKey() {
        return String.valueOf(id);
    }

    @Override
    public String getValue() {
        return name;
    }
}
