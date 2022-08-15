export function showFood(json,category = 0) {
    return json
        .filter(data => {
            if(category === 0) {
                return true
            } else {
                return data.code === category;
            }
        })
        .map(data => `<div class="food"">
            <div class="food-img">
                <img src="${data.img}" alt="${data.name}">
            </div>
            <div class="food-name" data-num="${data.num}">
                ${data.name}
            </div>
        </div>`)
        .join('');
}

function starCheckBox(num) {
    let template = ``;
    let checked = ['','','','',''];
    let myRating = window.localStorage.getItem("foodRating") || null;
    myRating = JSON.parse(myRating);

    if (myRating !== null && myRating.length >0) {
        myRating = myRating.filter(data=>parseInt(data.num) === parseInt(num));

        if (myRating.length > 0) {
            checked[myRating[0].star] = `checked`;
        }
    }

    for (let i=1; i<6; i++) {
        template += `
            <span class="star">
                <label data-value="${i}">★
                    <input type="radio" name="star" value="${i}" ${checked[i-1]}>
                </label>
            </span>`;
    }
    return template;
}

function drawStar(num) {
    // ★★★★★
    let star = "★";
    let returnValue = "";
    for (let i=0; i<num; i++) {
        returnValue += star;
    }
    return returnValue;
}

function replyStr(comments) {
    return comments.map(comment => `
    <div class="reply">
        <div class="star">${drawStar(comment.star)}</div>
        <span class="text">${comment.comment}</span>
        <div class="user_id">-${comment.id}-</div>
    </div>`).join('');
}

function foodCodeStr(code) {
    const data = {
        1:'한식',
        2:'양식',
        3:'중식',
        4:'일식'
    }

    return data[code];
}

function foodEvr(comments, foodNum = 0) {
    let avr = comments.reduce((total,next) => total + next.star,0) / comments.length;
    let myRating = window.localStorage.getItem("foodRating") || null;
    myRating = JSON.parse(myRating);

    if (myRating !== null && myRating.length > 0) {
        myRating = myRating.filter(data => parseInt(data.num) === foodNum);
        avr = comments.reduce((total,next) => total + next.star,(parseInt(myRating[0].star)+1)) / (comments.length + myRating.length);
    }
    return avr.toFixed(1);
}

export function foodTemplate(data) {
    return `<div class="food-img">
            <img src="${data.img}" alt="${data.name}">
        </div>
        <div class="food-avr">평점 <span class="avr">${foodEvr(data.comment,data.num)}</span></div>
        <div class="rating-star">
            <div class="text">내 평가</div>
            ${starCheckBox(data.num)}
        </div>
        <div class="food-name" data-num="${data.num}">${data.name}</div>
        <div class="food-cate">${foodCodeStr(data.code)}</div>
        <div class="food-comment">
            <textarea></textarea>
            <button>작성</button>
        </div>
        <div class="food-reply">${replyStr(data.comment)}</div>`;
}