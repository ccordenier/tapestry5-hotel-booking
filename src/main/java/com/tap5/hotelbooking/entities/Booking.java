package com.tap5.hotelbooking.entities;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.apache.tapestry5.beaneditor.NonVisual;


@Entity
public class Booking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@NonVisual
	private Long id;

    private User user;

    private Hotel hotel;

    private Date checkinDate;

    private Date checkoutDate;

    private String creditCard;

    private String creditCardName;

    private int creditCardExpiryMonth;

    private int creditCardExpiryYear;

    private boolean smoking;

    private int beds;

	public Booking(){
		
	}
	
	public Booking(Hotel hotel, User user) {
        this.hotel = hotel;
        this.user = user;
        Calendar calendar = Calendar.getInstance();
        setCheckinDate(calendar.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        setCheckoutDate(calendar.getTime());
	}

	@Transient
    public BigDecimal getTotal() {
        return hotel.getPrice().multiply(new BigDecimal(getNights()));
    }

    @Transient
    public int getNights() {
        if (checkinDate == null || checkoutDate == null) {
            return 0;
        } else {
            return (int) (checkoutDate.getTime() - checkinDate.getTime()) / 1000 / 60 / 60 / 24;
        }
    }
    
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne
	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	@Basic
	public Date getCheckinDate() {
		return checkinDate;
	}

	public void setCheckinDate(Date checkinDate) {
		this.checkinDate = checkinDate;
	}

	@Basic
	public Date getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(Date checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public String getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}

	public String getCreditCardName() {
		return creditCardName;
	}

	public void setCreditCardName(String creditCardName) {
		this.creditCardName = creditCardName;
	}

	public int getCreditCardExpiryMonth() {
		return creditCardExpiryMonth;
	}

	public void setCreditCardExpiryMonth(int creditCardExpiryMonth) {
		this.creditCardExpiryMonth = creditCardExpiryMonth;
	}

	public int getCreditCardExpiryYear() {
		return creditCardExpiryYear;
	}

	public void setCreditCardExpiryYear(int creditCardExpiryYear) {
		this.creditCardExpiryYear = creditCardExpiryYear;
	}

	public boolean isSmoking() {
		return smoking;
	}

	public void setSmoking(boolean smoking) {
		this.smoking = smoking;
	}

	public int getBeds() {
		return beds;
	}

	public void setBeds(int beds) {
		this.beds = beds;
	}

	@Transient
    public String getDescription() {
        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
        return hotel == null ? null : hotel.getName() + ", " + df.format(getCheckinDate()) + " to "
                + df.format(getCheckoutDate());
    }
	
	/*public void validateEnterBookingDetails(ValidationContext context) {
        MessageContext messages = context.getMessageContext();
        if (checkinDate.before(today())) {
            messages.addMessage(new MessageBuilder().error().source("checkinDate").code(
                    "booking.checkinDate.beforeToday").build());
        } else if (checkoutDate.before(checkinDate)) {
            messages.addMessage(new MessageBuilder().error().source("checkoutDate").code(
                    "booking.checkoutDate.beforeCheckinDate").build());
        }
    }*/

    private Date today() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }

    @Override
    public String toString() {
        return "Booking(" + user + "," + hotel + ")";
    }

}
