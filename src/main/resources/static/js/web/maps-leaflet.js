// Leaflet Maps Integration - KHÔNG CẦN API KEY
// Thay thế Google Maps bằng OpenStreetMap (miễn phí 100%)

let map = null;
let marker = null;

// Khởi tạo Leaflet Map
function initMap(containerId = 'map', lat = 10.762622, lng = 106.660172) {
    try {
        console.log('🗺️ Đang khởi tạo bản đồ cho container:', containerId);
        
        // Kiểm tra Leaflet đã được load chưa
        if (typeof L === 'undefined') {
            console.error('❌ Leaflet library chưa được load!');
            setTimeout(() => initMap(containerId, lat, lng), 500);
            return null;
        }
        
        // Kiểm tra container có tồn tại không
        const container = document.getElementById(containerId);
        if (!container) {
            console.error('❌ Không tìm thấy container:', containerId);
            return null;
        }
        
        console.log('📦 Container found:', container);
        console.log('📦 Container dimensions:', container.offsetWidth, 'x', container.offsetHeight);
        
        // Nếu map đã tồn tại cho container này, xóa đi
        if (map && map._container && map._container.id === containerId) {
            console.log('🔄 Xóa bản đồ cũ...');
            map.remove();
            map = null;
            marker = null;
        }
        
        // Xóa innerHTML của container (phòng trường hợp map cũ còn sót)
        container.innerHTML = '';
        
        // Đảm bảo container có kích thước
        if (container.offsetHeight === 0) {
            console.warn('⚠️ Container height = 0, setting min-height...');
            container.style.minHeight = '300px';
        }
        
        console.log('🌍 Khởi tạo Leaflet map...');
        // Khởi tạo bản đồ với OpenStreetMap
        map = L.map(containerId, {
            center: [lat, lng],
            zoom: 15,
            zoomControl: true,
            attributionControl: true
        });

        console.log('🗺️ Thêm tile layer...');
        // Thêm tile layer từ OpenStreetMap (miễn phí, không cần API key)
        const tileLayer = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '© <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
            maxZoom: 19,
            minZoom: 5
        });
        
        tileLayer.on('tileerror', function(error) {
            console.error('❌ Tile loading error:', error);
        });
        
        tileLayer.on('tileload', function() {
            console.log('✅ Tiles đang load...');
        });
        
        tileLayer.addTo(map);

        console.log('📍 Thêm marker...');
        // Tạo marker
        marker = L.marker([lat, lng], {
            draggable: true
        }).addTo(map);

        // Lắng nghe sự kiện kéo marker
        marker.on('dragend', function(event) {
            const position = marker.getLatLng();
            console.log('🎯 Marker dragged to:', position.lat, position.lng);
            
            // Enable textarea tạm thời để cập nhật địa chỉ
            const addressInput = document.getElementById('diaChiChiTiet') || 
                               document.getElementById('diaChi');
            let wasDisabled = false;
            
            if (addressInput && addressInput.disabled) {
                wasDisabled = true;
                addressInput.disabled = false;
                console.log('🔓 Tạm thời enable textarea để cập nhật địa chỉ');
            }
            
            updateCoordinates(position.lat, position.lng);
            reverseGeocode(position.lat, position.lng);
            
            // Không disable lại vì user đang trong chế độ edit
        });

        // Click vào map để đặt marker mới
        map.on('click', function(e) {
            placeMarker(e.latlng);
        });

        // Force resize map sau khi khởi tạo
        setTimeout(() => {
            if (map) {
                console.log('🔄 Invalidating map size...');
                map.invalidateSize();
            }
        }, 100);

        console.log('✅ Bản đồ OpenStreetMap đã load thành công!');
        return map;
    } catch (error) {
        console.error('❌ Lỗi khởi tạo bản đồ:', error);
        console.error('Error stack:', error.stack);
        return null;
    }
}

// Đặt marker tại vị trí
function placeMarker(latlng) {
    console.log('📍 Placing marker at:', latlng.lat, latlng.lng);
    
    if (marker) {
        marker.setLatLng(latlng);
    } else {
        marker = L.marker(latlng, { draggable: true }).addTo(map);
        
        // Add dragend event to new marker
        marker.on('dragend', function(event) {
            const position = marker.getLatLng();
            console.log('🎯 Marker dragged to:', position.lat, position.lng);
            
            // Enable textarea tạm thời để cập nhật địa chỉ
            const addressInput = document.getElementById('diaChiChiTiet') || 
                               document.getElementById('diaChi');
            
            if (addressInput && addressInput.disabled) {
                addressInput.disabled = false;
                console.log('🔓 Tạm thời enable textarea để cập nhật địa chỉ');
            }
            
            updateCoordinates(position.lat, position.lng);
            reverseGeocode(position.lat, position.lng);
        });
    }
    
    map.panTo(latlng);
    
    // Enable textarea trước khi cập nhật
    const addressInput = document.getElementById('diaChiChiTiet') || 
                       document.getElementById('diaChi');
    
    if (addressInput && addressInput.disabled) {
        addressInput.disabled = false;
        console.log('🔓 Tạm thời enable textarea để cập nhật địa chỉ');
    }
    
    updateCoordinates(latlng.lat, latlng.lng);
    reverseGeocode(latlng.lat, latlng.lng);
}

// Reverse Geocoding - chuyển tọa độ thành địa chỉ
function reverseGeocode(lat, lng) {
    console.log('=== REVERSE GEOCODING START ===');
    console.log('🔄 Tọa độ:', lat, lng);
    
    // Tìm input địa chỉ ngay từ đầu
    const addressInput = document.getElementById('diaChiChiTiet') || 
                       document.getElementById('diaChi');
    
    console.log('🔍 Tìm kiếm element diaChiChiTiet:', document.getElementById('diaChiChiTiet'));
    console.log('🔍 Tìm kiếm element diaChi:', document.getElementById('diaChi'));
    
    if (!addressInput) {
        console.error('❌ KHÔNG TÌM THẤY textarea/input địa chỉ!');
        console.log('📋 Tất cả textarea trong document:');
        document.querySelectorAll('textarea').forEach(ta => {
            console.log('  - ID:', ta.id, 'Name:', ta.name, 'Value:', ta.value.substring(0, 50));
        });
        return;
    }
    
    console.log('✅ Tìm thấy element:', addressInput.tagName, 'ID:', addressInput.id);
    console.log('📦 Thuộc tính:', {
        disabled: addressInput.disabled,
        readOnly: addressInput.readOnly,
        value: addressInput.value.substring(0, 50) + '...'
    });
    
    // Enable textarea nếu đang bị disabled
    if (addressInput.disabled) {
        addressInput.disabled = false;
        console.log('🔓 Đã enable textarea');
    }
    
    const url = `https://nominatim.openstreetmap.org/reverse?format=json&lat=${lat}&lon=${lng}&addressdetails=1`;
    console.log('📡 Gọi API:', url);
    
    fetch(url)
        .then(response => {
            console.log('📥 Response status:', response.status);
            if (!response.ok) {
                throw new Error(`HTTP ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            console.log('📍 Dữ liệu nhận được:', data);
            
            if (data && data.display_name) {
                const oldValue = addressInput.value;
                console.log('📝 Địa chỉ CŨ:', oldValue);
                console.log('📝 Địa chỉ MỚI:', data.display_name);
                
                // CẬP NHẬT GIÁ TRỊ
                addressInput.value = data.display_name;
                
                console.log('📝 Giá trị SAU KHI cập nhật:', addressInput.value);
                console.log('✅ Đã set value thành công:', addressInput.value === data.display_name);
                
                // Trigger events
                addressInput.dispatchEvent(new Event('input', { bubbles: true }));
                addressInput.dispatchEvent(new Event('change', { bubbles: true }));
                
                console.log('=== REVERSE GEOCODING SUCCESS ===');
            } else {
                console.warn('⚠️ API không trả về display_name');
                console.log('Response data:', data);
            }
        })
        .catch(error => {
            console.error('❌ LỖI reverse geocoding:', error);
            console.error('Error stack:', error.stack);
        });
}

// Geocoding - chuyển địa chỉ thành tọa độ
function geocodeAddress(address, callback) {
    if (!address || address.trim() === '') {
        alert('Vui lòng nhập địa chỉ');
        return;
    }

    const url = `https://nominatim.openstreetmap.org/search?format=json&q=${encodeURIComponent(address)}&countrycodes=vn&limit=1`;
    
    fetch(url)
        .then(response => response.json())
        .then(data => {
            if (data && data.length > 0) {
                const lat = parseFloat(data[0].lat);
                const lng = parseFloat(data[0].lon);
                
                if (map) {
                    const latlng = L.latLng(lat, lng);
                    map.setView(latlng, 15);
                    if (marker) {
                        marker.setLatLng(latlng);
                    } else {
                        marker = L.marker(latlng, { draggable: true }).addTo(map);
                    }
                }
                
                updateCoordinates(lat, lng);
                
                if (callback) {
                    callback({ lat, lng });
                }
            } else {
                alert('Không tìm thấy địa chỉ. Vui lòng thử lại với địa chỉ khác.');
            }
        })
        .catch(error => {
            console.error('Lỗi geocoding:', error);
            alert('Không thể tìm kiếm địa chỉ. Vui lòng thử lại.');
        });
}

// Cập nhật tọa độ vào hidden fields
function updateCoordinates(lat, lng) {
    // Cập nhật cho địa chỉ giao hàng (modal)
    const latInput = document.getElementById('latitude');
    const lngInput = document.getElementById('longitude');
    
    if (latInput) latInput.value = lat;
    if (lngInput) lngInput.value = lng;
    
    // Cập nhật cho profile map (nếu có callback)
    if (window.updateProfileCoordinates && typeof window.updateProfileCoordinates === 'function') {
        window.updateProfileCoordinates(lat, lng);
    }
    
    console.log('Tọa độ đã cập nhật:', lat, lng);
}

// Khởi tạo autocomplete (simplified version)
function initAutocomplete(inputId = 'diaChiChiTiet') {
    const input = document.getElementById(inputId);
    if (!input) return;

    let timeout = null;
    input.addEventListener('input', function() {
        clearTimeout(timeout);
        const query = this.value;
        
        if (query.length < 3) return;
        
        timeout = setTimeout(() => {
            searchAddress(query);
        }, 500);
    });
}

// Tìm địa chỉ
function searchAddress(query) {
    const url = `https://nominatim.openstreetmap.org/search?format=json&q=${encodeURIComponent(query)}&countrycodes=vn&limit=5`;
    
    fetch(url)
        .then(response => response.json())
        .then(data => {
            // Có thể hiển thị suggestions ở đây
            console.log('Kết quả tìm kiếm:', data);
        })
        .catch(error => {
            console.error('Lỗi tìm kiếm:', error);
        });
}

// Lấy vị trí hiện tại
function getCurrentLocation(callback) {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
            (position) => {
                const lat = position.coords.latitude;
                const lng = position.coords.longitude;
                
                if (map) {
                    const latlng = L.latLng(lat, lng);
                    map.setView(latlng, 15);
                    if (marker) {
                        marker.setLatLng(latlng);
                    } else {
                        marker = L.marker(latlng, { draggable: true }).addTo(map);
                    }
                }
                
                updateCoordinates(lat, lng);
                reverseGeocode(lat, lng);
                
                if (callback) {
                    callback({ lat, lng });
                }
            },
            (error) => {
                console.error('Lỗi lấy vị trí:', error);
                alert('Không thể lấy vị trí hiện tại. Vui lòng cho phép truy cập vị trí.');
            }
        );
    } else {
        alert('Trình duyệt của bạn không hỗ trợ Geolocation');
    }
}

// Hiển thị bản đồ cho địa chỉ (chỉ xem)
function showAddressOnMap(address, lat, lng, containerId = 'map') {
    try {
        console.log('🗺️ Hiển thị bản đồ cho địa chỉ:', containerId);
        
        const container = document.getElementById(containerId);
        if (!container) {
            console.error('❌ Không tìm thấy container:', containerId);
            return null;
        }
        
        // Xóa nội dung cũ nếu có
        container.innerHTML = '';
        
        const viewMap = L.map(containerId).setView([lat || 10.762622, lng || 106.660172], 15);
        
        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '© OpenStreetMap contributors',
            maxZoom: 19
        }).addTo(viewMap);
        
        const viewMarker = L.marker([lat || 10.762622, lng || 106.660172]).addTo(viewMap);
        
        if (address) {
            viewMarker.bindPopup(address).openPopup();
        }
        
        // Force resize map
        setTimeout(() => {
            if (viewMap) {
                viewMap.invalidateSize();
            }
        }, 100);
        
        console.log('✅ Bản đồ xem địa chỉ đã load thành công!');
        return viewMap;
    } catch (error) {
        console.error('❌ Lỗi hiển thị bản đồ:', error);
        console.error('Error stack:', error.stack);
        return null;
    }
}

// Tìm kiếm địa chỉ trên bản đồ
function searchAddressOnMap() {
    const addressInput = document.getElementById('diaChiChiTiet') || 
                        document.getElementById('diaChi');
    const address = addressInput ? addressInput.value.trim() : '';
    
    if (!address) {
        alert('Vui lòng nhập địa chỉ trước');
        return;
    }
    
    geocodeAddress(address);
}

// Reset bản đồ
function resetMap() {
    const defaultLat = 10.762622;
    const defaultLng = 106.660172;
    
    if (map) {
        const latlng = L.latLng(defaultLat, defaultLng);
        map.setView(latlng, 15);
        if (marker) {
            marker.setLatLng(latlng);
        }
    }
    
    updateCoordinates(defaultLat, defaultLng);
}

// Export functions
window.mapsHelper = {
    initMap,
    initAutocomplete,
    getCurrentLocation,
    geocodeAddress,
    reverseGeocode,
    showAddressOnMap,
    searchAddress: searchAddressOnMap,
    resetMap,
    placeMarker,
    updateCoordinates
};

console.log('✅ Maps helper (OpenStreetMap) đã sẵn sàng - KHÔNG CẦN API KEY!');
