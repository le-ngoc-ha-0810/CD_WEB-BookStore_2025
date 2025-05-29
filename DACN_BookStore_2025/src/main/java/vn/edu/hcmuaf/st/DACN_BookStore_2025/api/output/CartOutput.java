package vn.edu.hcmuaf.st.DACN_BookStore_2025.api.output;

import vn.edu.hcmuaf.st.DACN_BookStore_2025.dto.CartDTO;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartOutput {
    private double total;
    private List<CartDTO> booksList = new ArrayList<>();
    private int totalQuantity; // Tổng số lượng sản phẩm trong giỏ

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<CartDTO> getBooksList() {
        return booksList;
    }

    public void setBooksList(List<CartDTO> booksList) {
        this.booksList = booksList;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    // Tổng tiền + phí ship (15k)
    public double getTotalMoney() {
        return total + 15000;
    }

    // Định dạng tổng tiền có phí ship
    public String getFormat() {
        DecimalFormat df = new DecimalFormat("###,###,###");
        return df.format(getTotalMoney()) + "VND";
    }

    // Định dạng tổng tiền chưa có phí ship
    public String getTotalFormat() {
        DecimalFormat df = new DecimalFormat("###,###,###");
        return df.format(total) + "VND";
    }
}
