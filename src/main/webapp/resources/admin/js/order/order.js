document.addEventListener('DOMContentLoaded', function () {
    const productModal = document.getElementById('product-modal');
    const customerModal = document.getElementById('customer-modal');
    const addCustomerModal = document.getElementById('form-add-customer');
    const modalChoseVoucher = document.getElementById('modalOverlayAddVoucher')

    const chooseProductBtn = document.getElementById('btn-choose-product');
    const chooseCustomerBtn = document.getElementById('btn-choose-customer');

    const closeProductModalBtn = document.getElementById('btn-close-product-modal');
    const closeCustomerModalBtn = document.getElementById('btn-close-customer-modal');

    const customerNameInput = document.getElementById('customer-name');
    const productListBody = document.getElementById('product-list-invoice');
    const totalPaymentElement = document.getElementById('total-payment');
    const createInvoiceBtn = document.getElementById('btn-new-tab');
    const productSearchBtn = document.getElementById('btn-search-product');
    const productTableBody = document.getElementById('product-table');
    const customerPaymentInput = document.getElementById('customer-payment');
    const returnMoneyElement = document.getElementById('return-money');
    const remainingMoneyElement = document.getElementById('remaining-money');
    let tabsContainer = document.querySelector(".tabs");

    let totalPayment = 0;
    let invoiceCounter = 0;
    let selectedInvoiceId = 0;
    let listProduct = [];
    let deletedInvoiceIDs = [];
    let listOrder = []
    let idOrderSelect = null;
    fetchProducts(0, 2)
    fetchCustomers(0)
    fetchLocation()
    fetchFillter()
    // Hiển thị modal chọn sản phẩm
    chooseProductBtn.addEventListener('click', function () {
        if (invoiceCounter < 1) {
            notificationAddCusstomer('Thất bại!', "Hãy tạo hóa đơn trước khi chọn sản phẩm", 'error')
            return
        }
        toggleModal(productModal, true)
    });

    document.getElementById('openModalBtnAddVoucher').addEventListener('click', function(){
        toggleModal(modalChoseVoucher, true)
    })
    // Đóng modal chọn sản phẩm
    closeProductModalBtn.addEventListener('click', () => toggleModal(productModal, false));

    // Hiển thị modal chọn khách hàng
    chooseCustomerBtn.addEventListener('click', function () {
        if (invoiceCounter < 1) {
            notificationAddCusstomer('Thất bại!', "Hãy tạo hóa đơn trước khi chọn khách hàng", 'error')
            return;
        }
        toggleModal(customerModal, true)
    });

    // Đóng modal chọn khách hàng
    closeCustomerModalBtn.addEventListener('click', () => toggleModal(customerModal, false));

    const chooseBtns = document.querySelectorAll(".choose-btn");
    const quantityModal = document.querySelector(".quantity-modal");
    chooseBtns.forEach((btn) => {
        btn.addEventListener("click", () => {
            quantityModal.classList.remove("hidden");
        });
    });

    // Thêm sản phẩm vào danh sách khi bấm nút "Thêm"
    productTableBody.addEventListener('click', function (e) {
        if (e.target.classList.contains('btn-add-product')) {
            toggleModal(productModal, false);
            addProductToInvoice(e.target);
        }
    });

    // Cập nhật tổng tiền khi thay đổi số lượng sản phẩm
    productListBody.addEventListener('input', function (e) {
        if (e.target.classList.contains('product-quantity')) {
            updateTotalPrice();
        }
    });

    // Xóa sản phẩm khỏi danh sách
    productListBody.addEventListener('click', function (e) {
        if (e.target.classList.contains('btn-delete-product')) {
            let idProduct = e.target.closest('tr').getAttribute("data-product-id");
            const index = listProduct.findIndex(product => product.id === idProduct);
            if (index !== -1) {
                listProduct.splice(index, 1);
            }
            e.target.closest('tr').remove()
            updateTotalPrice();
        }
    });

    // Chọn khách hàng từ modal
    document.getElementById('customer-list').addEventListener('click', function (e) {
        if (e.target.classList.contains('btn-add-customer')) {
            $('#infoDetail').empty()
            customerNameInput.innerText = e.target.getAttribute('data-name');
            customerNameInput.setAttribute('data-customer-id', e.target.getAttribute('customer-id'))
            var phoneNumber = e.target.getAttribute('data-sdt');
            var email  = e.target.getAttribute('data-email')
            $('#infoDetail').append('<p>Số điện thoại: ' + phoneNumber + '</p>');
            $('#infoDetail').append('<p>email: ' + email + '</p>');
            $('#btn-delete-customer').prop('disabled', false);
            toggleModal(customerModal, false);
        }
    });
    //Xóa khách hàng
    document.getElementById('btn-delete-customer').addEventListener('click', function(e){
        customerNameInput.innerText = 'Khách lẻ';
        customerNameInput.setAttribute('data-customer-id', '')
        $('#infoDetail').empty()
        $('#btn-delete-customer').prop('disabled', true);
    })

    // Tạo hóa đơn chờ
    createInvoiceBtn.addEventListener('click', () => {
        if(invoiceCounter <= 4){
            let newInvoiceID;
            if (deletedInvoiceIDs.length > 0) {
                // Nếu có hóa đơn đã bị xóa, tái sử dụng ID đã bị xóa
                newInvoiceID = deletedInvoiceIDs.pop();
            }else {
                // Nếu không có ID nào bị xóa, tiếp tục tăng invoiceCounter
                newInvoiceID = invoiceCounter;
            }
            createOrderNewTabOrder(newInvoiceID)
        }
        else {
            Swal.fire({
                icon: "error",
                title: "Hóa đơn đạt giới hạn",
                showConfirmButton: true,
            });
            return
        }
    });
    // Chọn hóa đơn chờ
    tabsContainer.addEventListener('click', function (e) {
        if (e.target.classList.contains('tab')) {
            selectInvoice(e.target);
        }
    });

    // Tìm kiếm sản phẩm theo tên
    productSearchBtn.addEventListener('click', function () {
        fetchProducts(0);
    });

    function fetchFillter(){
        $.ajax({
           url : '/api/admin/order/get/filter',
           type : 'GET',
           success : function(reponse){
               reponse.colors.forEach(item => {
                   $('#search-input-color').append($('<option></option>').val(item.id).text(item.colorName));
               })
               reponse.sizes.forEach(item => {
                   $('#search-input-size').append($('<option></option>').val(item.id).text(item.sizeName));
               })
               reponse.categories.forEach(item => {
                   $('#search-input-category').append($('<option></option>').val(item.id).text(item.categoryName));
               })
           }
        });
    }
    // Tìm kiếm khách hàng theo tên
    document.getElementById('search-btn-customer').addEventListener('click', function () {
        fetchCustomers(0);
    });

    // Cập nhật trả tiền khách hàng
    customerPaymentInput.addEventListener('input', function () {
        calculateCustomerMoney();
    });
    //Lưu đơn hàng
    document.getElementById('btn-order-payment').addEventListener('click', () => saveInvoice(selectedInvoiceId));
    // ----- Helper Functions -----

    // Hiển thị/Ẩn modal
    function toggleModal(modalElement, show) {
        if (show) {
            modalElement.style.display = 'flex';
        } else {
            modalElement.style.display = 'none';
        }
    }


    // Thêm sản phẩm vào danh sách hóa đơn
    function addProductToInvoice(button) {
        let rowCount = productListBody.getElementsByTagName('tr').length;
        let row = button.closest('tr');
        // Lấy dữ liệu từ các ô (td) bằng class
        let productId = button.getAttribute('product-id')
        let name = row.querySelector('.product-name').textContent;
        let color = row.querySelector('.product-color').textContent;
        let size = row.querySelector('.product-size').textContent;
        let quantity = row.querySelector('.product-quantity').textContent;
        let price = row.querySelector('.product-price').getAttribute('data-product-price');
        let imgSrc = $(row).find('.product-image img').attr('src');
        let quantityProductCart = document.querySelector(`#product-list-invoice tr input[product-id="${productId}"]`);
        let totalPriceProductCart = document.querySelector(`span[product-id="${productId}"]`);
        let quantityBy;
        // kiểm tra xem product đã có trong giỏ hàng hay chưa
        if (quantityProductCart != null) {
            if (!checkQuantityProduct(Number(quantity), Number(quantityProductCart?.value))) {
                return
            }
            // cập nhật số lượng tại input
            quantityProductCart.value = parseInt(quantityProductCart.value) + 1;
            // Cập nhật số lượng trong list
            let productToUpdate = listProduct.find(item => item.product?.id === productId)
            console.log(productToUpdate.quantity + ": số lượng sản phẩm cũ")
            if (productToUpdate) {
                productToUpdate.quantity = quantityProductCart.value
                console.log(productToUpdate.quantity + ": số lượng sản phẩm")
                let totalAmount = (Number(quantityProductCart.value)) * Number(price);
                totalPriceProductCart.innerHTML = (totalAmount).toString()
            }
        } else {
            // tạo 1 row mới ở table #product-table-cart
            const newRow = document.createElement("tr");
            newRow.innerHTML = `
            <tr data-product-id="${productId}">
                <td>${rowCount + 1}</td>
                <td>
                    <div class="product-in-table">
                        <img src="${imgSrc}" alt="Sản phẩm">
                        <div class="product-detail">
                            <span>${name}</span><br>
                            <p class="product-detail-amount">Giá tiền: ${(Number(price)).toLocaleString('vi-VN')} VND</p>
                            <small>Màu sắc: ${color}</small>
                            <br>
                            <small>Kích cỡ: ${size}</small>
                        </div>
                    </div>
                </td>
                <td>
                    <div class="quantity-wrapper">
                        <button class="quantity-btn decrease">-</button>
                        <input type="number" value="1" min="1" product-id="${productId}" class="product-quantity-cart" data-price="${price}">
                        <button class="quantity-btn increase">+</button>
                    </div>
                </td>

                <td><span class="product-price" product-id="${productId}">${(Number(quantityBy?.value || 1) * Number(price)).toLocaleString('vi-VN')} </span> VND</td>
                <td>
                    <button class="action-btn delete-btn" id="productListBody">Xóa</button>
                </td>
            </tr>
        `;
            productListBody.appendChild(newRow);
            changeQuantityInput(newRow, productId, price, quantity);
            // update tổng tiền bên tính toán
            let orderDetail = {
                id: null,
                price: price,
                quantity: 1,
                order: {
                    id : idOrderSelect
                },
                productDetail:{
                    id : productId,
                }
            }
            listProduct.push(orderDetail)
            createdOrderDetail(orderDetail, productId)
        }
        updateTotalPrice();
        // saveDataToLocalStorage(selectedInvoiceId)
    }

    function changeQuantityInput(newRow, productId, price, totalQuantityProduct){
        const quantityInput = newRow.querySelector(".product-quantity-cart");
        const totalPrice = newRow.querySelector(".product-price");
        newRow.querySelector(".decrease").addEventListener("click", () => {
            if (quantityInput.value > 1) {
                let productToUpdate = listProduct.find(item => item.id === productId)
                quantityInput.value--;
                productToUpdate.quantity = quantityInput.value
                totalPrice.innerHTML = (Number(quantityInput.value) * Number(price)).toString()
                updateTotalPrice();
            }
        });
        newRow.querySelector(".increase").addEventListener("click", () => {
            let productToUpdate = listProduct.find(item => item.product?.id === productId)
            quantityInput.value++;
            productToUpdate.quantity = quantityInput.value
            totalPrice.innerHTML = (Number(quantityInput.value) * Number(price)).toString()
            updateTotalPrice();
        });
        quantityInput.addEventListener("change", () => {
            if(Number(quantityInput.value) > Number(totalQuantityProduct)){
                quantityInput.value = totalQuantityProduct
                Swal.fire({
                    icon: "error",
                    title: "Số lượng vượt quá giới hạn",
                    showConfirmButton: true,
                    // timer: 1500
                });
            }
            if (quantityInput.value < 1) quantityInput.value = 1;
            let productToUpdate = listProduct.find(item => item.product?.id === productId)
            productToUpdate.quantity = quantityInput.value
            totalPrice.innerHTML = (Number(quantityInput.value) * Number(price)).toString()
            updateTotalPrice();
        });
        //Xóa sản phẩm trong table
        newRow.querySelector('.delete-btn').addEventListener('click', function() {
            console.log(productId + " id sản phẩm")
            const updatedProducts = listProduct.filter(product => product.product?.id !== productId);
            updatedProducts.forEach(item => console.log(item.name +' Tên của sản phẩm'))
            listProduct = updatedProducts
            newRow.remove();
            updateTotalPrice()
        });
    }
    // Tạo hóa đơn mới
    function createInvoice(index, orderID) {
        invoiceCounter++;
        selectedInvoiceId = index
        idOrderSelect = orderID
        const newTab = document.createElement('button');
        newTab.className = 'tab';
        newTab.innerText = `Hóa đơn ${Number(index) + 1}`;
        newTab.setAttribute('data-invoice-id', index);
        newTab.setAttribute('data-order-id', orderID)
        // Thêm tab vào danh sách
        tabsContainer.insertBefore(newTab, document.querySelector('.add-tab'));
        // Tạo nội dung mới cho tab
        activateTab(index);
        resetInvoice();
    }
    // Xóa hóa đơn
    function removeInvoice(index) {
        const tabToRemove = document.querySelector(`.tab[data-invoice-id="${index}"]`); // Tìm tab dựa trên thuộc tính data-invoice-id
        if (tabToRemove) {
            tabToRemove.remove();  // Xóa tab khỏi DOM
            deletedInvoiceIDs.push(index);
            invoiceCounter--
            if(invoiceCounter >= 0){
                selectedInvoiceId =  invoiceCounter
                activateTab(selectedInvoiceId);
            }
        }
    }
    //chuyển tabs
    function activateTab(index) {

        let tab;
        let tabs = document.querySelectorAll('.tab');
        tabs.forEach((t) => {
            t.classList.remove('active')
            if (t.getAttribute('data-invoice-id') == index) {
                tab = t
            }
        })
        tab.classList.add('active');
    }

    // Chọn hóa đơn
    function selectInvoice(button) {
        selectedInvoiceId = button.getAttribute('data-invoice-id');
        idOrderSelect = button.getAttribute('data-order-id');
        activateTab(selectedInvoiceId)
        resetInvoice()
        fetchOrderDetail(idOrderSelect)
    }

    // Cập nhật tổng tiền
    function updateTotalPrice() {
        totalPayment = 0;
        const quantityProductCart = document.querySelectorAll(`.product-quantity-cart`);
        quantityProductCart.forEach(function (input) {
            const quantity = input.value;
            const price = input.getAttribute('data-price');
            totalPayment += quantity * price;
        });
        totalPaymentElement.textContent = totalPayment.toLocaleString() + ' VND';
        $('#total-price-table-invoice').text(totalPayment.toLocaleString() + ' VND')
        $('#form-invoice-total-amount').text(totalPayment.toLocaleString() + ' VND')
        // calculateCustomerMoney();
    }

    // Tính tiền trả lại và còn thiếu
    function calculateCustomerMoney() {
        const customerPayment = parseInt(customerPaymentInput.value) || 0;
        const returnMoney = customerPayment - totalPayment;
        if (returnMoney >= 0) {
            returnMoneyElement.textContent = returnMoney.toLocaleString() + 'VND';
            remainingMoneyElement.textContent = '0 VND';
        } else {
            returnMoneyElement.textContent = '0 VND';
            remainingMoneyElement.textContent = (-returnMoney).toLocaleString() + 'VND';
        }
    }

    // Đặt lại hóa đơn sau khi tạo
    function resetInvoice() {
        listProduct = []
        productListBody.innerHTML = '';
        totalPayment = 0;
        updateTotalPrice();
    }

    function checkQuantityProduct(quantityProduct, quantityAddToCart) {
        if (quantityProduct <= quantityAddToCart) {
            toast({
                title: "Thất bại!",
                message: "Sản phẩm không đủ",
                type: "error",
                duration: 2500
            });
            return false;
        } else if (quantityAddToCart <= 0) {
            return false;
        } else if (quantityAddToCart == null || isNaN(quantityAddToCart) || quantityAddToCart === '') {
            return false;
        }
        return true;
    }

    // Lấy sản phẩm trong list và hiện lên bảng
    function generatedProductFromInvoice(productAtInvoice) {
        if (productAtInvoice != null) {
            for (let i = 0; i < productAtInvoice.length; i++) {
                const newRow = document.createElement("tr");
                newRow.innerHTML = `
            <tr data-product-id="${productAtInvoice[i]?.productDetail.id}">
                <td>${i + 1}</td>
                <td>
                    <div class="product-in-table">
                        <img src="${productAtInvoice[i]?.productDetail.images[0].urlImage}" alt="Sản phẩm">
                        <div class="product-detail">
                            <span>${productAtInvoice[i]?.productDetail.productName}</span><br>
                            <p class="product-detail-amount">Giá tiền: ${(productAtInvoice[i]?.productDetail.price).toLocaleString('vi-VN')} VND</p>
                            <small>Màu sắc: ${productAtInvoice[i]?.productDetail.colorName}</small>
                            <br>
                            <small>Kích cỡ: ${productAtInvoice[i]?.productDetail.sizeName}</small>
                        </div>
                    </div>
                </td>
                <td>
                    <div class="quantity-wrapper">
                        <button class="quantity-btn decrease">-</button>
                        <input type="number" value="${productAtInvoice[i].quantity}" min="1" product-id="${productAtInvoice[i]?.productDetail.id}" class="product-quantity-cart" data-price="${productAtInvoice[i]?.productDetail.price}">
                        <button class="quantity-btn increase">+</button>
                    </div>
                </td>

                <td><span class="product-price" product-id="${productAtInvoice[i]?.productDetail.id}">${(Number(productAtInvoice[i].quantity || 1) * Number(productAtInvoice[i]?.productDetail.price)).toLocaleString('vi-VN')} </span> VND</td>
                <td>
                    <button class="action-btn delete-btn">Xóa</button>
                </td>
            </tr>
        `;
                productListBody.appendChild(newRow);
                // Thêm sự kiện tăng/giảm số lượng cho input
                changeQuantityInput(newRow, productAtInvoice[i]?.productDetail.id, productAtInvoice[i]?.productDetail.price, productAtInvoice[i].quantity)
            }
        }
    }

    //Hiểm thị sản phẩm
    function fetchProducts(page) {
        const inputSearch = document.getElementById('search-input-product').value
        $.ajax({
            url: `http://localhost:8080/api/admin/order/get/products?page=${page}&limit=8`,
            type: 'GET',
            data: {keyword: inputSearch},
            success: function (response) {
                renderProductListFromSearch(response.content);
                renderPagination(response.totalPages, response.number + 1, 'pagination-product');
            },
            error: function (error) {
                console.log("Error:", error);
            }
        });
    }

//   hiểm thị số trang
    function renderPagination(totalPages, currentPage, idTable) {
        const paginationContainer = document.getElementById(idTable);
        paginationContainer.innerHTML = '';
        const createPageItem = (text, isActive = false, isDisabled = false, page) => {
            const pageItem = document.createElement('div');
            pageItem.className = `page-item${isActive ? ' active' : ''}${isDisabled ? ' disabled' : ''}`;
            pageItem.textContent = text;
            if (!isDisabled) {
                pageItem.onclick = () => {
                    if (idTable === 'pagination-product') {
                        fetchProducts(page - 1);
                    } else {
                        fetchCustomers(page - 1)
                    }
                    renderPagination(totalPages, page, idTable);
                };
            }
            return pageItem;
        };
        if (currentPage > 1) {
            paginationContainer.appendChild(createPageItem('Prev', false, false, currentPage - 1));
        } else {
            paginationContainer.appendChild(createPageItem('Prev', false, true));
        }
        const startPage = Math.max(1, currentPage - 1);
        const endPage = Math.min(totalPages, currentPage + 1);
        if (startPage > 1) {
            paginationContainer.appendChild(createPageItem(1, false, false, 1));
            if (startPage > 2) {
                paginationContainer.appendChild(createPageItem('...', false, true));
            }
        }
        for (let i = startPage; i <= endPage; i++) {
            paginationContainer.appendChild(createPageItem(i, i === currentPage, false, i));
        }
        if (endPage < totalPages) {
            if (endPage < totalPages - 1) {
                paginationContainer.appendChild(createPageItem('...', false, true));
            }
            paginationContainer.appendChild(createPageItem(totalPages, false, false, totalPages));
        }
        if (currentPage < totalPages) {
            paginationContainer.appendChild(createPageItem('Next', false, false, currentPage + 1));
        } else {
            paginationContainer.appendChild(createPageItem('Next', false, true));
        }
    }

    //render sản phẩm
    function renderProductListFromSearch(products) {
        const listContainer = document.getElementById('product-table');
        listContainer.innerHTML = '';
        if (products.length > 0) {
            products.forEach((product, index) => {
                $('#product-table').append(`
                        <tr>
                            <td>${index + 1}</td>
                            <td class="product-name" >${product.name}</td>
                            <td class="product-image"><img src="${product.image}" alt="ảnh lỗi" height="100" width="auto"></td>
                            <td class="product-size">${product.sizeName}</td>
                            <td class="product-quantity">${product.quantity}</td>
                            <td class="product-color">${product.colorName}</td>
                            <td class="product-price" data-product-price="${product.price}">${(Number(product.price)).toLocaleString('vi-VN')} VND</td>
                            <td>
                                <button class="btn-add-product" product-id='${product.id}'>Chọn</button>
                            </td>
                        </tr>
                                `);
            });
        } else {
            $('#product-table').append('not found product')
        }
    }

    // lấy list customer
    function fetchCustomers(page) {
        const inputSearch = document.getElementById('search-input-customer').value
        $.ajax({
            url: `/api/admin/user/get/customers?page=${page}&limit=8`,
            type: 'GET',
            data: {keyword: inputSearch},
            success: function (response) {
                renderCustomerListFromSearch(response.content);
                renderPagination(response.totalPages, response.number + 1, 'pagination-customer');
            },
            error: function (error) {
                console.log("Error:", error);
            }
        });
    }

    // render customer từ list
    function renderCustomerListFromSearch(customer) {
        const listContainer = document.getElementById('customer-list');
        listContainer.innerHTML = '';
        if (customer.length > 0) {
            customer.forEach((customer, index) => {
                $('#customer-list').append(`
                        <tr>
                            <td>${index + 1}</td>
                            <td class="product-name" >${customer.fullName}</td>
                            <td class="product-size">${customer.phoneNumber}</td>
                            <td>
                                <button id="btn-add-customer" class="btn-add-customer" data-name='${customer.fullName}' customer-id='${customer.id}' data-sdt='${customer.phoneNumber}' data-email='${customer.email}'>Chọn</button>
                            </td>
                        </tr>
                                `);
            });
        } else {
            $('#product-table').append('not found product')
        }
    }

    function checkBeforeSaving() {
        let check = true
        let customerPayment = document.getElementById('customer-payment')?.value
        let totalPayment = document.getElementById('form-invoice-total-amount')?.innerHTML
        console.log(totalPayment + " tổng tiền phải trả")
        // let InvoiceExists = document.
        if (customerPayment < Number(totalPayment.replace(/VND/g, '').replace(/\./g, ''))) {
            notificationAddCusstomer('Thất bại!', "Số tiền khách trả còn thiếu", 'error')
            check = false
        }
        if (listProduct == null) {
            notificationAddCusstomer('Thất bại!', "Hóa đơn chưa có sản phẩm nào", 'error')
            check = false
        }
        return check
    }

    function saveInvoice(index) {
        if (!checkBeforeSaving()) {
            return
        }
        let invoice = JSON.parse(localStorage.getItem(index))
        let customerID = invoice?.customerID
        let orderNote = invoice?.note
        let totalAmount = invoice?.totalAmount
        let totalProducts = invoice?.listProduct.reduce((total, item) => total + item.quantity, 0);
        let data = {
            id: idOrderSelect,
            status: 1,
            user: customerID ? {id: customerID} : null,
            note: orderNote,
            totalAmount: totalAmount || 0,
            paymentMethod: 1,
            totalProducts: totalProducts
        }
        $.ajax({
            url: `http://localhost:8080/api/admin/order/save/invoice`,
            type: 'POST',
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function (response) {
                if (response.id != null) {
                    saveInvoiceDetail(response.id)
                }
            },
            error: function (xhr, status,error) {
                notificationAddCusstomer('Thất bại!', "Có lỗi khi lưu hóa đơn", 'error')
                console.log("Error:", error);
                console.error('HTTP Status:', xhr.status); // Mã trạng thái HTTP
                console.error('Response Text:', xhr.responseText); // Nội dung phản hồi
                console.error('Error:', error); // Mô tả lỗi
            }
        });
    }

    function saveInvoiceDetail(idInvoice) {
        if (!listProduct || listProduct.length === 0) {
            return;
        }
        listProduct.forEach(item => {
            item.order = {
                id: idInvoice
            };
        });
        $.ajax({
            url: `http://localhost:8080/api/admin/order/save/invoice/details`,
            type: 'POST',
            contentType: "application/json",
            data: JSON.stringify(listProduct),
            success: function (response) {
                updateQuantity()
            },
            error: function (error) {
                Swal.fire({
                    icon: "error",
                    title: "Có lỗi khi lưu hóa đơn chi tiết",
                    showConfirmButton: true,
                    timer: 1500
                });
                console.log("Error:", error);
            }
        });
    }

    function updateQuantity() {
        let promises = [];
        listProduct.forEach(item => {
            let data = {
                productDetail: {
                    id: item.product.id
                },
                quantity: item.quantity
            }
            let promise = $.ajax({
                url: `http://localhost:8080/api/admin/order/update/products`,
                type: 'PUT',
                contentType: "application/json",
                data: JSON.stringify(data),
                success: function (response) {
                    return true;
                },
                error: function (error) {
                    Swal.fire({
                        icon: "error",
                        title: "Có lỗi khi trừ số lượng sản phẩm",
                        showConfirmButton: true,
                        timer: 1500
                    });
                    return;
                    console.log("Error:", error);
                }

            });
            promises.push(promise);
        })
        printInvoice(listProduct);
        customerNameInput.innerText = 'Khách lẻ';
        customerNameInput.setAttribute('data-customer-id', '')
        $('#infoDetail').empty()
        $('#btn-delete-customer').prop('disabled', true);
        fetchProducts(0)
        resetInvoice();
        localStorage.removeItem(selectedInvoiceId);
        removeInvoice(selectedInvoiceId)
    }

//********************* in hóa đơn **************
    function printInvoice(products) {
        let currentDate = new Date();
        let formattedDate = currentDate.toLocaleDateString('vi-VN');
        var nameCustomer = $('#customer-name').text();
        let totalAmout = $('#form-invoice-total-amount').text()
        $('#table_print').empty()
        $('#totalPricePrint').text(totalAmout.toLocaleString());
        products.forEach((product, index) =>{
            $('#table_print').append(`
                        <tr>
                            <tr>
                                <td colspan="4"><strong>${product.name}</strong></td>
                            </tr>
                        <tr>
                            <td>Đơn Giá: ${(product.price).toLocaleString()} VND</td>
                            <td>Số Lượng: ${product.quantity}</td>
                            <td colspan="2">Tổng: ${product.price * product.quantity} VND</td>
                        </tr>
            `);
        })
        printJS({
            printable: 'invoice',       // ID của phần cần in
            type: 'html',               // Loại nội dung là HTML
            targetStyles: ['*'],        // Áp dụng tất cả các style hiện tại
            header: `
    <div style="text-align: center; margin-bottom: 20px;">
      <h3>Hóa Đơn Thanh Toán</h3>
      <p><strong>TN2K STORE</strong></p>
      <p>Địa chỉ: 123 Đường ABC, Nam Từ Liêm, Hà Nội</p>
      <p>Điện thoại: 0999999999</p>
      <hr>
      <p><strong>Khách Hàng:</strong> ${nameCustomer}</p>
      <p><strong>Ngày:</strong> ${formattedDate}</p>
    </div>
  `,                           // Thêm tiêu đề tùy chỉnh
            style: `
    @media print {
      body {
        font-family: Arial, sans-serif;
        font-size: 14px;
        line-height: 1.5;
        margin: 0;
        padding: 0;
      }
      .table {
        width: 100%;
        border-collapse: collapse;
      }
      .table th, .table td {
        border: 1px solid #000;
        padding: 8px;
        text-align: left;
      }
      .total-amount {
        text-align: right;
        margin-right: 20px; /* Khoảng cách với mép phải */
        font-weight: bold;
        font-size: 16px; /* Tăng kích thước để nổi bật */
      }
      .center-content {
        text-align: center;
      }
    }
  `,
            css: 'https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css'
        });

    }

//**************** Thêm mới khách hàng **************
    //Mở thêm khách hàng
    document.getElementById('btn-add-customer').addEventListener('click', function(e){
        toggleModal(addCustomerModal, true);
        toggleModal(customerModal, false)
    })

    //đóng form thêm khách hàng
    document.getElementById('cancel-btn-add-customer').addEventListener('click', ()=> toggleModal(addCustomerModal, false))

    //function cho thông báo
    function notificationAddCusstomer(title, message, type){
        toast({
            title: title,
            message: message,
            type: type,
            duration: 2500
        });
    }

    //fetch địa chỉ cho các ô select
    function fetchLocation(){
        const apiUrl = 'https://provinces.open-api.vn/api/p';
        $.ajax({
            url: apiUrl,
            method: 'GET',
            success: function(data) {
                console.log(data);
                data.forEach(function(value) {
                    $('#area').append(`<option value="${value.code}">${value.name}</option>`);
                });
            },
            error: function(error) {
                console.error('Error fetching provinces data:', error);
            }
        });
    }
    function fetchDistricts(districtsCode) {
        const apiUrlDistricts = `https://provinces.open-api.vn/api/p/${districtsCode}/?depth=2`;
        $.ajax({
            url: apiUrlDistricts,
            method: 'GET',
            success: function(data) {
                console.log(data);
                let districts = data.districts;
                $('#Districts').empty().append('<option value="">Select District</option>');
                districts.forEach(function(value) {
                    $('#Districts').append(`<option value="${value.code}">${value.name}</option>`);
                });
            },
            error: function(error) {
                console.error('Error fetching districts data:', error);
            }
        });
    }
    function fetchWards(wardCode) {
        const apiUrlWards = `https://provinces.open-api.vn/api/d/${wardCode}/?depth=2`;
        $.ajax({
            url: apiUrlWards,
            method: 'GET',
            success: function(data) {
                console.log(data);
                let wards = data.wards;
                $('#Wards').empty().append('<option value="">Select Ward</option>');
                wards.forEach(function(value) {
                    $('#Wards').append(`<option value="${value.code}">${value.name}</option>`);
                });
            },
            error: function(error) {
                console.error('Error fetching wards data:', error);
            }
        });
    }

    $('#area').on('change', function(e) {
        fetchDistricts(e.target.value);
    });

    $('#Districts').on('change', function(e) {
        fetchWards(e.target.value);
    });
    function validateCustomer(name, phoneNumber, email){
        let emailRegex = /^[\w.%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        let phoneRegex = /([\+84|84|0]+(3|5|7|8|9|1[2|6|8|9]))+([0-9]{8})\b/;
        if(!name ||  (!phoneNumber && !email)){
            notificationAddCusstomer('Thất bại!','Tên, số điện thoại hoặc email đang trống!', 'error')
            return false;
        }
        if(email && !emailRegex.test(email)){
            notificationAddCusstomer('Thất bại!', 'email không hợp lệ!', 'error')
            return false;
        }
        if(phoneNumber && !phoneRegex.test(phoneNumber)){
            notificationAddCusstomer('Thất bại!', 'Số điện thoại không hợp lệ!', 'error')
            return false;
        }
        return true
    }

    //add khách hàng
    function addCustomer(){
        let name = $("#fullnameCustomer").val()
        let phoneNumber = $("#phoneNumberAdd").val()
        let email = $("#emailCustomer").val()
        let city = $('#area option:selected').text();
        let district = $('#Districts option:selected').text();
        let ward = $('#Wards option:selected').text();
        let gender = $('#gender').val();
        let dateOfBirth = $('#dob').val();
        const address = [city, district, ward].filter(value => value.trim() !== "").join(", ");
        let addressDetail = $('#addressAddDetail').text();
        if(!validateCustomer(name, phoneNumber, email)){
            return
        }
        let data = {
            email : email,
            fullName : name,
            phoneNumber : phoneNumber,
            gender : gender,
            dateOfBirth : dateOfBirth,
            address:[
                {
                    fullName: name,
                    phoneNumber: phoneNumber,
                    ward: ward,
                    city : city,
                    district : district,
                    address: address,
                    streetDetails:addressDetail,
                    status: true,
                }
            ]
        }
        $.ajax({
            url: `http://localhost:8080/api/admin/user/save/customer`,
            type: 'POST',
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function() {
                toggleModal(addCustomerModal,false)
                notificationAddCusstomer('Thêm thành công', 'success')
                $('#infoDetail').empty()
                customerNameInput.innerText = data.fullName,
                customerNameInput.setAttribute('data-customer-id',data.id)
                var phoneNumber = data.phoneNumber;
                var email  = data.email;
                $('#infoDetail').append('<p>Số điện thoại: ' + phoneNumber + '</p>');
                $('#infoDetail').append('<p>email: ' + email + '</p>');
                $('#btn-delete-customer').prop('disabled', false);
                toggleModal(customerModal, false);
                saveDataToLocalStorage(selectedInvoiceId)
                fetchCustomers(0)
            },
            error: function(xhr, status, error) {
                let errorMap = JSON.parse(xhr.responseText);
                let errorMessages = Object.values(errorMap);
                notificationAddCusstomer('Thất bại!', errorMessages, 'error')
                console.error('Error fetching districts data:', error);
            }
        });
    }
    $('#add-customer').on('click', function(e) {
        addCustomer()
    })

//*****************Đơn Hàng ***************
              //lấy đơn hàng
    fetchOrder()
    function fetchOrder(){
        $.ajax({
            url: `/api/admin/order/get/orders`,
            type: 'GET',
            success: function(data) {
                data.forEach(item => {
                    if(invoiceCounter <= 4){
                        let newInvoiceID;
                        if (deletedInvoiceIDs.length > 0) {
                            // Nếu có hóa đơn đã bị xóa, tái sử dụng ID đã bị xóa
                            newInvoiceID = deletedInvoiceIDs.pop();
                        }else {
                            // Nếu không có ID nào bị xóa, tiếp tục tăng invoiceCounter
                            newInvoiceID = invoiceCounter;
                        }
                        console.log('Id hóa đơn :'+ item.id)
                        createInvoice(newInvoiceID, item.id);
                    }
                    let order = {
                        id : item.id,
                        user : item.user,
                        promotion : item.promotion
                    }
                    listOrder.push(order)
                    // console.log(listOrder)
                })
            },
            error: function(xhr, status, error) {
                let errorMap = JSON.parse(xhr.responseText);
                let errorMessages = Object.values(errorMap);
                notificationAddCusstomer(errorMessages, 'error')
                console.error('Error fetching districts data:', error);
            }
        }).always(function() {
            fetchOrderDetail(idOrderSelect)
        });
    }
//***** lấy đơn hàng chi tiết
    function fetchOrderDetail(orderID){
        $.ajax({
            url: `/api/admin/order/get/orderdetails?id=1`,
            type: 'GET',
            success: function(data) {
                listProduct = []
                    data.forEach(item => {
                        listProduct.push(item)
                    })
                listProduct.forEach(item => console.log(item.id + " số lượng sản phẩm"))
                productListBody.innerHTML = ''
                generatedProductFromInvoice(data)
                updateTotalPrice()
            },
            error: function(xhr, status, error) {
                let errorMap = JSON.parse(xhr.responseText);
                let errorMessages = Object.values(errorMap);
                // notificationAddCusstomer(errorMessages, 'error')
                console.error('Error fetching districts data:', error);
            }
        });
    }

    // sửa hóa đơn
    function updateOrderDetail(data){
        $.ajax({
            url: `/api/admin/order/get/orderdetails?id=1`,
            type: 'PUT',
            contentType: "application/json",
            success: function(data) {

            },
            error: function(xhr, status, error) {
                let errorMap = JSON.parse(xhr.responseText);
                console.error('Error fetching districts data:', error);
            }
        });
    }

    // Tạo hóa đơn mới
    function createOrderNewTabOrder(newInvoiceID){
        let data = {
            orderSource : 0,
            paymentStatus : 'PENDING',
            deliveryStatus : 'COMPLETED',
            orderStatus : 'PENDING_INVOICE',
            totalAmount : 0
        }
        $.ajax({
            url: `http://localhost:8080/api/admin/order/save/invoice`,
            type: 'POST',
            contentType: "application/json",
            data : JSON.stringify(data),
            success: function(respone) {
                createInvoice(newInvoiceID, respone.id);
            },
            error: function(xhr, status, error) {
                let errorMap = JSON.parse(xhr.responseText);
                let errorMessages = Object.values(errorMap);
                notificationAddCusstomer(errorMessages, 'error')
                console.error('Error fetching districts data:', error);
            }
        });
    }
    function createdOrderDetail(data, productId){
        $.ajax({
            url: `/api/admin/order/get/orderdetails?id=1`,
            type: 'POST',
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function(data) {
                let orderDetail = listProduct.find(item => item.productDetail.id === productId);

                if (orderDetail) {
                    orderDetail.id = data.id
                }
            },
            error: function(xhr, status, error) {
                let errorMap = JSON.parse(xhr.responseText);
                console.error('Error fetching districts data:', error);
            }
        });
    }

    $('#xemlisst').on('click', function(){
        console.log(listProduct)
    })
//**************** Giao hàng **************
    $('#flexSwitchCheckDefault').on('change', function(e){
        let modal = document.getElementById('form-customer')
        toggleModal(modal, true)
    })

});
