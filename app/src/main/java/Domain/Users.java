package Domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Users {
    public int id;
    public String first_name;
    public String last_name;
    public String email;
    public String phone;
    public String password;
    public String image;
    @SerializedName("results")
    private List<Users> listUser;
    public String address;

    public Users(String phone) {
        this.phone = phone;
    }

    public Users(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public Users(int id, String password) {
        this.id = id;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Users(String first_name, String last_name, String email, String phone, String password, String address) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.address = address;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Users> getListUser() {
        return listUser;
    }

    public void setListUser(List<Users> listUser) {
        this.listUser = listUser;
    }
}
