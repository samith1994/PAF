/**
 * 
 */
$(document).ready(function() {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});

$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	// Form validation-------------------
	var status = validateItemForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// If valid------------------------
	var type = ($("#hidItemIDSave").val() == "") ? "POST" : "PUT";
	$.ajax(
		{
			url: "fundingAPI",
			type: type,
			data: $("#formItem").serialize(),
			dataType: "text",
			complete: function(response, status) {
				onItemSaveComplete(response.responseText, status);
			}
		});
});

function onItemSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hidItemIDSave").val("");
	$("#formItem")[0].reset();
}

$(document).on("click", ".btnUpdate", function(event) {
	$("#hidItemIDSave").val($(this).data("itemid"));
	$("#Name").val($(this).closest("tr").find('td:eq(0)').text());
	$("#Desc").val($(this).closest("tr").find('td:eq(1)').text());
	$("#date").val($(this).closest("tr").find('td:eq(2)').text());
});


$(document).on("click", ".btnRemove", function(event) {
	$.ajax(
		{
			url: "fundingAPI",
			type: "DELETE",
			data: "fundingID=" + $(this).data("itemid"),
			dataType: "text",
			complete: function(response, status) {
				onItemDeleteComplete(response.responseText, status);
			}
		});
});

function onItemDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		}
		else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	}
	else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	}
	else {
			$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}

}


// CLIENT-MODEL================================================================
function validateItemForm() {
	// CODE
	
	// name
	if ($("#Name").val().trim() == "") {
		return "Insert name.";
	}
	// Description-------------------------------
	if ($("#Desc").val().trim() == "") {
		return "Insert Description.";
	}
	// Date------------------------
	if ($("#date").val().trim() == "") {
		return "Insert date.";
	}
	return true;
}


