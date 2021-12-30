package com.smarthome.spring.jpa.postgresql.model;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.Date;
/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "product")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	@Column(name = "productName")
    private String productName;
	@Column(name = "category")
    private String category;
	@Column(name = "productCode")
    private String productCode;
	@Column(name = "date")
    private String date;
	@Column(name = "quantity")
    private int quantity;
	@Column(name = "price")
    private double price;
	@Column(name = "brand")
    private String brand;
	@Column(name = "location")
    private String location;// where do we put the product
	@Column(name = "userId")
    private long userId; // reference id
	@Lob
	@Column(name = "image")
    private Byte[] image;
	
	public Product(String productName,String category, String productCode, int quantity, double price, String brand, String location, long userId, Byte[] image) {
		this.productName = productName;
		this.category = category;
		this.productCode = productCode;
		this.date = date2Str(new Date());
		this.quantity = quantity;
		this.price = price;
		this.brand = brand;
		this.location = location;
		this.userId = userId;
		this.image = image;
	};
	
	public Product() {
		
	}
	
    public long getId() {
        return id;
    }

    public void setId(long productId) {
        this.id = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Byte[] getImage() {
		return image;
	}

	public void setImage(Byte[] image) {
		this.image = image;
	}
    
	private Date str2Date(String date) throws ParseException {
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Date formatedDate = sdf.parse(date); // returns a String when it is parsed
		 return formatedDate;
	}
	
	private String date2Str(Date date) {
		Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = formatter.format(date);
		return s;
	}
}