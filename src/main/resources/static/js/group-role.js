loadTable();
var listRoleData;
$(document).ready(function(){
	$('#delete-all').on('click',function(){
		deleteAll();
	});
	
	$('#role-name').keyup(function(){
		$.ajax({
			type:'get',
			url:'/api/groups/'+group_id+'/roles/search-not-in?searchParam='+$(this).val(),
			success: function(data){
				var html='';
				$.each(data,function(i,role){
					html+='<option value="'+role.roleName+'" label="'+role.description+'">';
				});
				$('#listRole').html(html);
				console.log(data);
				listRoleData = data;
			},
			error: function(res){
			}
		});
	});
	
	$('#add_role').submit(function(){
		var id=0;
		$.each(listRoleData,function(i,role){
			if(role.roleName === $('#role-name').val() ){
				id=role.id;
			}
		});
		console.log(id);
		$.ajax({
			type:'post',
			url:'/api/groups/'+group_id+'/roles/'+id,
			contentType : 'application/json; charset=utf-8',
			complete: function(res){
				
				if(res.status===200 || res.status===201){
					showMessage('alert',res.responseText,true);
					loadTable();
					$('#role-name').val('');
				}else{
					showMessage('alert',res.responseText,false);
				}
			}
		});
		return false;
	});
	
});

function loadTable(){
	var listRole=[];
	$.ajax({
		url:'/api/groups/'+group_id+'/roles',
		type:'GET',
		success: function(data){
			$.each(data,function(i,role){
				listItem=[];
				listItem.push('<input type="checkbox" class="checkbox-delete flat" value="'+role.id+'" />')
				listItem.push(role.id);
				listItem.push(role.roleName);
				listItem.push(role.description);
				listItem.push(role.createdAt);
				
				listItem.push('<img style="margin:0% 35%;cursor:pointer" alt="view Icon" src="/images/view-infor.png" width="25px" height="25px" onclick="viewUserInfor('+role.id+')" />')
				listItem.push('<img style="margin:0% 35%;cursor:pointer" alt="delete Icon" src="/images/icon_trash.png" width="25px" height="25px" onclick="removeUser('+role.id+')" />');
				listRole.push(listItem);
			});
			showOnDataTable(listRole);
		},
		error: function(error){
			console.log(error);
			showOnDataTable([]);
		}
	});
}

function showOnDataTable(list){
	$('#datatable-checkbox').dataTable({
		data:list,
		destroy:true,
//		 columnDefs: [
//			    { className: 'text-center', targets: [0,1,2,3,4,5,6,7,8,9] },
//			    { targets: [0,8,9], "orderable": false},
//			  ]
	})
}

function removeUser(roleId){
	
	selected = confirm("Are you really delete User have ID : "+roleId);
	if(selected){
		$.ajax({
			url:'/api/groups/'+group_id+'/roles/'+roleId,
			type:'delete',
			complete: function(res){
				if(res.status===200){
					showMessage('nofitication',res.responseText,true);
				}else{
					showMessage('nofitication',res.responseText,false);
				}
				loadTable();
			}
		});
	}
}

function showMessage(tagId,msg,isSuccess){
	$('#'+tagId).attr('style','display:block');
	if(isSuccess){
		$('#'+tagId).prop('class','alert alert-success');
		$('#'+tagId).html('<strong>Success!!!<strong> '+msg);
		
	}else{
		$('#'+tagId).prop('class','alert alert-danger');
		$('#'+tagId).html('<strong>Fail!!!<strong> '+msg);
	}
	
	$('#'+tagId).fadeIn(50);
    $('#'+tagId).hide().slideDown(500);
    setTimeout(function() {
        $('#'+tagId).fadeOut(1500);
    }, 3000);
}

function viewUserInfor(id){
	
	$.ajax({
		url:'/api/roles/'+id,
		type:'GET',
		complete: function(res){
			if(res.status===200 || res.status === 201){
				console.log(res);
				$('#rolePopup').modal('show');
				$('#id').val(res.responseJSON.id);
				$('#roleName').val(res.responseJSON.roleName);
				$('#description').val(res.responseJSON.description);
				
				$('#createdAt').val(res.responseJSON.createdAt);
				
			}else{
				showMessage('nofitication',res.responseText,false);
			}
		}
	});
}

function deleteAll(){
	listId = new ListIdWrapper();
	if($('.checkbox-delete:checkbox:checked').length===0){
		alert('Ban nen chon it nhat 1 user de xoa');
		return;
	}
	$('.checkbox-delete:checkbox:checked').each(function(i,element){
		listId.addIdToArray($(element).val());
	});
	selected = confirm('Are you really delete !!!');
	if(selected){
	$.ajax({
		url:'/api/groups/'+group_id+'/roles',
		type:'DELETE',
		contentType : 'application/json; charset=utf-8',
		data: JSON.stringify(listId),
		complete: function(res){
			if(res.status===200){
				showMessage('nofitication',res.responseText,true);
				loadTable();
			}else{
				showMessage('nofitication',res.responseText,false);
			}
		}
	});
	}
}

