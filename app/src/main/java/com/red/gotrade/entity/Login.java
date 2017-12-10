package com.red.gotrade.entity;

/**
 * Created by Red on 2017/12/7.
 */

public class Login {

    /**
     * code : 200
     * data : {"address":"广东工业大学生活东区东一331","nickname":"管理员","password":"","phone":12345,"status":1,"token":"64a30e8a-3639-43e1-8f5b-ac8fd0a2c76b"}
     * message : SUCCESS
     */

    private int code;
    private DataBean data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * address : 广东工业大学生活东区东一331
         * nickname : 管理员
         * password :
         * phone : 12345
         * status : 1
         * token : 64a30e8a-3639-43e1-8f5b-ac8fd0a2c76b
         */

        private String address;
        private String nickname;
        private String password;
        private String phone;
        private int status;
        private String token;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
