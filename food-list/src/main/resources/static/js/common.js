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

export function rexExp(value) {
    // 특수문자 포함시 true
    let specialStr = new RegExp(/[`~!@#$%^&*\[\]|'"()\-_+=,.\/\\?><]/, "gi");
    return specialStr.test(value);
}

export function valueEmpty(value) {
    // 비었으면 false
    return value.trim() !== "";
}

export function removeAllActive() {
    Array.from(this).forEach(ele => {
        ele.classList.remove('active');
    })
}

export function getCookie(name) {
    let matches = document.cookie.match(new RegExp(
        "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
    ));
    let result = matches ? decodeURIComponent(matches[0]) : undefined;
    return result ? result.split('=')[1] : null;
}

export function setCookie(name, value, options = {}) {
    options = {
        path: '/',
    };

    if (options.expires instanceof Date) {
        options.expires = options.expires.toUTCString();
    }

    let updatedCookie = encodeURIComponent(name) + "=" + encodeURIComponent(value);

    for (let optionKey in options) {
        updatedCookie += `; ${optionKey}`;
        let optionValue = options[optionKey];
        if (optionValue !== true) {
            updatedCookie += `=${optionValue}`;
        }
    }

    document.cookie = updatedCookie;
}

export function login(path) {
    location.href = `/login?redirect=${path}`;
}