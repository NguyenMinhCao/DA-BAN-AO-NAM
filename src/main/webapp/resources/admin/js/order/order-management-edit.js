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
    var getProductDetailId = '';

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
                const idProductDetailDiv = productEdit.querySelector('.idProduct')
                if (idProductDetailDiv) {
                    const getProductDeId = idProductDetailDiv.textContent.trim();
                    getProductDetailId = getProductDeId;
                }
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
                const idProductDetailDiv = productEdit.querySelector('.idProduct')
                const idOrderDetailDiv = productEdit.querySelector('.idOrderDetail')
                if (idOrderDetailDiv) {
                    const getId = idOrderDetailDiv.textContent.trim();
                    getIdOrderDetail = getId;
                    console.log(getIdOrderDetail)
                }

                if (idProductDetailDiv) {
                    // Lấy nội dung văn bản của 'idProduct' và loại bỏ khoảng trắng
                    const productDetailId = idProductDetailDiv.textContent.trim();
                    const trElement = productEdit.closest('tr');
                    if (trElement) {
                        // Tìm phần tử có class 'quantity-pro-order' bên trong <tr>
                        const quantityElement = trElement.querySelector('.quantity-pro-order');

                        if (quantityElement) {
                            inputQuantity.value = quantityElement.textContent.trim();
                        }
                    }

                    displayQuanProductDetail(productDetailId)
                    getProductDetailId = productDetailId
                    console.log('ProductDetail ID:', productDetailId);
                }
            }
        })
    })

    //Hiển thị số lượng trong kho
    async function displayQuanProductDetail(getProductDetailId) {
        try {
            const response = await fetch('/admin/orders/product-detail/' + getProductDetailId, {
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
        confirmRemove.onclick = () => handleButtonClick(false);
    }


    const handleButtonClick = async (isUpdate) => {
        var isUp = true;
        if (isUpdate) {
            isUp = true
        } else {
            isUp = false
        }

        const restocking = document.getElementById('isRestockingCheck')
        const quantity = parseInt(inputQuantity.value, 10);
        const totalProduct = parseInt(dataTotalProduct.textContent, 10);
        if (quantity > totalProduct) {
            toast({
                title: "Thất bại!",
                message: "Không thể lớn hơn số sản phẩm trong kho.",
                type: "error",
                duration: 1700
            });
            return;
        }

        const sentData = {
            productDetailId: getProductDetailId,
            quantity: inputQuantity.value,
            updateORemove: isUp,
            restocking: restocking.checked ? 'true' : 'false'
        }

        console.log(sentData)

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
            } else {
                modalOverlayEditOrder.style.display = 'none'
                modalOverlayRemoveProduct.style.display = 'none'
                if (isUp) {
                    toast({
                        title: "Thành công",
                        message: "Cập nhật thành công số lượng.",
                        type: "success",
                        duration: 1700
                    });
                } else {
                    toast({
                        title: "Thành công",
                        message: "Xóa sản phẩm thành công.",
                        type: "success",
                        duration: 1700
                    });
                }

            }

            setTimeout(() => {
                window.location.reload(true);
            }, 1200);
        } catch (error) {
            console.error('Error:', error);
        }
    }

    // const quantityProductDetail =
    // remove product
    var originalQuantity = document.getElementById('originalQuantity')
    var isWarehouse = document.getElementById('warehouse')

    openModalRemove.forEach((modalR) => {
        modalR.addEventListener('click', (e) => {
            modalOverlayRemoveProduct.style.display = 'block'
            let modalRT = modalR.getAttribute('quantity-product-detail')
            originalQuantity.textContent = `Số lượng ban đầu là ${modalRT}`
            isWarehouse.textContent = `Nhập kho ${modalRT} sản phẩm`
        })
    })

    // openModalRemove.forEach((button) => {
    //     button.addEventListener('click', () => {
    //         var productEdit = button.closest('.product-edit');

    //         if (productEdit) {
    //             // Tìm phần tử con có class 'idProduct' trong 'product-edit'
    //             const idProductDetailDiv = productEdit.querySelector('.idProduct')
    //             const idOrderDetailDiv = productEdit.querySelector('.idOrderDetail')
    //             if (idOrderDetailDiv) {
    //                 const getId = idOrderDetailDiv.textContent.trim();
    //                 getIdOrderDetail = getId;
    //                 console.log(getIdOrderDetail)
    //             }

    //             if (idProductDetailDiv) {
    //                 // Lấy nội dung văn bản của 'idProduct' và loại bỏ khoảng trắng
    //                 const productId = idProductDetailDiv.textContent.trim();

    //                 getProductDetailId = productId
    //                 console.log('Product ID:', productId);
    //             }
    //         }
    //     })

    // })

    const inputSearchProduct = document.getElementById('inputSearchProduct');
    const tableSelectProduct = document.querySelector('.table-select-product');
    const modalOverlayConfirmAddProduct = document.getElementById('modalOverlayConfirmAddProduct');
    const confirmAddProductButton = document.getElementById('confirmAddProductButton');
    const cancelAddProductButton = document.getElementById('cancelAddProductButton');

    var idProductDetail = '';
    var sizeProductDetail = '';
    var colorProductDetail = '';
    var nameProductSelect = '';
    var idOrderData = ''

    // Hiển thị bảng khi click vào ô input search product
    inputSearchProduct.addEventListener('click', function (event) {
        tableSelectProduct.style.display = 'block';
        // Ngăn chặn sự kiện lan truyền để tránh kích hoạt sự kiện document click
        event.stopPropagation();
    });

    // Ngăn chặn sự kiện click trên bảng để không ẩn khi click vào bảng
    tableSelectProduct.addEventListener('click', function (event) {
        event.stopPropagation();
    });

    // Ẩn bảng khi click ra ngoài
    document.addEventListener('click', function () {
        tableSelectProduct.style.display = 'none';
    });

    // Lắng nghe sự kiện click vào các sản phẩm để mở modal
    const productDetailSelects = document.querySelectorAll('.product-detail-select');
    productDetailSelects.forEach((productDetail) => {
        productDetail.addEventListener('click', (event) => {
            idProductDetail = productDetail.getAttribute('id-product-detail');
            colorProductDetail = productDetail.getAttribute('color-product-detail')
            sizeProductDetail = productDetail.getAttribute('size-product-detail')
            nameProductSelect = productDetail.getAttribute('name-product-select')
            idOrderData = productDetail.getAttribute('id-order-data')
            var textProductDetail = document.getElementById('textProductDetail')
            textProductDetail.innerHTML = `Xác nhận thêm sản phẩm ${nameProductSelect}<br>Size: ${sizeProductDetail}<br> Màu: ${colorProductDetail}`;
            modalOverlayConfirmAddProduct.style.display = 'block';
            // Ngăn chặn sự kiện lan truyền nếu cần
            event.stopPropagation();
        });
    });

    // Xử lý khi click vào nút xác nhận trong modal
    confirmAddProductButton.addEventListener('click', function (event) {
        const inputQuantityAdd = document.getElementById('inputQuantityAdd')

        async function addProductDetail() {
            const sentData = {
                quantityProduct: inputQuantityAdd.value,
                idOrder: idOrderData,
                idProductDetail: idProductDetail
            }

            if (!inputQuantityAdd.value.trim()) {
                toast({
                    title: "Cảnh báo!",
                    message: "Vui lòng nhập số lượng.",
                    type: "error",
                    duration: 1700
                });
                return;
            }

            try {
                const response = await fetch('/admin/orders/add/product', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json'
                    },
                    body: JSON.stringify(sentData)
                });

                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                } else {
                    tableSelectProduct.style.display = 'none';
                    toast({
                        title: "Thành công",
                        message: "Thêm thành công sản phẩm.",
                        type: "success",
                        duration: 1700
                    });
                }

                setTimeout(() => {
                    window.location.reload(true);
                }, 1200);
            } catch (error) {
                toast({
                    title: "Thêm thất bại",
                    message: error,
                    type: "error",
                    duration: 1700
                });
            }
        }

        addProductDetail();


        modalOverlayConfirmAddProduct.style.display = 'none';

        // Ngăn chặn sự kiện lan truyền để tránh ẩn bảng
        event.stopPropagation();
    });

    // Xử lý khi click vào nút hủy trong modal
    cancelAddProductButton.addEventListener('click', function (event) {
        // Ẩn modal mà không làm gì thêm
        modalOverlayConfirmAddProduct.style.display = 'none';

        // Ngăn chặn sự kiện lan truyền để tránh ẩn bảng
        event.stopPropagation();
    });

    // Ngăn chặn sự kiện click trên modal để không ẩn khi click vào nội dung modal
    modalOverlayConfirmAddProduct.addEventListener('click', function (event) {
        event.stopPropagation();
    });

    // Lắng nghe sự kiện input vào ô search product
    inputSearchProduct.addEventListener('input', function (event) {
        const query = event.target.value.trim();
        if (query.length > 0) {
            // Gọi hàm tìm kiếm sản phẩm với query
            searchProducts(query);
        } else {
            location.reload();
            tableSelectProduct.style.display = 'none';
        }
    });

    // Hàm tìm kiếm sản phẩm (ví dụ sử dụng Fetch API)
    function searchProducts(query) {
        // Gửi yêu cầu đến backend Spring để tìm kiếm sản phẩm
        fetch(`/admin/product/search?filter=name~~'*${encodeURIComponent(query)}*'`)
            .then(response => response.json())
            .then(data => {
                // Xử lý dữ liệu trả về và cập nhật bảng sản phẩm
                populateProductTable(data);
                tableSelectProduct.style.display = 'block';
                console.log(data)
            })
            .catch(error => {
                console.error('Error:', error);
                // Hiển thị thông báo lỗi hoặc xử lý lỗi tại đây
            });
    }

    // Hàm cập nhật bảng sản phẩm với dữ liệu trả về
    function populateProductTable(products) {
        const tableContent = tableSelectProduct.querySelector('.display-product-detail-search');
        tableContent.innerHTML = ''; // Xóa nội dung cũ

        if (products.length === 0) {
            tableContent.innerHTML = '<p style="padding:10px; margin:0px">Không tìm thấy sản phẩm nào.</p>';
            return;
        }

        console.log(products)

        products.forEach(product => {
            const productDiv = document.createElement('div');
            productDiv.classList.add('product-detail-select');
            productDiv.setAttribute('id-product-detail', product.id);
            productDiv.setAttribute('name-product-select', product.name);
            productDiv.setAttribute('size-product-detail', `${product.sizeName}`);
            productDiv.setAttribute('color-product-detail', `${product.colorName}`);
            productDiv.innerHTML = `
                <div class="product-img">
                        <img alt=""
                            src="/images/product/${product.urlImage}">
                        </div>
                                                                        <div
                                                                            class="d-flex flex-grow-1 justify-content-between">
                                                                            <div class="name-product-detail">
                                                                                <div>
                                                                                    <span>${product.name}</span>
                                                                                </div>
                                                                                <div><span>${product.sizeName},
                                                                                        ${product.colorName}</span>
                                                                                </div>
                                                                            </div>
                                                                            <div class="price-product-detail">
                                                                                <div style="text-align: end;">
                                                                                    <span>
                                                                                         ${formatter.format(product.price)}
                                                                                    </span>
                                                                                </div>
                                                                                <div style="margin-top: 4px;"><span>Có
                                                                                        thể
                                                                                        bán:
                                                                                        ${product.quantity}</span>
                                                                                </div>
                                                                            </div>
                                                                        </div>
            `;

            // Thêm sự kiện click vào sản phẩm để mở modal
            productDiv.addEventListener('click', (event) => {
                const nameProductSelect = productDiv.getAttribute('name-product-select');
                const sizeProduct = productDiv.getAttribute('size-product-detail')
                const colorProduct = productDiv.getAttribute('size-product-detail')

                // Cập nhật nội dung modal
                var textProductDetail = document.getElementById('textProductDetail')
                textProductDetail.innerHTML = `Xác nhận thêm sản phẩm ${nameProductSelect}<br>Size: ${sizeProduct}<br> Màu: ${colorProduct}`;

                // Hiển thị modal
                const modalOverlayConfirmAddProduct = document.getElementById('modalOverlayConfirmAddProduct');
                modalOverlayConfirmAddProduct.style.display = 'block';

                event.stopPropagation();
            });

            tableContent.appendChild(productDiv);
        });
    }

    const formatter = new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND',
        minimumFractionDigits: 0,
    });
})