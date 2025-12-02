import { getJSON, postWithCSRF, openPostcode } from "./utils.js";

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

            getJSON(
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

        postWithCSRF(
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

// $('.title span').on('click', function() {
//     window.location.href = '/manage/user';
// });
//
//
// $('.reg-list-row').on('click', function() {
//
//     $.ajax({
//         url: '/api/admin/searchUser',
//         type: 'GET',
//         data: {
//             userNo: $(this).data('user-no')
//         },
//         success: function(response) {
//             const user = response[0];
//
//             $('#user-id').val(user.f_id);
//             $('#user-name').val(user.f_name);
//             $('#user-phone').val(user.f_phone);
//             $('#user-email').val(user.f_email);
//             $('#user-dept').val(user.f_dept);
//             $('#user-position').val(user.f_position);
//             $('#sample4_roadAddress').val(user.f_main_address);
//             $('#sample4_extraAddress').val(user.f_sub_address);
//             $('#user-login').val(user.f_login_date);
//             $('#user-modify').val(user.f_mod_date);
//             $('#user-memo').text(user.f_memo);
//             $('#user-no').val(user.f_user_no);
//
//             if (user.f_status === "N") {
//                 $("#use").prop("checked", true);
//                 $("#stop").prop("checked", false);
//             } else if (user.f_status === "Y") {
//                 $("#use").prop("checked", false);
//                 $("#stop").prop("checked", true);
//             }
//         },
//         error: function(error) {
//             console.error(error);
//         }
//     });
// });
//
// // 우편번호 API
// function sample4_execDaumPostcode() {
//     new daum.Postcode({
//         oncomplete: function (data) {
//             var roadAddr = data.roadAddress; // 도로명 주소 변수
//             var extraRoadAddr = ''; // 참고 항목 변수
//             if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
//                 extraRoadAddr += data.bname;
//             }
//             if (data.buildingName !== '' && data.apartment === 'Y') {
//                 extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
//             }
//             if (extraRoadAddr !== '') {
//                 extraRoadAddr = ' (' + extraRoadAddr + ')';
//             }
//
//             document.getElementById('sample4_postcode').value = data.zonecode;
//             document.getElementById("sample4_roadAddress").value = roadAddr;
//
//             if (roadAddr !== '') {
//                 document.getElementById("sample4_extraAddress").value = extraRoadAddr;
//             } else {
//                 document.getElementById("sample4_extraAddress").value = '';
//             }
//
//             var guideTextBox = document.getElementById("guide");
//             if (data.autoRoadAddress) {
//                 var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
//                 guideTextBox.innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';
//                 guideTextBox.style.display = 'block';
//
//             } else if (data.autoJibunAddress) {
//                 var expJibunAddr = data.autoJibunAddress;
//                 guideTextBox.innerHTML = '(예상 지번 주소 : ' + expJibunAddr + ')';
//                 guideTextBox.style.display = 'block';
//             } else {
//                 guideTextBox.innerHTML = '';
//                 guideTextBox.style.display = 'none';
//             }
//         }
//     }).open();
// }
//
// $('.post-btn').on('click', function () {
//     sample4_execDaumPostcode();
// });
//
// // 삭제 버튼 클릭
// $('.del-btn').on('click', function() {
//     const token = $("meta[name='_csrf']").attr("content");
//     const header = $("meta[name='_csrf_header']").attr("content");
//     let userNo = $('#user-no').val();
//
//     if (userNo) {
//         $.ajax({
//             url: '/api/admin/deleteUser',
//             type: 'POST',
//             data: {
//                 sort: '회원',
//                 userNo: userNo
//             },
//             beforeSend: function (xhr) {
//                 xhr.setRequestHeader(header, token);
//             },
//             success: function(response) {
//                 if (response === 1) {
//                     alert('삭제되었습니다.');
//                     window.location.reload();
//                 } else {
//                     alert('삭제 실패');
//                 }
//
//             }, error: function(error) {
//                 console.error(error);
//             }
//         })
//
//     } else {
//         alert('회원을 선택해주세요.');
//     }
//
// });
//
// // 작품 등록 버튼
// $('.save-btn').on('click', function() {
//
// });