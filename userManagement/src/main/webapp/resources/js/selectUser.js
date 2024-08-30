const updateBtn = document.querySelector("#updateBtn");
const deleteBtn = document.querySelector("#deleteBtn");
const goToList = document.querySelector("#goToList");

goToList.addEventListener("click", () => {
  location.href = "/selectAll"; // 목록 페이지 요청
});

// 삭제 버튼이 클릭 되었을 때
// GET 방식으로 하면 주소에 정보가 담겨야해서 아무나 삭제할 수 있음
// -> POST 방식을 사용하기 위해 form태그를 만들어서 요청함
deleteBtn.addEventListener("click", () => {
  // confirm을 이용해서 삭제할지 확인
  if(!confirm("삭제 하시겠습니까?")) return;

  /* form 태그, input 태그 생성 후 body 제일 마지막에 추가해 submit하기 */

  // form 요소 생성
  const deleteForm = document.createElement("form");

  // 요청 주소 설정
  deleteForm.action = "/deleteUser";

  // 데이터 전달 방식 설정
  deleteForm.method = "POST";

  // input 요소 생성
  const input = document.createElement("input");

  // input을 form에 자식으로 추가
  deleteForm.append(input);

  // input type, name, value 설정
  input.type = "hidden"
  input.name = "userNo"

  const userNoTd = document.querySelector("#userNoTd");
  input.value = userNoTd.innerText;

  // body 태그 제일 마지막에 form 추가
  document.querySelector("body").append(deleteForm);

  // deleteForm 제출하기
  deleteForm.submit();
});
