loadTable();
$(document).ready(function(){
	$('#delete-all').on('click',function(){
		multiDelete();
	});	
});

function loadTable(){
	var listUser=[];
	$.ajax({
		url:'/api/users',
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
				listItem.push('<img style="margin:0 5%;cursor:pointer" alt="delete Icon" src="/images/icon_trash.png" width="25px" height="25px" onclick="removeUser('+user.id+')" />' +
						'<a href="/admin/user/edit/' + user.id + '"><img style="margin:0 5%;cursor:pointer" alt="delete Icon" src="/images/icon_edit.png" width="25px" height="25px" /></a>');
				listUser.push(listItem)
			});
			viewOnDataTable(listUser);
		},
		error: function(error){
			console.log(error);
			viewOnDataTable([]);
		}
	});
}

function viewOnDataTable(list){
	$('#datatable-checkbox').dataTable({
		data:list,
		destroy:true
	})
}

function removeUser(userId) {	
	selected = confirm("Do you want delete User have ID : " + userId);
	if(selected){
		$.ajax({
			url: '/api/users/'+ userId,
			type:'delete',
			complete: function(res){
			if(res.status===200){
				console.log("hihihi");
				showMessage('notification',res.responseText,true);
			}else{
				showMessage('notification',res.responseText,false);
			}
			loadTable();
		}
		});
	}
}



function deleteAll(){
	$('.checkbox-delete:checkbox:checked').each(function(i,element){
		console.log($(element).val());
	})
}

function showMessage(tagId,msg,isSuccess){
	console.log('showmessage' + msg);
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
       $('#'+tagId).fadeOut(500);
   }, 1500);
}
$(function(){
	$('#btnAddUser').click(function(e){
	    e.preventDefault();
	    $('input').next().remove();		
		addUser();
	});
});
function addUser() {	
	var userInfo = $('#form-add-user').serializeJSON({parseBooleans: true});
	var jsonUserInfo = JSON.stringify(userInfo);

	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : '/api/users',
		dataType : 'json',
		data : jsonUserInfo,
		complete : function(response) {
			if(response.status===200){
				showMessage('notification-addUser',response.responseText, true);
			}else if(response.status===400){
				console.log(response);
                $.each(response.responseJSON.errorMessages,function(key,value){
    	            $('input[name='+key+']').after('<span style="color: red;">'+value+'</span>');
                });
			}else if(response.status===409){
				showMessage('notification-addUser',response.responseText, false);
			}			
		}
	}); 
}

function multiDelete(){
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
			url:'/api/users',
			type:'DELETE',
			contentType : 'application/json; charset=utf-8',
			data: JSON.stringify(listId),
			complete: function(res){
				if(res.status===200){
					showMessage('notification',res.responseText,true);
					loadTable();
				}else{
					showMessage('notification',res.responseText,false);
				}
			}
		});
	}
}