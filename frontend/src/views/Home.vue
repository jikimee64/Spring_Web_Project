<template>
	<div class="home">
		<h1>This is Home page</h1>
		<KossieCoder title="home title" name="Kossie Coder" />
		<form action>
			<!-- <InputField :name="name" @update-name="updateName" /> -->
			<!-- 넙겨받은 value가 updateName의 파라미터로 들어감 -->
			<!-- <InputField :name="name" @update-name="name = $event.target.value" /> -->
			<!-- 데이터를 넘겨주고 다시 받는 과정 너무 복잡... 컴포넌트에도 v-model를 사용해서
			해결할 수 있음(자동으로 차일드 컴포넌트의 value를 보냄)-->
			<InputField v-model="name" />
			<br /><button @click="updateName">Submit</button>
			<button>Submit</button>
		</form>
		{{name}}
	</div>
</template>

<script>
import KossieCoder from '@/components/KossieCoder.vue'
import InputField from '@/components/InputField.vue'
//속성들이 들어감
export default {
	components: {
		KossieCoder,
		InputField
	},
	data() {
		return {
			name: 'Kossie Coder'
		}
	},

	beforeCreate(){ //데이터에 접근 불가
		//console.log('beforeCreate', this.name)
	},

	created(){ //데이터에 접근 가능
	//ex)보통 서버에 데이터를 요청하고 넘어온 데이터를 업데이트 해줄때 사용?
	//입문자는 처음에 created를 많이 사용
		//console.log('created', this.name)
	},

	beforeMount(){
		//alert('beforeMount')
	}, 
	mounted(){
		//alert('mounted')
	},

	beforeUpdate(){
		//alert('beforeUpdate')
	},
	updated(){
		//alert('updated')
	},

	//home -> about 이동시 home에 있는게 destroy
	beforeDestroy(){ 
		//메모리 누수를 방지하기 위해 이벤트리스너 삭제 or 데이터초기화 등을 많이 선언함
		//alert('beforeDestroy')
	},

	destroyed(){
		
	},
	 methods: {
	 	updateName() {
	     this.name = 'hello';
	 	}
	 }


}
</script>


<style scoped>
/* scoped를 넣어야만 한 페이지 안에있는 template안에서만
css가 먹힘 scoped를 넣어주는게 국룰 */
h1 {
	color: red;
}
</style>