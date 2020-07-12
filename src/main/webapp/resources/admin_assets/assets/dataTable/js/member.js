/*  // 표시 건수기능 숨기기
		 lengthChange: true,
		 // 검색 기능 숨기기
		 searching: false,
		 // 정렬 기능 숨기기
		 ordering: false,
		 // 정보 표시 숨기기
		 info: false,
		 // 페이징 기능 숨기기
		 paging: true,
		 // pagingType: scrolling */

$(document).ready(function () {


    var table = $('#myTable').DataTable({
        /*ajax: {
            'url':'MOCK_DATA.json', 
            //'type': 'POST',
            'dataSrc':''
        },*/
        responsive: true,
        orderMulti: true,
        order : [[1, 'desc']],
        "language": {
            "emptyTable": "데이터가 없어요.",
            "lengthMenu": "페이지당 _MENU_ 개씩 보기",
            "info": "현재 _START_ - _END_ / _TOTAL_건",
            "infoEmpty": "데이터 없음",
            "infoFiltered": "( _MAX_건의 데이터에서 필터링됨 )",
            "search": "에서 검색: ",
            "zeroRecords": "일치하는 데이터가 없어요.",
            "loadingRecords": "로딩중...",
            "processing":     "잠시만 기다려 주세요...",
            "paginate": {
                "next": "다음",
                "previous": "이전"
            }
        },
        "pageLength": 10,
        "lengthMenu": [[3, 5, 10, -1], [3, 5, 10, "All"]],
        dom : 'Blfrtip',
        buttons:[{
			extend:'csvHtml5',
			text: 'excel',
			footer: true,
			bom: true,
			className: 'excel',
			action: function (e, dt, node, config)
			{
				//This will send the page to the location specified
				location.href = 'viewMemberExcel.do';
			}
		},
		{extend:'csvHtml5',
			text: 'pdf',
			footer: true,
			bom: true,
			className: 'pdf',
			action: function (e, dt, node, config)
			{
				  //This will send the page to the location specified
				  window.location.href = 'viewMemberPdf.do';
			}
		},
		{extend:'csvHtml5',
			text: '전체 쪽지',
			footer: true,
			bom: true,
			className: 'message',
			action: function (e, dt, node, config)
			{
				  //This will send the page to the location specified
				  window.location.href = '#';
			}
		}
		]
    });

    /* Column별 검색기능 추가 */
    $('#myTable_filter').prepend('<select id="select"></select>');
    $('#myTable > thead > tr').children().each(function (indexInArray, valueOfElement) { 
        if(valueOfElement.innerHTML != '정지버튼'){
        	$('#select').append('<option class=visibility:hidden;>'+valueOfElement.innerHTML+'</option>');
        }
    });
    
    
    $('.dataTables_filter input').unbind().bind('keyup', function () {
        var colIndex = document.querySelector('#select').selectedIndex;
        table.column(colIndex).search(this.value).draw();
    });
    
	
	$(".message").attr("data-toggle", "modal");
	$(".message").attr("data-target", "#messagePageModal");


});