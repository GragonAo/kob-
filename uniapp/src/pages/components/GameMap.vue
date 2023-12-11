<template>
	<view class="gamemap">
		<canvas style="width: 400px; height: 400px; background-color: wheat;" canvas-id="canvas"></canvas>
		<view class="player-input">
		<button @click="onBtnUpClick">
			上
		</button>
		<button @click="onBtnRightClick">
			右
		</button>
		<button @click="onBtnDownClick">
			下
		</button>
		<button @click="onBtnLeftClick">
			左
		</button>
	</view>
	</view>

</template>

<script setup lang="ts">
import { loop } from '@/assets/scripts/AcGameObject';
import { GameMap } from '@/assets/scripts/GameMap';
import { useGameMapStore } from '@/stores/modules/gameMap';
import { ref } from 'vue';
import { getCurrentInstance } from 'vue';
import { onMounted } from 'vue';
const gameMap = ref<GameMap>();
onMounted(() => {
	const instance = getCurrentInstance();
	const ctx = uni.createCanvasContext("canvas", instance);
	const gameMapStore = useGameMapStore();
	gameMapStore.setGameMapInfo(ctx);
	loop();
  if(ctx){
    gameMap.value = new GameMap(ctx);
  }
});

const onBtnUpClick = ()=>{
	if(gameMap){
		gameMap.value?.next_step(0);
	}
}
const onBtnDownClick = ()=>{
	if(gameMap){
		gameMap.value?.next_step(2);
	}
}
const onBtnLeftClick = ()=>{
	if(gameMap){
		gameMap.value?.next_step(3);
	}
}
const onBtnRightClick = ()=>{
	if(gameMap){
		gameMap.value?.next_step(1);
	}
}
</script>

<style scoped>
.gamemap {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.player-input {
  width: 100%;
}
</style>
