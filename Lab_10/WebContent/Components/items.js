$(document).ready(function()
{ 
if ($("#alertSuccess").text().trim() == "") 
 { 
 $("#alertSuccess").hide(); 
 } 
 $("#alertError").hide(); 
}); 
$(document).on("click", "#btnSave", function(event)
{ 
// Clear alerts---------------------
 $("#alertSuccess").text(""); 
 $("#alertSuccess").hide(); 
 $("#alertError").text(""); 
 $("#alertError").hide(); 
// Form validation-------------------
var status = validateItemForm(); 
if (status != true) 
 { 
 $("#alertError").text(status); 
 $("#alertError").show(); 
 return; 
 } 
// If valid------------------------
var type = ($("#hidItemIDSave").val() == "") ? "POST" : "PUT"; 
 $.ajax( 
 { 
 url : "ItemsAPI", 
 type : type, 
 data : $("#formItem").serialize(), 
 dataType : "text", 
 complete : function(response, status) 
 { 
 onItemSaveComplete(response.responseText, status); 
 } 
 }); 
});

function onItemSaveComplete(response, status)
{ 
if (status == "success") 
 { 
 var resultSet = JSON.parse(response); 
 if (resultSet.status.trim() == "success") 
 { 
 $("#alertSuccess").text("Successfully saved."); 
 $("#alertSuccess").show(); 
 $("#divItemsGrid").html(resultSet.data); 
 } else if (resultSet.status.trim() == "error") 
 { 
 $("#alertError").text(resultSet.data); 
 $("#alertError").show(); 
 } 
 } else if (status == "error") 
 { 
 $("#alertError").text("Error while saving."); 
 $("#alertError").show(); 
 } else
 { 
 $("#alertError").text("Unknown error while saving.."); 
 $("#alertError").show(); 
 } 
 $("#hidItemIDSave").val(""); 
 $("#formItem")[0].reset(); 
}

$(document).on("click", ".btnUpdate", function(event)
{ 
$("#hidItemIDSave").val($(this).data("itemid")); 
 $("#itemCode").val($(this).closest("tr").find('td:eq(0)').text()); 
 $("#itemName").val($(this).closest("tr").find('td:eq(1)').text()); 
 $("#itemPrice").val($(this).closest("tr").find('td:eq(2)').text()); 
 $("#itemDesc").val($(this).closest("tr").find('td:eq(3)').text()); 
 $("#itemGroup").val($(this).closest("tr").find('td:eq(3)').text());
});

$(document).on("click", ".btnRemove", function(event)
{ 
 $.ajax( 
 { 
 url : "ItemsAPI", 
 type : "DELETE", 
 data : "itemID=" + $(this).data("itemid"),
 dataType : "text", 
 complete : function(response, status) 
 { 
 onItemDeleteComplete(response.responseText, status); 
 } 
 }); 
});

function onItemDeleteComplete(response, status)
{ 
if (status == "success") 
 { 
 var resultSet = JSON.parse(response); 
 if (resultSet.status.trim() == "success") 
 { 
 $("#alertSuccess").text("Successfully deleted."); 
 $("#alertSuccess").show(); 
 $("#divItemsGrid").html(resultSet.data); 
 } else if (resultSet.status.trim() == "error") 
 { 
 $("#alertError").text(resultSet.data); 
 $("#alertError").show(); 
 } 
 } else if (status == "error") 
 { 
 $("#alertError").text("Error while deleting."); 
 $("#alertError").show(); 
 } else
 { 
 $("#alertError").text("Unknown error while deleting.."); 
 $("#alertError").show(); 
 } 
}
function validateItemForm() 
{ 
// CODE
if ($("#productCode").val().trim() == "") 
 { 
 return "Insert Product Code."; 
 } 
// NAME
if ($("#productName").val().trim() == "") 
 { 
 return "Insert Product Name."; 
 } 9
// PRICE-------------------------------
if ($("#productPrice").val().trim() == "") 
 { 
 return "Insert Product Price."; 
 } 
// is numerical value
var tmpPrice = $("#productPrice").val().trim(); 
if (!$.isNumeric(tmpPrice)) 
 { 
 return "Insert a numerical value for Product Price."; 
 } 
// convert to decimal price
 $("#productPrice").val(parseFloat(tmpPrice).toFixed(2)); 
// DESCRIPTION------------------------
if ($("#productDesc").val().trim() == "") 
 { 
 return "Insert Product Description."; 
 } 
//Group
if ($("#productGroup").val().trim() == "") 
{ 
return "Insert Product Group."; 
} 
return true; 
}
