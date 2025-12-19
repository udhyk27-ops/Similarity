import { getAJAX } from "./utils.js";

const Similar = {
    init() {
        this.setEvents();
    },

    setEvents() {
        this.setupUploadBox();
        this.setupImageModal();

        const reset = document.querySelector('#reset-btn');
        reset.addEventListener('click', () => {

            ['.status-text', '#upload-img-div', '.match-cnt', '.sec2-2']
                .forEach(s => document.querySelector(s).style.display = 'none');

            document.querySelectorAll('.upload-text')
                .forEach(el => el.style.display = 'block');

            document.querySelector('.fileImg').src = '/images/add-file.png';
        });
    },

    // 이미지 업로드
    setupUploadBox() {
        const dropArea = $('.upload-box');
        const fileInput = $('#fileInput');
        const allowedExt = ['png', 'jpg', 'jpeg'];

        const handleFile = (file) => {
            if (!file || !file.name) {
                $('.status-text').text('유효하지 않은 파일입니다.').css('color','red').show();
                return;
            }
            const ext = file.name.split('.').pop().toLowerCase(); // 확장자

            // 업로드 실패
            if (!allowedExt.includes(ext)) {
                $('.fileImg').attr('src', '/images/file_error.png');
                $('.upload-text').hide();
                $('.status-text').text('파일 업로드 실패').css('color','red').show();
                return;
            }

            // 유사도 AJAX
            const filename = file.name;
            getAJAX('/api/admin/compareImage', { filename }, response => {
                const cntEl = document.querySelector('.match-cnt');
                const sec2El = document.querySelector('.sec2-2');
                const imgEl = document.querySelector('.sec2-2 img');
                const textEl = document.querySelector('.match-result-text');

                if (!response) {
                    cntEl.textContent = '매칭결과: 1건';
                    imgEl.src = '/images/ocean.png';
                    textEl.textContent = '작품코드 : EMC558202\n푸른 일렁임\n최서아\n청년미술제\n장려상';
                } else {
                    cntEl.textContent = '매칭결과: 1건';
                    imgEl.src = response.f_filepath;
                    textEl.textContent =
                        `작품코드: ${response.f_code}
${response.f_title}
${response.f_author}
${response.f_contest}
${response.f_award}`;
                }

                cntEl.style.display = 'block';
                sec2El.style.display = 'block';
            });
            
            // 업로드 성공
            const element = document.getElementById('upload-img-div');
            if (element.style.display === 'none') element.style.display = 'block';
            

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

                    $('.upload-img-text pre').text(infoText);
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

    // 이미지 클릭 모달
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

document.addEventListener('DOMContentLoaded', () => Similar.init());
