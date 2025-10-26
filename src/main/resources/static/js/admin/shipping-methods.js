// ============= SHIPPING METHODS PAGE JAVASCRIPT =============
// Synchronized with products.js functionality

// ============= DELETE MODAL =============
function showDeleteModal(button) {
    const methodId = button.getAttribute('data-method-id');
    const methodName = button.getAttribute('data-method-name');
    
    // Set method name in modal
    document.getElementById('methodName').textContent = methodName;
    
    // Set form action
    const deleteForm = document.getElementById('deleteForm');
    deleteForm.action = `/admin/shipping-methods/delete/${methodId}`;
    
    // Show modal
    const deleteModal = new bootstrap.Modal(document.getElementById('deleteModal'));
    deleteModal.show();
}

// ============= STATUS TOGGLE =============
let currentStatusButton = null;

function toggleStatus(button) {
    currentStatusButton = button;
    const methodId = button.getAttribute('data-method-id');
    const currentStatus = button.getAttribute('data-current-status') === 'true';
    
    // Create dropdown menu
    const dropdownMenu = document.getElementById('statusDropdownMenu');
    
    // Position dropdown below button
    const rect = button.getBoundingClientRect();
    dropdownMenu.style.position = 'fixed';
    dropdownMenu.style.top = (rect.bottom + 5) + 'px';
    dropdownMenu.style.left = rect.left + 'px';
    dropdownMenu.style.display = 'block';
    
    // Add click handlers to status items
    const statusItems = dropdownMenu.querySelectorAll('.status-item');
    statusItems.forEach(item => {
        item.onclick = function() {
            const newStatus = this.getAttribute('data-status') === 'true';
            
            // Only submit if status actually changed
            if (newStatus !== currentStatus) {
                updateMethodStatus(methodId, newStatus);
            }
            
            // Hide dropdown
            dropdownMenu.style.display = 'none';
        };
    });
}

// Close dropdown when clicking outside
document.addEventListener('click', function(event) {
    const dropdownMenu = document.getElementById('statusDropdownMenu');
    if (dropdownMenu && !event.target.closest('.status-toggle') && !event.target.closest('.status-dropdown')) {
        dropdownMenu.style.display = 'none';
    }
});

function updateMethodStatus(methodId, newStatus) {
    // Create and submit form
    const form = document.createElement('form');
    form.method = 'POST';
    form.action = `/admin/shipping-methods/toggle-status/${methodId}`;
    
    document.body.appendChild(form);
    form.submit();
}

// ============= SORT TABLE =============
function sortTable(sortBy) {
    const urlParams = new URLSearchParams(window.location.search);
    const currentSortBy = urlParams.get('sortBy') || 'thuTu';
    const currentSortDir = urlParams.get('sortDir') || 'asc';
    
    let newSortDir = 'asc';
    
    // If clicking the same column, toggle direction
    if (currentSortBy === sortBy) {
        newSortDir = currentSortDir === 'asc' ? 'desc' : 'asc';
    }
    
    // Update URL parameters
    urlParams.set('sortBy', sortBy);
    urlParams.set('sortDir', newSortDir);
    
    // Reload page with new sort parameters
    window.location.search = urlParams.toString();
}

// ============= HIGHLIGHT ACTIVE SORT COLUMN =============
document.addEventListener('DOMContentLoaded', function() {
    const urlParams = new URLSearchParams(window.location.search);
    const currentSortBy = urlParams.get('sortBy') || 'thuTu';
    const currentSortDir = urlParams.get('sortDir') || 'asc';
    
    // Find and highlight the active sort column
    const sortableHeaders = document.querySelectorAll('.sortable');
    sortableHeaders.forEach(header => {
        const sortField = header.getAttribute('data-sort');
        if (sortField === currentSortBy) {
            header.style.background = '#f8f9fa';
            const sortIcon = header.querySelector('.sort-icon');
            if (sortIcon) {
                sortIcon.classList.add('active');
                sortIcon.classList.remove('fa-sort');
                if (currentSortDir === 'asc') {
                    sortIcon.classList.add('fa-sort-up');
                } else {
                    sortIcon.classList.add('fa-sort-down');
                }
            }
        }
    });
});

// ============= AUTO-HIDE ALERTS =============
document.addEventListener('DOMContentLoaded', function() {
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(alert => {
        setTimeout(() => {
            const bsAlert = new bootstrap.Alert(alert);
            bsAlert.close();
        }, 5000); // Auto-hide after 5 seconds
    });
});

// ============= SEARCH FORM ENHANCEMENT =============
document.addEventListener('DOMContentLoaded', function() {
    const searchInput = document.querySelector('.search-input');
    if (searchInput) {
        // Add search icon animation on focus
        searchInput.addEventListener('focus', function() {
            this.parentElement.style.transform = 'scale(1.02)';
            this.parentElement.style.transition = 'all 0.3s ease';
        });
        
        searchInput.addEventListener('blur', function() {
            this.parentElement.style.transform = 'scale(1)';
        });
    }
});

// ============= SMOOTH SCROLL TO TOP =============
function scrollToTop() {
    window.scrollTo({
        top: 0,
        behavior: 'smooth'
    });
}

// Show scroll to top button when scrolling down
window.addEventListener('scroll', function() {
    const scrollButton = document.getElementById('scrollToTopBtn');
    if (scrollButton) {
        if (window.pageYOffset > 300) {
            scrollButton.style.display = 'block';
        } else {
            scrollButton.style.display = 'none';
        }
    }
});

// ============= TABLE ROW HOVER EFFECT =============
document.addEventListener('DOMContentLoaded', function() {
    const tableRows = document.querySelectorAll('.table tbody tr');
    tableRows.forEach(row => {
        row.addEventListener('mouseenter', function() {
            this.style.transition = 'all 0.3s ease';
        });
    });
});

// ============= FORM VALIDATION ENHANCEMENT =============
document.addEventListener('DOMContentLoaded', function() {
    const forms = document.querySelectorAll('form');
    forms.forEach(form => {
        form.addEventListener('submit', function(event) {
            // Add loading state to submit button
            const submitBtn = this.querySelector('button[type="submit"]');
            if (submitBtn) {
                submitBtn.disabled = true;
                submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin me-2"></i>Đang xử lý...';
            }
        });
    });
});
