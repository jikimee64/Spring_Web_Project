<template>
	<div class="container">
		<div class="row">
			<h1>회원 가입</h1>
		</div>
		<div class="row">
			<div class="row">
				<div class="input-field col s12">
					<input
						id="username"
						v-model="user.username"
						type="text"
						class="validate"
						placeholder="사용자 아이디"
					/>
				</div>
			</div>
			<div class="row">
				<div class="input-field col s12">
					<input
						id="password"
						v-model="user.password"
						type="password"
						class="validate"
						placeholder="비밀번호"
					/>
				</div>
			</div>
			<a class="btn waves-effect waves-light" @click="signup">
				회원가입
				<i class="material-icons right">send</i>
			</a>
		</div>
	</div>
</template>

<script>

export default {
	computed: {

	},
	data() {
		return {
			user: {
				username: '',
				password: '',
			}
		}
	},

	methods: {
		signup() {
            this.$store.dispatch('user/getUsers', this.user)
		}
	}
}
</script>

<style>
</style>