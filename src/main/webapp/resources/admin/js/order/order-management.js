document.addEventListener('DOMContentLoaded', () => {
    //Đẩy vận chuyển
    const modalOpenOverlayPushTransport = document.getElementById('modalOpenOverlayPushTransport');
    const modalOverlayPushTransport = document.getElementById('modalOverlayPushTransport');

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
});