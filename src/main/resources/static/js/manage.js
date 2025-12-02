import { getAJAX, postAJAX, openPostcode } from "./utils.js";

const UserAdminModule = {
    init() {
        this.bindEvents();
    },

    bindEvents() {
        // 제목 클릭 → 목록으로 이동
        $('.title span').on('click', () => {
            const pageType = $('body').data('page-type'); // body 태그에 data-page-type="user" 또는 "admin"
            window.location.href = `/manage/${pageType}`;
        });

        // 리스트에서 회원 클릭 시 정보 로드
        $('.reg-list-row').on('click', e => {
            const userNo = $(e.currentTarget).data('user-no');
            if (!userNo) return;

            getAJAX(
                '/api/admin/searchUser',
                { userNo },
                response => this.fillUserInfo(response[0])
            );
        });

        // 우편번호 찾기 (User 페이지에서 사용)
        if ($('.post-btn').length) {
            $('.post-btn').on('click', () => {
                openPostcode(addr => {
                    $('#sample4_postcode').val(addr.zonecode);
                    $('#sample4_roadAddress').val(addr.roadAddr);
                    $('#sample4_extraAddress').val(addr.extraRoadAddr);
                });
            });
        }

        // 삭제 버튼
        $('.del-btn').on('click', () => this.deleteUser());

        // 저장 버튼
        if ($('.save-btn.auth').length) {
            $('.save-btn.auth').on('click', () => this.saveAuth());
        } else {
            $('.save-btn').on('click', () => {
                console.log("저장 버튼 클릭됨. 주소 저장 등 로직 추가 예정.");
            });
        }
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

        // 권한 체크박스 초기화 (Admin 페이지)
        if ($('input[name="auth"]').length) {
            $('input[name="auth"]').each(function() {
                $(this).prop('checked', false);
            });

            if (user.f_auth) {
                user.f_auth.forEach(authName => {
                    $(`input[name="auth"][value="${authName}"]`).prop('checked', true);
                });
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
        const userNo = $('#user-no').val();
        if (!userNo) return alert('회원을 선택해주세요.');

        const sort = $('body').data('page-type') === 'admin' ? '관리자' : '회원';

        postAJAX('/api/admin/deleteUser', { sort, userNo }, response => {
            if (response === 1) {
                alert('삭제되었습니다.');
                window.location.reload();
            } else {
                alert('삭제 실패');
            }
        });
    },

    /** 권한 저장 */
    saveAuth() {
        const userNo = $('#user-no').val();
        if (!userNo) return alert('회원을 선택해주세요.');

        const authArr = $('input[name="auth"]:checked').map(function() {
            return $(this).next().text().trim();
        }).get();

        postAJAX('/api/admin/saveAuth', { userNo, auth: authArr }, response => {
            if (response === 1) alert('권한 저장 완료');
            else alert('권한 저장 실패');
        });
    }
};

// 초기화
document.addEventListener("DOMContentLoaded", () => UserAdminModule.init());
