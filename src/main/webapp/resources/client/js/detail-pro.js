const selectColorBoxs = document.querySelectorAll('.box-select-variant .select-box-colors');
const selectSizeBoxs = document.querySelectorAll('.select-box-sizes')
var idSizeSelect = ''
var idColorSelect = ''
var proDDetailSize = ''
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



        console.log('id color : ', idColorSelect)
        console.log('id product detail : ', proDDetailColor)

    });
})


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
        proDDetailSize = selectbox.getAttribute('idProductDetailSize')

        console.log('id size : ', idSizeSelect)
        console.log('id product detail : ', proDDetailSize)

        
    });
})