package com.red.gotrade.entity;

import java.util.List;

/**
 * Created by Red on 2017/12/10.
 */

public class MyGoods {
    /**
     * code : 200
     * data : [{"client":{"address":"东一331","nickname":"红河","password":"","phone":"","status":0,"token":""},"deal":false,"gName":"无敌u盘","gSort":1,"gid":"2db143cc-0c00-49aa-8a9d-1173b1ca1320","goodImg":{"gid":"2db143cc-0c00-49aa-8a9d-1173b1ca1320","imgId":0,"imgUrl":"http://39.108.62.108:8080/sjk/img/electronic.png"},"intro":"快来瞧瞧，USB3.0极速U盘500M每秒上下行","ownerPhone":"311","price":150}]
     * message : SUCCESS
     */

    private int code;
    private String message;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * client : {"address":"东一331","nickname":"红河","password":"","phone":"","status":0,"token":""}
         * deal : false
         * gName : 无敌u盘
         * gSort : 1
         * gid : 2db143cc-0c00-49aa-8a9d-1173b1ca1320
         * goodImg : {"gid":"2db143cc-0c00-49aa-8a9d-1173b1ca1320","imgId":0,"imgUrl":"http://39.108.62.108:8080/sjk/img/electronic.png"}
         * intro : 快来瞧瞧，USB3.0极速U盘500M每秒上下行
         * ownerPhone : 311
         * price : 150
         */

        private ClientBean client;
        private boolean deal;
        private String gName;
        private int gSort;
        private String gid;
        private GoodImgBean goodImg;
        private String intro;
        private String ownerPhone;
        private Double price;

        public ClientBean getClient() {
            return client;
        }

        public void setClient(ClientBean client) {
            this.client = client;
        }

        public boolean isDeal() {
            return deal;
        }

        public void setDeal(boolean deal) {
            this.deal = deal;
        }

        public String getGName() {
            return gName;
        }

        public void setGName(String gName) {
            this.gName = gName;
        }

        public int getGSort() {
            return gSort;
        }

        public void setGSort(int gSort) {
            this.gSort = gSort;
        }

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public GoodImgBean getGoodImg() {
            return goodImg;
        }

        public void setGoodImg(GoodImgBean goodImg) {
            this.goodImg = goodImg;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getOwnerPhone() {
            return ownerPhone;
        }

        public void setOwnerPhone(String ownerPhone) {
            this.ownerPhone = ownerPhone;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public static class ClientBean {
            /**
             * address : 东一331
             * nickname : 红河
             * password :
             * phone :
             * status : 0
             * token :
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

        public static class GoodImgBean {
            /**
             * gid : 2db143cc-0c00-49aa-8a9d-1173b1ca1320
             * imgId : 0
             * imgUrl : http://39.108.62.108:8080/sjk/img/electronic.png
             */

            private String gid;
            private String imgId;
            private String imgUrl;

            public String getGid() {
                return gid;
            }

            public void setGid(String gid) {
                this.gid = gid;
            }

            public String getImgId() {
                return imgId;
            }

            public void setImgId(String imgId) {
                this.imgId = imgId;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }
        }
    }
}
