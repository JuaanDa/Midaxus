package com.example.Midaxus.model.entities;
import java.time.LocalDateTime;
import java.util.Objects;

public class StudentCartItem{

    private String cartItemId;
    private StudentCart studentCart;     // composición: pertenece a un StudentCart
    private CourseGroup courseGroup;
    private LocalDateTime addedAt;

    // Getters
    public String getCartItemId() { return cartItemId; }
    public StudentCart getStudentCart() { return studentCart; }
    public CourseGroup getCourseGroup() { return courseGroup; }
    public LocalDateTime getAddedAt() { return addedAt; }

    // Setters
    public void setCartItemId(String cartItemId) { this.cartItemId = cartItemId; }

    /** Mantiene la relación bidireccional con StudentCart */
    public void setStudentCart(StudentCart studentCart) {
        if (this.studentCart != null && this.studentCart != studentCart) {
            this.studentCart.internalRemoveItem(this);
        }
        this.studentCart = studentCart;
        if (studentCart != null) {
            studentCart.internalAddItem(this);
        }
    }

    public void setCourseGroup(CourseGroup courseGroup) { this.courseGroup = courseGroup; }
    public void setAddedAt(LocalDateTime addedAt) { this.addedAt = addedAt; }

    @Override
    public String toString() {
        return "StudentCartItem{" +
                "cartItemId='" + cartItemId + '\'' +
                ", studentCartId=" + (studentCart != null ? studentCart.getCartId() : "null") +
                ", courseGroup=" + courseGroup +
                ", addedAt=" + addedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentCartItem that)) return false;
        return Objects.equals(cartItemId, that.cartItemId);
    }
    @Override
    public int hashCode() { return Objects.hash(cartItemId); }
}

