import { getAJAX, postAJAX, initDatePicker, downloadImage, openPostcode, filterTableRows, fillFormFromRow } from './utils.js';

const InvitModule = {
    init() {
        this.path = window.location.pathname.split('/').pop();
        this.bindEvents();
        initDatePicker(".flatpickr");
    },

    bindEvents() {
        const self = this;

        // 테이블 행 클릭
        $('.reg-list-row').on('click', function() {
            const workNo = $(this).data('work-no');
            $('.info-workNo').val(workNo);
            self.loadWork(workNo);
        });
        // 이미지 다운로드
        $('.download-btn').on('click', () => downloadImage('#work-img', '#work-title'));

        // 모달 열기
        $('#openModal').on('click', () => self.openUserModal());

        // 모달 닫기
        $('.close').on('click', () => $('#myModal').fadeOut());
        $(window).on('click', e => { if ($(e.target).is('#myModal')) $('#myModal').fadeOut(); });

        // 모달 검색
        $('.modal-sch .cell-btn').on('click', () => self.doSearch());
        $('input[name="modal-keyword"]').on('keypress', e => { if(e.key === 'Enter') self.doSearch(); });

        // 회원 선택
        $('#user-row').on('click', '.cell-btn', function() {
            fillFormFromRow({
                row: $(this).closest('.row'),
                inputs: '.user-tb input',
                mapping: ['.f_area', '.f_name', '.f_birth', '.f_phone', '.f_email', null, '.f_main_address', '.f_sub_address', '.f_user_no']
            });
            $('#myModal').fadeOut();
        });

        // 우편번호 버튼
        $('.post-btn').on('click', () => openPostcode(self.fillAddress));

        // 삭제 버튼
        $('.del-btn').on('click', () => self.deleteWork());

        // 수정 버튼
        $('.mod-btn').on('click', () => self.modifyWork());

        // 작품 등록 페이지 이동
        $('.ins-btn').on('click', () => window.location.href = '/single/invit');
    },

    loadWork(workNo) {
        getAJAX('/api/admin/searchWork', { sort: this.path, workNo }, response => {
            this.fillWorkForm(response[0]);
        });
    },

    fillWorkForm(work) {
        $('#work-code').text(work.f_code);
        $('#work-title').val(work.f_title);
        $('#work-author').val(work.f_author);
        $('#work-year').val(work.f_year);
        $('#work-regdate').text(work.f_reg_date);
        $('#work-name').text(`${work.f_name}(${work.f_id})`);
        $('#work-dept').text(work.f_dept);
        $('#work-img').attr('src', work.f_filepath);

        $('#user-area').val(work.f_area);
        $('#user-name').val(work.f_name);
        $('#user-birth').val(work.f_birth);
        $('#user-phone').val(work.f_phone);
        $('#user-email').val(work.f_email);
        $('#sample4_roadAddress').val(work.f_main_address);
        $('#sample4_extraAddress').val(work.f_sub_address);
        $('#info-userNo').val(work.f_user_no);

        if(work.f_filepath) {
            $('.img-div, .download-btn').show();
        } else {
            $('.img-div, .download-btn').hide();
        }
    },

    openUserModal() {
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
    },

    doSearch() {
        filterTableRows({
            tableSelector: '.user-list-tb',
            rowSelector: '.row',
            excludeSelector: '.header-row, #user-template',
            searchInputSelector: 'input[name="modal-keyword"]',
            optionSelector: '.modal-sch select',
            columns: ['.f_name', '.f_id']
        });
    },

    fillAddress(result) {
        $('#sample4_postcode').val(result.zonecode);
        $('#sample4_roadAddress').val(result.roadAddr);
        $('#sample4_extraAddress').val(result.extraRoadAddr);
    },

    deleteWork() {
        const workNo = $('.info-workNo').val();
        if(!workNo) return alert('선택된 작품이 없습니다.');

        postAJAX('/api/admin/deleteWork', { sort: this.path, workNo }, resp => {
            if(resp === 1) alert('삭제되었습니다.'), window.location.reload();
            else alert('삭제 실패');
        });
    },

    modifyWork() {
        const workNo = $('.info-workNo').val();
        if(!workNo) return alert('작품을 선택해주세요.');

        const workValues = this.path + ', ' + $('.work-tb input').map((i, el) => $(el).val()).get() + ',' + workNo;
        const userValues = this.path + ', ' + $('.user-tb input').map((i, el) => $(el).val()).get() + ',' + workNo;

        postAJAX('/api/admin/modifyInfo', { work: workValues, user: userValues },
            resp => { if(resp === 1) alert('수정 완료'), window.location.reload(); else alert('수정 실패'); }
        );
    }
};

document.addEventListener('DOMContentLoaded', () => InvitModule.init());

export default InvitModule;