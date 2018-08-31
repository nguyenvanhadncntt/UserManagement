$(document).ready(function(){
	loadTable();
});

function loadTable(){
	$.ajax({
		type:'GET',
		url:'',
		success: function(data){
			console.log(data);
		},
		error: function(res){
			console.log(res);
		}
	})
}

function showOnDataTable(list){
	$('#datatable-checkbox').dataTable({
		data:list,
		destroy:true
	})
}