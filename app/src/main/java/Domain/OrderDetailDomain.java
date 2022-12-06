package Domain;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ord_detail")
public class OrderDetailDomain {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int order_id, product_id, quanity, discount_id;
    public String create_at;
    public Double ori_price;
    public Double total;

    public OrderDetailDomain(int id, int order_id, int product_id, int quanity, int discount_id, String create_at, Double ori_price, Double total) {
        this.id = id;
        this.order_id = order_id;
        this.product_id = product_id;
        this.quanity = quanity;
        this.discount_id = discount_id;
        this.create_at = create_at;
        this.ori_price = ori_price;
        this.total = total;
    }

    public OrderDetailDomain() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQuanity() {
        return quanity;
    }

    public void setQuanity(int quanity) {
        this.quanity = quanity;
    }

    public int getDiscount_id() {
        return discount_id;
    }

    public void setDiscount_id(int discount_id) {
        this.discount_id = discount_id;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public Double getOri_price() {
        return ori_price;
    }

    public void setOri_price(Double ori_price) {
        this.ori_price = ori_price;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
