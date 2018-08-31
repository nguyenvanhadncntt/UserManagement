loadTable();
$(document).ready(function(){
	$('#delete-all').on('click',function(){
		deleteAll();
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
				listItem.push('<img style="margin:0 35%;cursor:pointer" alt="delete Icon" src="/images/icon_trash.png" width="25px" height="25px" onclick="removeUser('+user.id+')" />');
				listUser.push(listItem);
			});
			viewOnDataTable(listUser);
//			$('#check-all').each(function(index,e){
//				
//			});
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

function removeUser(userId){
	
	selected = confirm("Are you really delete User have ID : " + userId);
	if(selected){
		$.ajax({
			url: '/api/users/'+ userId,
			type:'delete',
			complete: function(res){
			if(res.status===200){
				showMessage(res.responseText,true);
			}else{
				showMessage(res.responseText,false);
			}
			loadTable();
		}
		});
	}
}

function showMessage(msg,isSuccess){
	$('#nofitication').attr('style','display:block');
	if(isSuccess){
		$('#nofitication').prop('class','alert alert-success');
		$('#nofitication').html('<strong>Success!!!<strong> '+msg);
		
	}else{
		$('#nofitication').prop('class','alert alert-danger');
		$('#nofitication').html('<strong>Fail!!!<strong> '+msg);
	}
}

function deleteAll(){
	$('.checkbox-delete:checkbox:checked').each(function(i,element){
		console.log($(element).val());
	})
}

function addUser() {
	var userInfo = $('#form-add-user').serializeJSON({parseBooleans: true});
	var jsonUserInfo = JSON.stringify(userInfo);
	console.log(jsonUserInfo);		
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : '/api/users',
		dataType : 'json',
		data : jsonUserInfo,
		complete : function(response) {
			if(response.status===200){
				showMessageAddUser(response.responseText, true);
			}else{
				showMessageAddUser(response.responseText, false);
			}			
		}
	});
}	

function showMessageAddUser(msg, isSuccess) {
	if(isSuccess){
		$('#notification-addUser').prop('class', 'alert alert-success');
		$('#notification-addUser').html('<strong>Add user sucesss </strong>');
	}else{
		$('#notification-addUser').prop('class', 'alert alert-danger');
		$('#notification-addUser').html('<strong>Fail </strong>' + msg);		
	}
}