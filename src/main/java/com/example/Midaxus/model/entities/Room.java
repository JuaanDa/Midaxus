package com.example.Midaxus.model.entities;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Room {
        private String roomId;
        private int capacity;
        private Set<String> features = new HashSet<>();
        private boolean support;

        // Getters
        public String getRoomId() { return roomId; }
        public int getCapacity() { return capacity; }
        public Set<String> getFeatures() { return features; }
        public boolean isSupport() { return support; }

        // Setters
        public void setRoomId(String roomId) { this.roomId = roomId; }
        public void setCapacity(int capacity) { this.capacity = capacity; }
        public void setFeatures(Set<String> features) {
            this.features = (features != null) ? new HashSet<>(features) : new HashSet<>();
        }
        public void setSupport(boolean support) { this.support = support; }

        @Override
        public String toString() {
            return "Room{" +
                    "roomId='" + roomId + '\'' +
                    ", capacity=" + capacity +
                    ", features=" + features +
                    ", support=" + support +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Room room)) return false;
            return Objects.equals(roomId, room.roomId);
        }
        @Override
        public int hashCode() { return Objects.hash(roomId); }
    }
