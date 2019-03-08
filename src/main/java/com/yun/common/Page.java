package com.yun.common;

public class Page {

    private int row;
    private int pageNo;
    private int pageSize;
    private int pageCnt;

    private int startNum;

    public int getStartNum() {
        return (this.getPageNo()-1)*this.getPageSize();
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getPageNo() {
        if(pageNo<1){
            pageNo=1;
        }
        if(pageNo>this.getPageCnt()){
            pageNo=this.getPageCnt();
        }
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageCnt() {
        if(row<=0){
            return 1;
        }
        if(row%pageSize==0){
            return row/pageSize;
        }else{
            return row/pageSize+1;
        }
    }

    public void setPageCnt(int pageCnt) {
        this.pageCnt = pageCnt;
    }
}
