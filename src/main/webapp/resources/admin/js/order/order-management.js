document.addEventListener('DOMContentLoaded', () => {
    const modalOpenOverlayReceiveMoney = document.getElementById('modalOpenOverlayReceiveMoney');
    const modalOverlayReceiveMoney = document.getElementById('modalOverlayReceiveMoney');
    const closeModalOverlayReceiveMoney = document.querySelectorAll('.modal-overlay-receive-money-content .btn-close-receive-money');
    const confirmPushShipping = document.getElementById('confirmPushShipping');
    const cancelDeliveryBtn = document.getElementById('cancelDeliveryBtn');

    if (modalOpenOverlayReceiveMoney) {
        modalOpenOverlayReceiveMoney.addEventListener('click', () => {
            modalOverlayReceiveMoney.style.display = 'block';
        })
    }

    closeModalOverlayReceiveMoney.forEach((closeModal) => {
        closeModal.addEventListener('click', () => {
            modalOverlayReceiveMoney.style.display = 'none';
        })
    });


    //cập nhật xác nhận giao hàng chuyển trạng thái DELIVERY
    if (confirmPushShipping) {
        var pathname = window.location.pathname;
        var parts = pathname.split('/');
        var orderId = parts[parts.length - 1];

        confirmPushShipping.onclick = async function sendData() {
            try {
                const response = await fetch('/admin/orders/' + orderId, {
                    method: 'PUT',
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
                window.location.reload(true);
            } catch (error) {
                console.error('Error:', error);
            }
        }
    }

    //cập nhật xác nhận giao hàng chuyển trạng thái PENDING
    if (cancelDeliveryBtn) {
        var pathname = window.location.pathname;
        var parts = pathname.split('/');
        var orderId = parts[parts.length - 1];

        cancelDeliveryBtn.onclick = async function sendData() {
            try {
                const response = await fetch('/admin/orders/' + orderId, {
                    method: 'PUT',
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
                window.location.reload(true);
            } catch (error) {
                console.error('Error:', error);
            }
        }
    }
});