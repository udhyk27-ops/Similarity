flatpickr(".flatpickr", {
    locale: 'ko',
    dateFormat: "Y-m-d",
});

$('.reg-list-row').on('click', function() {

   $.ajax({
      url: '/api/admin/searchProduct',
      type: 'GET',
      data: {
         contestNo: $(this).data('contest-no')
      },
      success: function(response) {
          const product = response[0];

          $('#work-code').text(product.f_code);
          $('#work-title').val(product.f_title);
          $('#work-contest').val(product.f_contest);
          $('#work-author').val(product.f_author);
          $('#work-award').val(product.f_award);
          $('#work-host').val(product.f_city);
          $('#work-manager').val(product.f_nation);
          $('#work-year').val(product.f_year);
          $('#work-regdate').text(product.f_reg_date);
          $('#work-name').text(product.f_name + "(" + product.f_id + ")");
          $('#work-dept').text(product.f_dept);
          $('#work-img').attr('src', product.f_path);
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
    $tb.find('input').eq(5).val(mainAddress);
    $tb.find('input').eq(6).val(subAddress);
});