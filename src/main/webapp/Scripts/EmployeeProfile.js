document.addEventListener("DOMContentLoaded", function (e) {
  document.getElementById("updateform").addEventListener("submit", function(e) {
         if(confirm("Are you sure ? ") == true) {
         }else {
          e.preventDefault();
         }
  })
})

let url = "http://localhost:8094/ExpenseReimbursement/updateemployee";

const sendAjaxGet = (url, func) => {
	let xhr = new XMLHttpRequest() || new ActiveXObject("Microsoft.HTTPRequest");
	xhr.onreadystatechange = function() {
		if (this.readyState==4 && this.status==200) {
			func(this);
		}
	}
	xhr.open("GET", url);
	xhr.send();
}

sendAjaxGet(url, display);

function display(xhr) {
	employeeArr = JSON.parse(xhr.response).requests;
	document.getElementById("First_name").value = employeeArr.firstname;
	document.getElementById("Last_name").value = employeeArr.lastname;
	document.getElementById("User_name").value = employeeArr.username;
	document.getElementById("Password").value = employeeArr.password;
	document.getElementById("Address").value = employeeArr.address;

}