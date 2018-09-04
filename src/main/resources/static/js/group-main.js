var list_group = [];
var list_item = [];
var idEdit;
$('.add-compelete').hide();
$('.edit-compelete').hide();
$('.error-add').hide();
$('.error-edit').hide();
$('.delete-compelete').hide();
// var $groups = $('#groups');
// var date = $('#datepicker').datepicker({ dateFormat: 'dd-mm-yy' }).val();

function dataTable(list) {
	$('#dataTables-example').dataTable({
		data : list,
		
		"aaSorting" : [ [ 3, "desc" ] ],
		columns : [ {
			title : 'Check'
		}, {
			title : "Name"
		}, {
			title : "Description"
		}, {
			title : "Created Time"
		}, {
			title : "Role."
		}, {
			title : "User"
		}, {
			title : "Delete"
		}, {
			title : "Info"
		},

		],
		destroy : true,
		 columnDefs: [
			    { targets: [0,4,5,6,7], "orderable": false},
			  ]
	});
}

function getGroups() {
	list_group = [];
	$.ajax({
			type : 'GET',
			url : '/api/groups',
			success : function(groups) {
				$.each(groups,function(i, group) {
						var d = new Date(group.createdAt);
						var getMonth = (d.getMonth() + 1) > 10 ? (d
								.getMonth() + 1)
								: ("0" + (d.getMonth() + 1));
						var getHours = (d.getHours() < 10) ? ("0" + d
								.getHours())
								: (d.getHours());
						var getMinutes = (d.getMinutes() < 10) ? ("0" + d
								.getMinutes())
								: (d.getMinutes());
						console.log("hahah" + getMonth);
						var datestring = d.getDate() + "-"
								+ getMonth + "-"
								+ d.getFullYear() + " "
								+ getHours + ":" + getMinutes;
						list_item = [];
						list_item
								.push('<input type="checkbox" id="check-all" value="'
										+ group.id
										+ '" class="flat">');
						list_item.push(group.name);
						list_item.push(group.description);

						list_item.push(datestring);
						list_item.push('<a>role</a>');
						list_item.push('<a href=/admin/group/'
								+ group.id
								+ '>User in group</a>');
						list_item
								.push('<img alt="" src="/images/icon_trash.png" style="cursor:pointer" onclick="deleteGroup('
										+ group.id
										+ ')" wight="25" height="25" id="deleteGroup">')
						list_item
								.push('<img alt="" src="/images/icon_edit.png" style="cursor:pointer" data-toggle="modal" data-target="#editModal" onclick="viewEditGroup('+group.id+')" wight="25" height="25" id="modalEdit">')
						list_group.unshift(list_item);
					});
				dataTable(list_group);

				},
				error : function(res) {
					
					dataTable([]);
				}
			});
}
function viewEditGroup(id) {
	var URL = '/api/groups/'+id;
	$.ajax({
		url : URL,
		type : 'GET',
		success : function(group) {
			console.log(group);
			$('#form-edit-group').find('input').val(group.name);
			$('#form-edit-group').find('textarea').val(group.description);
		}
	});
	idEdit=id;
}
function doSaveGroup() {
	var URL = "/api/groups/" + idEdit;
	console.log(idEdit);
	var editGroup = $('#form-edit-group').serializeJSON();
	var jsonStringGroup = JSON.stringify(editGroup);

	$.ajax({
		url : URL,
		type : 'PUT',
		contentType : "application/json; charset=utf-8",
		data : jsonStringGroup,
		dataType : 'json',
		complete : function(res) {
			if (res.status == 200 || res.status == 201) {
				console.log($('.edit-compelete').text());
				$('.edit-compelete').fadeIn(100);
				$('.edit-compelete').hide().slideDown(500);
				$('.error-edit').hide();
				setTimeout(function() {
					$('.edit-compelete').fadeOut(1000);
				}, 3000);t(1000);
				getGroups();
			} else {
				if (res.status == 400) {
					$('.error-edit').fadeIn(50);
					$('.error-edit').text(res.responseText);
					$('.error-edit').hide().slideDown(1000);
					setTimeout(function() {
						$('.error-edit').fadeOut(1000);
					}, 3000);
				}
			}
		}
	});
}
function deleteGroup(groupId) {
	var i = confirm("Are you really delete a group?");
	if (i) {
		$.ajax({
			type : 'DELETE',
			url : '/api/groups/' + groupId,
			success : function(groups) {
				$('.delete-compelete').fadeIn(50);
				$('.delete-compelete').text('Deleted success ');
				$('.delete-compelete').hide().slideDown(1000);
				setTimeout(function() {
					$('.delete-compelete').fadeOut(1000);
				}, 3000);
				getGroups();
			},
			error : function(res) {
				console.log(res);
				alert("error delete");
			}
		})
	}
}

$(function() {
	getGroups();

	$('#getCheckBoxButton').on('click', function(event) {
		if ($('input[type="checkbox"]:checked').length == 0) {
			alert("You have not checked");
		} else {
			var i = confirm("Are you really delete a group?");
			if (i) {
				listId = new ListIdWrapper();
				$('input[type="checkbox"]:checked').each(function(index, elem) {
					listId.addIdToArray($(elem).val());
				});

				checkBoxValueJSON = JSON.stringify(listId);
				console.log(checkBoxValueJSON);
				$.ajax({
					type : 'DELETE',
					url : '/api/groups/',
					data : checkBoxValueJSON,
					contentType : "application/json; charset=utf-8",
					success : function(groups) {
						$('.delete-compelete').fadeIn(50);
						$('.delete-compelete').text('Deleted success ');
						$('.delete-compelete').hide().slideDown(1000);
						setTimeout(function() {
							$('.delete-compelete').fadeOut(1000);
						}, 3000);

						getGroups();
					},
					error : function(res) {
						console.log(res);
						alert("error delete");
					}
				})
				console.log($('input[type="checkbox"]:checked').length);

			}
		}
	});

	$('#add-group').click(function() {

		var group = $('#form-add-group').serializeJSON();
		var jsonStringGroup = JSON.stringify(group);
		console.log(group);
		if (false) {
			alert("You must not blank");
		} else {
			$.ajax({
				type : 'POST',
				url : '/api/groups',
				data : jsonStringGroup,
				contentType : "application/json; charset=utf-8",
				complete : function(res) {
					if (res.status == 201) {
						console.log($('.add-compelete').text());
						$('.add-compelete').show(1000);
						$('.add-compelete').hide().slideDown(1000);
						$('.error-add').hide();
						setTimeout(function() {
							$('.add-compelete').fadeOut(1000);
						}, 3000);
						getGroups();
					} else {
						if (res.status == 400) {
							$('.error-add').fadeIn(50);
							$('.error-add').text(res.responseText);
							$('.error-add').hide().slideDown(1000);
							setTimeout(function() {
								$('.error-add').fadeOut(1000);
							}, 3000);
						}
					}
				}
			});
		}
	});

})