document.addEventListener('DOMContentLoaded', () => {
    // Toast function
    //Đẩy vận chuyển
    const modalOpenOverlayPushTransport = document.getElementById('modalOpenOverlayPushTransport');
    const modalOverlayPushTransport = document.getElementById('modalOverlayPushTransport');
    const openModalEditInformation = document.getElementById('modalEditInformation')
    const modalOverlayEditInformation = document.getElementById('modalOverlayEditInformation')
    const modalOverlayEditNote = document.getElementById('modalOverlayEditNote')
    // const btnConfirmUpdateUser = document.getElementById('confirmUpdateUser')
    const selectBox = document.querySelector('.select-box')
    const modalOpenEditNote = document.getElementById('modalOpenEditNote')
    const modalOverlayEditAddress = document.getElementById('modalOverlayEditAddress')
    const btnOpenModalEditAddress = document.getElementById('btnOpenModalEditAddress')
    var shippingMethodSelect = document.getElementById('shippingMethodSelect');
    const textReasonReturn = document.getElementById('textReasonReturn')
    const textNoteUpdate = document.getElementById('inputNoteUser')

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

    if (openModalEditInformation) {
        openModalEditInformation.addEventListener('click', () => {
            modalOverlayEditInformation.style.display = 'block'
        })
    }
    //Sủa thông tin khách hàng
    // openModalEditInformation.onclick = () => modalOverlayEditInformation.style.display = 'block'


    //Sủa thông tin ghi chú
    // modalOpenEditNote.onclick = () => modalOverlayEditNote.style.display = 'block'
    if (modalOpenEditNote) {
        const textNoteDb = document.getElementById('text-note-db')
        modalOpenEditNote.addEventListener('click', () => {
            modalOverlayEditNote.style.display = 'block'
            textNoteUpdate.value = textNoteDb.textContent
        })
    }

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

    // cập nhật thông tin liên hệ
    // if (btnConfirmUpdateUser) {
    //     btnConfirmUpdateUser.onclick = async () => {

    //         const emailPattern = /^[a-zA-Z0-9._%+-]+@gmail\.com$/;
    //         const phonePattern = /^0\d{9}$/;

    //         var emailUserInput = document.getElementById('emailUserInput')
    //         var oldEmailUser = emailUserInput.getAttribute('email-old-user')
    //         var phoneUserInput = document.getElementById('phoneNumberInput')
    //         var confirmUpdateUserDB = document.getElementById('confirmUpdateUserDB')

    //         const valEmailInput = emailUserInput.value.trim();
    //         const phoneInput = phoneUserInput.value.trim();

    //         // Kiểm tra Email
    //         if (!emailPattern.test(valEmailInput)) {
    //             toast({
    //                 title: "Thất bại!",
    //                 message: 'Vui lòng nhập email hợp lệ kết thúc bằng @gmail.com!',
    //                 type: "error",
    //                 duration: 1700
    //             });
    //             return
    //         }

    //         // Kiểm tra Số Điện Thoại
    //         if (!phonePattern.test(phoneInput)) {
    //             toast({
    //                 title: "Thất bại!",
    //                 message: 'Vui lòng nhập số điện thoại hợp lệ (10 số, bắt đầu bằng 0).!',
    //                 type: "error",
    //                 duration: 1700
    //             });
    //             return
    //         }


    //         const sentData = {
    //             emailUser: valEmailInput,
    //             phoneNumber: phoneInput,
    //             oldEmailUser: oldEmailUser,
    //             updateUserDb: confirmUpdateUserDB.checked ? 'true' : 'false'
    //         }

    //         try {
    //             const response = await fetch('/admin/user/update-order', {
    //                 method: 'PUT',
    //                 headers: {
    //                     'Content-Type': 'application/json',
    //                     'Accept': 'application/json'
    //                 },
    //                 body: JSON.stringify(sentData)
    //             });

    //             if (!response.ok) {
    //                 throw new Error('Network response was not ok ' + response.statusText);
    //             } else {
    //                 toast({
    //                     title: "Thành công!",
    //                     message: 'Cập nhật thành công',
    //                     type: "success",
    //                     duration: 1200
    //                 });
    //                 modalOverlayEditInformation.style.display = 'none'

    //                 setTimeout(() => {
    //                     window.location.reload(true);
    //                 }, 1200);
    //             }

    //         } catch (error) {
    //             console.error('Error:', error);
    //         }
    //     }
    // }

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

        const productRestocking = document.getElementById('productRestocking')
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
                orderDetailId: orderDetailId,
                restocking: productRestocking.checked ? 'true' : 'false'
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

    // cập nhật note 
    const confirmUpdateNote = document.getElementById('confirmUpdateNote');
    const idOrderNote = confirmUpdateNote.getAttribute('id-order-update-note')

    if (confirmUpdateNote) {
        confirmUpdateNote.onclick = () => {
            const textNoteUpdateValue = textNoteUpdate.value.trim();
            if (textNoteUpdateValue === '') {
                toast({
                    title: "Thất bại!",
                    message: 'Vui lòng nhập ghi chú.',
                    type: "error",
                    duration: 1700
                });

            } else {
                updateNoteUser()
            }
        }
    }

    async function updateNoteUser() {
        try {
            const response = await fetch('/admin/orders/' + idOrderNote + '/note/edit', {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                body: textNoteUpdate.value
            });

            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            const data = await response.json();
            if (data === true) {
                modalOverlayEditNote.style.display = 'none'
                toast({
                    title: "Thành công",
                    message: "Cạp nhật ghi chú thành công.",
                    type: "success",
                    duration: 1000
                });
                setTimeout(() => {
                    window.location.reload(true);
                }, 1000);
            } else {
                toast({
                    title: "Thất bại!",
                    message: "Cập nhật thất bại.",
                    type: "error",
                    duration: 1000
                });
            }
            console.log("Response Data:", data);
            // window.location.reload(true);
        } catch (error) {
            console.error('Error:', error);
        }
    }


    // cập nhật address
    const emailUserOrder = document.getElementById('emailUserOrder')

    if (btnOpenModalEditAddress) {
        btnOpenModalEditAddress.addEventListener('click', () => {
            modalOverlayEditAddress.style.display = 'block'
            // const email = emailUserOrder.textContent.trim()
            // const emailData = {
            //     email: email
            // }
            // async function fetchAddressByUser() {
            //     try {
            //         const response = await fetch('/admin/user/find-by-email', {
            //             method: 'POST',
            //             headers: {
            //                 'Content-Type': 'application/json',
            //                 'Accept': 'application/json'
            //             },
            //             body: JSON.stringify(emailData)
            //         });
            //         console.log(response)
            //         if (!response.ok) {
            //             toast({
            //                 title: "Thất bại!",
            //                 message: "Người dùng không có địa chỉ hoặc có lỗi!",
            //                 type: "error",
            //                 duration: 1700
            //             });
            //         } else {
            //             const data = await response.json();
            //             console.log("Response Data:", data);
            //             populateSelect(data)
            //         }

            //     } catch (error) {
            //         console.error('Error:', error);
            //     }
            // }
            // fetchAddressByUser();
        })
    }

    // Hàm để thêm các option vào thẻ select
    function populateSelect(addresses) {
        const select = document.getElementById("userAddressSelect");

        // Xóa các option hiện tại
        select.innerHTML = '';

        // Thêm option mặc định
        const defaultOption = document.createElement("option");
        defaultOption.value = "";
        defaultOption.textContent = "Chọn địa chỉ";
        select.appendChild(defaultOption);

        if (addresses && addresses.length > 0) {
            addresses.forEach(address => {
                const option = document.createElement("option");
                option.value = address.id; // Sử dụng ID của địa chỉ

                // Tạo nội dung hiển thị cho option
                // Ví dụ: Họ tên - Số điện thoại - Địa chỉ
                option.textContent = `${address.fullName} - ${address.phoneNumber} - ${address.streetDetails}, ${address.ward}, ${address.district}, ${address.city}`;

                // Nếu bạn muốn lưu thêm dữ liệu khác vào option, có thể sử dụng data attributes
                // option.setAttribute('data-fullName', address.fullName);
                // option.setAttribute('data-phoneNumber', address.phoneNumber);
                // option.setAttribute('data-ward', address.ward);
                // option.setAttribute('data-district', address.district);
                // option.setAttribute('data-city', address.city);
                // option.setAttribute('data-streetDetails', address.streetDetails);

                select.appendChild(option);
            });
        } else {
            // Nếu không có địa chỉ nào, thêm một option thông báo
            const option = document.createElement("option");
            option.value = "";
            option.textContent = "Không có địa chỉ nào";
            select.appendChild(option);
        }
    }

    const confirmUpdateAddress = document.getElementById('confirmUpdateAddress');

    const addressDetail = document.getElementById('addressDetail')
    const inputAddressWard = document.getElementById('inputAddressWard')
    const inputDistrictAddress = document.getElementById('inputDistrictAddress')
    const inputPhoneNumberAddress = document.getElementById('inputPhoneNumberAddress')
    const inputCityAddress = document.getElementById('inputCityAddress')
    const inputFullNameAddress = document.getElementById('inputFullNameAddress')
    const idOrderUpdateAddress = inputPhoneNumberAddress.getAttribute('id-order')

    function isValidPhoneNumber(phone) {
        const phoneRegex = /^[0-9]{10}$/; // Điều chỉnh regex theo yêu cầu
        return phoneRegex.test(phone);
    }

    // Kiểm tra và thêm event listener cho nút xác nhận cập nhật địa chỉ
    if (confirmUpdateAddress) {
        confirmUpdateAddress.addEventListener('click', () => {
            // Hiển thị modal hoặc overlay nếu cần
            modalOverlayEditAddress.style.display = 'block';

            // Thu thập dữ liệu từ các trường nhập liệu
            const fullName = inputFullNameAddress.value.trim();
            const phoneNumber = inputPhoneNumberAddress.value.trim();
            const ward = inputAddressWard.value.trim();
            const district = inputDistrictAddress.value.trim();
            const city = inputCityAddress.value.trim();
            const streetDetails = addressDetail.value.trim();

            // Kiểm tra và xác thực dữ liệu
            if (!fullName) {
                toast({
                    title: "Cảnh báo!",
                    message: "Vui lòng nhập Họ tên.",
                    type: "error",
                    duration: 2000
                });
                return;
            }

            if (!phoneNumber) {
                toast({
                    title: "Cảnh báo!",
                    message: "Vui lòng nhập Số điện thoại.",
                    type: "error",
                    duration: 2000
                });
                return;
            }

            if (!isValidPhoneNumber(phoneNumber)) {
                toast({
                    title: "Cảnh báo!",
                    message: "Số điện thoại không hợp lệ. Vui lòng nhập lại.",
                    type: "error",
                    duration: 2000
                });
                return;
            }

            if (!ward) {
                toast({
                    title: "Cảnh báo!",
                    message: "Vui lòng nhập Phường/Xã.",
                    type: "error",
                    duration: 2000
                });
                return;
            }

            if (!district) {
                toast({
                    title: "Cảnh báo!",
                    message: "Vui lòng nhập Quận/Huyện.",
                    type: "error",
                    duration: 2000
                });
                return;
            }

            if (!city) {
                toast({
                    title: "Cảnh báo!",
                    message: "Vui lòng nhập Thành phố.",
                    type: "error",
                    duration: 2000
                });
                return;
            }

            if (!streetDetails) {
                toast({
                    title: "Cảnh báo!",
                    message: "Vui lòng nhập Chi tiết đường.",
                    type: "error",
                    duration: 2000
                });
                return;
            }

            // Tạo đối tượng dữ liệu để gửi đến server
            const addressData = {
                fullName: fullName,
                phoneNumber: phoneNumber,
                ward: ward,
                district: district,
                city: city,
                streetDetails: streetDetails,
                idOrder: idOrderUpdateAddress
            };

            console.log("Dữ liệu gửi lên server:", addressData);

            // Gửi dữ liệu đến server bằng fetch
            async function sendAddressToServer() {
                try {
                    const response = await fetch('/admin/order/update-address', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                            'Accept': 'application/json'
                        },
                        body: JSON.stringify(addressData)
                    });

                    if (!response.ok) {
                        // Nếu server trả về lỗi
                        const errorData = await response.json();
                        toast({
                            title: "Thất bại!",
                            message: errorData.message || "Cập nhật địa chỉ không thành công!",
                            type: "error",
                            duration: 2000
                        });
                        console.error('Lỗi từ server:', errorData);
                    } else {
                        // Nếu server trả về thành công
                        const successData = await response.json();
                        toast({
                            title: "Thành công!",
                            message: "Cập nhật địa chỉ thành công.",
                            type: "success",
                            duration: 1200
                        });
                        console.log("Dữ liệu phản hồi từ server:", successData);



                        // Đóng modal hoặc reset form nếu cần
                        modalOverlayEditAddress.style.display = 'none';
                        setTimeout(() => {
                            window.location.reload(true);
                        }, 1000);
                    }

                } catch (error) {
                    // Xử lý lỗi mạng hoặc lỗi khác
                    toast({
                        title: "Lỗi!",
                        message: "Đã xảy ra lỗi khi gửi dữ liệu đến server.",
                        type: "error",
                        duration: 2000
                    });
                    console.error('Error:', error);
                }
            }

            // Gọi hàm gửi dữ liệu
            sendAddressToServer();
        })
    };

    // userAddressSelect.addEventListener('change', function () {
    //     // Lấy giá trị của option được chọn
    //     const selectedValue = this.value;

    //     // Lấy nội dung (text) của option được chọn
    //     const selectedText = this.options[this.selectedIndex].text;

    //     console.log('Giá trị được chọn:', selectedValue);
    //     console.log('Nội dung được chọn:', selectedText);

    //     // Bạn có thể thực hiện các thao tác khác với giá trị này, ví dụ: gửi dữ liệu lên server
    // });


});