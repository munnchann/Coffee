package Domain;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "address")
public class Address {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String address_user;



    public Address(String address_user) {
        this.address_user = address_user;
    }

    public String getAddress_user() {
        return address_user;
    }

    public void setAddress_user(String address_user) {
        this.address_user = address_user;
    }
}
