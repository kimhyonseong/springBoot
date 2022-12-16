package com.example.foodpreference.utils;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Page {
  private int contentCnt; // 필수값
  private int start;  // 시작번호
  private int end;    // 끝번호
  private int currentPage = 1;  // 현재 페이지 1부터 시작
  private int blockSize = 10;   // 한 블록에 나오는 페이지 갯수
  private int pageSize = 20;    // 한 페이지에 나오는 컨텐츠 갯수
  private String link;
  private boolean next = false;
  private boolean prev = false;
  private int maxContentPage;

  public Page(int contentCnt) {
    this.contentCnt = contentCnt;
    calPage();
  }

  public Page(int contentCnt, int currentPage) {
    this.contentCnt = contentCnt;
    this.currentPage = currentPage;
    calPage();
  }

  public Page(int contentCnt, int currentPage, int blockSize, int pageSize) {
    this.contentCnt = contentCnt;
    this.currentPage =currentPage;
    this.blockSize = blockSize;
    this.pageSize = pageSize;
    calPage();
  }

  public void calPage() {
    calMaxContentCnt();
    calStartPage();
    calEndPage();
    prevNext();
  }

  public void calMaxContentCnt() {
    this.maxContentPage = (int) Math.ceil((double) contentCnt / pageSize);
  }

  public void calStartPage() {
    if (contentCnt > 0 && currentPage >= 1) {
      // ((현재 페이지 / 블록 사이즈) 올림
      this.start = ((currentPage-1) / blockSize) * blockSize + 1;

      this.start = Math.min(this.start, this.maxContentPage);
    } else {
      this.start = 1;
    }
  }

  public void calEndPage() {
    if (contentCnt > 0) {
      // ((현재페이지 / 블록 사이즈) 올림 * 블록 사이즈)
      this.end = (int) Math.ceil((double) (currentPage+1) / blockSize) * blockSize;

      // 전체 갯수에 대한 페이지보다 크다면 교체
      this.end = Math.min(this.end, this.maxContentPage);
    } else {
      this.end = this.start;
    }
  }

  public void prevNext() {
    this.prev = this.start > 1;
    this.next = this.end < (this.contentCnt/this.pageSize);
  }
}
