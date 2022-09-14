import * as common from "./common.js";

const foodList = document.querySelector('.food-list');
const foodInfo = document.querySelector(".food-info");

foodList?.addEventListener("click", foodClickEvent);
foodList?.addEventListener("mousemove", foodMousemoveEvent);

foodInfo?.addEventListener("click", foodInfoClickEvent);
foodInfo?.addEventListener("mousemove", starMousemoveEvent);

// 음식 선택 이벤트
function foodClickEvent(e) {
    if (e.target.classList.contains("food-name") && e.type === "click") {
        foodTemplate(e.target.dataset.infoId).then(r => {
            console.log(r);
            foodInfo.classList.remove("none");
        });
        foodReviewTemplate(e.target.dataset.infoId).then(r => console.log(r));
    }
}

let prevFoodList = foodList;
function foodMousemoveEvent(e) {
    //enter
    if (e.target.classList.contains("food-name") && !prevFoodList.classList.contains("food-name")) {
        common.toggleActive.call(e.target);
    }

    //leave
    else if (!e.target.classList.contains("food-name") && prevFoodList.classList.contains("food-name")) {
        common.toggleActive.call(prevFoodList);
    } else if (!e.target.classList.contains("food-name") && !prevFoodList.classList.contains("food-name")) {
        common.removeAllActive.call(document.querySelectorAll(".food-name"));
    }
    prevFoodList = e.target;
}

export async function foodTemplate(index) {
    try {
        let loginComment = "";
        let loginScore = 0;

        let url = `/food/info/${index}`;
        await axios.get(url).then((response)=> {
            let data = response.data;

            if (data.myReview != null) {
                loginComment = data.myReview.comment;
                loginScore = data.myReview.score;
            }

            foodInfo.querySelector(".food-info-box").innerHTML = `<div class="food-img">
                                <img src="${data.food.foodImg.imgUrl}" alt="">
                            </div>
                            <div class="food-avr">
                            평점 <span class="avr">${data.totalScore}</span>
                            <span class="avr-count">(${data.totalCount}명 참여)</span>
                            </div>
                            <div class="rating-star">
                                <div class="text">내 평가</div>
                                <div class="stars">
                                    <span class="star ${loginScore >= 1 ? 'fixedStar yellow' : ''}">
                                        <label data-value="1">
                                            <input type="radio" name="star" ${loginScore === 1 ? 'checked' : ''} value="1">
                                        </label>
                                    </span>
                                    <span class="star ${loginScore >= 2 ? 'fixedStar yellow' : ''}">
                                        <label data-value="2">
                                            <input type="radio" name="star" ${loginScore === 2 ? 'checked' : ''} value="2">
                                        </label>
                                    </span>
                                    <span class="star ${loginScore >= 3 ? 'fixedStar yellow' : ''}">
                                        <label data-value="3">
                                            <input type="radio" name="star" ${loginScore === 3 ? 'checked' : ''} value="3">
                                        </label>
                                    </span>
                                    <span class="star ${loginScore >= 4 ? 'fixedStar yellow' : ''}">
                                        <label data-value="4">
                                            <input type="radio" name="star" ${loginScore === 4 ? 'checked' : ''} value="4">
                                        </label>
                                    </span>
                                    <span class="star ${loginScore >= 5 ? 'fixedStar yellow' : ''}">
                                        <label data-value="5"><!--★-->
                                            <input type="radio" name="star" ${loginScore === 5 ? 'checked' : ''} value="5">
                                        </label>
                                    </span>
                                </div>
                            </div>
                            <div class="food-name">${data.food.name}</div>
                            <div class="food-cate">양식</div>
                            <div class="food-comment">
                                <form class="review-form" method="post" action="/food/review">
                                    <input type="hidden" name="_method" value="">
                                    <input type="hidden" class="foodId" name="foodId" value="${index}">
                                    <input type="hidden" class="score" name="score" value="${loginScore}">
                                    <textarea name="comment">${loginComment}</textarea>
                                    <input type="submit" class="reviewBt" value="작성">
                                    <input type="button" class="delteBt" value="삭제">
                                </form>
                            </div>`;
        });
    } catch (e) {
        console.log(e);
    }
}

export async function foodReviewTemplate(index,page = 0) {
    try {
        let url = `/food/review/${index}`;

        if (page > 0) {
            url = `/food/review/${index}/${page}`;
        }
        console.log("url : "+url);

        await axios.get(url).then((response) => {
            let review = '';

            let data = '';
            for(let i=0, length=response.data.reviews.length; i<length; i++) {
                data = response.data.reviews[i];
                review += `<div class="food-reply">
                        <div class="reply">
                            <div class="star">${drawReviewStar(data.score)}</div><!--★-->
                            <span class="text">${data.comment}</span>
                            <div class="user_id">-${data.memberId}-</div>
                        </div>
                    </div>`;
            }

            let blockSize = 5;
            let pageSize = response.data.pageSize;
            let totalCnt = response.data.totalCount;
            let start = Math.floor(Math.floor(response.data.currentPage/pageSize)/blockSize);

            foodInfo.querySelector(".food-review-box").innerHTML = review;
            foodInfo.querySelector(".food-review-paging-box").innerHTML = paging(start,totalCnt,blockSize,pageSize);
        })
    } catch (e) {
        console.log(e);
    }
}

function paging(start,totalCnt,size,pageSize) {
    let pagingHtml = "";
    let totalPage = Math.ceil(totalCnt/pageSize);
    let currentBlock = start*size;
    let totalBlock = Math.ceil(totalPage/size);

    if (currentBlock > 0) {
        pagingHtml += `<a href="javascript:void(0);" class="paging prev" data-page="${currentBlock-1}"> < </a>`;
    }
    for (let i=currentBlock; i<currentBlock+size; i++) {
        pagingHtml += `&nbsp;&nbsp;<a href="javascript:void(0);" class="paging" data-page="${i}">-${i+1}-</a>&nbsp;&nbsp;`;
        if (i === totalPage-1) break;
    }
    if (currentBlock+1 < totalBlock) {
        pagingHtml += `<a href="javascript:void(0);" class="paging next" data-page="${currentBlock+size}"> > </a>`;
    }

    return pagingHtml;
}

function drawReviewStar(num) {
    let star = '';

    for(let i=0; i<5; i++) {
        if (i === num) break;
        star += '★';
    }

    return star;
}

function foodInfoClickEvent(e) {
    if (e.target.name === "star") {
        starClickEvent(e);
    } else if (e.target.nodeName.toLowerCase() === "button") {
        // writeReview();
        // alert("리뷰 작성이 완료되었습니다.");
        // reloadFoodScore(foodNum);
        e.preventDefault();
    }
}

function starClickEvent(e) {
    const star = document.querySelectorAll("input[name='star']");
    const starLabel = document.querySelectorAll(".star");
    let index = Array.from(star).findIndex((ele) => ele === e.target);

    fixStar(starLabel, index);
}

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
    if (common.getCookie('loginId') === null) {
        if (confirm('로그인이 필요합니다. 로그인하시겠습니까?')) {
            // 현재 음식 인덱스 번호 지정
            // window.localStorage.setItem("currentFood", `${foodNum}`);
            common.login("/foodList");
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
    document.querySelector(".score").value = num + 1;

    return true;
}

/*
export function formAction() {
    const form = document.querySelector(".review-form");

    if(common.getCookie("loginId") != null) {
        form.submit();
    } else {
        common.login("/foodList/"+form.foodId);
    }
}
*/