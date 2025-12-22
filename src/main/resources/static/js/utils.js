const path = window.location.pathname.split('/').pop();

export function bindEvents(context) {
    const self = context;

    const startDate = document.querySelector('input[name="startDate"]');
    const endDate = document.querySelector('input[name="endDate"]');

    // startDate 변경 시 endDate 최소값 설정
    startDate.addEventListener('change', () => {
        if (endDate.value && endDate.value < startDate.value) {
            endDate.value = '';
        }
    });

    // 테이블 행 클릭
    $('.reg-list-row').on('click', function() {
        const workNo = $(this).data('work-no');
        $('.info-workNo').val(workNo);
        self.loadWork(workNo);

        const img = document.querySelector('#work-img');
        const btn = document.querySelector('.download-btn');
        img.onerror = function () {
            this.parentElement.style.display = 'none';
            btn.style.display = 'none';
        }

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
    $('.filter').on('change', function() { window.location.href = '/admin/award?filter=' + $(this).val(); });

    // 모달 내 회원 선택
    $('#user-row').on('click', '.cell-btn', function() {
        fillFormFromRow({
            row: $(this).closest('.row'),
            inputs: '.user-tb input',
            mapping: ['.f_area', '.f_name', '.f_birth', '.f_phone', '.f_email', null, '.f_main_address', '.f_sub_address', '.f_user_no']
        });
        $('#myModal').fadeOut();
    });

    $('.del-btn').on('click', () => deleteWork());
    $('.post-btn').on('click', () => openPostcode(self.fillAddress));
    $('.mod-btn').on('click', () => modifyWork());
    $('.ins-btn').on('click', () => regWork());
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
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        beforeSend: xhr => xhr.setRequestHeader(header, token),
        success: onSuccess,
        error: onError
    });
}

export function excelAJAX(url, data, onSuccess, onError = console.error) {
    const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        url,
        type: 'POST',
        data,
        processData: false,
        contentType: false,
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
    const today = new Date();
    const tenYearsAgo = new Date(today.getFullYear() - 10, today.getMonth(), today.getDate());

    flatpickr(selector, {
        locale: 'ko',
        dateFormat: 'Y-m-d',
        minDate: tenYearsAgo,
        maxDate: today,
        ...options
    });
}

/**
 * 이미지 다운로드
 */
function downloadImage(imgSelector, fileNameSelector) {
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
function filterTableRows({ tableSelector, rowSelector, excludeSelector, searchInputSelector, optionSelector, columns }) {
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
    const rows = row.map(item => ([
        item.f_user_no,
        item.f_name,
        item.f_dept,
        item.f_position,
        item.f_phone
    ]));

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
function openUserModal() {
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
 * 작품등록(코드발급)
 */
function regWork() {
    const code = document.getElementById('work-code').textContent.trim();
    const workNo = document.getElementsByClassName('info-workNo')[0].value;
    if (code === '') {
        postAJAX('/api/admin/regWork', {sort: path, workNo: workNo}, resp => {
            if (resp === 1) alert('등록되었습니다.'), window.location.reload();
            else alert('등록 실패');
        });
    } else {
        alert('이미 등록되어있는 작품입니다.');
    }
}

/**
 * 작품 수정
 */
function modifyWork() {
    const workNo = $('.info-workNo').val();
    if (!workNo) return alert('작품을 선택해주세요.');

    const phoneReg = /^0\d{1,2}-\d{4}-\d{4}$/;
    const emailReg = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
    const phone = document.querySelector('#user-phone').value;
    const email = document.querySelector('#user-email').value;
    if (!phoneReg.test(phone)) return alert('전화번호 형식이 맞지 않습니다.');
    if (!emailReg.test(email)) return alert('이메일 형식이 맞지 않습니다.');

    const workValues = path + ', ' + $('.work-tb input').map((i, el) => $(el).val()).get() + ',' + workNo;
    const userValues = path + ', ' + $('.user-tb input').map((i, el) => $(el).val()).get() + ',' + workNo;

    postAJAX('/api/admin/modifyInfo', { work: workValues, user: userValues },
        resp => { if(resp === 1) alert('수정 완료'), window.location.reload(); else alert('수정 실패'); }
    );
}

/**
 * 모달 검색
 */
function doSearch() {
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
function deleteWork() {
    const path = window.location.pathname.split('/').pop();
    const workNo = $('.info-workNo').val();
    if(!workNo) return alert('선택된 작품이 없습니다.');

    postAJAX('/api/admin/deleteWork', { sort: path, workNo }, resp => {
        if(resp === 1) alert('삭제되었습니다.'), window.location.reload();
        else alert('삭제 실패');
    });
}