import * as common from "./common.js";

const foodList = document.querySelector('.food-list');
const foodInfo = document.querySelector(".food-info");

foodList?.addEventListener("click", foodClickEvent);
foodList?.addEventListener("mousemove", foodMousemoveEvent);

// 음식 선택 이벤트
function foodClickEvent(e) {
    if (e.target.classList.contains("food-name") && e.type === "click") {
        const index = e.target.dataset.num;

        console.log(e.target.dataset);

        foodTemplate(e).then(r => console.log(r));
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

async function foodTemplate(e) {
    try {
        foodInfo.querySelector("img").src=e.target.dataset.infoImg;
        foodInfo.querySelector(".avr").innerText=e.target.dataset.infoAvr;
        foodInfo.querySelector(".food-cate").innerText=e.target.dataset.infoCate;
        foodInfo.classList.remove("none");

        /*
        let url = `/food/info/${e.target.dataset.infoIndex}`;
        await axios.get(url).then((response)=> console.log(response));
         */
    } catch (e) {
        console.log(e);
    }
}