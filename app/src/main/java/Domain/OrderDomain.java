package Domain;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ords")
public class OrderDomain {
    @PrimaryKey(autoGenerate = true)
   public int id;
    public int user_id, employee_id;
   public Double total;
   public int payment_id, shipInfo_id, voucher_id;
   public String create_at, ouput_status, order_status, stt_voucher;

    public OrderDomain(int id, int user_id, int employee_id, Double total, int payment_id, int shipInfo_id, String create_at, String ouput_status, String order_status) {
        this.id = id;
        this.user_id = user_id;
        this.employee_id = employee_id;
        this.total = total;
        this.payment_id = payment_id;
        this.shipInfo_id = shipInfo_id;
        this.create_at = create_at;
        this.ouput_status = ouput_status;
        this.order_status = order_status;
    }

    public OrderDomain(int user_id, int employee_id, Double total, int payment_id, int shipInfo_id, String create_at, String ouput_status, String order_status) {
        this.user_id = user_id;
        this.employee_id = employee_id;
        this.total = total;
        this.payment_id = payment_id;
        this.shipInfo_id = shipInfo_id;
        this.create_at = create_at;
        this.ouput_status = ouput_status;
        this.order_status = order_status;
    }

    public OrderDomain(int user_id, Double total, int payment_id, String create_at, String ouput_status, String order_status) {
        this.user_id = user_id;
        this.total = total;
        this.payment_id = payment_id;
        this.create_at = create_at;
        this.ouput_status = ouput_status;
        this.order_status = order_status;
    }

    public OrderDomain(int user_id, Double total, int payment_id, int voucher_id, String create_at, String ouput_status, String order_status, String stt_voucher) {
        this.user_id = user_id;
        this.total = total;
        this.payment_id = payment_id;
        this.voucher_id = voucher_id;
        this.create_at = create_at;
        this.ouput_status = ouput_status;
        this.order_status = order_status;
        this.stt_voucher = stt_voucher;
    }

    @Override
    public String toString() {
        return "OrderDomain{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", total=" + total +
                ", payment_id=" + payment_id +
                ", create_at='" + create_at + '\'' +
                ", ouput_status='" + ouput_status + '\'' +
                ", order_status='" + order_status + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public OrderDomain() {
    }

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public int getShipInfo_id() {
        return shipInfo_id;
    }

    public void setShipInfo_id(int shipInfo_id) {
        this.shipInfo_id = shipInfo_id;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getOuput_status() {
        return ouput_status;
    }

    public void setOuput_status(String ouput_status) {
        this.ouput_status = ouput_status;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }
}
