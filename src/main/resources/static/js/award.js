flatpickr(".flatpickr", {
    locale: 'ko',
    dateFormat: "Y.m.d",
});

$('#openModal').on('click', function () {

    let $template = $('#user-template');

    $.ajax({
        url: '/api/admin/searchUser',
        type: 'GET',
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
                    })

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
