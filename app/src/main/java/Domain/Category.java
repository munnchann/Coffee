package Domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Category {
    @SerializedName("id")
    @Expose
   private int categoryid;
    @SerializedName("name")
    @Expose
   private String name;
    @SerializedName("nickname")
    @Expose
   private String nickname;
    @SerializedName("data")
   private List<Category> listCategory;


    public Category(int categoryid, String name, String nickname, List<Category> listCategory) {
        this.categoryid = categoryid;
        this.name = name;
        this.nickname = nickname;
        this.listCategory = listCategory;

    }

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<Category> getListCategory() {
        return listCategory;
    }

    public void setListCategory(List<Category> listCategory) {
        this.listCategory = listCategory;
    }
}
