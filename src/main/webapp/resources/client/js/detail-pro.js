const selectColorBoxs = document.querySelectorAll('.box-select-variant .select-box-colors');
const selectSizeBoxs = document.querySelectorAll('.select-box-sizes')
const btnAddProductToCart = document.getElementById('btnAddProductToCart')
var idSizeSelect = ''
var idColorSelect = ''
var proDDetailSelect = ''
var idSizeSelectCurrent = ''
var idColorSelectCurrent = ''
var productSelectCurrent = ''
var proDDetailColor = ''


//chọn color
selectColorBoxs.forEach((selectbox) => {
    selectbox.addEventListener('click', () => {
        // Đặt lại các thay đổi cho tất cả các selectBox
        selectColorBoxs.forEach(box => {
            box.classList.remove('enabled')
        });

        // Chỉ thay đổi selectBox được chọn
        selectbox.classList.add('enabled')



        idColorInColor = selectbox.getAttribute('idColorInColor')
        proDDetailColor = selectbox.getAttribute('idProductdetailcolor')
        idProductColor = selectbox.getAttribute('idProductColor')


        idColorSelectCurrent = idColorInColor;
        productSelectCurrent = idProductColor

        console.log('id color : ', idColorInColor)
        console.log('id product : ', idProductColor)
        findProductDetailByColorAndProduct(idColorInColor, idProductColor)
    });
})

//hiển thị các size tồn tại
async function findProductDetailByColorAndProduct(idColor, idProduct) {
    try {
        const dataSent = {
            idColor: idColor,
            idProduct: idProduct
        }
        const response = await fetch('/product/product-detail-by-color', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify(dataSent)
        });

        if (!response.ok) {
            throw new Error('Network response was not ok ' + response.statusText);
        }

        const lstIdSize = await response.json();
        console.log(lstIdSize)
        selectSizeBoxs.forEach((selectbox) => {
            const idSizeSelect = Number(selectbox.getAttribute('idSizeInSize')); // Chuyển thành số
            console.log('idSizeSelect', idSizeSelect);

            if (!lstIdSize.includes(idSizeSelect)) {
                // Nếu không nằm trong danh sách, thêm lớp 'disabled'
                selectbox.classList.add('disabled');
            } else {
                // Nếu nằm trong danh sách, đảm bảo loại bỏ lớp 'disabled' nếu có
                selectbox.classList.remove('disabled');
            }
        });

    } catch (error) {
        console.error('Error:', error);
    }
}


//chọn size
selectSizeBoxs.forEach((selectbox) => {
    selectbox.addEventListener('click', () => {
        // Đặt lại các thay đổi cho tất cả các selectBox
        selectSizeBoxs.forEach(box => {
            box.classList.remove('enabled')
        });

        // Chỉ thay đổi selectBox được chọn
        selectbox.classList.add('enabled')


        idSizeSelect = selectbox.getAttribute('idSizeInSize')
        idProductSize = selectbox.getAttribute('idProductSize')

        productSelectCurrent = idProductSize;
        idSizeSelectCurrent = idSizeSelect;

        console.log('id size : ', idSizeSelect)
        console.log('id product : ', idProductSize)
        findProductDetailBySizeAndProduct(idSizeSelect, idProductSize)
    });
})

//hiển thị các color tồn tại
async function findProductDetailBySizeAndProduct(idSize, idProduct) {
    try {
        const dataSent = {
            idSize: idSize,
            idProduct: idProduct
        }
        const response = await fetch('/product/product-detail-by-size', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify(dataSent)
        });

        if (!response.ok) {
            throw new Error('Network response was not ok ' + response.statusText);
        }

        const lstIdColor = await response.json();
        console.log(lstIdColor)
        selectColorBoxs.forEach((selectbox) => {
            const idColorSelect = Number(selectbox.getAttribute('idColorInColor')); // Chuyển thành số
            console.log('idsidSizeSelect', idSizeSelect);

            if (!lstIdColor.includes(idColorSelect)) {
                // Nếu không nằm trong danh sách, thêm lớp 'disabled'
                selectbox.classList.add('disabled');
            } else {
                // Nếu nằm trong danh sách, đảm bảo loại bỏ lớp 'disabled' nếu có
                selectbox.classList.remove('disabled');
            }
        });

    } catch (error) {
        console.error('Error:', error);
    }
}


if (btnAddProductToCart) {
    btnAddProductToCart.onclick = async function () {
        const dataSent = {
            idSize: idSizeSelectCurrent,
            idProduct: productSelectCurrent,
            idColor: idColorSelectCurrent
        }
        try {
            const response = await fetch('/add-product-to-cart', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                body: JSON.stringify(dataSent)
            });

            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }

            const data = await response.json();
            console.log("Response Data:", data);
            // if (inputQuantity) {
            //     inputQuantity.value = currentQuantity;
            // }
        } catch (error) {
            console.error('Error:', error);
        }
    }
}