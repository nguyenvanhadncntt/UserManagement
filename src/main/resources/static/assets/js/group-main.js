$(function() {
	var $groups = $('#groups');
//	var date = $('#datepicker').datepicker({ dateFormat: 'dd-mm-yy' }).val();
	var list_group = [];
	var list_item =[]
	
	
	$.ajax({
		type: 'GET',
		url: '/api/groups',
		success: function(groups) {
			$.each(groups, function(i, group) {
				list_item=[];
				list_item.push(group.name);
				list_item.push(group.description);
				var d = new Date(group.createdAt);
				var datestring = d.getDate()  + "-" + (d.getMonth()+1) + "-" + d.getFullYear() + " " +
				d.getHours() + ":" + d.getMinutes();
				list_item.push(datestring);
				list_item.push('<a>role</a>');
//				list_item.push('<a>user</a>');
				list_item.push('<a href=/admin/group/'+group.id+'>User in group</a>');
				list_group.push(list_item);
			});
			$('#dataTables-example').dataTable( {
		        data: list_group,
		        columns: [
		            { title: "Name" },
		            { title: "Description" },
		            { title: "Created Time" },
		            { title: "Role." },
		            { title: "User" },
		            
		        ]
		    } );
		},
		error: function(res){
            console.log("khong co ket qua");
            data=[];
        }
	});
	
	$('#add-group').on('click',function(){
		var group = {
			name: $name.val(),
		description: $description.val()
		};
		$.ajax({
			type: 'POST',
			url: '/api/groups',
			data: group,
			success: function(newGroup) {
				
				list_item.push(newGroup.name);
				list_item.push(newGroup.description);
				list_group.push(list_item);
				
				$('#dataTables-example').dataTable( {
			        data: list_group,
			        columns: [
			            { title: "Name" },
			            { title: "Description" },
			            { title: "Created Time" },
			            { title: "Role." },
			            { title: "User" },
			            
			        ]
			    } );
			}
				
		});
		
	});
	 
})