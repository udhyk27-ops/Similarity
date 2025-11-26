$(document).ready(function () {
    const dropArea = $('.upload-box');
    const fileInput = $('#fileInput');
    const statusText = $('.status-text');
    const allowedExt = ['png', 'jpg', 'jpeg'];

    function handleFile(file) {
        const ext = file.name.split('.').pop().toLowerCase();

        // 실패 처리
        if (!allowedExt.includes(ext)) {
            $('.fileImg').attr('src', '/images/file_error.png');
            $('.upload-text').hide();
            $('.status-text').text('파일 업로드 실패').css('color','red').show();
            return;
        }

        // 성공 처리
        $('.fileImg').attr('src', '/images/file.png');
        $('.upload-text').hide();
        $('.status-text').text('파일 업로드 성공').css('color','green').show();

        // 업로드 영역 업데이트
        const reader = new FileReader();
        reader.onload = function(e) {
            // 이미지 미리보기
            $('.img-div .upload-img').attr('src', e.target.result);

            // 파일 정보 텍스트
            const fileSizeKB = (file.size / 1024).toFixed(1); // kb 단위
            const img = new Image();
            img.onload = function() {
                const width = img.width;
                const height = img.height;
                const infoText = `파일명 : ${file.name}\n이미지 사이즈: ${width}x${height}\n이미지 크기: ${fileSizeKB}kb`;
                $('.text-div pre').text(infoText);
            };
            img.src = e.target.result;
        };
        reader.readAsDataURL(file);
    }

    // 클릭 시 파일 선택
    dropArea.on('click', function() {
        fileInput.click();
    });

    fileInput.on('click', function(e){
        e.stopPropagation(); // 무한 호출 방지
    });

    fileInput.on('change', function() {
        if (this.files.length > 0) handleFile(this.files[0]);
    });

    // 드래그 이벤트
    dropArea.on('dragover', function(e) {
        e.preventDefault();
        e.originalEvent.dataTransfer.dropEffect = 'copy';
        dropArea.addClass('drag-over');
    });

    dropArea.on('dragleave dragend', function(e) {
        dropArea.removeClass('drag-over');
    });

    dropArea.on('drop', function(e) {
        e.preventDefault();
        dropArea.removeClass('drag-over');
        if (e.originalEvent.dataTransfer.files.length > 0) {
            handleFile(e.originalEvent.dataTransfer.files[0]);
        }
    });
});

// image modal
$('.img-div').on('click', function() {
    $('.modal-img').attr('src', $(this).find('img').attr('src'));
    $('#myModal').fadeIn();
});

$(window).on('click', function (e) {
    if ($(e.target).is('#myModal')) {
        $('#myModal').fadeOut();
    }
});