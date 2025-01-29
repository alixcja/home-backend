package de.explore.grabby.booking.model.booking;

import de.explore.grabby.booking.model.entity.BookingEntity;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Booking implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOKING_SEQ")
  @SequenceGenerator(name = "BOOKING_SEQ", sequenceName = "BOOKING_TABLE_SEQ", allocationSize = 1)

  @Column(name = "id")
  private int id;

  @Column(name = "user_id")
  private String userId;

  @JoinColumn(name = "bookingEntity_id")
  @ManyToOne
  private BookingEntity bookingEntity;

  private LocalDate startDate;

  private LocalDate endDate;

  private LocalDate bookingDate;

  private Boolean isCancelled;

  private Boolean isReturned;
  // field to count how often booking was extended

  public Booking() {
  }

  public Booking(String userId, BookingEntity bookingEntity, LocalDate startDate, LocalDate endDate) {
    this.userId = userId;
    this.bookingEntity = bookingEntity;
    this.startDate = startDate;
    this.endDate = endDate;
    this.bookingDate = LocalDate.now();
    this.isCancelled = false;
    this.isReturned = false;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public BookingEntity getBookingEntity() {
    return bookingEntity;
  }

  public void setBookingEntity(BookingEntity bookingEntity) {
    this.bookingEntity = bookingEntity;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public LocalDate getBookingDate() {
    return bookingDate;
  }

  public void setBookingDate(LocalDate bookingDate) {
    this.bookingDate = bookingDate;
  }

  public Boolean getIsCancelled() {
    return isCancelled;
  }

  public void setIsCancelled(Boolean isCancelled) {
    this.isCancelled = isCancelled;
  }

  public Boolean getIsReturned() {
    return isReturned;
  }

  public void setIsReturned(Boolean isReturned) {
    this.isReturned = isReturned;
  }
}
