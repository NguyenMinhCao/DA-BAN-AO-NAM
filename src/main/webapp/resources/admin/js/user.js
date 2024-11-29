// Tính năng tìm kiếm trong bảng
function searchTable() {
    var input, filter, table, tr, td, i, txtValue;
    input = document.getElementById('searchInput');
    filter = input.value.toUpperCase();
    table = document.getElementById('userTable');
    tr = table.getElementsByTagName('tr');

    // Lặp qua các dòng và ẩn/hiện dựa trên từ khóa tìm kiếm
    for (i = 1; i < tr.length; i++) {
        td = tr[i].getElementsByTagName('td');
        let found = false;
        for (let j = 0; j < td.length; j++) {
            if (td[j]) {
                txtValue = td[j].textContent || td[j].innerText;
                if (txtValue.toUpperCase().indexOf(filter) > -1) {
                    found = true;
                    break;
                }
            }
        }
        tr[i].style.display = found ? '' : 'none';
    }
}

// add

// Optional JS for form validation or dynamic features (e.g., showing/hiding certain sections)
document.addEventListener('DOMContentLoaded', function() {
    // Add custom JavaScript here for dynamic functionalities
});
