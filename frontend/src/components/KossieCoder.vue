<template>
    <div>
        <p>header</p>
        <!-- 속성의 이름을 kossie로 올리는거 -->
         <slot name="header" :kossie="kossie"></slot> 
        <p>Body</p>
         <slot name="footer"></slot>
        <p>footer</p>
        <slot></slot>
        <!-- 각각의 slot에 이름을 주어 
        원하는 slot에 줄 수 있다 -->


        <!-- <h1>{{title}}</h1>
        <slot></slot>
        <p>{{name}}</p><br>
        <button @click="updateName">Change Name</button> -->
    </div>
</template>

<script>
export default {
    props: {
        title:{
            type:String, //받는 Type설정 가능
            required: false, //코지코더 컴포넌트를 쓰고싶으면 반드시 props를 보내라!!
            default: 'default title' //required없이 디폴트 설정 가능
        },
        //props 여러개 받기 가능
        name: {
            type:String,
            default: 'default name'
        },
        //받은 props는 직접적으로 변경하면 안됨
        //부모에서도 바꾸고 자식에서도 바꾸면 헷갈리기 때문에..
        //데이터를 넘겨주는 부모에서 데이터를 바꿔야 된다
    },
    //title을 props로 받을 것이다
    data(){
        return{
            kossie: 'coder'
            //name: 'Kossie Coder',
            //props랑 이름이 겹치면 안됨
        }
    },
    methods:{
        updateName(){
            this.name = 'Kossie Coder Updated';
             //받은 props는 직접적으로 변경하면 안됨
             //작동은 하지만 warning이 뜸
        }
    }
}
</script>

<style scoped>

</style>