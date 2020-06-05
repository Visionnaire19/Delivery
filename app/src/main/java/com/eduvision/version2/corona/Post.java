package com.eduvision.version2.corona;


public class Post {

    private String ID;
    private String Img_uri;
    private String Title;
    private String Price;

    public Post (String Img_uri, String Title, String Price, String ID) {
        this.Img_uri = Img_uri;
        this.Title = Title;
        this.Price = Price;
        this.ID = ID;
    }


    public Post() {
    }




    public String getImg_uri() {
        return Img_uri;
    }

    public void setImg_uri(String img_uri1) {
        this.Img_uri = img_uri1;
    }



    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        this.Price = price;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
