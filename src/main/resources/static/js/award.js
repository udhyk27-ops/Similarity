import * as util from './utils.js';

const Award = {
    init() {
        this.path = window.location.pathname.split('/').pop();
        util.bindEvents(this);
        util.initDatePicker(".flatpickr");
    },

    loadWork(workNo) {
        util.getAJAX('/api/admin/searchWork', { sort: this.path, workNo }, response => {
            this.fillWorkForm(response[0]);
        });
    },

    fillWorkForm(work) {
        $('#work-code').text(work.f_code);
        $('#work-title').val(work.f_title);
        $('#work-contest').val(work.f_contest);
        $('#work-author').val(work.f_author);
        $('#work-award').val(work.f_award);
        $('#work-host').val(work.f_host);
        $('#work-manager').val(work.f_manager);
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

document.addEventListener('DOMContentLoaded', () => Award.init());
