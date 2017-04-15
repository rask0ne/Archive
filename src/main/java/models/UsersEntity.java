package models;

import javax.persistence.*;

/**
 * Created by rask on 15.04.2017.
 */

    @Entity
    @Table(name = "users", schema = "archive", catalog = "")
    public class UsersEntity {
        private int id;
        private String username;
        private String password;
        private int role;

        public UsersEntity() {
        }

        public UsersEntity(int id, String username, String password, int role){
            this.setId(id);
            this.setUsername(username);
            this.setPassword(password);
            this.setRole(role);
        }

        @Id
        @Column(name = "Id", nullable = false)
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Basic
        @Column(name = "Username", nullable = false, length = 128)
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        @Basic
        @Column(name = "Password", nullable = false, length = 32)
        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Basic
        @Column(name = "Role", nullable = false)
        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            models.UsersEntity that = (models.UsersEntity) o;

            if (id != that.id) return false;
            if (role != that.role) return false;
            if (username != null ? !username.equals(that.username) : that.username != null) return false;
            if (password != null ? !password.equals(that.password) : that.password != null) return false;

            return true;
        }

        public UsersEntity(String username, String password, int role) {

            this.username = username;
            this.password = password;
            this.role = role;

        }

        @Override
        public int hashCode() {
            int result = id;
            result = 31 * result + (username != null ? username.hashCode() : 0);
            result = 31 * result + (password != null ? password.hashCode() : 0);
            result = 31 * result + role;
            return result;
        }
    }
