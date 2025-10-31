// order.js - Fixed version
document.addEventListener('DOMContentLoaded', function() {
    console.log('Order.js loaded');
    
	const orderForm = document.querySelector('.order-form');
	    if (orderForm) {
	        orderForm.addEventListener('submit', function(e) {
	            // Xử lý số điện thoại: loại bỏ dấu cách trước khi submit
	            processPhoneNumberBeforeSubmit();
	            
	            if (!validateForm()) {
	                e.preventDefault();
	            }
	        });
	    }
	    
	    // Hàm xử lý số điện thoại trước khi submit
	    function processPhoneNumberBeforeSubmit() {
	        const soDienThoaiInput = document.getElementById('soDienThoai');
	        if (soDienThoaiInput && soDienThoaiInput.value) {
	            // Loại bỏ tất cả khoảng trắng và ký tự không phải số
	            const cleanedPhone = soDienThoaiInput.value.replace(/\s/g, '').replace(/[-\+\(\)]/g, '');
	            soDienThoaiInput.value = cleanedPhone;
	            console.log('Processed phone number:', cleanedPhone);
	        }
	    }
    
    // Hàm validate form hoàn chỉnh
    function validateForm() {
        let isValid = true;
        let errorMessages = [];
        
        const diaChi = document.getElementById('diaChiGiaoHang');
        const soDienThoai = document.getElementById('soDienThoai');
        const paymentMethods = document.querySelectorAll('input[name="phuongThucThanhToan"]');
        
        // Validate địa chỉ
        if (!diaChi.value.trim()) {
            showValidationError(diaChi, 'Vui lòng nhập địa chỉ giao hàng');
            isValid = false;
            errorMessages.push('Vui lòng nhập địa chỉ giao hàng');
        } else {
            clearValidationError(diaChi);
        }
        
        // Validate số điện thoại
        if (!soDienThoai.value.trim()) {
            showValidationError(soDienThoai, 'Vui lòng nhập số điện thoại');
            isValid = false;
            errorMessages.push('Vui lòng nhập số điện thoại');
        } else if (!isValidPhone(soDienThoai.value)) {
            showValidationError(soDienThoai, 'Số điện thoại phải có 10 chữ số và bắt đầu bằng 0');
            isValid = false;
            errorMessages.push('Số điện thoại không hợp lệ');
        } else {
            clearValidationError(soDienThoai);
        }
        
        // Validate phương thức thanh toán
        let paymentSelected = false;
        paymentMethods.forEach(method => {
            if (method.checked) {
                paymentSelected = true;
            }
        });
        
        if (!paymentSelected) {
            // Highlight payment section
            const paymentSection = document.querySelector('.payment-methods');
            if (paymentSection) {
                paymentSection.style.border = '1px solid #e94560';
                paymentSection.style.borderRadius = '8px';
                paymentSection.style.padding = '10px';
                paymentSection.style.backgroundColor = 'rgba(233, 69, 96, 0.05)';
            }
            isValid = false;
            errorMessages.push('Vui lòng chọn phương thức thanh toán');
        } else {
            // Clear payment section highlight
            const paymentSection = document.querySelector('.payment-methods');
            if (paymentSection) {
                paymentSection.style.border = '';
                paymentSection.style.padding = '';
                paymentSection.style.backgroundColor = '';
            }
        }
        
        // Hiển thị toast nếu có lỗi
        if (!isValid) {
            let toastMessage = 'Vui lòng kiểm tra lại thông tin đơn hàng';
            if (errorMessages.length > 0) {
                toastMessage = errorMessages[0];
            }
            showToast('error', 'Lỗi đặt hàng', toastMessage);
            
            // Scroll to first error field
            setTimeout(() => {
                const firstError = document.querySelector('.validation-error');
                if (firstError) {
                    firstError.closest('.form-group').scrollIntoView({ 
                        behavior: 'smooth', 
                        block: 'center' 
                    });
                } else {
                    // Nếu không có lỗi field nào khác, scroll đến payment section
                    const paymentSection = document.querySelector('.payment-methods');
                    if (paymentSection && !paymentSelected) {
                        paymentSection.scrollIntoView({ 
                            behavior: 'smooth', 
                            block: 'center' 
                        });
                    }
                }
            }, 1000);
        }
        
        return isValid;
    }
    
    // Hiển thị lỗi validation
    function showValidationError(input, message) {
        const formGroup = input.closest('.form-group');
        let errorElement = formGroup.querySelector('.validation-error');
        
        if (!errorElement) {
            errorElement = document.createElement('div');
            errorElement.className = 'validation-error';
            formGroup.appendChild(errorElement);
        }
        
        errorElement.textContent = message;
        errorElement.style.color = '#e94560';
        errorElement.style.fontSize = '12px';
        errorElement.style.marginTop = '5px';
        errorElement.style.fontWeight = '500';
        errorElement.style.display = 'flex';
        errorElement.style.alignItems = 'center';
        errorElement.style.gap = '5px';
        
        // Thêm icon warning
        if (!errorElement.querySelector('.error-icon')) {
            const errorIcon = document.createElement('span');
            errorIcon.className = 'error-icon';
            errorIcon.innerHTML = '⚠';
            errorIcon.style.fontSize = '14px';
            errorElement.insertBefore(errorIcon, errorElement.firstChild);
        }
        
        input.style.borderColor = '#e94560';
        input.style.boxShadow = '0 0 0 2px rgba(233, 69, 96, 0.1)';
    }
    
    // Xóa lỗi validation
    function clearValidationError(input) {
        const formGroup = input.closest('.form-group');
        const errorElement = formGroup.querySelector('.validation-error');
        
        if (errorElement) {
            errorElement.remove();
        }
        
        input.style.borderColor = '#e0e0e0';
        input.style.boxShadow = 'none';
    }
    
    // Kiểm tra số điện thoại (10 chữ số, bắt đầu bằng 0)
	function isValidPhone(phone) {
	        // Loại bỏ khoảng trắng và ký tự đặc biệt trước khi kiểm tra
	        const cleanedPhone = phone.replace(/\s/g, '').replace(/[-\+\(\)]/g, '');
	        // Kiểm tra: bắt đầu bằng 0 và có đúng 10 chữ số
	        const phoneRegex = /^0\d{9}$/;
	        return phoneRegex.test(cleanedPhone);
	    }
    
    // Hiển thị toast notification
    function showToast(type, title, message, duration = 4000) {
        const existingToast = document.querySelector('.toast');
        if (existingToast) {
            existingToast.remove();
        }
        
        const toast = document.createElement('div');
        toast.className = `toast toast-${type}`;
        
        let iconClass = '';
        if (type === 'success') iconClass = 'fa-circle-check';
        else if (type === 'error') iconClass = 'fa-circle-xmark';
        else if (type === 'warning') iconClass = 'fa-circle-exclamation';
        
        toast.innerHTML = `
            <div class="toast-icon">
                <i class="fa-solid ${iconClass}"></i>
            </div>
            <div class="toast-content">
                <div class="toast-title">${title}</div>
                <div class="toast-message">${message}</div>
            </div>
            <button class="toast-close">
                <i class="fa-solid fa-xmark"></i>
            </button>
        `;
        
        document.body.appendChild(toast);
        
        // Hiển thị toast với animation
        setTimeout(() => {
            toast.classList.add('show');
        }, 100);
        
        // Xử lý sự kiện đóng toast
        toast.querySelector('.toast-close').addEventListener('click', function() {
            toast.classList.add('hide');
            setTimeout(() => {
                if (toast.parentNode) {
                    toast.remove();
                }
            }, 300);
        });
        
        // Tự động ẩn sau duration
        setTimeout(() => {
            toast.classList.add('hide');
            setTimeout(() => {
                if (toast.parentNode) {
                    toast.remove();
                }
            }, 300);
        }, duration);
    }
    
    // Thêm sự kiện input để clear validation real-time
    const diaChiInput = document.getElementById('diaChiGiaoHang');
    const soDienThoaiInput = document.getElementById('soDienThoai');
    const paymentRadios = document.querySelectorAll('input[name="phuongThucThanhToan"]');
    
    if (diaChiInput) {
        diaChiInput.addEventListener('input', function() {
            if (this.value.trim()) {
                clearValidationError(this);
            }
        });
    }
    
	if (soDienThoaiInput) {
	        soDienThoaiInput.addEventListener('input', function() {
	            if (this.value.trim() && isValidPhone(this.value)) {
	                clearValidationError(this);
	            }
	        });
	        
	        // Format số điện thoại khi người dùng nhập (HIỂN THỊ có dấu cách)
	        soDienThoaiInput.addEventListener('input', function() {
	            // Lưu vị trí con trỏ
	            const cursorPosition = this.selectionStart;
	            
	            // Loại bỏ tất cả ký tự không phải số
	            let value = this.value.replace(/\D/g, '');
	            
	            // Giới hạn độ dài 10 số
	            if (value.length > 10) {
	                value = value.substring(0, 10);
	            }
	            
	            // Format hiển thị với dấu cách
	            let formattedValue = value;
	            if (value.length > 3 && value.length <= 6) {
	                formattedValue = value.substring(0, 3) + ' ' + value.substring(3);
	            } else if (value.length > 6) {
	                formattedValue = value.substring(0, 3) + ' ' + value.substring(3, 6) + ' ' + value.substring(6);
	            }
	            
	            this.value = formattedValue;
	            
	            // Khôi phục vị trí con trỏ (cộng thêm 1 nếu thêm dấu cách)
	            let newCursorPosition = cursorPosition;
	            if (cursorPosition === 4 && value.length >= 4) newCursorPosition++;
	            if (cursorPosition === 8 && value.length >= 7) newCursorPosition++;
	            
	            this.setSelectionRange(newCursorPosition, newCursorPosition);
	        });
	        
	        // Giới hạn chỉ nhập số (cho phép backspace, delete, etc.)
	        soDienThoaiInput.addEventListener('keydown', function(e) {
	            // Cho phép các phím điều hướng và xóa
	            if ([8, 9, 13, 16, 17, 18, 20, 27, 33, 34, 35, 36, 37, 38, 39, 40, 45, 46, 91, 92, 93].includes(e.keyCode)) {
	                return;
	            }
	            
	            // Chỉ cho phép số
	            if ((e.keyCode < 48 || e.keyCode > 57) && (e.keyCode < 96 || e.keyCode > 105)) {
	                e.preventDefault();
	            }
	        });
	    }
    
    // Clear payment validation khi chọn phương thức
    if (paymentRadios) {
        paymentRadios.forEach(radio => {
            radio.addEventListener('change', function() {
                const paymentSection = document.querySelector('.payment-methods');
                if (paymentSection) {
                    paymentSection.style.border = '';
                    paymentSection.style.padding = '';
                    paymentSection.style.backgroundColor = '';
                }
            });
        });
    }
});

// Thêm vào order.js
function showQRPayment(method, amount, description) {
    const qrText = `${method}://payment?amount=${amount}&description=${encodeURIComponent(description)}`;
    
    // Gọi API tạo QR code
    fetch(`/qrcode/generate?text=${encodeURIComponent(qrText)}&width=300&height=300`)
        .then(response => response.json())
        .then(data => {
            if (data.status === 'success') {
                // Hiển thị modal với QR code
                showQRModal(data.qrCode, amount, description, method);
            } else {
                showToast('error', 'Lỗi', 'Không thể tạo mã QR');
            }
        })
        .catch(error => {
            console.error('Error generating QR code:', error);
            showToast('error', 'Lỗi', 'Không thể tạo mã QR');
        });
}

function showQRModal(qrCodeBase64, amount, description, method) {
    const modalHtml = `
        <div class="qr-modal" style="position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.5); display: flex; align-items: center; justify-content: center; z-index: 10000;">
            <div style="background: white; padding: 30px; border-radius: 15px; text-align: center; max-width: 400px;">
                <h3>Quét mã để thanh toán</h3>
                <img src="data:image/png;base64,${qrCodeBase64}" alt="QR Code" style="width: 250px; height: 250px; margin: 15px 0;">
                <p><strong>Số tiền:</strong> ₫${formatCurrency(amount)}</p>
                <p><strong>Nội dung:</strong> ${description}</p>
                <button onclick="this.closest('.qr-modal').remove()" style="background: #e94560; color: white; border: none; padding: 10px 20px; border-radius: 5px; cursor: pointer;">
                    Đóng
                </button>
            </div>
        </div>
    `;
    
    document.body.insertAdjacentHTML('beforeend', modalHtml);
}

// Hàm format currency (cần cho QR modal)
function formatCurrency(amount) {
    return new Intl.NumberFormat('vi-VN').format(Math.round(amount));
}

// Thêm CSS
const orderStyle = document.createElement('style');
orderStyle.textContent = `
    .toast {
        position: fixed;
        top: 20px;
        right: 20px;
        min-width: 300px;
        background: white;
        padding: 20px;
        border-radius: 10px;
        box-shadow: 0 10px 30px rgba(0,0,0,0.2);
        display: flex;
        align-items: center;
        gap: 15px;
        z-index: 9999;
        border-left: 4px solid;
        animation: slideInRight 0.3s ease-out;
    }
    
    .toast-success { border-left-color: #28a745; }
    .toast-error { border-left-color: #e94560; }
    .toast-warning { border-left-color: #ffc107; }
    
    .toast-icon { font-size: 24px; }
    .toast-success .toast-icon { color: #28a745; }
    .toast-error .toast-icon { color: #e94560; }
    .toast-warning .toast-icon { color: #ffc107; }
    
    .toast-content { flex: 1; }
    .toast-title { 
        font-weight: 600;
        margin-bottom: 5px;
        color: #0f3460;
    }
    .toast-message { 
        color: #666;
        font-size: 14px;
    }
    
    .toast-close {
        background: none;
        border: none;
        font-size: 16px;
        color: #999;
        cursor: pointer;
        padding: 5px;
    }
    
    .toast-close:hover { color: #666; }
    
    @keyframes slideInRight {
        from { transform: translateX(100%); opacity: 0; }
        to { transform: translateX(0); opacity: 1; }
    }
    
    .validation-error {
        color: #e94560;
        font-size: 12px;
        margin-top: 5px;
        font-weight: 500;
        display: flex;
        align-items: center;
        gap: 5px;
    }
    
    .error-icon {
        font-size: 14px;
    }
    
    .form-control:focus {
        border-color: #0f3460;
        box-shadow: 0 0 0 2px rgba(15, 52, 96, 0.1);
    }
    
    .form-control.error {
        border-color: #e94560;
        box-shadow: 0 0 0 2px rgba(233, 69, 96, 0.1);
    }
    
    .payment-methods {
        transition: all 0.3s ease;
    }
`;
document.head.appendChild(orderStyle);