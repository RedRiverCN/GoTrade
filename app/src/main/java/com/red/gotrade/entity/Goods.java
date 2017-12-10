package com.red.gotrade.entity;

import java.util.List;

/**
 * Created by Red on 2017/12/7.
 */

public class Goods {

    /**
     * code : 200
     * data : {"endRow":3,"firstPage":0,"hasNextPage":false,"hasPreviousPage":false,"isFirstPage":false,"isLastPage":true,"lastPage":0,"list":[{"client":{"address":"广东工业大学生活东区东一331","nickname":"管理员","password":"","phone":"","status":0,"token":""},"gName":"1","gSort":3,"gid":"1","goodImg":{"gid":"1","imgId":0,"imgUrl":"233"},"intro":"1","ownerPhone":"12345","price":1},{"client":{"address":"广东工业大学生活东区东一331","nickname":"管理员","password":"","phone":"","status":0,"token":""},"gName":"苹果手机","gSort":2,"gid":"2","goodImg":{"gid":"2","imgId":0,"imgUrl":"233"},"intro":"23","ownerPhone":"12345","price":2},{"client":{"address":"广东工业大学生活东区东一331","nickname":"管理员","password":"","phone":"","status":0,"token":""},"gName":"大米手机","gSort":2,"gid":"b8bc8518-d73a-4651-92a5-7ec1cca6a1e6","goodImg":{"gid":"b8bc8518-d73a-4651-92a5-7ec1cca6a1e6","imgId":0,"imgUrl":"111"},"intro":"9成新","ownerPhone":"12345","price":12.6}],"navigateFirstPage":0,"navigateLastPage":0,"navigatePages":8,"navigatepageNums":[],"nextPage":0,"orderBy":"","pageNum":0,"pageSize":0,"pages":0,"prePage":0,"size":3,"startRow":1,"total":3}
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
         * endRow : 3
         * firstPage : 0
         * hasNextPage : false
         * hasPreviousPage : false
         * isFirstPage : false
         * isLastPage : true
         * lastPage : 0
         * list : [{"client":{"address":"广东工业大学生活东区东一331","nickname":"管理员","password":"","phone":"","status":0,"token":""},"gName":"1","gSort":3,"gid":"1","goodImg":{"gid":"1","imgId":0,"imgUrl":"233"},"intro":"1","ownerPhone":"12345","price":1},{"client":{"address":"广东工业大学生活东区东一331","nickname":"管理员","password":"","phone":"","status":0,"token":""},"gName":"苹果手机","gSort":2,"gid":"2","goodImg":{"gid":"2","imgId":0,"imgUrl":"233"},"intro":"23","ownerPhone":"12345","price":2},{"client":{"address":"广东工业大学生活东区东一331","nickname":"管理员","password":"","phone":"","status":0,"token":""},"gName":"大米手机","gSort":2,"gid":"b8bc8518-d73a-4651-92a5-7ec1cca6a1e6","goodImg":{"gid":"b8bc8518-d73a-4651-92a5-7ec1cca6a1e6","imgId":0,"imgUrl":"111"},"intro":"9成新","ownerPhone":"12345","price":12.6}]
         * navigateFirstPage : 0
         * navigateLastPage : 0
         * navigatePages : 8
         * navigatepageNums : []
         * nextPage : 0
         * orderBy :
         * pageNum : 0
         * pageSize : 0
         * pages : 0
         * prePage : 0
         * size : 3
         * startRow : 1
         * total : 3
         */

        private int endRow;
        private int firstPage;
        private boolean hasNextPage;
        private boolean hasPreviousPage;
        private boolean isFirstPage;
        private boolean isLastPage;
        private int lastPage;
        private int navigateFirstPage;
        private int navigateLastPage;
        private int navigatePages;
        private int nextPage;
        private String orderBy;
        private int pageNum;
        private int pageSize;
        private int pages;
        private int prePage;
        private int size;
        private int startRow;
        private int total;
        private List<ListBean> list;
        private List<String> navigatepageNums;

        public int getEndRow() {
            return endRow;
        }

        public void setEndRow(int endRow) {
            this.endRow = endRow;
        }

        public int getFirstPage() {
            return firstPage;
        }

        public void setFirstPage(int firstPage) {
            this.firstPage = firstPage;
        }

        public boolean isHasNextPage() {
            return hasNextPage;
        }

        public void setHasNextPage(boolean hasNextPage) {
            this.hasNextPage = hasNextPage;
        }

        public boolean isHasPreviousPage() {
            return hasPreviousPage;
        }

        public void setHasPreviousPage(boolean hasPreviousPage) {
            this.hasPreviousPage = hasPreviousPage;
        }

        public boolean isIsFirstPage() {
            return isFirstPage;
        }

        public void setIsFirstPage(boolean isFirstPage) {
            this.isFirstPage = isFirstPage;
        }

        public boolean isIsLastPage() {
            return isLastPage;
        }

        public void setIsLastPage(boolean isLastPage) {
            this.isLastPage = isLastPage;
        }

        public int getLastPage() {
            return lastPage;
        }

        public void setLastPage(int lastPage) {
            this.lastPage = lastPage;
        }

        public int getNavigateFirstPage() {
            return navigateFirstPage;
        }

        public void setNavigateFirstPage(int navigateFirstPage) {
            this.navigateFirstPage = navigateFirstPage;
        }

        public int getNavigateLastPage() {
            return navigateLastPage;
        }

        public void setNavigateLastPage(int navigateLastPage) {
            this.navigateLastPage = navigateLastPage;
        }

        public int getNavigatePages() {
            return navigatePages;
        }

        public void setNavigatePages(int navigatePages) {
            this.navigatePages = navigatePages;
        }

        public int getNextPage() {
            return nextPage;
        }

        public void setNextPage(int nextPage) {
            this.nextPage = nextPage;
        }

        public String getOrderBy() {
            return orderBy;
        }

        public void setOrderBy(String orderBy) {
            this.orderBy = orderBy;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getPrePage() {
            return prePage;
        }

        public void setPrePage(int prePage) {
            this.prePage = prePage;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getStartRow() {
            return startRow;
        }

        public void setStartRow(int startRow) {
            this.startRow = startRow;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public List<String> getNavigatepageNums() {
            return navigatepageNums;
        }

        public void setNavigatepageNums(List<String> navigatepageNums) {
            this.navigatepageNums = navigatepageNums;
        }

        public static class ListBean {
            /**
             * client : {"address":"广东工业大学生活东区东一331","nickname":"管理员","password":"","phone":"","status":0,"token":""}
             * gName : 1
             * gSort : 3
             * gid : 1
             * goodImg : {"gid":"1","imgId":0,"imgUrl":"233"}
             * intro : 1
             * ownerPhone : 12345
             * price : 1
             */

            public ClientBean client;
            public Boolean deal;
            public String gName;
            public int gSort;
            public String gid;
            public GoodImgBean goodImg;
            public String intro;
            public String ownerPhone;
            public double price;

            public ClientBean getClient() {
                return client;
            }

            public void setClient(ClientBean client) {
                this.client = client;
            }

            public Boolean getDeal() {
                return deal;
            }

            public void setDeal(Boolean deal) {
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

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public static class ClientBean {
                /**
                 * address : 广东工业大学生活东区东一331
                 * nickname : 管理员
                 * password :
                 * phone :
                 * status : 0
                 * token :
                 */

                public String address;
                public String nickname;
                public String password;
                public String phone;
                public int status;
                public String token;

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
                 * gid : 1
                 * imgId : 0
                 * imgUrl : 233
                 */

                public String gid;
                public int imgId;
                public String imgUrl;

                public String getGid() {
                    return gid;
                }

                public void setGid(String gid) {
                    this.gid = gid;
                }

                public int getImgId() {
                    return imgId;
                }

                public void setImgId(int imgId) {
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
}
