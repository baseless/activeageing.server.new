package se.hkr.activeageing.server.viewmodels;

/**
 * Manufacturer engine class.
 * Created by Daniel Ryhle & Emil Eider & Magnus Kanfjäll on 2015-11-10.
 */
public class ManufacturerViewModel {

    private String name;
    private String logoUrl;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
