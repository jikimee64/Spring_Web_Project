<template>
	<div class="findid">
		<div class="row">
			<h1>아이디 찾기</h1>
		</div>
		<div class="row">
			<div class="row">
				<div class="input-field col s12">
					<input
						id="username"
						v-model="username"
						type="text"
						class="validate"
						placeholder="사용자 아이디"
					/>
				</div>
			</div>
			<a class="btn waves-effect waves-light" @click="findid">
				아이디찾기
				<!-- <i class="material-icons right">찾기</i> -->
			</a>
			<br/><hr/>

			{{findId}}

			 <!-- <a class="btn waves-effect waves-light" @click="users">
				findID 체크
				 <i class="material-icons right">찾기</i> 
			</a>  -->
			<br/><hr/>
		 <!-- <p v-if="findId !== ''">findID비움</p>
 			<p v-else>{{this.$store.state.user.username}}</p>  -->
			<!-- <a class="btn waves-effect waves-light" @click="users">
				클릭하세요.
				<!-- <i class="material-icons right">찾기</i>
			</a> -->
			
		</div>
	</div>
</template>

<script>

export default {
	
	data() {
		return {
			username: '',
			findId: '',
		}
	},

	created () {
      	this.$store.dispatch('user/getId', this.username)
    },

	methods: {
		findid() {
			// this.$store.dispatch('user/getId', this.username)
			
				this.findId = this.$store.state.user.username;
		
			//this.findId = this.$store.state.user.username;
		 	  
    },
			
			
			//console.log('찾은 아이디 : ' + this.findId)
		// users(){
		// 	this.findId = this.$store.state.user.username;
		// 	console.log("제발 : " + this.findId)
		// 	 this.findId = this.$store.state.user.username;
		// 	 console.log('찾은 아이디 : ' + this.findId)
		// }
	},
	computed: {
			//vuex에서 데이터를 받음

	}
}
</script>

<style>
</style>