<template>
	<div class="spring">
		<button v-on:click="axiosTest">액시오스 테스트</button>
	</div>
</template>

<script>
import axios from "axios";
export default {

	methods: {
		axiosTest() {
			axios({
				method: 'post',
				url: 'spring.vue',
				headers: { 'Content-Type': 'application/json' }
			}).then(function (res) {
				console.log(res);
			})
		}

	}
}
</script>

<style>
</style>