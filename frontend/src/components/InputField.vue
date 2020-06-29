<template>
	<div>
		<label for>Name</label>
		<input type="text" :value="value" style="padding:30px;
      border: 2px solid green;" 
      @input="$emit('input', $event.target.value)"> 
      <!-- 3번째 방법 -->
      
      <!-- @input="updateName"/> 1번째 방법--> 
          <!-- @input="$emit('update-name', $event)"> 2번째 방법 -->
<!-- https://skout90.github.io/2018/01/21/Vue/v-model-%EC%BB%A4%EC%8A%A4%ED%85%80-%EC%BB%B4%ED%8F%AC%EB%84%8C%ED%8A%B8-%EC%A0%9C%EC%9E%91/ -->
<!-- https://kamang-it.tistory.com/entry/Vue20%EA%B0%92%EC%9D%98-%EB%8F%99%EC%A0%81-%EB%B3%80%EA%B2%BD%EA%B3%BC-%EC%96%91%EB%B0%A9%ED%96%A5%EB%B0%94%EC%9D%B8%EB%94%A9-%EA%B7%B8%EB%A6%AC%EA%B3%A0-vmodel%EC%8B%A4%EC%9A%A9%ED%8E%B8 -->
      <!--
           props인데 v:model이면 양방향 바인딩이기때문에 X
    ":value=""로 ㄱㄱ-->
    <!-- value가 바뀌었을때 parent로 보내는 방법    -->

  
	</div>
</template>

<script>
export default {
    props:{
        value: {
            type:String,
            required:true
        }
    },
    methods:{
        // updateName(e) {
        //     console.log(e.target.value);
        //     this.$emit('update-name', e.target.value)
        //     //$emit를 통해 부모로 보냄
        //     //$emit(이름, 보낼 값)
        // }
    }
}
</script>

<style>
</style>