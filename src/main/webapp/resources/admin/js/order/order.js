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
    let selectedInvoiceId = null;
    let listProduct = [];
    let listOrder = [];
    let idOrderSelect = null;
    let listCoupons = [];
    let idCoupon = null;
    let tabs = [];

// Fetch dữ liệu
    fetchProducts(0, 2);
    fetchCustomers(0);
    fetchLocation();
    fetchFillter();
    fetchOrder()

// Tạo hóa đơn chờ
    createInvoiceBtn.addEventListener("click", () => {
        createOrderNewTabOrder(invoiceCounter);
    });

// Chọn tab hóa đơn
    tabsContainer.addEventListener("click", function (e) {
        if (e.target.classList.contains("tab")) {
            selectInvoice(e.target);
        }
    });

// Tạo hóa đơn mới
    function createInvoice(index, orderID) {
        if (tabs.length >= 5) {
            notificationAddCusstomer(
                "Cảnh báo",
                "Hóa đơn đã đạt giới hạn",
                "error"
            );
            return;
        }

        // Thêm tab mới vào danh sách
        tabs.push(index);
        invoiceCounter++;
        selectedInvoiceId = index;
        idOrderSelect = orderID;

        // Tạo tab mới
        const newTab = document.createElement("button");
        newTab.className = "tab";
        newTab.innerText = `Hóa đơn ${index + 1}`;
        newTab.setAttribute("data-invoice-id", index);
        newTab.setAttribute("data-order-id", orderID);

        // Thêm vào DOM
        tabsContainer.insertBefore(newTab, document.querySelector(".add-tab"));
        activateTab(index);
        resetInvoice();
        resetCoupon();
    }

// Xóa hóa đơn
    function removeInvoice(index) {
        const tabToRemove = document.querySelector(
            `.tab[data-invoice-id="${index}"]`
        );
        if (!tabToRemove) return;

        // Xóa tab khỏi danh sách và DOM
        tabs = tabs.filter((tab) => tab !== index);
        tabToRemove.remove();

        // Cập nhật tab hiện tại
        if (tabs.length === 0) {
            selectedInvoiceId = null;
        } else if (index === Math.max(...tabs)) {
            selectedInvoiceId = Math.max(...tabs); // Tab lớn nhất còn lại
        } else {
            selectedInvoiceId = Math.min(...tabs); // Tab nhỏ nhất còn lại
        }
        activateTab(selectedInvoiceId);
    }

// Chuyển tab
    function activateTab(index) {
        const allTabs = document.querySelectorAll(".tab");
        allTabs.forEach((tab) => {
            tab.classList.remove("active");
            if (tab.getAttribute("data-invoice-id") == index) {
                tab.classList.add("active");
                idOrderSelect = tab.getAttribute("data-order-id");
            }
        });
    }

// Chọn tab hóa đơn
    function selectInvoice(button) {
        selectedInvoiceId = parseInt(button.getAttribute("data-invoice-id"), 10);
        idOrderSelect = button.getAttribute("data-order-id");
        activateTab(selectedInvoiceId);
        resetInvoice();
        resetCoupon();
        let orderSelect = listOrder.find((item) => item.id == idOrderSelect);
        if (orderSelect && orderSelect.user && orderSelect.user.id) {
            console.log(orderSelect.user + " user selce")
            customerNameInput.innerText = orderSelect.user.fullName;
            customerNameInput.setAttribute("data-customer-id", orderSelect.user.id);
            $("#infoDetail").empty();
            $("#infoDetail").append(
                `<p>Số điện thoại: ${orderSelect.user.phoneNumber}</p>`
            );
            $("#infoDetail").append(`<p>Email: ${orderSelect.user.email}</p>`);
            $("#btn-delete-customer").prop("disabled", false);
        } else {
            customerNameInput.innerText = "Khách lẻ";
            customerNameInput.setAttribute("data-customer-id", "");
            $("#infoDetail").empty();
        }
        fetchOrderDetail(idOrderSelect);
    }

// Fetch đơn hàng
    function fetchOrder() {
        $.ajax({
            url: `/api/admin/order/get/orders`,
            type: "GET",
            success: function (data) {
                data.forEach((item) => {
                    if (invoiceCounter < 5) {
                        createInvoice(invoiceCounter, item.id);
                    }
                    listOrder.push(item);
                });
                resetCoupon()
            },
            error: function (xhr) {
                let errorMap = JSON.parse(xhr.responseText);
                let errorMessages = Object.values(errorMap);
                notificationAddCusstomer(errorMessages, "error");
            },
        }).always(function () {
            fetchOrderDetail(idOrderSelect);
            let orderSelect = listOrder.find((item) => item.id == idOrderSelect);
            if (orderSelect && orderSelect.user) {
                customerNameInput.innerText = orderSelect.user.fullName;
                customerNameInput.setAttribute("data-customer-id", orderSelect.user.id);
                $("#infoDetail").empty();
                $("#infoDetail").append(
                    `<p>Số điện thoại: ${orderSelect.user.phoneNumber}</p>`
                );
                $("#infoDetail").append(`<p>Email: ${orderSelect.user.email}</p>`);
                $("#btn-delete-customer").prop("disabled", false);
            }
        });
    }

// Tạo hóa đơn mới
    function createOrderNewTabOrder(newInvoiceID) {
        let data = {
            orderSource: 0,
            paymentStatus: "PENDING",
            deliveryStatus: "COMPLETED",
            orderStatus: "PENDING_INVOICE",
            totalAmount: 0,
        };
        $.ajax({
            url: `/api/admin/order/save/invoice`,
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function (response) {
                listOrder.push(response);
                createInvoice(newInvoiceID, response.id);
            },
            error: function (xhr) {
                let errorMap = JSON.parse(xhr.responseText);
                let errorMessages = Object.values(errorMap);
                notificationAddCusstomer(errorMessages, "error");
            },
        });
    }

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
    $('#btn-reset-product').on('click', function () {
        $('#search-input-color').prop("selectedIndex", 0);
        $('#search-input-size').prop("selectedIndex", 0);
        $('#search-input-category').prop("selectedIndex", 0);
        $('#search-input-product').val('')
        fetchProducts(0)
    })

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


    // Chọn khách hàng từ modal
    document.getElementById('customer-list').addEventListener('click', function (e) {
        if (e.target.classList.contains('btn-add-customer')) {
            $('#infoDetail').empty()
            let idCustomer = e.target.getAttribute('customer-id')
            let customerName = e.target.getAttribute('data-name')
            customerNameInput.innerText = customerName;
            customerNameInput.setAttribute('data-customer-id', idCustomer)
            var phoneNumber = e.target.getAttribute('data-sdt');
            var email  = e.target.getAttribute('data-email')
            $('#infoDetail').append('<p>Số điện thoại: ' + phoneNumber + '</p>');
            $('#infoDetail').append('<p>Email: ' + email + '</p>');
            $('#btn-delete-customer').prop('disabled', false);
            toggleModal(customerModal, false);
            let orderUpdate = listOrder.find(item => item.id == idOrderSelect)
            if (!orderUpdate) {
                console.error(`Không tìm thấy đơn hàng với id: ${idOrderSelect}`);
            } else {
                if (!orderUpdate.user) {
                    orderUpdate.user = {};
                }
                orderUpdate.user.id = idCustomer;
                orderUpdate.user.fullName = customerName;
                orderUpdate.user.phoneNumber = phoneNumber
                orderUpdate.user.email = email;
                updateOrder(orderUpdate)
            }
        }
    });
    //Xóa khách hàng
    document.getElementById('btn-delete-customer').addEventListener('click', function(e){
        customerNameInput.innerText = 'Khách lẻ';
        customerNameInput.setAttribute('data-customer-id', '')
        $('#infoDetail').empty()
        $('#btn-delete-customer').prop('disabled', true);
        let orderUpdate = listOrder.find(item => item.id == idOrderSelect)
        if(orderUpdate && orderUpdate.user){
            orderUpdate.user.id = 0
            orderUpdate.user.email = ''
            orderUpdate.user.fullName = ''
            orderUpdate.user.phoneNumber = ''
        }
        updateOrder(orderUpdate)
    })

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
    document.getElementById('btn-order-payment').addEventListener('click', () => updateQuantity());
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
            quantityProductCart.value = parseInt(quantityProductCart.value) + 1;
            let productToUpdate = listProduct.find(item => item.productDetail?.id == productId)
            if (productToUpdate) {
                productToUpdate.quantity = quantityProductCart.value
                let totalAmount = ((Number(quantityProductCart.value)) * Number(price));
                productToUpdate.price = totalAmount
                totalPriceProductCart.innerHTML = ((totalAmount).toLocaleString('vi-VN')).toString()
                updateOrderDetail(productToUpdate)
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
                            <p class="product-detail-amount">Giá tiền: ${(Number(price)).toLocaleString('vi-VN')} đ</p>
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

                <td><span class="product-price" product-id="${productId}">${(Number(quantityBy?.value || 1) * Number(price)).toLocaleString('vi-VN')} </span> đ</td>
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
                orderId: idOrderSelect,
                productDetail:{
                    id : productId,
                    price: price,
                    productName: name
                }
            }
            listProduct.push(orderDetail)
            console.log("id của hóa đơn muốn lưu " + idOrderSelect)
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
                let productToUpdate = listProduct.find(item => item.productDetail?.id === productId)
                quantityInput.value--;
                productToUpdate.quantity = quantityInput.value
                let totalAmount = (Number(quantityInput.value) * Number(price))
                totalPrice.innerHTML = ((totalAmount).toLocaleString('vi-VN')).toString()
                productToUpdate.price = totalAmount
                resetCoupon()
                renderCoupons(listCoupons)
                updateTotalPrice();
                updateOrderDetail(productToUpdate)
            }
        });
        newRow.querySelector(".increase").addEventListener("click", () => {
            if(Number(quantityInput.value) >= Number(totalQuantityProduct)){
                notificationAddCusstomer('Cảnh báo', 'Số lượng sản phẩm đạt giới hạn', 'error')
                return;
            }
            let productToUpdate = listProduct.find(item => item.productDetail?.id === productId)
            quantityInput.value++;
            productToUpdate.quantity = quantityInput.value
            let totalAmount = (Number(quantityInput.value) * Number(price))
            totalPrice.innerHTML = ((totalAmount).toLocaleString('vi-VN')).toString()
            productToUpdate.price = totalAmount
            resetCoupon()
            renderCoupons(listCoupons)
            updateTotalPrice();
            updateOrderDetail(productToUpdate)

        });
        quantityInput.addEventListener("change", () => {
            if(Number(quantityInput.value) > Number(totalQuantityProduct)){
                quantityInput.value = totalQuantityProduct
                notificationAddCusstomer('Cảnh báo', 'Số lượng sản phẩm đạt giới hạn', 'error')
            }
            if (quantityInput.value < 1) quantityInput.value = 1;
            let productToUpdate = listProduct.find(item => item.productDetail?.id === productId)
            productToUpdate.quantity = quantityInput.value
            let totalAmount = (Number(quantityInput.value) * Number(price))
            totalPrice.innerHTML = ((totalAmount).toLocaleString('vi-VN')).toString()
            productToUpdate.price = totalAmount
            resetCoupon()
            renderCoupons(listCoupons)
            updateTotalPrice();
            updateOrderDetail(productToUpdate)
        });
        //Xóa sản phẩm trong table
        newRow.querySelector('.delete-btn').addEventListener('click', function() {
            let productDelete = listProduct.find(product => product.productDetail?.id === productId);
            const updatedProducts = listProduct.filter(product => product.productDetail?.id !== productId);
            listProduct = updatedProducts
            newRow.remove();
            resetCoupon()
            renderCoupons(listCoupons)
            updateTotalPrice()
            if(productDelete){
                deleteOrderDetail(productDelete.id)
            }
        });
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
        totalPaymentElement.textContent = totalPayment.toLocaleString() + ' đ';
        $('#total-price-table-invoice').text(totalPayment.toLocaleString() + ' đ')
        $('#form-invoice-total-amount').text(totalPayment.toLocaleString() + ' đ')
        $('#customer-payment').val(totalPayment)
        document.getElementById('form-invoice-total-amount').setAttribute('totalPayment', totalPayment)
        calculateCustomerMoney();
        renderCoupons(listCoupons)
    }
    function updateTotalPriceWithCoupons(value, discountFixed, discountPercent, type, maximumReduction) {
        totalPayment = 0;
        const quantityProductCart = document.querySelectorAll(`.product-quantity-cart`);
        quantityProductCart.forEach(function (input) {
            const quantity = input.value;
            const price = input.getAttribute('data-price');
            totalPayment += quantity * price;
        });
        console.log(discountFixed + " discountPercent " + discountPercent)
        if(value && type){
            if(type == 'PERCENTAGE'){
                if(discountPercent && discountPercent >0){
                    let totalPaymentAfterCoupons = (totalPayment * (Number(discountPercent)/100))
                    if(totalPaymentAfterCoupons > maximumReduction? maximumReduction : 0){
                        $('#moneyDecrease').text((totalPayment - Number(maximumReduction)).toLocaleString() + ' đ')
                        totalPayment = (totalPayment - Number(maximumReduction))
                        console.log(totalPayment + " discountPercent")
                    }else{
                        $('#moneyDecrease').text((totalPayment * (Number(discountPercent)/100)).toLocaleString() + ' đ')
                        totalPayment = totalPayment - (totalPayment * (Number(discountPercent)/100))
                        console.log(totalPayment + " discountPercent")
                    }

                }
            }else{
                if(discountFixed && discountFixed >0){
                    $('#moneyDecrease').text(discountFixed.toLocaleString() + ' đ')
                    totalPayment = totalPayment - Number(discountFixed)
                    console.log(totalPayment + " discountFixed")
                }
            }
        }
        $('#form-invoice-total-amount').text(totalPayment.toLocaleString() + ' đ')
        document.getElementById('form-invoice-total-amount').setAttribute('totalPayment', totalPayment)
        calculateCustomerMoney();
    }

    function resetCoupon(){
        const radios = document.querySelectorAll('input[name="voucher-select"]');
        radios.forEach(radio => {
            radio.checked = false;
        });
        idCoupon = null
        updateTotalPriceWithCoupons()
        $('#voucher').val('')
        $('#moneyDecrease').text('0 đ')
    }

    // Tính tiền trả lại và còn thiếu
    function calculateCustomerMoney() {
        const customerPayment = parseInt(customerPaymentInput.value) || 0;
        const returnMoney = customerPayment - totalPayment;
        if (returnMoney >= 0) {
            returnMoneyElement.textContent = returnMoney.toLocaleString() + ' đ';
            remainingMoneyElement.textContent = '0 đ';
        } else {
            returnMoneyElement.textContent = '0 đ';
            remainingMoneyElement.textContent = (-returnMoney).toLocaleString() + ' đ';
        }
    }

    // Đặt lại hóa đơn sau khi tạo
    function resetInvoice() {
        listProduct = []
        productListBody.innerHTML = '';
        totalPayment = 0;
        $('#description').val('')
        $('#customer-payment').val('')
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
                        <img src="/images/product/${productAtInvoice[i]?.productDetail.images[0]?.urlImage}" alt="Sản phẩm">
                        <div class="product-detail">
                            <span>${productAtInvoice[i]?.productDetail.productName}</span><br>
                            <p class="product-detail-amount">Giá tiền: ${(productAtInvoice[i]?.productDetail.price).toLocaleString('vi-VN')} đ</p>
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
                <td><span class="product-price" product-id="${productAtInvoice[i]?.productDetail.id}">${(Number(productAtInvoice[i].quantity || 1) * Number(productAtInvoice[i]?.productDetail.price)).toLocaleString('vi-VN')} </span> </td>
                <td>
                    <button class="action-btn delete-btn">Xóa</button>
                </td>
            </tr>
        `;
                productListBody.appendChild(newRow);
                // Thêm sự kiện tăng/giảm số lượng cho input
                changeQuantityInput(newRow, productAtInvoice[i]?.productDetail.id, productAtInvoice[i]?.productDetail.price, productAtInvoice[i].productDetail?.quantity)
            }
        }
    }

    //Hiểm thị sản phẩm
    function fetchProducts(page) {
        let optionColor = $('#search-input-color').val()
        let optionSize = $('#search-input-size').val()
        let optionCategory = $('#search-input-category').val()
        const inputSearch = document.getElementById('search-input-product').value
        $.ajax({
            url: `/api/admin/order/get/products?page=${page}&limit=8`,
            type: 'GET',
            data: {
                keyword: inputSearch,
                color:optionColor,
                size: optionSize,
                category: optionCategory
            },
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
                            <td class="product-image"><img src="/images/product/${product.image}" alt="ảnh lỗi" height="100" width="auto"></td>
                            <td class="product-size">${product.sizeName}</td>
                            <td class="product-quantity">${product.quantity}</td>
                            <td class="product-color">${product.colorName}</td>
                            <td class="product-price" data-product-price="${product.price}">${(Number(product.price)).toLocaleString('vi-VN')} đ</td>
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
            url: `/api/admin/user/get/order/customers?page=${page}&limit=20`,
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
                            <td class="product-size">${customer.phoneNumber? customer.phoneNumber : ''}</td>
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
        if (customerPayment < Number(totalPayment.replace(/đ/g, '').replace(/\./g, ''))) {
            notificationAddCusstomer('Thất bại!', "Số tiền khách trả còn thiếu", 'error')
            check = false
        }
        if (listProduct[0]?.id == null) {
            notificationAddCusstomer('Thất bại!', "Hóa đơn chưa có sản phẩm nào", 'error')
            check = false
        }
        return check
    }

    function saveInvoice() {
        let note = $('#description').val()
        let orderDetailSave = listProduct.filter(item => item.orderId == idOrderSelect)
        let totalAmount = document.getElementById('form-invoice-total-amount').getAttribute('totalPayment')
        let totalProducts = orderDetailSave.reduce((sum, item) => sum + Number(item.quantity), 0);
        let paymentMethod = $('#payMethod').val()
        let data = {
            id: idOrderSelect,
            note: note,
            totalAmount: totalAmount || 0,
            paymentMethod: paymentMethod,
            paymentStatus : 'COMPLETED',
            orderStatus : 'COMPLETED',
            totalProducts: totalProducts,
            promotion: {
                id : idCoupon
            }
        }
        $.ajax({
            url: `/api/admin/order/update/invoice`,
            type: 'PUT',
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function (response) {
            },
            error: function (xhr, status,error) {
                notificationAddCusstomer('Thất bại!', "Có lỗi khi lưu hóa đơn", 'error')
                console.log("Error:", error);
                console.error('HTTP Status:', xhr.status); // Mã trạng thái HTTP
                console.error('Response Text:', xhr.responseText); // Nội dung phản hồi
                console.error('Error:', error); // Mô tả lỗi
            }
        });
        printInvoice(listProduct, idOrderSelect);
        customerNameInput.innerText = 'Khách lẻ';
        customerNameInput.setAttribute('data-customer-id', '')
        $('#infoDetail').empty()
        $('#btn-delete-customer').prop('disabled', true);
        fetchProducts(0)
        resetInvoice();
        if(idCoupon && idCoupon >0){
            // updateCoupon(idCoupon, 1)
            fecthCoupons()
        }
        resetCoupon()
        removeInvoice(selectedInvoiceId)
    }

    function updateQuantity() {
        let promises = [];
        let hasError = false;
        let listOrderddd = []
        if (!checkBeforeSaving()) {
            return
        }
        listProduct.forEach(item => {
            let data = {
                productDetail: {
                    id: item.productDetail?.id
                },
                quantity: item.quantity
            }
            listOrderddd.push(data)
        });
            $.ajax({
                url: `/api/admin/order/update/products`,
                type: 'PUT',
                contentType: "application/json",
                data: JSON.stringify(listOrderddd),
                success: function (xhr, status, error) {
                    saveInvoice();
                },
                error: function (xhr, status, error) {
                    hasError = true;
                    if(xhr){
                        let errorMap = JSON.parse(xhr.responseText);
                        let errorMessages = Object.values(errorMap);
                        notificationAddCusstomer('Thất bại!', errorMessages, 'error')
                    }
                    console.error('Error fetching districts data:', error);
                }

            });
    }

//********************* in hóa đơn **************
    function printInvoice(products, idOrderSelect) {
        $('#totalPriceVoucher').text('')
        $('#totalPricePrint').text('')
        $('#contaiTotalPricePrint').hide()
        $('#contaiTotalPriceVoucher').hide()
        let totalPriceInTable = 0;
        let currentDate = new Date();
        let formattedDate = currentDate.toLocaleDateString('vi-VN');
        var nameCustomer = $('#customer-name').text();
        let totalAmout = $('#form-invoice-total-amount').text()
        let totalAmountNumber = document.getElementById('form-invoice-total-amount').getAttribute('totalPayment')
        const quantityProductCart = document.querySelectorAll(`.product-quantity-cart`);
        quantityProductCart.forEach(function (input) {
            const quantity = input.value;
            const price = input.getAttribute('data-price');
            totalPriceInTable += quantity * price;
        });
        if(totalPriceInTable > totalAmountNumber && idCoupon){
            let totalPriceVoucher = totalPriceInTable - totalAmountNumber
            $('#contaiTotalPricePrint').show()
            $('#contaiTotalPriceVoucher').show()
            $('#totalPriceVoucher').text(totalPriceVoucher.toLocaleString() +' đ');
            $('#totalPricePrint').text(totalPriceInTable.toLocaleString()+' ');
            console.log("totalPriceVoucher " + totalPriceVoucher)
        }
        $('#totalPricePayMent').text(totalAmout)
        $('#table_print').empty()
        products.forEach((product, index) =>{
            $('#table_print').append(`
                        <tr>
                            <tr>
                                <td colspan="4"><strong>${product.productDetail?.productName}</strong></td>
                            </tr>
                        <tr>
                            <td>Đơn Giá: ${(product.productDetail?.price).toLocaleString()} đ</td>
                            <td>Số Lượng: ${product.quantity}</td>
                            <td colspan="2">Tổng: ${(product.productDetail?.price * product.quantity).toLocaleString()} đ</td>
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
      <p>Mã hóa đơn: #${idOrderSelect}</p>
      <p>Địa chỉ: Tòa nhà FPT Polytechnic, 13 phố Trịnh Văn Bô, phường Phương Canh, quận Nam Từ Liêm, TP Hà Nội</p>
      <p>Điện thoại: (024) 8582 0808</p>
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
            url: `/api/admin/user/save/customer`,
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
                $('#infoDetail').append('<p>Email: ' + email + '</p>');
                $('#btn-delete-customer').prop('disabled', false);
                toggleModal(customerModal, false);
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

//***** lấy đơn hàng chi tiết
    function fetchOrderDetail(orderID){
        $.ajax({
            url: `/api/admin/order/get/orderdetails?id=${orderID}`,
            type: 'GET',
            success: function(data) {
                listProduct = []
                    data.forEach(item => {
                        listProduct.push(item)
                    })
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
            url: `/api/admin/order/update/invoice/detail`,
            type: 'PUT',
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function(data) {

            },
            error: function(xhr, status, error) {
                let errorMap = JSON.parse(xhr.responseText);
                console.error('Error fetching districts data:', error);
            }
        });
    }

    function updateOrder(data){
        $.ajax({
            url: `/api/admin/order/update/invoice`,
            type: 'PUT',
            contentType: "application/json",
            data : JSON.stringify(data),
            success: function(respone) {
            },
            error: function(xhr, status, error) {
                let errorMap = JSON.parse(xhr.responseText);
                let errorMessages = Object.values(errorMap);
                notificationAddCusstomer(errorMessages, 'error')
                console.error('Error fetching districts data:', error);
            }
        });
    }


    function createdOrderDetail(dataOrderDetail, productId){
        $.ajax({
            url: `/api/admin/order/save/invoice/detail`,
            type: 'POST',
            contentType: "application/json",
            data: JSON.stringify(dataOrderDetail),
            success: function(data) {
                let orderDetail = listProduct.find(item => item.productDetail.id === productId);

                if (orderDetail) {
                    orderDetail.id = data.id
                    console.log("sản phẩm thêm mới " + orderDetail.id)
                }
            },
            error: function(xhr, status, error) {
                let errorMap = JSON.parse(xhr.responseText);
                console.error('Error fetching districts data:', error);
            }
        });
    }

    //Xóa hóa đơn chi tiết
    function deleteOrderDetail(IdOrderDetail){
        $.ajax({
            url:`/api/admin/order/delete/invoice/detail/${IdOrderDetail}`,
            type: 'DELETE',
            success: function(data){

            },
            error: function(xhr, status, error) {
                let errorMap = JSON.parse(xhr.responseText);
                console.error('Error fetching districts data:', error);
            }
        })
    }
    $('#xemlisst').on('click', function(){
        console.log(listProduct)
    })
//**************** Giảm giá **************

    // lấy danh sách giảm giá
    $('#closeModalBtnBackVouCher').on('click', function(){
        resetCoupon()
        toggleModal(modalChoseVoucher, false)
    })
    // chọn giảm giá
    $('#closeModalBtnAddVouCher').on('click', function(){
        const checkedInput = document.querySelector('input[name="voucher-select"]:checked');
            const value = checkedInput?.value;
            const discountFixed = checkedInput?.getAttribute('data-discountFixed');
            const discountPercent= checkedInput?.getAttribute('data-discountPercent');
            const maximumReduction = checkedInput?.getAttribute('data-maximumReduction');
            const type = checkedInput?.dataset.type
            const code = checkedInput?.dataset.code
        updateTotalPriceWithCoupons(value, discountFixed, discountPercent, type, maximumReduction)
        console.log("discountFixed "+discountFixed+ " discountPercent " + discountPercent + " type " + type)
        toggleModal(modalChoseVoucher, false)
        idCoupon = value
        $('#voucher').val(code)
    })
    fecthCoupons()
    function fecthCoupons(){
        $.ajax({
           url: '/api/admin/order/get/coupons',
           type: 'GET',
           success: function(response){
               response.forEach(item => listCoupons.push(item))
               listCoupons.forEach(item => console.log(item))
               renderCoupons(listCoupons)
           },
            error: function(xhr, error){
                let errorMap = JSON.parse(xhr.responseText);
                console.error('Error fetching districts data:', error);
            }
        });
    }
    // function updateCoupon(id, quantity){
    //     $.ajax({
    //         url: `/api/admin/order/update/coupon?id=${id}&quantity=${quantity}`,
    //         type: 'PUT',
    //         contentType: "application/json",
    //         success: function(response){
    //
    //         },
    //         error: function(xhr, error){
    //             let errorMap = JSON.parse(xhr.responseText);
    //             console.error('Error fetching districts data:', error);
    //         }
    //     });
    // }
    function renderCoupons(coupons){
        let contentCoupons = $('#contentCoupons')
        let searchCouponCode = $('#searchCoupon').val()
        let toTalPrice = document.getElementById('form-invoice-total-amount').getAttribute('totalPayment')
        contentCoupons.empty()
        let listCouponsActive = coupons.filter(
            item => item.minimumValue <= Number(toTalPrice)
            && !searchCouponCode || item.couponCode === searchCouponCode
        )
        listCouponsActive.forEach(item => {
            let endDate = item.endDate
            let date = new Date(endDate[0], endDate[1] - 1, endDate[2], endDate[3], endDate[4]);
            let formattedDate = date.toISOString().slice(0, 16).replace("T", " ");
            let newCoupons =
                `
            <div class="voucher-item">
                <div class="hhiiii" style="display: flex; justify-content: center; align-items: center; position: relative; background-color: #71cd14;">
                    <div class="vm3TF0" style="display: flex; justify-content: center; align-items: center; width: 90px; height: 90px;">
                        <img class="e52C78 nh7RxM" style="width: 45px; height: 45px; border-radius: 50%;" src="/img/voucher.png" alt="Logo">
                    </div>
                </div>

                <div class="voucher-details">
                    <div class="voucher-exp">Đơn tối thiểu: ${item.minimumValue? (item.minimumValue).toLocaleString() : '0'} ₫ || Giảm giá đến ${item.discountType == 'PERCENTAGE'? item.discountValuePercent + "%" : (item.discountValueFixed).toLocaleString() + 'đ'}
                    ${item.discountType == 'PERCENTAGE'? (item.maximumReduction? '|| giảm tối đa ' + item.maximumReduction : '') : ''}
                    <br>HSD: ${formattedDate}
                </div>
                </div>
                <div class="voucher-checkbox" style="padding-right: 15px;">
                    <input type="radio" name="voucher-select" value="${item.id}" data-discountFixed="${item.discountValueFixed}" data-discountPercent="${item.discountValuePercent}" data-type="${item.discountType}" data-code="${item.couponCode}" data-maximumReduction="${item.maximumReduction}"/>
                </div>
            </div>
           
        `
            $('#contentCoupons').append(newCoupons)
        })
    }
});
