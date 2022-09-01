import * as common from "./common.js";
import {removeAllActive} from "./common.js";

const foodList = document.querySelector('.food-list');
const foodInfo = document.querySelector(".food-info");

foodList.addEventListener("click", foodClickEvent);
foodList.addEventListener("mousemove", foodMousemoveEvent);

// 음식 선택 이벤트
function foodClickEvent(e) {
    if (e.target.classList.contains("food-name") && e.type === "click") {
        const index = e.target.dataset.num;

        // foodInfo.innerHTML = json.foodTemplate(foodData[index]);
        // removeActive.call(document.querySelectorAll(".food-name"));
        // drawMyStar(index);
        // myComment(index);
        // reloadFoodScore(index);
        // displayShow('food-info');
        // scrollClass('food-info');
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