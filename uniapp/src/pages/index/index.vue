<template>
    <view class="matching">
    <Matching/>
    </view>
    <PlayGround/>
</template>

<script setup lang='ts'>
import Matching from './components/matching/matching.vue';
import PlayGround from './components/PlayGround/PlayGround.vue';
</script>

<style lang='scss'>
page{
  width: 100%;
  height: 100%;
  background: white;
}
.matching{
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 100%;
}
</style>