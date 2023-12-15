<template>
    <PlayGround v-if="gameStore.gameState == GameState.Game"/>
    <view class="matching" v-else>
    <Matching/>
    </view>
</template>

<script setup lang='ts'>
import Matching from './components/matching/matching.vue';
import PlayGround from './components/PlayGround/PlayGround.vue';
import { onShow,onHide} from '@dcloudio/uni-app';
import { ref } from 'vue';
import { connectWebSocket } from '@/utils/connectWebSocket';
import { useGameStore } from '@/stores/modules/game';
import { GameState } from '@/enums/game';
const gameStore = useGameStore();
const wbSocket = ref<connectWebSocket>();
//页面显示时
onShow(()=>{
  wbSocket.value = new connectWebSocket();
  wbSocket.value.socketTask();
  gameStore.initGameStore();
});
onHide(()=>{
  wbSocket.value?.closeSocket();
  gameStore.clearGameState();
})
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