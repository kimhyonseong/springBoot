import jsonData from './food.json' assert {type: "json"};
import * as json from "./jsonParse.js";

const foodData = jsonData;

// 초기 셋팅
const foodList = document.querySelector('.food-list');
foodList.innerHTML = json.showFood(foodData);
const foodInfo = document.querySelector(".food-info");

// 객체 선언
const mainBox = document.querySelector('main');
const foodName = document.getElementById("food");
let foodNum = parseInt(window.localStorage.getItem("currentFood")) || 0;  // 현재 음식 인덱스

const cate = document.querySelector('.category');
const cateBtn = cate.querySelectorAll('label');

const loginBox = document.querySelectorAll('.login');
const loginInputs = loginBox.item(0).querySelectorAll('input');
const regiInputs = loginBox.item(1).querySelectorAll('input');
const loginBt = loginBox.item(0).querySelectorAll('.buttons>input');
const loginForm = document.querySelector('form[class="loginForm"]');
const registerForm = document.querySelector('form[class="registerForm"]');

const searchForm = document.querySelector(".search-form");

//클래스까지 스크롤
function scrollClass(clasNm) {
    const element = document.querySelector(`.${clasNm}`);
    setTimeout(() => {
        window.scroll({
            top: window.scrollY + element.getBoundingClientRect().top,
            behavior: 'smooth'
        })
    }, 0);
}

// 클래스 토클
function toggleActive() {
    if (!this.classList.contains('a-fix')) {
        this.classList.toggle('active');
    }
}

function removeActive() {
    Array.from(this).forEach(ele => {
        ele.classList.remove('active');
    })
}

function activeFix(index) {
    cateBtn.forEach((btn, i) => {
        btn.classList.remove('a-fix');
        btn.classList.remove('active');

        if (i === index) {
            btn.classList.add('a-fix');
            btn.classList.add('active');
        }
    })
}

function colorStar(stars, num = 0) {
    for (let i = 0; i < 5; i++) {
        if (i <= num) {
            stars.item(i).classList.add('yellow');
        } else {
            stars.item(i).classList.remove('yellow');
        }
    }

    if (num === -1) {
        for (let i = 0; i < 5; i++) {
            if (stars.item(i).classList.contains('fixedStar')) {
                stars.item(i).classList.add('yellow');
            }
        }
    }
}

function fixStar(stars, num = 0) {
    // 로그인 이동
    if (getCookie('id') === null) {
        if (confirm('로그인이 필요합니다. 로그인하시겠습니까?')) {
            // 현재 음식 인덱스 번호 지정
            window.localStorage.setItem("currentFood", `${foodNum}`);
            login();
            scrollClass('background');
        }
        return false;
    }

    for (let i = 0; i < 5; i++) {
        if (i <= num) {
            stars.item(i).classList.add('fixedStar');
        } else {
            stars.item(i).classList.remove('fixedStar');
        }
    }

    return true;
}

function login() {
    allNone();
    displayShow('login');
}

function loginAction(e) {
    if (this.dataset.value === 'login') {
        e.preventDefault();

        const userId = loginForm.querySelector('input[name="user_id"]').value;
        const password = loginForm.querySelector('input[name="user_pw"]').value;

        if (!valueEmpty(userId) || !valueEmpty(password)) {
            alert("빈칸을 확인해주세요.");
            return false;
        }

        if (window.localStorage.getItem('id')) {
            const userDataId = window.localStorage.getItem('id').split(',');
            const userDataPw = window.localStorage.getItem('password').split(',');
            const userDataNm = window.localStorage.getItem('name').split(',');

            let test = userDataId.indexOf(userId);

            if (userDataPw[test] === password) {
                setCookie('id', userDataId[test]);
                setCookie('name', userDataNm[test]);
                //alert('로그인 완료.');

                // 내가 전에 평가하던 페이지로 가기
                allNone();
                foodInfo.innerHTML = json.foodTemplate(foodData[window.localStorage.getItem("currentFood")]);
                setTimeout(() => {
                    displayShow('form-div');
                    displayShow('food-list');
                    displayShow('food-info');
                }, 500)
            } else {
                alert('아이디와 비밀번호가 일치하지 않습니다.');
                return false;
            }
        } else {
            alert("가입되지 않은 회원입니다.");
            return false;
        }
    } else {
        register();
    }
}

function register() {
    allNone();
    displayShow('register');
}

function getCookie(name) {
    let matches = document.cookie.match(new RegExp(
        "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
    ));
    let result = matches ? decodeURIComponent(matches[0]) : undefined;
    return result ? result.split('=')[1] : null;
}

function setCookie(name, value, options = {}) {
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

function localSave(obj) {
    const userIds = window.localStorage.getItem('id');
    let all = {
        id: '',
        password: '',
        name: ''
    }

    if (typeof obj === 'object' && obj !== null) {
        if (userIds) {
            if (userIds.split(',').includes(obj.userId)) {
                alert('이미 사용 중인 아이디입니다.');
                return false;
            }
            all.id = ',' + userIds;
            all.password = ',' + window.localStorage.getItem('password');
            all.name = ',' + window.localStorage.getItem('name');
        }

        window.localStorage.setItem('id', `${obj.userId}${all.id}`);
        window.localStorage.setItem('password', `${obj.password}${all.password}`);
        window.localStorage.setItem('name', `${obj.name}${all.name}`);
    } else {
        alert('잘못된 요청입니다.');
        return false;
    }
    return true;
}

function toggleLabel(e) {
    const label = this.parentNode.querySelector(`label[for="${this.id}"]`);
    e.preventDefault();
    if (label === null) return 0;
    if (this.value.trim() === '') {
        label.classList.toggle('focus');
    } else {
        label.classList.toggle('font-black');
    }
}

function allNone() {
    Array.from(mainBox.children).forEach(child => {
        child.classList.add('none');
    });
}

function displayShow(classNm) {
    const element = document.querySelector(`.${classNm}`);
    element.classList.remove('none');
}

function displayNone(classNm) {
    const element = document.querySelector(`.${classNm}`);
    element.classList.add('none');
}

// 음식 검색
foodName.addEventListener('focus', toggleLabel);
foodName.addEventListener('focusout', toggleLabel);

// 카테고리 이벤트
let prevCateIndex = 0;
cateBtn.forEach(btn => btn.addEventListener('mouseenter', toggleActive))
cateBtn.forEach(btn => btn.addEventListener('mouseleave', toggleActive))
cateBtn.forEach((btn, index) =>
    btn.addEventListener('click', () => {
        // 이전 카테고리와 누른 카테고리가 다를 시 이벤트 진행
        if (prevCateIndex !== index) {
            activeFix(index);
            displayNone("food-list");
            setTimeout(() => {
                foodList.innerHTML = json.showFood(foodData, index);
                displayShow("food-list");
            }, 500);
        }
        // 이전 카테고리 순서 저장
        prevCateIndex = index;
    }));
activeFix(0);

// 음식 선택 이벤트
function foodClickEvent(e) {
    if (e.target.classList.contains("food-name") && e.type === "click") {
        const index = e.target.dataset.num;
        foodNum = index;

        foodInfo.innerHTML = json.foodTemplate(foodData[index]);
        removeActive.call(document.querySelectorAll(".food-name"));
        drawMyStar(index);
        myComment(index);
        reloadFoodScore(index);
        displayShow('food-info');
        scrollClass('food-info');
    }
}

let prevFoodList = foodList;
function foodMousemoveEvent(e) {
    //enter
    if (e.target.classList.contains("food-name") && !prevFoodList.classList.contains("food-name")) {
        toggleActive.call(e.target);
    }

    //leave
    else if (!e.target.classList.contains("food-name") && prevFoodList.classList.contains("food-name")) {
        toggleActive.call(prevFoodList);
    } else if (!e.target.classList.contains("food-name") && !prevFoodList.classList.contains("food-name")) {
        removeActive.call(document.querySelectorAll(".food-name"));
    }
    prevFoodList = e.target;
}

function drawMyStar(num) {
    let rating = window.localStorage.getItem("foodRating") || null;
    let myRating = null;
    rating = JSON.parse(rating);

    if (getCookie('id') === null || getCookie('id') === undefined) {
        return false
    }

    if (rating !== null) {
        myRating = rating.filter(data => parseInt(data.num) === parseInt(num)) || null;

        if (myRating !== null && myRating.length > 0) {
            fixStar(document.querySelectorAll(".star"), myRating[0].star);
            colorStar(document.querySelectorAll(".star"), myRating[0].star);
        } else {
            return false;
        }
    }
}

function myComment (num) {
    let rating = window.localStorage.getItem("foodRating") || null;
    let myComment = null;
    rating = JSON.parse(rating);

    if (getCookie('id') === null || getCookie('id') === undefined) {
        return false
    }

    if (rating !== null) {
        myComment = rating.filter(data => parseInt(data.num) === parseInt(num)) || null;

        if (myComment !== null && myComment.length > 0) {
            document.querySelector(".food-comment>textarea").innerText = myComment[0].comment;
        } else {
            return false;
        }
    }
}

function reloadFoodScore(num) {
    let rating = window.localStorage.getItem("foodRating") || null;
    let review = foodData.filter(data=>parseInt(data.num) === parseInt(num)).map(data => data.comment)[0];
    let totalSize = review.length;
    let firstValue = 0;
    let totalScore = 0;
    let avg = 0;

    if (rating !== null) {
        rating = JSON.parse(rating).filter(data => parseInt(data.num) === parseInt(num));

        if (rating.length > 0) {
            firstValue = parseInt(rating[0].star) + 1;
            totalSize++;
        }
    }

    // 누산기를 이용하여 총점 계산
    totalScore = review.reduce((total,data) => parseInt(total) + parseInt(data.star),firstValue);
    avg = (totalScore / totalSize).toFixed(1);

    document.querySelector(".avr").innerText = avg;
}

foodList.addEventListener("click", foodClickEvent);
foodList.addEventListener("mousemove", foodMousemoveEvent);


// 별 평가 이벤트
let prevFoodInfo = "NONE";
function starMousemoveEvent(e) {
    // leave
    if (prevFoodInfo.toLowerCase() === "label" && e.target.nodeName.toLowerCase() !== "label") {
        const star = document.querySelectorAll(".star");
        colorStar.call(window, star, -1);
    }

    // enter
    if (e.target.nodeName.toLowerCase() === "label" && e.type === "mousemove") {
        const star = document.querySelectorAll(".star");
        let index = Array.from(star).findIndex((ele) => ele === e.toElement.parentElement);
        colorStar.call(window, star, index);
    }
    prevFoodInfo = e.target.nodeName;
}

function starClickEvent(e) {
    const star = document.querySelectorAll("input[name='star']");
    const starLabel = document.querySelectorAll(".star");
    let index = Array.from(star).findIndex((ele) => ele === e.target);

    fixStar(starLabel, index);
}

function writeReview() {
    const index = document.querySelector("input[name='star']:checked").value;
    const comment = document.querySelector(".food-comment>textarea").value;
    const saveInfo = {
        num: foodNum,
        star: index-1,
        comment: comment
    }

    ratingStar(saveInfo);
}

function foodInfoClickEvent(e) {
    if (e.target.name === "star") {
        starClickEvent(e);
    } else if (e.target.nodeName.toLowerCase() === "button") {
        writeReview();
        alert("리뷰 작성이 완료되었습니다.");
        reloadFoodScore(foodNum);
        e.preventDefault();
    }
}

foodInfo.addEventListener("click", foodInfoClickEvent);
foodInfo.addEventListener("mousemove", starMousemoveEvent);

// 로그인
loginInputs.forEach(input => input.addEventListener('focus', toggleLabel));
loginInputs.forEach(input => input.addEventListener('focusout', toggleLabel));
loginInputs.forEach(input =>
    input.addEventListener('keypress', (e) => {
        if (e.code.toLowerCase() === "space") {
            e.preventDefault();
        }
    }));
loginBt.forEach(bt => bt.addEventListener('click', loginAction))

// 회원가입
function valueEmpty(value) {
    // 비었으면 false
    return value.trim() !== "";
}

function rexExp(value) {
    // 특수문자 포함시 true
    let specialStr = new RegExp(/[`~!@#$%^&*\[\]|'"()\-_+=,.\/\\?><]/, "gi");
    return specialStr.test(value);
}

function validationUser(userInfo) {
    // 유효성 검사 - 모두 통과시 true
    if (typeof userInfo !== "object") {
        alert('유효한 값이 아닙니다.');
        return false;
    }
    if (rexExp(userInfo.userId) || rexExp(userInfo.name)) {
        alert("아이디와 이름에는 특수문자 사용이 불가 합니다.");
        return false;
    }
    if (!valueEmpty(userInfo.userId) || !valueEmpty(userInfo.name)) {
        alert('빈칸을 확인해주세요.');
        return false;
    }
    return true;
}

// 회원가입
regiInputs.forEach(input => input.addEventListener('focus', toggleLabel));
regiInputs.forEach(input => input.addEventListener('focusout', toggleLabel));
regiInputs.forEach(input =>
    input.addEventListener('keypress', (e) => {
        if (e.code.toLowerCase() === "space") {
            e.preventDefault();
        }
    }));
registerForm.addEventListener('submit', (e) => {
    e.preventDefault();

    const user = {
        userId: registerForm.querySelector('input[name="user_id"]').value.trim(),
        password: registerForm.querySelector('input[name="user_pw"]').value.trim(),
        name: registerForm.querySelector('input[name="user_name"]').value.trim()
    }

    if (!validationUser(user)) {
        return false;
    }

    if (localSave(user)) {
        alert('가입이 완료되었습니다.');
        allNone();
        displayShow('login');
    }
})

registerForm.querySelector('input[data-value="goLogin"]')
    .addEventListener('click', login);

function ratingStar(saveInfo) {
    const obj = {
        num: saveInfo.num,
        star: saveInfo.star,
        comment: saveInfo.comment
    }
    let prevRating = "";
    let ratingArray = [];
    ratingArray.push(obj);

    if (window.localStorage.getItem("foodRating")) {
        prevRating = JSON.parse(window.localStorage.getItem("foodRating"));

        //이전에 평가한 것 중에 없으면 진행
        if (!prevRating.some(ele => ele.num === saveInfo.num)) {
            ratingArray = [...ratingArray, ...prevRating];
            window.localStorage.setItem("foodRating", JSON.stringify(ratingArray));
        } else {
            // 이전에 평가 했으면 star 값만 수정
            prevRating = prevRating.map(data => {
                if (parseInt(data.num) === parseInt(obj.num)) {
                    data.star = obj.star;
                    data.comment = obj.comment;
                    return data;
                } else {
                    return data;
                }
            });

            window.localStorage.setItem("foodRating", JSON.stringify(prevRating));
        }
    } else {
        window.localStorage.setItem("foodRating", JSON.stringify(ratingArray));
    }
}

// 음식 검색
function searchFood() {
    const foodName = document.querySelector(".food-search").value;
    let resultFood = foodData.filter(food => food.name.includes(foodName));

    foodList.innerHTML = json.showFood(resultFood);
}

searchForm.addEventListener("submit", (e) => {
    e.preventDefault();
    searchFood();
});