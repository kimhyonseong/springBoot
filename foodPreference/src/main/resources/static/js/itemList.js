$(function () {
    let liForm = `<li>
                <a href="/item/ITEM_ID">
                    <dl>
                        <dt>
                            <img src="ITEM_IMG">
                        </dt>
                        <dd>
                            <div>
                                <div class="item-name">ITEM_NAME</div>
                                <div class="item-price">ITEM_PRICE</div>
                                <div class="item-other">ITEM_DESC</div>
                            </div>
                        </dd>
                    </dl>
                </a>
            </li>`;

    let param = window.location.search.match(/page=[0-9]+/gi);
    showItem(param);

    function showItem(page) {
        let requestUrl = "/itemList/all";

        if (page != null) {
            page = parseInt(param[0].match(/[0-9]+/)[0]);
        }

        if (page > 0) {
            requestUrl += `?page=${page}`;
        }

        $.ajax({
            url: requestUrl,
        }).done(function (json) {
            console.log(json);
            let html = "";
            let pageHtml = "";
            let defaultImg = "https://via.placeholder.com/150";

            for (let i = 0; i < json.itemList.length; i++) {
                let item = json.itemList[i].item;
                let itemImg = json.itemList[i].itemImg ?
                    json.itemList[i].itemImg.imgPath + json.itemList[i].itemImg.fileName : defaultImg;

                html += liForm.replace("ITEM_ID", item.idx)
                    .replace("ITEM_NAME", item.name)
                    .replace("ITEM_PRICE", item.price)
                    .replace("ITEM_DESC", item.description)
                    .replace("ITEM_IMG", itemImg);
            }
            if (json.page != null) {
                if (json.page.prev)
                    pageHtml += `<a href="${json.page.link}?page=${json.page.start - json.page.blockSize}">`;

                pageHtml += `<ul>`;
                for (let i = json.page.start; i <= json.page.end; i++)
                    pageHtml += `<li><a class="${i - 1 === json.page.currentPage ? 'on' : ''}" 
                                    href="${json.page.link}?page=${i - 1}">${i}</a></li>`;
                pageHtml += `</ul>`;

                if (json.page.next)
                    pageHtml += `<a href="${json.page.link}?page=${json.page.next + 1}">`;
            }

            $(".itemList").html(html);
            $(".page-box").html(pageHtml);
        })
    }
})