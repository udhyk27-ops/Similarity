$('.title span').on('click', function() {
    window.location.href = '/award';
});


$('.reg-list-row').on('click', function() {
    $.ajax({
        url: '/api/admin/searchUser',
        type: 'GET',
        data: {
            userNo: $(this).data('work-no')
        },
        success: function(response) {
            const work = response[0];

            $('#work-code').text(work.f_code);
            $('#work-title').val(work.f_title);
            $('#work-contest').val(work.f_contest);
            $('#work-author').val(work.f_author);
            $('#work-award').val(work.f_award);
            $('#work-host').val(work.f_city);
            $('#work-manager').val(work.f_nation);
            $('#work-year').val(work.f_year);
            $('#work-regdate').text(work.f_reg_date);
            $('#work-name').text(work.f_name + "(" + work.f_id + ")");
            $('#work-dept').text(work.f_dept);
            $('#work-img').attr('src', work.f_path);
        },
        error: function(error) {
            console.error(error);
        }
    });
});

$('#openModal').on('click', function () {

    let $template = $('#user-template');

    $.ajax({
        url: '/api/admin/searchUser',
        type: 'GET',
        data: {
            sort: $('.select-user').val()
        },
        success: function(data) {
            $('#user-row').empty();

            if (data.length === 0) {
                $('#user-row').append('<div class="row"><div class="cell" style="justify-content: center">등록된 회원이 없습니다.</div></div>');
            } else {
                data.forEach(function(user) {
                    let row = $template.clone().removeAttr('id').show(); // 템플릿 복제
                    row.css('display', 'flex');
                    row.find('.cell').css({
                        'display': 'flex',
                        'justify-content': 'center'
                    });
                    row.find('.f_userNo').text(user.f_userNo);
                    row.find('.f_place').text(user.f_place);
                    row.find('.f_birth').text(user.f_birth);
                    row.find('.f_main_address').text(user.f_main_address);
                    row.find('.f_sub_address').text(user.f_sub_address);

                    row.find('.f_name').text(user.f_name);
                    row.find('.f_id').text(user.f_id);
                    row.find('.f_reg_num').text(user.f_reg_num);
                    row.find('.f_reg_date').text(user.f_reg_date);
                    row.find('.f_phone').text(user.f_phone);
                    row.find('.f_email').text(user.f_email);

                    $('#user-row').append(row);
                });
            }
        },
        error: function(error) {
            console.error(error);
        }

    });

    $('#myModal').fadeIn();
});

$('.close').on('click', function () {
    $('#myModal').fadeOut();
});


// 모달 바깥 클릭 시 닫기
$(window).on('click', function (e) {
    if ($(e.target).is('#myModal')) {
        $('#myModal').fadeOut();
    }
});

// Modal 검색 폼
function doSearch() {
    let option = $('.modal-sch select').val();
    let keyword = $('input[name="modal-keyword"]').val().toLowerCase();

    $('.user-list-tb .row').not('.header-row, #user-template').each(function () {
        let $row = $(this);

        let nameText = $row.find('.f_name').text().toLowerCase();
        let idText = $row.find('.f_id').text().toLowerCase();

        let showRow = false;

        if (!keyword.trim()) {
            showRow = true;
        } else {
            if (!option) {
                if (nameText.includes(keyword) || idText.includes(keyword)) {
                    showRow = true;
                }
            } else if (option === 'op-name') {
                if (nameText.includes(keyword)) showRow = true;
            } else if (option === 'op-id') {
                if (idText.includes(keyword)) showRow = true;
            }
        }
        $row.toggle(showRow);
    });
}

$('.modal-sch .cell-btn').on('click', function () {
    doSearch();
});

$('input[name="modal-keyword"]').on('keypress', function (e) {
    if (e.key === 'Enter') {
        doSearch();
    }
});

// 회원 정보 선택
$('#user-row').on('click', '.cell-btn', function() {
    $('#myModal').fadeOut();

    let $row = $(this).closest('.row'); // 클릭한 버튼의 row

    // 모달 row 데이터 가져오기
    let userNo = $row.find('.f_userNo').text();
    let name = $row.find('.f_name').text();
    let phone = $row.find('.f_phone').text();
    let email = $row.find('.f_email').text();
    let place = $row.find('.f_place').text();
    let birth = $row.find('.f_birth').text();
    let mainAddress = $row.find('.f_main_address').text();
    let subAddress = $row.find('.f_sub_address').text();

    // 뷰 input에 채우기
    let $tb = $('.user-tb');

    $tb.find('input').eq(0).val(place);
    $tb.find('input').eq(1).val(name);
    $tb.find('input').eq(2).val(birth);
    $tb.find('input').eq(3).val(phone);
    $tb.find('input').eq(4).val(email);
    $tb.find('input').eq(6).val(mainAddress);
    $tb.find('input').eq(7).val(subAddress);
    $tb.find('input').eq(8).val(userNo);
});

// 우편번호 API
function sample4_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function (data) {
            var roadAddr = data.roadAddress; // 도로명 주소 변수
            var extraRoadAddr = ''; // 참고 항목 변수
            if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                extraRoadAddr += data.bname;
            }
            if (data.buildingName !== '' && data.apartment === 'Y') {
                extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }
            if (extraRoadAddr !== '') {
                extraRoadAddr = ' (' + extraRoadAddr + ')';
            }

            document.getElementById('sample4_postcode').value = data.zonecode;
            document.getElementById("sample4_roadAddress").value = roadAddr;

            if (roadAddr !== '') {
                document.getElementById("sample4_extraAddress").value = extraRoadAddr;
            } else {
                document.getElementById("sample4_extraAddress").value = '';
            }

            var guideTextBox = document.getElementById("guide");
            if (data.autoRoadAddress) {
                var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
                guideTextBox.innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';
                guideTextBox.style.display = 'block';

            } else if (data.autoJibunAddress) {
                var expJibunAddr = data.autoJibunAddress;
                guideTextBox.innerHTML = '(예상 지번 주소 : ' + expJibunAddr + ')';
                guideTextBox.style.display = 'block';
            } else {
                guideTextBox.innerHTML = '';
                guideTextBox.style.display = 'none';
            }
        }
    }).open();
}

$('.post-btn').on('click', function () {
    sample4_execDaumPostcode();
});


// 삭제 버튼 클릭
$('.del-btn').on('click', function() {
    let $userNo = $('.info-userNo');
    if ($userNo.val() !== '') {
        $.ajax({
            url: '/api/admin/deleteUser',
            type: 'POST',
            data: {
                userNo: $userNo.val()
            },
            success: function(response) {
                if (response === 1) {
                    alert('삭제되었습니다.');
                    window.location.reload();
                } else {
                    alert('삭제 실패');
                }
            }, error: function(error) {
                console.error(error);
            }
        })
    } else {
        alert('선택된 회원이 없습니다.');
    }
});

// 작품 등록 버튼
$('.ins-btn').on('click', function() {

});