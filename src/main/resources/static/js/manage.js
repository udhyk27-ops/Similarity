import * as util from "./utils.js";
import {excelAJAX} from "./utils.js";

const Manage = {
    init() {
        this.bindEvents();
    },

    bindEvents() {
        // 리스트에서 회원 클릭 시 정보 로드
        $('.reg-list-row').on('click', e => {
            const userNo = $(e.currentTarget).data('user-no');
            if (!userNo) return;
            $('#user-no').val(userNo);

            util.getAJAX(
                '/api/admin/searchUser',
                { userNo },
                response =>
                    this.fillUserInfo(response[0])
            );
        });

        // 우편번호 찾기 (User 페이지에서 사용)
        if ($('.post-btn').length) {
            $('.post-btn').on('click', () => {
                util.openPostcode(addr => {
                    $('#sample4_postcode').val(addr.zonecode);
                    $('#sample4_roadAddress').val(addr.roadAddr);
                    $('#sample4_extraAddress').val(addr.extraRoadAddr);
                });
            });
        }

        // 삭제
        $('.del-btn').on('click', () => this.deleteUser());

        // 저장
        $('#manage-form').on('submit', e =>  {
            e.preventDefault()

            const formData = $(e.currentTarget).serialize();
            console.log('formData:', formData);

            if ( $('body').data('page-type') === 'user' ) {
                this.saveUser(formData);
            } else {
                this.saveAdmin(formData);
            }
        });

        // 엑셀 저장
        $('.excel-div .cell-btn').on('click', () => {
            const sort = $('body').data('page-type') === 'user' ? '회원' : '관리자';
            const date = new Date();
            const today = date.getFullYear().toString() + (date.getMonth() + 1) + date.getDate();


            // schFilter, keyword 넘기기
            const schFilter = document.querySelector('select[name="schFilter"]');
            const keyword = document.querySelector('input[name="keyword"]');

            console.log('schFilter: ' + schFilter.value);
            console.log('keyword: ' + keyword.value);

            const formData = new FormData();
            formData.append('sort', sort);
            formData.append('schFilter', schFilter.value);
            formData.append('keyword', keyword.value);

            // console.log('formData:', formData);

            util.excelAJAX('/api/admin/selExcel', formData, response => {

                console.log(response);

                if (response) {
                    util.excel({
                        header: '.reg-list-tb > .header-row > .cell',
                        row: response,
                        fileName: sort + '목록_' + today + '.xlsx'
                    });
                } else {
                    console.log('통신 오류');
                }
            });
        });
    },

    fillUserInfo(user) {
        $('#user-id').val(user.f_id);
        $('#user-name').val(user.f_name);
        $('#user-phone').val(user.f_phone);
        $('#user-email').val(user.f_email);
        $('#user-dept').val(user.f_dept);
        $('#user-position').val(user.f_position);
        $('#user-login').text(user.f_login_date);
        $('#user-modify').text(user.f_mod_date);
        $('#user-memo').text(user.f_memo);
        $('#user-no').val(user.f_user_no);

        // 주소 관련 필드 초기화 (User 페이지)
        if ($('#sample4_roadAddress').length) {
            $('#sample4_roadAddress').val(user.f_main_address);
            $('#sample4_extraAddress').val(user.f_sub_address);
        }

        // 1) 체크박스 초기화 (Admin 페이지)
        $('input[name="auth"]').prop('checked', false);

        // 2) 권한 존재하면
        if (user.f_auth && user.f_auth.length > 0) {
            for (let key in user.f_auth[0]) {
                if (user.f_auth[0][key] === 'Y') {
                    $(`input[name="auth"][value="${key}"]`).prop('checked', true);
                }
            }
        }

        // 상태 체크
        if (user.f_status === "Y") {
            $("#use").prop("checked", true);
            $("#stop").prop("checked", false);
        } else if (user.f_status === "N") {
            $("#use").prop("checked", false);
            $("#stop").prop("checked", true);
        }
    },

    /** 회원 삭제 */
    deleteUser() {
        const sort = $('body').data('page-type') === 'user' ? '회원' : '관리자';
        const userNo = $('#user-no').val();
        if (!userNo) return alert('대상을 선택해주세요.');

        util.postAJAX('/api/admin/deleteUser', { sort, userNo }, response => {
            if (response === 1) {
                alert('삭제되었습니다.');
                window.location.reload();
            } else {
                alert('삭제 실패');
            }
        });
    },

    /** 운영회원 저장 */
    saveUser(formData) {
        util.postAJAX('/api/admin/saveInfo', formData + '&f_sort=회원', response => {
            alert(response === 1 ? '저장 완료' : '저장 실패');
            location.reload();
        });
    },
    
    /** 관리자 저장 */
    saveAdmin(formData) {
        util.postAJAX('/api/admin/saveInfo', formData + '&f_sort=관리자', response => {
            alert(response === 1 ? '저장 완료' : '저장 실패');
            location.reload();
        });
    }
};

// 초기화
document.addEventListener("DOMContentLoaded", () => Manage.init());
