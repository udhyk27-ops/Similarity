import * as util from './utils.js';

const InvitModule = {
    init() {
        this.path = window.location.pathname.split('/').pop();
        this.bindEvents();
        util.initDatePicker(".flatpickr");
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
        $('.download-btn').on('click', () => util.downloadImage('#work-img', '#work-title'));

        // 모달 열기
        $('#openModal').on('click', () => util.openUserModal());

        // 모달 닫기
        $('.close').on('click', () => $('#myModal').fadeOut());
        $(window).on('click', e => { if ($(e.target).is('#myModal')) $('#myModal').fadeOut(); });

        // 모달 검색
        $('.modal-sch .cell-btn').on('click', () => util.doSearch());
        $('input[name="modal-keyword"]').on('keypress', e => { if(e.key === 'Enter') util.doSearch(); });

        // 회원 선택
        $('#user-row').on('click', '.cell-btn', function() {
            util.fillFormFromRow({
                row: $(this).closest('.row'),
                inputs: '.user-tb input',
                mapping: ['.f_area', '.f_name', '.f_birth', '.f_phone', '.f_email', null, '.f_main_address', '.f_sub_address', '.f_user_no']
            });
            $('#myModal').fadeOut();
        });

        // 우편번호 버튼
        $('.post-btn').on('click', () => util.openPostcode(self.fillAddress));

        // 삭제 버튼
        $('.del-btn').on('click', () => util.deleteWork());

        // 수정 버튼
        $('.mod-btn').on('click', () => util.modifyWork());

        // 작품 등록 페이지 이동
        $('.ins-btn').on('click', () => window.location.href = '/single/invit');
    },

    loadWork(workNo) {
        util.getAJAX('/api/admin/searchWork', { sort: this.path, workNo }, response => {
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

    fillAddress(result) {
        $('#sample4_postcode').val(result.zonecode);
        $('#sample4_roadAddress').val(result.roadAddr);
        $('#sample4_extraAddress').val(result.extraRoadAddr);
    }

};

document.addEventListener('DOMContentLoaded', () => InvitModule.init());

export default InvitModule;