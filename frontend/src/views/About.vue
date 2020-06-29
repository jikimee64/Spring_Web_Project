<template>
	<div class="about">
		<h1>This is an about page</h1>

<!-- slot
자식 -> 부모 -> 자식..
코지속성을 받아서 부모에 넣어주고 부모에서 다시 template안에 
넣어준뒤 자식에게 보냄
프로젝트할때 유용하게 사용될때가 있을거임
 -->

<!-- 코지코더 사이에 정의한 dom태그가 slot에 들어감 -->
    <KossieCoder>
        <!-- v-slot: 대신 #만 적어도됨 -->
     <!-- <template v-slot:header="props">
       <p>header11 {{props.kossie}}</p> -->
         <template #header="{ kossie }">
       <p>header11 {{kossie}}</p>
       <!-- props 생략 가능 -->
     </template>
      <template v-slot:footer>
       <p>footer11</p>
     </template>

     <!-- default를 쓰면 slot에 이름을 주지 않은 곳에 들어감 -->
      <template v-slot:default>
       <p>default11</p>
     </template>


    </KossieCoder>

		<!-- <KossieCoder /> -->
  <!-- <KossieCoder :title="title" /> -->
    <!-- number타입으로 넘어감-->
	</div>
</template>

<script>
import KossieCoder from '@/components/KossieCoder.vue'
export default {
	components: {
		KossieCoder
  }
    // data(){
  //   return {
  //     title: 5
  //   }
  // }
}
</script>

