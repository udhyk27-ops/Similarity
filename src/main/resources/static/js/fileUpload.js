const fileUpload = {
    enablePreview: false, // 이미지 미리보기 활성화 여부

    init: function (options = {}) {
        this.enablePreview = options.enablePreview || false;
    },
    chkSize : function (){
        const files = Array.from(fileInput.files);
        let chkSizeFiles = [];

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

        fileUpload.renderFileList();
    }
}