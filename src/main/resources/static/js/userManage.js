import { getAJAX, postAJAX, openPostcode } from "./utils.js";

const UserModule = {
    init() {
        this.bindEvents();
    },

    bindEvents() {
        // 제목 클릭 → 목록으로 이동
        $('.title span').on('click', () => {
            window.location.href = '/manage/user';
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

        // 우편번호 찾기
        $('.post-btn').on('click', () => {
            openPostcode(addr => {
                $('#sample4_postcode').val(addr.zonecode);
                $('#sample4_roadAddress').val(addr.roadAddr);
                $('#sample4_extraAddress').val(addr.extraRoadAddr);
            });
        });

        // 삭제 버튼
        $('.del-btn').on('click', () => this.deleteUser());

        // 저장 버튼 (아직 로직 없음 → 비워둠)
        $('.save-btn').on('click', () => {
            console.log("저장 버튼 클릭됨. 로직 추가 예정.");
        });
    },

    fillUserInfo(user) {
        $('#user-id').val(user.f_id);
        $('#user-name').val(user.f_name);
        $('#user-phone').val(user.f_phone);
        $('#user-email').val(user.f_email);
        $('#user-dept').val(user.f_dept);
        $('#user-position').val(user.f_position);
        $('#sample4_roadAddress').val(user.f_main_address);
        $('#sample4_extraAddress').val(user.f_sub_address);
        $('#user-login').val(user.f_login_date);
        $('#user-modify').val(user.f_mod_date);
        $('#user-memo').text(user.f_memo);
        $('#user-no').val(user.f_user_no);

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

        postAJAX(
            '/api/admin/deleteUser',
            { sort: '회원', userNo },
            response => {
                if (response === 1) {
                    alert('삭제되었습니다.');
                    window.location.reload();
                } else {
                    alert('삭제 실패');
                }
            }
        );
    }
};

document.addEventListener("DOMContentLoaded", () => UserModule.init());
