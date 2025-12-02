const SimilarModule = {
    init() {
        this.setEvents();
    },

    setEvents() {
        this.setupUploadBox();
        this.setupImageModal();
    },

    setupUploadBox() {
        const dropArea = $('.upload-box');
        const fileInput = $('#fileInput');
        const allowedExt = ['png', 'jpg', 'jpeg'];

        const handleFile = (file) => {
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

            const reader = new FileReader();
            reader.onload = function(e) {
                $('.img-div .upload-img').attr('src', e.target.result);

                const fileSizeKB = (file.size / 1024).toFixed(1);
                const img = new Image();

                img.onload = function() {
                    const width = img.width;
                    const height = img.height;
                    const infoText =
                        `파일명 : ${file.name}\n이미지 사이즈: ${width}x${height}\n이미지 크기: ${fileSizeKB}kb`;

                    $('.text-div pre').text(infoText);
                };

                img.src = e.target.result;
            };

            reader.readAsDataURL(file);
        };

        // 클릭 시 fileInput 실행
        dropArea.on('click', () => fileInput.click());
        fileInput.on('click', e => e.stopPropagation());
        fileInput.on('change', function() {
            if (this.files.length > 0) handleFile(this.files[0]);
        });

        // 드래그 이벤트
        dropArea.on('dragover', e => {
            e.preventDefault();
            dropArea.addClass('drag-over');
        });

        dropArea.on('dragleave dragend', () => dropArea.removeClass('drag-over'));

        dropArea.on('drop', e => {
            e.preventDefault();
            dropArea.removeClass('drag-over');

            if (e.originalEvent.dataTransfer.files.length > 0) {
                handleFile(e.originalEvent.dataTransfer.files[0]);
            }
        });
    },

    setupImageModal() {
        $('.img-div').on('click', function() {
            $('.modal-img').attr('src', $(this).find('img').attr('src'));
            $('#myModal').fadeIn();
        });

        $(window).on('click', function(e) {
            if ($(e.target).is('#myModal')) $('#myModal').fadeOut();
        });
    }
};

document.addEventListener('DOMContentLoaded', () => SimilarModule.init());

export default SimilarModule;
