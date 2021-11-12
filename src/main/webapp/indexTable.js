function validate() {
    let rsl = true
    let atr = $('.form-control')
    for (let node of atr) {
        if (node.value === '' || node.value === null) {
            alert(node.getAttribute('title'))
            rsl = false
            break
        }
    }
    return rsl
}

function validatePhoto() {
    let rsl = true
    let atr = $('.downloadedPhoto')
    for (let node of atr) {
        if (node.files[0] === undefined) {
            alert(node.getAttribute('title'))
            rsl = false
            break
        }
    }
    return rsl
}

function getItems(userName) {
    let allItems = false
    if (document.querySelector("input[name=select]:checked")) {
        allItems = true
    }
    $.ajax({
        type: 'GET',
        crossDomain : true,
        url: 'http://localhost:8080/job4j_cars/index.do?allItems=' + allItems,
        dataType: 'text',
    }).done(function(data) {
        let dt = JSON.parse(data)
        console.log(data)
        for (const [key, value] of Object.entries(dt)) {
            if (key === 'items') {
                let tbody = $('tbody').text("")
                for (let i = 0; i < value.length; i++) {
                    let tr = $('<tr>')
                    let numb = $('<td>', {
                        text: `${value[i].id}`
                    })
                    if (`${value[i].author}` === userName) {
                        numb.append($('<a>', {
                            href: "edit.jsp?itemId=" + `${value[i].id}`
                        }).append($('<i>', {
                            class: "fa fa-edit mr-3"
                        })))
                    }
                    let photo = $('<td>', {
                        style: "text-align: center"
                    })
                    if (value[i].photo != null) {
                        photo.append($('<img>', {
                            src: "download.do?name=" + `${value[i].id}` + ".JPG",
                            width: "140px",
                            height: "90px"
                        }))
                    }
                    let mark = $('<td>',{
                        text: `${value[i].mark}`
                    })
                    let model = $('<td>', {
                        text: `${value[i].model}`
                    })
                    let bodyType = $('<td>', {
                        text: `${value[i].bodyType}`
                    })
                    let description = $('<td>', {
                        text: `${value[i].description}`
                    })
                    let created = $('<td>', {
                        text: `${value[i].created}`
                    })
                    let author = $('<td>', {
                        text: `${value[i].author}`
                    })
                    let isActive = $('<td>')
                    if (`${value[i].active}` === "true") {
                        isActive.text("Активно")
                    } else {
                        isActive.text("Завершено")
                    }
                    tr.append(numb)
                    tr.append(photo)
                    tr.append(mark)
                    tr.append(model)
                    tr.append(bodyType)
                    tr.append(description)
                    tr.append(created)
                    tr.append(author)
                    tr.append(isActive)
                    tbody.append(tr)
                 }
            }
        }
    });
}

function getItemById(itemId) {
    $.ajax({
        type: 'GET',
        crossDomain : true,
        url: 'http://localhost:8080/job4j_cars/index.do?itemId=' + itemId,
        dataType: 'text',
    }).done(function(data) {
        let dt = JSON.parse(data)
        for (const [key, value] of Object.entries(dt)) {
            if (key === 'items') {
                $('input[name="mark"]').val(`${value[0].mark}`)
                $('input[name="model"]').val(`${value[0].model}`)
                $('input[name="bodyType"]').val(`${value[0].bodyType}`)
                $('input[name="description"]').val(`${value[0].description}`)
                $('input[name="created"]').val(`${value[0].created}`)
                $('input[name="active"]').val(`${value[0].active}`)

                if (`${value[0].active}` === "true") {
                    $('input[name="isActive"]').val("false")
                    $('#actBtn').text("Снять с публикации")
                } else {
                    $('input[name="isActive"]').val("true")
                    $('#actBtn').text("Опубликовать")
                }
                let photo = $('#photo')
                if ("photo" in value[0]) {
                    $('input[name="photo"]').val(`${value[0].photo}`)
                    photo.append($('<input>', {
                        type: "hidden",
                        name: "photo"
                    }))
                    photo.append($('<form>', {
                        action: "item.do?action=deletePhoto&itemId=" +`${value[0].id}`,
                        method: "post"
                    }).append($('<button>', {
                        type: "submit",
                        class: "btn btn-primary",
                        text: "Delete Photo"
                    })))
                    photo.append($('<img>', {
                        src: "download.do?name=" + `${value[0].id}` + ".JPG",
                        class: "img-thumbnail",
                        width: "140px",
                        height: "90px"
                    }))
                } else {
                    photo.append($('<a>', {
                        text: "Загрузить фото",
                        href: "upload.jsp?itemId=" + `${value[0].id}`
                    }))
                }
            }
        }
    });
}
