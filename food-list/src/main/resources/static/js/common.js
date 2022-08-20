'use strict';

//클래스까지 스크롤
export function scrollClass(clasNm) {
    const element = document.querySelector(`.${clasNm}`);
    setTimeout(() => {
        window.scroll({
            top: window.scrollY + element.getBoundingClientRect().top,
            behavior: 'smooth'
        })
    }, 0);
}

//라벨 클래스 토글
export function toggleLabel(e) {
    const label = this.parentNode.querySelector(`label[for="${this.id}"]`);
    e.preventDefault();
    if (label === null) return 0;
    if (this.value.trim() === '') {
        label.classList.toggle('focus');
    } else {
        label.classList.toggle('font-black');
    }
}

// 클래스 토클
export function toggleActive() {
    if (!this.classList.contains('a-fix')) {
        this.classList.toggle('active');
    }
}