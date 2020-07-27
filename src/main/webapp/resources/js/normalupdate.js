
			var flag = false; 
			
			$('#updateform').submit(
					function() {
						// alert("가입");
						if ($('#user_id').val() == "") { // 아이디 검사
							alert('ID를 입력해 주세요.');
							$('#user_id').focus();
							return false;
						} else if ($('#user_pwd').val() == "") { // 비밀번호 검사
							alert('비밀번호를 입력해 주세요.');
							$('#user_pwd').focus();
							return false;
						} else if ($('#re-password').val() == "") { // 비밀번호 검사
							alert('비밀번호 확인을 입력해 주세요.');
							$('#re-password').focus();
							return false;
						} else if ($('#user_name').val() == "") { // 이름 검사
							alert('이름을 입력해 주세요.');
							$('#user_name').focus();
							return false;
						} else if ($('#user_email').val() == "") { // 이메일 체크
							alert('이메일 주소를 입력해 주세요.');
							$('#user_email').focus();
							return false;
						} else if ($('#nickname').val() == "") {
							alert('닉네임을 입력해 주세요.');
							$('#nickname').focus();
							return false;
						} else if (regFlag[0] === false || regFlag[1] === false
								|| regFlag[2] === false || regFlag[3] ===false) {
							alert('모든 값을 조건에 맞게 입력해주세요.')
							return false;
						} else if (toggleid === false) {
							alert('아이디 중복체크를 해주세요.')
							return false;
						} else if (pwCheck === false) {
							alert('입력하신 비밀번호가 일치하지 않습니다.')
							return false;
						} else if (toggle === false) {
							alert('이메일 중복체크를 해주세요.')
							return false;
						}
						
						else{
							flag = true;
						}
		
						if(flag === true){
							/* var formData = $(".joinform").serialize() */
							/* var file = $(#'joinform')[0].files[0]; */
							var formData = new FormData(this);
							console.log(formData)
			
							console.log("데이터 : " + formData.get("user_id"))
							
							$.ajax({
								url : "/member/normaljoin.do",
								processData : false,
								contentType : false,
								cache : false,
								type : 'POST',
								data : formData,
								success : function(data) {
								},
								error : function(e) {
									console.log("error:", e);
								}
							})
					}
						
			});

	
		// 내용입력 이랑 입력 강제

			var toggle = false;
			var pwCheck = false;
			var regFlag = [ false, false, false, false ];
			var toggleid = false;

			// 유효성 검사
			// 목표 : 이름은 2글자 이상 10글자 이하로 정한다
			// 숫자나 특수문자가 입력되면 안된다.

			// 1. input창에 값을 받아서 확인한다.
			// 2. 값이 올바르게 입력되면 "GOOD"을 프린트한다.
			// 3. 값이 올바르지 않으면 "글자나 영어를 2~5개까지 입력하셔야 합니다."라는 경고창이 뜬다.

		
			let idInput = document.querySelector("#user_id");
			let idResult = document.querySelector(".idcheck");
			
			function IdCheck() {
				if (idInput.value.length > 1 && idInput.value.length <= 12) {
					idResult.innerHTML = '유효성 조건에 만족하셨습니다. 중복체크를 해주세요.'
					regFlag[3] = true;
				
				} else {
					idResult.innerHTML = "아이디는 2글자 이상, 12글자 이하로 해주세요.";
					regFlag[3] = false;
				}
			}
			idInput.addEventListener("blur", IdCheck);

			let check_num = /[0-9]/; // 숫자
			let check_eng = /[a-zA-Z]/; // 문자
			let check_spc = /[~!@#$%^&*()_+|<>?:{}]/; // 특수문자
			let check_kor = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/; // 한글체크

			let nameInput = document.querySelector("#user_name");
			let nameResult = document.querySelector(".name");

			function nameCheck() {
				if (nameInput.value.length > 1 && nameInput.value.length < 10) {
					if (check_kor.test(nameInput.value)
							&& !check_num.test(nameInput.value)
							&& !check_eng.test(nameInput.value)
							&& !check_spc.test(nameInput.value)) {
						nameResult.innerHTML = "사용 가능한 이름 입니다.";
						regFlag[0] = true;
					} else {
						nameResult.innerHTML = "한글만 입력이 가능합니다.(2~10자)";
						regFlag[0] = false;
					}
				} else if (nameInput.value.length === 0) {
					nameResult.innerHTML = "이름을 입력해주세요.(2~10자)";
					regFlag[0] = false;
				} else {
					nameResult.innerHTML = "한글만 입력이 가능합니다.(2~10자)";
					regFlag[0] = false;
				}
			}

			nameInput.addEventListener("blur", nameCheck);

			// 목표 : 이메일문법확인하기

			let emailInput = document.querySelector("#user_email");
			let emailResult = document.querySelector(".email");
			let reg_email = /^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/;

			function emailCheck() {
				if (reg_email.test(emailInput.value)) {
					emailResult.innerHTML = "사용 가능한 이메일 입니다.";
					regFlag[1] = true;
				} else {
					emailResult.innerHTML = "형식에 맞지 않는 이메일 주소입니다.";
					regFlag[1] = false;
				}
			}

			emailInput.addEventListener("blur", emailCheck);

			// 목표 : 비밀번호를 입력하세요.

			let passwordInput = document.querySelector("#user_pwd");
			let passwordResult = document.querySelector(".password");
			let reg_password = /^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/;

			function passwordCheck() {
				if (reg_password.test(passwordInput.value)) {
					passwordResult.innerHTML = "사용 가능한 비밀번호 입니다.";
					regFlag[2] = true;
				} else {
					passwordResult.innerHTML = "비밀번호는 8자 이상이어야 하며, 숫자/소문자/특수문자를 모두 포함해야 합니다.";
					regFlag[2] = false;
				}
			}

			passwordInput.addEventListener("blur", passwordCheck);

			$('#re-password').keyup(function() {
				if ($('#user_pwd').val() != $('#re-password').val()) {
					$('#message').text('암호가 일치하지 않습니다');
					pwCheck = false;
				} else {
					$('#message').text('암호가 일치합니다.');
					pwCheck = true;
				}
			});

			$('#IdCheck').click(
					function(e) {
						e.preventDefault();
						var value = $('#user_id').val();
						$.ajax({
							url : "/ajax/idcheck.do",
							type : 'POST',
							dataType : 'json',
							data : {
								id : $('#user_id').val()
							},
							success : function(data) {
								// 아이디 중복시 아이디 인풋값 제거

								if ($('#user_id').val() === '') {
									
									swal({
										  title: "아이디를 입력해주세요",
										  icon: "info",
										  buttons: true
										})
									
									// $('.idcheck').html("아이디를 입력해주세요.")
									toggleid = false;
								} else if (data == 1) { // 아이디 존재
									
									swal({
										title: "동일한 아이디가 존재합니다.",
										  icon: "info",
										  buttons: true
										})
									
									// $('.idcheck').html("동일한 아이디가 존재합니다.")
									$('#user_id').val("");
									$('#user_id').focus();
									toggleid = false;
								} else {
									if(regFlag[3] === false){
										swal({
											  title: "유효성 검사부터 해주세요.",
											  icon: "info",
											  buttons: true
											  
											})
										toggleid = false;
									}else{
										
										swal({
											  title: "사용하셔도 됩니다.",
											  icon: "success",
											  buttons: true
											  
											})
											toggleid = true;
									}
									
										
									// $('.idcheck').html("사용하셔도 됩니다.")
									toggleid = true;
								}
							},
							error : function(request, status, error) {
								console.log("code:" + request.status + "\n"
										+ "error:" + error);

							}
						})

					});
			
			$('#EmailCheck').click(
					function(e) {
						e.preventDefault();
						var value = $('#user_email').val();
						$.ajax({
							url : "/ajax/onlyEmailCheck.do",
							type : 'POST',
							dataType : 'json',
							data : {
								user_email : $('#user_email').val()
							},
							success : function(data) {
								// 이메일 중복시 아이디 인풋값 제거

								if ($('#user_email').val() === '') {
									
									swal({
										  title: "이메일을 입력해주세요",
										  icon: "info",
										  buttons: true
										})
									toggle = false;
								} else if (data == 1) { // 이메일이 존재
									
									swal({
										title: "동일한 이메일이 존재합니다.",
										  icon: "info",
										  buttons: true
										})
									
									// $('.idcheck').html("동일한 아이디가 존재합니다.")
									$('#user_email').val("");
									$('#user_email').focus();
									toggle = false;
									
								} else {
									if(regFlag[1] === false){
										swal({
											  title: "유효성 검사부터 해주세요.",
											  icon: "info",
											  buttons: true
											})
										toggle = false;
									}else{
										swal({
											  title: "사용하셔도 됩니다.",
											  icon: "success",
											  buttons: true 
											})
											toggle = true;
									}
			
									// $('.idcheck').html("사용하셔도 됩니다.")
									toggle = true;
								}
							},
							error : function(request, status, error) {
								console.log("code:" + request.status + "\n"
										+ "error:" + error);

							}
						})

					});


			// Add the following code if you want the name of the file appear on
			// select
			$(".custom-file-input").on(
					"change",
					function() {
						var fileName = $(this).val().split("\\").pop();
						$(this).siblings(".custom-file-label").addClass(
								"selected").html(fileName);
					});

			// 썸네일 시작
			var file = document.querySelector('#file');

			file.onchange = function() {
				var fileList = file.files;

				// 읽기
				var reader = new FileReader();
				reader.readAsDataURL(fileList[0]);

				// 로드 한 후
				reader.onload = function() {
					document.querySelector('#preview').src = reader.result;
				};
			};

