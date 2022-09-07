document.addEventListener("DOMContentLoaded", function (e) {
  createOnStartUp();
  document.getElementById("approveform").addEventListener("submit", function(e) {
         if(confirm("Are you sure ? ") == true) {
         }else {
          e.preventDefault();
         }
  })
})



const createOnStartUp = () => {
ShowAll();
}

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

const display = (xhr) => {
	requestArr = JSON.parse(xhr.responseText).requests;
	let table = document.getElementById("requestTable");
	table.removeChild(document.getElementById("requestTableBody"));
	let newBody = document.createElement("tbody");
	newBody.setAttribute("id", "requestTableBody");
	table.appendChild(newBody);
	for (i in requestArr) {
		let newRow = document.createElement("tr");

		newRow.innerHTML =
			`<td>${requestArr[i].id}</td>
			<td>${requestArr[i].employee.firstname} ${requestArr[i].employee.lastname}</td>
			<td>${requestArr[i].description}</td>
			<td>$ ${requestArr[i].amount}</td>
			<td>${requestArr[i].isApproved ? "Reviewed" : "Pending"}</td>
			<td>${requestArr[i].decision ? requestArr[i].decision : "Pending"}</td>
			<td>${requestArr[i].manager.firstname} ${requestArr[i].manager.lastname}</td>
			`
		newBody.appendChild(newRow);
	}
}

const ShowAll = () => {
	url = "http://localhost:8094/ExpenseReimbursement/managerallReqs";
	sendAjaxGet(url, display);
}

const ShowPending = () => {
	url = "http://localhost:8094/ExpenseReimbursement/managerpendingReqs";
	sendAjaxGet(url, display);
}

const ShowResolved = () => {
	url = "http://localhost:8094/ExpenseReimbursement/managerresolvedReqs";
	sendAjaxGet(url, display);
	}