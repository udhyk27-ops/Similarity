/**
 * 대량등록, 동기화 관련
 * */
const syncExcelData = {
    parseExcelFile : function(file) {
        const reader = new FileReader();

        reader.onload = (e) => {
            const data = e.target.result;
            const workbook = XLSX.read(data, {type: "array"});
            const sheetName = workbook.SheetNames[0];
            const workSheet = workbook.Sheets[sheetName];
            // 첫 번째 행을 헤더로 지정(옵션) + 데이터를 json으로
            const jsonList = XLSX.utils.sheet_to_json(workSheet, {header: 1});
            workDataList = jsonList.slice(1);

            // 데이터 뿌리기
            this.renderList(jsonList, "sync");

        }
        reader.onerror = (e) => {
            console.error("파일 읽기 오류", e);
            alert("파일을 읽는 도중 오류가 발생했습니다");
        }
        reader.readAsArrayBuffer(file);
    },
    renderList : function (data, flag=""){
        const fileCnt = document.getElementById("fileCnt");
        const excelDataList = document.getElementById("excelDataList");
        const type = document.getElementById('type').value;
        const colspan = (type === 'award') ? 12 : 8;

        let html = ""
        const rows = workDataList.length > 0 ? workDataList : data.slice(1);
        if(flag === "sync"){
            const chkText = "저작물사이즈(너비, 높이)";
            const fileContainer = document.getElementById("fileListContainer");
            if ((type === "invit" && data[0][4] !== chkText) || (type === "award" && data[0][6] !== chkText)) {
                alert("양식에 맞는 파일을 첨부해주세요");
                fileContainer.remove();
                excelDataList.innerHTML = "";
                return;
            }
        }

        if(!data || data.length === 0 || rows.length === 0){
            html += `<tr>
                        <td colspan="${colspan}">해당하는 데이터가 없습니다</td>
                     </tr>`
        }

        const totalCnt = rows.length;
        rows.forEach((row, i) => {
            const rowNum = totalCnt - i;
            html += ` <tr data-index="${i}">`
            html += `<td><input class="input_chk checkbox" type="checkbox" name="list_check[]" value="${i}"></td>`;
            html += `<td>${rowNum}</td>`;
            row.forEach(cell => {
                const cellVal = (cell !== undefined && cell !== null) ? cell : "";
                html += `<td>${cellVal}</td>`;
            });
            html += `<td><button data-index="${i}" class="btn-pink singleDelBtn" onclick="syncExcelData.deleteRow(${i})">X</button></td>`
            html += `</tr>`;
        });
        fileCnt.innerHTML = totalCnt;
        excelDataList.innerHTML = html;

    },
    deleteRow : function(index){
        if(index >= 0 && index < workDataList.length){
            workDataList.splice(index, 1);
        }
        this.renderList(workDataList);
    },
    deleteSelectedRows : function(){
        const chkboxList = document.querySelectorAll(".checkbox:checked");
        if(chkboxList.length === 0){
            alert("삭제할 항목을 선택해주세요");
            return
        }
        // 내림차순 정렬(인덱스 변경 오류 방지)
        const selectedRows = Array.from(chkboxList)
            .map(el => parseInt(el.value, 10))
            .sort((a, b) => b - a);
        selectedRows.forEach(index => this.deleteRow(index));
    },
    sendData : function(){

        const chkboxList = document.querySelectorAll(".checkbox:not(:checked)");

        const selectedRows = Array.from(chkboxList)
            .map(el => parseInt(el.value, 10))
            .sort((a, b) => b - a);
        selectedRows.forEach(index => this.deleteRow(index));

        const type = document.getElementById('type').value;
        if(workDataList.length === 0){
            alert("등록할 데이터를 선택해주세요. \n데이터가 없을 경우 메타정보 업로드와 동기화를 진행해주세요")
            return false;
        }
        if(photoInput.files.length === 0){
            if(!confirm("사진 파일이 없습니다. 등록하시겠습니까?")) return false;
        }
        const data = this.mapData(workDataList);

        const formData = new FormData();
        formData.append("data", JSON.stringify(data));
        for (let photo of photoInput.files) {
            formData.append("photos", photo);
        }

        const url = `/worklist/bulk/${type}`;
        const token = document.querySelector('meta[name="_csrf"]').content;
        const header = document.querySelector('meta[name="_csrf_header"]').content;
        fetch(url, {
            method: 'POST',
            headers: {
                [header]: token
            },
            body: formData
        }).then(res => res.json())
            .then(data => {
                alert(data.msg);
                if(data.status){
                    location.reload();
                }
            })
    },
    mapData : function(data){
        const type = document.getElementById('type').value;
        let headers = [];
        let chkNum = 0;
        if(type == 'award'){
            headers = ["f_filename", "f_contest", "f_author", "f_award", "f_title", "f_year", "f_work_size", "f_host", "f_manager"];
            chkNum = 6;
        }else{
            headers = ["f_filename", "f_author", "f_title", "f_year", "f_work_size"];
            chkNum = 4;
        }
        const mapData = data.map(rows => {
            let obj = {};
            rows.forEach((cell, i) => {
                if(i == chkNum) cell = cell.replace(/, /g, "x");
                if(headers[i]) obj[headers[i]] = cell;
            });
            return obj;
        });
        return mapData;
    }


}
