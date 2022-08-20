'use strict';
import * as common from './common.js'

const loginBox = document.querySelectorAll('.login');
const loginInputs = loginBox.item(0).querySelectorAll('input');

const loginBt = loginBox.item(0).querySelectorAll('.buttons>input');
const loginForm = document.querySelector('form[class="loginForm"]');

// 로그인
loginInputs.forEach(input => input.addEventListener('focus', common.toggleLabel));
loginInputs.forEach(input => input.addEventListener('focusout', common.toggleLabel));
loginInputs.forEach(input =>
    input.addEventListener('keypress', (e) => {
        if (e.code.toLowerCase() === "space") {
            e.preventDefault();
        }
    }));
//loginBt.forEach(bt => bt.addEventListener('click', loginAction))