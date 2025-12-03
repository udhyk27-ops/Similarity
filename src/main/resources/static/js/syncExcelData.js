// 대량등록 동기화 관련
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
            this.renderList(jsonList);

        }
        reader.onerror = (e) => {
            console.error("파일 읽기 오류", e);
            alert("파일을 읽는 도중 오류가 발생했습니다");
        }
        reader.readAsArrayBuffer(file);
    },
    renderList : function (data){
        const fileCnt = document.getElementById("fileCnt");
        const excelDataList = document.getElementById("excelDataList");
        const type = document.getElementById('type').value;
        const colspan = type == 'award' ? 12 : 9;

        let html = ""
        const rows = workDataList.length > 0 ? workDataList : data.slice(1);
        if(!data || data.length === 0 || rows.length === 0){
            html += `<tr>
                        <td colspan="${colspan}">데이터가 없습니다</td>
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
        console.log(selectedRows);
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
            alert("등록할 데이터가 없습니다. 작품 메타정보를 업로드하고 동기화 해주세요")
            return;
        }
        const data = this.mapData(workDataList);
        console.log(data);
        const url = `/workList/bulk/${type}`;
        const token = document.querySelector('meta[name="_csrf"]').content;
        const header = document.querySelector('meta[name="_csrf_header"]').content;
        fetch(url, {
            method: 'POST',
            headers: {
                [header]: token,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        }).then(res => res.json())
            .then(data => {
                alert(data.msg);
                if(data.status){
                    workDataList = [];
                    this.renderList(workDataList);
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
            headers = ["f_filename", "f_author", "f_code", "f_title", "f_year", "f_work_size"];
            chkNum = 5;
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
