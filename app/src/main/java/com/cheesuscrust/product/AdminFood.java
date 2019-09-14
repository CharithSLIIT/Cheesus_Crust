package com.cheesuscrust.product;

public class AdminFood {

    private int id;
    private String name;
    private String description;
    private String sprice;
    private String mprice;
    private String lprice;
    private String type;
    private byte[] image;


    public AdminFood(int id, String name, String description, String sprice, String mprice, String lprice, String type, byte[] image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sprice = sprice;
        this.mprice = mprice;
        this.lprice = lprice;
        this.type = type;
        this.image = image;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getSprice() {
        return sprice;
    }

    public void setSprice(String sprice) {
        this.sprice = sprice;
    }

    public String getMprice() {
        return mprice;
    }

    public void setMprice(String mprice) {
        this.mprice = mprice;
    }

    public String getLprice() {
        return lprice;
    }

    public void setLprice(String lprice) {
        this.lprice = lprice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

}
