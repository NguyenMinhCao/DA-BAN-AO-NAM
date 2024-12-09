document.addEventListener('DOMContentLoaded', () => {
    // Function submitLogoutForm
    const logoutForm = document.getElementById('logoutForm');
    if (logoutForm) {
        logoutForm.addEventListener('submit', (event) => {
            event.preventDefault(); // Ngăn chặn form gửi lại mặc định
            logoutForm.submit(); // Gửi form
        });
    }

    // Định nghĩa hàm submitLogoutForm
    window.submitLogoutForm = function () {
        const logoutForm = document.getElementById('logoutForm');
        if (logoutForm) {
            logoutForm.submit(); // Gửi form
        }
    };

    // File input handling
    const fileInput = document.getElementById('file-input');
    if (fileInput) {
        fileInput.addEventListener('change', function (event) {
            const file = event.target.files[0];
            if (file) {
                if (file.size > 1024 * 1024) {
                    alert("Dung lượng tệp vượt quá 1MB. Vui lòng chọn tệp nhỏ hơn.");
                    return;
                }
                const reader = new FileReader();
                reader.onload = function (e) {
                    const imageLabel = document.querySelector('.cW0oBM');
                    if (imageLabel) {
                        imageLabel.style.backgroundImage = `url(${e.target.result})`;
                    }
                };
                reader.readAsDataURL(file);
            }
        });

        // Kích hoạt input file ẩn khi nhấn nút "choose-file-btn"
        const chooseFileBtn = document.getElementById('choose-file-btn');
        if (chooseFileBtn) {
            chooseFileBtn.addEventListener('click', function () {
                fileInput.click(); // Kích hoạt input file ẩn
            });
        }
    }

    // Modal handling voucher
    const modalOverlayAddVoucher = document.getElementById('modalOverlayAddVoucher');
    const openModalBtnAddVoucher = document.getElementById('openModalBtnAddVoucher');
    const closeModalBtnAddVouCher = document.getElementById('closeModalBtnAddVouCher');

    if (openModalBtnAddVoucher) {
        openModalBtnAddVoucher.addEventListener('click', () => {
            modalOverlayAddVoucher.style.display = 'block';
        });
    }

    if (closeModalBtnAddVouCher) {
        closeModalBtnAddVouCher.addEventListener('click', () => {
            modalOverlayAddVoucher.style.display = 'none';
        });
    }

    if (modalOverlayAddVoucher) {
        modalOverlayAddVoucher.addEventListener('click', (e) => {
            if (e.target === modalOverlayAddVoucher) {
                modalOverlayAddVoucher.style.display = 'none';
            }
        });
    }

    // Modal handling address
    const modalOverlayAddress = document.getElementById('modalOverlayAddress');
    const openModalBtnAddress = document.getElementById('openModalBtnAddress');
    const closeModalBtnAddress = document.getElementById('closeModalBtnAddress');
    const closeModalBtnAddressXn = document.getElementById('closeModalBtnAddressXn');


    if (openModalBtnAddress) {
        openModalBtnAddress.addEventListener('click', () => {
            modalOverlayAddress.style.display = 'block';
        });
    }

    if (closeModalBtnAddress) {
        closeModalBtnAddress.addEventListener('click', () => {
            modalOverlayAddress.style.display = 'none';
        });
    }

    if (closeModalBtnAddressXn) {
        closeModalBtnAddressXn.addEventListener('click', () => {
            modalOverlayAddress.style.display = 'none';
        });
    }

    if (modalOverlayAddress) {
        modalOverlayAddress.addEventListener('click', (e) => {
            if (e.target === modalOverlayAddress) {
                modalOverlayAddress.style.display = 'none';
            }
        });
    }

    // Modal handling add address
    const modalOverlayAddAddress = document.getElementById('modalOverlayAddAddress');
    const openModalBtnAddAddress = document.getElementById('openModalBtnAddAddress');
    const closeModalBtnAddAddress = document.getElementById('closeModalBtnAddAddress');
    const closeModalBtnAddAddressXn = document.getElementById('closeModalBtnAddAddressXn');


    if (openModalBtnAddress) {
        openModalBtnAddAddress.addEventListener('click', () => {
            modalOverlayAddAddress.style.display = 'block';
        });
    }

    if (closeModalBtnAddAddress) {
        closeModalBtnAddAddress.addEventListener('click', () => {
            modalOverlayAddAddress.style.display = 'none';
        });
    }

    if (closeModalBtnAddAddressXn) {
        closeModalBtnAddAddressXn.addEventListener('click', () => {
            modalOverlayAddAddress.style.display = 'none';
        });
    }

    if (modalOverlayAddAddress) {
        modalOverlayAddAddress.addEventListener('click', (e) => {
            if (e.target === modalOverlayAddAddress) {
                modalOverlayAddAddress.style.display = 'none';
            }
        });
    }

    // Modal handling add address
    const modalOverlayUpdateAddress = document.getElementById('modalOverlayUpdateAddress');
    const openModalBtnUpdateAddress = document.getElementById('openModalBtnUpdateAddress');
    const closeModalBtnUpdateAddress = document.getElementById('closeModalBtnUpdateAddress');
    const closeModalBtnUpdateAddressXn = document.getElementById('closeModalBtnUpdateAddressXn');


    document.querySelectorAll('.openModalBtnUpdateAddress').forEach(button => {
        button.addEventListener('click', () => {
            modalOverlayUpdateAddress.style.display = 'block';
        });
    });


    if (closeModalBtnUpdateAddress) {
        closeModalBtnUpdateAddress.addEventListener('click', () => {
            modalOverlayUpdateAddress.style.display = 'none';
        });
    }

    if (closeModalBtnUpdateAddressXn) {
        closeModalBtnUpdateAddressXn.addEventListener('click', () => {
            modalOverlayUpdateAddress.style.display = 'none';
        });
    }

    if (modalOverlayUpdateAddress) {
        modalOverlayUpdateAddress.addEventListener('click', (e) => {
            if (e.target === modalOverlayUpdateAddress) {
                modalOverlayUpdateAddress.style.display = 'none';
            }
        });
    }


});
