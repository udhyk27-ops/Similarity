import { getAJAX } from "./utils.js";

const Similar = {
    init() {
        this.setEvents();
    },

    setEvents() {
        this.setupUploadBox();
        this.setupImageModal();
    },

    /**
     * 이미지 업로드
     */
    setupUploadBox() {
        const dropArea = $('.upload-box');
        const fileInput = $('#fileInput');
        const allowedExt = ['png', 'jpg', 'jpeg'];

        const handleFile = (file) => {
            if (!file || !file.name) {
                $('.status-text').text('유효하지 않은 파일입니다.').css('color','red').show();
                return;
            }

            const ext = file.name.split('.').pop().toLowerCase();

            // console.log(ext); // 확장자
            // console.log(file.name); // 파일명

            // 업로드 실패
            if (!allowedExt.includes(ext)) {
                $('.fileImg').attr('src', '/images/file_error.png');
                $('.upload-text').hide();
                $('.status-text').text('파일 업로드 실패').css('color','red').show();
                return;
            }

            // 유사도 AJAX
            const filename = file.name;
            const cnt = document.getElementsByClassName('match-cnt');
            getAJAX('/api/admin/compareImage', { filename }, response => {
                console.log(response);

                if (!response) {
                    document.querySelector('.sec2-2').style.display = 'none';
                    return;
                }

                // 이미지
                const img = document.querySelector('.sec2-2 img');
                img.src = response.f_filepath;

                // 텍스트 구성
                const text =
                    `작품코드: ${response.f_code}
                    ${response.f_title}
                    ${response.f_author}
                    ${response.f_contest}
                    ${response.f_award}`;

                // 텍스트 삽입
                const pre = document.querySelector('.match-result-text');
                pre.textContent = text;

                // 결과 영역 표시
                document.querySelector('.sec2-2').style.display = 'block';

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

    /**
     * 이미지 클릭 모달
     */
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
