export function bindEvents(context) {
    const self = context;
    const path = window.location.pathname.split('/').pop();

    // 테이블 행 클릭
    $('.reg-list-row').on('click', function() {
        const workNo = $(this).data('work-no');
        $('.info-workNo').val(workNo);
        self.loadWork(workNo);
    });

    // 이미지 다운로드
    $('.download-btn').on('click', () => downloadImage('#work-img', '#work-title'));

    // 모달 열기
    $('#openModal').on('click', () => openUserModal());

    // 모달 닫기
    $('.close').on('click', () => $('#myModal').fadeOut());
    $(window).on('click', e => { if ($(e.target).is('#myModal')) $('#myModal').fadeOut(); });

    // 모달 검색
    $('.modal-sch .cell-btn').on('click', () => doSearch());
    $('input[name="modal-keyword"]').on('keypress', e => { if(e.key === 'Enter') doSearch(); });

    // 필터 변경
    $('.filter').on('change', function() {
        window.location.href = '/award?filter=' + $(this).val();
    });

    // 모달 내 회원 선택
    $('#user-row').on('click', '.cell-btn', function() {
        fillFormFromRow({
            row: $(this).closest('.row'),
            inputs: '.user-tb input',
            mapping: ['.f_area', '.f_name', '.f_birth', '.f_phone', '.f_email', null, '.f_main_address', '.f_sub_address', '.f_user_no']
        });
        $('#myModal').fadeOut();
    });

    // 삭제 버튼
    $('.del-btn').on('click', () => deleteWork());

    // 우편번호 버튼
    $('.post-btn').on('click', () => openPostcode(self.fillAddress));

    // 수정 버튼
    $('.mod-btn').on('click', () => modifyWork());

    // 작품 등록 페이지 이동
    $('.ins-btn').on('click', () => window.location.href = '/single/' + path);
}


/**
 * AJAX
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

/**
 * 모달
 */
export function openUserModal() {
    const workNo = $('.info-workNo').val();
    if (!workNo) return alert('작품을 선택해주세요.');

    getAJAX('/api/admin/searchUser', { sort: $('.select-user').val() }, data => {
        const $template = $('#user-template');
        const $userRow = $('#user-row');
        $userRow.empty();

        if(data.length === 0) {
            $userRow.append('<div class="row"><div class="cell" style="justify-content: center">등록된 회원이 없습니다.</div></div>');
            return;
        }

        data.forEach(user => {
            const row = $template.clone().removeAttr('id').show().css('display','flex');
            row.find('.cell').css({'display':'flex','justify-content':'center'});
            Object.keys(user).forEach(key => {
                row.find(`.${key}`).text(user[key]);
            });
            $userRow.append(row);
        });

        $('#myModal').fadeIn();
    });
}

/**
 * 작품 수정
 */
export function modifyWork() {
    const path = window.location.pathname.split('/').pop();
    const workNo = $('.info-workNo').val();
    if (!workNo) return alert('작품을 선택해주세요.');

    const workValues = path + ', ' + $('.work-tb input').map((i, el) => $(el).val()).get() + ',' + workNo;
    const userValues = path + ', ' + $('.user-tb input').map((i, el) => $(el).val()).get() + ',' + workNo;

    postAJAX('/api/admin/modifyInfo', { work: workValues, user: userValues },
        resp => { if(resp === 1) alert('수정 완료'), window.location.reload(); else alert('수정 실패'); }
    );
}


/**
 * 모달 검색
 */
export function doSearch() {
    filterTableRows({
        tableSelector: '.user-list-tb',
        rowSelector: '.row',
        excludeSelector: '.header-row, #user-template',
        searchInputSelector: 'input[name="modal-keyword"]',
        optionSelector: '.modal-sch select',
        columns: ['.f_name', '.f_id']
    });
}

/**
 * 작품 삭제
 */
export function deleteWork() {
    const path = window.location.pathname.split('/').pop();
    const workNo = $('.info-workNo').val();
    if(!workNo) return alert('선택된 작품이 없습니다.');

    postAJAX('/api/admin/deleteWork', { sort: path, workNo }, resp => {
        if(resp === 1) alert('삭제되었습니다.'), window.location.reload();
        else alert('삭제 실패');
    });
}