// 파일 업로드 관련
const fileUpload = {
    enablePreview: false, // 이미지 미리보기 활성화 여부

    init: function (options = {}) {
        this.enablePreview = options.enablePreview || false;
    },
    chkExt : function (type=""){
        const files = Array.from(fileInput.files);

        let chkExtFiles = [];
        if(type === 'img'){
            chkExtFiles = files.filter(file => {
                const ext = file.name.split('.').pop().toLowerCase();
                if (ext !== 'jpg' && ext !== 'jpeg' && ext !== 'png') {
                    alert(`${file.name} : 이미지만 등록 가능합니다.`);
                    fileInput.value = '';
                    return false;
                }
                return true;
            })
        }else {
            chkExtFiles = files.filter(file => {
                const ext = file.name.split('.').pop().toLowerCase();
                if (ext !== 'xls' && ext !== 'xlsx') {
                    alert(`${file.name} : xls, xlsx만 등록 가능합니다.`);
                    fileInput.value = '';
                    return false;
                }
                return true;
            })
        }
        this.chkSize(chkExtFiles);
    },
    chkSize : function (chkExtFiles=[]){
        const files = Array.from(fileInput.files);
        let chkSizeFiles = [];

        if(chkExtFiles.length > 0) {
            chkSizeFiles = chkExtFiles;
        }
        chkSizeFiles = files.filter(file => {
            if (file.size > 50 * 1024 * 1024) {
                alert(`${file.name} : 50MB 이하만 등록 가능합니다.`);
                return false;
            }
            return true;
        });
        const dt = new DataTransfer();
        chkSizeFiles.forEach(f => dt.items.add(f));
        fileInput.files = dt.files;

        this.renderFileList(chkSizeFiles)
    },
    renderFileList : function (files){
        fileList.innerHTML = "";

        let html = "<div class='file-list' id='fileListContainer'>";

        if(files || files.length > 0){
            files.forEach((file, index) => {

                html += `
                <div class="file-item">
            `;

                // 이미지 미리보기가 true + 이미지일 때만 표시
                if (this.enablePreview && file.type.startsWith("image/")) {
                    const url = URL.createObjectURL(file);
                    html += `<img src="${url}" class="preview-img">`;
                }

                html += `
                    <span>${file.name} (${fileUpload.formatSize(file.size)})</span>
                    <a href="#" class="delete-btn" onclick="fileUpload.removeFile(${index}); return false;">X</a>
                </div>
            `;
            });
        }
        html += "</div>";
        fileList.innerHTML = html;
    },
    formatSize : function (size) {


        const units = ["B", "KB", "MB", "GB"];
        let i = 0;

        while (size >= 1024 && i < units.length - 1) {
            size /= 1024;
            i++;
        }
        return size.toFixed(2) + " " + units[i];
    },
    removeFile : function (index) {
        const dt = new DataTransfer();
        const files = Array.from(fileInput.files);

        files.splice(index, 1);

        files.forEach(f => dt.items.add(f));
        fileInput.files = dt.files;

        fileUpload.renderFileList(files);
    }
}

// 대량 등록 사진 파일 업로드

// Handle files
function handleFiles(files) {
    const allowedTypes = ['image/jpg', 'image/jpeg', 'image/png'];
    // const maxSize = 5 * 1024 * 1024; // 5 MB

    if(files.length > 100){
        alert("최대 100장까지만 등록가능합니다");
        return false;
    }
    if (files.length > 0) {
        let validFiles = 0;
        let invalidFiles = 0;

        Array.from(files).forEach(file => {
            if (!allowedTypes.includes(file.type)) {
                showNotification(`${file.name} 올바른 파일 형식이 아닙니다`, 'error');
                invalidFiles++;
                return;
            }

            // if (file.size > maxSize) {
            //     showNotification(`${file.name} is too large. Maximum file size is 5 MB.`, 'error');
            //     invalidFiles++;
            //     return;
            // }

            // Handle valid file
            displayFilePreview(file);
            validFiles++;
        });

        if (validFiles > 0) {
            simulateUpload(validFiles);
            showNotification(`${validFiles} 파일 추가 완료.`, 'success');
        }

        if (invalidFiles > 0) {
            showNotification(`${invalidFiles} 업로드 실패`, 'error');
        }
    }
}

function displayFilePreview(file) {
    const reader = new FileReader();
    const fileId = `file-${Date.now()}-${Math.floor(Math.random() * 1000)}`;

    reader.onload = function(event) {
        const preview = document.createElement('div');
        preview.classList.add('file-preview');
        preview.id = fileId;

        // Format file size
        const fileSize = formatFileSize(file.size);

        // Determine if it's an image or PDF
        const isImage = file.type.startsWith('image/');

        let previewContent;
        if (isImage) {
            previewContent = `<img src="${event.target.result}" class="preview-img">`;
        } else {
            previewContent = `<div class="file-icon">📄</div>`;
        }

        preview.innerHTML = `
          <div class="preview-img-container">
            ${previewContent}
          </div>
          <div class="file-info">
            <div class="file-name">${file.name}</div>
            <div class="file-size">${fileSize}</div>
            <div class="file-actions">
              <button class="remove-btn">삭제</button>
            </div>
          </div>
        `;

        // Add remove functionality
        const removeBtn = preview.querySelector('.remove-btn');
        removeBtn.addEventListener('click', () => {
            removeFile(fileId, file.size);
        });

        filePreviews.appendChild(preview);

        // Update statistics
        fileCount++;
        totalSize += file.size;
        updateDisplay();
    };

    reader.readAsDataURL(file);
}

function removeFile(fileId, size) {
    const fileElement = document.getElementById(fileId);
    if (fileElement) {
        // Animate removal
        fileElement.style.opacity = '0';
        fileElement.style.transform = 'scale(0.9)';

        setTimeout(() => {
            fileElement.remove();

            // Update statistics
            fileCount--;
            totalSize -= size;
            updateDisplay();

            showNotification('파일 삭제중', 'success');
        }, 300);
    }

    resetPhotoInput();
}

function updateDisplay() {
    fileCountEl.textContent = fileCount;
    totalSizeEl.textContent = formatFileSize(totalSize);

    if (fileCount > 0) {
        uploadStats.style.display = 'block';
        progressFill.style.width = '100%';
    } else {
        uploadStats.style.display = 'none';
    }
}

function formatFileSize(bytes) {
    if (bytes === 0) return '0 Bytes';

    const k = 1024;
    const sizes = ['Bytes', 'KB', 'MB', 'GB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));

    return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
}

function showNotification(message, type = 'success') {
    // Clear any existing timeout
    clearTimeout(notification.timeout);

    // Hide notification if it's already showing
    notification.classList.remove('show', 'success', 'error');

    // Set content and show
    document.getElementById('notificationText').textContent = message;
    notification.classList.add('show', type);

    // Reset progress bar
    const progressBar = notification.querySelector('.notification-progress');
    progressBar.style.transition = 'none';
    progressBar.style.transform = 'scaleX(0)';

    // Force reflow to restart animation
    void notification.offsetWidth;

    // Start progress animation
    progressBar.style.transition = 'transform 3s linear';
    progressBar.style.transform = 'scaleX(1)';

    // Auto-hide after 3 seconds
    notification.timeout = setTimeout(() => {
        notification.classList.remove('show');
    }, 3000);
}

function simulateUpload(fileCount) {
    // This would be where you'd actually upload to a server
    // For now we're just showing the progress visually
    progressFill.style.width = '0%';

    setTimeout(() => {
        progressFill.style.width = '30%';

        setTimeout(() => {
            progressFill.style.width = '60%';

            setTimeout(() => {
                progressFill.style.width = '100%';
            }, 200);
        }, 200);
    }, 100);
}

// Reset the file input after a file is removed
function resetPhotoInput() {
    photoInput.value = '';
}

// Initial UI setup
function initUI() {
    if (fileCount === 0) {
        uploadStats.style.display = 'none';
    }
}