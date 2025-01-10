document.addEventListener('DOMContentLoaded', () => {
    // Toast function


    //Đẩy vận chuyển
    const modalOpenOverlayPushTransport = document.getElementById('modalOpenOverlayPushTransport');
    const modalOverlayPushTransport = document.getElementById('modalOverlayPushTransport');
    const openModalEditInformation = document.getElementById('modalEditInformation')
    const modalOverlayEditInformation = document.getElementById('modalOverlayEditInformation')
    const modalOverlayEditNote = document.getElementById('modalOverlayEditNote')
    const btnConfirmUpdateUser = document.getElementById('confirmUpdateUser')
    const selectBox = document.querySelector('.select-box')
    const modalOpenEditNote = document.getElementById('modalOpenEditNote')
    var shippingMethodSelect = document.getElementById('shippingMethodSelect');
    const textReasonReturn = document.getElementById('textReasonReturn')

    if (modalOpenOverlayPushTransport) {
        modalOpenOverlayPushTransport.addEventListener('click', () => {
            modalOverlayPushTransport.style.display = 'block';
        })
    }

    // Nhận tiền
    const modalOpenOverlayReceiveMoney = document.getElementById('modalOpenOverlayReceiveMoney');
    const modalOverlayReceiveMoney = document.getElementById('modalOverlayReceiveMoney');

    if (modalOpenOverlayReceiveMoney) {
        modalOpenOverlayReceiveMoney.addEventListener('click', () => {
            modalOverlayReceiveMoney.style.display = 'block';
        })
    }

    //Sủa thông tin khách hàng
    openModalEditInformation.onclick = () => modalOverlayEditInformation.style.display = 'block'

    //Sủa thông tin ghi chú
    modalOpenEditNote.onclick = () => modalOverlayEditNote.style.display = 'block'

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

    const changeStatusOrder = document.querySelectorAll('.change-status-order');

    //cập nhật xác nhận giao hàng chuyển trạng thái giao hàng
    changeStatusOrder.forEach(change => {
        var pathname = window.location.pathname;
        var parts = pathname.split('/');
        var orderId = parts[parts.length - 1];
        change.addEventListener('click', async (e) => {
            var contentBodyStatus = null;
            var contentBodyField = null;
            if (change.id === 'cancelDeliveryBtn') {
                contentBodyStatus = 'PENDING';
                contentBodyField = 'DELIVERY';
            }
            else if (change.id === 'confirmPushShipping') {
                contentBodyStatus = 'DELIVERY';
                contentBodyField = 'DELIVERY';
            }
            else if (change.id === 'confirmDelivery') {
                contentBodyStatus = 'COMPLETED';
                contentBodyField = 'DELIVERY';
            }
            else if (change.id === 'confirmReceiveMoney') {
                contentBodyStatus = 'PENDING';
                contentBodyField = 'PAYMENT';
            } else {
                contentBodyStatus = null;
                contentBodyField = null;
            }

            const dataStatus = {
                status: contentBodyStatus,
                fieldUpdate: contentBodyField
            };

            try {
                const response = await fetch('/admin/orders/' + orderId, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json'
                    },
                    body: JSON.stringify(dataStatus)
                });

                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }

                const data = await response.json();
                console.log("Response Data:", data);
                window.location.reload(true);
            } catch (error) {
                console.error('Error:', error);
            }
        })
    })

    if (btnConfirmUpdateUser) {
        btnConfirmUpdateUser.onclick = async () => {

            var emailUserInput = document.getElementById('emailUserInput')
            var oldEmailUser = emailUserInput.getAttribute('email-old-user')
            var phoneUserInput = document.getElementById('phoneNumberInput')
            const valEmailInput = emailUserInput.value;
            const phoneInput = phoneUserInput.value;
            const sentData = {
                emailUser: valEmailInput,
                phoneNumber: phoneInput,
                oldEmailUser: oldEmailUser
            }

            try {
                const response = await fetch('/admin/user/update-order', {
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
    }

    //trả hàng
    const btnReturnProduct = document.querySelectorAll('.product-order .text-return');
    const modalOverlayReturn = document.getElementById('modalOverlayReturn')
    const confirmReturnProduct = document.getElementById('confirmReturnProduct')
    const quantityInput = document.getElementById('quantityReturn');
    var quantityProductCurrent = ''
    var productDetailID = ''
    var orderDetailId = ''


    var orderId = textReasonReturn.getAttribute('id-order-current')

    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    btnReturnProduct.forEach((btn) => {
        btn.addEventListener('click', () => {
            modalOverlayReturn.style.display = 'block';
            const trElement = btn.closest('tr');
            quantityProductCurrent = trElement.getAttribute('quantity-productD-current')
            productDetailID = trElement.getAttribute('id-product-detail')
            orderDetailId = trElement.getAttribute('order-detail-id')
            console.log(orderDetailId)
        })
    })

    confirmReturnProduct.onclick = () => {
        let isValid = true;
        var isReturn = true;

        // Kiểm tra Số lượng
        const quantityValue = quantityInput.value.trim();
        if (quantityValue === '') {
            toast({
                title: "Thất bại!",
                message: 'Vui lòng nhập số lượng.',
                type: "error",
                duration: 1700
            });
            isValid = false;
        } else if (!/^\d+$/.test(quantityValue) || parseInt(quantityValue) <= 0) {
            toast({
                title: "Thất bại!",
                message: 'Số lượng phải là số nguyên dương.',
                type: "error",
                duration: 1700
            });
            isValid = false;
        } else {
            isValid = true;
        }

        // Kiểm tra Lý do trả hàng
        const reasonValue = textReasonReturn.value.trim();
        if (reasonValue === '') {
            toast({
                title: "Thất bại!",
                message: 'Vui lòng nhập lý do trả hàng.',
                type: "error",
                duration: 1700
            });
            isValid = false;
        } else {
            isValid = true;
        }

        const textReasonReturnn = document.getElementById('textReasonReturn')
        const quantityInputP = quantityInput.value.trim();
        const textReasonReturnData = textReasonReturnn.value.trim();
        // kiểm tra tồn kho
        // if (isValid) {
        //     (async () => {
        //         try {
        //             const sentData = {
        //                 quantityValue: quantityInputP,
        //                 orderId: orderId,
        //                 productDetailID: productDetailID
        //             }
        //             const response = await fetch('/admin/orders/check-quantity-product', {
        //                 method: 'POST',
        //                 headers: {
        //                     'Content-Type': 'application/json',
        //                     'Accept': 'application/json'
        //                 },
        //                 body: JSON.stringify(sentData)
        //             });

        //             if (!response.ok) {
        //                 throw new Error('Network response was not ok: ' + response.statusText);
        //             }

        //             const data = await response.json();

        //             if (data === false) {
        //                 toast({
        //                     title: "Thất bại!",
        //                     message: 'Không đủ sản phẩm trong kho.',
        //                     type: "error",
        //                     duration: 1700
        //                 });
        //             } else {

        //             }

        //             // window.location.reload(true);
        //         } catch (error) {
        //             console.error('Error:', error);
        //         }
        //     })(); // Gọi hàm ngay tại đây
        // }
        if (isValid) {
            console.log('quantityProductCurrent <<<<' + quantityProductCurrent)
            console.log('quantityInputP <<<<<' + quantityInputP)

            if (quantityProductCurrent >= quantityInputP) {
                returnProduct()

            } else {
                toast({
                    title: "Thất bại!",
                    message: 'Chỉ có thể hoàn tối đa ' + quantityProductCurrent + ' sản phẩm .',
                    type: "error",
                    duration: 1700
                });
            }
        }

        async function returnProduct() {
            const sentData = {
                quantityValue: quantityInputP,
                orderId: orderId,
                productDetailID: productDetailID,
                description: textReasonReturnData,
                orderDetailId: orderDetailId
            }

            try {
                const response = await fetch('/admin/orders/return-product', {
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
                const data = await response.json();
                let updateSuccess = true;

                if (data === true) {
                    modalOverlayReturn.style.display = 'none'
                    toast({
                        title: "Thành công!",
                        message: "Bạn đã trả hàng thành công",
                        type: "success",
                        duration: 1700
                    });

                    setTimeout(() => {
                        window.location.reload(true);
                    }, 1000);
                } else {

                    toast({
                        title: "Thất bại!",
                        message: "Không đủ sản phẩm trong kho.",
                        type: "error",
                        duration: 1000
                    });
                    updateSuccess = false;
                }


                if (updateSuccess) {
                    console.log('Trả hàng thành công.');
                }
            } catch (error) {
                console.error('Error:', error);
            }
        }
    }

});