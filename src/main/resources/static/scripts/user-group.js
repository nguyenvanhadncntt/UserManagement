loadTable();
$(window).ready(function(){
	$('#delete-all').on('click',function(){
		deleteAll();
	});
	
	console.log($('.iCheck-helper'));
	
	$('#email').keyup(function(){
		$.ajax({
			type:'get',
			url:'/api/groups/'+group_id+'/users/search-not-in?searchParam='+$(this).val(),
			success: function(data){
				var html='';
				$.each(data,function(i,user){
					html+='<option value="'+user.email+'" label="'+user.fullname+'">';
				});
				$('#listUser').html(html);
			},
			error: function(res){
			}
		});
	});
	
	$('#add_user').submit(function(e){
		email = {
					"email":""
				};
		email.email = $('#email').val();
		$.ajax({
			type:'post',
			url:'/api/groups/'+group_id+'/users',
			contentType : 'application/json; charset=utf-8',
			data: JSON.stringify(email),
			complete: function(res){
				if(res.status===200){
					showMessage('alert',res.responseText,true);
					loadTable();
					$('#email').val('');
				}else{
					showMessage('alert',res.responseText,false);
				}
			}
		});
		return false;
		
		
	});
	
});

function loadTable(){
	var listUser=[];
	$.ajax({
		url:'/api/groups/'+group_id+'/users',
		type:'GET',
		success: function(data){
			$.each(data,function(i,user){
				listItem=[];
				listItem.push('<input type="checkbox" class="checkbox-delete" value="'+user.id+'" id="check-all" class="flat">');
				listItem.push(user.id);
				listItem.push(user.email);
				listItem.push(user.fullname);
				listItem.push(user.birthday);
				listItem.push((user.gender===true)?'Female':'Male');
				listItem.push(user.address);
				listItem.push(user.phone);
				listItem.push('<img style="margin:20% 35%;cursor:pointer" alt="delete Icon" src="/images/view-infor.png" width="25px" height="25px" onclick="viewUserInfor('+user.id+')" />')
				listItem.push('<img style="margin:20% 35%;cursor:pointer" alt="delete Icon" src="/images/icon_trash.png" width="25px" height="25px" onclick="removeUser('+user.id+')" />');
				listUser.push(listItem);
			});
			showOnDataTable(listUser);
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
		 columnDefs: [
			    { className: 'text-center', targets: [0,1,2,3,4,5,6,7,8,9] },
			    { targets: [0,8,9], "orderable": false},
			  ]
	})
}

function removeUser(userId){
	
	selected = confirm("Are you really delete User have ID : "+userId);
	if(selected){
		$.ajax({
			url:'/api/groups/'+group_id+'/users/'+userId,
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
		url:'/api/groups/'+group_id+'/users/'+id,
		type:'GET',
		complete: function(res){
			if(res.status===200){
				console.log(res);
				$('#userPopup').modal('show');
				$('#id').val(res.responseJSON.id);
				$('#fullName').val(res.responseJSON.fullname);
				$('#emailshow').val(res.responseJSON.email);
				$('#block').val(res.responseJSON.nonLocked);
				$('#birthday').val(res.responseJSON.birthday);
				$('#createdAt').val(res.responseJSON.createdAt);
				if(res.responseJSON.gender === true){
					console.log($('#female'))
					$('#female').prop('checked',true);
				}else{
					console.log('male')
					$('#male').prop('checked',true);
				}
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
		url:'/api/groups/'+group_id+'/users',
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
