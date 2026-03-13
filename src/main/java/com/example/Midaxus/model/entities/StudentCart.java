package com.example.Midaxus.model.entities;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;


public class StudentCart {

        private String cartId;
        private Student student;
        private AcademicPeriod academicPeriod;
        private LocalDateTime createdAt;
        private CartStatus status;
        private Set<StudentCartItem> cartItems = new LinkedHashSet<>();

        // Getters
        public String getCartId() { return cartId; }
        public Student getStudent() { return student; }
        public AcademicPeriod getAcademicPeriod() { return academicPeriod; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public CartStatus getStatus() { return status; }
        public Set<StudentCartItem> getCartItems() { return cartItems; }

        // Setters
        public void setCartId(String cartId) { this.cartId = cartId; }
        public void setStudent(Student student) { this.student = student; }
        public void setAcademicPeriod(AcademicPeriod academicPeriod) { this.academicPeriod = academicPeriod; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
        public void setStatus(CartStatus status) { this.status = status; }

        /** Reemplaza el conjunto completo manteniendo la bidireccionalidad */
        public void setCartItems(Set<StudentCartItem> newItems) {
            for (StudentCartItem it : new LinkedHashSet<>(cartItems)) {
                it.setStudentCart(null);
            }
            this.cartItems.clear();
            if (newItems != null) {
                for (StudentCartItem it : newItems) addItem(it);
            }
        }

        // Conveniencia para manejar la composición 1..* (StudentCart -> StudentCartItem)
        public void addItem(StudentCartItem item) {
            if (item == null) return;
            item.setStudentCart(this); // internamente llamará a internalAddItem
        }

        public void removeItem(StudentCartItem item) {
            if (item == null) return;
            if (cartItems.contains(item)) item.setStudentCart(null); // internamente llamará a internalRemoveItem
        }

        // Solo uso interno para evitar recursión en setStudentCart
        void internalAddItem(StudentCartItem item) { this.cartItems.add(item); }
        void internalRemoveItem(StudentCartItem item) { this.cartItems.remove(item); }

        @Override
        public String toString() {
            return "StudentCart{" +
                    "cartId='" + cartId + '\'' +
                    ", student=" + student +
                    ", academicPeriod=" + academicPeriod +
                    ", createdAt=" + createdAt +
                    ", status=" + status +
                    ", cartItemsCount=" + cartItems.size() +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof StudentCart that)) return false;
            return Objects.equals(cartId, that.cartId);
        }
        @Override
        public int hashCode() { return Objects.hash(cartId); }
    }
