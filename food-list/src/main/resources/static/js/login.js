'use strict';
import * as common from './common.js'

const loginBox = document.querySelectorAll('.login');
const loginInputs = loginBox?.item(0).querySelectorAll('input');
const loginBt = loginBox?.item(0).querySelectorAll('.buttons>input');
const loginForm = document.querySelector('form[class="loginForm"]');
const registerForm = document.querySelector('form[class="registerForm"]');

// 로그인
if (loginInputs != null) {
    loginInputs.forEach(input => {
        if ((input.type === "text" || input.type === "password") && input.value !== "") {
            document.querySelector(`label[for="${input.id}"]`).classList.add("focus");
            document.querySelector(`label[for="${input.id}"]`).classList.add("font-black");
        }
    })
    loginInputs.forEach(input => input.addEventListener('focus', common.toggleLabel));
    loginInputs.forEach(input => input.addEventListener('focusout', common.toggleLabel));
    loginInputs.forEach(input =>
        input.addEventListener('keypress', (e) => {
            if (e.code.toLowerCase() === "space") {
                e.preventDefault();
            }
        }));
}
loginBt?.forEach(bt => bt.addEventListener('click', loginAction))

function loginAction(e) {
    e.preventDefault();

    // 로그인 클릭
    if (this.dataset.value === 'login') {
        const userId = loginForm.querySelector('input[name="memberId"]').value;
        const password = loginForm.querySelector('input[name="memberPw"]').value;

        // 빈칸 확인
        if (!common.valueEmpty(userId) || !common.valueEmpty(password)) {
            alert("빈칸을 확인해주세요.");
            return false;
        }

        loginForm.submit();
    } else if(this.dataset.value === 'register') {
        location.href = "/register";
    } else if(this.dataset.value === 'signup') {
        register();
    } else if(this.dataset.value === 'goLogin') {
        location.href = "/login";
    }
}

function register() {
    const user = {
        userId: registerForm.querySelector("#memberId").value.trim(),
        password: registerForm.querySelector("#memberPw").value.trim(),
        name: registerForm.querySelector('input[name="name"]').value.trim()
    }

    if (!validationUser(user)) {
        return false;
    } else {
        registerForm.submit();
    }
}

registerForm?.querySelector('input[data-value="goLogin"]')
    .addEventListener('click', history.back);

function validationUser(userInfo) {
    // 유효성 검사 - 모두 통과시 true
    if (typeof userInfo !== "object") {
        alert('유효한 값이 아닙니다.');
        return false;
    }
    if (common.rexExp(userInfo.userId) || common.rexExp(userInfo.name)) {
        alert("아이디와 이름에는 특수문자 사용이 불가 합니다.");
        return false;
    }
    if (!common.valueEmpty(userInfo.userId) || !common.valueEmpty(userInfo.name)) {
        alert('빈칸을 확인해주세요.');
        return false;
    }
    return true;
}