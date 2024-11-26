document.addEventListener('DOMContentLoaded', () => {
    // Function submitLogoutForm
    const logoutForm = document.getElementById('logoutForm');
    if (logoutForm) {
        logoutForm.addEventListener('submit', () => {
            logoutForm.submit();
        });
    }

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

    // Modal handling
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
});
