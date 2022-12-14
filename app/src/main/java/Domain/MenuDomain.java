package Domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class MenuDomain implements Serializable {
    private int id;
    private String name_pro;
    private String desciption;
    private Double price;
    private String image;
    private Timestamp create_at;
    private boolean status;
    private int numberInCart;
    @SerializedName("data")
    private List<MenuDomain> listData;
  //  private Category category_id;
    private int category_id;
    @SerializedName("getData")
    private List<MenuDomain> menuDomainList;

    public MenuDomain(int id, String name_pro, String desciption, Double price, String image, Timestamp create_at, boolean status, List<MenuDomain> listData, int category_id, List<MenuDomain> menuDomainList) {
        this.id = id;
        this.name_pro = name_pro;
        this.desciption = desciption;
        this.price = price;
        this.image = image;
        this.create_at = create_at;
        this.status = status;
        this.listData = listData;
        this.category_id = category_id;
        this.menuDomainList = menuDomainList;
    }

    public MenuDomain() {
        this.id = id;
        this.name_pro = name_pro;
        this.desciption = desciption;
        this.price = price;
        this.image = image;
        this.create_at = create_at;
        this.status = status;
        this.listData = listData;
        this.menuDomainList = menuDomainList;
        this.category_id = category_id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_pro() {
        return name_pro;
    }

    public void setName_pro(String name_pro) {
        this.name_pro = name_pro;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Timestamp getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Timestamp create_at) {
        this.create_at = create_at;
    }



    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }

    public List<MenuDomain> getListData() {
        return listData;
    }

    public void setListData(List<MenuDomain> listData) {
        this.listData = listData;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public List<MenuDomain> getMenuDomainList() {
        return menuDomainList;
    }

    public void setMenuDomainList(List<MenuDomain> menuDomainList) {
        this.menuDomainList = menuDomainList;
    }
}
