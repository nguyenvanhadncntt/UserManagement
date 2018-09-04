var idEdit;
loadTable();

$(document).ready(function(){
	$('#add_role').submit(function(e){
		newRole = $('#add_role').serializeJSON();
		$.ajax({
			type:'post',
			url:'/api/roles',
			contentType : 'application/json; charset=utf-8',
			data: JSON.stringify(newRole),
			complete: function(res){
				if(res.status===200){
					showMessage('alert',res.responseText,true);
					loadTable();
				}else{
					showMessage('alert',res.responseText,false);
				}
			}
		});
		return false;
	});
	$('#edit_role').submit(function(e){
		editRole={
				"id":0,
				"roleName":"",
				"description":"",
		}
		editRole.id=idEdit;
		editRole.roleName = $('#roleNameEdit').val();
		editRole.description = $('#desEdit').val();
		console.log(editRole);
		$.ajax({
			type:'put',
			url:'/api/roles',
			contentType : 'application/json; charset=utf-8',
			data: JSON.stringify(editRole),
			complete: function(res){
				if(res.status===200){
					showMessage('alert_edit',res.responseText,true);
					loadTable();
				}else{
					showMessage('alert_edit',res.responseText,false);
				}
			}
		});
		return false;
	})
});

function loadTable(){
	var listUser=[];
	$.ajax({
		type:'GET',
		url:'/api/roles/groups',
		success: function(data){
			$.each(data,function(i,role){
				listItem=[];
				listItem.push('<input type="checkbox" class="checkbox-delete" value="'+role.id+'" id="check-all" class="flat">');
				listItem.push(role.id);
				listItem.push('<a href="#" onclick="viewUserHasRole('+role.id+')">'+role.roleName+'</a>');
				listItem.push(role.description);
				date = new Date(role.createdAt)
				createdAt = ((date.getMonth()+1)<10?('0'+(date.getMonth()+1)):(date.getMonth()+1))+"/"+(date.getDate()<10?('0'+date.getDate()):date.getDate())+"/"+date.getFullYear()
				listItem.push(createdAt);
				listItem.push('<img style="margin:0 35%;cursor:pointer" alt="delete Icon" src="/images/icon_edit.png" width="25px" height="25px" onclick="showInfor('+role.id+')" />')
				listItem.push('<img style="margin:0 35%;cursor:pointer" alt="delete Icon" src="/images/icon_trash.png" width="25px" height="25px" onclick="removeRole('+role.id+')" />');
				listUser.push(listItem);
			});
			showOnDataTable(listUser);
		},
		error: function(res){
			showOnDataTable(listUser);
		}
	})
}

function showOnDataTable(list){
	$('#datatable-checkbox').dataTable({
		data:list,
		destroy:true
	})
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
function showInfor(roleId){
	idEdit = roleId;	
	$.ajax({
		type:'get',
		url:'/api/roles/'+roleId,
		success: function(data){
			$('#editRoleModel').modal('show');
			$('#id').val(data.id);
			$('#roleNameEdit').val(data.roleName);
			$('#desEdit').val(data.description);
			console.log(data);
		},
		error: function(res){
			console.log(res);
		}
	});
}

function multiDelete(){
	listId = new ListIdWrapper();
	if($('.checkbox-delete:checkbox:checked').length===0){
		alert('Ban nen chon it nhat 1 role de xoa!!!');
		return;
	}
	$('.checkbox-delete:checkbox:checked').each(function(i,element){
		listId.addIdToArray($(element).val());
	});
	selected = confirm('Are you really delete !!!');
	if(selected){
	$.ajax({
		url:'/api/roles',
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

function viewUserHasRole(roleId){
	$.ajax({
		type:'get',
		url:'/api/roles/'+roleId,
		success: function(data){
			console.log(data);
			$('#modelTitle').text('Group Has Role: '+data.roleName);
			listUser = []; 
			data.groupRoles.forEach(function(roleGroup,i){
				list=[];
				list.push(roleGroup.group.name);
				list.push(roleGroup.group.description);
				date = new Date(roleGroup.group.createdAt)
				createdAt = ((date.getMonth()+1)<10?('0'+(date.getMonth()+1)):(date.getMonth()+1))+"/"+(date.getDate()<10?('0'+date.getDate()):date.getDate())+"/"+date.getFullYear()
				list.push(createdAt);
				listUser.push(list);
			}); 
			
			$('#datatable-userHasRole').dataTable({
				data:listUser,
				destroy:true
			})
			$('#userHasRole').modal('show');
		},
		error: function(res){
			console.log(res);
		}
	});
}

function removeRole(roleId){
	selected = confirm('Are you really delete role of system have id: '+roleId);
	if(selected){
		$.ajax({
			type:'delete',
			url: '/api/roles/'+roleId,
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