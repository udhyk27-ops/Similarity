/**
 * AJAX
 * @param url
 * @param data
 * @param onSuccess
 * @param onError
 */
export function getAJAX(url, data, onSuccess, onError = console.error) {
    $.ajax({ url, type: 'GET', data, success: onSuccess, error: onError });
}

export function postAJAX(url, data, onSuccess, onError = console.error) {
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

/**
 * 우편번호 API
 * @param callback
 */
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

/**
 * flatpickr
 * @param selector
 * @param options
 */
export function initDatePicker(selector, options = {}) {
    flatpickr(selector, { locale: 'ko', dateFormat: 'Y-m-d', ...options });
}

/**
 * 이미지 다운로드
 * @param imgSelector
 * @param fileNameSelector
 */
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

/**
 * 모달
 * @param tableSelector
 * @param rowSelector
 * @param excludeSelector
 * @param searchInputSelector
 * @param optionSelector
 * @param columns
 */
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

/**
 * 모달 검색 후 input 값 채우기
 * @param row
 * @param inputs
 * @param mapping
 */
export function fillFormFromRow({ row, inputs, mapping }) {
    const $row = $(row);
    const $inputs = $(inputs);

    mapping.forEach((colSelector, idx) => {
        if(colSelector) $inputs.eq(idx).val($row.find(colSelector).text());
    });
}

/**
 * 엑셀
 * @param header
 * @param row
 * @param fileName
 */
export function excel({ header, row, fileName }) {
    // 1) 헤더
    const headers = [];
    $(header).each(function () {
        headers.push($(this).text().trim());
    });

    // 2) 행 데이터
    const rows = [];
    $(row).each(function () {
        const rowData = [];
        $(this).find('.cell').each(function () {
            rowData.push($(this).text().trim());
        });
        rows.push(rowData);
    });

    // 3) 시트 데이터 구성
    const sheetData = [headers, ...rows];

    // 4) 엑셀 생성
    const ws = XLSX.utils.aoa_to_sheet(sheetData);
    const wb = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, "Sheet1");

    // 5) 파일 저장
    XLSX.writeFile(wb, fileName);
}