$(document).ready(function () {

    dropArea.on('drop', function(e) {
        e.preventDefault();
        const files = e.originalEvent.dataTransfer.files;
        if (files.length > 0) {
            const file = files[0];
            const ext = file.name.split('.').pop().toLowerCase();

            if (!allowedExt.includes(ext)) {
                $('.fileImg').attr('src', '/assets/fileUpload_Error.png');
                $('.modal-back-1 p').css('display', 'none');
                $('.modal-back-2 p').css('display', 'none');

                return; // 업로드 중단
            }
            uploadFile(file);
        }
    });

    $('#fileInput').on('change', function() {
        const file = this.files[0];
        if (file) {
            const ext = file.name.split('.').pop().toLowerCase();
            if (!allowedExt.includes(ext)) {
                $('.fileImg').attr('src', '/assets/fileUpload_Error.png');
                $('.modal-back-1 p').css('display', 'none');
                $('.modal-back-2 p').css('display', 'none');
                return;
            }
            uploadFile(file);
        }
    });
});


function uploadFile(file) {
    const img = $('.fileImg');
    const p = $('.modal-back-1 p');
    const p2 = $('.modal-back-2 p');

    const formData = new FormData();
    formData.append('file', file);

    $.ajax({
        url: '/Employee/upload_excel',
        type: 'post',
        data: formData,
        dataType: 'json',
        contentType: false,
        processData: false,
        success: function(response) {
            // console.log('응답:', response);

            if (response['status']) {

                alert(response['msg']);

                img.attr('src', '/assets/fileIcon.png');
                text.text(file.name);

                p.css('display', 'none');
                p2.css('display', 'none');
            } else {
                alert(response['msg']);

                p.css('display', 'none');
                p2.css('display', 'none');

                img.attr('src', '/assets/fileUpload_Error.png');
            }
        },
        error: function(error) {
            p.css('display', 'none');
            p2.css('display', 'none');

            // console.log('에러:', error);
            img.attr('src', '/assets/fileUpload_Error.png');
        }
    });
}

$('.img-div').on('click', function() {
    $('.modal-img').attr('src', $(this).find('img').attr('src'));
    $('#myModal').fadeIn();
});

$(window).on('click', function (e) {
    if ($(e.target).is('#myModal')) {
        $('#myModal').fadeOut();
    }
});