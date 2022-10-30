document.getElementById('content').addEventListener('input', function(event) {
	document.getElementById('submit').disabled = !this.value;
}, false);
//input id가 content 인것에대해 input 데이터가 있을경우 submit 버튼 활성화