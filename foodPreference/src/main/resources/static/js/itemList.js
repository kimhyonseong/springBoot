$(function () {
    let liForm = `<li>
                <a href="/item/ITEM_ID">
                    <dl>
                        <dt>
                            <img src="https://via.placeholder.com/150">
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

    $.ajax({
        url:"/itemList/all",
    }).done(function (json) {
        console.log(json);
        let html = "";
        for (let i=0; i<json.length; i++) {
            html += liForm.replace("ITEM_ID",json[i].idx)
                .replace("ITEM_NAME",json[i].name)
                .replace("ITEM_PRICE",json[i].price)
                .replace("ITEM_DESC",json[i].description);
        }
        $(".itemList").html(html);
    })
})