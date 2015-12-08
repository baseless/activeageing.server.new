package se.hkr.activeageing.server.viewmodels;

import java.math.BigDecimal;

/**
 * Product viewModel class.
 * Created by Daniel Ryhle & Emil Eider & Andreas Svensson on 2015-11-10.
 */
public class ProductViewModel {
    private String name;
    private String description;
    private BigDecimal price;
    private String tags;
    private String imageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
