import * as util from "./utils.js";

const UserAdminModule = {
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
        if ($('.save-btn.auth').length) {
            $('.save-btn.auth').on('click', () => this.saveAuth());
        } else {
            $('.save-btn').on('click', () => {
                console.log("저장 버튼 클릭됨. 주소 저장 등 로직 추가 예정.");
            });
        }

        // 엑셀 저장
        $('.excel-div .cell-btn').on('click', () => {
            const sort = $('body').data('page-type') === 'user' ? '회원' : '관리자';
            util.excel({
                header: '.reg-list-header .cell',
                row: '.reg-list-row',
                fileName: sort + '목록.xlsx'
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
        $('#user-login').val(user.f_login_date);
        $('#user-modify').val(user.f_mod_date);
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
        if (user.f_status === "N") {
            $("#use").prop("checked", true);
            $("#stop").prop("checked", false);
        } else if (user.f_status === "Y") {
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

    /** 저장 */
    saveAuth() {
        const sort = $('body').data('page-type') === 'user' ? '회원' : '관리자';
        const userNo = $('#user-no').val();
        if (!userNo) return alert('대상을 선택해주세요.');

        const authArr = $('input[name="auth"]:checked').map(function() {
            return $(this).next().text().trim();
        }).get();

        util.postAJAX('/api/admin/modifyInfo', { userNo, auth: authArr }, response => {
            if (response === 1) alert('권한 저장 완료');
            else alert('권한 저장 실패');
        });
    }
};

// 초기화
document.addEventListener("DOMContentLoaded", () => UserAdminModule.init());
