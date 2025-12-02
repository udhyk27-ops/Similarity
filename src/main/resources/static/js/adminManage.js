import { getJSON, postWithCSRF } from "./utils.js";

const AdminModule = {
    init() {
        this.bindEvents();
    },

    bindEvents() {
        // 제목 클릭 → 목록으로 이동
        $('.title span').on('click', () => {
            window.location.href = '/manage/admin';
        });

        // 리스트에서 회원 클릭 시 정보 로드
        $('.reg-list-row').on('click', e => {
            const userNo = $(e.currentTarget).data('user-no');
            if (!userNo) return;

            getJSON(
                '/api/admin/searchUser',
                { userNo },
                response => this.fillUserInfo(response[0])
            );
        });

        // 삭제 버튼
        $('.del-btn').on('click', () => this.deleteUser());

        // 저장 버튼 (권한 체크박스 처리)
        $('.save-btn').on('click', () => this.saveAuth());
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

        // 권한 체크박스 초기화
        $('input[name="auth"]').each(function() {
            $(this).prop('checked', false);
        });

        if (user.f_auth) {
            // 예: f_auth가 ['수상작 등록관리', '유사도 검색'] 배열이라면 체크
            user.f_auth.forEach(authName => {
                $(`input[name="auth"][value="${authName}"]`).prop('checked', true);
            });
        }

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
        const userNo = $('#user-no').val();
        if (!userNo) return alert('회원을 선택해주세요.');

        postWithCSRF(
            '/api/admin/deleteUser',
            { sort: '관리자', userNo },
            response => {
                if (response === 1) {
                    alert('삭제되었습니다.');
                    window.location.reload();
                } else {
                    alert('삭제 실패');
                }
            }
        );
    },

    /** 권한 저장 */
    saveAuth() {
        const userNo = $('#user-no').val();
        if (!userNo) return alert('회원을 선택해주세요.');

        // 체크된 권한 배열
        const authArr = $('input[name="auth"]:checked').map(function() {
            return $(this).next().text().trim(); // 체크박스 옆 텍스트
        }).get();

        postWithCSRF(
            '/api/admin/saveAuth',
            { userNo, auth: authArr },
            response => {
                if (response === 1) {
                    alert('권한 저장 완료');
                } else {
                    alert('권한 저장 실패');
                }
            }
        );
    }
};

document.addEventListener("DOMContentLoaded", () => AdminModule.init());
