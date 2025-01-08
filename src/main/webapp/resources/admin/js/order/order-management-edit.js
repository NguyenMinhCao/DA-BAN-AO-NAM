document.addEventListener('DOMContentLoaded', () => {
    const openModalEdit = document.querySelectorAll('.openModalEdit')
    const modalOverlayEditOrder = document.getElementById('modalOverlayEditOrder')
    const openModalRemove = document.querySelectorAll('.openModalRemove')
    const modalOverlayRemoveProduct = document.getElementById('modalOverlayRemoveProduct')
    const dataTotalProduct = document.getElementById('data-total-product')
    const inputQuantity = document.getElementById('inputQuantity')
    const btnConfirmUpdate = document.getElementById('confirmEdit')
    const confirmRemove = document.getElementById('confirmRemove')
    var getIdOrderDetail = '';
    var getProductId = '';

    openModalEdit.forEach((modalEdit) => {
        modalEdit.onclick = () => {
            modalOverlayEditOrder.style.display = 'block'
        }
    })

    //close modal
    const getAllCloseModal = document.querySelectorAll('.btn-close-modal');
    const getAllModal = document.querySelectorAll('.modal-overlay');

    getAllCloseModal.forEach((closeModal) => {
        closeModal.addEventListener('click', () => {
            getAllModal.forEach((modalBox => {
                modalBox.style.display = 'none';
            }))
        })
    });

    openModalRemove.forEach((button) => {
        button.addEventListener('click', () => {
            var productEdit = button.closest('.product-edit');

            if (productEdit) {
                // Tìm phần tử con có class 'idProduct' trong 'product-edit'
                const idOrderDetailDiv = productEdit.querySelector('.idOrderDetail')
                if (idOrderDetailDiv) {
                    const getId = idOrderDetailDiv.textContent.trim();
                    getIdOrderDetail = getId;
                    console.log(getIdOrderDetail)
                }
            }
        })
    })

    openModalEdit.forEach((button) => {
        button.addEventListener('click', () => {
            var productEdit = button.closest('.product-edit');

            if (productEdit) {
                // Tìm phần tử con có class 'idProduct' trong 'product-edit'
                const idProductDiv = productEdit.querySelector('.idProduct')
                const idOrderDetailDiv = productEdit.querySelector('.idOrderDetail')
                if (idOrderDetailDiv) {
                    const getId = idOrderDetailDiv.textContent.trim();
                    getIdOrderDetail = getId;
                    console.log(getIdOrderDetail)
                }

                if (idProductDiv) {
                    // Lấy nội dung văn bản của 'idProduct' và loại bỏ khoảng trắng
                    const productId = idProductDiv.textContent.trim();
                    const trElement = productEdit.closest('tr');
                    if (trElement) {
                        // Tìm phần tử có class 'quantity-pro-order' bên trong <tr>
                        const quantityElement = trElement.querySelector('.quantity-pro-order');

                        if (quantityElement) {
                            inputQuantity.value = quantityElement.textContent.trim();
                        }
                    }

                    displayQuanProduct(productId)
                    getProductId = productId
                    console.log('Product ID:', productId);
                }
            }
        })
    })

    //Hiển thị số lượng trong kho
    async function displayQuanProduct(idProduct) {
        try {
            const response = await fetch('/admin/orders/product/' + idProduct, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                }
            });

            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }

            const data = await response.json();
            console.log("Response Data:", data);
            dataTotalProduct.textContent = data
            // if (inputQuantity) {
            //     inputQuantity.value = currentQuantity;
            // }
        } catch (error) {
            console.error('Error:', error);
        }
    }

    //update số lượng
    if (btnConfirmUpdate) {
        btnConfirmUpdate.onclick = () => handleButtonClick(true);
    }

    //xóa sản phẩm
    if (confirmRemove) {
        confirmRemove.onclick = () => {

        }
        confirmRemove.onclick = () => handleButtonClick(false);
    }


    const handleButtonClick = async (isUpdate) => {
        var isUp = true;
        if (isUpdate) {
            isUp = true
        } else {
            isUp = false
        }
        const sentData = {
            productId: getProductId,
            quantity: inputQuantity.value,
            updateORemove: isUp
        }

        try {
            const response = await fetch('/admin/orders/' + getIdOrderDetail + '/edit', {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                body: JSON.stringify(sentData)
            });

            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }

            window.location.reload(true);
        } catch (error) {
            console.error('Error:', error);
        }
    }


    // remove product
    openModalRemove.forEach((modalR) => {
        modalR.onclick = () => {
            modalOverlayRemoveProduct.style.display = 'block'
        }
    })

    // openModalRemove.forEach((button) => {
    //     button.addEventListener('click', () => {
    //         var productEdit = button.closest('.product-edit');

    //         if (productEdit) {
    //             // Tìm phần tử con có class 'idProduct' trong 'product-edit'
    //             const idProductDiv = productEdit.querySelector('.idProduct')
    //             const idOrderDetailDiv = productEdit.querySelector('.idOrderDetail')
    //             if (idOrderDetailDiv) {
    //                 const getId = idOrderDetailDiv.textContent.trim();
    //                 getIdOrderDetail = getId;
    //                 console.log(getIdOrderDetail)
    //             }

    //             if (idProductDiv) {
    //                 // Lấy nội dung văn bản của 'idProduct' và loại bỏ khoảng trắng
    //                 const productId = idProductDiv.textContent.trim();

    //                 getProductId = productId
    //                 console.log('Product ID:', productId);
    //             }
    //         }
    //     })

    // })


})