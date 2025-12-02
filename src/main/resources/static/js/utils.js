// AJAX
export function getJSON(url, data, onSuccess, onError = console.error) {
    $.ajax({ url, type: 'GET', data, success: onSuccess, error: onError });
}

export function postWithCSRF(url, data, onSuccess, onError = console.error) {
    const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        url,
        type: 'POST',
        data,
        beforeSend: xhr => xhr.setRequestHeader(header, token),
        success: onSuccess,
        error: onError
    });
}

// 주소찾기 API
export function openPostcode(callback) {
    new daum.Postcode({
        oncomplete: function(data) {
            const roadAddr = data.roadAddress;
            let extraRoadAddr = '';
            if(data.bname && /[동|로|가]$/g.test(data.bname)) extraRoadAddr += data.bname;
            if(data.buildingName && data.apartment === 'Y') extraRoadAddr += (extraRoadAddr ? ', ' : '') + data.buildingName;
            if(extraRoadAddr) extraRoadAddr = extraRoadAddr ? ` (${extraRoadAddr})` : '';

            if(callback) callback({ zonecode: data.zonecode, roadAddr, extraRoadAddr });
        }
    }).open();
}

// 날짜 선택기
export function initDatePicker(selector, options = {}) {
    flatpickr(selector, { locale: 'ko', dateFormat: 'Y-m-d', ...options });
}

// 이미지 다운로드
export function downloadImage(imgSelector, fileNameSelector) {
    const img = $(imgSelector).attr('src');
    if (!img) return alert('이미지가 없습니다.');

    const link = document.createElement('a');
    link.href = img;
    link.download = $(fileNameSelector).val() || 'download.png';
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
}

// 모달 검색/필터
export function filterTableRows({ tableSelector, rowSelector, excludeSelector, searchInputSelector, optionSelector, columns }) {
    const keyword = $(searchInputSelector).val().toLowerCase();
    const option = $(optionSelector).val();

    $(`${tableSelector} ${rowSelector}`).not(excludeSelector).each(function() {
        const $row = $(this);
        let showRow = false;

        if (!keyword.trim()) showRow = true;
        else {
            const matches = columns.map(col => $row.find(col).text().toLowerCase());
            if (!option) showRow = matches.some(text => text.includes(keyword));
            else if (option === 'op-name') showRow = matches[0].includes(keyword);
            else if (option === 'op-id') showRow = matches[1].includes(keyword);
        }

        $row.toggle(showRow);
    });
}

// 모달 선택 후 input 채우기
export function fillFormFromRow({ row, inputs, mapping }) {
    const $row = $(row);
    const $inputs = $(inputs);

    mapping.forEach((colSelector, idx) => {
        if(colSelector) $inputs.eq(idx).val($row.find(colSelector).text());
    });
}